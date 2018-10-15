package com.imooc.o2o.util;

public class PathUtil {
	/**
	 * 获取当前操作系统的分隔符
	 */
	private static String separator = System.getProperty("file.separator");
	/**
	 * 上传图片的初始地址D:/javacode/o2omall/img
	 * @return String 
	 */
	public static String getImgBasePath() {
		//获取当前操作系统的名字
		String os=System.getProperty("os.name");
		String basePath="";
		if(os.toLowerCase().startsWith("win")) {
			basePath="D:/javacode/o2omall/img";
		}else {
			basePath="/home/laojia/img";
		}
		basePath.replace("/", separator);
		return basePath;
	}
	
	/**
	 *  水印图片的上传地址
	 * @param shopId
	 * @return 水印图片的上传地址+shopId
	 */
	public static String  getShopImagePath(long shopId) {
		
		String imagePath="/upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", separator);
		
	}
}
