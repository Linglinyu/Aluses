package cn.ruihe.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.entity.BrcUserInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author YKX
 * 
 * @description
 */
public class BrcUserInfoUtils {

	
	private static final String URL_USER_INFOS = "http://api.justbon.com/brc-app-web/app/getUserHeadAndStepS.json";
	private static final String PARAM_USER_INFOS = "{\"IDs\":\"%s\"}";
	
	

	public static List<BrcUserInfo> getBrcUserInfos(String userIds) {

		String str = HttpRequestor.doPostJsonStr(URL_USER_INFOS,
				String.format(PARAM_USER_INFOS, userIds));
		try {
			str=java.net.URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(str);
		JSONObject jsonObject;
		try {
			jsonObject = JSON.parseObject(str);
			JSONObject dataObject = jsonObject.getJSONObject("datas");
			if (dataObject != null && dataObject.getBoolean("success")) {
				return JSON.parseArray(dataObject.getJSONArray("rows").toString(),
						BrcUserInfo.class);
			}
		} catch (Exception e) {
			throw new MessageException("蓝光返回数据解析出错",400);
		}
		return null;
	}
}
