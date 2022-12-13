package g10.manga.comicable.helper;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageHelper extends Thread {

    private String url;
    private Bitmap bitmap;
    private ImageView imageView;
    private boolean running;

    public ImageHelper(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
        running = true;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void run() {
        InputStream inputStream = null;

        while (running) {
            try {
                sleep(1000);
                inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                Log.d("Image Helper : decodeStream", "Success");
            } catch (Exception e) {
                Log.e("Image Helper : decodeStream", "Fail : " + e.getLocalizedMessage());
                // makeToast
            }

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                Log.d("Image Helper : setImageBitmap", "Success");
            } else {
                Log.e("Image Helper : setImageBitmap", "Fail");
                // makeToast
            }

            this.running = false;
        }
    }

}
