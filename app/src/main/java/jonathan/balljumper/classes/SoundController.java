package jonathan.balljumper.classes;

import android.content.Context;
import android.media.MediaPlayer;

import jonathan.balljumper.R;

/**
 * Created by Jonathan on 23/08/2017.
 */

public class SoundController {
    private final MediaPlayer mpBounce; // http://soundbible.com/1120-Bounce.html

    private boolean isMuted = false;

    public SoundController(Context context) {
        this.mpBounce = MediaPlayer.create(context, R.raw.bounce);
        this.mpBounce.setLooping(false);
        this.mpBounce.setVolume(.5f, .5f);
    }

    public void mute(boolean mute) {
        this.isMuted = mute;
    }

    public void playBounce() {
        if (this.mpBounce.isPlaying()) {
            this.mpBounce.seekTo(0);
        } else {
            this.mpBounce.start();
        }
    }
}
