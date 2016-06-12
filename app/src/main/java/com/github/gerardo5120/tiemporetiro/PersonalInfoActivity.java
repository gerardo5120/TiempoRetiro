package com.github.gerardo5120.tiemporetiro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PersonalInfoActivity extends AppCompatActivity {
    private EditText etDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        ListView l = (ListView) findViewById(R.id.listView);

        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "purpose" };
        int[] to = { R.id.name, R.id.value };

        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.personal_info_item, from, to);
        l.setAdapter(adapter);
    }




    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<>();

        DateFormat dateFormat = android.text.format.DateFormat.
                getDateFormat(PersonalInfoActivity.this);

        final Calendar c = Calendar.getInstance();
        c.set(15, 2, 1988);




        list.add(putData(getString(R.string.birth_date),
                dateFormat.format(c.getTime())));
        list.add(putData(getString(R.string.listed_weeks),
                "287 Semanas"));
        list.add(putData(getString(R.string.hire_date),
                dateFormat.format(c.getTime())));

        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("purpose", purpose);
        return item;
    }






    public class DatePickerFragment extends android.app.DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            DateFormat dateFormat = android.text.format.DateFormat.
                    getDateFormat(PersonalInfoActivity.this);

            final Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);

            PersonalInfoActivity.this.etDate.setText(dateFormat.format(c.getTime()));
        }
    }
}
