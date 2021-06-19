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

import java.util.List;

import sg.tong95.pblsfirstprototype.Model.Event;
import sg.tong95.pblsfirstprototype.Model.Speaker;

public class ProgrammeRecyclerViewAdapter extends RecyclerView.Adapter<ProgrammeRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Event> events;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventTitle, tvEventStartTime;
        ImageView ivEventImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventStartTime = itemView.findViewById(R.id.tvEventStartTime);
            ivEventImage = itemView.findViewById(R.id.ivEventImage);
        }
    }

    public ProgrammeRecyclerViewAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ProgrammeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_programme, parent, false);
        ProgrammeRecyclerViewAdapter.ViewHolder viewHolder = new ProgrammeRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammeRecyclerViewAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);
        System.out.println("Check: " + event.getDate());
        holder.tvEventTitle.setText(event.getTitle());
        holder.tvEventStartTime.setText(event.getDate() + " " + event.getStartTime());
        if(event.getPresentation_id().equals("1")){
            Glide.with(context).load(R.drawable.seminar).into(holder.ivEventImage);
        } else if(event.getPresentation_id().equals("2")){
            Glide.with(context).load(R.drawable.workshop).into(holder.ivEventImage);
        } else if(event.getPresentation_id().equals("3")){
            Glide.with(context).load(R.drawable.pbl).into(holder.ivEventImage);
        } else if(event.getPresentation_id().equals("4")){
            Glide.with(context).load(R.drawable.roundtable).into(holder.ivEventImage);
        }
        //Picasso.get().load(speaker.getAvatar()).rotate(90).into(holder.ivSpeakerAvatar);



//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, SpeakerDetail.class);
//                i.putExtra("id", speaker.getSpeaker_id());
//                context.startActivity(i);
//                System.out.println(speaker.getName() + " Is tapped");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

}


