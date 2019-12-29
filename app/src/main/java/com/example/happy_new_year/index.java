package com.example.happy_new_year;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class index extends AppCompatActivity {

    private Button button1 , button2 , button3;
    private static final int
            it_title = Menu.FIRST,
            it_record = Menu.FIRST+1,
            it_main = Menu.FIRST+2,
            it_random_line = Menu.FIRST+3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(index.this, MainActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(index.this, record.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(index.this, RandomLine.class);
                startActivity(intent);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){


        menu.add(0  , it_title , Menu.NONE , "首頁");
        menu.add(0  , it_main , Menu.NONE , "紀錄");
        menu.add(0  , it_record , Menu.NONE , "顯示");
        menu.add(0  , it_random_line , Menu.NONE , "祝賀話!");

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String mes = "";
        Intent intent;

        switch (item.getItemId()){

            case it_main:
                 intent = new Intent(index.this, MainActivity.class);
                startActivity(intent);
                mes = "前往紀錄紅包";
                this.finish();
                break;
            case it_record:

                 intent = new Intent(index.this, record.class);
                startActivity(intent);
                this.finish();
                mes = "前往紅包紀錄";
                break;
            case it_title:
                 intent = new Intent(index.this, index.class);
                startActivity(intent);
                mes = "前往首頁";
                this.finish();
                break;
            case it_random_line:
                intent = new Intent(index.this, RandomLine.class);
                startActivity(intent);
                mes = "前往隨機祝賀話";
                this.finish();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        Toast.makeText(this , mes,  Toast.LENGTH_SHORT).show();
        return true;
    }
}
