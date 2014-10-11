import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLManager {
	
	private XMLStreamReader  r;
	private ArrayList<Event> events = new ArrayList<>();
	
	public XMLManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void createXML(String artist) {
		try {
			artist = artist.replace(" ", "%20");
			URL website = new URL("http://api.bandsintown.com/artists/"+artist+"/events.xml");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("events.xml");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} 
		catch (MalformedURLException e) { 
		    System.out.println(e);
		} 
		catch (IOException e) {   
			System.out.println(e);			
		}
	}
	public void parseXML() {
		Boolean venue = false;
		try {		
	        r = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream("events.xml"));
			Event e = new Event();
			while (r.hasNext()) {
				switch (r.next()) {	
					case XMLStreamConstants.START_ELEMENT:
						switch (r.getLocalName()) {
							case "datetime":
								e.setDate(r.getElementText());
								break;
							case "venue":
								venue = true;
								break;
							case "url":								
								if(venue)
									e.setUrl(new URL(r.getElementText()));
								break;
							case "name":
								if(venue)
									e.setName(r.getElementText());
								break;
							case "city":
								if(venue)
									e.setCity(r.getElementText());
								break;
							case "country":
								if(venue)
									e.setCountry(r.getElementText());
								break;						
						}
					break;
					case XMLStreamConstants.END_ELEMENT:
						switch (r.getLocalName()) {
							case "event":
								if(e!=null)
									events.add(e);
								e=new Event();
								break;
							case "venue":
								venue = false;
								break;
						}
				}
			}
			if(events.isEmpty())
				System.out.println("No concert!");
		}
		catch (IOException e) {
			System.out.println(e);	
		}	
		catch (XMLStreamException e) {
			System.out.println(e);			 
		}	
	}
	public void outPutEvents() {
		for(Event e:events)
			System.out.println(e.toString());
	}
}
