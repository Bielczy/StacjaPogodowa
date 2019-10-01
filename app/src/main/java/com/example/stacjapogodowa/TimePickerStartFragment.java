package com.example.stacjapogodowa;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerStartFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    TextView start_time;
    Button btnTimeStart;
    public onTimeStartSelected onTimeStartSelectedCallback;

    public TimePickerStartFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {

        start_time = (TextView) getActivity().findViewById(R.id.start_time);
        btnTimeStart = (Button) getActivity().findViewById(R.id.btnTimeStart);
//
        start_time.setText(String.format("%02d", hour )+ " : " + String.format("%02d", minute ));

        if(onTimeStartSelectedCallback != null)
            onTimeStartSelectedCallback.onSelected(view, hour, minute);
    }

    public interface onTimeStartSelected {
        void onSelected(TimePicker view, int hour, int minute);
    }
}
