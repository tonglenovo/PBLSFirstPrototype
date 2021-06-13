package sg.tong95.pblsfirstprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import sg.tong95.pblsfirstprototype.Model.Speaker;

public class SpeakerActivity extends AppCompatActivity {

    RecyclerView rvSpeaker;
    SpeakerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RealmHelper helper;
    List<Speaker> list = new ArrayList<Speaker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new RealmHelper();
        list = helper.getAllSpeaker();

        rvSpeaker = findViewById(R.id.rvSpeaker);
        rvSpeaker.setHasFixedSize(true);
        rvSpeaker.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager = new LinearLayoutManager(this);
        rvSpeaker.setLayoutManager(layoutManager);
        adapter = new SpeakerAdapter(SpeakerActivity.this,list);
        rvSpeaker.setAdapter(adapter);


        System.out.println("Check List: " + list.get(1).getName());
    }
}