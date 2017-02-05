package first.com.movie_player;

import android.content.Context;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.List;

public class MovieList extends AppCompatActivity {

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

        db=new DBHandler(getApplicationContext());
        location=db.access_data();

        getWindow().setFormat(PixelFormat.UNKNOWN);
        mc=new MediaController(this);
        uri=uri.parse(location.get(i));
        Log.d("upro123", String.valueOf(uri));
        play= (Button) findViewById(R.id.play);
        videov= (VideoView) findViewById(R.id.video);
        i++;
        videov.setVideoURI(uri);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.GONE);
                videov.setMediaController(mc);
                mc.setAnchorView(videov);
                videov.start();

            }
        });

    }
}
