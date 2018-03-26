package com.ai.img.test;

import java.io.IOException;
import com.ai.img.svm.SvmPredict;
import com.ai.img.svm.SvmTrain;

public class MySVMTest {
	
	//b and d ����ѵ�����ݴ�ŵ�ַ
	String train_model_address = "E:/work/workspace/captcha/test/b_and_d_test/trainedModel/trainSVM.txt";
	
	//�������ú�����LibSVM�����ʽ��ѵ���ı�  
    public String[] str_trained = {"-g","2.0","-c","32","-t","2","-m","500.0","-h","0",train_model_address};   
    
    //b and d ģ�����ݴ�ŵ�ַ
    private String str_model = train_model_address+".model";    //ѵ����õ���ģ���ļ�  
   
    //b and d �������ݴ�ŵ�ַ
    private String testTxt = "E:/work/workspace/captcha/test/b_and_d_test/trainedModel/testSVM.txt";
    
    //b and d ���Խ����ŵ�ַ
    private String resTxt = "E:/work/workspace/captcha/test/b_and_d_test/testResult/res.txt";
    
    
    //�����ļ���ģ���ļ�������ļ�·��  
    private String[] str_result = {testTxt, str_model, resTxt}; 
    
    private static MySVMTest libSVM = null;

    /* 
     * ˽�л����캯������ѵ�����������õ�����ģ�� 
     */  
    private MySVMTest(){  
          
    }  
      
    public static MySVMTest getInstance(){  
        if(libSVM==null)  
            libSVM = new MySVMTest();  
        return libSVM;  
    }  
    
    /* 
     * ѵ������ģ�� 
     */  
    public void trainByLibSVM(){  
        try {  
            //ѵ�����ص���ģ���ļ�����ʵ��һ��·�������Կ���Ҫ���SvmTrain.java  
            str_model = SvmTrain.main(str_trained); 
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    /* 
     * Ԥ�����,������׼ȷ�� 
     */  
    public double tellByLibSVM(){  
        double accuracy=0;   
        try {  
            //���Է��ص���׼ȷ�ʣ����Կ���Ҫ���svm_predict.java  
            accuracy = SvmPredict.main(str_result); 
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return accuracy;  
    }  
  
    public static void main(String[] args){  
    	MySVMTest tat = MySVMTest.getInstance();  
        System.out.println("����ѵ������ģ�͡�������");  
        tat.trainByLibSVM();  
//        System.out.println("����Ӧ�÷���ģ�ͽ��з��ࡣ������");  
//        tat.tellByLibSVM();  
    }  
}
