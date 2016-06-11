package com.github.gerardo5120.tiemporetiro;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by cruzgerardoyanezteran on 10/6/16.
 */
public class ListedWeeks extends DialogFragment {
    private EditText etListedWeeks;

    public ListedWeeks() {
        // NOPE
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listed_weeks, container);
        etListedWeeks = (EditText) view.findViewById(R.id.etListedWeeks);
        getDialog().setTitle(getString(R.string.app_name));

        return view;
    }
}