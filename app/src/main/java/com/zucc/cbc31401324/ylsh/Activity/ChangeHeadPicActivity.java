package com.zucc.cbc31401324.ylsh.Activity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zucc.cbc31401324.ylsh.Bin.FormFile;
import com.zucc.cbc31401324.ylsh.Bin.GSONError;
import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.BuildConfig;
import com.zucc.cbc31401324.ylsh.CachePathUtil;
import com.zucc.cbc31401324.ylsh.FileUtils;
import com.zucc.cbc31401324.ylsh.R;
import com.zucc.cbc31401324.ylsh.http.HttpUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenbaichang on 2018/3/11.
 */

public class ChangeHeadPicActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView circleImageView;
    private File mTmpFile;
    private File mCropImageFile;
    private String pathpic;
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int REQUEST_CROP = 102;
    private GSONError gsonerror;
    private static final int LOGIN_RESULT = 1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int code = (int) msg.obj;
            switch (msg.what) {
                case LOGIN_RESULT:
                    if (200 == code) {
                        //TODO 更新UI
                        LoginResult.user.setUserHeadSrc(LoginResult.user.getUserId() + ".jpg");
                        Log.i("cws:", LoginResult.user.getUserHeadSrc());
                    } else {
                        // TODO 失败 报错
                        Log.d("ChangeHeadPicActivity", "handleMessage: ");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_headpic);
        circleImageView = (CircleImageView) findViewById(R.id.circleImage);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupDialog();
            }
        });
        Button button = findViewById(R.id.btn_save);
        button.setOnClickListener(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private void setupDialog() {
        //show window
        final String[] items = {"拍照", "相册"};
        AlertDialog.Builder listDialog = new AlertDialog.Builder(ChangeHeadPicActivity.this);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    camera();
                } else if (i == 1) {
                    gallery();
                }
            }
        });
        listDialog.show();
    }

    private void gallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private void camera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            mTmpFile = new File(FileUtils.createRootPath(getBaseContext()) + "/" + System.currentTimeMillis() + ".jpg");
            FileUtils.createFile(mTmpFile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID + ".provider", mTmpFile));
            } else {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    crop(mTmpFile.getAbsolutePath());
                } else {
                    Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    //更换图片
                    circleImageView.setImageURI(Uri.fromFile(mCropImageFile));
                    Log.i("cws", Uri.fromFile(mCropImageFile).getPath());
                } else {
                    Toast.makeText(this, "截图失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK && data != null) {
                    String imagePath = handleImage(data);
                    crop(imagePath);
                } else {
                    Toast.makeText(this, "打开图库失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void crop(String imagePath) {
        //mCropImageFile = FileUtils.createTmpFile(getBaseContext());
        mCropImageFile = getmCropImageFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropImageFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_CROP);
    }

    //把fileUri转换成ContentUri
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    //获取裁剪的图片保存地址
    private File getmCropImageFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LoginResult loginResult = new LoginResult();
//            System.currentTimeMillis()
            File file = new File(getExternalCacheDir(), LoginResult.user.getUserId() + ".jpg");
            return file;
        }
        return null;
    }

    private String handleImage(Intent data) {
        Uri uri = data.getData();
        String imagePath = null;
        if (Build.VERSION.SDK_INT >= 19) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("" +
                            "content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equals(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            }
        } else {
            imagePath = getImagePath(uri, null);
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    // getSrc
    //
    @Override
    public void onClick(View view) {
        // 图片保存
        // loginresult src
        // 图片发给服务器
        switch (view.getId()) {
            case R.id.btn_save:
//                LoginResult loginResult = new LoginResult();
//                loginResult = mCropImageFile;
                Intent intent = new Intent();
                intent.putExtra("0", mCropImageFile);
                setResult(0, intent);
                finish();
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        if (mCropImageFile != null)
                            Log.i("cws", mCropImageFile.toString());
                        HashMap<String, String> params = new HashMap<>();
                        params.put("userId", LoginResult.user.getUserId());
                        params.put("userHeadSrc", LoginResult.user.getUserId() + ".jpg");
                        int code = HttpUtil.post(HttpUtil.serverPath + "/user/upload", params, mCropImageFile);

                        try {
                            FormFile file = new FormFile(mCropImageFile.getName(), HttpUtil.readFileImage(mCropImageFile), "upload", "image/jpeg");
                            HttpUtil.post(HttpUtil.serverPath + "/user/upload", params, new FormFile[]{file});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        String path = "";
//                        //1.创建客户端对象
//                        HttpClient hc = new DefaultHttpClient();
//                        //2.创建post请求对象
//                        HttpPost hp = new HttpPost(path);
//                        //TODO 如何将File类型数据放到List里面
//                        //封装form表单提交的数据
//                        BasicNameValuePair bnvp = new BasicNameValuePair("userHeadSrc", "pathpic");
//                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
//                        //把BasicNameValuePair放入集合中
//                        parameters.add(bnvp);
//                        try {
//                            //要提交的数据都已经在集合中了，把集合传给实体对象
//                            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
//                            //设置post请求对象的实体，其实就是把要提交的数据封装至post请求的输出流中
//                            hp.setEntity(entity);
//                            //3.使用客户端发送post请求
//                            HttpResponse hr = hc.execute(hp);
//                            if (hr.getStatusLine().getStatusCode() == 200) {
//                                InputStream is = hr.getEntity().getContent();
//                                String text = Utils.getTextFromStream(is);
//
//                        //发送消息，让主线程刷新ui显示text
                        Message msg = handler.obtainMessage();
                        msg.what = LOGIN_RESULT;
                        msg.obj = code;
                        handler.sendMessage(msg);
//                            }
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
                    }
                };
                t.start();
                break;
            default:
                break;
        }
    }

    private void parseJASONWithGASON(String text) {
        Gson gson = new Gson();
        gsonerror = gson.fromJson(text, GSONError.class);
    }
}
