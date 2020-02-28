package com.example.threemath;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Categoria> {

    private LayoutInflater inflater;
    private int resource=0;
    int punteggio = 0;
    Double dpunteggio = 0.0;


    public CustomAdapter (Context context, int resourceId, List<Categoria> object){
        super(context,resourceId,object);
        resource = resourceId;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public View getView( int position,View v, ViewGroup parent) {
        if (null == v) {
            Log.d("DEBUG", "Inflating view");
            v = inflater.inflate(R.layout.elemento_categoria, null);
        }
        Categoria c = getItem(position);

        TextView testoCategoria ;
        testoCategoria = (TextView) v.findViewById(R.id.textCategoria);
        testoCategoria.setText(""+c.getcategoria());





        if(position>=7){
            dpunteggio = (Double) c.getPunteggioDouble();
            TextView testoPunteggio ;
            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText(""+dpunteggio+"%");
        }else {
            punteggio = (int) c.getPunteggio();
            TextView testoPunteggio ;
            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText(""+punteggio);
        }






        return v;
    }


}
