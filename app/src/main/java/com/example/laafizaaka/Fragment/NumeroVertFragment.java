package com.example.laafizaaka.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.laafizaaka.AproposActivity;
import com.example.laafizaaka.ClassUtile.MyAdapter;
import com.example.laafizaaka.R;

public class NumeroVertFragment extends Fragment {

    private final String[] nom={"Allo terrorism", "Police nationale", "Gendarmerie", "SONABEL", "ONEA"};
    private final int[] logo={R.drawable.burkina_police_municipale, R.drawable.logo_police_nationale,
            R.drawable.le_macaron_de_la_gendarmerie2, R.drawable.sonabel, R.drawable.onea};
    private final int[] img={R.drawable.phone, R.drawable.phone, R.drawable.phone, R.drawable.phone, R.drawable.phone};
    private final String[] num={"17", "17", "17", "17", "17"};
    private TextView BtnNon, BtnOk;
    private androidx.appcompat.app.AlertDialog.Builder builder;
    private ImageView about,negativeBtn, positiveBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View view= inflater.inflate(R.layout.numero_vert_fragment, container, false);

        ListView lv = view.findViewById(R.id.listview);
        MyAdapter adapter= new MyAdapter(getContext(), nom, num, logo, img);
        lv.setAdapter(adapter);

        FrameLayout frameLayout = view.findViewById(R.id.id_container);
        RelativeLayout relativeLayout = view.findViewById(R.id.rlayout);
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
                    String num= "17";
                    ShowDialog(getContext(), num);
                    break;
                case 1:
                    String num1= "17";
                    ShowDialog(getContext(), num1);
                    Toast.makeText(getContext(), "Allo Police", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String num2= "17";
                    ShowDialog(getContext(), num2);
                    Toast.makeText(getContext(), "Allo Gendarmerie", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    String num3= "17";
                    ShowDialog(getContext(), num3);
                    Toast.makeText(getContext(), "Allo SONABEL", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    String num4= "17";
                    ShowDialog(getContext(), num4);
                    Toast.makeText(getContext(), "Allo ONEA", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        about.setOnClickListener(v -> startActivity(new Intent(getContext(), AproposActivity.class)));

        return view;

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    private void ShowDialog(Context context, String num) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog_layout);
        dialog.setCancelable(false);

        TextView btnOui = (TextView) dialog.findViewById(R.id.btnOk);
        TextView btnNon= (TextView) dialog.findViewById(R.id.btnNon);
        // if butnton is clicked, close the custom dialog
        btnOui.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(context, "Vous avez accepter de partager votre position", Toast.LENGTH_SHORT).show();
            PasserLappel(context, num);
        });
        btnNon.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(context, "Vous avez refuser de partager votre position", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    private void PasserLappel(Context context, String num) {
        Intent intent= new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+num));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Veuillez accepter les permissions pour passer les appels", Toast.LENGTH_SHORT).show();
            requestPermission();
        }else {
            startActivity(intent);
        }
    }


}
