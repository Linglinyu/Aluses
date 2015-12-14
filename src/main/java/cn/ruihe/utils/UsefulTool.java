package cn.ruihe.utils;



import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class UsefulTool {

    private static String hexString = "0123456789ABCDEF";

    /**
     * 获取文件后缀名
     *
     * @param filename 文件名
     * @return 后缀名
     */
    public static String getSuffix(String filename) {
        int index = filename.lastIndexOf('.');
        return index > -1 ? filename.substring(index) : "";
    }

    /**
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     *
     * @param str 字符串
     * @return Byte字符串
     */
    public static String enByteString(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            sb.append(hexString.charAt((aByte & 0xf0) >> 4));
            sb.append(hexString.charAt((aByte & 0x0f)));
        }
        return sb.toString();
    }

    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     *
     * @param bytes Byte字符串
     * @return 字符串
     */
    public static String deByteString(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    /**
     * 字符串转unicode
     *
     * @param string 字符串
     * @return Unicode字符串
     */
    public static String enUnicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (char c : string.toCharArray()) {
            unicode.append("\\u").append(Integer.toHexString(c).toUpperCase());
        }
        return unicode.toString();
    }

    /**
     * unicode转字符串
     *
     * @param unicode Unicode字符串
     * @return 字符串
     */
    public static String deUnicode(String unicode) {
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }

    public static boolean inArray(int[] arr, int item) {
        for(int i : arr) {
            if(i == item) {
                return true;
            }
        }
        return false;
    }

    public static boolean inArray(Object[] arr, Object item) {
        for(Object i : arr) {
            if(Objects.equals(i, item)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getPinyin(String src) {
        char[] srcChar = src.toCharArray();
        String[] srcArry = new String[srcChar.length];
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

        // 设置格式
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String full = "";
        String sple = "";
        try {
            for (char c : srcChar) {
                // 判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    srcArry = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    full += srcArry[0];
                    if(srcArry[0].length() > 0) {
                        sple += srcArry[0].charAt(0);
                    }
                } else {
                    full += Character.toString(c);
                    sple += Character.toString(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return new String[]{full, sple};
    }
}
