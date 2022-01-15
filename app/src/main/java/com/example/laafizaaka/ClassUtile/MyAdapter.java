package com.example.laafizaaka.ClassUtile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.laafizaaka.R;

public class MyAdapter extends ArrayAdapter {
    int[] imageArray, imageArr;
    String[] nom;
    String[] numero;

    public MyAdapter(@NonNull Context context, String[] nom1, String[] numero1, int[] imageArray1, int[] imageArr1) {

        super(context, R.layout.custom_list_numero_vert, R.id.idNom, nom1);
        this.nom= nom1;
        this.numero= numero1;
        this.imageArray= imageArray1;
        this.imageArr= imageArr1;
    }

public View getView(int position, View convertView, ViewGroup parent){

    LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View row= inflater.inflate(R.layout.custom_list_numero_vert, parent, false);

    ImageView Image= row.findViewById(R.id.idPic);
    ImageView img= row.findViewById(R.id.phoneimge);
    TextView Nom= row.findViewById(R.id.idNom);
    TextView Numero= row.findViewById(R.id.idNum);

    Image.setImageResource(imageArray[position]);
    img.setImageResource(imageArr[position]);
    Nom.setText(nom[position]);
    Numero.setText(numero[position]);

        return row;
}
}
