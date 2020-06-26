package com.example.imb.uzbekistanhotels.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class DateDialog extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

    OnDateSetListener onDateSetListener;

    public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        onDateSetListener.onChangeInView(year, month, dayOfMonth);
    }

    public interface OnDateSetListener {
        void onChangeInView(int year, int month, int day);
    }
}
