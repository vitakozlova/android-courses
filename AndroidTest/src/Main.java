import java.util.Scanner;

public class Main {	
	public static void main(String[] args) {
		System.out.println("Please, input artist name");
		Scanner in = new Scanner(System.in);
		XMLManager m = new XMLManager();
		m.createXML(in.nextLine());
		m.parseXML();
		m.outPutEvents();
		
	}	
}
