package spring.cloud.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpUtil {
	
	public static String post(String url, Map<String,Object> params){
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			if (params != null) {
				int n = params.size();
				NameValuePair[] data = new NameValuePair[n];
				Iterator iter = params.entrySet().iterator();
				int i = -1;
				while (iter.hasNext()) {
					i++;
					Map.Entry entry = (Map.Entry) iter.next();
					String name = (String) entry.getKey();
					String value = (String) entry.getValue();
					nvps.add(new BasicNameValuePair(name, value)); 
				}
			}
			
			HttpClient httpClient = HttpClients.createDefault();
			
//			HttpGet httpGet = new HttpGet(jsonObj.getString("callbackUrl") + "?data=" + URLEncoder.encode(JSON.toJSONString(resultMap),"UTF-8"));
//			httpGet.getParams().setParameter("data", );
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom()  
			        .setConnectTimeout(5000) 
			        .setConnectionRequestTimeout(1000)
			        .setSocketTimeout(5000).build(); 
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));   
			HttpResponse response = httpClient.execute(httpPost);
			
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode == 200){
				HttpEntity httpEntity = response.getEntity();
				if(httpEntity!=null){
					return EntityUtils.toString(httpEntity);
				}
			}else{
				System.out.println("调用" + url + "接口, 参数：" + JSON.toJSONString(params) + "，返回状态码：" + statusCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
