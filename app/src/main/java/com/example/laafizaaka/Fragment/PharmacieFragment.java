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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laafizaaka.AproposActivity;
import com.example.laafizaaka.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class PharmacieFragment extends Fragment {

    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
    private ImageView aboutImg;
    private View bottomSheet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.pharmacie_fragment, container, false);


        frameLayout= view.findViewById(R.id.phar_container);
        relativeLayout= view.findViewById(R.id.phar_rlayout);
        aboutImg= view.findViewById(R.id.about);
        bottomSheet= view.findViewById(R.id.idrelative);

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

        final BottomSheetBehavior behavior= BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Toast.makeText(getContext(), "ouvre", Toast.LENGTH_SHORT).show();
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Toast.makeText(getContext(), "ferme", Toast.LENGTH_SHORT).show();
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Toast.makeText(getContext(), "vers le haut", Toast.LENGTH_SHORT).show();
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Toast.makeText(getContext(), "vers le bat", Toast.LENGTH_SHORT).show();
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Toast.makeText(getContext(), "a gauche", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        return view;
    }
}
