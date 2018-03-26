package com.ai.img.preprocessor;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImagePreProcessor {
	
	public BufferedImage preProcess(String fileName) throws IOException;

}
