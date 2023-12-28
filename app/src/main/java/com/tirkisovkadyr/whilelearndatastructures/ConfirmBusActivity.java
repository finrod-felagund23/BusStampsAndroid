package com.tirkisovkadyr.whilelearndatastructures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tirkisovkadyr.whilelearndatastructures.services.DBFilesIO;

import java.util.List;

public class ConfirmBusActivity extends AppCompatActivity {

    private final DBFilesIO dbFilesIO = new DBFilesIO(this);
    private final Bundle arguments = getIntent().getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_bus);



        LinearLayout linearLayout = findViewById(R.id.linear_layout_confirm_bus);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(100, 20, 100, 0);

        List<String> stations = dbFilesIO.getStations();

        for (String station : stations) {
            Button btn = new Button(this);

            btn.setText(station);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
            btn.setOnClickListener((View view) -> {

                dbFilesIO.saveBusStamp(
                        Integer.parseInt( arguments.get("busNumber").toString()
                        ),
                        (String) btn.getText()

                );
            });
            btn.setLayoutParams(layoutParams);
            linearLayout.addView(btn);
        }

        Button btn = new Button(this);
        btn.setText("Create new Station +");
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        btn.setLayoutParams(layoutParams);
        btn.setOnClickListener(this::createNewStation);

        linearLayout.addView(btn);


    }

    public void createNewStation(View view) {
        Intent intent = new Intent(this, CreateNewStationActivity.class);
        startActivity(intent);
    }


}