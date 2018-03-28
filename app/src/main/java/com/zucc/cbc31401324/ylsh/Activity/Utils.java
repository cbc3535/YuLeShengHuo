package com.zucc.cbc31401324.ylsh.Activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * Created by chenbaichang on 2018/3/28.
 */

public class Utils {
    public static String getTextFromStream(InputStream is){

        byte[] b = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while((len = is.read(b)) != -1){
                bos.write(b, 0, len);
            }
            String text = new String(bos.toByteArray());
            bos.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
