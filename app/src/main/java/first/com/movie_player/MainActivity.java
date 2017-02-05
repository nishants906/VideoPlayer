package first.com.movie_player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);


        db=new DBHandler(getApplicationContext());
        db.resetTable_Records();


    }

        @Override
        public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
            switch (requestCode) {
                case 1: {

                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        init_phone_video_grid();
                        initList();


                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
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
