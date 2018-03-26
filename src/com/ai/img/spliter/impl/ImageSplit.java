package com.ai.img.spliter.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

/**
 * 以空白竖线条为分界线切分字符
 * @author zhangqh27
 *
 */
public class ImageSplit {
	
	public static void main(String[] args) throws IOException {
		
		List<BufferedImage> spliteImage = spliteImage("F:\\work\\workspace_ssd\\ai\\CheckPicture_Identify\\image\\1234.png");
		int i = 1;
		for (BufferedImage bufferedImage : spliteImage) {
			ImageIO.write(bufferedImage, "jpeg", new File("F:\\work\\workspace_ssd\\ai\\CheckPicture_Identify\\image\\"+"fragment_"+i+".jpeg"));
			i++;
		}
		
	}
	
	public static List<BufferedImage> spliteImage(String fileName) throws IOException{
		
		List<BufferedImage> result = new ArrayList<>();
		BufferedImage image = ImageIO.read(new File(fileName));
		int height = image.getHeight();
		int width = image.getWidth();
		
		int start = 0;
		int end = 0;
		List<Integer> blacks = new ArrayList<>(width);
		for(int i =0;i<width;i++){
			//遍历每一列，记录黑色像素的个数
			int count =0 ;
			for(int j =0;j<height;j++){
				if(isBlack(image.getRGB(i, j))){
					count++;
				}
			}
			blacks.add(count);
		}
		
		Map<Integer, Integer> fragments = new LinkedHashMap<>();
		for(int m =0;m<blacks.size()-1;m++){
			if(blacks.get(m)==0&&blacks.get(m+1)>0){
				start=m+1;
			}
			if(blacks.get(m)>0&&blacks.get(m+1)==0){
				end = m;
				fragments.put(start, end);
			}
		}
		
		for (Entry<Integer, Integer> entry : fragments.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			BufferedImage subimage = image.getSubimage(key, 0, value-key, height);
			result.add(subimage);
		}
		
		
		return result;
	}

	private static boolean isBlack(int rgb) {
		Color color = new Color(rgb);
		if(color.getRed()+color.getGreen()+color.getBlue()<200){
			return true;
		}
		return false;
	}

}
