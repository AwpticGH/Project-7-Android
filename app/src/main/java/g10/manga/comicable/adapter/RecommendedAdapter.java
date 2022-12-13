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
import g10.manga.comicable.model.manga.RecommendedModel;

public class RecommendedAdapter extends ArrayAdapter<RecommendedModel> {

    private List<RecommendedModel> data;
    private Context context;
    private int layoutId;
    private int tvTitleId;
    private int imageViewId;
    private int tvDescId;
    private int tvTypeId;

    public RecommendedAdapter(@NonNull Context context, int resource, @NonNull List<RecommendedModel> objects, int tvTitleId, int imageViewId, int tvDescId, int tvTypeId) {
        super(context, resource, objects);
        this.data = objects;
        this.context = context;
        this.layoutId = resource;
        this.tvTitleId = tvTitleId;
        this.imageViewId = imageViewId;
        this.tvDescId = tvDescId;
        this.tvTypeId = tvTypeId;
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
        TextView tvDesc = convertedView.findViewById(tvDescId);
        TextView tvType = convertedView.findViewById(tvTypeId);

        tvTitle.setText(data.get(position).getTitle());
        tvDesc.setText(data.get(position).getDescription());
        tvType.setText(data.get(position).getType());
        ImageHelper imageHelper = new ImageHelper(imageView, data.get(position).getEndpoint());
        imageHelper.start();

        return convertedView;
    }
}
