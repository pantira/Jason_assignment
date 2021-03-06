package com.egco428.xmlparser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleXML {

    private String country = "country";
    private String temperature = "temperature";
    private String humidity = "humidity";
    private String pressure = "pressure";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    public HandleXML(String url){
        this.urlString = url;
    }
    public String getCountry(){
        return country;
    }
    public String getTemperature(){
        return temperature;
    }
    public String getHumidity(){
        return humidity;
    }
    public String getPressure(){
        return pressure;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName(); //type info in <>
                switch(event){
                    case XmlPullParser.START_TAG:                         // start_tag=<
                        if(name.equals(humidity)){
                            humidity=myParser.getAttributeValue(null,"value"); //get from value attribute
                        }
                        else if(name.equals(pressure)){
                            pressure = myParser.getAttributeValue(null,"value");
                        }
                        else if(name.equals(temperature)){
                            temperature = myParser.getAttributeValue(null,"value");
                        }
                        else {
                            //
                        }
                        break;
                    case XmlPullParser.TEXT: //find element get element to text
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG: //get from text
                        if(name.equals("country")){
                            country=text;
                        }
                        break;
                }
                event =myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) //create con to wepservice
                            url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES
                            , false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }
}
