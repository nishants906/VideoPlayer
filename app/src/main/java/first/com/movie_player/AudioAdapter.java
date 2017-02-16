package first.com.movie_player;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Test on 2/16/2017.
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    public int Type_Text = 1;
    Context context;

    ArrayList<String> song=null;
    DBHandler db;

    public AudioAdapter() {
    }

    ArrayList<String> location=null;

    public AudioAdapter(Context context, List<String> songs, List<String> location) {
        this.context = context;
        song = new ArrayList<>(songs);
        db=new DBHandler(context);

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
        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                db.resetTable_Records();
                db.new_note(song.get(position),location.get(position));
                Log.d("confirm",location.get(position));
                Log.d("confirm",song.get(position));
                Intent intent=new Intent(context,MoviePlayer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        ImageView imv;
        LinearLayout view;
        public ViewHolder(final View itemView, int View_Type, final Context context) {
            super(itemView);
            if (View_Type == Type_Text) {
                list = (Button) itemView.findViewById(R.id.list);
                imv= (ImageView) itemView.findViewById(R.id.image);
                view= (LinearLayout) itemView.findViewById(R.id.view);
                //Text = (TextView) itemView.findViewById(R.id.type1_text);
                HolderId = 1;
            }//If
        }//Constructor

    }//ViewHolder Class

}
