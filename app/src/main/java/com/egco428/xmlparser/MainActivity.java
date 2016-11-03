package com.egco428.xmlparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    //private String url2 = "&appid=9ccf8c2ff4f125acbef4944be08d5e37";
    private String link = "http://www.w3schools.com/js/myTutorials.txt";

    private TextView text;
    private TextView link_url;
    String display="";
    String url="";
    // private EditText location,country,temperature,humidity,pressure;
    //private HandleXML obj;
    private JsonParser obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);
        link_url = (TextView) findViewById(R.id.textView2);
        obj = new JsonParser(link);
        obj.fetchJSON();
        while (obj.parsingComplete) ;
        for(int i=0;i<obj.getDisplay().size();i++) {
             display= display + obj.getDisplay().get(i)+"\n";
             url = url + obj.getLink().get(i)+"\n";
        }
        text.setText(display);
        link_url.setText(url);

    }
}

