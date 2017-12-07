package com.example.gtercn.car.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.gtercn.car.utils.TLog;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016-11-1.
 * multipart/form-data
 * 兼容性问题，将 apache http core and mime 版本降低到4.2.2
 *
 */
public class MultipartFileRequest extends Request<String> {
    private static  final String TAG = MultipartFileRequest.class.getSimpleName();

    private MultipartEntity mEntity;

    private Response.Listener<String> mListener;

    private Map<String, String> mMultipartMap;

    private Map<String, String> mFileMap;

    private String boundary;

    public MultipartFileRequest(String url,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener,
                            Map<String, String> multipartMap,
                            Map<String, String> fileMap){
        super(Method.POST, url, errorListener);
        boundary = UUID.randomUUID().toString();
        mEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, Charset.forName("UTF-8"));
      //  mEntity = new MultipartEntity(HttpMultipartMode.STRICT, boundary, Charset.forName("UTF-8"));
        mListener = listener;
        mMultipartMap = multipartMap;
        mFileMap = fileMap;

        buildMultipartEntity();
    }

    public void addBody(String key, String value){
        if(mMultipartMap != null)
            mMultipartMap.put(key, value);
    }

    @Override
    public String getBodyContentType() {
        TLog.i(TAG, "-->>> contentType : " + mEntity.getContentType().getValue());
//        return "multipart/form-data; boundary=" + boundary;
        return mEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        byte[] value = bos.toByteArray();
        TLog.i(TAG, "-->>> value : " + value.length);
        return value;
    }

    private void buildMultipartEntity(){
        /**
         * 参数部分
         */
        if(mMultipartMap != null){
            for(Map.Entry<String, String> entry: mMultipartMap.entrySet()){
                try {
                    mEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName("UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * file
         */
        if(mFileMap != null){
            for(Map.Entry<String, String> entry : mFileMap.entrySet()){
                String key = entry.getKey();
                String filePath = entry.getValue();
                TLog.i(TAG, "-->>> filePath : " + filePath);
                String[] str = filePath.split("/");
                String fileName = str[str.length -1];
                TLog.i(TAG, "-->>> fileName : " + fileName);
                try {
                    File file = new File(filePath);
                    TLog.i(TAG,"---------->"+file.length());
                    byte[] data = new byte[(int)file.length()];
                    FileInputStream is = new FileInputStream(file);
                    int c = is.read(data);
                    if(c > 0){
                        TLog.i(TAG,"-----------c----->");
                        mEntity.addPart(entry.getKey(), new ByteArrayBody(data, "image/JPEG", fileName));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    TLog.i(TAG,"---------e-------->"+e);
                }
            }
        }

    }

    @Override
    protected void deliverResponse(String response) {
        if(mListener != null)
            mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("Charset", "UTF-8");
        map.put("Content-Type", "multipart/form-data; boundary=" + boundary);
//        map.put("Accept", "application/json");
        return map;
    }
}
