package com.ai.img.normalize;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 该类实现图像缩放归一化,为后期比对工作提供便利.
 * 
 * @author Administrator
 *
 */
public class ImageNormalize {

	// 定义归一化后的图像大小
	private static int TARGET_WIDTH = 20;
	private static int TARGET_HEIGHT = 20;
	
	public static BufferedImage normalize(BufferedImage img){
		
		//先调整原图像的长宽比
		BufferedImage process_rate = process_rate(img);
		
		//再调整图像的长宽像素个数.
		BufferedImage resize = resize(process_rate);
		
		return resize;
	}

	public static BufferedImage process_rate(BufferedImage img) {

		BufferedImage normalRateImg = null;

		int height = img.getHeight();
		int width = img.getWidth();
		// 如果高度和宽度的比值比归一图像的高度和宽度的比值小,说明当前图像比标准图像胖了,要给图片增高.
		if (height / width < TARGET_HEIGHT / TARGET_WIDTH) {

			int nomal_height = width * TARGET_HEIGHT / TARGET_WIDTH;
			normalRateImg = new BufferedImage(width, nomal_height, BufferedImage.TYPE_INT_RGB);

			int distance = nomal_height - height;

			// 算出两头各要增加的白色块行数
			int increase = distance / 2;

			// 头部新增的色块行用白色色块填充
			for (int i = 0; i < increase; i++) {
				for (int j = 0; j < width; j++) {
					normalRateImg.setRGB(j, i, Color.WHITE.getRGB());
				}
			}
			// 将图片复制过来
			for (int i = increase; i < increase + height; i++) {
				for (int j = 0; j < width; j++) {
					normalRateImg.setRGB(j, i, img.getRGB(j, i - increase));
				}
			}
			// 底部新增的色块行用白色色块填充
			for (int i = increase + height; i < nomal_height; i++) {
				for (int j = 0; j < width; j++) {
					normalRateImg.setRGB(j, i, Color.WHITE.getRGB());
				}
			}

		} else if (height / width > TARGET_HEIGHT / TARGET_HEIGHT) {
			// 如果高度和宽度的比值比归一图像的高度和宽度的比值大,说明当前图像比标准图像廋了,要给图片增肥.
			int nomal_width = height * TARGET_WIDTH / TARGET_WIDTH;
			normalRateImg = new BufferedImage(nomal_width, height, BufferedImage.TYPE_INT_RGB);
			int distance = nomal_width - width;
			// 算出两侧各要增加的色块列数
			int increase = distance / 2;

			// 左侧新增的色块列用白色色块填充
			for (int i = 0; i < increase; i++) {
				for (int j = 0; j < height; j++) {
					normalRateImg.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
			// 将图片复制过来
			for (int i = increase; i < increase + width; i++) {
				for (int j = 0; j < height; j++) {
					normalRateImg.setRGB(i, j, img.getRGB(i-increase, j));
				}
			}
			// 底部新增的色块行用白色色块填充
			for (int i = increase + width; i < nomal_width; i++) {
				for (int j = 0; j < height; j++) {
					normalRateImg.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		} else {
			normalRateImg = img;
		}

		return normalRateImg;

	}

	public static BufferedImage resize(BufferedImage sourceImg) {

		int sourceWidth = sourceImg.getWidth();
		int sourceHeight = sourceImg.getHeight();

		BufferedImage targeImage = new BufferedImage(TARGET_WIDTH, TARGET_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < TARGET_HEIGHT; i++) {
			// 计算出当前填充像素的纵坐标在原图像中的映射纵坐标.
			int mappingHeight = i * sourceHeight / TARGET_HEIGHT;
			if (mappingHeight > sourceHeight)
				mappingHeight = sourceHeight;

			for (int j = 0; j < TARGET_WIDTH; j++) {
				// 计算出当前填充像素的横坐标在原图像中的纵坐标.
				int mappingWidth = j * sourceWidth / TARGET_WIDTH;
				if (mappingWidth > sourceWidth)
					mappingWidth = sourceWidth;
				targeImage.setRGB(j, i, sourceImg.getRGB(mappingWidth, mappingHeight));
			}
		}

		return targeImage;
	}

}
