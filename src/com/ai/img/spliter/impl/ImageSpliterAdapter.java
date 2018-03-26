package com.ai.img.spliter.impl;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import com.ai.img.spliter.ImageSpliter;

public abstract class ImageSpliterAdapter implements ImageSpliter {

	@Override
	public boolean isBlack(int rgb) {
		Color color = new Color(rgb);
		if(color.getRed()+color.getGreen()+color.getBlue()<200){
			return true;
		}
		return false;
	}
	
	@Override
	public  void splitInVertical(List<BufferedImage> fregments) throws IOException{
		
		if(fregments != null){
			
			for(int i =0;i<fregments.size();i++){
				
				BufferedImage image = fregments.get(i);
				
				if (image == null) {
					throw new RuntimeException("ȥ����������׿�������Ҫ����һ���ǿյ�ͼ�����");
				}

				int upSide = 0;
				int downSide = 0;

				int height = image.getHeight();
				int width = image.getWidth();
				// �ȴ������ұ���,�����кڿ鼴ֹͣѭ��,,��Ϊ��߽�
				label1: for (int m = 0; m < height; m++) {
					for (int j = 0; j < width; j++) {
						if (image.getRGB(j, m) == Color.BLACK.getRGB()) {
							upSide = m;
							break label1;
						}
					}
				}
				// ��������ѭ��,�����кڿ鼴ֹͣѭ��,��Ϊ�ұ߽�
				label2: for (int m = height - 1; m >= 0; m--) {
					for (int j = 0; j < width; j++) {
						if (image.getRGB(j, m) == Color.BLACK.getRGB()) {
							downSide = m;
							break label2;
						}
					}
				}
				fregments.set(i, image.getSubimage(0, upSide, image.getWidth(), downSide - upSide+1));
			}
		}
	}
	
}
