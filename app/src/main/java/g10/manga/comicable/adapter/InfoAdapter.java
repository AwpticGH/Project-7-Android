package g10.manga.comicable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import g10.manga.comicable.helper.ImageHelper;
import g10.manga.comicable.model.manga.ChapterListModel;
import g10.manga.comicable.model.manga.ChapterModel;
import g10.manga.comicable.model.manga.InfoModel;

public class InfoAdapter extends ArrayAdapter<ChapterListModel> {

    private InfoModel data;
    private Context context;
    private int layoutId;
    private int imageViewId;
    private int tvTitleId;
    private int tvTypeId;
    private int tvAuthorId;
    private int tvStatusId;
    private int tvRatingId;
    private int tvGenreId;
    private int tvChapterNameId;

    public InfoAdapter(@NonNull Context context, int resource, InfoModel object, int layoutId, int imageViewId, int tvTitleId, int tvTypeId, int tvAuthorId, int tvStatusId, int tvRatingId, int tvGenreId, int tvChapterNameId) {
        super(context, resource, object.getChapterList());
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
        this.imageViewId = imageViewId;
        this.tvTitleId = tvTitleId;
        this.tvTypeId = tvTypeId;
        this.tvAuthorId = tvAuthorId;
        this.tvStatusId = tvStatusId;
        this.tvRatingId = tvRatingId;
        this.tvGenreId = tvGenreId;
        this.tvChapterNameId = tvChapterNameId;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertedView, ViewGroup parent) {
        convertedView = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);
        ImageView imageView = convertedView.findViewById(imageViewId);
        TextView tvTitle = convertedView.findViewById(tvTitleId);
        TextView tvType = convertedView.findViewById(tvTypeId);
        TextView tvAuthor = convertedView.findViewById(tvAuthorId);
        TextView tvStatus = convertedView.findViewById(tvStatusId);
        TextView tvRating = convertedView.findViewById(tvRatingId);
        TextView tvGenre = convertedView.findViewById(tvGenreId);
        TextView tvChapterName = convertedView.findViewById(tvChapterNameId);

        tvTitle.setText(data.getTitle());
        tvType.setText(data.getType());
        tvAuthor.setText(data.getAuthor());
        tvStatus.setText(data.getStatus());
        tvRating.setText(data.getRating());
        tvGenre.setText(Arrays.toString(data.getGenres().toArray()));
        tvChapterName.setText(data.getChapterList().get(position).getName());
        ImageHelper imageHelper = new ImageHelper(imageView, data.getThumbnail());
        imageHelper.start();

        return convertedView;
    }
}
