package sg.tong95.pblsfirstprototype;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import sg.tong95.pblsfirstprototype.Model.Speaker;
import sg.tong95.pblsfirstprototype.Model.UpdateCode;

public class RealmHelper {

    RealmConfiguration config = new RealmConfiguration
            .Builder()
            .allowWritesOnUiThread(true)
            .build();

    Realm realm = Realm.getInstance(config);

    public boolean checkFirstTime(){
        RealmResults<UpdateCode>results = realm.where(UpdateCode.class).findAll();
        if(results.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUpdate(){

        // newVersion is taking from json

        int newVersion = 12;
        RealmResults<UpdateCode>results = realm.where(UpdateCode.class).findAll();
        int currentVersion = results.get(0).getSpeakerCode();
        System.out.println("Check Update: " + String.valueOf(results.get(0).getSpeakerCode()));
        if(newVersion > currentVersion){
            System.out.println("Check Update: true");
            return true;
        } else{
            System.out.println("Check Update: false");
            return false;
        }

    }

    public List<Speaker> getAllSpeaker(){
        return realm.where(Speaker.class).findAll();
    }
    
    public Speaker getSpeakerByID(String id){
        return realm.where(Speaker.class).equalTo("speaker_id", id).findFirst();
    }

    public void insertData(Context context){

        if(checkFirstTime()){
            System.out.println("Had no value");
            initData(context);
        } else {
            if(checkUpdate()){
                System.out.println("Have new update");
                deleteData();
                insertData(context);
            } else {
                System.out.println("No update");
            }
        }

//        RealmResults<UpdateCode>results = realm.where(UpdateCode.class).findAll();
//        if(results.isEmpty()){
//            System.out.println("Had no value");
//            initData(context);
//        } else {
//            if(checkUpdate()){
//
//            }
//            System.out.println("deleteData, insertData, getData");
//        }
    }

    public void deleteData(){
        final RealmResults<UpdateCode> item = realm.where(UpdateCode.class).findAll();
        final RealmResults<Speaker> speakers = realm.where(Speaker.class).findAll();
        if(!speakers.isEmpty()){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    speakers.deleteAllFromRealm();
                    System.out.println("Deleted Speaker");
                }
            });
        }

        if(!item.isEmpty()){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    item.deleteAllFromRealm();
                    System.out.println("Deleted UpdateCode");
                }
            });
        }

    }

    public void initData(Context context){
        String urlUpdateCode = "https://ipbls.herokuapp.com/WebServices/getUpdateCode.php";
        String urlSpeaker = "https://ipbls.herokuapp.com/WebServices/getAllSpeaker.php";
        String urlImageLocation = "https://ipbls.herokuapp.com/upload/";
        List<UpdateCode> codeList = new ArrayList<UpdateCode>();
        List<Speaker> speakerList = new ArrayList<Speaker>();

//        ProgressDialog dialog = new ProgressDialog(context);
//        dialog.setMessage("Please wait....");
//        dialog.setCancelable(true);
//        dialog.show();

        StringRequest requestUpdateCode = new StringRequest(urlUpdateCode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        int id = jsonArray.getJSONObject(0).getInt("id");
                        int speakerUpdateCode = jsonArray.getJSONObject(0).getInt("speakerCode");
                        int eventUpdateCode =jsonArray.getJSONObject(0).getInt("eventCode");
                        System.out.println("Check 1U: " + String.valueOf(id) + " , " + String.valueOf(speakerUpdateCode) + " , " + String.valueOf(eventUpdateCode)  + " . ");
                        codeList.add(new UpdateCode(id,speakerUpdateCode,eventUpdateCode));
                        System.out.println("Check 2U: " + String.valueOf(codeList.get(0).getSpeakerCode()));
                        realm.beginTransaction();
                        realm.copyToRealm(codeList);
                        realm.commitTransaction();
                        System.out.println("Inserted");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        StringRequest requestSpeaker = new StringRequest(urlSpeaker, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String speakerID = object.getString("id");
                        String speakerName = object.getString("name");
                        String speakerAvatar = urlImageLocation + object.getString("avatar");
                        String speakerDesc = object.getString("description");
                        System.out.println("Check 1S: " + speakerID + " , " + speakerName+ " , " + speakerAvatar + " . ");
                        speakerList.add(new Speaker(speakerID, speakerName, speakerAvatar, speakerDesc));
                    }
                    realm.beginTransaction();
                    realm.copyToRealm(speakerList);
                    realm.commitTransaction();
                    System.out.println("Inserted");
                    System.out.println("Check 2S: " + speakerList.get(0).getName() + " , " + speakerList.get(1).getName()+ " , " + speakerList.get(2).getName() + " . ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue updateQueue = Volley.newRequestQueue(context);
        updateQueue.add(requestUpdateCode);
        RequestQueue speakerQueue = Volley.newRequestQueue(context);
        speakerQueue.add(requestSpeaker);

    }
}
