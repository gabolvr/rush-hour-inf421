import java.util.LinkedList;
import java.util.Queue;

public class State {
	
	public Vehicle[] vehicles;
	
	public int[][] grid;
	
	public int num_vehicles, size, moves;
	
	public boolean final_state;
	
	State previous_state;
	
	public State(int num_vehicles, int size, Vehicle[] vehicles) {
		this.num_vehicles = num_vehicles;
		this.vehicles = vehicles;
		this.size = size;
		setGrid();
		this.previous_state = null;
		this.moves = 0;
		this.final_state = vehicles[1].x_end == size && vehicles[1].orientation == 'h';
	}
	
	public State(int num_vehicles, int size, Vehicle[] vehicles, int moves, State previous) {
		this.num_vehicles = num_vehicles;
		this.vehicles = vehicles;
		this.size = size;
		setGrid();
		this.previous_state = previous;
		this.moves = moves;
		this.final_state = vehicles[1].x_end == size && vehicles[1].orientation == 'h';
	}
	
	private void setGrid() {
		grid = new int[size + 1][size + 1];
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++)
				grid[i][j] = 0;
		}
		
		for (int v = 1; v <= num_vehicles; v++) {
			for (int i = vehicles[v].y_start; i <= vehicles[v].y_end; i++) {
				for (int j = vehicles[v].x_start; j <= vehicles[v].x_end; j++) {
					if (grid[i][j] == 0)
						grid[i][j] = v;
				}
			}
		}
	}
	
	public void printState() {
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if (j < size)
					System.out.print(grid[i][j] + " ");
				else
					System.out.println(grid[i][j]);
			}
		}
	}
	
	public Vehicle[] copyVehicles() {
		Vehicle[] copy = new Vehicle[num_vehicles + 1];
		for (int i = 1; i <= num_vehicles; i++) {
			copy[i] = new Vehicle(vehicles[i].id, vehicles[i].orientation, vehicles[i].length,
					vehicles[i].x_start, vehicles[i].y_start);
		}
		return copy;
	}
	
	public Queue<State> possibleStates(){
		Queue<State> states = new LinkedList<State>();
		
		State next_state;
		
		for (int v = 1; v <= num_vehicles; v++) {
			switch(vehicles[v].orientation) {
			case 'h':
				for (int x = vehicles[v].x_end + 1; x <= size && grid[vehicles[v].y_start][x] == 0; x++) {
					Vehicle[] next_vehicles = copyVehicles();
					next_vehicles[v].x_end = x;
					next_vehicles[v].x_start = x - (vehicles[v].length - 1);
					next_state = new State(num_vehicles, size, next_vehicles, moves + 1, this);
					states.add(next_state);
				}
				for (int x = vehicles[v].x_start - 1; x >= 1 && grid[vehicles[v].y_start][x] == 0; x--) {
					Vehicle[] next_vehicles = copyVehicles();
					next_vehicles[v].x_start = x;
					next_vehicles[v].x_end = x + (vehicles[v].length - 1);
					next_state = new State(num_vehicles, size, next_vehicles, moves + 1, this);
					states.add(next_state);
				}
				break;
			case 'v':
				for (int y = vehicles[v].y_end + 1; y <= size && grid[y][vehicles[v].x_start] == 0; y++) {
					Vehicle[] next_vehicles = copyVehicles();
					next_vehicles[v].y_end = y;
					next_vehicles[v].y_start = y - (vehicles[v].length - 1);
					next_state = new State(num_vehicles, size, next_vehicles, moves + 1, this);
					states.add(next_state);
				}
				for (int y = vehicles[v].y_start - 1; y >= 1 && grid[y][vehicles[v].x_start] == 0; y--) {
					Vehicle[] next_vehicles = copyVehicles();
					next_vehicles[v].y_start = y;
					next_vehicles[v].y_end = y + (vehicles[v].length - 1);
					next_state = new State(num_vehicles, size, next_vehicles, moves + 1, this);
					states.add(next_state);
				}
				break;
			}
		}
		
		return states;
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
	    State other = (State) o;
	    // field comparison
	    if (num_vehicles != other.num_vehicles || size != other.size)
	    	return false;
	    for (int i = 1; i <= num_vehicles; i++) {
	    	if (!vehicles[i].equals(other.vehicles[i]))
	    		return false;
	    }
	    return true;
	}
	
	public long simpleRepresentation() {
		long representation = 0;
		
		for (int i = 1; i <= num_vehicles; i++) {
			if (this.vehicles[i].orientation == 'h')
				representation = 8 * representation + this.vehicles[i].x_start - 1;
			else if (this.vehicles[i].orientation == 'v')
				representation = 8 * representation + this.vehicles[i].y_start - 1;
		}
		
		return representation;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = 1; i <= num_vehicles; i++) {
			if (this.vehicles[i].orientation == 'h')
				hash = 8 * hash + vehicles[i].x_start - 1;
			else if (this.vehicles[i].orientation == 'v')
				hash = 8 * hash + vehicles[i].y_start - 1;
		}
		return hash;
	}
}
