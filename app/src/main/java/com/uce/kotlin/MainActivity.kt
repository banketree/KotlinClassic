package com.test.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.test.kotlin.Delegate.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = "测试哦"
        sample_text.setOnClickListener {
            //            Test.main()
//            Test2.main()
            main7()
        }
    }
}
