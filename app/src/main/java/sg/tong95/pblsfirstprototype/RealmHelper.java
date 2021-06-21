package sg.tong95.pblsfirstprototype;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
import io.realm.RealmObject;
import io.realm.RealmResults;
import sg.tong95.pblsfirstprototype.Model.Days;
import sg.tong95.pblsfirstprototype.Model.Event;
import sg.tong95.pblsfirstprototype.Model.Presentation;
import sg.tong95.pblsfirstprototype.Model.Speaker;
import sg.tong95.pblsfirstprototype.Model.UpdateCode;

public class RealmHelper {

    RealmConfiguration config = new RealmConfiguration
            .Builder()
            .allowWritesOnUiThread(true)
            .build();

    Realm realm = Realm.getInstance(config);

    Context context;
    List<UpdateCode> getCodeList;
    List<Speaker> getSpeakerList;
    ProgressDialog dialog;

    public RealmHelper(Context context){
        this.context = context;
    }

    public static final String URL_UPDATE_CODE = "https://ipbls.herokuapp.com/WebServices/getUpdateCode.php";
    public static final String URL_SPEAKER = "https://ipbls.herokuapp.com/WebServices/getAllSpeaker.php";
    public static final String URL_SPEAKER_IMAGE = "https://ipbls.herokuapp.com/upload/";
    public static final String URL_EVENT = "https://ipbls.herokuapp.com/WebServices/getAllEvent.php";
    List<UpdateCode> codeList = new ArrayList<UpdateCode>();
    List<Speaker> speakerList = new ArrayList<Speaker>();
    List<Days> daysList = new ArrayList<Days>();
    List<Presentation> presentationList = new ArrayList<Presentation>();
    List<Event> eventList = new ArrayList<Event>();

    public boolean checkFirstTime(){
        RealmResults<UpdateCode>results = realm.where(UpdateCode.class).findAll();
        if(results.isEmpty()){
            System.out.println("True");
            return true;
        } else {
            System.out.println("False");
            return false;
        }
    }

    public boolean checkInternetConnection(Activity context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if(activeNetwork != null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                Toast.makeText(context, "Wifi Enabled", Toast.LENGTH_SHORT).show();
            }
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(context, "Data Network Enabled", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void testTrial(Activity activity){
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        dialog.show();
        if(checkFirstTime() == true){
            //First time user
            System.out.println("First time user");
            if(checkInternetConnection(activity)){
                //Have internet
                System.out.println("Have Internet");
                // Add data
                initData();
            } else {
                // No Internet
                System.out.println("No Internet");
                // Display a popup request and exit the app
            }
        } else {
            //Not first time using
            System.out.println("Not first time user");
            if(checkInternetConnection(activity)){
                //Have internet
                System.out.println("Have Internet");
                dialog.dismiss();

            } else {
                // No Internet
                System.out.println("No Internet");
            }
        }
    }

    public void testClass(){



    }

    public void addCodeList (){
        getCodeList = new ArrayList<UpdateCode>();
        WebDataServices services = new WebDataServices(context);
        services.getUpdateCode(new WebDataServices.getUpdateListener() {
            @Override
            public void onResponse(List<UpdateCode> response) {
                getCodeList = response;
                realm.beginTransaction();
                realm.copyToRealm(getCodeList);
                realm.commitTransaction();
                System.out.println("Inserted");
                System.out.println("Code: " + String.valueOf(getCodeList.get(0).getSpeakerCode()));
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void addSpeaker(){
        getSpeakerList = new ArrayList<Speaker>();
        WebDataServices services = new WebDataServices(context);
        services.getSpeakerList(new WebDataServices.getSpeakerListener() {
            @Override
            public void onResponse(List<Speaker> response) {
                getSpeakerList = response;
                realm.beginTransaction();
                realm.copyToRealm(getSpeakerList);
                realm.commitTransaction();
                System.out.println("Inserted");
                System.out.println("Speaker Name: " + getSpeakerList.get(0).getName());
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    //if(checkUpdate(activity)){
    //                    // have update
    //                    System.out.println("Check Update: true");
    //                    // delete the current realm data and insert again.
    //                } else {
    //                    // no update
    //                    System.out.println("Check Update: false");
    //                }



//
//    public boolean checkUpdate(Activity activity){
//
//        // newVersion is taking from json
//        //int newVersion = version;
//
//        StringRequest requestUpdateCode = new StringRequest(urlUpdateCode, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response != null){
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        int id = jsonArray.getJSONObject(0).getInt("id");
//                        int speakerUpdateCode = jsonArray.getJSONObject(0).getInt("speakerCode");
//                        int eventUpdateCode =jsonArray.getJSONObject(0).getInt("eventCode");
//                        codeList.add(new UpdateCode(id,speakerUpdateCode,eventUpdateCode));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        RequestQueue updateQueue = Volley.newRequestQueue(activity);
//        updateQueue.add(requestUpdateCode);
//
//        int newVersion = codeList.get(0).getSpeakerCode();
//        System.out.println("CodeList: " + String.valueOf(newVersion));
////        int newVersion = getCode.size();
//        RealmResults<UpdateCode>results = realm.where(UpdateCode.class).findAll();
//        int currentVersion = results.get(0).getSpeakerCode();
////        System.out.println("Check Update: " + String.valueOf(results.get(0).getSpeakerCode()));
////        System.out.println("Check newVersion: " + String.valueOf(newVersion));
//        if(newVersion > currentVersion){
//            //System.out.println("Check Update: true");
//            return true;
//        } else{
//            //System.out.println("Check Update: false");
//            return false;
//        }
//
//
//    }
//
    public List<Speaker> getAllSpeaker(){
        return realm.where(Speaker.class).findAll();
    }
    
    public Speaker getSpeakerByID(String id){
        return realm.where(Speaker.class).equalTo("speaker_id", id).findFirst();
    }

    public String getSpeakerNameByIDString(String id){
        return realm.where(Speaker.class).equalTo("speaker_id", id).findFirst().getName();
    }

    public Event getEventByID(String id){
        return realm.where(Event.class).equalTo("id",id).findFirst();
    }

    public List<Presentation> getAllPresentation(){
        return realm.where(Presentation.class).findAll();
    }

    public List<Event> getEventFromPresentationDay(String presentation, String days){
        RealmResults<Event> results;
        switch (days){
            case "Day 1":
                 results = realm.where(Event.class).equalTo("presentation_id", presentation).equalTo("days_id","1").findAll();
                break;
            case "Day 2":
                results = realm.where(Event.class).equalTo("presentation_id", presentation).equalTo("days_id","2").findAll();
                break;
            case "Day 3":
                results = realm.where(Event.class).equalTo("presentation_id", presentation).equalTo("days_id","3").findAll();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + days);
        }
//        return realm.where(Event.class).equalTo("presentation_id", presentation).equalTo("days_id","2").findAll();
        return results;
    }

    public List<Event> getEventByDay(String days){
        RealmResults<Event> results;
        System.out.println("Days: " + days);
        RealmResults results1 = realm.where(Event.class).findAll();
        System.out.println(results1.isEmpty());
        System.out.println("event size: " + results1.size());
        switch (days){
            case "Day 1":
                results = realm.where(Event.class).equalTo("days_id","1").findAll();
                break;
            case "Day 2":
                results = realm.where(Event.class).equalTo("days_id","2").findAll();
                break;
            case "Day 3":
                results = realm.where(Event.class).equalTo("days_id","3").findAll();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + days);
        }
//        if(days.equals("Day 1")){
//
//        } else if(days.equals("Day 2")){
//
//        } else {
//
//        }
        return results;
    }

    public List<Event> getAllEventDay1(){
        return realm.where(Event.class).equalTo("days_id","1").findAll();
    }
    public List<Event> getAllEventDay2(){
        return realm.where(Event.class).equalTo("days_id","2").findAll();
    }
    public List<Event> getAllEventDay3(){
        return realm.where(Event.class).equalTo("days_id","3").findAll();
    }

    public void insertData (Activity activity){

        if(checkFirstTime()){
            System.out.println("Had no value");
            initData();
        } else {
//            if(checkUpdate()){
//                System.out.println("Have new update");
//                deleteData();
//                insertData(activity);
//            } else {
//                System.out.println("No update");
//            }
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

    public void insertDays(){
        daysList.add(new Days("1","Day 1"));
        daysList.add(new Days("2","Day 2"));
        daysList.add(new Days("3","Day 3"));

        realm.beginTransaction();
        realm.copyToRealm(daysList);
        realm.commitTransaction();
    }

    public void insertPresentation(){
        presentationList.add(new Presentation("1", "Seminar"));
        presentationList.add(new Presentation("2", "Workshop"));
        presentationList.add(new Presentation("3", "PBL in action"));
        presentationList.add(new Presentation("4", "Roundtable"));

        realm.beginTransaction();
        realm.copyToRealm(presentationList);
        realm.commitTransaction();
    }

    public void insertUpdateCode (){

        StringRequest requestUpdateCode = new StringRequest(URL_UPDATE_CODE, new Response.Listener<String>() {
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
        //RequestQueue updateQueue = Volley.newRequestQueue(activity);
        //updateQueue.add(requestUpdateCode);
        MySingleton.getInstance(context).addToRequestQueue(requestUpdateCode);

    }
    public void insertSpeaker (){

        StringRequest requestSpeaker = new StringRequest(URL_SPEAKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String speakerID = object.getString("id");
                        String speakerName = object.getString("name");
                        String speakerAvatar = URL_SPEAKER_IMAGE + object.getString("avatar");
                        String speakerDesc = object.getString("description");
                        System.out.println("Check 1S: " + speakerID + " , " + speakerName+ " , " + speakerAvatar + " . ");
                        speakerList.add(new Speaker(speakerID, speakerName, speakerAvatar, speakerDesc));
                    }
                    realm.beginTransaction();
                    realm.copyToRealm(speakerList);
                    realm.commitTransaction();
                    System.out.println("Inserted");
                    System.out.println("Check 2S: " + speakerList.get(0).getName() + " , " + speakerList.get(1).getName()+ " , " + speakerList.get(2).getName() + " . ");
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
//        RequestQueue speakerQueue = Volley.newRequestQueue(context);
//        speakerQueue.add(requestSpeaker);
        MySingleton.getInstance(context).addToRequestQueue(requestSpeaker);
    }

    public void insertEvent (){

        StringRequest requestEvent = new StringRequest(URL_EVENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String title = object.getString("title");
                            String location = object.getString("location");
                            String date = object.getString("date");
                            String startTime = object.getString("startTime");
                            String endTime = object.getString("endTime");
                            String detail = object.getString("detail");
                            String presentation_id = object.getString("presentation_id");
                            String days_id = object.getString("days_id");
                            String speaker_id = object.getString("speaker_id");
                            eventList.add(new Event(id,title,location,date,startTime,endTime,detail,presentation_id,days_id,speaker_id));

                        }
                        realm.beginTransaction();
                        realm.copyToRealm(eventList);
                        realm.commitTransaction();
                        System.out.println("Inserted Event");
                        dialog.dismiss();
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
        //RequestQueue updateQueue = Volley.newRequestQueue(activity);
        //updateQueue.add(requestUpdateCode);
        MySingleton.getInstance(context).addToRequestQueue(requestEvent);

    }

    public List<Event> getTest(){
        return realm.where(Event.class).findAll();
    }

    public void initData(){
        insertUpdateCode();
        insertSpeaker();
        insertDays();
        insertPresentation();
        insertEvent();
    }


}
