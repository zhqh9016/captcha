package com.ai.img.tools;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class ImageDownload {

	public static void main(String[] args) {
		try {
			for(int i=0;i<1000;i++){
				Thread.sleep(100l);
				downloadImage3();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void downloadImage() {
		HttpURLConnection urlConnection = null;
		for (int i = 0; i < 30; i++) {
			try {
				URL url = new URL("https://59.173.248.30:7013/include1/kaptcha.jsp?r=0.35200907982444873");
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				int statusCode = urlConnection.getResponseCode();
				// ִ��getMethod
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + urlConnection.getContentEncoding());
				}
				// ��ȡ����
				String picName = "img2//" + i + ".jpg";
				InputStream inputStream = urlConnection.getInputStream();
				OutputStream outStream = new FileOutputStream(picName);
				byte[] b = new byte[1024];
				int read = 0;
				while ((read = inputStream.read(b)) > 0) {
					outStream.write(b, 0, read);
				}
				outStream.close();
				System.out.println(i + "OK!");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// �ͷ�����
				urlConnection.disconnect();
			}
		}
	}

	public static void downloadImage3() throws Exception {
		HttpClient httpClient = HttpsUtils.getHttpClient();
		String httpGetUrl = "https://59.173.248.30:7013/include1/kaptcha.jsp?r=0.35200907982444873";
		HttpGet httpGet = new HttpGet(httpGetUrl);

		HttpResponse response = httpClient.execute(httpGet);

		InputStream inputStream = response.getEntity().getContent();

		OutputStream outputStream = new FileOutputStream(
				"E:/work/workspace/captcha/sourcePicture/" + UUID.randomUUID().toString()+".jpg");

		byte[] b = new byte[1024];
		int l = 0;
		while ((l = inputStream.read(b)) > 0) {
			outputStream.write(b, 0, l);
		}

		outputStream.close();
	}
}
