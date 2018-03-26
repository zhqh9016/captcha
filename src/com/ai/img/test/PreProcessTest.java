package com.ai.img.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.ai.img.preprocessor.impl.NineAreaPreProcessor;

public class PreProcessTest {
	@Test
	public void fun1() throws IOException{
		
		String fileName = "image/100/100.jpg";
		BufferedImage image = NineAreaPreProcessor.readImageFromFile(fileName);
		//将图片转为黑白点阵图
		NineAreaPreProcessor.imageToBinaryMode(image);
		
		//执行两次降噪降噪
		for(int i=0;i<10;i++){
			NineAreaPreProcessor.removeBackGround(image);
		}
		
		int height = image.getHeight();
		int width = image.getWidth();
		for(int i =0;i<height;i++){
			for(int j=0;j<width;j++){
				if(image.getRGB(j, i)==Color.BLACK.getRGB()){
					System.out.print("8");
				}else{
					System.out.print("`");
				}
			}
			System.out.println();
		}
		
		ImageIO.write(image, "jpg", new FileOutputStream("image/100/a10.jpg"));
		
	}

}
