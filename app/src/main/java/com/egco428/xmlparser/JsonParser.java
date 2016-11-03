package com.egco428.xmlparser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 6272user on 11/3/2016 AD.
 */
public class JsonParser {
//    private String country = "county";
//    private String temperature = "temperature";
//    private String humidity = "humidity";
//    private String pressure = "pressure";
    private String urlString = null;
    ArrayList<String> display = new ArrayList<>();
    ArrayList<String> link = new ArrayList<>();

    public volatile boolean parsingComplete = true;
    public JsonParser(String url){
        this.urlString = url;
    }
//    public String getCountry(){
//        return country;
//    }
//    public String getTemperature(){
//        return temperature;
//    }
//    public String getHumidity(){
//        return humidity;
//    }
//    public String getPressure(){
//        return pressure;
//    }

    public ArrayList<String> getDisplay(){
        return display;
    }

    public ArrayList<String> getLink(){
        return link;
    }

    public void readAndParseJSON(String in){
        try {
//            JSONObject reader =new JSONObject(in);
//            JSONObject sys =reader.getJSONObject("sys");
//            country =sys.getString("country");
//            JSONObject main = reader.getJSONObject("main");
//            temperature = main.getString("temp");
//            pressure = main.getString("pressure");
//            humidity = main.getString("humidity");
//            parsingComplete =false;
            JSONArray jsonarray = new JSONArray(in);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                display.add(jsonobject.getString("display"));
                link.add(jsonobject.getString("url"));
            }
            parsingComplete =false;

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void fetchJSON(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
