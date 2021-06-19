package sg.tong95.pblsfirstprototype.Model;

import io.realm.RealmObject;

public class Days extends RealmObject {
    private String id;
    private String days;

    public Days(){

    }

    public Days(String id, String days) {
        this.id = id;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
