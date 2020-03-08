package com.zhx.weather.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.zhx.weather.manager.UserInfoManager;

/**
 * Created by liyu on 2016/12/27.
 */

public class TTSManager {

    private static TTSManager instance;

    private SpeechSynthesizer mTts;

    public static TTSManager getInstance(Context context,String who,String speed) {
        if (instance == null) {
            synchronized (TTSManager.class) {
                instance = new TTSManager(context,who,speed);
            }
        }
        return instance;
    }

    public static void destroy() {
        if (instance != null) {
            instance.mTts.stopSpeaking();
            instance.mTts.destroy();
            instance = null;
        }
    }

    private TTSManager(Context context, String who, String speed) {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5e632645");
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        //设置使用云端引擎
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, UserInfoManager.Companion.getINSTANCE().getVoiceName());
        //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY,"1");//支持实时音频流抛出，仅在synthesizeToUri条件下支持
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //	mTts.setParameter(SpeechConstant.STREAM_TYPE, AudioManager.STREAM_MUSIC+"");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");

//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }

    public void speak(String text, final TTSCallback callback) {
        if (TextUtils.isEmpty(text) || mTts.isSpeaking())
            return;
        mTts.startSpeaking(text, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {
                if (callback != null)
                    callback.onStart();
            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                if (callback != null)
                    callback.onCompleted();
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
    }

    public interface TTSCallback {

        void onStart();

        void onCompleted();
    }
}
