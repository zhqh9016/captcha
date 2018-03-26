package com.ai.img.preprocessor.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ai.img.preprocessor.ImagePreProcessor;

/**
 * �ṩͼƬ�ڰ׻������봦����.
 * @author zhangqh27
 *
 */
public class NineAreaPreProcessor implements ImagePreProcessor {
	
	//�ж�һ�������ǲ��ǰ�ɫ�ķ�ֵ
	public static int IS_WHITE_RGB_VALUE = 550;
	
	/**
	 * ��ͼƬ���кڰ׻������봦��
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public BufferedImage preProcess(String fileName) throws IOException{
		
		BufferedImage img = readImageFromFile(fileName);
		
		//��ͼƬתΪ�ڰ׵���ͼ
		imageToBinaryMode(img);
		
		//ִ�����ν���
		removeBackGround(img);
		removeBackGround(img);
		removeBackGround(img);
		
		return img;
	}

	/**
	 * ȥ�����
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
	 * ���ļ��ж�ȡͼƬ�ļ�
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
	 * ���R/G/B����ֵ�ĺͳ���һ����ֵ�ģ�����Ϊ�ǰ�ɫ������Ϊ��ɫ��
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
	 * ͨ������Ź���Χ���ھӵĸ������ж��ǲ��ǹ�����
	 * @param x ������
	 * @param y ������
	 * @return
	 */
	public static int check_background_by_nine_area(int x ,int y,BufferedImage image){
		
		//�����ǰ��Ϊ����ɫ,ֱ�ӷ���
		if(isWhite(image.getRGB(x, y))==1){
			return 1;
		}
		int height = image.getHeight();
		int width = image.getWidth();
		//������ʱ��žŹ��񱳾�ɫ���صĸ���
		int sum = 0;
		
		//����ǵ�һ��
		if(y==0){
			//����ǵ�һ��
			if(x==0){
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x+1, y))+isBlack(image.getRGB(x, y+1))+isBlack(image.getRGB(x+1, y+1));
				
			}else if(x==width-1){
			//��������һ��	
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x, y+1))+isBlack(image.getRGB(x-1, y+1));
			}else{
				sum= isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x, y+1))+isBlack(image.getRGB(x-1, y+1))+
						isBlack(image.getRGB(x+1, y))+isBlack(image.getRGB(x+1, y+1));
			}
		}else if(y == height-1){
		//��������һ��
			//����ǵ�һ��
			if(x==0){
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x+1, y))+isBlack(image.getRGB(x, y-1))+isBlack(image.getRGB(x+1, y-1));
			}else if(x==width-1){
			//��������һ��	
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x, y-1))+isBlack(image.getRGB(x-1, y-1));
			}else{
				sum= isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x-1, y))+isBlack(image.getRGB(x-1, y-1))+isBlack(image.getRGB(x, y-1))+
						isBlack(image.getRGB(x+1, y-1))+isBlack(image.getRGB(x+1, y));
			}
		}else{
		//������
			//����ǵ�һ��
			if(x==0){
				sum = isBlack(image.getRGB(x, y))+isBlack(image.getRGB(x, y-1))+isBlack(image.getRGB(x+1, y-1))+isBlack(image.getRGB(x+1, y))+
						isBlack(image.getRGB(x+1, y+1))+isBlack(image.getRGB(x, y+1));
			}else if(x==width-1){
			//��������һ��	
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
		
		//��ͼƬתΪ�ڰ׵���ͼ
		imageToBinaryMode(img);
		
		//����
		removeBackGround(img);
		
		ImageIO.write(img, "jpeg", new File("F:\\work\\workspace_ssd\\ai\\CheckPicture_Identify\\image\\kaptcha101.jpg"));
	}

}
