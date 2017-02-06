package first.com.movie_player;

import android.content.Context;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.swipper.library.Swipper;

import java.util.List;

public class MovieList extends Swipper {

    Uri uri;
    VideoView videov;
    Button play;
    Context context;
    String loc;
    DBHandler db;
    List<String> location=null;
    MediaController mc;
    int i=0;

    public MovieList() {

    }

    public MovieList(Context context) {
        this.context = context;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        set(this);

        db=new DBHandler(getApplicationContext());
        location=db.access_data();

        getWindow().setFormat(PixelFormat.UNKNOWN);
        mc=new MediaController(this);
        uri=uri.parse(location.get(i));
        Log.d("upro123", String.valueOf(uri));
        videov= (VideoView) findViewById(R.id.video);
        videov.setVideoURI(uri);

                DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videov.getLayoutParams();
                params.width =  metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                videov.setLayoutParams(params);

                Brightness(Orientation.VERTICAL);
                Volume(Orientation.CIRCULAR);
                Seek(Orientation.HORIZONTAL,videov);


                videov.setMediaController(mc);
                mc.setAnchorView(videov);
                videov.start();

    }
}
