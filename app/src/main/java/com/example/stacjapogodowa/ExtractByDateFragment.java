package com.example.stacjapogodowa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public  class ExtractByDateFragment extends Fragment{

    Button btnDateStart, btnDateStop, btnTimeStart, btnTimeStop;
    FirebaseAuth firebaseAuth;

    private TemperatureFragment.DateRange range = new TemperatureFragment.DateRange();

    public ExtractByDateFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extract_by_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDateStart = view.findViewById(R.id.btnDateStart);
        btnDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateStartPickerDialog();
            }
        });

        btnDateStop = view.findViewById(R.id.btnDateStop);
        btnDateStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateStopPickerDialog();
            }
        });

        btnTimeStart = view.findViewById(R.id.btnTimeStart);
        btnTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeStartPickerDialog();
            }
        });

        btnTimeStop = view.findViewById(R.id.btnTimeStop);
        btnTimeStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeStopPickerDialog();
            }
        });

        view.findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.extractByDateMainContiner, TemperatureFragment.NewInstance(range))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    public void showDateStartPickerDialog() {
        DatePickerStartFragment newFragmentDataStart = new DatePickerStartFragment();
        newFragmentDataStart.onDateStartSelectedCallback = new DatePickerStartFragment.OnDateStartSelected() {
            @Override
            public void onSelected(DatePicker view, int year, int month, int day) {
                range.setStartDate(year, month, day);
            }
        };
        newFragmentDataStart.show(getActivity().getSupportFragmentManager(), "datePickerStart");
    }

    public void showDateStopPickerDialog() {
        DatePickerStopFragment newFragmentDataStop = new DatePickerStopFragment();
        newFragmentDataStop.onDataStopSelectedCallback = new DatePickerStopFragment.OnDateStopSelected(){

            @Override
            public void onSelected(DatePicker view, int year, int month, int day) {
                range.setStopDate(year, month, day);
            }
        };
        newFragmentDataStop.show(getActivity().getSupportFragmentManager(), "datePickerStop");
    }

    public void showTimeStartPickerDialog() {
        TimePickerStartFragment newFragmentTimeStart = new TimePickerStartFragment();
        newFragmentTimeStart.onTimeStartSelectedCallback = new TimePickerStartFragment.onTimeStartSelected(){

            @Override
            public void onSelected(TimePicker view, int hrs, int min) {
                range.setStartTime(hrs, min);
            }
        };
        newFragmentTimeStart.show(getActivity().getSupportFragmentManager(), "timePickerStart");
    }

    public void showTimeStopPickerDialog() {
        TimePickerStopFragment newFragmentTimeStop = new TimePickerStopFragment();
        newFragmentTimeStop.onTimeStopSelectedCallback = new TimePickerStopFragment.onTimeStopSelected() {
            @Override
            public void onSelected(TimePicker view, int hrs, int min) {
                range.setStopTime(hrs, min);
            }
        };
        newFragmentTimeStop.show(getActivity().getSupportFragmentManager(), "timePickerStop");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_fragm, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout: {
                firebaseAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}