package com.example.threemath;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeActivity2 extends AppCompatActivity {
    private Button bCategoria1;
    private Button bCategoria2;
    private Button bCategoria3;
    private Button bCategoria4;

    /** lista **/
    ListView lista;
    CustomAdapter customAdapter;


    /**
     * suoni
     **/
    MediaPlayer mpBat;
    ArrayList<Categoria> listaCategoria = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_2);
        Categoria c = new Categoria ("addizioni");



        /**creazione lista **/
        lista= findViewById(R.id.mylist);
        customAdapter = new CustomAdapter(this, R.layout.elemento_categoria, new ArrayList<Categoria>());
        lista.setAdapter(customAdapter);
        listaCategoria.add(c);
        customAdapter.add(c);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Categoria a = (Categoria) customAdapter.getItem(position);
                String  str  = lista.getItemAtPosition(position).toString();
                AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity2.this);
                b.setTitle("Delete !!!!");
                b.setMessage("Sei sicuro di volere eliminare  ? ");
                b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        customAdapter.remove(a);
                        customAdapter.notifyDataSetChanged();
                    }
                });
                b.setNegativeButton("NO",null);
                AlertDialog al = b.create();
                al.show();
            }
        });


        /***suoni**/
       // mpBat = MediaPlayer.create(this, R.raw.bat);


    }


   /** public void onClickCategoria(View v) {
        bCategoria1 = (Button) findViewById(R.id.bottoneCategoriaDomanda1);
        bCategoria2 = (Button) findViewById(R.id.bottoneCategoriaDomanda2);
        bCategoria3 = (Button) findViewById(R.id.bottoneCategoriaDomanda3);
        bCategoria4 = (Button) findViewById(R.id.bottoneCategoriaDomanda4);

        if (bCategoria1.isPressed()) {

            startBattuta();
            releaseResourcesBattuta();

            /*new intent*/
            // Intent intent = new Intent();
            // intent.setClass(getApplicationContext(), AddizioniActivity.class);
         /**   Intent intent = new Intent(getApplicationContext(), AddizioniActivity.class);
            startActivityForResult(intent, 0);

        }
        if (bCategoria2.isPressed()) {
            /*apri sott*/

       /** }
        if (bCategoria3.isPressed()) {
            /*apri divis*/

     /**   }
        if (bCategoria4.isPressed()) {
            /*apri molti*/

    /**    }


    }**/

    /**
     * player campanella e bat
     */
   /** public void startBattuta() {
        mpBat.start();
    }

    public void releaseResourcesBattuta() {
        mpBat.release();
    }*/


}
