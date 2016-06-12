package com.github.gerardo5120.tiemporetiro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.gerardo5120.zpiechart.ValueDial;
import com.github.gerardo5120.zpiechart.ZPieChart;

public class MainActivity extends AppCompatActivity {
    private OnDrawExtDerechosHandler drawExtDerechosHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZPieChart pieChart = new ZPieChart(this,
                new float[] { 32f, 15f, 9.25f, 3.75f });

        ValueDial ageDial = new ValueDial(47f, 0f);

        ValueDial retirementDial = new ValueDial(13f, 47f);
        retirementDial.setColor(0xFFEC407A);

        pieChart.getValueDials().add(ageDial);
        pieChart.getValueDials().add(retirementDial);


        drawExtDerechosHandler = new OnDrawExtDerechosHandler();
        pieChart.setOnDrawChartHandler(drawExtDerechosHandler);
        setContentView(pieChart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit_info:
                Intent intent = new Intent(this, PersonalInfoActivity.class);

                //Bundle b = new Bundle();
                //intent.putExtra("Message", "Holla");

                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
