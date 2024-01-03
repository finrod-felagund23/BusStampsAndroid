package com.tirkisovkadyr.whilelearndatastructures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tirkisovkadyr.whilelearndatastructures.services.DBFilesIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CreateNewStationActivity extends AppCompatActivity {


    private static final String BUS_STAMPS   = "bus_stamps.json";
    private static final String BUS_STATIONS = "bus_stations.json";

    private static Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_station);
    }

    public void onConfirmBtnClick(View view) {
        EditText edt = findViewById(R.id.editTextBusStationName);
        String name = String.valueOf(edt.getText());

        addBusStation(name);

        Intent data = new Intent();
        data.putExtra(ConfirmBusActivity.ACCESS_MESSAGE, name);
        setResult(RESULT_OK, data);
        finish();
    }

    public List<String> getStations() {

        FileInputStream fin = null;
        String json = null;

        try {

            fin = openFileInput(BUS_STATIONS);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            json = new String(bytes);
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        } finally {

            try {
                if (fin != null) {
                    System.out.println("CreateNewStationActivity::getStation::fin.close_0");
                    fin.close();
                    System.out.println("CreateNewStationActivity::getStation::fin.close_1");
                }
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        TypeToken<List<String>> typeToken = new TypeToken<List<String>>(){};
        List<String> lst = gson.fromJson(json, typeToken);
        return (lst != null) ? lst: new ArrayList<>();
    }

    public void addBusStation(String name) {
        List<String> stations = getStations();

        stations.add(name);

        saveBusStations(stations);
    }

    public void saveBusStations(List<String> stations) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(BUS_STATIONS, MODE_PRIVATE);
            fos.write(gson.toJson(stations).getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Stations has been updated", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) {
                    System.out.println("CreateNewActivity::saveBusStations::fos.close_0");
                    fos.close();
                    System.out.println("CreateNewActivity::saveBusStations::fos.close_0");
                }
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}