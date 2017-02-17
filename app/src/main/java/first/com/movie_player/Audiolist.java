package first.com.movie_player;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Audiolist extends AppCompatActivity implements View.OnClickListener {


    int count;
    DBHandler db;
    private GridLayoutManager lLayout;
    private Cursor cursor;

    private List<String> songs = new ArrayList<>();
    private List<String> location = new ArrayList<>();

    Button audio, video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiolist);


        audio = (Button) findViewById(R.id.audio);
        video = (Button) findViewById(R.id.video);

        db=new DBHandler(getApplicationContext());
        db.resetTable_Records();



        audio.setOnClickListener(this);
        video.setOnClickListener(this);
        init_phone_audio_grid();
        initList();


    }

    private void initList() {

        lLayout = new GridLayoutManager(Audiolist.this, 2);

        RecyclerView list = (RecyclerView) findViewById(R.id.mulist);

        AudioAdapter madapter = new AudioAdapter(getApplicationContext(), songs, location);

        list.setLayoutManager(lLayout);

        list.setAdapter(madapter);
        list.setItemAnimator(new DefaultItemAnimator());
        madapter.notifyDataSetChanged();


    }

    private void init_phone_audio_grid() {


        String[] proj = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE};
        cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        count = cursor.getCount();
        for (int i = 0; i < count; i++) {
            int column_index = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            cursor.moveToPosition(i);
            songs.add(cursor.getString(column_index));
        }
        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int column_index = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            location.add(cursor.getString(column_index));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video: {
                Intent intent = new Intent(Audiolist.this, MovieList.class);
                startActivity(intent);

            }
            case R.id.audio: {
                Intent intent = new Intent(Audiolist.this, Audiolist.class);
                startActivity(intent);
            }
        }
    }
}
