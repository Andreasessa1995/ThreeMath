package com.example.threemath;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class CustomAdapter extends ArrayAdapter<Categoria> {

    private LayoutInflater inflater;
    private int resource = 0;
    int punteggio = 0;
    Double dpunteggio = 0.0;


    public CustomAdapter(Context context, int resourceId, List<Categoria> object) {
        super(context, resourceId, object);
        resource = resourceId;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (null == v) {
            Log.d("DEBUG", "Inflating view");
            v = inflater.inflate(R.layout.elemento_categoria, null);
        }
        Categoria c = getItem(position);

        TextView testoCategoria;
        testoCategoria = (TextView) v.findViewById(R.id.textCategoria);
        testoCategoria.setText("" + c.getcategoria());

        TextView testoPunteggio;

        if (testoCategoria.getText().toString().equalsIgnoreCase("Addizioni")) {
            testoCategoria.setTextColor(Color.YELLOW);
            punteggio = (int) c.getPunteggio();

            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText("" + punteggio);
            testoPunteggio.setTextColor(Color.YELLOW);

        } else if (testoCategoria.getText().toString().equalsIgnoreCase("Sottrazioni")) {
            testoCategoria.setTextColor(Color.YELLOW);
            punteggio = (int) c.getPunteggio();

            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText("" + punteggio);
            testoPunteggio.setTextColor(Color.YELLOW);

        } else if (testoCategoria.getText().toString().equalsIgnoreCase("Moltiplicazioni")) {
            testoCategoria.setTextColor(Color.YELLOW);
            punteggio = (int) c.getPunteggio();

            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText("" + punteggio);
            testoPunteggio.setTextColor(Color.YELLOW);

        } else if (testoCategoria.getText().toString().equalsIgnoreCase("Divisioni")) {
            testoCategoria.setTextColor(Color.YELLOW);
            punteggio = (int) c.getPunteggio();

            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText("" + punteggio);
            testoPunteggio.setTextColor(Color.YELLOW);

        }else if (testoCategoria.getText().toString().equalsIgnoreCase("Indovinelli")) {
            testoCategoria.setTextColor(Color.YELLOW);
            punteggio = (int) c.getPunteggio();

            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText("" + punteggio);
            testoPunteggio.setTextColor(Color.YELLOW);

        }else   {
            testoCategoria.setTextColor(Color.WHITE);
            punteggio = (int) c.getPunteggio();

            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText("" + punteggio);
            testoPunteggio.setTextColor(Color.WHITE);
        }



        if (position >= 17) {

            dpunteggio = (Double) c.getPunteggioDouble();
            String s = String.format(Locale.getDefault(), "%.2f", dpunteggio);
            testoPunteggio = (TextView) v.findViewById(R.id.textPunteggio);
            testoPunteggio.setText("" + s + "%");

        }


        return v;
    }


}
