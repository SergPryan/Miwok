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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        List<Word> words = new ArrayList<Word>();
        words.add(new Word(R.drawable.family_father,"father","әpә"));
        words.add(new Word(R.drawable.family_mother,"mother","әṭa"));
        words.add(new Word(R.drawable.family_son,"son","angsi"));
        words.add(new Word(R.drawable.family_daughter,"daughter","tune"));
        words.add(new Word(R.drawable.family_older_brother,"older brother","taachi"));
        words.add(new Word(R.drawable.family_younger_brother,"younger brother","chalitti"));
        words.add(new Word(R.drawable.family_older_sister,"older sister","ama"));
        words.add(new Word(R.drawable.family_grandfather,"grandfather","paapa"));
        words.add(new Word(R.drawable.family_grandmother,"family_grandmother","mamo"));
        WordAdapter arrayAdapter = new WordAdapter(this,words,R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);
    }
}
