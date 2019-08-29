/*
 * Copyright (C) 2015 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leonyr.view.colorpicker.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.leonyr.view.R;
import com.leonyr.view.colorpicker.view.ColorPanelView;
import com.leonyr.view.colorpicker.view.ColorPickerView;

public class ColorPickerDialogFragment extends DialogFragment {

    public static final String COLOR_PICKER_RESULT_COLOR = "color_picker_result_color";

    private ColorPickerView mColorPicker;
    private ColorPanelView mOldColorPanel;
    private ColorPanelView mNewColorPanel;
    private Button mOkButton;

    public ColorPickerDialogListener mListener;

    public interface ColorPickerDialogListener {
        void onColorSelected(int color);
    }

    public static ColorPickerDialogFragment newInstance(int initialColor) {
        return newInstance(null, null, initialColor, false);
    }

    public static ColorPickerDialogFragment newInstance(
            String title, String okButtonText, int initialColor, boolean showAlphaSlider) {

        ColorPickerDialogFragment frag = new ColorPickerDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("ok_button", okButtonText);
        args.putBoolean("alpha", showAlphaSlider);
        args.putInt("init_color", initialColor);

        frag.setArguments(args);

        return frag;
    }

    public void setmListener(ColorPickerDialogListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);

        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        return d;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.colorpicker_dialog, container);


        TextView titleView = (TextView) v.findViewById(android.R.id.title);
        mColorPicker = (ColorPickerView) v.findViewById(R.id.ColorPicker_color_picker_view);
        mOldColorPanel = (ColorPanelView) v.findViewById(R.id.ColorPicker_color_panel_old);
        mNewColorPanel = (ColorPanelView) v.findViewById(R.id.ColorPicker_color_panel_new);
        mOkButton = (Button) v.findViewById(android.R.id.button1);

        mColorPicker.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {

            @Override
            public void onColorChanged(int newColor) {
                mNewColorPanel.setColor(newColor);
            }
        });

        mOkButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mListener){
                    mListener.onColorSelected(mNewColorPanel.getColor());
                }
                dismiss();
            }

        });

        Bundle bundle = getArguments();
        if (null == bundle) {
            return v;
        }

        String title = bundle.getString("title");
        if (title != null) {
            titleView.setText(title);
        } else {
            titleView.setVisibility(View.GONE);
        }

        if (savedInstanceState == null) {
            mColorPicker.setAlphaSliderVisible(bundle.getBoolean("alpha"));

            String ok = bundle.getString("ok_button");
            if (ok != null) {
                mOkButton.setText(ok);
            }
            int initColor = bundle.getInt("init_color");

            mOldColorPanel.setColor(initColor);
            mColorPicker.setColor(initColor, true);
        }

        return v;
    }
}
