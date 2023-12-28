package com.tirkisovkadyr.whilelearndatastructures;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tirkisovkadyr.whilelearndatastructures.services.DBFilesIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String FILE_NAME = "dump.txt";
    private DBFilesIO dbFilesIO = new DBFilesIO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClickReadFromFileBtn(View view) {
        Intent intent = new Intent(this, ReadFromFileActivity.class);
        startActivity(intent);
    }

    public void onClickBusBtn(View view) {
        Button btn = (Button) view;

        Intent intent = new Intent(this, ConfirmBusActivity.class);
        intent.putExtra("busNumber", (String) btn.getText());
        startActivity(intent);

    }

    public void onClickDeleteFileBtn(View view) {
        dbFilesIO.clearFile();
    }




}