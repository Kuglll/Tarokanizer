package com.kusa.tarokanizer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kusa.tarokanizer.data_classes.CardView
import com.kusa.tarokanizer.data_classes.Player
import com.kusa.tarokanizer.data_classes.Settings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

const val SHARED_PREFERENCES = "SHARED_PREFERENCES"

class MainActivity : AppCompatActivity(), Dialog.DialogListener{

    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

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
        addButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //creates a dialog that runs in another thread (class Dialog)
                val dialog = Dialog(this@MainActivity)
                dialog.show(supportFragmentManager, "dialog")
            }
        })
        settingsButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                startActivity(SettingsActivity.startSettingsActivity(this@MainActivity))
            }
        })
        backButton.visibility = View.GONE
        finishButton.visibility = View.GONE
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
            settings.isAutomaticMode = preferences.getBoolean("automaticMode", true)
            settings.ena = preferences.getInt("ena", 0)
            settings.dva = preferences.getInt("dva", 0)
            settings.tri = preferences.getInt("tri", 0)
            settings.soloEna = preferences.getInt("soloEna", 0)
            settings.soloDva = preferences.getInt("soloDva", 0)
            settings.soloTri = preferences.getInt("soloTri", 0)
            settings.soloBrez = preferences.getInt("soloBrez", 0)

            settings.beracPikolo = preferences.getInt("beracPikolo", 0)
            settings.valat = preferences.getInt("valat", 0)
            settings.napovedanValat = preferences.getInt("napovedanValat", 0)
            settings.barvniValat = preferences.getInt("barvniValat", 0)
            settings.mondFang = preferences.getInt("mondFang", 0)
            settings.renons = preferences.getInt("renons", 0)

            settings.trula = preferences.getInt("trula", 0)
            settings.napovedanaTrula = preferences.getInt("napovedanaTrula", 0)
            settings.kralji = preferences.getInt("kralji", 0)
            settings.napovedaniKralji = preferences.getInt("napovedaniKralji", 0)
            settings.spicka = preferences.getInt("spicka", 0)
            settings.napovedanaSpicka = preferences.getInt("napovedanaSpicka", 0)
            settings.kralj = preferences.getInt("kralj", 0)
            settings.napovedanKralj = preferences.getInt("napovedanKralj", 0)

            settings.radlc = preferences.getInt("radlc", 0)
        }
    }

    fun BuildRecyclerView() {

        recyclerView.setHasFixedSize(true) //increases performance
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = Adapter(this, mCardViewList)

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

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
            editor.putString("cardView$i", json)
            i++
        }
        editor.putInt("numberOfCardViews", i)
        editor.apply()
    }


}