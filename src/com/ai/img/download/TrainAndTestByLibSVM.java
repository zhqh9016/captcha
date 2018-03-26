package com.ai.img.download;

import java.io.IOException;

import com.ai.img.svm.SvmPredict;
import com.ai.img.svm.SvmTrain;

public class TrainAndTestByLibSVM {  
    //�������ú�����LibSVM�����ʽ��ѵ���ı�  
    public String[] str_trained = {"-g","2.0","-c","32","-t","2","-m","500.0","-h","0","E:\\test\\train\\IF_IDF\\allTrainVSM.txt"};   
    private String str_model = "E:\\test\\train\\IF_IDF\\allTrainVSM.txt.model";    //ѵ����õ���ģ���ļ�  
    private String testTxt = "E:\\test\\test\\IF_IDF\\allTestVSM.txt";  
    //�����ļ���ģ���ļ�������ļ�·��  
    private String[] str_result = {testTxt, str_model, "E:\\test\\Res.txt"};    
    private static TrainAndTestByLibSVM libSVM = null;  
      
    /* 
     * ˽�л����캯������ѵ�����������õ�����ģ�� 
     */  
    private TrainAndTestByLibSVM(){  
          
    }  
      
    public static TrainAndTestByLibSVM getInstance(){  
        if(libSVM==null)  
            libSVM = new TrainAndTestByLibSVM();
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
        TrainAndTestByLibSVM tat = TrainAndTestByLibSVM.getInstance();  
        System.out.println("����ѵ������ģ�͡�������");  
        tat.trainByLibSVM();  
        System.out.println("����Ӧ�÷���ģ�ͽ��з��ࡣ������");  
        tat.tellByLibSVM();  
    }  
} 
