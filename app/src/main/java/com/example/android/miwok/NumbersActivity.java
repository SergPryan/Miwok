/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mMediaPlayer.stop();
                        mMediaPlayer.seekTo(0);
                    }  else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final List<Word> words = new ArrayList<Word>();
        words.add(new Word(R.drawable.number_one,R.raw.number_one,"one","lutti"));
        words.add(new Word(R.drawable.number_two,R.raw.number_two,"two","atiiko"));
        words.add(new Word(R.drawable.number_three,R.raw.number_three,"three","tolookosu"));
        words.add(new Word(R.drawable.number_four,R.raw.number_four,"four","oyyisa"));
        words.add(new Word(R.drawable.number_five,R.raw.number_five,"five","massokka"));
        words.add(new Word(R.drawable.number_six,R.raw.number_six,"six","temmokka"));
        words.add(new Word(R.drawable.number_seven,R.raw.number_seven,"seven","kenekaku"));
        words.add(new Word(R.drawable.number_eight,R.raw.number_eight,"eight","kawinta"));
        words.add(new Word(R.drawable.number_nine,R.raw.number_nine,"nine","wo’e"));
        words.add(new Word(R.drawable.number_ten,R.raw.number_ten,"ten","na’aacha"));
        WordAdapter arrayAdapter = new WordAdapter(this,words,R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
//                    mAudioManager.registerMediaButtonEventReceiver(afChangeListener);
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getmSoundResourceId());
                    mMediaPlayer.start();
                    Log.v("Numbers activity","Current world " +word);
                    mMediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });

    }
    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer=null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


}
