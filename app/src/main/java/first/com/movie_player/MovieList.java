package first.com.movie_player;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;
import java.util.List;

public class MovieList extends AppCompatActivity implements View.OnClickListener {
    Cursor cursor;
    int count;
    private List<String> videos = new ArrayList<>();
    private List<String> location = new ArrayList<>();
    DBHandler db;
    private GridLayoutManager lLayout;
    FlowingDrawer mDrawer;
    Button video,audio;

    public MovieList() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MovieList", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MovieList", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });


        video= (Button) findViewById(R.id.video);
        audio= (Button) findViewById(R.id.audio);

        video.setOnClickListener(this);
        audio.setOnClickListener(this);

        db=new DBHandler(getApplicationContext());
        db.resetTable_Records();
        db.add_data(0);


        init_phone_video_grid();
        initList();


    }



    private void initList() {

        lLayout = new GridLayoutManager(MovieList.this,2);

        RecyclerView list = (RecyclerView) findViewById(R.id.mulist);

        VideoAdapter madapter = new VideoAdapter(getApplicationContext(),videos,location);

        list.setLayoutManager(lLayout);

        list.setAdapter(madapter);
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
            videos.add(cursor.getString(column_index));
        }
        for (int i =0;i<count ;i++)
        {
            cursor.moveToPosition(i);
            int column_index = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            location.add(cursor.getString(column_index));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video:{
                Intent intent=new Intent(MovieList.this,MovieList.class);
                startActivity(intent);

            }
            case R.id.audio:{
                Intent intent=new Intent(MovieList.this,Audiolist.class);
                startActivity(intent);
            }

        }
    }
}