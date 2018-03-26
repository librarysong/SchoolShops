package cn.tsu.edu.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;


public class ImageUtil {
	private static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss"); // 时间格式化的格式
	private static final Random r = new Random();

   public static void main(String[] args) throws Exception
   {
	   Thumbnails.of(new File("E://image/xiaohuangren.jpg")).
	   size(200, 200).watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.5f).outputQuality(0.8f).toFile("E://image/xiaohuangren6.jpg");
   }
	/*public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}*/
	
	public static String generateThumbnail(InputStream thumbnail, String fileName,String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	private static void makeDirPath(String targetAddr) {
		String realFileParentPath =PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

/*	private static String getFileExtension(CommonsMultipartFile cFile) {
		String originalFileName = cFile.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}*/
	
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	public static String getRandomFileName() {
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		String nowTimeStr = sDateFormat.format(new Date()); // 当前时间
		return nowTimeStr + rannum;
	}
	
	//删除图片
	public static void deleteFileOrPath(String storePath)
	{
		File fileOrPath=new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists())
		{
			if(fileOrPath.isDirectory())
			{
				File files[]=fileOrPath.listFiles();
				for(int i=0;i<files.length;i++)
				{
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
