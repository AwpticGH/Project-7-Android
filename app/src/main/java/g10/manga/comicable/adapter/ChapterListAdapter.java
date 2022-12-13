package g10.manga.comicable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import g10.manga.comicable.helper.ImageHelper;
import g10.manga.comicable.model.manga.ChapterListModel;
import g10.manga.comicable.model.manga.PopularModel;

public class ChapterListAdapter extends ArrayAdapter<ChapterListModel> {

    private List<ChapterListModel> data;
    private Context context;
    private int layoutId;
    private int tvTitleId;
    private int imageViewId;

    public ChapterListAdapter(@NonNull Context context, int resource, @NonNull List<ChapterListModel> objects, int tvTitleId, int imageViewId) {
        super(context, resource, objects);
        this.data = objects;
        this.context = context;
        this.layoutId = resource;
        this.tvTitleId = tvTitleId;
        this.imageViewId = imageViewId;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertedView, ViewGroup parent) {
        convertedView = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);
        TextView tvTitle = convertedView.findViewById(tvTitleId);
        ImageView imageView = convertedView.findViewById(imageViewId);

        tvTitle.setText(data.get(position).getName());
        ImageHelper imageHelper = new ImageHelper(imageView, data.get(position).getEndpoint());
        imageHelper.start();

        return convertedView;
    }

}
