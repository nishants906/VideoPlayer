package first.com.movie_player;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.github.rubensousa.previewseekbar.PreviewSeekBar;
import com.swipper.library.Swipper;

import java.util.List;

public class MovieList extends Swipper implements SensorEventListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    Uri uri;
    VideoView videov;
    Context context;
    DBHandler db;
    List<String> location=null;
    int i=0;

    PreviewSeekBar seekBar;
    Button play;
    Button pause;
    Button forward;
    Button back;
    LinearLayout ll;


    private SensorManager mSensorManager;
    private Sensor mProximity;

    public MovieList() {

    }

    public MovieList(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        set(this);

        ll= (LinearLayout) findViewById(R.id.ll);
        videov= (VideoView) findViewById(R.id.video);
        seekBar= (PreviewSeekBar) findViewById(R.id.seek);
        play= (Button) findViewById(R.id.play);
        pause= (Button) findViewById(R.id.pause);
        back= (Button) findViewById(R.id.back);
        forward= (Button) findViewById(R.id.forward);

        videov.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);

        videov.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                seekBar.setMax(videov.getDuration());
                seekBar.postDelayed(onEverySecond, 1000);
            }
        });
        seekBar.addOnSeekBarChangeListener(this);



        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        db=new DBHandler(getApplicationContext());
        location=db.access_data();

        getWindow().setFormat(PixelFormat.UNKNOWN);
        uri=uri.parse(location.get(i));
        Log.d("upro123", String.valueOf(uri));
        videov.setVideoURI(uri);


        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videov.getLayoutParams();
        params.width =  metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videov.setLayoutParams(params);
        Brightness(Orientation.VERTICAL);
        Volume(Orientation.CIRCULAR);
        Seek(Orientation.HORIZONTAL,videov);
        videov.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        videov.seekTo(0);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        db.add_data((videov.getCurrentPosition()));
        Log.d("confirm", String.valueOf(videov.getCurrentPosition()));
        mSensorManager.unregisterListener(this);
        }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if ( event.values[0]<= 3.0) {
                //near
                videov.pause();
            } else {
                //far
                videov.start();
            }
            Log.d("mismatch", String.valueOf(event.values[0]));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.play:{
                videov.start();
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.pause:{
                videov.pause();
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.video:{
                ll.setVisibility(View.VISIBLE);
            }
        }
    }

    private Runnable onEverySecond=new Runnable() {

        @Override
        public void run() {

            if(seekBar != null) {
                seekBar.setProgress(videov.getCurrentPosition());
            }

            if(videov.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
            }

        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(fromUser) {
            // this is when actually seekbar has been seeked to a new position
            videov.seekTo(progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
