package com.lcx;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Util {

    public static void main(String[] args) throws IOException {
        //获取输入流
        URL url = new URL("https://res.sxspv.com:4433/resources/images/data_screen/6250b23c54fdd6ab18dd7e50aa79566c.gif");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream bis = httpURLConnection.getInputStream();

        //获取输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get("a.gif")));
        bos.write(decode);

        bos.close();
        baos.close();
        bis.close();
    }

}


