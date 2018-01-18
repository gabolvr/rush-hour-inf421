
public class Vehicle {
	
	public int id, x_start, x_end, y_start, y_end, length;
	
	public char orientation;
	
	public Vehicle(int id, char orientation, int length, int x, int y) {
		this.id = id;
		this.orientation = orientation;
		this.length = length;
		this.x_start = x;
		this.x_end = x + (orientation == 'h' ? 1 : 0) * (length - 1);
		this.y_start = y;
		this.y_end = y + (orientation == 'v' ? 1 : 0) * (length - 1);
	}
}
