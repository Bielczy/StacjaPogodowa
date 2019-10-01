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
public class DatePickerStartFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Button btnDateStart;
    TextView start_date;
    public OnDateStartSelected onDateStartSelectedCallback;

    public DatePickerStartFragment() {
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

        return  new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        start_date = (TextView) getActivity().findViewById(R.id.start_date);
        btnDateStart = (Button)getActivity().findViewById(R.id.btnDateStart);

        start_date.setText(String.format("%02d", day) + "-" + String.format("%02d", month + 1) + "-" + year);


        if(onDateStartSelectedCallback != null)
            onDateStartSelectedCallback.onSelected(view, year, month, day);
    }

    public interface OnDateStartSelected {

        void onSelected(DatePicker view, int year, int month, int day);
    }
}
