package fr.arikkusan.FKClasses;

import java.util.ArrayList;

public class FK_Event {

    private String name;
    private String desc;
    private int startDate;
    private boolean activated;

    public FK_Event(String name, String desc, int startDate) {
        this.name = name;
        this.desc = desc;
        this.startDate = startDate;
    }

    public FK_Event(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static FK_Event searchEventOfDay(ArrayList<FK_Event> events, int day) {

        for (FK_Event event: events) {

            if (event.getStartDate() == day)
                return event;

        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
