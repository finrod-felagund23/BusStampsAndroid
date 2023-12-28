package com.tirkisovkadyr.whilelearndatastructures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tirkisovkadyr.whilelearndatastructures.services.DBFilesIO;

public class CreateNewStationActivity extends AppCompatActivity {

    private DBFilesIO dbFilesIO = new DBFilesIO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_station);
    }

    public void onConfirmBtnClick(View view) {
        EditText edt = findViewById(R.id.editTextBusStationName);
        String name = String.valueOf(edt.getText());

        dbFilesIO.addBusStation(name);
    }
}