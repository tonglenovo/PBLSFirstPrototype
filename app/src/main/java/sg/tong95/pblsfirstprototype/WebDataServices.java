package sg.tong95.pblsfirstprototype;

import android.app.ProgressDialog;
import android.content.Context;
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

import sg.tong95.pblsfirstprototype.Model.Speaker;
import sg.tong95.pblsfirstprototype.Model.UpdateCode;

public class WebDataServices {

    Context context;
    //int speakerUpdateCode;
    List<UpdateCode> updateCodeList;
    List<Speaker> speakerList;

    public static final String URL_UPDATE_CODE = "https://ipbls.herokuapp.com/WebServices/getUpdateCode.php";
    public static final String URL_SPEAKER = "https://ipbls.herokuapp.com/WebServices/getAllSpeaker.php";
    public static final String URL_SPEAKER_IMAGE = "https://ipbls.herokuapp.com/upload/";
    public static final String URL_EVENT = "";

    public WebDataServices(Context context){
        this.context = context;
    }

    public interface getUpdateListener{
        void onResponse(List<UpdateCode> response);
        void onError(String error);
    }

    public interface getSpeakerListener{
        void onResponse(List<Speaker> response);
        void onError(String error);
    }

    public void getUpdateCode(getUpdateListener getUpdateListener){
        StringRequest requestUpdateCode = new StringRequest(URL_UPDATE_CODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    //speakerUpdateCode = 0;
                    updateCodeList = new ArrayList<UpdateCode>();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        int id = jsonArray.getJSONObject(0).getInt("id");
                        int speakerUpdateCode = jsonArray.getJSONObject(0).getInt("speakerCode");
                        int eventUpdateCode =jsonArray.getJSONObject(0).getInt("eventCode");
                        updateCodeList.add(new UpdateCode(id, speakerUpdateCode,eventUpdateCode));
                        getUpdateListener.onResponse(updateCodeList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                getUpdateListener.onError("Something Wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(requestUpdateCode);
        //return speakerUpdateCode;
    }

    public void getSpeakerList(getSpeakerListener listener){
        StringRequest requestSpeaker = new StringRequest(URL_SPEAKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                speakerList = new ArrayList<Speaker>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String speakerID = object.getString("id");
                        String speakerName = object.getString("name");
                        String speakerAvatar = URL_SPEAKER_IMAGE + object.getString("avatar");
                        String speakerDesc = object.getString("description");
                        //System.out.println("Check 1S: " + speakerID + " , " + speakerName+ " , " + speakerAvatar + " . ");
                        speakerList.add(new Speaker(speakerID, speakerName, speakerAvatar, speakerDesc));
                        listener.onResponse(speakerList);
                    }
                    //System.out.println("Check 2S: " + speakerList.get(0).getName() + " , " + speakerList.get(1).getName()+ " , " + speakerList.get(2).getName() + " . ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(requestSpeaker);
    }
}
