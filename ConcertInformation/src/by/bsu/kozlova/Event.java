package by.bsu.kozlova;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {
	private Date date;
	private String time;
	private URL url;
	private String name;
	private String city;
	private String country;
	public Event() {
		
	}

	public Date getDate() {
		return date;
	}
	public void setDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			this.date = ((Date)format.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}		
	
	public String getTime() {
		return time;
	}

	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Date&time: " + date.toLocaleString() + "\nUrl: " + url
				+ "\nName: " + name + "\nCity: " + city + "\nCountry: " + country;
	}
}
