package impl;

/**
 * Created by GreenyNeko on 01.12.2014.
 */

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.kod.knightsofdrakonur.framework.Audio;
import com.kod.knightsofdrakonur.framework.Music;
import com.kod.knightsofdrakonur.framework.Sound;

public class AndroidAudio implements Audio
{
    AssetManager assets;
    SoundPool soundPool;

    public AndroidAudio(Activity activity)
    {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Music createMusic(String fileName)
    {
        try
        {
            AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
            return new AndroidMusic(assetFileDescriptor);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not load music \'" + fileName + "\'");
        }
    }

    @Override
    public Sound createSound(String filename)
    {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundId);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not load sound \'" + filename + "\'");
        }
    }


}
