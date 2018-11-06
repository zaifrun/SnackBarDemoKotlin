package org.pondar.snackbardemokotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentName = "" //nothing entered

    //This is the backup where we save the name in case the user hits the undo button
    private var backup  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val parent = layout
        //setting up the click listener.
        saveButton.setOnClickListener { _ ->
            backup = currentName //creating a backup

            //the following two lines hide the keyboard after clicking the button
            //which is what you want!
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(parent.windowToken, 0)

            //save the text entered in the current name field.
            currentName = editText.text.toString()
            lastEntered.text = currentName
            //Now setup our snackbar and show it

            val snackbar = Snackbar
                    .make(parent, "Name saved", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        //This code will ONLY be executed in case that
                        //the user has hit the UNDO button
                        currentName = backup //get backup
                        lastEntered.text = currentName
                        val snackbar = Snackbar.make(parent, "Old name restored!", Snackbar.LENGTH_SHORT)

                        //Show the user we have restored the name - but here
                        //on this snackbar there is NO UNDO - so no SetAction method is called
                        //if you wanted, you could include a REDO on the second action button
                        //for instance.
                        snackbar.show()
                    }

            snackbar.show()
        }
    }
}

