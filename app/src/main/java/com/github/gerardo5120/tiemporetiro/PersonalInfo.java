package com.github.gerardo5120.tiemporetiro;

import java.util.Calendar;

/**
 * Created by cruzgerardoyanezteran on 12/6/16.
 */
public class PersonalInfo  {

    public enum ListedWeeksBy {
        MANUALLY
        , HIRE_DATE
    }

    private Calendar birthDate;
    private int listedWeeks;
    private Calendar hireDate;
    private ListedWeeksBy listedWeeksBy = ListedWeeksBy.HIRE_DATE;



    public PersonalInfo() {
        // NOPE
    }

    public PersonalInfo(Calendar birthDate, Calendar hireDate) {
        this.birthDate = birthDate;
        setHireDate(hireDate);
    }

    public PersonalInfo(Calendar birthDate, int listedWeeks) {
        this.birthDate = birthDate;
        this.listedWeeks = listedWeeks;

        listedWeeksBy = ListedWeeksBy.MANUALLY;
    }



    public int getListedWeeks() {
        return listedWeeks;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public Calendar getHireDate() {
        return hireDate;
    }

    public ListedWeeksBy getListedWeeksBy() {
        return listedWeeksBy;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public void setListedWeeks(int listedWeeks) throws IllegalArgumentException {
        if (listedWeeksBy != ListedWeeksBy.MANUALLY)
            throw new IllegalArgumentException("Please set ListedWeeksBy to MANUALLY");

        this.listedWeeks = listedWeeks;
    }

    public void setHireDate(Calendar hireDate) {
        this.hireDate = hireDate;

        if (listedWeeksBy == listedWeeksBy.HIRE_DATE)
            listedWeeks = getWeeksBetween(hireDate, Calendar.getInstance());
    }

    public void setListedWeeksBy(ListedWeeksBy listedWeeksBy) {
        this.listedWeeksBy = listedWeeksBy;
    }

    public static float getYears(int weeks) {
        return ((float) weeks) / 52.1429f;
    }

    public float getYears() {
        return ((float) listedWeeks) / 52.1429f;
    }

    private static int getWeeksBetween(Calendar startDate, Calendar endDate) {
        long startMilis = startDate.getTimeInMillis();
        long endMilis = endDate.getTimeInMillis();

        long diff = Math.abs(startMilis - endMilis);

        return (int) (diff / (7 * 24 * 60 * 60 * 1000));
    }
}
