package first.com.movie_player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Test on 2/3/2017.
 */public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    public int Type_Text = 1;
    Context context;

    ArrayList<String> song=null;
    ArrayList<String> checklist=null;
    ArrayList<String> location=null;





    public MusicAdapter(Context context, List<String> songs, List<String> location) {
        this.context = context;
        song = new ArrayList<>(songs);
        checklist=new ArrayList<String>();
        this.location = new ArrayList<>(location);
        Log.d("songs", String.valueOf(songs));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ViewHolder vItem = null;

        if (viewType == Type_Text) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            vItem = new ViewHolder(v, viewType, parent.getContext());
        }

        Log.d("item123", String.valueOf(vItem));
        return vItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder.HolderId == 1) {
            holder.list.setText(song.get(position));
        }

        holder.list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri=Uri.parse(location.get(position));
                Log.d("confirm",location.get(position));
                Log.d("confirm",song.get(position));
                Intent intent=new Intent(context,MovieList.class);
                context.startActivity(intent);

                }

        });

    }

    @Override
    public int getItemCount() {
        Log.d("size", String.valueOf(song.size()));
        return song.size();

    }

    @Override
    public int getItemViewType(int position) {

        return Type_Text;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int HolderId;
        Button list;
        public ViewHolder(final View itemView, int View_Type, final Context context) {
            super(itemView);
            if (View_Type == Type_Text) {
                list = (Button) itemView.findViewById(R.id.list);
                //Text = (TextView) itemView.findViewById(R.id.type1_text);
                HolderId = 1;
            }//If
        }//Constructor

    }//ViewHolder Class

}

