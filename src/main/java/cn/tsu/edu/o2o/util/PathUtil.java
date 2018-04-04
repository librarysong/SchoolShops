package cn.tsu.edu.o2o.util;

/**
 * 
 * @author 宋维飞
 *
 */
public class PathUtil {
	private static String seperator=System.getProperty("file.separator");
	public static String getImgBasePath(){
		String os=System.getProperty("os.name");
		String basePath="";
		if(os.toLowerCase().startsWith("win")) {
			basePath="E:/image";
		}else {
			basePath="/home/song/image";
		}
		
		basePath=basePath.replace("/", seperator);
		return basePath;
	}
	
	public static String getShopImagePath(long shopId) {
		String imagePath="/upload/images/item/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
	}

}
