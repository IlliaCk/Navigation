package com.example.navigation

import android.content.Intent
import android.os.Bundle
import com.example.navigation.databinding.ActivityMenuBinding

class MenuActivity : BaseActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.openBoxBtn.setOnClickListener { onOpenBoxPressed() }
        binding.optionsBtn.setOnClickListener { onOptionsPressed() }
        binding.aboutBtn.setOnClickListener { onAboutPressed() }
        binding.exitBtn.setOnClickListener { onExitPressed() }

        options=savedInstanceState?.getParcelable(KEY_OPTIONS) ?:Options.DEFAULT

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== OPTIONS_REQUEST_CODE && resultCode== RESULT_OK){
            options=data?.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS) ?:
                throw java.lang.IllegalArgumentException("Can`t get the updated data from OptionsActivity")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS,options)
    }
    private fun onOpenBoxPressed(){
        val intent=Intent(this,BoxSelectionActivity::class.java)
        intent.putExtra(BoxSelectionActivity.EXTRA_OPTIONS,options)
        startActivity(intent)
    }
    private fun onOptionsPressed(){
        val intent=Intent(this,OptionsActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS,options)
        startActivityForResult(intent, OPTIONS_REQUEST_CODE)
    }
    private fun onAboutPressed(){
        val intent=Intent(this,AboutActivity::class.java)
        startActivity(intent)
    }
    private fun onExitPressed(){
        finish()
    }
    companion object{
        const val KEY_OPTIONS="options"
        const val OPTIONS_REQUEST_CODE=1
    }
}
