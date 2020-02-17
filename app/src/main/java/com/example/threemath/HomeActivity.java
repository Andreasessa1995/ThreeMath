package com.example.threemath;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button bCategoria1;
    private Button bCategoria2;
    private Button bCategoria3;
    private Button bCategoria4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }



    public void onClickCategoria (View v ){
        bCategoria1 = (Button) findViewById(R.id.bottoneCategoriaDomanda1);
        bCategoria2 = (Button) findViewById(R.id.bottoneCategoriaDomanda2);
        bCategoria3 = (Button) findViewById(R.id.bottoneCategoriaDomanda3);
        bCategoria4 = (Button) findViewById(R.id.bottoneCategoriaDomanda4);

        if (bCategoria1.isPressed()){
            /*new intent*/
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), AddizioniActivity.class);
            startActivityForResult(intent, 0);

        }
        if (bCategoria2.isPressed()){
            /*apri sott*/

        }
        if (bCategoria3.isPressed()){
            /*apri divis*/

        }
        if (bCategoria4.isPressed()){
            /*apri molti*/

        }


    }





}
