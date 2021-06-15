package sg.tong95.pblsfirstprototype;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
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

    String urlUpdateCode = "https://ipbls.herokuapp.com/WebServices/getUpdateCode.php";
    List<UpdateCode> codeList = new ArrayList<UpdateCode>();
    List<Speaker> speakerList = new ArrayList<Speaker>();
    int checkVersion = 0;

    public void testClass(){
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait....");
        dialog.setCancelable(true);
        dialog.show();

        addSpeaker();
    }

    public void getUpdateCode (){
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
                //System.out.println("Speaker Name: " + getSpeakerList.get(0).getName());
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void getSpeakerName(){
        RealmResults<Speaker> realmResults = realm.where(Speaker.class).findAll();
        String name = realmResults.get(0).getName();
        System.out.println("Name: " + name);
    }



//    public boolean checkInternetConnection(Activity context){
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
//
//        if(activeNetwork != null){
//            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
//                Toast.makeText(context, "Wifi Enabled", Toast.LENGTH_SHORT).show();
//            }
//            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
//                Toast.makeText(context, "Data Network Enabled", Toast.LENGTH_SHORT).show();
//            }
//            return true;
//        } else {
//            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//    }
//
//    public boolean checkFirstTime(){
//        RealmResults<UpdateCode>results = realm.where(UpdateCode.class).findAll();
//        if(results.isEmpty()){
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void testTrial(Activity activity){
//        if(checkFirstTime()){
//            //First time user
//            System.out.println("First time user");
//            if(checkInternetConnection(activity)){
//                //Have internet
//                System.out.println("Have Internet");
//                // Add data
//            } else {
//                // No Internet
//                System.out.println("No Internet");
//                // Display a popup request and exit the app
//            }
//        } else {
//            //Not first time using
//            System.out.println("Not first time user");
//            if(checkInternetConnection(activity)){
//                //Have internet
//                System.out.println("Have Internet");
//                if(checkUpdate(activity)){
//                    // have update
//                    System.out.println("Check Update: true");
//                    // delete the current realm data and insert again.
//                } else {
//                    // no update
//                    System.out.println("Check Update: false");
//                }
//            } else {
//                // No Internet
//                System.out.println("No Internet");
//            }
//        }
//    }
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
//    public List<Speaker> getAllSpeaker(){
//        return realm.where(Speaker.class).findAll();
//    }
    
//    public Speaker getSpeakerByID(String id){
//        return realm.where(Speaker.class).equalTo("speaker_id", id).findFirst();
//    }

    public void insertData (Activity activity){

//        if(checkFirstTime()){
//            System.out.println("Had no value");
//            initData(activity);
//        } else {
//            if(checkUpdate()){
//                System.out.println("Have new update");
//                deleteData();
//                insertData(activity);
//            } else {
//                System.out.println("No update");
//            }
//        }

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

//    public void insertUpdateCode (Activity activity){
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
//                        System.out.println("Check 1U: " + String.valueOf(id) + " , " + String.valueOf(speakerUpdateCode) + " , " + String.valueOf(eventUpdateCode)  + " . ");
//                        codeList.add(new UpdateCode(id,speakerUpdateCode,eventUpdateCode));
//                        System.out.println("Check 2U: " + String.valueOf(codeList.get(0).getSpeakerCode()));
//                        realm.beginTransaction();
//                        realm.copyToRealm(codeList);
//                        realm.commitTransaction();
//                        System.out.println("Inserted");
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
//    }
//    public void insertSpeaker (Activity context){
//
//        StringRequest requestSpeaker = new StringRequest(urlSpeaker, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for(int i=0; i<jsonArray.length(); i++){
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String speakerID = object.getString("id");
//                        String speakerName = object.getString("name");
//                        String speakerAvatar = urlImageLocation + object.getString("avatar");
//                        String speakerDesc = object.getString("description");
//                        System.out.println("Check 1S: " + speakerID + " , " + speakerName+ " , " + speakerAvatar + " . ");
//                        speakerList.add(new Speaker(speakerID, speakerName, speakerAvatar, speakerDesc));
//                    }
//                    realm.beginTransaction();
//                    realm.copyToRealm(speakerList);
//                    realm.commitTransaction();
//                    System.out.println("Inserted");
//                    System.out.println("Check 2S: " + speakerList.get(0).getName() + " , " + speakerList.get(1).getName()+ " , " + speakerList.get(2).getName() + " . ");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue speakerQueue = Volley.newRequestQueue(context);
//        speakerQueue.add(requestSpeaker);
//    }

    public void initData(Activity activity){
//        insertUpdateCode(activity);
//        insertSpeaker(activity);

    }


}
