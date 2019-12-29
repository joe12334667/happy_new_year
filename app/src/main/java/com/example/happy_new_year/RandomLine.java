package com.example.happy_new_year;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RandomLine extends AppCompatActivity {

    private Button bt_random;
    private RadioGroup rg_random;
    private TextView tv_show;
    private static final int
            it_title = Menu.FIRST,
            it_record = Menu.FIRST+1,
            it_main = Menu.FIRST+2,
            it_random_line = Menu.FIRST+3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_line);

        rg_random = findViewById(R.id.rg_random);
        bt_random = findViewById(R.id.bt_random);
        bt_random.setEnabled(false);
        tv_show = findViewById(R.id.tv_show);

        rg_random.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                bt_random.setEnabled(true);
            }
        });

        bt_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (rg_random.getCheckedRadioButtonId()){
                    case R.id.rb_1:
                        tv_show.setText(getResources().getStringArray(R.array.old)[(int) Math.round(Math.random()*(6-1))]);
                        break;
                    case R.id.rb_2:
                        tv_show.setText(getResources().getStringArray(R.array.new_child)[(int) Math.round(Math.random()*(5-1))]);
                        break;

                }

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

            case it_title:
                intent = new Intent(RandomLine.this, index.class);
                startActivity(intent);
                mes = "前往首頁";
                this.finish();
                break;
            case it_main:

                intent = new Intent(RandomLine.this, MainActivity.class);
                startActivity(intent);
                mes = "前往紀錄紅包";
                this.finish();
                break;
            case it_record:
                intent = new Intent(RandomLine.this, record.class);
                startActivity(intent);
                mes = "前往紅包紀錄";
                this.finish();
                break;
            case it_random_line:
                intent = new Intent(RandomLine.this, RandomLine.class);
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
