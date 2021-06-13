package sg.tong95.pblsfirstprototype.Model;

import io.realm.RealmObject;

public class UpdateCode extends RealmObject {
    private int updateID;
    private int speakerCode;
    private int eventCode;

    public UpdateCode(){

    }

    public UpdateCode(int updateID, int speakerCode, int eventCode) {
        this.updateID = updateID;
        this.speakerCode = speakerCode;
        this.eventCode = eventCode;
    }

    public int getUpdateID() {
        return updateID;
    }

    public void setUpdateID(int updateID) {
        this.updateID = updateID;
    }

    public int getSpeakerCode() {
        return speakerCode;
    }

    public void setSpeakerCode(int speakerCode) {
        this.speakerCode = speakerCode;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}
