import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class RushHour {

	Vehicle[] vehicles;
	
	int num_vehicles, size;
	
	int[][] state;
	
	public RushHour(String name) {
		try {
			FileReader file = new FileReader(name);
			BufferedReader readFile = new BufferedReader(file);
			
			String line;
			
			line = readFile.readLine();
			if (line != null) {
				this.size = Integer.parseInt(line);
				//System.out.println(this.size);
				this.state = new int[size + 1][size + 1];
				
				for (int i = 1; i <= size; i++) {
					for (int j = 1; j <= size; j++)
						state[i][j] = 0;
				}
			}
			
			line = readFile.readLine();
			if (line != null) {
				this.num_vehicles = Integer.parseInt(line);
				//System.out.println(this.num_vehicles);
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
			
			setGrid();
			
			file.close();
		} catch(IOException e) {
			System.err.printf("Error opening file : %s.\n", e.getMessage());
		}
	}
	
	
	private void setGrid() {
		for (int v = 1; v <= num_vehicles; v++) {
			for (int i = vehicles[v].y_start; i <= vehicles[v].y_end; i++) {
				for (int j = vehicles[v].x_start; j <= vehicles[v].x_end; j++) {
					if (state[i][j] == 0)
						state[i][j] = v;
				}
			}
		}
	}
	
	private void printState() {
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if (j < size)
					System.out.print(state[i][j] + " ");
				else
					System.out.println(state[i][j]);
			}
		}
	}
	
	public static void main(String[] args) {
		RushHour test = new RushHour("initial-state.txt");
		test.printState();
	}

}
