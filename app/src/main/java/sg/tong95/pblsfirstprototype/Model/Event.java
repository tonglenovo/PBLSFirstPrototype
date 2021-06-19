package sg.tong95.pblsfirstprototype.Model;

import io.realm.RealmObject;

public class Event extends RealmObject {
    private String id;
    private String title;
    private String location;
    private String date;
    private String startTime;
    private String endTime;
    private String detail;
    private String presentation_id;
    private String days_id;
    private String speaker_id;

    public Event(){

    }

    public Event(String id, String title, String location, String date, String startTime, String endTime, String detail, String presentation_id, String days_id, String speaker_id) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.detail = detail;
        this.presentation_id = presentation_id;
        this.days_id = days_id;
        this.speaker_id = speaker_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPresentation_id() {
        return presentation_id;
    }

    public void setPresentation_id(String presentation_id) {
        this.presentation_id = presentation_id;
    }

    public String getDays_id() {
        return days_id;
    }

    public void setDays_id(String days_id) {
        this.days_id = days_id;
    }

    public String getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(String speaker_id) {
        this.speaker_id = speaker_id;
    }
}
