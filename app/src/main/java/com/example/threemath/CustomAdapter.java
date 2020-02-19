package com.example.threemath;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Categoria> {

    private LayoutInflater inflater;
    private int resource=0;


    public CustomAdapter (Context context, int resourceId, List<Categoria> object){
        super(context,resourceId,object);
        resource = resourceId;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (null == v) {
            Log.d("DEBUG", "Inflating view");
            v = inflater.inflate(R.layout.elemento_categoria, null);
        }
        Button categoria ;
        Categoria c =getItem(position);
        categoria = (Button) v.findViewById(R.id.bottoneCategoria);
        categoria .setText("b"+c.getcategoria());

        return v;
    }


}
