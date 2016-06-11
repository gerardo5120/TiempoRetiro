package com.github.gerardo5120.tiemporetiro;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cruzgerardoyanezteran on 11/6/16.
 */
public class PersonalInfoItemsActivity extends ListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "purpose" };
        int[] to = { R.id.name, R.id.value };

        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.personal_info_item, from, to);
        setListAdapter(adapter);
    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<>();

        list.add(putData("Fecha Nac.", "15/feb/1988"));
        list.add(putData("Semanas Cot.", "287 Semanas"));
        list.add(putData("Fecha Contratación", "15/sep/2008"));

        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("purpose", purpose);
        return item;
    }
}
