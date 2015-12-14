package cn.ruihe.utils;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.fileupload.FileItem;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.BucketReferer;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class OssUploadUtil {


//    accessKeyId=sOPI9Q3vRIUyvJ8U
//    #\u963F\u91CC\u4E91\u5BC6\u7801
//            accessKeySecret=o1Nx2BgpuJMv47pG0id4m99gpm5WTG
//    #\u963F\u91CC\u4E91\u670D\u52A1\u5668\u5730\u5740
//            endpoint="";
//    #\u5B58\u50A8\u8282\u70B9\u540D\u79F0
//            bucketName=brc01

    //30年
    private long EXPIRE_TIME = 30 * 365 * 86400 * 1000L;


    //配置文件路径
    private String propertyFileName = "config/filePath";
    private ResourceBundle resourceBundle;
    //ResourceBundle.getBundle(propertyFileName);
    //key
    private String accessKeyId = "sOPI9Q3vRIUyvJ8U";
    //resourceBundle.getString("accessKeyId");
    //secret
    private String accessKeySecret = "o1Nx2BgpuJMv47pG0id4m99gpm5WTG";
    //resourceBundle.getString("accessKeySecret");
    // 杭州服务器
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    //"brc02.img-cn-hangzhou.aliyuncs.com";

    //"oss-cn-hangzhou.aliyuncs.com";
    //resourceBundle.getString("endpoint");
    //数据容器名称
    private String bucketName = "brc02";
    //resourceBundle.getString("bucketName");

    // 初始化一个OSSClient
    OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

//    http://brc02.img-cn-hangzhou.aliyuncs.com/1449631288952_058783930?Expires=2395711289&OSSAccessKeyId=sOPI9Q3vRIUyvJ8U&Signature=mHv/8o%2BNLm7vDrxYTnBmUt5RRDU%3D
//
//    http://brc02.img-cn-hangzhou.aliyuncs.com/1449631288952_058783930?Expires=2395711289&OSSAccessKeyId=sOPI9Q3vRIUyvJ8U&Signature=mHv/8o%2BNLm7vDrxYTnBmUt5RRDU%3D
//
//
//    http://brc02.img-cn-hangzhou.aliyuncs.com/1449631288952_058783930?Expires=2395711289&OSSAccessKeyId=sOPI9Q3vRIUyvJ8U&Signature=mHv/8o%2BNLm7vDrxYTnBmUt5RRDU%3D

    /**
     * 上传文件处理
     * //	  * @param bucketName
     *
     * @param key
     * @param filePath
     * @throws FileNotFoundException
     */
    public void putObject(String key, String filePath) throws FileNotFoundException {
        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 获取指定文件的输入流
        File file = new File(filePath);
        InputStream content = new FileInputStream(file);
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();

        // 必须设置ContentLength
        meta.setContentLength(file.length());

        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, content, meta);

        // 打印ETag
        System.out.println(result.getETag());
    }

    /*
     * 新建bucket
     */
    public void createBucket(String bucketName) {
        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 新建一个Bucket
        client.createBucket(bucketName);
    }

    /**
     * 列出所有上传文件
     *
     * @param bucketName
     */
    public void listObjects(String bucketName) {

        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = client.listObjects(bucketName);

        // 遍历所有Object
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }

    /**
     * 获取指定文件
     *
     * @param bucketName
     * @param key
     * @throws IOException
     */
    public void getObject(String bucketName, String key) throws IOException {
        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = client.getObject(bucketName, key);
        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();
        // 处理Object

        // 关闭流
        objectContent.close();
    }

    public void getObjectUrl() {
        //key
        String accessKeyId = "sOPI9Q3vRIUyvJ8U";
        //secret
        String accessKeySecret = "o1Nx2BgpuJMv47pG0id4m99gpm5WTG";
        //服务器端生成url签名字串
        OSSClient Server = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            Date expiration = DateUtil.parseRfc822Date("Wed, 18 Mar 2016 14:20:00 GMT");
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, accessKeyId, HttpMethod.GET);
            //设置过期时间
            request.setExpiration(expiration);
            // 生成URL签名(HTTP GET请求)
            URL signedUrl = Server.generatePresignedUrl(request);
            System.out.println("signed url for getObject: " + signedUrl);

            //客户端使用使用url签名字串发送请求
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            Map<String, String> customHeaders = new HashMap<String, String>();
            // 添加GetObject请求头
            customHeaders.put("Range", "bytes=100-1000");
            OSSObject object = client.getObject(signedUrl, customHeaders);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 上传文件
     *
     * @param filePath 本地上传图片存放路径
     * @param fileName 云服务器上文件名称
     */
    public void uploadObject(String filePath, String fileName) {
        String filepath = "user/pageUpload/";
        // 初始化一个OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        File file = new File(filePath);
        try {
            InputStream content = new FileInputStream(file);
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            meta.setContentLength(file.length());
            //上传到云服务器的存放路径
            String key = filepath + fileName;
            PutObjectResult result = client.putObject(bucketName, key, content, meta);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void getRefer() {
        String accessKeyId = "sOPI9Q3vRIUyvJ8U";
        //secret
        String accessKeySecret = "o1Nx2BgpuJMv47pG0id4m99gpm5WTG";
        // 杭州服务器
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        //数据容器名称
        String bucketName = "brc01";

        // 初始化一个OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        List<String> refererList = new ArrayList<String>();
        BucketReferer br = new BucketReferer(true, refererList);
        br = client.getBucketReferer(bucketName);
        refererList = br.getRefererList();
        for (String referer : refererList) {
            System.out.println(referer);
        }
    }

    public InputStream getInputStream(String key) throws IOException {
        OSSObject object = client.getObject(bucketName, key);
        InputStream objectContent = object.getObjectContent();
        return objectContent;
    }

    public void deleteObject(String key) {
        // 初始化OSSClient    OSSClient client = ...;    // 删除Object
        client.deleteObject(bucketName, key);
    }


    /***
     * 更具流上传
     *
     * @param item
     * @return
     */
    public String uploadFileItem(FileItem item, String extName) {
        BufferedInputStream bis = null;
        try {
            InputStream is = item.getInputStream();
            bis = new BufferedInputStream(is);
            return this.upload(bis, item.getSize(), extName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /***
     * 更具流上传
     *
     * @param fileName
     * @return
     */
    public String uploadFile(String fileName) {
        BufferedInputStream bis = null;
        try {
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);
            bis = new BufferedInputStream(is);
            String extName = this.getExtName(fileName);
            if (extName == null) {
                extName = "";
            }
            return this.upload(bis, file.length(), extName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String upload(InputStream is, long size, String extName) {
        // 初始化一个OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            meta.setContentLength(size);
            //上传到云服务器的存放路径
            String key = getFileKey();
            key += "."+extName;
            //filepath + fileName;
            PutObjectResult result = client.putObject(bucketName, key, is, meta);
            return getFileUrlByKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到唯一的一个key
     *
     * @return
     */
    private String getFileKey() {
        Random random = new Random();
        int i = random.nextInt(999999999);
        java.text.NumberFormat nf = new java.text.DecimalFormat("000000000");
        long now = System.currentTimeMillis();
        String key = now + "_" + nf.format(i);
        return key;
    }


    private static String getExtName(String fileName) {
        String ext = "";
        if (fileName != null) {
            fileName = fileName.trim();
            if (fileName.length() > 0 && fileName.contains(".") && !fileName.endsWith(".")) {
                ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
            }
        }
        return ext;
    }


    /**
     * 获取图片URL
     *
     * @param key 云服务器存放文件的名称
     */
    private String getFileUrlByKey(String key) {
        // 设置URL过期时间为1小时
        try {
            Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);
            // 生成URL
            URL url = client.generatePresignedUrl(bucketName, key, expireDate);
            if (url != null) {
                return url.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        OssUploadUtil util = new OssUploadUtil();
        String url = util.uploadFile("/home/xuhui/16166432_1449421971559_mthumb.jpg");
        System.out.println(url);
    }
}
