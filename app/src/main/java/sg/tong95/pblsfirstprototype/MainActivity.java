package sg.tong95.pblsfirstprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    ImageView ivProgramme,ivPresentation,ivSpeaker,ivAnnouncement, ivFavourite, ivInformation;
    RealmHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageOnClick();

        // CheckInternet connection for update
        // checkInternet true initCheckUpdate
        // checkInternet false ask user to connect wifi and try again.

        Realm.init(getApplicationContext());
        helper = new RealmHelper(MainActivity.this);
        helper.testTrial(MainActivity.this);


    }

    public void imageOnClick(){

        ivProgramme = findViewById(R.id.ivProgramme);
        ivPresentation = findViewById(R.id.ivPresentation);
        ivSpeaker = findViewById(R.id.ivSpeaker);
        ivAnnouncement = findViewById(R.id.ivAnnouncement);
        ivFavourite = findViewById(R.id.ivFavourite);
        ivInformation = findViewById(R.id.ivInformation);


        ivProgramme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Programme", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, ProgrammeActivity.class);
                i.putExtra("name", "programme");
                startActivity(i);
            }
        });

        ivPresentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Presentation", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, PresentationActivity.class);
                startActivity(i);
            }
        });

        ivSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Speaker", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, SpeakerActivity.class);
                startActivity(i);
            }
        });

        ivAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Announcement", Toast.LENGTH_SHORT).show();
            }
        });

        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Favourite", Toast.LENGTH_SHORT).show();
            }
        });

        ivInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
            }
        });
    }



}