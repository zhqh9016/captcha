package com.ai.img.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.ai.img.normalize.ImageNormalize;
import com.ai.img.preprocessor.ImagePreProcessor;
import com.ai.img.preprocessor.impl.NineAreaPreProcessor;
import com.ai.img.spliter.ImageSpliter;
import com.ai.img.spliter.impl.DistributeImageSpliter;
import com.ai.img.spliter.impl.EquallyImageSpliter;

public class ImageIdentify {
	
	@Test
	public void fun1() throws IOException{
		String fileName = "image//500//500.jpg";
		//ͼƬԤ����
		NineAreaPreProcessor preProcessor = new NineAreaPreProcessor();
		BufferedImage preProcess2 = preProcessor.preProcess(fileName);
		//ͼƬ�ָ�
		EquallyImageSpliter spliter = new EquallyImageSpliter();
		List<BufferedImage> split = spliter.splitInHorizontal(preProcess2);
		//ͼƬ����
		for(int i = 0;i<split.size();){
			BufferedImage bufferedImage = split.get(i);
			i++;
			ImageIO.write(bufferedImage, "jpeg", new FileOutputStream("image//500//50"+i+".jpg"));
		}
		
	}
	
	@Test
	public void fun2() throws IOException{
		File train_dir = new File("E:/work/workspace/captcha/sourcePicture");

		deepReadFiles(train_dir);
	}
	
	/**
	 * �ݹ鴦���ļ�
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void deepReadFiles(File file) throws IOException {

		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles!=null&&listFiles.length>0){
				for (File file2 : listFiles) {
					deepReadFiles(file2);
				}
			}
		} else {
			doSomething(file);
		}
	}
	
	private void doSomething(File file) throws IOException {
		String fileName = file.getAbsolutePath();
		System.out.println(fileName);
		String outputDir = "E:/work/workspace/captcha/modelPicture/";
		splitPicture(fileName, outputDir);
	}

	private void splitPicture(String fileName, String outputDir) throws IOException, FileNotFoundException {
		//ͼƬԤ����
		ImagePreProcessor preProcessor = new NineAreaPreProcessor();
		BufferedImage preProcess2 = preProcessor.preProcess(fileName);
		//ͼƬ����ָ�horizontal
		ImageSpliter spliter = new DistributeImageSpliter();
		List<BufferedImage> split = spliter.splitInHorizontal(preProcess2);
		//ͼƬ�����и�vertical
		spliter.splitInVertical(split);
		//ͼ���һ������
		for(int i =0;i<split.size();i++){
			BufferedImage bufferedImage = split.get(i);
			BufferedImage normalize = ImageNormalize.normalize(bufferedImage);
			split.set(i, normalize);
		}
		
		//ͼƬ����
		for(int i = 0;i<split.size();){
			BufferedImage bufferedImage = split.get(i);
			i++;
			ImageIO.write(bufferedImage, "jpg", new FileOutputStream(outputDir+UUID.randomUUID().toString()+".jpg"));
		}
	}
	
	@Test
	public void fun3() throws IOException{
		int m = 2;
		String fileName = "image//"+m+"00//"+m+"00.jpg";
		//ͼƬԤ����
		ImagePreProcessor preProcessor = new NineAreaPreProcessor();
		BufferedImage preProcess2 = preProcessor.preProcess(fileName);
		ImageIO.write(preProcess2, "jpg", new FileOutputStream("image//"+m+"00//"+m+"0000"+m+".jpg"));
	}

}
