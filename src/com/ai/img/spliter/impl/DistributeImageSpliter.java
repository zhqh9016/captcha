package com.ai.img.spliter.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 先根据空白线条分割图像,如果分割后的图像数量少于4个, 再将最宽的一个按照竖直映射的值最小的点进行分割.
 * 
 * @author zhangqh27
 *
 */
public class DistributeImageSpliter extends ImageSpliterAdapter {

	@Override
	public List<BufferedImage> splitInHorizontal(BufferedImage image) throws IOException {

		List<BufferedImage> result = null;
		// 1.先将左右两侧的白块去掉
		BufferedImage cleanSideWhite = cleanSideWhite(image);
		// 2.先用空白竖条分割
		List<BufferedImage> spliteByWhiteStrip = spliteByWhiteStrip(cleanSideWhite);
		// 3.如果分割完的图片数量少于四个,统计各图像宽度,先将最宽的片段采用数值投影的方式进行分割,
		if (spliteByWhiteStrip.size() < 4) {
			result = spliteByReflect(spliteByWhiteStrip, 0);
		} else {
			result = spliteByWhiteStrip;
		}

		return result;
	}

	/**
	 * 将两侧的白块去掉
	 * 
	 * @param image
	 * @return
	 */
	private BufferedImage cleanSideWhite(BufferedImage image) {
		BufferedImage result = null;
		if (image == null) {
			throw new RuntimeException("去除两侧白块请求需要传递一个非空的图像对象");
		}

		int leftSide = 0;
		int rightSide = 0;

		int height = image.getHeight();
		int width = image.getWidth();
		// 先从左往右遍历,遇到有黑块即停止循环,,作为左边界
		label1: for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (image.getRGB(i, j) == Color.BLACK.getRGB()) {
					leftSide = i;
					break label1;
				}
			}
		}
		// 从右往左循环,遇到有黑块即停止循环,作为右边界
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
	 * 通过白竖线分割图像
	 * 
	 * @param image
	 * @return
	 */
	public List<BufferedImage> spliteByWhiteStrip(BufferedImage image) {

		List<BufferedImage> result = new ArrayList<>();
		int height = image.getHeight();
		int width = image.getWidth();
		// 用于临时标记每一个片段的起始横坐标.
		int start = 0;
		int end = 0;
		// 用于标记当前遍历的区域是文字区域还是空白区域,1为文字区域,0为空白区域
		int areaFlag = 0;
		for (int i = 0; i < width; i++) {
			// 遍历每一列，记录黑色像素的个数
			int count = 0;
			for (int j = 0; j < height; j++) {
				if (isBlack(image.getRGB(i, j))) {
					count++;
				}
			}
			// 如果当前列包含黑色像素,并且areaFlag为0,说明从空白区域进入了文字区域
			if (count > 0 && areaFlag == 0) {
				areaFlag = 1;
				start = i;
			}
			// 如果当前列所有像素为白色,并且areaFlag为1,说明从文字区域进入了空白区域,或者已经扫描到了最后一列
			if ((count == 0 && areaFlag == 1) || i == width - 1) {
				areaFlag = 0;
				end = i;
				// 截取子片段,起点为start标记的位置,宽度为end-start.高度为height.
				BufferedImage subimage = image.getSubimage(start, 0, end - start, height);
				result.add(subimage);
			}
		}
		return result;
	}

	/**
	 * 根据竖直映射方图分割图像
	 * 
	 * @param imageFregments
	 * @param deep
	 * @return
	 */
	public List<BufferedImage> spliteByReflect(List<BufferedImage> imageFregments, int deep) {
		List<BufferedImage> result = new ArrayList<>();
		if (imageFregments.size() < 4) {
			// 如果片段数少于四个,找到最宽的一个,
			int index = getWidestFregmentIndex(imageFregments);
			BufferedImage widest = imageFregments.get(index);
			// 根据竖直映射方图分割法对图像进行分割.
			List<BufferedImage> splitedFregment = new ArrayList<>();
			// 统计出各列黑块的数量
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
				// 找到黑块数量最少的三列.
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
				// 在排出的三个最低点中找出离中心先最近的一个
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

			// 用分割后的片段组替换之前的片段.
			if (index != 0) {
				result.addAll(imageFregments.subList(0, index));
			}
			result.addAll(splitedFregment);
			if (index < imageFregments.size() - 1) {
				result.addAll(imageFregments.subList(index + 1, imageFregments.size()));
			}

		}
		deep++;
		// 如果当前片段小于4个并且递归深度小于4,执行递归调用.
		if (result.size() < 4 && deep < 4) {
			result = spliteByReflect(result, deep);
		}
		return result;
	}

	/**
	 * 获取图像片段组中最宽的.
	 * 
	 * @param imageFregments
	 * @return
	 */
	private int getWidestFregmentIndex(List<BufferedImage> imageFregments) {
		// 1.找到最宽的一个片段
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
