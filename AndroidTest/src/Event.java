import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
	private String date;
	private String time;
	private URL url;
	private String name;
	private String city;
	private String country;
	public Event() {
		
	}

	public Event(String date, String time, URL url, String name, String city, String country) {
		super();
		this.date = date;
		this.time = time;
		this.url = url;
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");		
		SimpleDateFormat timeformater= new SimpleDateFormat("HH:mm:ss");
		Date fullDate;
		try {
			fullDate = ((Date)format.parse(date));	
			this.time = timeformater.format(fullDate);		
			this.date = dateFormatter.format(fullDate);
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
		return "Event [date=" + date + ", time=" + time + ", url=" + url
				+ ", name=" + name + ", city=" + city + ", country=" + country
				+ "]";
	}
	
}