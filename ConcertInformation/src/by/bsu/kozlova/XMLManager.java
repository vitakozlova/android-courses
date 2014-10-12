package by.bsu.kozlova;

import android.os.Environment;
import android.os.StrictMode;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class XMLManager {

	private ArrayList<Event> events = new ArrayList<Event>();
	
	public XMLManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void createXML(String artist) {
		try {
			artist = artist.replace(" ", "%20");
			URL url = new URL("http://api.bandsintown.com/artists/"+artist+"/events.xml");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            File SDCardRoot = Environment.getExternalStorageDirectory();
            File file = new File(SDCardRoot,"input.xml");
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;

            }
            fileOutput.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    public ArrayList<Event> parseXML() {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        Event e = null;
        Boolean venue = false;
        String text ="";
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            File file = new File(Environment.getExternalStorageDirectory()+"/input.xml");
            FileInputStream fis = new FileInputStream(file);
            parser.setInput(new InputStreamReader(fis));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("event")) {
                            e = new Event();
                        }
                        else if (tagname.equalsIgnoreCase("venue")) {
                            venue = true;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("event")) {
                            events.add(e);
                            venue = false;
                        } else if (tagname.equalsIgnoreCase("datetime")) {
                            e.setDate(text);
                        } else if (tagname.equalsIgnoreCase("url") ) {
                            if(venue)
                                e.setUrl(new URL(text));
                        } else if (tagname.equalsIgnoreCase("name")) {
                            if(venue)
                                e.setName(text);
                        }
                          else if (tagname.equalsIgnoreCase("city")) {
                            if(venue)
                                e.setCity(text);
                        }
                        else if (tagname.equalsIgnoreCase("country")) {
                            if(venue)
                                e.setCountry(text);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return events;
    }
}
