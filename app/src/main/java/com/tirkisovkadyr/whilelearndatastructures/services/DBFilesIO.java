package com.tirkisovkadyr.whilelearndatastructures.services;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tirkisovkadyr.whilelearndatastructures.MainActivity;
import com.tirkisovkadyr.whilelearndatastructures.datasets.BusStampRepr;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBFilesIO extends AppCompatActivity {

    private static final String BUS_STAMPS   = "bus_stamps.json";
    private static final String BUS_STATIONS = "bus_stations.json";

    private static Gson gson = new Gson();

    private AppCompatActivity context;

    public DBFilesIO(AppCompatActivity context) { this.context = context; }

    public void clearFile() {

        System.out.println("DBFilesIO::clearFile::1");
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(BUS_STAMPS, MODE_PRIVATE);
            System.out.println("DBFilesIO::clearFile::2");
            fos.write("".getBytes(StandardCharsets.UTF_8));
            System.out.println("DBFilesIO::clearFile::3");
            Toast.makeText(context, "File has been cleared!", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Saving file
    public void saveBusStamp(int busNumber, String busStation) {

        List<BusStampRepr> stamps = getBusStamps();

        stamps.add(new BusStampRepr(busNumber, busStation, makeDTStamp()));

        FileOutputStream fos = null;

        String json = gson.toJson(stamps);

        try {
            fos = openFileOutput(BUS_STAMPS, MODE_PRIVATE);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(context, "Stamp has been saved", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<BusStampRepr> getBusStamps() {
        FileInputStream fin = null;
        String json = null;

        try {

            fin = openFileInput(BUS_STAMPS);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            json = new String(bytes);
        } catch (IOException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        } finally {

            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        TypeToken<List<BusStampRepr>> typeToken = new TypeToken<List<BusStampRepr>>(){};
        return gson.fromJson(json, typeToken);

    }

    public void saveBusStations(List<String> stations) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(BUS_STATIONS, MODE_PRIVATE);
            fos.write(gson.toJson(stations).getBytes(StandardCharsets.UTF_8));
            Toast.makeText(context, "Stations has been updated", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String makeDTStamp() {

        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd G 'at' HH:mm:ss z");
        String date = df.format(Calendar.getInstance().getTime());

        return date;

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
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {

            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;

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
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        } finally {

            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        TypeToken<List<String>> typeToken = new TypeToken<List<String>>(){};
        return gson.fromJson(json, typeToken);
    }

    public void addBusStation(String name) {
        List<String> stations = getStations();

        stations.add(name);

        saveBusStations(stations);
    }


}
