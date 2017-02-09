package first.com.movie_player;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Cursor cursor;
    int count;
    private List<String> songs = new ArrayList<>();
    private List<String> location = new ArrayList<>();
    DBHandler db;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        db=new DBHandler(getApplicationContext());
        db.resetTable_Records();

        init_phone_video_grid();
        initList();

    }



    private void initList() {
        RecyclerView list = (RecyclerView) findViewById(R.id.mulist);


        VideoAdapter madapter = new VideoAdapter(getApplicationContext(),songs,location);



        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        list.setAdapter(madapter);
        list.setLayoutManager(lm);
        list.setItemAnimator(new DefaultItemAnimator());
        madapter.notifyDataSetChanged();

    }


    private void init_phone_video_grid() {

        String[] proj = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE };
        cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        count = cursor.getCount();
        for (int i =0;i<count ;i++)
        {
            int column_index = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
            cursor.moveToPosition(i);
            songs.add(cursor.getString(column_index));
        }
        for (int i =0;i<count ;i++)
        {
            cursor.moveToPosition(i);
            int column_index = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            location.add(cursor.getString(column_index));
        }

    }
}
