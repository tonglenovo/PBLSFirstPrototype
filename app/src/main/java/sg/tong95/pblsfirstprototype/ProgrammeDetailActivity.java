package sg.tong95.pblsfirstprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import sg.tong95.pblsfirstprototype.Model.Event;

public class ProgrammeDetailActivity extends AppCompatActivity {

    TextView tvEDTitle, tvEDLocation, tvEDStartTime, tvEDEndTime, tvEDSpeaker, tvEDDesc;
    RealmHelper helper;
    Event eventList;
    String speakerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new RealmHelper(ProgrammeDetailActivity.this);
        String id = getIntent().getStringExtra("event_id");
        eventList = helper.getEventByID(id);
        speakerName = helper.getSpeakerNameByIDString(eventList.getSpeaker_id());
//        System.out.println("CheckName: " + speakerName);

        initTextView();


    }

    private void initTextView(){
        tvEDTitle = findViewById(R.id.tvEDTitle);
        tvEDLocation = findViewById(R.id.tvEDLocation);
        tvEDStartTime = findViewById(R.id.tvEDStartTime);
        tvEDEndTime = findViewById(R.id.tvEDEndTime);
        tvEDSpeaker = findViewById(R.id.tvEDSpeaker);
        tvEDDesc = findViewById(R.id.tvEDDesc);

        tvEDTitle.setText(eventList.getTitle());
        tvEDLocation.setText(eventList.getLocation());
        tvEDStartTime.setText(eventList.getDate()+ " " + eventList.getStartTime());
        tvEDEndTime.setText(eventList.getDate()+ " " + eventList.getEndTime());
        tvEDSpeaker.setText(speakerName);
        tvEDDesc.setText(eventList.getDetail());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return true;
    }
}