package com.ai.img.test;

import java.io.IOException;
import com.ai.img.svm.SvmPredict;
import com.ai.img.svm.SvmTrain;

public class MySVMTest {
	
	//b and d 测试训练数据存放地址
	String train_model_address = "E:/work/workspace/captcha/test/b_and_d_test/trainedModel/trainSVM.txt";
	
	//参数设置和满足LibSVM输入格式的训练文本  
    public String[] str_trained = {"-g","2.0","-c","32","-t","2","-m","500.0","-h","0",train_model_address};   
    
    //b and d 模型数据存放地址
    private String str_model = train_model_address+".model";    //训练后得到的模型文件  
   
    //b and d 测试数据存放地址
    private String testTxt = "E:/work/workspace/captcha/test/b_and_d_test/trainedModel/testSVM.txt";
    
    //b and d 测试结果存放地址
    private String resTxt = "E:/work/workspace/captcha/test/b_and_d_test/testResult/res.txt";
    
    
    //测试文件、模型文件、结果文件路径  
    private String[] str_result = {testTxt, str_model, resTxt}; 
    
    private static MySVMTest libSVM = null;

    /* 
     * 私有化构造函数，并训练分类器，得到分类模型 
     */  
    private MySVMTest(){  
          
    }  
      
    public static MySVMTest getInstance(){  
        if(libSVM==null)  
            libSVM = new MySVMTest();  
        return libSVM;  
    }  
    
    /* 
     * 训练分类模型 
     */  
    public void trainByLibSVM(){  
        try {  
            //训练返回的是模型文件，其实是一个路径，可以看出要求改SvmTrain.java  
            str_model = SvmTrain.main(str_trained); 
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    /* 
     * 预测分类,并返回准确率 
     */  
    public double tellByLibSVM(){  
        double accuracy=0;   
        try {  
            //测试返回的是准确率，可以看出要求改svm_predict.java  
            accuracy = SvmPredict.main(str_result); 
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return accuracy;  
    }  
  
    public static void main(String[] args){  
    	MySVMTest tat = MySVMTest.getInstance();  
        System.out.println("正在训练分类模型。。。。");  
        tat.trainByLibSVM();  
//        System.out.println("正在应用分类模型进行分类。。。。");  
//        tat.tellByLibSVM();  
    }  
}
