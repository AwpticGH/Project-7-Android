package g10.manga.comicable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.helper.ImageHelper;
import g10.manga.comicable.model.manga.ChapterListModel;
import g10.manga.comicable.model.manga.PopularModel;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ViewHolder> {

    private List<ChapterListModel> data;
    private Context context;
    private ChapterListAdapter.OnObjectSelected onObjectSelected;
    private int layoutId;
    private int btnChapterNameId;

    public ChapterListAdapter(List<ChapterListModel> data, Context context, ChapterListAdapter.OnObjectSelected onObjectSelected, int layoutId, int btnChapterNameId) {
        this.data = data;
        this.context = context;
        this.onObjectSelected = onObjectSelected;
        this.layoutId = layoutId;
        this.btnChapterNameId = btnChapterNameId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chapter, parent, false);
        return new ViewHolder(view, layoutId, btnChapterNameId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ChapterListModel model = data.get(position);

        holder.button.setText(model.getName());
        holder.button.setOnClickListener(view -> {
            onObjectSelected.onSelected(model);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnObjectSelected {
        void onSelected(ChapterListModel model);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private Button button;

        public ViewHolder(@NonNull View itemView, int layoutId, int buttonId) {
            super(itemView);
            layout = itemView.findViewById(layoutId);
            button = itemView.findViewById(buttonId);
        }
    }

}
