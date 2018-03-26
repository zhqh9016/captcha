package com.ai.img.svm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.ai.img.preprocessor.impl.NineAreaPreProcessor;

public class TrainSVMCreator {

	@Test
	public void fun1() throws IOException {

		// 测试数据集文件夹
		// File train_dir = new  File("E:\\work\\workspace\\captcha\\test\\model");
		// 建模数据集文件夹
		// File train_dir = new  File("E:\\work\\workspace\\captcha\\modelPicture");

		// b和d建模数据集文件夹
		File train_dir = new File("E:/work/workspace/captcha/test/b_and_d_test/modelPicture");

		deepReadFiles(train_dir);

	}

	/**
	 * 递归处理文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void deepReadFiles(File file) throws IOException {

		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file2 : listFiles) {
					deepReadFiles(file2);
				}
			}
		} else {
			doSomething(file);
		}
	}

	/**
	 * 处理文件的逻辑
	 * 
	 * @throws IOException
	 */
	private void doSomething(File file) throws IOException {

		BufferedImage image = ImageIO.read(file);

		File output = new File("E:/work/workspace/captcha/test/b_and_d_test/trainedModel/trainSVM.txt");

		FileWriter fileWriter = new FileWriter(output, true);

		// 确认当前样本的标签
		File parent = file.getParentFile();
		char label = parent.getName().charAt(0);
		// char label = file.getName().charAt(0);
//		String label = file.getName();

		// 拼接样本行
		StringBuffer strBuffer = buildTrainLine((int)label + "", image);

		fileWriter.write(strBuffer.toString());

		fileWriter.close();
	}

	private StringBuffer buildTrainLine(String label, BufferedImage image) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(label);

		// 统计每一横条中黑块个数
		for (int i = 0; i < image.getHeight(); i++) {
			int count = countLineHorizontal(image, i);
			strBuffer.append(" ").append(i).append(":").append(count);
		}

		// 统计每一竖条中黑块个数,并写到训练文件中
		for (int i = 0; i < image.getWidth(); i++) {
			int count = countLineVertical(image, i);
			strBuffer.append(" ").append(i + image.getHeight()).append(":").append(count);
		}
		///////////////////////////////
		File file = new File("E:/work/workspace/captcha/test/b_and_d_test/trainedModel/ppp.txt");
		Writer writer = null;
			try {
				writer = new FileWriter(file,true);
				writer.write("\n"+label+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		///////////////////////////////

		strBuffer.append("\n");
		return strBuffer;
	}

	/**
	 * 统计每一竖条中黑块的个数
	 * 
	 * @param image
	 * @param i
	 * @return
	 */
	private int countLineVertical(BufferedImage image, int i) {
		int count = 0;

		for (int j = 0; j < image.getHeight(); j++) {
			int rgb = image.getRGB(i, j);
			Color color = new Color(rgb);
			if (color.getRed()+color.getGreen()+color.getBlue()<NineAreaPreProcessor.IS_WHITE_RGB_VALUE) {
				count++;
			}
		}

		return count;
	}

	/**
	 * 统计每一横条中黑块的个数
	 * 
	 * @param image
	 * @param i
	 * @return
	 * @throws IOException 
	 */
	private int countLineHorizontal(BufferedImage image, int i) {
		int count = 0;
		File file = new File("E:/work/workspace/captcha/test/b_and_d_test/trainedModel/ppp.txt");
		Writer writer = null;
		try {
			writer = new FileWriter(file,true);
			for (int j = 0; j < image.getWidth(); j++) {
				int rgb = image.getRGB(j, i);
				Color color = new Color(rgb);
				if (color.getRed()+color.getGreen()+color.getBlue()<NineAreaPreProcessor.IS_WHITE_RGB_VALUE) {
					count++;
					writer.write("1");
				}else{
					writer.write("0");
				}
			}
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return count;
	}

}
