import java.util.LinkedList;
import java.util.Queue;

public class State {
	
	public Vehicle[] vehicles;
	
	public int[][] grid;
	
	public int num_vehicles, size, moves;
	
	public boolean final_state;
	
	State previous_state;
	
	// Constructor for the initial state
	public State(int num_vehicles, int size, Vehicle[] vehicles) {
		this.num_vehicles = num_vehicles;
		this.vehicles = vehicles;
		this.size = size;
		setGrid();
		this.previous_state = null;
		this.moves = 0;
		this.final_state = vehicles[1].x_end == size && vehicles[1].orientation == 'h';
	}
	
	// Constructor for a state storing as well the number of moves to arrive at this state and the previous state
	public State(int num_vehicles, int size, Vehicle[] vehicles, int moves, State previous) {
		this.num_vehicles = num_vehicles;
		this.vehicles = vehicles;
		this.size = size;
		setGrid();
		this.previous_state = previous;
		this.moves = moves;
		this.final_state = vehicles[1].x_end == size && vehicles[1].orientation == 'h';
	}
	
	// Place each vehicle in the grid and check if a valid state or not
	private boolean setGrid() {
		grid = new int[size + 1][size + 1];
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++)
				grid[i][j] = 0;
		}
		
		for (int v = 1; v <= num_vehicles; v++) {
			for (int i = vehicles[v].y_start; i <= vehicles[v].y_end; i++) {
				for (int j = vehicles[v].x_start; j <= vehicles[v].x_end; j++) {
					// If the place is empty, the vehicle can be there
					if (grid[i][j] == 0)
						grid[i][j] = v;
					else
						return false;
				}
			}
		}
		
		return true;
	}
	
	// Print the grid representing the state
	public void printState() {
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if (j < size)
					System.out.printf("%2d ", grid[i][j]);
				else
					System.out.printf("%2d\n", grid[i][j]);
			}
		}
	}
	
	// Creates a new array for the vehicles and copy them
	public Vehicle[] copyVehicles() {
		Vehicle[] copy = new Vehicle[num_vehicles + 1];
		for (int i = 1; i <= num_vehicles; i++) {
			copy[i] = new Vehicle(vehicles[i].id, vehicles[i].orientation, vehicles[i].length,
					vehicles[i].x_start, vehicles[i].y_start);
		}
		return copy;
	}
	
	// Find the possible states that could be reached from the actual state with one move
	public Queue<State> possibleStates(){
		Queue<State> states = new LinkedList<State>();
		
		State next_state;
		
		// Verify for each vehicle the possible states 
		for (int v = 1; v <= num_vehicles; v++) {
			switch(vehicles[v].orientation) {
			case 'h':
				// Moving right
				for (int x = vehicles[v].x_end + 1; x <= size && grid[vehicles[v].y_start][x] == 0; x++) {
					Vehicle[] next_vehicles = copyVehicles();
					next_vehicles[v].x_end = x;
					next_vehicles[v].x_start = x - (vehicles[v].length - 1);
					next_state = new State(num_vehicles, size, next_vehicles, moves + 1, this);
					states.add(next_state);
				}
				// Moving left
				for (int x = vehicles[v].x_start - 1; x >= 1 && grid[vehicles[v].y_start][x] == 0; x--) {
					Vehicle[] next_vehicles = copyVehicles();
					next_vehicles[v].x_start = x;
					next_vehicles[v].x_end = x + (vehicles[v].length - 1);
					next_state = new State(num_vehicles, size, next_vehicles, moves + 1, this);
					states.add(next_state);
				}
				break;
			case 'v':
				// Moving down
				for (int y = vehicles[v].y_end + 1; y <= size && grid[y][vehicles[v].x_start] == 0; y++) {
					Vehicle[] next_vehicles = copyVehicles();
					next_vehicles[v].y_end = y;
					next_vehicles[v].y_start = y - (vehicles[v].length - 1);
					next_state = new State(num_vehicles, size, next_vehicles, moves + 1, this);
					states.add(next_state);
				}
				// Moving up
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
	
	// Heuristic with the number of cars between the first one and the exit
	public int heuristicCarsBetween() {
		int cars = 0;
		for (int x = vehicles[1].x_end + 1; x <= size; x++) {
			if (grid[vehicles[1].y_start][x] != 0)
				cars++;
		}
		return cars;
	}
	
	// Heuristic with the distance between the first car and the exit
	public int heuristicDistance() {
		return size - vehicles[1].x_start;
	}
	
	// Verify if two states are equal
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
	
	// Creates a unique hashCode for the state, considering that the size of the grid is as most 8
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
