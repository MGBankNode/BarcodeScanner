package com.example.barcodescanner;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Point_CustomDialog extends Dialog{
    private Context context;

    //해쉬맵으로 바코드 번호-> 사용자 이름, 포인트 받아오기    //DB필요 : 사용자 이름, 바코드 번호, 적립 포인트
    private String myPoint = "";

    public interface ICustomDialogEventListener{
        void customDialogEvent(int finish_flag);
    }
    private ICustomDialogEventListener onCustomDialogEventListener;

    public Point_CustomDialog(Context context,ICustomDialogEventListener onCustomDialogEventListener){
        super(context);
        this.context=context;
        this.onCustomDialogEventListener = onCustomDialogEventListener;
    }
    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final String barcode) { //파라미터에 textview와 같은것을 넘겨받을수  잇음

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.point_customdialog);

        // 커스텀 다이얼로그 사이즈를 조정한다.
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dlg.getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = dlg.getWindow();
        window.setAttributes(lp);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button saveButton = (Button) dlg.findViewById(R.id.saveButton);
        final TextView user_name = dlg.findViewById(R.id.user_name);
        final TextView barcode_num = dlg.findViewById(R.id.barcode_num);
        final TextView my_point = dlg.findViewById(R.id.my_point);

        final BarcodeInfo barcodeInfo = new BarcodeInfo(barcode, getContext());
        barcodeInfo.BarcodeInfoRequestHandler(new BarcodeInfo.VolleyCallback() {
            @Override
            public void onSuccess(String userName, String curPoint) {
                //정보 불러오기
                barcode_num.setText(barcode);
                user_name.setText(userName);

                myPoint = curPoint;
                String pointText = myPoint + "p";
                my_point.setText(myPoint);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '적립' 버튼 클릭시
                onCustomDialogEventListener.customDialogEvent(1);
                String addPoint = "100";

                //포인트 적립 후 서버로 값 전달 코드 필요-> 업데이트
                barcodeInfo.AddBarcodeRequestHandler(addPoint, new BarcodeInfo.VolleyCallback() {
                    @Override
                    public void onSuccess(String userName, String curPoint) {
                        //정보 불러오기
                        String pointText = curPoint + "p";
                        my_point.setText(pointText);
                        myPoint = curPoint;
                    }
                });

            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "다이얼로그 닫기.", Toast.LENGTH_SHORT).show();

                onCustomDialogEventListener.customDialogEvent(0);
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });


    }
}
