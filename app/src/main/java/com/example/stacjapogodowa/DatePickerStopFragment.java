package com.example.stacjapogodowa;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerStopFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Button btnDateStop;
    TextView stop_date;
    public OnDateStopSelected onDataStopSelectedCallback;

    public DatePickerStopFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar calendar;
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        stop_date = (TextView) getActivity().findViewById(R.id.stop_date);
        btnDateStop = (Button) getActivity().findViewById(R.id.btnDateStop);

        stop_date.setText(String.format("%02d", day) + "-" + String.format("%02d", month + 1) + "-" + year);

        if(onDataStopSelectedCallback != null)
            onDataStopSelectedCallback.onSelected(view, year, month, day);

    }
    public interface OnDateStopSelected {
        void onSelected(DatePicker view, int year, int month, int day);
    }
}