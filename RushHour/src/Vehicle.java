
public class Vehicle {
	
	public int id, length, x_start, x_end, y_start, y_end;
	
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
	
	@Override
	public boolean equals(Object o) {
	    // self check
	    if (this == o)
	        return true;
	    // null check
	    if (o == null)
	        return false;
	    // type check and cast
	    if (getClass() != o.getClass())
	        return false;
	    Vehicle other = (Vehicle) o;
	    // field comparison
	    return id == other.id && length == other.length && orientation == other.orientation 
	    		&& x_start == other.x_start && y_start == other.y_start;
	}
}
