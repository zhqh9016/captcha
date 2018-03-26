package com.ai.img.normalize;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * ����ʵ��ͼ�����Ź�һ��,Ϊ���ڱȶԹ����ṩ����.
 * 
 * @author Administrator
 *
 */
public class ImageNormalize {

	// �����һ�����ͼ���С
	private static int TARGET_WIDTH = 20;
	private static int TARGET_HEIGHT = 20;
	
	public static BufferedImage normalize(BufferedImage img){
		
		//�ȵ���ԭͼ��ĳ����
		BufferedImage process_rate = process_rate(img);
		
		//�ٵ���ͼ��ĳ������ظ���.
		BufferedImage resize = resize(process_rate);
		
		return resize;
	}

	public static BufferedImage process_rate(BufferedImage img) {

		BufferedImage normalRateImg = null;

		int height = img.getHeight();
		int width = img.getWidth();
		// ����߶ȺͿ�ȵı�ֵ�ȹ�һͼ��ĸ߶ȺͿ�ȵı�ֵС,˵����ǰͼ��ȱ�׼ͼ������,Ҫ��ͼƬ����.
		if (height / width < TARGET_HEIGHT / TARGET_WIDTH) {

			int nomal_height = width * TARGET_HEIGHT / TARGET_WIDTH;
			normalRateImg = new BufferedImage(width, nomal_height, BufferedImage.TYPE_INT_RGB);

			int distance = nomal_height - height;

			// �����ͷ��Ҫ���ӵİ�ɫ������
			int increase = distance / 2;

			// ͷ��������ɫ�����ð�ɫɫ�����
			for (int i = 0; i < increase; i++) {
				for (int j = 0; j < width; j++) {
					normalRateImg.setRGB(j, i, Color.WHITE.getRGB());
				}
			}
			// ��ͼƬ���ƹ���
			for (int i = increase; i < increase + height; i++) {
				for (int j = 0; j < width; j++) {
					normalRateImg.setRGB(j, i, img.getRGB(j, i - increase));
				}
			}
			// �ײ�������ɫ�����ð�ɫɫ�����
			for (int i = increase + height; i < nomal_height; i++) {
				for (int j = 0; j < width; j++) {
					normalRateImg.setRGB(j, i, Color.WHITE.getRGB());
				}
			}

		} else if (height / width > TARGET_HEIGHT / TARGET_HEIGHT) {
			// ����߶ȺͿ�ȵı�ֵ�ȹ�һͼ��ĸ߶ȺͿ�ȵı�ֵ��,˵����ǰͼ��ȱ�׼ͼ��C��,Ҫ��ͼƬ����.
			int nomal_width = height * TARGET_WIDTH / TARGET_WIDTH;
			normalRateImg = new BufferedImage(nomal_width, height, BufferedImage.TYPE_INT_RGB);
			int distance = nomal_width - width;
			// ��������Ҫ���ӵ�ɫ������
			int increase = distance / 2;

			// ���������ɫ�����ð�ɫɫ�����
			for (int i = 0; i < increase; i++) {
				for (int j = 0; j < height; j++) {
					normalRateImg.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
			// ��ͼƬ���ƹ���
			for (int i = increase; i < increase + width; i++) {
				for (int j = 0; j < height; j++) {
					normalRateImg.setRGB(i, j, img.getRGB(i-increase, j));
				}
			}
			// �ײ�������ɫ�����ð�ɫɫ�����
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
			// �������ǰ������ص���������ԭͼ���е�ӳ��������.
			int mappingHeight = i * sourceHeight / TARGET_HEIGHT;
			if (mappingHeight > sourceHeight)
				mappingHeight = sourceHeight;

			for (int j = 0; j < TARGET_WIDTH; j++) {
				// �������ǰ������صĺ�������ԭͼ���е�������.
				int mappingWidth = j * sourceWidth / TARGET_WIDTH;
				if (mappingWidth > sourceWidth)
					mappingWidth = sourceWidth;
				targeImage.setRGB(j, i, sourceImg.getRGB(mappingWidth, mappingHeight));
			}
		}

		return targeImage;
	}

}
