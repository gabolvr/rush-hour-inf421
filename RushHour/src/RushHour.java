import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class RushHour {

	Vehicle[] vehicles;
	
	int num_vehicles, size;
	
	State initial_state;
	
	public HashSet<State> visited_states;
	
	public HashSet<Integer> visited;
	
	public Queue<State> next_states;
	
	public RushHour(String name) {
		try {
			FileReader file = new FileReader(name);
			BufferedReader readFile = new BufferedReader(file);
			
			String line;
			
			line = readFile.readLine();
			if (line != null) {
				this.size = Integer.parseInt(line);
			}
			
			line = readFile.readLine();
			if (line != null) {
				this.num_vehicles = Integer.parseInt(line);
				this.vehicles = new Vehicle[num_vehicles + 1];
			}
			
			line = readFile.readLine();
			StringTokenizer new_vehicle_line;
			Vehicle new_vehicle;
			
			int id, length, x, y;
			char orientation;
			
			while (line != null) {
				new_vehicle_line = new StringTokenizer(line, " ");
				id = Integer.parseInt(new_vehicle_line.nextElement().toString());
				orientation = new_vehicle_line.nextElement().toString().charAt(0);
				length = Integer.parseInt(new_vehicle_line.nextElement().toString());
				x = Integer.parseInt(new_vehicle_line.nextElement().toString());
				y = Integer.parseInt(new_vehicle_line.nextElement().toString());
				new_vehicle = new Vehicle(id, orientation, length, x, y);
				this.vehicles[id] = new_vehicle;
				
				//System.out.println(id + " - " + orientation + " - " + length + " - " + x + " - " + y);
				
				line = readFile.readLine();
			}
			
			initial_state = new State(num_vehicles, size, vehicles);
			
			this.next_states = new LinkedList<State>();
			this.next_states.add(initial_state);
			
			this.visited_states = new HashSet<State>();
			this.visited_states.add(initial_state);
			
			file.close();
		} catch(IOException e) {
			System.err.printf("Error opening file : %s.\n", e.getMessage());
		}
	}
	
	public int solve() {
		State next_state, possible_state;
		Queue<State> possible_states;
		while(!next_states.isEmpty()) {
			next_state = next_states.poll();
			//next_state.printState();
			//System.out.println("moves : " + next_state.moves + " hash : " + next_state.hashCode());
			
			if(next_state.final_state) {
				printSolution(next_state);
				return next_state.moves;
			}
			
			possible_states = next_state.possibleStates();
			while(!possible_states.isEmpty()) {
				possible_state = possible_states.poll();
				if(!visited_states.contains(possible_state)) {
					next_states.add(possible_state);
					visited_states.add(possible_state);
				}
			}
		}
		return -1;
	}
	
	private void printSolution(State solution) {
		Stack<State> steps = new Stack<State>();
		int num_steps = 0;
		
		while(solution != null) {
			steps.push(solution);
			solution = solution.previous_state;
		}
		
		while(!steps.isEmpty()) {
			System.out.println("# Step " + num_steps + " :");
			solution = steps.pop();
			solution.printState();
			System.out.println("-----");
			num_steps++;
		}
	}
	
	public static void main(String[] args) {
		RushHour test = new RushHour("initial-state.txt");
		System.out.println("Total steps : " + test.solve());
	}

}
