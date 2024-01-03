package com.tirkisovkadyr.whilelearndatastructures;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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


public class ConfirmBusActivity extends AppCompatActivity {

    private Bundle arguments;

    private static final String BUS_STAMPS   = "bus_stamps.json";
    private static final String BUS_STATIONS = "bus_stations.json";

    private static final Gson gson = new Gson();

    public static final int LAUNCH_CREATE_NEW_STATION_ACTIVITY = 1;

    public static final String ACCESS_MESSAGE = "ACCESS_MESSAGE";
//    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//
//            }
//    );


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_bus);
        arguments = getIntent().getExtras();

        LinearLayout linearLayout = findViewById(R.id.linear_layout_confirm_bus);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.TOP;
        layoutParams.setMargins(100, 20, 100, 0);

        List<String> stations = getStations();

        for (String station : stations) {
            Button btn = new Button(this);

            btn.setText(station);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            btn.setOnClickListener((View view) -> saveBusStamp(
                    Integer.parseInt(arguments.get("busNumber").toString()
                    ),
                    (String) btn.getText()

            ));
            btn.setLayoutParams(layoutParams);
            linearLayout.addView(btn);
        }

        LinearLayout.LayoutParams createNewStationLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        createNewStationLayoutParams.gravity = Gravity.BOTTOM;
        createNewStationLayoutParams.setMargins(100, 20, 100, 0);

        Button btn = new Button(this);
        btn.setId(R.id.create_new_station_btn);
        btn.setText("Create new Station +");
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        btn.setLayoutParams(createNewStationLayoutParams);
        btn.setOnClickListener(this::createNewStation);


        linearLayout.addView(btn);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (data.getResultCode() == Activity.RESULT_CANCELED) {
//            Toast.makeText(ConfirmBusActivity.this, "Creating new station - Screwed up", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (requestCode == LAUNCH_CREATE_NEW_STATION_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String stationName = data.getStringExtra(ACCESS_MESSAGE);


                LinearLayout linearLayout = findViewById(R.id.linear_layout_confirm_bus);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.gravity = Gravity.TOP;
                layoutParams.setMargins(100, 20, 100, 0);

                Button btn = new Button(ConfirmBusActivity.this);
                btn.setText(stationName);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                btn.setOnClickListener((View view) -> saveBusStamp(
                        Integer.parseInt(arguments.get("busNumber").toString()
                        ),
                        (String) btn.getText()

                ));
                btn.setLayoutParams(layoutParams);
                linearLayout.addView(btn);

                Button createNewStationBtn = findViewById(R.id.create_new_station_btn);
//                btn.setId(R.id.create_new_station_btn);
//                btn.setText("Create new Station +");
//                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                btn.setLayoutParams(createNewStationLayoutParams);
//                btn.setOnClickListener(this::createNewStation);


                linearLayout.removeView(createNewStationBtn);
                linearLayout.addView(createNewStationBtn);

            }
        }
    }

    public void createNewStation(View view) {
        Intent intent = new Intent(this, CreateNewStationActivity.class);
        startActivityForResult(intent, LAUNCH_CREATE_NEW_STATION_ACTIVITY);
    }

    public List<String> getStations() {

        FileInputStream fin = null;
        String json;

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
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {};
        List<String> lst = gson.fromJson(json, typeToken);
        return (lst != null) ? lst: new ArrayList<>();
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
            Toast.makeText(this, "Stamp has been saved", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<BusStampRepr> getBusStamps() {
        FileInputStream fin = null;
        String json;

        try {

            fin = openFileInput(BUS_STAMPS);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            json = new String(bytes);
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        } finally {

            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        TypeToken<List<BusStampRepr>> typeToken = new TypeToken<List<BusStampRepr>>() {
        };
        List<BusStampRepr> lst = gson.fromJson(json, typeToken);
        return (lst != null) ? lst: new ArrayList<>();
    }

    public String makeDTStamp() {

        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd G 'at' HH:mm:ss z");

        return df.format(Calendar.getInstance().getTime());

    }
}