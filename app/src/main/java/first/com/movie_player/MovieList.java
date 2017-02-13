package first.com.movie_player;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.swipper.library.Swipper;

import java.util.List;

public class MovieList extends Swipper implements SensorEventListener{

    Uri uri;
    VideoView videov;
    Context context;
    DBHandler db;
    List<String> location=null;
    MediaController mc;
    int i=0;

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

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        db=new DBHandler(getApplicationContext());
        location=db.access_data();

        getWindow().setFormat(PixelFormat.UNKNOWN);
        mc=new MediaController(this);
        uri=uri.parse(location.get(i));
        Log.d("upro123", String.valueOf(uri));
        videov= (VideoView) findViewById(R.id.video);
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
        videov.setMediaController(mc);
        mc.setAnchorView(videov);
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
}
