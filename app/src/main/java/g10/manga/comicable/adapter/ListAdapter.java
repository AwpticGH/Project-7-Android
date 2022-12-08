package g10.manga.comicable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.model.manga.ListModel;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListModel> data;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;

    public ListAdapter(Context context, List<ListModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    // Binding data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getTitle());

        // set image here
    }

    // Getting total number of cells
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle;
        ImageView imgThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.text_call_list_result);
            txtTitle.setOnClickListener(this);

            imgThumbnail = itemView.findViewById(R.id.image_thumbnail);
            imgThumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Convenience method for getting data at click position
    public ListModel getItem(int id) {
        return data.get(id);
    }

    // Allows click events to be caught
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
