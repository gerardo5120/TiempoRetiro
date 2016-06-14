package com.github.gerardo5120.tiemporetiro;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PersonalInfoActivity extends AppCompatActivity {
    private EditText etDate;
    private PersonalInfo personalInfo;

    private TextView tvDatePickerReceiver;

    private enum ListItems { BIRTH_DATE, LISTED_WEEKS, HIRE_DATE };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.edit_info));


        setContentView(R.layout.activity_personal_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        Calendar calBirthDate = Calendar.getInstance();
        Calendar calHireDate = Calendar.getInstance();

        calBirthDate.set(1988, 2, 15);
        calHireDate.set(2008, 9, 15);

        personalInfo = new PersonalInfo(calBirthDate, 200);



        ListView l = (ListView) findViewById(R.id.listView);

        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "purpose" };
        int[] to = { R.id.name, R.id.value };

        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.personal_info_item, from, to);
        l.setAdapter(adapter);

        l.setOnItemClickListener(itemClickListener);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return true;
    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<>();

        DateFormat dateFormat = android.text.format.DateFormat.
                getDateFormat(PersonalInfoActivity.this);



        list.add(putData(getString(R.string.birth_date),
                dateFormat.format(personalInfo.getBirthDate().getTime())));
        list.add(putData(getString(R.string.listed_weeks),
                String.valueOf(personalInfo.getListedWeeks())));

        String valHireDate;


        if (personalInfo.getHireDate() != null)
            valHireDate = dateFormat.format(personalInfo.getHireDate().getTime());
        else
            valHireDate = getString(R.string.no_date_spec);

        list.add(putData(getString(R.string.hire_date),
                valHireDate));

        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<>();
        item.put("name", name);
        item.put("purpose", purpose);
        return item;
    }









    private AdapterView.OnItemClickListener itemClickListener =
            new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (ListItems.values()[position] == ListItems.BIRTH_DATE) {
                tvDatePickerReceiver = (TextView) view.findViewById(R.id.value);
                PersonalInfoActivity.this.showDatePicker(ListItems.BIRTH_DATE.toString());
            }
            else if (ListItems.values()[position] == ListItems.LISTED_WEEKS) {
                PersonalInfoActivity.this.showListedWeeks();
            }
            else if (ListItems.values()[position] == ListItems.HIRE_DATE) {
                tvDatePickerReceiver = (TextView) view.findViewById(R.id.value);
                PersonalInfoActivity.this.showDatePicker(ListItems.HIRE_DATE.toString());
            }
        }
    };

    private void showListedWeeks() {
        ListedWeeksFragment listedWeeks = new ListedWeeksFragment();

        listedWeeks.show(getFragmentManager().beginTransaction(), "listed_weeks");
    }

    private void showDatePicker(String tag) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager().beginTransaction(), tag);
    }





    public static class DatePickerFragment extends android.app.DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private PersonalInfoActivity mActivity;

        @Override
        public void onAttach(Activity activity) {
            if (activity instanceof PersonalInfoActivity) {
                mActivity = (PersonalInfoActivity) activity;
            }

            super.onAttach(activity);
        }

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
                    getDateFormat(mActivity);

            final Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);


            if (ListItems.valueOf(getTag()) == ListItems.BIRTH_DATE)
                mActivity.personalInfo.setBirthDate(c);
            else if (ListItems.valueOf(getTag()) == ListItems.HIRE_DATE)
                mActivity.personalInfo.setHireDate(c);




            if (mActivity.tvDatePickerReceiver == null)
                return;

            mActivity.tvDatePickerReceiver.setText(dateFormat.format(c.getTime()));
        }
    }






    public static class ListedWeeksFragment extends DialogFragment {
        private PersonalInfoActivity mActivity;

        private EditText etListedWeeks;
        private EditText etYearsWorking;
        private RadioButton rbByHireDate;
        private RadioButton rbEntManually;

        @Override
        public void onAttach(Activity activity) {
            if (activity instanceof PersonalInfoActivity) {
                mActivity = (PersonalInfoActivity) activity;
            }

            super.onAttach(activity);
        }

        @Override
        public void onDetach() {

            if (rbByHireDate.isChecked() == true) {
                
            }


            super.onDetach();
        }

        public ListedWeeksFragment() {
            // NOPE
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.listed_weeks, container);
            getDialog().setTitle(getString(R.string.calculate_weeks));

            etListedWeeks = (EditText) view.findViewById(R.id.etListedWeeks);
            etYearsWorking = (EditText) view.findViewById(R.id.etYearsWorking);
            rbByHireDate = (RadioButton) view.findViewById(R.id.rbByHireDate);
            rbEntManually = (RadioButton) view.findViewById(R.id.rbEntManually);


            rbByHireDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enableControls(true);
                }
            });

            rbEntManually.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enableControls(false);
                }
            });


            etListedWeeks.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // NOPE
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int listedWeeks = 0;

                    if (s.length() > 0)
                        listedWeeks = Integer.valueOf(s.toString());

                    float yearsWorking = PersonalInfo.getYears(listedWeeks);

                    etYearsWorking.setText(String.valueOf(yearsWorking));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // NOPE
                }
            });



            if (mActivity.personalInfo.getListedWeeksBy() ==
                    PersonalInfo.ListedWeeksBy.HIRE_DATE) {
                rbByHireDate.setChecked(true);

                enableControls(true);
            }
            else if (mActivity.personalInfo.getListedWeeksBy() ==
                    PersonalInfo.ListedWeeksBy.MANUALLY) {
                rbEntManually.setChecked(true);

                enableControls(false);
            }

            return view;
        }

        //onSupportNavigateUp


        private void enableControls(boolean byHireDate) {
            if (byHireDate) {
                etListedWeeks.setEnabled(false);
                etYearsWorking.setEnabled(false);
            }
            else {
                etListedWeeks.setEnabled(true);
                etYearsWorking.setEnabled(true);

                etListedWeeks.requestFocus();
            }
        }
    }
}
