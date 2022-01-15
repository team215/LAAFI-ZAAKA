package com.example.laafizaaka.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laafizaaka.AproposActivity;
import com.example.laafizaaka.R;

public class PharmacieFragment extends Fragment {

    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
    private ImageView aboutImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.pharmacie_fragment, container, false);


        frameLayout= view.findViewById(R.id.phar_container);
        relativeLayout= view.findViewById(R.id.phar_rlayout);
        aboutImg= view.findViewById(R.id.about);

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            frameLayout.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }

        aboutImg.setOnClickListener(v -> startActivity(new Intent(getContext(), AproposActivity.class)));

        return view;
    }
}
