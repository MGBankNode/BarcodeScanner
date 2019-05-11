package com.example.barcodescanner;

public class RequestInfo {

    public enum RequestType{
        BARCODE_INFO,
        ADD_POINT
    }

    private final static String myIP = "ec2-13-124-68-124.ap-northeast-2.compute.amazonaws.com";
    private final static String myPort = "1119";
    private RequestType myRequestType;

    RequestInfo(RequestType requestType){
        this.myRequestType = requestType;
    }

    public String GetRequestIP(){
        return myIP;
    }

    public String GetRequestPORT(){
        return myPort;
    }

    public String GetProcessURL(){
        String processURL = "";

        switch (myRequestType){

            case BARCODE_INFO:
                processURL = "/nodeapi/barcode/barcodeinfo";
                break;

            case ADD_POINT:
                processURL = "/nodeapi/barcode/addpoint";
                break;
        }
        return processURL;
    }

}