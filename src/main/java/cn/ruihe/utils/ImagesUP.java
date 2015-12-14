package cn.ruihe.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ImagesUP {


    private static OssUploadUtil ossUtil = new OssUploadUtil();


    private static List<String> fileTypes = new ArrayList<String>();


    public static final String SPLIT_SYMBOL = ";";

    static {
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("bmp");
        fileTypes.add("gif");
        fileTypes.add("png");
    }


    private static String getRealPath(HttpServletRequest request, String fileName) {
        return request.getSession().getServletContext().getRealPath(fileName);//上传的目录
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


    public static String addImageBy200(HttpServletRequest request, String pathName, MultipartFile[] files) throws Exception {
        return addImage(request, pathName, files, true, 200);
    }


    public static String addImageBy300(HttpServletRequest request, String pathName, MultipartFile[] files) throws Exception {
        return addImage(request, pathName, files, true, 300);
    }


    public static String addImage(HttpServletRequest request, String pathName, MultipartFile[] files) throws Exception {
        return addImage(request, pathName, files, false, 0);
    }


    /**
     * 上传图片文件,并保存到指定的路径当中
     */
    public static String addImage(HttpServletRequest request, String pathName, MultipartFile[] files, boolean scale, int width) throws Exception {

        if (files == null) {
            return null;
        }

        String realPath = getRealPath(request, pathName);
        if (!realPath.endsWith("/")) {
            realPath += "/";
        }
        String images = "";
        for (int i = 0; i < files.length; i++) {
            String ext = getExtName(files[i].getOriginalFilename());
            if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
                String fileNameTemp = getFileKey() + "." + ext;
                String pathFileNameTemp = realPath + fileNameTemp;
                File fileTemp = new File(pathFileNameTemp);
                FileUtils.writeByteArrayToFile(fileTemp, files[i].getBytes());
                if (scale) {
                    scalePic(files[i], width, width, realPath, fileNameTemp);
                }

                String fileUrl = ossUtil.uploadFile(pathFileNameTemp);

                if ((i + 1) == files.length) {
                    images += fileUrl;
                } else {
                    images += fileUrl + SPLIT_SYMBOL;
                }
                fileTemp.delete();
            }
        }
        return images;
    }

    private static String getFileKey() {
        Random random = new Random();
        int i = random.nextInt(999999999);
        java.text.NumberFormat nf = new java.text.DecimalFormat("000000000");
        long now = System.currentTimeMillis();
        String key = now + "_" + nf.format(i);
        return key;
    }

//    public static String addEcImage(HttpServletRequest request, String imagesName, MultipartFile[] files, boolean isSaveSmallImg) throws Exception {
//
//        String pathStr = request.getSession().getServletContext().getRealPath(imagesName);//上传的目录
//        System.out.println(pathStr);
//        File lock = new File(pathStr);
//        if (lock.exists()) {
//            lock.createNewFile();
//            System.out.println("==============");
//        }
//
//        List<String> fileTypes = new ArrayList<String>();
//        fileTypes.add("jpg");
//        fileTypes.add("jpeg");
//        fileTypes.add("bmp");
//        fileTypes.add("gif");
//        fileTypes.add("png");
//
//        String images = "";
//        for (int i = 0; i < files.length; i++) {
//            String fileName = files[i].getOriginalFilename();
//            String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
//            // 对扩展名进行小写转换
//            ext = ext.toLowerCase();
//
//            if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
//                String filename = new Date().getTime() + "." + ext;
//                File lastFile = new File(pathStr + "/" + filename);
//                FileUtils.writeByteArrayToFile(lastFile, files[i].getBytes());
////			    addImageBySmal( request , imagesName , files[i] , filename);
//                String pathStr2 = request.getSession().getServletContext().getRealPath(imagesName);//上传的目录
//                uploadHead3(files[i], 300, 300, pathStr2, filename);
//                if ((i + 1) == files.length) {
//                    images += imagesName + "/" + filename;
//                } else {
//                    images += imagesName + "/" + filename + ";";
//                }
//            }
//        }
//        return images;
//    }


//
//    public static void main(String[] args){
////        File file=new File("");
////
////        ByteArrayInputStream bais = new ByteArrayInputStream();
////        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
//
//        String imgPath = "C://demo.jpg";
//
//        BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
//        image
//
//        Image src=ImageToolkit.getDefaultToolkit().getImage(imgPath);
//        Image src = null;
//        try {
//            src = ImageIO.read(mciis);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        BufferedImage tag = new BufferedImage((int) deskWidth, (int) deskHeight, BufferedImage.TYPE_3BYTE_BGR);
//        tag.getGraphics().drawImage(src, 0, 0, (int) deskWidth, (int) deskHeight, null); // 绘制缩小后的图
//    }


    public static boolean scalePic(MultipartFile oldImage, int height, int width, String pathStr, String filename)
            throws Exception {

        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage.getBytes());
        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
        Image src = ImageIO.read(mciis);
        double srcHeight = src.getHeight(null);
        double srcWidth = src.getWidth(null);
        double deskHeight = 0;// 缩略图高
        double deskWidth = 0;// 缩略图宽
        if (srcWidth > srcHeight) {
            if (srcWidth > width) {
                if (width / height > srcWidth / srcHeight) {
                    deskHeight = height;
                    deskWidth = srcWidth / (srcHeight / height);
                } else {
                    deskHeight = width / (srcWidth / srcHeight);
                    deskWidth = width;
                }
            } else {

                if (srcHeight > height) {
                    deskHeight = height;
                    deskWidth = srcWidth / (srcHeight / height);
                } else {
                    deskHeight = srcHeight;
                    deskWidth = srcWidth;
                }
            }
        } else if (srcHeight > srcWidth) {
            if (srcHeight > (height)) {
                if ((height) / width > srcHeight / srcWidth) {
                    deskHeight = srcHeight / (srcWidth / width);
                    deskWidth = width;
                } else {
                    deskHeight = height;
                    deskWidth = (height) / (srcHeight / srcWidth);
                }
            } else {
                if (srcWidth > width) {
                    deskHeight = srcHeight / (srcWidth / width);
                    deskWidth = width;
                } else {
                    deskHeight = srcHeight;
                    deskWidth = srcWidth;
                }

            }

        } else if (srcWidth == srcHeight) {

            if (width >= (height) && srcHeight > (height)) {
                deskWidth = (height);
                deskHeight = (height);
            } else if (width <= (height) && srcWidth > width) {
                deskWidth = width;
                deskHeight = width;
            } else if (width == (height) && srcWidth < width) {
                deskWidth = srcWidth;
                deskHeight = srcHeight;
            } else {
                deskHeight = srcHeight;
                deskWidth = srcWidth;
            }

        }
        BufferedImage tag = new BufferedImage((int) deskWidth, (int) deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, (int) deskWidth, (int) deskHeight, null); // 绘制缩小后的图
        ImageIO.write(tag, "JPEG", new File(pathStr, filename)); // 输出图像
        return true;
    }


//    public static boolean uploadHead2(MultipartFile oldImage, int height, int width, String pathStr, String filename)
//            throws Exception {
//
//        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage.getBytes());
//        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
//        Image src = ImageIO.read(mciis);
//        double srcHeight = src.getHeight(null);
//        double srcWidth = src.getWidth(null);
//        double deskHeight = 0;// 缩略图高
//        double deskWidth = 0;// 缩略图宽
//        if (srcWidth > srcHeight) {
//
//            if (srcWidth > width) {
//                if (width / height > srcWidth / srcHeight) {
//                    deskHeight = height;
//                    deskWidth = srcWidth / (srcHeight / height);
//                } else {
//                    deskHeight = width / (srcWidth / srcHeight);
//                    deskWidth = width;
//                }
//            } else {
//
//                if (srcHeight > height) {
//                    deskHeight = height;
//                    deskWidth = srcWidth / (srcHeight / height);
//                } else {
//                    deskHeight = srcHeight;
//                    deskWidth = srcWidth;
//                }
//
//            }
//
//        } else if (srcHeight > srcWidth) {
//            if (srcHeight > (height)) {
//                if ((height) / width > srcHeight / srcWidth) {
//                    deskHeight = srcHeight / (srcWidth / width);
//                    deskWidth = width;
//                } else {
//                    deskHeight = height;
//                    deskWidth = (height) / (srcHeight / srcWidth);
//                }
//            } else {
//                if (srcWidth > width) {
//                    deskHeight = srcHeight / (srcWidth / width);
//                    deskWidth = width;
//                } else {
//                    deskHeight = srcHeight;
//                    deskWidth = srcWidth;
//                }
//
//            }
//
//        } else if (srcWidth == srcHeight) {
//
//            if (width >= (height) && srcHeight > (height)) {
//                deskWidth = (height);
//                deskHeight = (height);
//            } else if (width <= (height) && srcWidth > width) {
//                deskWidth = width;
//                deskHeight = width;
//            } else if (width == (height) && srcWidth < width) {
//                deskWidth = srcWidth;
//                deskHeight = srcHeight;
//            } else {
//                deskHeight = srcHeight;
//                deskWidth = srcWidth;
//            }
//
//        }
//        BufferedImage tag = new BufferedImage((int) deskWidth, (int) deskHeight, BufferedImage.TYPE_3BYTE_BGR);
//        tag.getGraphics().drawImage(src, 0, 0, (int) deskWidth, (int) deskHeight, null); // 绘制缩小后的图
////		filename=filename+"$"+deskWidth+"$"+deskHeight;
////		uploadHead( tag , pathStr , filename );
//        return true;
//    }
//
//    public static boolean uploadHead3(MultipartFile oldImage, int height, int width, String pathStr, String filename)
//            throws Exception {
//
//        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage.getBytes());
//        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
//        Image src = ImageIO.read(mciis);
//        double srcHeight = src.getHeight(null);
//        double srcWidth = src.getWidth(null);
//        double deskHeight = srcHeight;// 缩略图高
//        double deskWidth = srcWidth;// 缩略图宽
//        if (srcWidth > srcHeight) {
//
//            if (srcWidth > width) {
//                if (width / height > srcWidth / srcHeight) {
//                    deskHeight = height;
//                    deskWidth = srcWidth / (srcHeight / height);
//                } else {
//                    deskHeight = width / (srcWidth / srcHeight);
//                    deskWidth = width;
//                }
//            } else {
//
//                if (srcHeight > height) {
//                    deskHeight = height;
//                    deskWidth = srcWidth / (srcHeight / height);
//                } else {
//                    deskHeight = srcHeight;
//                    deskWidth = srcWidth;
//                }
//
//            }
//
//        } else if (srcHeight > srcWidth) {
//            if (srcHeight > (height)) {
//                if ((height) / width > srcHeight / srcWidth) {
//                    deskHeight = srcHeight / (srcWidth / width);
//                    deskWidth = width;
//                } else {
//                    deskHeight = height;
//                    deskWidth = (height) / (srcHeight / srcWidth);
//                }
//            } else {
//                if (srcWidth > width) {
//                    deskHeight = srcHeight / (srcWidth / width);
//                    deskWidth = width;
//                } else {
//                    deskHeight = srcHeight;
//                    deskWidth = srcWidth;
//                }
//
//            }
//
//        } else if (srcWidth == srcHeight) {
//
//            if (width >= (height) && srcHeight > (height)) {
//                deskWidth = (height);
//                deskHeight = (height);
//            } else if (width <= (height) && srcWidth > width) {
//                deskWidth = width;
//                deskHeight = width;
//            } else if (width == (height) && srcWidth < width) {
//                deskWidth = srcWidth;
//                deskHeight = srcHeight;
//            } else {
//                deskHeight = srcHeight;
//                deskWidth = srcWidth;
//            }
//
//        }
//        BufferedImage tag = new BufferedImage((int) deskWidth, (int) deskHeight, BufferedImage.TYPE_3BYTE_BGR);
//        tag.getGraphics().drawImage(src, 0, 0, (int) deskWidth, (int) deskHeight, null); // 绘制缩小后的图
//        System.out.println("deskWidth=" + deskWidth);
////		filename=filename+"$"+(int)deskWidth+"$"+(int)deskHeight;
//        System.out.println("filename=" + filename);
//        uploadHead(tag, pathStr, filename);
//        return true;
//    }
//
//    /**
//     * @param file文件流
//     * @param path文件存放路径
//     * @param userId上传用户的Id
//     * @return 新文件名
//     * @throws Exception
//     */
//    public static String uploadHead(BufferedImage sourceImg, String path, String filename) throws Exception {
//        ImageIO.write(sourceImg, "JPEG", new File(path, filename)); // 输出图像
//        return null;
//    }

    public static String modifyImageUrl(HttpServletRequest request, String url) throws FileNotFoundException, IOException {
        String imagesName = "/mate_images";
        String imageUrl = "";
        if (url != null && url.length() > 0) {
            String pathStr = request.getSession().getServletContext().getRealPath(url);//上传的目录
            System.out.println(pathStr);
            File picture = new File(pathStr);
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
            System.out.println(String.format("%.1f", picture.length() / 1024.0));
            System.out.println(sourceImg.getWidth());
            System.out.println(sourceImg.getHeight());
            imageUrl = url + "$" + sourceImg.getWidth() + "$" + sourceImg.getHeight();
        }

        return imageUrl;
    }
}
