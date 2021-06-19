package sg.tong95.pblsfirstprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import sg.tong95.pblsfirstprototype.Model.Speaker;

public class SpeakerDetail extends AppCompatActivity {

    ImageView ivSpeakerDetailAvatar;
    TextView tvSpeakerDetailName, tvSpeakerDetailDesc;
    RealmHelper helper;
    Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_detail);

        String id = getIntent().getStringExtra("id");
        helper = new RealmHelper(SpeakerDetail.this);
        speaker = helper.getSpeakerByID(id);

        tvSpeakerDetailName = findViewById(R.id.tvSpeakerDetailName);
        tvSpeakerDetailDesc = findViewById(R.id.tvSpeakerDetailDesc);
        ivSpeakerDetailAvatar = findViewById(R.id.ivSpeakerDetailAvatar);

        tvSpeakerDetailName.setText(speaker.getName());
        tvSpeakerDetailDesc.setText(speaker.getDescription());
        Glide.with(SpeakerDetail.this).load(speaker.getAvatar()).into(ivSpeakerDetailAvatar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}