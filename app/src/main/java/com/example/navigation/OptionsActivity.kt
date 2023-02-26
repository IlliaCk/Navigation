package com.example.navigation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.navigation.databinding.ActivityOptionsBinding

class OptionsActivity : BaseActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var options: Options

    private lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?:
            intent.getParcelableExtra(EXTRA_OPTIONS) ?:
                throw java.lang.IllegalArgumentException("You need to specify EXTRA_OPTIONS")

        setupSpinner()
        setupCheckBox()
        updateUI()

        //println("new commit 2")
//        print("commit #3")

        //println("Hello from GitHub")

        binding.cancelButton.setOnClickListener { onCancelPressed() }
        binding.confirmButton.setOnClickListener { onConfirmPressed() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun setupSpinner() {
        boxCountItems = (1..6).map { BoxCountItem(it, "$it boxes") }
        adapter = ArrayAdapter(
            this,
            R.layout.item_spinner,
            boxCountItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        binding.boxCountSpinner.adapter = adapter
        binding.boxCountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val count=boxCountItems[position].count
                options=options.copy(boxCount = count)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }
    }

    private fun setupCheckBox(){
        binding.enableTimerCheckBox.setOnClickListener {
            options=options.copy(isTimerEnabled = binding.enableTimerCheckBox.isChecked)
        }
    }
    private fun updateUI(){
        binding.enableTimerCheckBox.isChecked=options.isTimerEnabled

        val currentIndex=boxCountItems.indexOfFirst { it.count==options.boxCount }
        binding.boxCountSpinner.setSelection(currentIndex)
    }

    private fun onConfirmPressed(){
        val intent=Intent()
        intent.putExtra(EXTRA_OPTIONS,options)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
    private fun onCancelPressed(){
        finish()
    }

    companion object{
        const val EXTRA_OPTIONS="EXTRA_OPTIONS"
        const val KEY_OPTIONS="KEY_OPTIONS"
    }
}

class BoxCountItem(val count:Int, private val optionTitle:String){

    override fun toString(): String {
        return optionTitle
    }
}