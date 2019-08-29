package com.leonyr.easyutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.leonyr.lib.utils.LogUtil;
import com.leonyr.view.colorpicker.dialog.ColorPickerDialogFragment;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMain;
    MAdapter mAdapter;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MAdapter();
        rvMain.setAdapter(mAdapter);

        random = new Random();

        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogFragment pickerDialogFragment = new ColorPickerDialogFragment();
                pickerDialogFragment.setmListener(new ColorPickerDialogFragment.ColorPickerDialogListener() {
                    @Override
                    public void onColorSelected(int color) {
                        LogUtil.e("color", "1111--" + color);
                    }
                });
                pickerDialogFragment.show(getSupportFragmentManager(), "color");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("color", "1111");
    }
}
