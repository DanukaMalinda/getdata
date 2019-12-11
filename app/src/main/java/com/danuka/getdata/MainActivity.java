package com.danuka.getdata;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOCAL_URL = "http://192.168.137.1:3000/androidApp/locations/1";
    private TextView mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData = (TextView) findViewById(R.id.textView);

        getLocationInfo();
    }

    private void getLocationInfo() {
        new LocationLoader().execute();
    }


    private class LocationLoader extends AsyncTask<String, Void, List<Pair<String, String>>> {
        private List<Pair<String, String>> locationList;

        @Override
        protected List<Pair<String, String>> doInBackground(String... strings) {
            String jsonString = QueryUtil.getJsonString(LOCAL_URL);

            return QueryUtil.ParseJson(jsonString);
        }

        @Override
        protected void onPostExecute(List<Pair<String, String>> result) {
            locationList = result;
            if (locationList == null) return;
            for (int i = 0; i < locationList.size(); i++) {
                String latitude = locationList.get(i).first;
                String longitude = locationList.get(i).second;
                mData.setText(latitude + " "+ longitude);
            }
        }
    }
}

