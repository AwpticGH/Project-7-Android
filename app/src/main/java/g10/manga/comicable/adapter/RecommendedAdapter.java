package g10.manga.comicable.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import g10.manga.comicable.model.manga.RecommendedModel;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private List<RecommendedModel> data;
    private Context context;
    private RecommendedAdapter.OnObjectSelected onObjectSelected;
    private int cardViewId;
    private int tvTitleId;
    private int imageViewId;
    private int tvDescId;
    private int tvTypeId;

    public RecommendedAdapter(List<RecommendedModel> data, Context context, RecommendedAdapter.OnObjectSelected onObjectSelected,
                          int cardViewId, int tvTitleId, int imageViewId, int tvDescId, int tvTypeId) {
        this.data = data;
        this.context = context;
        this.onObjectSelected = onObjectSelected;
        this.cardViewId = cardViewId;
        this.tvTitleId = tvTitleId;
        this.imageViewId = imageViewId;
        this.tvDescId = tvDescId;
        this.tvTypeId = tvTypeId;
    }

    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new RecommendedAdapter.ViewHolder(view, cardViewId, tvTitleId, imageViewId, tvDescId, tvTypeId);
    }

    @Override
    public void onBindViewHolder(RecommendedAdapter.ViewHolder holder, int position) {
        final RecommendedModel model = data.get(position);

        if (model.getType().equals("Manhua"))
            holder.tvType.setTextColor(Color.parseColor("#ff27AE60"));
        else if (model.getType().equals("Manhwa"))
            holder.tvType.setTextColor(Color.parseColor("#ffF2994A"));
        else if (model.getType().equals("Manga"))
            holder.tvType.setTextColor(Color.parseColor("#ffE8505B"));

        Glide.with(context)
                .load(model.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .into(holder.imageView);

        holder.tvTitle.setText(model.getTitle());
        holder.tvDesc.setText(model.getDescription());
        holder.tvType.setText(model.getType());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onObjectSelected.onSelected(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnObjectSelected {
        void onSelected(RecommendedModel model);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tvTitle;
        private ImageView imageView;
        private TextView tvDesc;
        private TextView tvType;

        public ViewHolder(@NonNull View itemView, int cardViewId, int tvTitleId, int imageViewId, int tvDescId, int tvTypeId) {
            super(itemView);
            cardView = itemView.findViewById(cardViewId);
            tvTitle = itemView.findViewById(tvTitleId);
            imageView = itemView.findViewById(imageViewId);
            tvDesc = itemView.findViewById(tvDescId);
            tvType = itemView.findViewById(tvTypeId);
        }
    }
}
