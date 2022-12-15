package g10.manga.comicable.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.helper.ImageHelper;
import g10.manga.comicable.model.manga.ChapterModel;
import g10.manga.comicable.model.manga.ListModel;

public class ChapterAdapter extends PagerAdapter {

    private ChapterModel data;
    private Context context;
    private int layoutId;
    private int tvPaginationId;
    private int imageViewId;

    public ChapterAdapter(ChapterModel data, Context context, int layoutId, int tvPaginationId, int imageViewId) {
        this.data = data;
        this.context = context;
        this.layoutId = layoutId;
        this.tvPaginationId = tvPaginationId;
        this.imageViewId = imageViewId;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return data.getImages().size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context)
                .inflate(layoutId, container, false);

        final String image = data.getImages().get(position);

        TextView tvPagination = view.findViewById(tvPaginationId);
        ImageView imageView = view.findViewById(imageViewId);

        String txtPagination = "Hal. " + position;
        tvPagination.setText(txtPagination);

        Glide.with(context)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .into(imageView);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
