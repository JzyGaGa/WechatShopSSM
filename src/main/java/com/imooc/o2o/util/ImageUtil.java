package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions; 
/**
 * 文件处理工具类
 * @author 59842
 *
 */
public class ImageUtil {
	
	private static final Random r=new Random();
	private static final SimpleDateFormat sDateFormate=new SimpleDateFormat("yyyyMMddHHmmss");
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	/**
	 * 形成水印图片
	 * @param thumbnail Spring自带的文件处理对象
	 * @param targetAddr 制作水印图片的原图片
	 * @return
	 */
	public static String generateThumbnail(MultipartFile thumbnail,String targetAddr) {
		//新取的名字
		String realFileName=getRandomFileName();
		//获取文件的扩展名
		String extension=getFileExtension(thumbnail);
		//创建文件夹
		makeDirPath(targetAddr);
		//相对地址
		String relativeAddr=targetAddr+realFileName+extension;
		//目标地址
		File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")), 0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	/**
	 * 创建文件夹
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
		File dirPath=new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}
	/**
	 * 获取输入文件流的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(MultipartFile cFile) {
		
		String originalFilename = cFile.getOriginalFilename();
		return originalFilename.substring(originalFilename.lastIndexOf("."));
	}
	/**
	 * 生成随机的文件名称，当前的年月日分秒+五位随机数
	 * @return
	 */
	private static String getRandomFileName() {
		//随机生成五位数
		int rannum=r.nextInt(89999)+10000;
		String nowTimeStr = sDateFormate.format(new Date());
		return nowTimeStr+rannum;
	}
	/**
	 * storePath是文件路径还是目录路径，
	 * 如果storePath是文件路径则删除文件
	 * 如果storePath是目录路径则删除该目录下的所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath=new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			//如果fileOrPath是目录文件的话就递归把他里面文件删除
			if(fileOrPath.isDirectory()) {
				File[] listFiles = fileOrPath.listFiles();
				for(int i=0;i<listFiles.length;i++) {
					listFiles[i].delete();
				}
				
			}
			//如果fileOrPath是文件的话就把他删除
			fileOrPath.delete();
		}
	}
	/**
	 * //size(337, 640)。outputQuality(0.5f)。toFile(dest);
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(CommonsMultipartFile thumbnail, String targetAddr) {
		//新取的名字
		String realFileName =getRandomFileName();
		//获取文件输入流的扩展名
		String extension = getFileExtension(thumbnail);
		//创建文件夹
		makeDirPath(targetAddr);
		//相对地址
		String relativeAddr = targetAddr + realFileName + extension;
		//目标地址
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			//size(337, 640)。outputQuality(0.5f)。toFile(dest);
			Thumbnails.of(thumbnail.getInputStream()).size(337, 640).outputQuality(0.5f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}
	/**
	 * 将多个图片处理完毕，并返回相对地址
	 * @param imgs
	 * @param targetAddr
	 * @return
	 */
	public static List<String> generateNormalImgs(List<MultipartFile> imgs, String targetAddr) {
		
		int count = 0;
		//相对地址的路径"/upload/item/shop/"+shopId+"/";
		List<String> relativeAddrList = new ArrayList<String>();
		if (imgs != null && imgs.size() > 0) {
			//创建文件夹"/upload/item/shop/"+shopId+"/";
			makeDirPath(targetAddr);
			//根据文件，获得任何一个图片的存储的位置和名字
			for (MultipartFile img : imgs) {
				//随机生成图片名字
				String realFileName =  getRandomFileName();
				//获得扩张名
				String extension = getFileExtension(img);
				//获得相对的路径名字
				String relativeAddr = targetAddr + realFileName + count + extension;
				//获得目标地址
				File dest = new File(PathUtil.getImgBasePath()+ relativeAddr);
				
				count++;
				try {
					Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
				} catch (IOException e) {
					throw new RuntimeException("创建图片失败：" + e.toString());
				}
				relativeAddrList.add(relativeAddr);
			}
		}
		//返回相对地址
		return relativeAddrList;
	}

}
