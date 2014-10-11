
/**
   @author Masha Barashko
*/
package project;

import employees.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Project {
	private String name;
	private int flow;
	private Set<Employee> command;
	private JSONObject jsonObject;
	public Project(String inputFileName) {
		command = new TreeSet<Employee>();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(inputFileName));
			jsonObject = (JSONObject) obj;
			
			this.name = (String) jsonObject.get("project name");
			this.flow = Integer.parseInt((String)jsonObject.get("project flow"));
	 
			JSONArray msg = (JSONArray) jsonObject.get("command");
			Iterator<JSONObject> iterator = msg.iterator();
			while(iterator.hasNext()) {
				JSONObject temp = iterator.next();
				String s = (String) temp.get("post");
				if(s.compareTo("junior developer") == 0) {
					command.add(new JuniorDeveloper((String)temp.get("name"), Integer.parseInt((String)temp.get("time")),
							Integer.parseInt((String)temp.get("earnings"))));
				}
				if(s.compareTo("senior developer") == 0) {
					command.add(new SeniorDeveloper((String)temp.get("name"), Integer.parseInt((String)temp.get("time")),
							Integer.parseInt((String)temp.get("earnings"))));
				}
				if(s.compareTo("team leader") == 0) {
					command.add(new TeamLeader((String)temp.get("name"), Integer.parseInt((String)temp.get("time")),
							Integer.parseInt((String)temp.get("earnings"))));
				}
				if(s.compareTo("developer") == 0) {
					command.add(new Developer((String)temp.get("name"), Integer.parseInt((String)temp.get("time")),
							Integer.parseInt((String)temp.get("earnings"))));
				}
				if(s.compareTo("project manager") == 0) {
					command.add(ProjectManager.getInstance((String)temp.get("name"), Integer.parseInt((String)temp.get("time")),
							Integer.parseInt((String)temp.get("earnings"))));
				}
				if(s.compareTo("tester") == 0) {
					command.add(new Tester((String)temp.get("name"), Integer.parseInt((String)temp.get("time")),
							Integer.parseInt((String)temp.get("earnings"))));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public int duration() {
		int c=0;
		for(Employee e : command) 
			c+=e.getTime();
		return c;
	}
	public int price() {
		int c=0;
		for(Employee e : command) 
			c+=e.getEarnings();
		return c - flow;
	}
	public void printEmployeeDetails(String outputFileName) {
		try {
			PrintWriter pw = new PrintWriter(outputFileName);
			pw.println("Project name: " + this.name);
			pw.println("Project duration: " + this.duration() + " hours");
			pw.println("Project price: " + this.price() + "$");
			for(Employee e : command) 
				pw.println(e.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

