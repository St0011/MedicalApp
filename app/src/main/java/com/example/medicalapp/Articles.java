package com.example.medicalapp;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Articles extends Activity {

    private ListView listView;
    private String[][] articles = {
            {"Walking Daily", ",", "                (Click for more details)"},
            {"Covid 19", ",", "                        (Click for more details)"},
            {"Smoking", ",", "                        (Click for more details)"},
            {"Menstrual Cramps", ",", "       (Click for more details)"},
            {"Healthy Gut", ",", "                   (Click for more details)"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        listView = findViewById(R.id.listView);
        ArticlesListAdapter adapter = new ArticlesListAdapter(this, articles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] articleData = articles[position];
                String itemName = articleData[0];

                // Show different toast messages based on the item clicked
                switch (itemName) {
                    case "Walking Daily":
                        GOTO("Clicked on Walking Daily");
                        break;
                    case "Covid 19":
                        GOTO("Clicked on Covid 19");
                        break;
                    case "Smoking":
                        GOTO("Clicked on Smoking");
                        break;
                    case "Menstrual Cramps":
                        GOTO("Clicked on Menstrual Cramps");
                        break;
                    case "Healthy Gut":
                        GOTO("Clicked on Healthy Gut");
                        break;
                    default:

                        break;
                }
            }
        });
    }

    private void GOTO(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
