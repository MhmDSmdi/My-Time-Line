package com.blucode.mhmd.timeline.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blucode.mhmd.timeline.R;

public class HomeFragment extends Fragment {

    private Button btnShareContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        btnShareContent = v.findViewById(R.id.btn_home_shareContent);
        btnShareContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShareMessageActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
