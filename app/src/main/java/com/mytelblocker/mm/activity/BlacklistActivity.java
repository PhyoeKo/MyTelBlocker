/*
 * Copyright © Ricki Hirner (bitfire web engineering).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package com.mytelblocker.mm.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.mytelblocker.mm.R;
import com.mytelblocker.mm.adapter.MilitaryDataListAdapter;
import com.mytelblocker.mm.model.DataHeader;
import com.mytelblocker.mm.model.MilitaryData;
import com.mytelblocker.mm.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

public class BlacklistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MilitaryDataListAdapter militaryDataListAdapter = new MilitaryDataListAdapter();
    RecyclerView rvList;
    private ArrayList<Object> fuckingMilitaryDataList = new ArrayList<>();
    ToggleButton toggleService;
    AppPreference appPreference;
    Spinner spCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        requestPermissions();
        toggleService = findViewById(R.id.toggle);
        rvList = findViewById(R.id.rv_list);
        spCategory = findViewById(R.id.sp_category);
        rvList.setAdapter(militaryDataListAdapter);
        appPreference = new AppPreference(getApplicationContext());
        loadSpinner();

        loadDefault();

        toggleService.setChecked(appPreference.getServiceStatus());
        toggleService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPreference.setServiceStatus(isChecked);
            }
        });

        spCategory.setOnItemSelectedListener(this);
    }

    private void loadSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.testArray));
        spCategory.setAdapter(dataAdapter);
    }

    protected void requestPermissions() {
        List<String> requiredPermissions = new ArrayList<>();
        requiredPermissions.add(Manifest.permission.CALL_PHONE);
        requiredPermissions.add(Manifest.permission.READ_PHONE_STATE);
        requiredPermissions.add(Manifest.permission.READ_CALL_LOG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requiredPermissions.add(Manifest.permission.ANSWER_PHONE_CALLS);
        }
        List<String> missingPermissions = new ArrayList<>();

        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        if (!missingPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    missingPermissions.toArray(new String[0]), 0);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean ok = true;
        if (grantResults.length != 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    ok = false;
                    break;
                }
            }
        } else {
            // treat cancellation as failure
            ok = false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fuckingMilitaryDataList.clear();
        String selected = (String) parent.getSelectedItem();
        switch (selected) {
            case "Banking and finance":
                loadBankingServices();
                break;
            case "Cigarettes":
                loadCigarettes();
                break;
            case "Communications":
                loadCommunications();
                break;
            case "Construction":
                loadConstruction();
                break;

            case "Entertainment/Tourism":
                loadEntertainment();
                break;

            case "Food and drink":
                loadFoodAndDrink();
                break;
            case "Health and Beauty Products":
                loadHealthAndBeauty();
                break;

            case "Industrial Estates/Offices":
                loadIndustrial();
                break;
            case "International Trade":
                loadInternationalTrade();
                break;
            case "Manufacturing":
                loadManufacturing();
                break;
            case "Media":
                loadMedia();
                break;
            case "Retail":
                loadRetail();
                break;
            case "Trading companies":
                loadTrading();
                break;
            case "Transport":
                loadTransport();
                break;
            default:
                loadDefault();
                break;
        }
        militaryDataListAdapter.setData(fuckingMilitaryDataList);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void loadDefault() {
        loadBankingServices();
        loadCigarettes();
        loadCommunications();
        loadConstruction();
        loadEntertainment();
        loadFoodAndDrink();
        loadHealthAndBeauty();
        loadIndustrial();
        loadInternationalTrade();
        loadManufacturing();
        loadMedia();
        loadRetail();
        militaryDataListAdapter.setData(fuckingMilitaryDataList);
    }

    private void loadBankingServices() {
        fuckingMilitaryDataList.add(new DataHeader("Banking and finance"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Beer In English", "Beer in Myanmar"));
        }
    }

    private void loadCigarettes() {
        fuckingMilitaryDataList.add(new DataHeader("Cigarettes"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("MyTel In English", "MyTel in Myanmar"));
        }
    }

    private void loadCommunications() {
        fuckingMilitaryDataList.add(new DataHeader("Communications"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadConstruction() {
        fuckingMilitaryDataList.add(new DataHeader("Construction"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadEntertainment() {
        fuckingMilitaryDataList.add(new DataHeader("Entertainment/Tourism"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }


    private void loadFoodAndDrink() {
        fuckingMilitaryDataList.add(new DataHeader("Food and drink"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadHealthAndBeauty() {
        fuckingMilitaryDataList.add(new DataHeader("Health and Beauty Products"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadIndustrial() {
        fuckingMilitaryDataList.add(new DataHeader("Industrial Estates/Offices"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadInternationalTrade() {
        fuckingMilitaryDataList.add(new DataHeader("International Trade"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadManufacturing() {
        fuckingMilitaryDataList.add(new DataHeader("Manufacturing"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadMedia() {
        fuckingMilitaryDataList.add(new DataHeader("Media"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadRetail() {
        fuckingMilitaryDataList.add(new DataHeader("Retail"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadTrading() {
        fuckingMilitaryDataList.add(new DataHeader("Trading companies"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }

    private void loadTransport() {
        fuckingMilitaryDataList.add(new DataHeader("Transport"));
        for (int i = 0; i < 5; i++) {
            fuckingMilitaryDataList.add(new MilitaryData("Transport In English", "Transport in Myanmar"));
        }
    }
}
