package cn.tsu.edu.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tsu.edu.o2o.dto.ShopExecution;
import cn.tsu.edu.o2o.entity.PersonInfo;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.enums.ShopStateEnum;
import cn.tsu.edu.o2o.exceptions.ShopOperationException;
import cn.tsu.edu.o2o.service.ShopService;
import cn.tsu.edu.o2o.util.HttpServletRequestUtil;
import cn.tsu.edu.o2o.util.ImageUtil;
import cn.tsu.edu.o2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper=new ObjectMapper();
		Shop shop=null;
		try {
			shop=mapper.readValue(shopStr, Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
			shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2.注册店铺
		if(shop!=null &&shopImg!=null) {
			
			//Session  TODO
			PersonInfo owner=new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
				if(se.getState()==ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg",se.getStateInfo());
					return modelMap;
				}
				
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}catch(IOException e)
			{
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		
		//3.返回结果
	}
	
	//把CommonsMultipartFile转换为File
//	private static  void inputStreamToFile(InputStream ins,File file)
//	{
//		OutputStream os=null;
//		try {
//			os=new FileOutputStream(file);
//			int bytesRead=0;
//			byte[] buffer=new byte[1024];
//			while((bytesRead=ins.read(buffer))!=-1) {
//				os.write(buffer,0,bytesRead);
//			}
//		}catch(Exception e) {
//			throw new RuntimeException("调用inputstreamtofile产生异常"+e.getMessage());
//		}finally {
//			try {
//				if(os!=null) {
//					os.close();
//				}
//				
//				if(ins!=null) {
//					ins.close();
//				}
//			}catch(Exception e) {
//				throw new RuntimeException("调用inputstreamtofile关闭IO产生异常"+e.getMessage());
//			}
//		}
//	}

}
