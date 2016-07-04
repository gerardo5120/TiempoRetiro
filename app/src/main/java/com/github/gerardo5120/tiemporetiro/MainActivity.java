package com.github.gerardo5120.tiemporetiro;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.gerardo5120.zpiechart.Grad;
import com.github.gerardo5120.zpiechart.GradedDial;
import com.github.gerardo5120.zpiechart.Mark;
import com.github.gerardo5120.zpiechart.OnDrawChartParams;
import com.github.gerardo5120.zpiechart.OnDrawChartSimpleHandler;
import com.github.gerardo5120.zpiechart.OnDrawMarkValueParams;
import com.github.gerardo5120.zpiechart.OnDrawSliceParams;
import com.github.gerardo5120.zpiechart.OnDrawValueDialParams;
import com.github.gerardo5120.zpiechart.SingleMark;
import com.github.gerardo5120.zpiechart.ValueDial;
import com.github.gerardo5120.zpiechart.ZPieChart;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private OnDrawExtDerechosHandler drawExtDerechosHandler;
    private ZPieChart pieChart;

    private FloatingActionButton fabNext;
    private FloatingActionMenu famLayers;
    private FloatingActionButton fabDates;
    private FloatingActionButton fabTimes;
    private FloatingActionButton fabYears;
    private FloatingActionButton fabAllLayers;
    private FloatingActionButton fabPrevious;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = (ZPieChart) findViewById(R.id.vPieChart);

        pieChart.setValues(new float[] { 32f, 15f, 9.25f, 3.75f });


        ValueDial ageDial = new ValueDial(47f, 0f);

        ValueDial retirementDial = new ValueDial(9.25f, 47f);
        retirementDial.setColor(0xFFEC407A);

        pieChart.getValueDials().add(ageDial);
        pieChart.getValueDials().add(retirementDial);


        GradedDial timeDial = new GradedDial();
        timeDial.setDistance(70f);
        timeDial.setShowLine(false);

        Grad years = new Grad(5f, new Mark());
        //years.setMinScale(3.0f);
        timeDial.getGrads().add(years);


        SingleMark currentAge = new SingleMark(47f);
        timeDial.getSingleMarks().add(currentAge);

        SingleMark sixtyYearsOld = new SingleMark(60f);
        timeDial.getSingleMarks().add(sixtyYearsOld);

        SingleMark ageStartedWorking = new SingleMark(32f);
        timeDial.getSingleMarks().add(ageStartedWorking);


        pieChart.getGradedDials().add(timeDial);


        drawExtDerechosHandler = new OnDrawExtDerechosHandler();
        pieChart.setOnDrawChartHandler(drawExtDerechosHandler);







        famLayers = (FloatingActionMenu) findViewById(R.id.famLayers);
        famLayers.setOnMenuToggleListener(menuToggleListener);

        fabNext = (FloatingActionButton) findViewById(R.id.fabNext);
        fabNext.setOnClickListener(LayersButtonsOnClick);

        fabDates = (FloatingActionButton) findViewById(R.id.fabDates);
        fabDates.setOnClickListener(LayersButtonsOnClick);

        fabTimes = (FloatingActionButton) findViewById(R.id.fabTimes);
        fabTimes.setOnClickListener(LayersButtonsOnClick);

        fabYears = (FloatingActionButton) findViewById(R.id.fabYears);
        fabYears.setOnClickListener(LayersButtonsOnClick);

        fabAllLayers = (FloatingActionButton) findViewById(R.id.fabAllLayers);
        fabAllLayers.setOnClickListener(LayersButtonsOnClick);

        fabPrevious = (FloatingActionButton) findViewById(R.id.fabPrevious);
        fabPrevious.setOnClickListener(LayersButtonsOnClick);




    }

    private FloatingActionMenu.OnMenuToggleListener menuToggleListener =
            new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            float alphaFrom, alphaTo;
            final float alphaNormal = 1f;
            final float alphaFaded = 0.5f;

            if (opened == true) {
                alphaFrom = alphaNormal;
                alphaTo = alphaFaded;
            }
            else {
                alphaFrom = alphaFaded;
                alphaTo = alphaNormal;
            }

            AlphaAnimation fadeOut = new AlphaAnimation(alphaFrom, alphaTo);
            fadeOut.setInterpolator(new AccelerateInterpolator());
            fadeOut.setDuration(250);
            fadeOut.setFillAfter(true);

            pieChart.startAnimation(fadeOut);
            pieChart.setEnabled(!opened);
        }
    };

    private final ZPieChart.PieChartComponent[] showComponents = {
            ZPieChart.PieChartComponent.VALUE_DIALS,
            ZPieChart.PieChartComponent.LABELS,
            ZPieChart.PieChartComponent.GRADED_DIALS
    };

    private int lastComponentIndex() {
        return showComponents.length - 1;
    }

    private int currentComponent = lastComponentIndex();

    private ZPieChart.PieChartComponent showNext() {
        currentComponent++;

        if (currentComponent == showComponents.length) {
            currentComponent = 0;
        }

        return showComponents[currentComponent];
    }

    private ZPieChart.PieChartComponent showPrevious() {
        currentComponent--;

        if (currentComponent == -1) {
            currentComponent = (lastComponentIndex());
        }

        return showComponents[currentComponent];
    }

    private int getIndexComponent(ZPieChart.PieChartComponent component) {
        if (component == ZPieChart.PieChartComponent.GRADED_DIALS)
            return 2;
        else if (component == ZPieChart.PieChartComponent.VALUE_DIALS)
            return 0;
        else if (component == ZPieChart.PieChartComponent.GRADED_DIALS)
            return 1;
        else
            return 0;
    }


    private View.OnClickListener LayersButtonsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Click Listener");

            if (v.getId() == R.id.fabNext) {
                System.out.println("Next");
                pieChart.showOnly(showNext());
            }
            else if (v.getId() == R.id.fabDates) {
                System.out.println("Dates");
                pieChart.showOnly(ZPieChart.PieChartComponent.GRADED_DIALS);
                currentComponent =
                        getIndexComponent(ZPieChart.PieChartComponent.GRADED_DIALS);
            } else if (v.getId() == R.id.fabTimes) {
                System.out.println("Times");
                pieChart.showOnly(ZPieChart.PieChartComponent.LABELS);
                currentComponent =
                        getIndexComponent(ZPieChart.PieChartComponent.LABELS);
            } else if (v.getId() == R.id.fabYears) {
                System.out.println("Years");
                pieChart.showOnly(ZPieChart.PieChartComponent.VALUE_DIALS);
                currentComponent =
                        getIndexComponent(ZPieChart.PieChartComponent.VALUE_DIALS);
            } else if (v.getId() == R.id.fabAllLayers) {
                System.out.println("All Layers");
                currentComponent = lastComponentIndex();
                pieChart.showAllComponents();
            } else if (v.getId() == R.id.fabPrevious) {
                System.out.println("Previous");
                pieChart.showOnly(showPrevious());
            }

            famLayers.close(true);
        }
    };





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

                //startActivity(intent);
                startActivityForResult(intent, 1);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                PersonalInfo pi = (PersonalInfo) data.getSerializableExtra("personalInfo");

                System.out.println("Personal Info: " +
                    pi.toString());
            }
        }
    }




    public class OnDrawExtDerechosHandler extends OnDrawChartSimpleHandler {
        private GregorianCalendar birthDate;
        private GregorianCalendar now;
        private SimpleDateFormat dateFormat;

        private GregorianCalendar weeksAgo;

        public OnDrawExtDerechosHandler() {
            birthDate = new GregorianCalendar(1988, 02, 15);
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            weeksAgo = new GregorianCalendar();

            // System.out.println(dateFormat.format(birthDate.getTime()));
        }

        @Override
        public boolean onDrawBegin(OnDrawChartParams params) {
            weeksAgo.set(birthDate.get(GregorianCalendar.YEAR),
                    birthDate.get(GregorianCalendar.MONTH),
                    birthDate.get(GregorianCalendar.DAY_OF_MONTH));

            return true;
        }

        @Override
        public boolean onDrawValueDial(OnDrawChartParams params) {
            OnDrawValueDialParams dialParams = (OnDrawValueDialParams) params;

            if (dialParams.getValue() == 47f)
                dialParams.setText("Tienes " + String.valueOf((int) dialParams.getValue()) + " años de edad");
            else if (dialParams.getValue() == 9.25f)
                dialParams.setText(String.valueOf(9) + " años p/retiro (ED)");

            return super.onDrawValueDial(params);
        }

        @Override
        public boolean onDrawSlice(OnDrawChartParams params) {
            OnDrawSliceParams sliceParams = (OnDrawSliceParams) params;

            if (sliceParams.getValue() == 32) {
                //sliceParams.setText("A los 32 años empezaste");
                return false;
            }
            else if (sliceParams.getValue() == 15) {
                sliceParams.setText("772 sem's cot's (15 años)");
            }
            else if (sliceParams.getValue() == 9.25) {
                sliceParams.setText(sliceParams.getValue() + " años");
            }

            else if (sliceParams.getValue() == 3.75) {
                sliceParams.setText("Ext. derechos: " + sliceParams.getValue() + " años");
            }
            else if (sliceParams.getValue() == 1.0f)
                sliceParams.setText("Hello ;)");


            return super.onDrawSlice(params);
        }

        @Override
        public boolean onDrawMarkValue(OnDrawChartParams params) {
            OnDrawMarkValueParams markValueParams =  (OnDrawMarkValueParams) params;


            if (markValueParams.getMarkValue() == 60) {


                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.elderly);

                markValueParams.setIcon(Bitmap.createScaledBitmap(bitmap, 250, 250, false));

                System.out.println("Bitmap Height: " + bitmap.getHeight() +
                        " Width: " + bitmap.getWidth());

                // R.drawable.ic_action_beach_access

                // markValueParams.getCanvas().drawPicture();

                // Resources res = markValueParams.getCanvas()

                // Bitmap bitmap = BitmapFactory.decodeResource()
            }

            return true;
        }
    }
}
