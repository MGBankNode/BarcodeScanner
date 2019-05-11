package com.example.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CustomScannerActivity.class);
        integrator.initiateScan();
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("NKW", "onActivityResult: .");
        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            String re = scanResult.getContents();
            //barcodeId = Integer.parseInt(re);
            Log.d("NKW", "onActivityResult: ." + re);
            Toast.makeText(this, re, Toast.LENGTH_LONG).show();


            //바코드번호 뿌려주기

        }
        //바코드번호 받은후 조회 버튼 클릭(디비에서 사용자 찾기)
        // 커스텀다이얼로그 -> 사용자 누적 포인트 받아오기
        // -> 적립버튼 활성화(적립)
        // -> 디비로 넘겨주기


    }*/


}
