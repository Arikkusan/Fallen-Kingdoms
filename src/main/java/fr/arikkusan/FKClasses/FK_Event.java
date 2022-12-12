package fr.arikkusan.FKClasses;

import org.checkerframework.checker.nullness.qual.NonNull;

public class FK_Event {

    private String name;
    private String desc;
    private FK_Date startDate;
    private FK_Date duration;

    public FK_Event(@NonNull String name, @NonNull String desc, @NonNull FK_Date startDate, @NonNull FK_Date duration) {
        this.name = name;
        this.desc = desc;
        this.startDate = startDate;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(@NonNull String desc) {
        this.desc = desc;
    }

    public FK_Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull FK_Date startDate) {
        this.startDate = startDate;
    }

    public FK_Date getDuration() {
        return duration;
    }

    public void setDuration(@NonNull FK_Date duration) {
        this.duration = duration;
    }
}
