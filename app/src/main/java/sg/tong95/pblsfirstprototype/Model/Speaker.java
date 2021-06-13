package sg.tong95.pblsfirstprototype.Model;

import io.realm.RealmObject;

public class Speaker extends RealmObject {
    private String speaker_id;
    private String name;
    private String avatar;
    private String description;

    public Speaker(){

    }

    public Speaker(String speaker_id, String name, String avatar, String description) {
        this.speaker_id = speaker_id;
        this.name = name;
        this.avatar = avatar;
        this.description = description;
    }

    public String getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(String speaker_id) {
        this.speaker_id = speaker_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
