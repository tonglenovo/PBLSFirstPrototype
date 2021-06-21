package sg.tong95.pblsfirstprototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import sg.tong95.pblsfirstprototype.Model.Presentation;
import sg.tong95.pblsfirstprototype.Model.Speaker;

public class PresentationAdapter extends RecyclerView.Adapter<PresentationAdapter.ViewHolder> {

    Context context;
    List<Presentation> presentationList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvPresentationText;
        ImageView ivPresentationImage;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvPresentationText = itemView.findViewById(R.id.tvPresentationText);
            ivPresentationImage = itemView.findViewById(R.id.ivPresentationImage);
        }
    }

    public PresentationAdapter(Context context, List<Presentation> presentationList) {
        this.context = context;
        this.presentationList = presentationList;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_presentation, parent, false);
        PresentationAdapter.ViewHolder viewHolder = new PresentationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Presentation presentation = presentationList.get(position);
        holder.tvPresentationText.setText(presentation.getPresentation());
        switch (position){
            case 0:
                Glide.with(context).load(R.drawable.seminar).into(holder.ivPresentationImage);
                break;
            case 1:
                Glide.with(context).load(R.drawable.workshop).into(holder.ivPresentationImage);
                break;
            case 2:
                Glide.with(context).load(R.drawable.pbl).into(holder.ivPresentationImage);
                break;
            case 3:
                Glide.with(context).load(R.drawable.roundtable).into(holder.ivPresentationImage);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, presentation.getPresentation(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ProgrammeActivity.class);
                i.putExtra("name", "presentation");
                i.putExtra("p_id", presentation.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return presentationList.size();
    }
}
