package com.tirkisovkadyr.whilelearndatastructures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tirkisovkadyr.whilelearndatastructures.services.DBFilesIO;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadFromFileActivity extends AppCompatActivity {


    private static final String BUS_STAMPS   = "bus_stamps.json";
    private static final String BUS_STATIONS = "bus_stations.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_from_file);

        TextView fileTextView = findViewById(R.id.fileTextView);

        fileTextView.setText(readFromFile());

    }

    public String readFromFile() {

        FileInputStream fin = null;

        try {

            fin = openFileInput(BUS_STAMPS);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return text;

        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {

            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;

    }


}