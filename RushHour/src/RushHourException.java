
public class RushHourException extends Exception {

	public RushHourException(String e) {
		switch(e) {
		case "incomplete":
			System.out.println("Incomplete input file.");
			break;
		case "size":
			System.out.println("Invalid value for the size of the grid.");
			break;
			
		}
	}
}
