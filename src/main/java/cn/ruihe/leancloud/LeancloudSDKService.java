package cn.ruihe.leancloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>Title: LeancloudSDKService.java</p>
 * <p>Description: </p>
 * @author try
 * @date 2015年7月16日-上午10:06:35
 * @version 1.0
 */
public class LeancloudSDKService {

	
	public static final String AppID = "p1inq33dn39opaj3brd1k1j79ad1dnqsavtsb9mn9bpntzyz";
	
	public static final String AppKey = "gcovi34s9h9i2b2uv0kkfatgz5hb0kjovwn7t9lhlba8fnp8";
	
//	public static final String AppID = "s2zzg1jng2hx9haoj3fvck2nfvverciju51z6q8q7ehta5jp";
//	
//	public static final String AppKey = "fjntes17xq3s7sc61s83wbm6j2h033cfc9dnjd8xs8058i4t";
	
	public static final String MasterKey = "e7d4nofgp4psyg9ebavrtnd8svhq5hkj85vh9f7ier2g1omq";
	
	
	public static final String LeancloudCreateUrl = "https://api.leancloud.cn/1.1/classes/_Conversation";
	
	public static final String LeancloudAddUrl = "https://api.leancloud.cn/1.1/classes/_Conversation/";
	
	public static final String APPLICATION_JSON = "application/json";
    
	public static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    
	/**
	 * 创建一个对话
	 * @param name 创建者
	 * @return
	 */
	public  String  getLeancloudByCreate( String user , String ceancloud_name ){
		
		String objectId = "";
		
        try { 
        	
        	Map map = new HashMap();
        	map.put("name", ceancloud_name);
//        	map.put("tr", true);
        	
        	//设置成员
        	List a = new ArrayList();
        	a.add(user);
        	
        	Map all =new HashMap();
        	all.put("m",a);
        	
        	map.putAll(all);
        	
        	JSONObject ob = new JSONObject(map);
        	
        	System.out.println(ob.toString());
        	URL postUrl = new URL(LeancloudCreateUrl);  
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-AVOSCloud-Application-Id",  AppID);  
            connection.setRequestProperty("X-AVOSCloud-Application-Key", AppKey);  
            connection.setRequestProperty("Content-Type","application/json");
            connection.setChunkedStreamingMode(5);
            
            
            OutputStream os = connection.getOutputStream();       
            os.write(ob.toString().getBytes("UTF-8"));       
            os.close(); 
            
            connection.connect();  
            
            InputStream in = connection.getInputStream();  
            BufferedReader br = new BufferedReader(new InputStreamReader(in));  
            String line = ""; 
            while((line = br.readLine()) != null) {
            	objectId+=line;
                System.out.println(line);  
            }  
            br.close();  
            in.close();
            JSONObject jb = JSONObject.parseObject(objectId);
            return jb.get("objectId").toString();
        } catch(IOException e) {  
            e.printStackTrace();  
        }  
		return null;
	}
	
	
	
	/**
	 *  增删对话成员
	 * @param deviceToken 用户登录以后的认证key
	 * @return
	 */
	public String  getLeancloudByAdd( String deviceToken , boolean isDelete , String userName ){
		
		String objectId = "";
		
        try { 
        	Map map = new HashMap();
        	Map all =new HashMap();
        	
        	//设置删除对象
        	List a = new ArrayList();
        	a.add(userName);
        	
        	if( isDelete ){//删除
            	//设置删除type
            	all.put("__op","Remove");
        	}else {
        		all.put("__op","AddUnique");
			}
        	
        	all.put("objects", a);
        	map.put("m", all);
        	
        	JSONObject ob = new JSONObject(map);
        	
        	System.out.println(ob.toString());
        	URL postUrl = new URL(LeancloudAddUrl+deviceToken);
        	
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("X-AVOSCloud-Application-Id",  AppID);  
            connection.setRequestProperty("X-AVOSCloud-Application-Key", AppKey);  
            connection.setRequestProperty("Content-Type","application/json");
            connection.setChunkedStreamingMode(5);
            
            
            OutputStream os = connection.getOutputStream();       
            os.write(ob.toString().getBytes("UTF-8"));       
            os.close(); 
            
            connection.connect();  
            
            InputStream in = connection.getInputStream();  
            BufferedReader br = new BufferedReader(new InputStreamReader(in));  
            String line = ""; 
            while((line = br.readLine()) != null) {
            	objectId+=line;
                System.out.println(line);  
            }  
            br.close();  
            in.close();
            JSONObject jb = JSONObject.parseObject(objectId);
            
            return jb.get("objectId").toString();
        } catch(IOException e) {  
            e.printStackTrace();  
        }  
		        
		return null;
	}
	public static void main(String[] args) {
		new LeancloudSDKService().getLeancloudByAdd("55b1260c00b0d7c04ca9148b",false,"LarryPage");
//		new LeancloudSDKService().getLeancloudByCreate("try;demo","try");
	}
}
