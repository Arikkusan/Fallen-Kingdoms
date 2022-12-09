package fr.arikkusan.FKClasses;

import org.checkerframework.checker.nullness.qual.NonNull;

public class FKEvent {

    private String name;
    private String desc;
    private FKDate startDate;
    private FKDate duration;

    public FKEvent(@NonNull String name, @NonNull String desc, @NonNull FKDate startDate, @NonNull FKDate duration) {
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

    public FKDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull FKDate startDate) {
        this.startDate = startDate;
    }

    public FKDate getDuration() {
        return duration;
    }

    public void setDuration(@NonNull FKDate duration) {
        this.duration = duration;
    }
}
