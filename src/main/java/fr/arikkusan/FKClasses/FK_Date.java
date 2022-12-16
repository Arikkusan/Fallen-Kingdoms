package fr.arikkusan.FKClasses;

public class FK_Date {
    private int day;
    private int hour;
    private int minute;
    private int sec;
    private boolean changed;

    public FK_Date(int day, int minute) {
        this.day = day;
        this.hour = 0;
        this.minute = minute;
        this.sec = 0;

    }

    public String getGameDuration() {
        String duration = "";

        if (hour > 0)
            duration += "0" + hour + ":";

        if (minute > 9)
            duration += minute;
        else
            duration += "0" + minute;

        duration += ":";

        if (sec > 9)
            duration += sec;
        else duration += "0" + sec;

        return duration;
    }

    public int getDay() {
        return day;
    }

    public int getSec() {
        return sec;
    }

    public void next() {

        sec += 1;
        if (sec == 60) {

            minute += 1;
            sec = 0;

        }
        if (minute == 60) {
            hour += 1;
            minute = 0;
        }

        day = ((minute + hour * 60) / 20) + 1;

        setChanged(minute % 20 == 0 && sec == 0);


    }

    public boolean changed() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
