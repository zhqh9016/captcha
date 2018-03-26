package com.ai.img.preprocessor.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ai.img.preprocessor.ImagePreProcessor;

/**
 * 提供图片黑白化及降噪处理功能.
 * @author zhangqh27
 *
 */
public class NineAreaPreProcessor implements ImagePreProcessor {
	
	//判断一个像素是不是白色的阀值
	public static int IS_WHITE_RGB_VALUE = 550;
	
	/**
	 * 对图片进行黑白化及降噪处理
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public BufferedImage preProcess(String fileName) throws IOException{
		
		BufferedImage img = readImageFromFile(fileName);
		
		//将图片转为黑白点阵图
		imageToBinaryMode(img);
		
		//执行两次降噪
		removeBackGround(img);
		removeBackGround(img);
		removeBackGround(img);
		
		return img;
	}

	/**
	 * 去除噪点
	 * @param img
	 */
	public static void removeBackGround(BufferedImage img) {
		
		int height = img.getHeight();
		int width = img.getWidth();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(check_background_by_nine_area(j, i, img)==1){
					img.setRGB(j, i, Color.WHITE.getRGB());
				}
			}
		}
	}

	/**
	 * 从文件中读取图片文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage readImageFromFile(String fileName) throws IOException {
		BufferedImage img = ImageIO
				.read(new File(fileName));
		return img;
	}

	public static void imageToBinaryMode(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = img.getRGB(j, i);
				if(isWhite(rgb)==1){
					img.setRGB(j, i, Color.WHITE.getRGB());
				}else {
					img.setRGB(j, i, Color.BLACK.getRGB());
				}
			}
		}
	}
	
	/**
	 * 如果R/G/B三个值的和超过一定阀值的，就认为是白色。否则为黑色。
	 * @param i
	 * @return
	 */
	public static int isWhite(int i){
		Color color = new Color(i);
		if(color.getRed()+color.getGreen()+color.getBlue()>IS_WHITE_RGB_VALUE){
			return 1;
		}
		return 0;
	}
	
	public static int isBlack(int i){
		Color color = new Color(i);
		if(color.getRed()+color.getGreen()+color.getBlue()>IS_WHITE_RGB_VALUE){
			return 0;
		}
		return 1;
	}
	
	/**
	 * 通过计算九宫格范围内邻居的个数来判断是不是孤立点
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @return
	 */
	public static int check_background_by_nine_area(int x ,int y,BufferedImage image){
		
		//如果当前点为背景色,直接返回
		if(isWhite(image.getRGB(x, y))==1){
			return 1;
		}
		int height = image.getHeight();
		int width = image.getWidth();
		//用于临时存放九宫格背景色像素的个数
		int sum = 0;
		
		//如果是第一行
		if(y==0){
			//如果是第一列
			if(x==0){
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x+1, y))+isBlack(image.getRGB(x, y+1))+isBlack(image.getRGB(x+1, y+1));
				
			}else if(x==width-1){
			//如果是最后一列	
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x, y+1))+isBlack(image.getRGB(x-1, y+1));
			}else{
				sum= isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x, y+1))+isBlack(image.getRGB(x-1, y+1))+
						isBlack(image.getRGB(x+1, y))+isBlack(image.getRGB(x+1, y+1));
			}
		}else if(y == height-1){
		//如果是最后一行
			//如果是第一列
			if(x==0){
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x+1, y))+isBlack(image.getRGB(x, y-1))+isBlack(image.getRGB(x+1, y-1));
			}else if(x==width-1){
			//如果是最后一列	
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x, y-1))+isBlack(image.getRGB(x-1, y-1));
			}else{
				sum= isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x-1, y-1))+isBlack(image.getRGB(x, y-1))+
						isBlack(image.getRGB(x+1, y-1))+isBlack(image.getRGB(x+1, y));
			}
		}else{
		//其他行
			//如果是第一列
			if(x==0){
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x, y-1))+isBlack(image.getRGB(x+1, y-1))+isBlack(image.getRGB(x+1, y))+
						isBlack(image.getRGB(x+1, y+1))+isBlack(image.getRGB(x, y+1));
			}else if(x==width-1){
			//如果是最后一列	
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x, y-1))+isBlack(image.getRGB(x-1, y-1))+isBlack(image.getRGB(x-1, y))+
						isBlack(image.getRGB(x-1, y+1))+isBlack(image.getRGB(x, y+1));
			}else{
				sum= isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x-1, y-1))+isBlack(image.getRGB(x, y-1))+
						isBlack(image.getRGB(x+1, y-1))+isBlack(image.getRGB(x+1, y))+isBlack(image.getRGB(x+1, y+1))+isBlack(image.getRGB(x, y+1))+
						isBlack(image.getRGB(x-1, y+1));
			}
		}
		if(sum>3)
			return 0;
		return 1;
	}
	
	public static void main(String[] args) throws IOException {
		String fileName = "F:\\work\\workspace_ssd\\ai\\CheckPicture_Identify\\image\\kaptcha100.jpg";
		BufferedImage img = readImageFromFile(fileName);
		
		//将图片转为黑白点阵图
		imageToBinaryMode(img);
		
		//降噪
		removeBackGround(img);
		
		ImageIO.write(img, "jpeg", new File("F:\\work\\workspace_ssd\\ai\\CheckPicture_Identify\\image\\kaptcha101.jpg"));
	}

}
