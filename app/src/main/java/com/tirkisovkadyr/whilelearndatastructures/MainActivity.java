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
    private static final String BUS_STAMPS   = "bus_stamps.json";
    private static final String BUS_STATIONS = "bus_stations.json";

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
        clearFile();
    }

    public void clearFile() {

        System.out.println("DBFilesIO::clearFile::1");
        FileOutputStream fos = null;
        FileOutputStream fos_ = null;

        try {
            fos = openFileOutput(BUS_STAMPS, MODE_PRIVATE);
            fos_ = openFileOutput(BUS_STATIONS, MODE_PRIVATE);
            System.out.println("DBFilesIO::clearFile::2");
            fos.write("".getBytes(StandardCharsets.UTF_8));
            fos_.write("".getBytes(StandardCharsets.UTF_8));
            System.out.println("DBFilesIO::clearFile::3");
            Toast.makeText(this, "File has been cleared!", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fos_ != null) {
                    fos_.close();
                }

            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




}