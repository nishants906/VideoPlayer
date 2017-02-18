package first.com.movie_player;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Audio extends Fragment {


    int count;
    DBHandler db;
    private GridLayoutManager lLayout;
    private Cursor cursor;

    RecyclerView list;
    private List<String> songs = new ArrayList<>();
    private List<String> location = new ArrayList<>();


    public Audio() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_audio, container, false);


        list = (RecyclerView) rootview.findViewById(R.id.mulist);

        db=new DBHandler(getContext());
        db.resetTable_Records();

        init_phone_audio_grid();
        initList();

        return rootview;

    }

    private void initList() {

        lLayout = new GridLayoutManager(getActivity(), 2);


        AudioAdapter madapter = new AudioAdapter(getActivity(), songs, location);

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
        cursor = getActivity().managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
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



}
