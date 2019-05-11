package com.example.barcodescanner;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BarcodeInfo {

    private Context context;
    private String barcode;
    private String userName;
    private String curPoint;
    private String addPoint;

    BarcodeInfo(String barcode, Context context){

        this.barcode = barcode;
        this.context = context;

    }

    public interface VolleyCallback{
        void onSuccess(String userName, String curPoint);
    }

    //바코드 사용자 정보 얻어오기 요청
    public void BarcodeInfoRequestHandler(final VolleyCallback callback){
        RequestInfo requestInfo = new RequestInfo(RequestInfo.RequestType.BARCODE_INFO);

        String url = "http://" + requestInfo.GetRequestIP() + ":" + requestInfo.GetRequestPORT() + requestInfo.GetProcessURL();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        BarcodeInfoResponse(response, callback);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                return BarcodeInfoRequest();
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);
        Log.d("요청 url: ", url);

    }

/*
        BarcodeInfoRequest(): Map<String, String>
        = 바코드 사용자 정보 요청 전달 파라미터 설정 함수
    */

    private Map<String, String> BarcodeInfoRequest(){

        Map<String, String> params = new HashMap<>();

        params.put("barcode", barcode);
        return params;

    }

    /*
        BarcodeInfoResponse(String, final VolleyCallback): void
        = 바코드 사용자 정보 요청 응답 처리 함수
    */

    private void BarcodeInfoResponse(String response, final VolleyCallback callback){
        try{
            Log.d("onResponse 호출 ", response);

            JSONObject json = new JSONObject(response);
            String resultString = (String) json.get("message");

            switch (resultString) {
                case "success":
                    JSONObject data = json.getJSONObject("data");

                    userName = (String) data.get("name");
                    curPoint = (String) data.get("point");

                    callback.onSuccess(userName, curPoint);
                    break;

                case "error":
                    break;

                case "db_fail":
                    break;

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //바코드 적립 요청
    public void AddBarcodeRequestHandler(String addPoint, final VolleyCallback callback){

        this.addPoint = addPoint;
        RequestInfo requestInfo = new RequestInfo(RequestInfo.RequestType.ADD_POINT);

        String url = "http://" + requestInfo.GetRequestIP() + ":" + requestInfo.GetRequestPORT() + requestInfo.GetProcessURL();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        AddBarcodeResponse(response, callback);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                return AddBarcodeRequest();
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);
        Log.d("요청 url: ", url);

    }

/*
        AddBarcodeRequest(): Map<String, String>
        = 바코드 적립 요청 전달 파라미터 설정 함수
    */

    private Map<String, String> AddBarcodeRequest(){

        Map<String, String> params = new HashMap<>();

        params.put("barcode", barcode);
        params.put("point", addPoint);
        return params;

    }

    /*
        AddBarcodeResponse(String, final VolleyCallback): void
        = 바코드 적립 요청 응답 처리 함수
    */

    private void AddBarcodeResponse(String response, final VolleyCallback callback){
        try{
            Log.d("onResponse 호출 ", response);

            JSONObject json = new JSONObject(response);
            String resultString = (String) json.get("message");

            switch (resultString) {
                case "success":
                    callback.onSuccess(userName,String.valueOf(Integer.parseInt(curPoint) + Integer.parseInt(addPoint)));
                    break;

                case "error":
                    break;

                case "db_fail":
                    break;

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
