package com.proyecto.Certificado;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class AgregarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        EditText etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener((View.OnClickListener) this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etPlannedDate:
                showDatePickerDialog();
                break;
        }
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        }
    }
}
