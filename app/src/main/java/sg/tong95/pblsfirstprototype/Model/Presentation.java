package sg.tong95.pblsfirstprototype.Model;

import io.realm.RealmObject;

public class Presentation extends RealmObject {
    private String id;
    private String presentation;

    public Presentation(){

    }

    public Presentation(String id, String presentation) {
        this.id = id;
        this.presentation = presentation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }
}
