package com.kusa.tarokanizer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kusa.tarokanizer.data_classes.CardView
import com.kusa.tarokanizer.data_classes.Player
import com.kusa.tarokanizer.data_classes.Settings

const val SHARED_PREFERENCES = "SHARED_PREFERENCES"

class MainActivity : AppCompatActivity(), Dialog.DialogListener{



    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: Adapter
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    lateinit var toolbar: Toolbar
    lateinit var settings: Settings

    companion object{
        lateinit var mCardViewList: ArrayList<CardView>


        fun getCardViewList(): ArrayList<CardView> = mCardViewList
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mCardViewList = ArrayList()

        settings = Settings.getInstance()
        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        loadCardViewList()
        loadSettings()

        BuildRecyclerView()

        initViews()
    }

    fun initViews() {
        val buttonNew: Button = findViewById(R.id.button_new)
        buttonNew.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //creates a dialog that runs in another thread (class Dialog)
                val dialog = Dialog(this@MainActivity)
                dialog.show(supportFragmentManager, "dialog")
            }
        })
        val buttonSettings: Button = findViewById(R.id.button_settings)
        buttonSettings.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                startActivity(SettingsActivity.startSettingsActivity(this@MainActivity))
            }
        })

    }

    fun loadCardViewList() {
        val gson = Gson()
        val numberOfCardViews = preferences.getInt("numberOfCardViews", 0)

        for (i in 0 until numberOfCardViews) {
            val json = preferences.getString("cardView$i", "")
            val cv = gson.fromJson(json, CardView::class.java)
            mCardViewList.add(cv)
        }
    }

    fun loadSettings() {
        if (preferences.contains("ena")) {
            settings.setAutomaticMode(preferences.getBoolean("automaticMode", true))
            settings.setEna(preferences.getInt("ena", 0))
            settings.setDva(preferences.getInt("dva", 0))
            settings.setTri(preferences.getInt("tri", 0))
            settings.setSoloEna(preferences.getInt("soloEna", 0))
            settings.setSoloDva(preferences.getInt("soloDva", 0))
            settings.setSoloTri(preferences.getInt("soloTri", 0))
            settings.setSoloBrez(preferences.getInt("soloBrez", 0))
            settings.setTrula(preferences.getInt("trula", 0))
            settings.setNapovedanaTrula(preferences.getInt("napovedanaTrula", 0))
            settings.setKralji(preferences.getInt("kralji", 0))
            settings.setNapovedaniKralji(preferences.getInt("napovedaniKralji", 0))
            settings.setSpicka(preferences.getInt("spicka", 0))
            settings.setNapovedanaSpicka(preferences.getInt("napovedanaSpicka", 0))
            settings.setKralj(preferences.getInt("kralj", 0))
            settings.setNapovedanKralj(preferences.getInt("napovedanKralj", 0))
        }
    }

    fun BuildRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true) //increases performance
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = Adapter(this, mCardViewList)

        mRecyclerView.setLayoutManager(mLayoutManager)
        mRecyclerView.setAdapter(mAdapter)

        mAdapter.setOnCardBoardClickListener { position ->
            //deleting the cardboard
            val deleteDialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Delete Game")
                    .setMessage("Do you want to delete selected game?")
                    .setPositiveButton("Yes", null)
                    .setNegativeButton("No", null)
                    .show()

            val positiveButton = deleteDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {

                    RemoveItem(position)
                    deleteDialog.dismiss()
                }
            })
        }
    }

    fun AddNewCardboard(position: Int, title: String, players: ArrayList<Player>) {
        mCardViewList.add(position, CardView(title, players))
        mAdapter.notifyItemInserted(position)
    }

    override fun onDialogPositiveClick(title: String, players: ArrayList<Player>) {
        AddNewCardboard(mCardViewList.size, title, players)
    }

    fun RemoveItem(position: Int) {
        mCardViewList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
        mAdapter.notifyDataSetChanged()
    }

    public override fun onPause() {
        super.onPause()
        storeCardViewList()
    }

    fun storeCardViewList() {
        editor = preferences.edit()
        val gson = Gson()
        var i = 0

        for (cv in mCardViewList) {
            val json = gson.toJson(cv)
            editor!!.putString("cardView$i", json)
            i++
        }
        editor.putInt("numberOfCardViews", i)
        editor.apply()
    }


}