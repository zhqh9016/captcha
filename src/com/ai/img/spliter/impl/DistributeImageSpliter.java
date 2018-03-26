package com.ai.img.spliter.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * �ȸ��ݿհ������ָ�ͼ��,����ָ���ͼ����������4��, �ٽ�����һ��������ֱӳ���ֵ��С�ĵ���зָ�.
 * 
 * @author zhangqh27
 *
 */
public class DistributeImageSpliter extends ImageSpliterAdapter {

	@Override
	public List<BufferedImage> splitInHorizontal(BufferedImage image) throws IOException {

		List<BufferedImage> result = null;
		// 1.�Ƚ���������İ׿�ȥ��
		BufferedImage cleanSideWhite = cleanSideWhite(image);
		// 2.���ÿհ������ָ�
		List<BufferedImage> spliteByWhiteStrip = spliteByWhiteStrip(cleanSideWhite);
		// 3.����ָ����ͼƬ���������ĸ�,ͳ�Ƹ�ͼ����,�Ƚ�����Ƭ�β�����ֵͶӰ�ķ�ʽ���зָ�,
		if (spliteByWhiteStrip.size() < 4) {
			result = spliteByReflect(spliteByWhiteStrip, 0);
		} else {
			result = spliteByWhiteStrip;
		}

		return result;
	}

	/**
	 * ������İ׿�ȥ��
	 * 
	 * @param image
	 * @return
	 */
	private BufferedImage cleanSideWhite(BufferedImage image) {
		BufferedImage result = null;
		if (image == null) {
			throw new RuntimeException("ȥ������׿�������Ҫ����һ���ǿյ�ͼ�����");
		}

		int leftSide = 0;
		int rightSide = 0;

		int height = image.getHeight();
		int width = image.getWidth();
		// �ȴ������ұ���,�����кڿ鼴ֹͣѭ��,,��Ϊ��߽�
		label1: for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (image.getRGB(i, j) == Color.BLACK.getRGB()) {
					leftSide = i;
					break label1;
				}
			}
		}
		// ��������ѭ��,�����кڿ鼴ֹͣѭ��,��Ϊ�ұ߽�
		label2: for (int i = width - 1; i >= 0; i--) {
			for (int j = 0; j < height; j++) {
				if (image.getRGB(i, j) == Color.BLACK.getRGB()) {
					rightSide = i;
					break label2;
				}
			}
		}
		if (rightSide + 2 > width) {
			result = image.getSubimage(leftSide, 0, width - leftSide, image.getHeight());
		} else {
			result = image.getSubimage(leftSide, 0, rightSide - leftSide + 2, image.getHeight());
		}

		return result;
	}

	/**
	 * ͨ�������߷ָ�ͼ��
	 * 
	 * @param image
	 * @return
	 */
	public List<BufferedImage> spliteByWhiteStrip(BufferedImage image) {

		List<BufferedImage> result = new ArrayList<>();
		int height = image.getHeight();
		int width = image.getWidth();
		// ������ʱ���ÿһ��Ƭ�ε���ʼ������.
		int start = 0;
		int end = 0;
		// ���ڱ�ǵ�ǰ���������������������ǿհ�����,1Ϊ��������,0Ϊ�հ�����
		int areaFlag = 0;
		for (int i = 0; i < width; i++) {
			// ����ÿһ�У���¼��ɫ���صĸ���
			int count = 0;
			for (int j = 0; j < height; j++) {
				if (isBlack(image.getRGB(i, j))) {
					count++;
				}
			}
			// �����ǰ�а�����ɫ����,����areaFlagΪ0,˵���ӿհ������������������
			if (count > 0 && areaFlag == 0) {
				areaFlag = 1;
				start = i;
			}
			// �����ǰ����������Ϊ��ɫ,����areaFlagΪ1,˵����������������˿հ�����,�����Ѿ�ɨ�赽�����һ��
			if ((count == 0 && areaFlag == 1) || i == width - 1) {
				areaFlag = 0;
				end = i;
				// ��ȡ��Ƭ��,���Ϊstart��ǵ�λ��,���Ϊend-start.�߶�Ϊheight.
				BufferedImage subimage = image.getSubimage(start, 0, end - start, height);
				result.add(subimage);
			}
		}
		return result;
	}

	/**
	 * ������ֱӳ�䷽ͼ�ָ�ͼ��
	 * 
	 * @param imageFregments
	 * @param deep
	 * @return
	 */
	public List<BufferedImage> spliteByReflect(List<BufferedImage> imageFregments, int deep) {
		List<BufferedImage> result = new ArrayList<>();
		if (imageFregments.size() < 4) {
			// ���Ƭ���������ĸ�,�ҵ�����һ��,
			int index = getWidestFregmentIndex(imageFregments);
			BufferedImage widest = imageFregments.get(index);
			// ������ֱӳ�䷽ͼ�ָ��ͼ����зָ�.
			List<BufferedImage> splitedFregment = new ArrayList<>();
			// ͳ�Ƴ����кڿ������
			int width = widest.getWidth();
			int height = widest.getHeight();
			if (width >= 3) {
				int[][] countArr = new int[widest.getWidth()][2];
				for (int i = 0; i < width; i++) {
					int count = 0;
					for (int j = 0; j < height; j++) {
						if (isBlack(widest.getRGB(i, j))) {
							count++;
						}
					}
					countArr[i][0] = i;
					countArr[i][1] = count;
				}
				// �ҵ��ڿ��������ٵ�����.
				int[] temp = new int[2];
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < width - i - 1; j++) {
						if (countArr[j][1] < countArr[j + 1][1]) {
							temp[0] = countArr[j][0];
							temp[1] = countArr[j][1];
							countArr[j][0] = countArr[j + 1][0];
							countArr[j][1] = countArr[j + 1][1];
							countArr[j + 1][0] = temp[0];
							countArr[j + 1][1] = temp[1];
						}
					}
				}
				// ���ų���������͵����ҳ��������������һ��
				int index1 = countArr[width - 1][0];
				int middleIndex = width / 2;
				for (int i = 0; i < 3; i++) {
					if (Math.abs(middleIndex - countArr[width - i - 1][0]) < Math.abs(middleIndex - index1)) {
						index1 = countArr[width - i - 1][0];
					}
				}

				splitedFregment.add(widest.getSubimage(0, 0, index1, height));
				splitedFregment.add(widest.getSubimage(index1, 0, width - index1, height));
			}

			// �÷ָ���Ƭ�����滻֮ǰ��Ƭ��.
			if (index != 0) {
				result.addAll(imageFregments.subList(0, index));
			}
			result.addAll(splitedFregment);
			if (index < imageFregments.size() - 1) {
				result.addAll(imageFregments.subList(index + 1, imageFregments.size()));
			}

		}
		deep++;
		// �����ǰƬ��С��4�����ҵݹ����С��4,ִ�еݹ����.
		if (result.size() < 4 && deep < 4) {
			result = spliteByReflect(result, deep);
		}
		return result;
	}

	/**
	 * ��ȡͼ��Ƭ����������.
	 * 
	 * @param imageFregments
	 * @return
	 */
	private int getWidestFregmentIndex(List<BufferedImage> imageFregments) {
		// 1.�ҵ�����һ��Ƭ��
		int width = 0;
		int index = 0;
		for (int i = 0; i < imageFregments.size(); i++) {
			if (imageFregments.get(i).getWidth() > width) {
				width = imageFregments.get(i).getWidth();
				index = i;
			}
		}
		return index;
	}
}
