package com.ai.img.spliter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface ImageSpliter {

	/**
	 * ��ͼ����������Ŀհײ���ȥ��,����ͼƬ�����ҷ�Ϊ������
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public  List<BufferedImage> splitInHorizontal(BufferedImage image) throws IOException;
	
	/**
	 * ȥ��ͼ����������Ŀհײ���
	 * @param fregments
	 * @throws IOException
	 */
	public  void splitInVertical(List<BufferedImage> fregments) throws IOException;
	
	boolean isBlack(int rgb);
}
