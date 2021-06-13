package sg.tong95.pblsfirstprototype;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sg.tong95.pblsfirstprototype.Model.Speaker;

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.ViewHolder> {
    Context context;
    List<Speaker> speakers;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvSpeakerName;
        ImageView ivSpeakerAvatar;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvSpeakerName = itemView.findViewById(R.id.tvSpeakerName);
            ivSpeakerAvatar = itemView.findViewById(R.id.ivSpeakerAvatar);
        }
    }

    public SpeakerAdapter(Context context, List<Speaker> speakers) {
        this.context = context;
        this.speakers = speakers;
    }

    @NonNull
    @Override
    public SpeakerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_speaker, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpeakerAdapter.ViewHolder holder, int position) {
        Speaker speaker = speakers.get(position);
        holder.tvSpeakerName.setText(speaker.getName());
        System.out.println("Speaker Img: " + speaker.getAvatar());
        //Picasso.get().load(speaker.getAvatar()).rotate(90).into(holder.ivSpeakerAvatar);

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        dialog.show();

        Glide.with(context)
                .load(speaker.getAvatar())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        dialog.dismiss();
                        return false;
                    }
                })
                .into(holder.ivSpeakerAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SpeakerDetail.class);
                i.putExtra("id", speaker.getSpeaker_id());
                context.startActivity(i);
                System.out.println(speaker.getName() + " Is tapped");
            }
        });
    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }
}
