package com.ai.img.spliter.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * ��ȡ�����ַ���ռ�����ͬ�Ĳ����з��ַ�
 * @author zhangqh27
 *
 */
public class EquallyImageSpliter extends ImageSpliterAdapter {
	
	public  List<BufferedImage> splitInHorizontal(BufferedImage image) throws IOException {
		
		//ȷ��ͼƬ�����ҿհױ߽�
		List<Integer> findBound = findBound(image);
		//��ͼƬ�����и�
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
		
		//ÿһ����ĸ��ռ�Ĵ�С
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
		//�ȴ������ұ���,�����кڿ鼴ֹͣѭ��,,��Ϊ��߽�
		label1: for(int i =0;i<width;i++){
			for(int j =0;j<height;j++){
				if(image.getRGB(i, j)==Color.BLACK.getRGB()){
					bound.add(i);
					break label1;
				}
			}
		}
		//��������ѭ��,�����кڿ鼴ֹͣѭ��,��Ϊ�ұ߽�
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
