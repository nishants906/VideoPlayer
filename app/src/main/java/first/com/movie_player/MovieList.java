package first.com.movie_player;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SettingsFragment.SettingsFragmentCallbackListener {


    public static int themeColor = Color.parseColor("#B24242");

    Cursor cursor;
    int count;
    private List<String> videos = new ArrayList<>();
    private List<String> location = new ArrayList<>();
    DBHandler db;
    private GridLayoutManager lLayout;
    DrawerLayout drawer;


    NavigationView navigationView;

    public MovieList() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        navigationView.setCheckedItem(R.id.video);

        db=new DBHandler(getApplicationContext());
        db.resetTable_Records();
        db.add_data(0);


        init_phone_video_grid();
        initList();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home_drawer, menu);//Menu Resource, Menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "settings Selected", Toast.LENGTH_LONG).show();
                showFragment("settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onColorChanged() {
        navigationView.setBackgroundColor(themeColor);
        navigationView.setItemIconTintList(ColorStateList.valueOf(themeColor));

    }

    @Override
    public void onAlbumArtBackgroundChangedVisibility(int visibility) {

    }

    @Override
    public void onAboutClicked() {
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
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.video) {
            Intent intent=new Intent(MovieList.this,MovieList.class);
            startActivity(intent);
        } else if (id == R.id.audio) {
            showFragment("audio");
        } else if (id == R.id.settings) {
            showFragment("settings");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(String type) {

        if (type == "settings") {
            SettingsFragment newFragment = new SettingsFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            if (newFragment == null) {
                newFragment = new SettingsFragment();
            }
            fm.beginTransaction()
                    .setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_right,
                            R.anim.slide_left,
                            R.anim.slide_right)
                    .add(R.id.fragContainer, newFragment, "settings")
                    .show(newFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
        else if(type=="audio"){

            Audio newFragment = new Audio();
            FragmentManager fm = getSupportFragmentManager();
            if (newFragment == null) {
                newFragment = new Audio();
            }
            fm.beginTransaction()
                    .setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_right,
                            R.anim.slide_left,
                            R.anim.slide_right)
                    .add(R.id.fragContainer, newFragment, "Audio")
                    .replace(R.id.fragContainer,newFragment)
                    .addToBackStack(null)
                    .commit();


        }
     }
}

