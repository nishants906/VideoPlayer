package first.com.movie_player;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

public class MovieList extends AppCompatActivity {

    VideoView videov;
    Uri uri;
    public MovieList(Uri uri) {
        this.uri=uri;
        Log.d("confirm1", String.valueOf(uri));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        videov= (VideoView) findViewById(R.id.video);
        videov.setVideoURI(uri);
        videov.start();


    }
}
