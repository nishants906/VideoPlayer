package first.com.movie_player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MovieList extends AppCompatActivity {

    VideoView videov;
    Uri uri;
    Button play;
    Context context;

    public MovieList(Context context, Uri uri) {
        this.context=context;
        this.uri=uri;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        play= (Button) findViewById(R.id.play);
        videov= (VideoView) findViewById(R.id.video);
        videov.setVideoURI(uri);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                videov.start();

            }
        });

    }
}
