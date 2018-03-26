package com.ai.img.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.ai.img.preprocessor.impl.NineAreaPreProcessor;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

public class CommonTest {
	
	@Test
	public void fun1(){
		
		File file = new File("E:/work/workspace/swagger/swagger-yaml-client/swagger.yaml");
//		String parent = file.getParent();
//		System.out.println(parent);
		File parentFile = file.getParentFile();
		String name = parentFile.getName();
		System.out.println(name);
		System.out.println(file.getAbsolutePath());
		
	}
	
	@Test
	public void fun2(){
		
		char c = 'z';
		System.out.println((int)c+"");
	}

	@Test
	public void fun3(){
		System.out.println((char)112);
	}
	
	@Test
    public void predictByLibSVM() throws IOException{
//    	File testFile = new File("E:/work/workspace/captcha/test/b_and_d_test/modelPicture/b/0b8d9fb2-8f0c-40d1-a83c-6a4fe6eb1c33.jpg");
    	File testFile = new File("E:/work/workspace/captcha/test/model/d (7).jpg");
    	List<svm_node> svmNodes = new ArrayList<svm_node>();
    	
    	BufferedImage read = ImageIO.read(testFile);
    	for(int i =0;i<read.getHeight();i++){
    		int count = 0;
    		for(int j =0;j<read.getWidth();j++){
    			int rgb = read.getRGB(j, i);
    			Color color = new Color(rgb);
    			if(color.getRed()+color.getGreen()+color.getBlue() < NineAreaPreProcessor.IS_WHITE_RGB_VALUE){
    				count++;
    			}
    		}
    		svm_node node = new svm_node();
    		node.index=i;
    		node.value=count;
    		svmNodes.add(node);
    	}
    	
    	for(int i = 0;i<read.getWidth();i++){
    		int count = 0;
    		for(int j = 0;j<read.getHeight();j++){
    			int rgb = read.getRGB(i, j);
    			Color color = new Color(rgb);
    			if(color.getRed()+color.getGreen()+color.getBlue()<NineAreaPreProcessor.IS_WHITE_RGB_VALUE){
    				count++;
    			}
    		}
    		svm_node node = new svm_node();
    		node.index=read.getHeight()+i;
    		node.value=count;
    		svmNodes.add(node);
    	}
    	svm_node[] array = svmNodes.toArray(new svm_node[svmNodes.size()]);
    	
    	String fp = "E:/work/workspace/captcha/test/b_and_d_test/trainedModel/trainSVM.txt.model";
		svm_model model = svm.svm_load_model(fp );
		double svm_predict = svm.svm_predict(model , array);
		
		System.out.println("Ô¤²â½á¹ûÎª:"+(char)svm_predict);
    	
    }
}
