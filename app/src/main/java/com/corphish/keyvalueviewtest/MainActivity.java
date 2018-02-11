package com.corphish.keyvalueviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.corphish.widgets.BottomSheetAlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomSheetAlertDialog.Builder bottomSheetAlertDialog = new BottomSheetAlertDialog.Builder(this);
        bottomSheetAlertDialog.show();
    }

    public String getText() {
        return "Success";
    }
}
