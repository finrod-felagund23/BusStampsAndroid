package com.tirkisovkadyr.whilelearndatastructures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tirkisovkadyr.whilelearndatastructures.services.DBFilesIO;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadFromFileActivity extends AppCompatActivity {

    private DBFilesIO dbFilesIO = new DBFilesIO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_from_file);

        TextView fileTextView = findViewById(R.id.fileTextView);

        fileTextView.setText(dbFilesIO.readFromFile());

    }


}