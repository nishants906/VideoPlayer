package first.com.movie_player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;

import java.io.IOException;
import java.util.List;

public class AudioPlayer extends AppCompatActivity implements MediaPlayer.OnPreparedListener,MediaController.MediaPlayerControl {

    MediaPlayer mMediaPlayer=null;
    MediaController mcontroller;
    DBHandler db;
    private Handler handler = new Handler();
    List<String> location=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);


        db=new DBHandler(getApplicationContext());
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);


        location=db.access_audio_data();
        Log.d("location123", String.valueOf(location));


        mcontroller=new MediaController(this);
        try {
            mMediaPlayer.setDataSource(String.valueOf(location.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("confirm12345", String.valueOf(db.access_audio_data().get(0)));
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mcontroller.hide();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        mcontroller.show();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mcontroller.setMediaPlayer(this);
        mcontroller.setAnchorView(findViewById(R.id.activity_audio_player));
        handler.post(new Runnable() {
            public void run() {
                mcontroller.setEnabled(true);
                mcontroller.show();
            }
        });
    }



    @Override
    public void start() {
        mMediaPlayer.start();

    }

    @Override
    public void pause() {
        mMediaPlayer.pause();

    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {

        mMediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mMediaPlayer.getAudioSessionId();
    }
}
