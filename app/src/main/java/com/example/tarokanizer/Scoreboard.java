package com.example.tarokanizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Scoreboard extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        linearLayout = findViewById(R.id.names);

        initialize();
    }

    public void initialize(){
        Intent intent = getIntent();

        String [] players = intent.getStringArrayExtra("playerNames");
        for (String player: players) {
            TextView tv = createTextView(player);
            linearLayout.addView(tv);
        }

    }


    public TextView createTextView(String player){
        //params = params are set here rather than in xml in layout
        TextView textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        textView.setText(player);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }


}
