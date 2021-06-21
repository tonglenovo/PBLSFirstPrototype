package sg.tong95.pblsfirstprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import sg.tong95.pblsfirstprototype.Model.Presentation;

public class PresentationActivity extends AppCompatActivity {

    RecyclerView rvPresentationList;
    RealmHelper helper;
    PresentationAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Presentation> presentationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvPresentationList = findViewById(R.id.rvPresentationList);
        helper = new RealmHelper(PresentationActivity.this);
        presentationList = helper.getAllPresentation();
        rvPresentationList.setHasFixedSize(true);
        rvPresentationList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager = new LinearLayoutManager(this);
        rvPresentationList.setLayoutManager(layoutManager);
        adapter = new PresentationAdapter(PresentationActivity.this, presentationList);
        rvPresentationList.setAdapter(adapter);
    }
}