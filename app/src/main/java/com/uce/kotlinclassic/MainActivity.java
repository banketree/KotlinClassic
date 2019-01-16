package com.uce.kotlinclassic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    private fun load() {
//        val path = Paths.get("words.txt")
//        val t = measureTimeMillis {
//            Files.lines(path).parallel().forEach { line ->
//                    val words = line.substringAfter('\t').replace('\t', ' ').toLowerCase()
//                val freq = line.substringBefore('\t').toInt()
//                ngrams.add(NGram(words, freq))
//            }
//        }
//        println("$currentThread: Loaded file in $t msec")
//    }
}
