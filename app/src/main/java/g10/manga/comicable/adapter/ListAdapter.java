package g10.manga.comicable.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.helper.ImageHelper;
import g10.manga.comicable.model.manga.ListModel;

public class ListAdapter extends ArrayAdapter<ListModel> {

    private List<ListModel> data;
    private Context context;
    private int layoutId;
    private int textViewId;
    private int imageViewId;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<ListModel> objects, int textViewId, int imageViewId) {
        super(context, resource, objects);
        this.data = objects;
        this.context = context;
        this.layoutId = resource;
        this.textViewId = textViewId;
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
        TextView text = convertedView.findViewById(textViewId);
        ImageView image = convertedView.findViewById(imageViewId);

        text.setText(data.get(position).getTitle());
        ImageHelper imageHelper = new ImageHelper(image, data.get(position).getEndpoint());
        imageHelper.start();

        return convertedView;
    }

}
