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














}
