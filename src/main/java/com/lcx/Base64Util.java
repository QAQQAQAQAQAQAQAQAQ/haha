package com.lcx;

import com.kingdee.shr.sso.client.util.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Util {

//    /**
//     * 图片URL转Base64编码
//     *
//     * @param imgUrl 图片URL
//     * @return Base64编码
//     */
//    public static String imageUrlToBase64(String imgUrl) {
//        URL url = null;
//        InputStream is = null;
//        ByteArrayOutputStream outStream = null;
//        HttpURLConnection httpUrl = null;
//
//        try {
//            url = new URL(imgUrl);
//            httpUrl = (HttpURLConnection) url.openConnection();
//            httpUrl.connect();
//            httpUrl.getInputStream();
//
//            is = httpUrl.getInputStream();
//            outStream = new ByteArrayOutputStream();
//
//            //创建一个Buffer字符串
//            byte[] buffer = new byte[1024];
//            //每次读取的字符串长度，如果为-1，代表全部读取完毕
//            int len = 0;
//            //使用输入流从buffer里把数据读取出来
//            while ((len = is.read(buffer)) != -1) {
//                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//                outStream.write(buffer, 0, len);
//            }
//
//            // 对字节数组Base64编码
//            return encode(outStream.toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (is != null) {
//                    is.close();
//                }
//                if (outStream != null) {
//                    outStream.close();
//                }
//                if (httpUrl != null) {
//                    httpUrl.disconnect();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 图片转字符串
//     *
//     * @param image 图片Buffer
//     * @return Base64编码
//     */
//    public static String encode(byte[] image) {
//        BASE64Encoder encoder = new BASE64Encoder();
//        return replaceEnter(encoder.encode(image));
//    }
//
//    /**
//     * 字符替换
//     *
//     * @param str 字符串
//     * @return 替换后的字符串
//     */
//    public static String replaceEnter(String str) {
//        String reg = "[\n-\r]";
//        Pattern p = Pattern.compile(reg);
//        Matcher m = p.matcher(str);
//        return m.replaceAll("");
//    }
//
//    public static void decode(String str) throws IOException {
//        byte[] bytes = BASE64Decoder.decodeBuffer(str);
//        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get("a.png")));
//        bos.write(bytes);
//        bos.close();
//    }

    public static void main(String[] args) throws IOException {
//        System.out.println(Base64Util.imageUrlToBase64("https://img-blog.csdnimg.cn/20210105221008901.png"));
//        d(Base64Util.imageUrlToBase64("https://img-blog.csdnimg.cn/20210105221008901.png"));
        //获取输入流
        URL url = new URL("https://img-blog.csdnimg.cn/20210105221008901.png");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream bis = httpURLConnection.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //获取输出流
        byte[] bytes = new byte[1024];
        int len;
        while ((len = bis.read(bytes)) != -1) {
            baos.write(bytes, 0, len);

        }
        //转成字符串base64
        Base64.Encoder encoder = Base64.getEncoder();
        String s = encoder.encodeToString(baos.toByteArray());
        System.out.println(s);
        //解码成字节码数组
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(s);
        //输出流输出
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get("a.png")));
        bos.write(decode);

        bos.close();
        baos.close();
        bis.close();
    }

}


