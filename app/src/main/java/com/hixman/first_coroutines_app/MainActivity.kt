package com.hixman.first_coroutines_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var myTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // define our views
        myTextView = findViewById(R.id.my_text)

        // this GlobalScope will never stop working until you close the app
        // launch is the way of starting here ; and launch means that i don't expect anything to be returned from you
        /**
         * Dispatchers help us choose which Thread we will use
         *
         * Dispatchers.Default: this is the default one , and it's use for running big or complex operations.
         * Dispatchers.IO     : this IO thread(Input and Output operations) it's used if we want to fetch or post data to the server.
         * Dispatchers.Main   : this is the main tread ; it's used for run the UI and simples tasks.
         * Dispatchers.Unconfined: A coroutine dispatcher that is not confined to any specific thread(see doc for more info :| ).
         * newSingleThreadContext(@name of the new thread): this is used if you want to create your own tread.
         * */
        GlobalScope.launch(newSingleThreadContext("my thread")) {
            printMyTextAfterDelay("hi world !")
        }


        runBlocking {
            printMyTextAfterDelay2("hix")
        }


        // if you want tow functions to work parallel
        GlobalScope.launch {
            // this tow fun will start to work at the same time
            delay(2000)
            Log.d("my fun", "this tow fun will start to work at the same time")
            launch {
                printMyTextAfterDelay3("ali1")
            }

            launch {
                printMyTextAfterDelay3("ali2")
            }

        }
    }

    suspend fun printMyTextAfterDelay(myText:String){
        // here the delay fun replace this(Thread.sleep(2000))
        delay(2000)
        Log.d("my fun", myText)
    }

    suspend fun printMyTextAfterDelay2(myText:String){
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            // this fun will help you to switch working from this thread to another one
            withContext(Dispatchers.Main){
                myTextView.text = "hi friend "
            }

        }
    }

    // the example in the runBlocking video
    suspend fun printMyTextAfterDelay3(myText:String){
        // here the delay fun replace this(Thread.sleep(2000))
        delay(2000)
        Log.d("my fun", myText)
    }


}