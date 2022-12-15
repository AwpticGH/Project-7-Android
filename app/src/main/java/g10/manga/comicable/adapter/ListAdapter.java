package g10.manga.comicable.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.net.URI;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.helper.ImageHelper;
import g10.manga.comicable.model.manga.ListModel;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListModel> data;
    private Context context;
    private ListAdapter.OnObjectSelected onObjectSelected;
    private int cardViewId;
    private int textViewId;
    private int imageViewId;

    public ListAdapter(List<ListModel> data, Context context, ListAdapter.OnObjectSelected onObjectSelected,
                       int cardViewId, int textViewId, int imageViewId) {
        this.data = data;
        this.context = context;
        this.onObjectSelected = onObjectSelected;
        this.cardViewId = cardViewId;
        this.textViewId = textViewId;
        this.imageViewId = imageViewId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_genre, parent, false);
        return new ViewHolder(view, cardViewId, textViewId, imageViewId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListModel model = data.get(position);

        Glide.with(context)
                .load(model.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .into(holder.imageView);

        holder.tvTitle.setText(model.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onObjectSelected.onObjectSelected(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnObjectSelected {
        void onObjectSelected(ListModel model);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tvTitle;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView, int cardViewId, int tvTitleId, int imageViewId) {
            super(itemView);
            this.cardView = itemView.findViewById(cardViewId);
            this.tvTitle = itemView.findViewById(tvTitleId);
            this.imageView = itemView.findViewById(imageViewId);
        }
    }

}
