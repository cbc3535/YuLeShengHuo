package com.zucc.cbc31401324.ylsh.http;

import com.zucc.cbc31401324.ylsh.Activity.Utils;
import com.zucc.cbc31401324.ylsh.Bin.FormFile;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenbaichang on 2018/3/28.
 */

public class HttpUtil {
    public static String serverIP = "106.15.230.57:8081";
    public static String serverPath = "http://" + serverIP + ":8081";

    public static JSONObject postMethod(List<BasicNameValuePair> parameters, String path) {
        JSONObject result = null;
        try {
            HttpClient hc = new DefaultHttpClient();
            HttpPost hp = new HttpPost(serverPath + path);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
            hp.setEntity(entity);
            HttpResponse hr = hc.execute(hp);
            int statusCode = hr.getStatusLine().getStatusCode();
            System.out.println(Calendar.getInstance() + ": statusCode = " + statusCode);
            if (statusCode == 200) {
                InputStream is = hr.getEntity().getContent();
                String text = Utils.getTextFromStream(is);
                System.out.println("result: " + text);
                result = new JSONObject(text);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int post(String actionUrl, Map<String, String> params, File file) {

        //前面设置报头不需要更改
        try {
            String BOUNDARY = "----WebKitFormBoundaryKoYogLDzBS3Emu1H"; //数据分隔线
            String MULTIPART_FORM_DATA = "multipart/form-data";
            URL url = new URL(actionUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);//允许输入
            conn.setDoOutput(true);//允许输出
            conn.setUseCaches(false);//不使用Cache
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY);

            //获取map对象里面的数据，并转化为string
            StringBuilder sb = new StringBuilder();
            //上传的表单参数部分，不需要更改
            for (Map.Entry<String, String> entry : params.entrySet()) {//构建表单字段内容
                sb.append("--");
                sb.append(BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                sb.append(entry.getValue());
                sb.append("\r\n");
            }
            System.out.println(sb.toString());


            //上传图片部分
            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(sb.toString().getBytes());//发送表单字段数据
            //调用自定义方法获取图片文件的byte数组
            byte[] content = readFileImage(file);
            //再次设置报头信息
            StringBuilder split = new StringBuilder();
            split.append("--");
            split.append(BOUNDARY);
            split.append("\r\n");


            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!非常重要
            //此处将图片的name设置为file ,filename不做限制，不需要管
            split.append("Content-Disposition: form-data;name=\"upload\";filename=\"temp.jpg\"\r\n");
            //这里的Content-Type非常重要，一定要是图片的格式，例如image/jpeg或者image/jpg
            //服务器端对根据图片结尾进行判断图片格式到底是什么,因此务必保证这里类型正确
            split.append("Content-Type: image/jpeg\r\n\r\n");
            System.out.println(split.toString());
            outStream.write(split.toString().getBytes());
            outStream.write(content, 0, content.length);
            outStream.write("\r\n".getBytes());
            byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();//数据结束标志
            outStream.write(end_data);
            outStream.flush();


            //返回状态判断
            int cah = conn.getResponseCode();
            //            if (cah != 200) throw new RuntimeException("请求url失败:"+cah);
            if (cah == 200)//如果发布成功则提示成功
            {
                System.out.println("上传成功");
            } else {
                throw new RuntimeException("请求url失败:" + cah);
            }
            outStream.close();
            conn.disconnect();
            return cah;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static byte[] readFileImage(File file) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(file));
        int len = bufferedInputStream.available();
        byte[] bytes = new byte[len];
        int r = bufferedInputStream.read(bytes);
        if (len != r) {
            bytes = null;
            throw new IOException("读取文件不正确");
        }
        bufferedInputStream.close();
        return bytes;
    }

    /**
     * 直接通过HTTP协议提交数据到服务器,实现表单提交功能
     *
     * @param actionUrl 上传路径
     * @param params    请求参数 key为参数名,value为参数值
     * @param files     上传文件
     */
    public static String post(String actionUrl, Map<String, String> params, FormFile[] files) {
        try {
            String BOUNDARY = "----WebKitFormBoundaryKoYogLDzBS3Emu1H"; //数据分隔线
            String MULTIPART_FORM_DATA = "multipart/form-data";

            URL url = new URL(actionUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);//允许输入  
            conn.setDoOutput(true);//允许输出  
            conn.setUseCaches(false);//不使用Cache  
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY);

            StringBuilder sb = new StringBuilder();

            //上传的表单参数部分，格式请参考文章  
            for (Map.Entry<String, String> entry : params.entrySet()) {//构建表单字段内容  
                sb.append("–");
                sb.append(BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                sb.append(entry.getValue());
                sb.append("\r\n");
            }
            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(sb.toString().getBytes());//发送表单字段数据  

            //上传的文件部分，格式请参考文章  
            for (FormFile file : files) {
                StringBuilder split = new StringBuilder();
                split.append("–");
                split.append(BOUNDARY);
                split.append("\r\n");
                split.append("Content-Disposition: form-data;name=\"" + file.getFormname() + "\";filename=\"" + file.getFilname() + "\"\r\n");
                split.append("Content-Type: " + file.getContentType() + "\r\n\r\n");
                outStream.write(split.toString().getBytes());
                outStream.write(file.getData(), 0, file.getData().length);
                outStream.write("\r\n".getBytes());
            }
            byte[] end_data = ("–" + BOUNDARY + "–\r\n").getBytes();//数据结束标志           
            outStream.write(end_data);
            outStream.flush();
            int cah = conn.getResponseCode();
            if (cah != 200) throw new RuntimeException("请求url失败");
            InputStream is = conn.getInputStream();
            int ch;
            StringBuilder b = new StringBuilder();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            outStream.close();
            conn.disconnect();
            return b.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
