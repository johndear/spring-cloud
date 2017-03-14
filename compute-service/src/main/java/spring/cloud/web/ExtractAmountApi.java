package spring.cloud.web;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.async.AsyncThread;
import spring.cloud.mapper.ds2.ApiInvokeRecordMapper;
import spring.cloud.utils.IpUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
public class ExtractAmountApi {
	
	Logger logger = Logger.getLogger(ExtractAmountApi.class);

	@Autowired
	private ApiInvokeRecordMapper apiInvokeRecordMapper;
	
	@RequestMapping("/amount/get")
	public String compute(HttpServletRequest request, String json){
		logger.info("请求json参数：" + json);
		JSONObject jsonObj = null;
		try {
			if(StringUtils.isEmpty(json)){
				return "json不允许为空！";
			}
			
			jsonObj = JSONObject.parseObject(json);
			
			if(StringUtils.isEmpty(jsonObj.getString("callId"))){
				return "callId不允许为空！";
			}
			
			if(StringUtils.isEmpty(jsonObj.getString("callbackUrl"))){
				return "callbackUrl不允许为空！";
			}
			
			if(StringUtils.isEmpty(jsonObj.getString("callFunctions"))){
				return "callFunctions不允许为空！";
			}
			
			JSONArray callFunctions = JSONArray.parseArray(jsonObj.getString("callFunctions"));
			if(callFunctions == null || callFunctions.size()==0){
				return "callFunctions不允许为空！";
			}
			
			for (int i = 0; i < callFunctions.size(); i++) {
	            final JSONObject newJson = (JSONObject) callFunctions.get(i);
				if(StringUtils.isEmpty(newJson.getString("functionCallId"))){
					return "functionCallId不允许为空！";
				}
	
				if(StringUtils.isEmpty(newJson.getString("function"))){
					return "function不允许为空！";
				}
				
				List<String> alisa = SpringUtil.getServiceAliases();
				if(!alisa.contains(newJson.getString("function"))){
					return "目前尚未提供"+ newJson.getString("function") + " 服务方法调用！请仔细核对。";
				}
			}
			
		} catch (Exception e) {
			return "json格式有误,请仔细检查！参考格式如下：<br>"
					+ "{<br>"
					+ "&nbsp;&nbsp;callId: 'c0001',<br>"
					+ "&nbsp;&nbsp;callFunctions: [{<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;functionCallId: 'f0001',<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;function: 'count',<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;params: [{<br>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name: 'itemId',<br>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value: 'xxxx',<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;}]<br>"
					+ "&nbsp;&nbsp;}]<br>"
				+ "}";
		}
		
		// 异步调用
		new AsyncThread(apiInvokeRecordMapper, IpUtil.getIpAddr(request), request.getRequestURL().toString(), jsonObj, false).start();
		
		// 返回调用成功
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", 200);
		resultMap.put("msg", "调用成功!");
		return JSONObject.toJSONString(resultMap);
	}

}


