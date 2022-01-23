package com.b12.contacts.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.b12.contacts.R;

public class ContactInfoFragment extends Fragment {
    private TextView name, number, letter;
    private ImageView imageView;
    Button call, sms;
    RelativeLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_info_layout, container, false);
        name = view.findViewById(R.id.tv_nameF);
        number = view.findViewById(R.id.tv_numberF);
        letter = view.findViewById(R.id.tv_letterF);
        imageView = view.findViewById(R.id.iv_imageF);
        call = view.findViewById(R.id.btn_call);
        sms = view.findViewById(R.id.btn_send_sms);
        layout = view.findViewById(R.id.layout_0);
        layout.setVisibility(View.GONE);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    public void displayData(@NonNull String nameS, String numberS, int imageS) {

        layout.setVisibility(View.VISIBLE);
        char l = nameS.charAt(0);
        name.setText(nameS);
        number.setText(numberS);
        letter.setText("" + l);
        imageView.setImageResource(imageS);
    }


}
