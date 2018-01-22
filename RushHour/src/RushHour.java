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
			
			// Get the size of the grid from the file
			line = readFile.readLine();
			if (line != null) {
				this.size = Integer.parseInt(line);
			}
			
			// Get the number of vehicles from the file
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
			
			// Get the informations for each vehicle
			while (line != null) {
				new_vehicle_line = new StringTokenizer(line, " ");
				id = Integer.parseInt(new_vehicle_line.nextElement().toString());
				orientation = new_vehicle_line.nextElement().toString().charAt(0);
				length = Integer.parseInt(new_vehicle_line.nextElement().toString());
				x = Integer.parseInt(new_vehicle_line.nextElement().toString());
				y = Integer.parseInt(new_vehicle_line.nextElement().toString());
				new_vehicle = new Vehicle(id, orientation, length, x, y);
				this.vehicles[id] = new_vehicle;
				
				line = readFile.readLine();
			}
			
			initial_state = new State(num_vehicles, size, vehicles);
			
			// Queue storing the next states to visit
			this.next_states = new LinkedList<State>();
			this.next_states.add(initial_state);
			
			// HashSet storing the states that have already been visited
			this.visited_states = new HashSet<State>();
			this.visited_states.add(initial_state);
			
			file.close();
		} catch(IOException e) {
			System.err.printf("Error opening file : %s.\n", e.getMessage());
		}
	}
	
	// Verify each possible state until finding a solution
	public int solve() {
		State next_state, possible_state;
		Queue<State> possible_states;
		// Verify each state in the queue
		while(!next_states.isEmpty()) {
			next_state = next_states.poll();
			//next_state.printState();
			//System.out.println("moves : " + next_state.moves + " hash : " + next_state.hashCode());
			
			// If this state is a solution
			if(next_state.final_state) {
				printSolution(next_state);
				return next_state.moves;
			}
			
			// Get all possible states after this one, with one move, and verify each one of them
			possible_states = next_state.possibleStates();
			while(!possible_states.isEmpty()) {
				possible_state = possible_states.poll();
				// If it is a state that has not beet visited yet, adds it to the queue
				if(!visited_states.contains(possible_state)) {
					next_states.add(possible_state);
					visited_states.add(possible_state);
				}
			}
		}
		return -1;
	}
	
	// Verify each possible state until finding a solution, using an heuristic
		public int solveHeuristic() {
			State next_state, possible_state;
			Queue<State> possible_states;
			// Verify each state in the queue
			while(!next_states.isEmpty()) {
				next_state = next_states.poll();
				//next_state.printState();
				//System.out.println("moves : " + next_state.moves + " hash : " + next_state.hashCode());
				
				// If this state is a solution
				if(next_state.final_state) {
					printSolution(next_state);
					return next_state.moves;
				}
				
				// Get all possible states after this one, with one move, and verify each one of them
				possible_states = next_state.possibleStates();
				while(!possible_states.isEmpty()) {
					possible_state = possible_states.poll();
					// If it is a state that has not beet visited yet and the heuristic is equal or less than the one from before
					// Adds it to the queue
					if(!visited_states.contains(possible_state) && 
							possible_state.heuristicCarsBetween() <= next_state.heuristicCarsBetween()) {
						next_states.add(possible_state);
						visited_states.add(possible_state);
					}
				}
			}
			return -1;
		}
	
	// Print the sequence of steps necessary to arrive at the solution
	private void printSolution(State solution) {
		Stack<State> steps = new Stack<State>();
		int num_steps = 0;
		
		// Get all previous states, starting from the solution until the initial state
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
		RushHour test = new RushHour("inputs/GameP11.txt");
		long startTime, endTime, time;
		
		startTime = System.nanoTime();
		System.out.println("Total steps : " + test.solve());
		endTime = System.nanoTime();
		time = (endTime - startTime) / 1000000;
		System.out.println("Elapsed time in milliseconds : " + time);
		
		startTime = System.nanoTime();
		System.out.println("Total steps : " + test.solveHeuristic());
		endTime = System.nanoTime();
		time = (endTime - startTime) / 1000000;
		System.out.println("Elapsed time in milliseconds : " + time);
	}

}
