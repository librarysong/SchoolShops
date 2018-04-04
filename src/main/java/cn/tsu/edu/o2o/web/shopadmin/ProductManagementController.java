package cn.tsu.edu.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tsu.edu.o2o.dto.ImageHolder;
import cn.tsu.edu.o2o.dto.ProductExecution;
import cn.tsu.edu.o2o.entity.Product;
import cn.tsu.edu.o2o.entity.ProductCategory;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.enums.ProductStateEnum;
import cn.tsu.edu.o2o.exceptions.ProductOperationException;
import cn.tsu.edu.o2o.service.ProductCategoryService;
import cn.tsu.edu.o2o.service.ProductService;
import cn.tsu.edu.o2o.util.CodeUtil;
import cn.tsu.edu.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	//支持上传商品详情土的最大数量
	
	private static final int IMAGEMAXCOUNT=6;
	
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request,
				"productStr");
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				//取出缩略图并构建imageHolder对象
				CommonsMultipartFile thumbnailFile=(CommonsMultipartFile)multipartRequest.getFile("thumbnail");
				thumbnail=new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
				//取出详情图
				for(int i=0;i<IMAGEMAXCOUNT;i++) {
					CommonsMultipartFile productImgfile=(CommonsMultipartFile) multipartRequest.getFile("productImg"+i);
					if(productImgfile!=null) {
						ImageHolder productImg=new ImageHolder(productImgfile.getOriginalFilename(), productImgfile.getInputStream());
						productImgList.add(productImg);
					}else {
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				
				Shop currentShop = (Shop) request.getSession().getAttribute(
						"currentShop");
				
				product.setShop(currentShop);
				ProductExecution pe = productService.addProduct(product,
						thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productId > -1) {
			Product product = productService.getProductById(productId);
			List<ProductCategory> productCategoryList = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request) {
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,
				"statusChange");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnail=null;
		List<ImageHolder> productImgs = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//若请求中存在文件流 则取出相关的文件
		
		try {
			
			if(multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
				
				//取出缩略图并构建ImageHodler对象
				CommonsMultipartFile thuMultipartFile=(CommonsMultipartFile)multipartHttpServletRequest.getFile("thumbnail");
				
				if(thuMultipartFile!=null) {
					thumbnail=new ImageHolder(thuMultipartFile.getOriginalFilename(), thuMultipartFile.getInputStream());
				}
				//取出详情图列表并构建List
				for(int i=0;i<IMAGEMAXCOUNT;i++) {
					CommonsMultipartFile productImgFile=(CommonsMultipartFile)multipartHttpServletRequest.getFile("productImg"+i);
					if(productImgFile!=null) {
						//若取出的第i个详情图片不为空，则加入
						ImageHolder productImg=new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
						productImgs.add(productImg);
					}else {
						break;
					}
				}
			}
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			
			String productString=HttpServletRequestUtil.getString(request, "productStr");
			
			//尝试获取前端传来的表单string流并将其转换为product实体类
			product=mapper.readValue(productString, Product.class);
		}catch(Exception e) {
			
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		
		//非空判断
		if(product!=null) {
			try {
				
				//从session中获取当前店铺的id并赋值给product
				Shop currentShop=(Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				//开始进行商品信息的变更
				ProductExecution pe=productService.modifyProduct(product, thumbnail, productImgs);
				if(pe.getState()==ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
		
	}
	
	@RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductListByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
				&& (currentShop.getShopId() != null)) {
			long productCategoryId = HttpServletRequestUtil.getLong(request,
					"productCategoryId");
			String productName = HttpServletRequestUtil.getString(request,
					"productName");
			Product productCondition = compactProductCondition(
					currentShop.getShopId(), productCategoryId, productName);
			ProductExecution pe = productService.getProductList(
					productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	
	private Product compactProductCondition(long shopId,
			long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if (productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}



}
