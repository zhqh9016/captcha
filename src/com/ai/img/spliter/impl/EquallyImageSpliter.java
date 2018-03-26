package com.ai.img.spliter.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 采取按照字符所占宽度相同的策略切分字符
 * @author zhangqh27
 *
 */
public class EquallyImageSpliter extends ImageSpliterAdapter {
	
	public  List<BufferedImage> splitInHorizontal(BufferedImage image) throws IOException {
		
		//确定图片的左右空白边界
		List<Integer> findBound = findBound(image);
		//对图片进行切割
		if(findBound.size()!=2){
			return null;
		}
		List<BufferedImage> images = processSplite(image,findBound);
		
		return images;
		
	}
	
	private  List<BufferedImage> processSplite(BufferedImage image, List<Integer> findBounds) {
		List<BufferedImage> result = new ArrayList<>();
		int size = findBounds.size();
		if(size!=2){
			return null;
		}
		Integer leftBound = findBounds.get(0);
		Integer rightBound = findBounds.get(1);
		
		//每一个字母所占的大小
		Integer cellSize = (rightBound-leftBound)/4;
		
		BufferedImage subimage1 = image.getSubimage(leftBound, 0, cellSize, image.getHeight());
		result.add(subimage1);
		BufferedImage subimage2 = image.getSubimage(leftBound+cellSize, 0, cellSize, image.getHeight());
		result.add(subimage2);
		BufferedImage subimage3 = image.getSubimage(leftBound+cellSize*2, 0, cellSize, image.getHeight());
		result.add(subimage3);
		BufferedImage subimage4 = image.getSubimage(leftBound+cellSize*3, 0, cellSize, image.getHeight());
		result.add(subimage4);
		
		return result;
	}

	public  List<Integer> findBound(BufferedImage image){
		
		List<Integer> bound = new ArrayList<Integer>();
		
		int height = image.getHeight();
		int width = image.getWidth();
		//先从左往右遍历,遇到有黑块即停止循环,,作为左边界
		label1: for(int i =0;i<width;i++){
			for(int j =0;j<height;j++){
				if(image.getRGB(i, j)==Color.BLACK.getRGB()){
					bound.add(i);
					break label1;
				}
			}
		}
		//从右往左循环,遇到有黑块即停止循环,作为右边界
		label2: for(int i = width-1;i>=0;i--){
			for(int j = 0;j<height;j++){
				if(image.getRGB(i, j)==Color.BLACK.getRGB()){
					bound.add(i);
					break label2;
				}
			}
		}
		return bound;
	}
	

	
}
