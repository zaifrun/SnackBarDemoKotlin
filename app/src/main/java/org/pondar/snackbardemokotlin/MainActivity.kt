package org.pondar.snackbardemokotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.inputmethod.InputMethodManager
import org.pondar.snackbardemokotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var currentName = "" //nothing entered

    //This is the backup where we save the name in case the user hits the undo button
    private var backup  = ""
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val parent = binding.layout
        //setting up the click listener.
        binding.saveButton.setOnClickListener {
            backup = currentName //creating a backup

            //the following two lines hide the keyboard after clicking the button
            //which is what you want!
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(parent.windowToken, 0)

            //save the text entered in the current name field.
            currentName = binding.editText.text.toString()
            binding.lastEntered.text = currentName
            //Now setup our snackbar and show it

            val snackbar = Snackbar
                    .make(parent, "Name saved", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        //This code will ONLY be executed in case that
                        //the user has hit the UNDO button
                        currentName = backup //get backup
                        binding.lastEntered.text = currentName
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

