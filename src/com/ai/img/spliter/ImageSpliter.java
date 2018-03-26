package com.ai.img.spliter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface ImageSpliter {

	/**
	 * 将图像左右两侧的空白部分去除,并将图片横向且分为几部分
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public  List<BufferedImage> splitInHorizontal(BufferedImage image) throws IOException;
	
	/**
	 * 去除图像上下两侧的空白部分
	 * @param fregments
	 * @throws IOException
	 */
	public  void splitInVertical(List<BufferedImage> fregments) throws IOException;
	
	boolean isBlack(int rgb);
}
