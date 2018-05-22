package com.zucc.cbc31401324.ylsh;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.zucc.cbc31401324.ylsh.Activity.MarkerClickActivity;
import com.zucc.cbc31401324.ylsh.Activity.SearchActivity;
import com.zucc.cbc31401324.ylsh.Activity.UpDataActivity;
import com.zucc.cbc31401324.ylsh.Bin.Passing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chenbaichang on 2018/2/6.
 */

public class Fragment_Map_Activity extends Fragment implements View.OnClickListener {
    private View mView;
    private TextureMapView mMapView;
    private TextView tv_postion;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private boolean  isFirstLocate = true;
    private MyLocationData locationData;
    private BDLocation myBDlocation;
    public BDLocation currlocation = null; // 存储当前定位信息
    private ImageView image;
    private Bitmap mBitmap;
    private Infos info;
    private BitmapDescriptor bitmap;
    private Marker marker;
    private RelativeLayout mMarkerLy;
    public double mLatitude;
    public double mLongitude;
    private String result,result1,result2,result3,result4,result5;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        SDKInitializer.initialize(getActivity().getApplicationContext());
        mView = inflater.inflate(R.layout.tab_map,container,false);
        mMapView = (TextureMapView)mView.findViewById(R.id.mTexturemap);
        mMarkerLy = (RelativeLayout)mView.findViewById(R.id.marker_ly);//初始化布局
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        RelativeLayout rl = (RelativeLayout)mView.findViewById(R.id.marker_ly);
        rl.setOnClickListener(this);
        Button btn = (Button)mView.findViewById(R.id.backsite);
        btn.setOnClickListener(this);
        Button btn1 = (Button)mView.findViewById(R.id.overlay);
        btn1.setOnClickListener(this);
        Button btn2 = (Button)mView.findViewById(R.id.addsite);
        btn2.setOnClickListener(this);
        Button btn3 = (Button)mView.findViewById(R.id.search);
        btn3.setOnClickListener(this);
        initLocation();
        initBaiDuMap();
        initMarker();
        search();
        //对 marker 添加点击相应事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                Infos markerinfo = (Infos)extraInfo.getSerializable("info");
                ImageView iv = (ImageView)mView.findViewById(R.id.mark_img);
                TextView tv = (TextView)mView.findViewById(R.id.mark_name);
                TextView tv1 = (TextView)mView.findViewById(R.id.mark_sitetype);
                TextView tv2 = (TextView)mView.findViewById(R.id.mark_countpeople);
                TextView tv3 = (TextView)mView.findViewById(R.id.mark_distance);
//                iv.setImageResource(makerinfo.getImgId());
                tv.setText(markerinfo.getName());
                tv1.setText(markerinfo.getCharge_type());
                tv2.setText(markerinfo.getAddr());
                tv3.setText(markerinfo.getDistance());

                result = markerinfo.getAddr();
                result1 = markerinfo.getName();
                result2 = markerinfo.getCharge_type();
                result3 = markerinfo.getSite_type();
                result4 = markerinfo.getSite_mode();
                result5 = markerinfo.getSite_info();
                mMarkerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMarkerLy.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permissionList.isEmpty()){
            String[] permissions= permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(),permissions,1);
        }else{
            requestLocation();
        }
        return mView;

    }

    private void initBaiDuMap() {

    }
    private void requestLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for (int result: grantResults
                            ) {
                        if(result !=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getActivity(), "必须同意所有的权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            System.exit(0);
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void initLocation(){
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                myBDlocation = bdLocation;
                StringBuilder  currentPosition =  new StringBuilder();
                currentPosition.append("维度：").append(bdLocation.getLatitude()).append("\n");
                currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
                currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
                currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
                currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
                currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
                currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
                currentPosition.append("门牌号：").append(bdLocation.getStreetNumber()).append("\n");
                currentPosition.append("定位方式：");


                if(bdLocation.getLocType() == BDLocation.TypeGpsLocation ||bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){

                    if(isFirstLocate){
                        LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(ll).zoom(18.0f);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        isFirstLocate = false;
                    }
                    MyLocationData.Builder  locationBuilder = new MyLocationData.Builder();
                    locationBuilder.latitude(bdLocation.getLatitude());
                    locationBuilder.longitude(bdLocation.getLongitude());
                    locationData = locationBuilder.build();
                    mBaiduMap.setMyLocationData(locationData);

                }
            }
        });
    }

    /**
     * 读取网络图片
     * @param imgurl
     * @return
     */
    protected Bitmap getBitmapFromUrl(String imgurl) {
        URL url;
        Bitmap bitmap = null;
        try {
            url = new URL(imgurl);
            InputStream is = url.openConnection().getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // 线程处理网络图片下载
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            mBitmap = getBitmapFromUrl(info.getImgurl());
            mHandler.sendMessage(msg);
        }
    };

    /**
     * 返回数据
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj == null) {
                        Log.e("main", "接收数据为空");
                    } else {
                        String result = msg.obj.toString();
                        try {
                            JSONObject json = new JSONObject(result);
                            parser(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    image.setImageBitmap(mBitmap);
                    break;
            }
        }
    };

    /**
     * 解析返回数据
     *
     * @param json
     */
    protected void parser(JSONObject json) {
        Infos infos = new Infos();
        List<Infos> list = infos.getInfos();
        try {
            JSONArray jsonArray = json.getJSONArray("contents");
            if (jsonArray != null && jsonArray.length() <= 0) {
                Toast.makeText(getActivity(), "没有符合要求的数据", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                    Infos info = new Infos();

                    info.setName(jsonObject2.getString("title"));
                    info.setAddr(jsonObject2.getString("address"));
                    info.setCharge_type(jsonObject2.getString("charge"));
                    info.setSite_mode(jsonObject2.getString("mode_text"));
                    info.setSite_info(jsonObject2.getString("info_text"));
                    info.setSite_type(jsonObject2.getString("type_text"));
                    info.setDistance("距离" + jsonObject2.getString("distance")
                            + "米");

                    JSONArray locArray = jsonObject2.getJSONArray("location");
                    double longitude = locArray.getDouble(0);
                    double latitude = locArray.getDouble(1);
                    info.setLatitude(latitude);
                    info.setLongitude(longitude);

                    float results[] = new float[1];
                    if (currlocation != null) {
                        Log.e("currlocation", currlocation.getLatitude()+"-"+currlocation.getLongitude());
                        Log.e("location", latitude+"-"+longitude);
                        Location.distanceBetween(currlocation.getLatitude(),
                                currlocation.getLongitude(), latitude,
                                longitude, results);
                    }
                    info.setDistance("距离" + (int)results[0] + "米");

                    list.add(info);
                }
            }
        } catch (Exception e) {
            Log.e("main", "parser解析错误！");
        }
    }

    /**
     * 发起云检索
     */
    private void search() {
        Infos infos = new Infos();
        infos.getInfos().clear();
        LBSSearchActivity.request(getRequestParams(), mHandler);
    }

    /**
     * 设定云检索参数
     *
     * @return
     */
    private HashMap<String, String> getRequestParams() {
        HashMap<String, String> map = new HashMap<String, String>();

        try {
            map.put("radius", "2000");
            if (currlocation != null) {
                map.put("location", currlocation.getLongitude() + ","
                        + currlocation.getLatitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 初始化覆盖物
     */
    private void initMarker() {
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.fishsite);
    }

    /**
     * 添加覆盖物
     *
     * @param infos
     */
    private void addOverlays(List<Infos> infos) {
        mBaiduMap.clear();
        LatLng latlng = null;
        for (Infos info : infos) {
            latlng = new LatLng(info.getLatitude(), info.getLongitude());
            Log.w("Main", latlng.toString());
            MarkerOptions options = new MarkerOptions().position(latlng)
                    .icon(bitmap).zIndex(9).draggable(false);

            options.animateType(MarkerOptions.MarkerAnimateType.grow);
            marker = (Marker) (mBaiduMap.addOverlay(options));
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    public void backsite(){
        mLatitude = myBDlocation.getLatitude();
        mLongitude = myBDlocation.getLongitude();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backsite:
                backsite();
                LatLng latl = new LatLng(mLatitude, mLongitude);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latl);
                mBaiduMap.animateMapStatus(update);
                break;
            case R.id.overlay:
                addOverlays(Infos.infos);
                search();
                break;
            case R.id.addsite:
                Intent intent = new Intent(getActivity(), UpDataActivity.class);
                Fragment_Map_Activity.this.startActivity(intent);
                break;
            case R.id.search:
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                Fragment_Map_Activity.this.startActivity(intent1);
                break;
            case R.id.marker_ly:
                Intent intent2 = new Intent(getActivity(), MarkerClickActivity.class);
                Passing.location = result;
                Passing.name = result1;
                Passing.charge = result2;
                Passing.sitetype = result3;
                Passing.model = result4;
                Passing.siteinfo = result5;
                Fragment_Map_Activity.this.startActivity(intent2);
                break;
            default:break;

        }
    }
}


