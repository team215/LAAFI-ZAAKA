package com.example.laafizaaka.Fragment;

import android.accessibilityservice.AccessibilityService;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laafizaaka.AproposActivity;
import com.example.laafizaaka.ClassUtile.MyAdapter;
import com.example.laafizaaka.R;

public class NumeroVertFragment extends Fragment {

    private String[] nom={"Allo terrorism", "Police nationale", "Gendarmerie", "SONABEL", "ONEA"};
    private int[] logo={R.drawable.burkina_police_municipale, R.drawable.logo_police_nationale,
            R.drawable.le_macaron_de_la_gendarmerie2, R.drawable.sonabel, R.drawable.onea};
    private int[] img={R.drawable.phone, R.drawable.phone, R.drawable.phone, R.drawable.phone, R.drawable.phone};
    private String[] num={"17", "17", "17", "17", "17"};
    private ListView lv;
    private TextView BtnNon, BtnOk;
    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
    private androidx.appcompat.app.AlertDialog.Builder builder;
    private ImageView about,negativeBtn, positiveBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View view= inflater.inflate(R.layout.numero_vert_fragment, container, false);

         lv= view.findViewById(R.id.listview);
        MyAdapter adapter= new MyAdapter(getContext(), nom, num, logo, img);
        lv.setAdapter(adapter);

        frameLayout= view.findViewById(R.id.id_container);
        relativeLayout= view.findViewById(R.id.rlayout);
        about= view.findViewById(R.id.about_num);

        //Vérification de la disponibilité de la connexion
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
           frameLayout.setVisibility(View.VISIBLE);

        }else {
            frameLayout.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }

        lv.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position){
                case 0:
                    ShowDialog(getContext(), negativeBtn, positiveBtn);
                    break;
                case 1:
                    Toast.makeText(getContext(), "Allo Police", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "Allo Gendarmerie", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "Allo SONABEL", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getContext(), "Allo ONEA", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        about.setOnClickListener(v -> startActivity(new Intent(getContext(), AproposActivity.class)));

        return view;

    }

    private void ShowDialog(Context context, ImageView negativeBtn, ImageView positiveBtn) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView= inflater.inflate(R.layout.alert_dialog_layout, null);

        builder.setView(mView);
    }


}
