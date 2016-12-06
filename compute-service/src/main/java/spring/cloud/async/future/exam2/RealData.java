package spring.cloud.async.future.exam2;

import java.util.concurrent.Callable;

public class RealData implements Callable {
	protected String data;

	public RealData(String data) {
		this.data = data;
	}

	public String call() throws Exception {
		// 鍒╃敤sleep鏂规硶鏉ヨ〃绀虹湡鏄笟鍔℃槸闈炲父缂撴參鐨�
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}
}
