package fr.arikkusan.FKClasses;

import org.checkerframework.checker.nullness.qual.NonNull;

public class FK_Date {

    public int MINUTE_MULTIPLIER = 1;
    public int DAY_DURATION = 20 * MINUTE_MULTIPLIER;
    private int whenFromStart;
    private int whenFromDay;
    private int durationEvent;



    public FK_Date(@NonNull int day, @NonNull  int minute) {

        this.whenFromDay = minute * MINUTE_MULTIPLIER;
        this.whenFromStart = day * DAY_DURATION + this.whenFromDay;

    }

    public FK_Date(@NonNull int duration) {

        this.durationEvent = duration * MINUTE_MULTIPLIER;

    }

    public int getWhenFromStart() {
        return whenFromStart;
    }
}
