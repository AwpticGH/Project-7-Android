package g10.manga.comicable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.helper.ImageHelper;
import g10.manga.comicable.model.manga.ChapterModel;
import g10.manga.comicable.model.manga.ListModel;

public class ChapterAdapter extends ArrayAdapter<String> {

    private ChapterModel data;
    private Context context;
    private int layoutId;
    private int tvChapterNameId;
    private int imageViewId;

    public ChapterAdapter(@NonNull Context context, int resource, @NonNull ChapterModel object, int tvChapterNameId, int imageViewId) {
        super(context, resource, object.getImages());
        this.data = object;
        this.context = context;
        this.layoutId = resource;
        this.tvChapterNameId = tvChapterNameId;
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
        TextView tvChapterName = convertedView.findViewById(tvChapterNameId);
        ImageView imageView = convertedView.findViewById(imageViewId);

        tvChapterName.setText(data.getTitle());
        ImageHelper imageHelper = new ImageHelper(imageView, data.getImages().get(position));
        imageHelper.start();

        return convertedView;
    }

}
