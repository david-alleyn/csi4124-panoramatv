package simModel;

import java.io.*;

public class Output {
	public int numTVAssembled = 0;

	private PanoramaTV model;

	public Output(PanoramaTV model) {
		this.model = model;
	}

	public void printConveyors(double clock) {
		System.out.println("\nTime: " + clock + ", # TVs Produced: " + numTVAssembled);
		for (int j = 0; j < model.conveyorSegments.length; j++) {
			System.out.print(j + " \t(" + model.conveyorSegments[j].getCapacity() + ")"
					+ ((model.conveyorSegments[j].getCapacity() < 10) ? "  " : " ") + "<");

			for (int i = model.conveyorSegments[j].getCapacity() - 1; i >= 0; i--) {
				if (model.conveyorSegments[j].positions[i] == null)
					System.out.print("_");
				else {
					String tvType;
					switch (model.conveyorSegments[j].positions[i].tvType) {
					case Small:
						tvType = "S";
						break;
					case Medium:
						tvType = "M";
						break;
					case Large:
						tvType = "L";
						break;
					case Flat:
						tvType = "F";
						break;
					default:
						tvType = "E";
					}
					System.out.print("[" + tvType + ((model.conveyorSegments[j].positions[i].inMotion) ? "m" : "")
							+ ((model.conveyorSegments[j].positions[i].finishedProcessing) ? "s]" : "]"));
				}
				System.out.print((i == 0) ? ">\n" : ",");
			}
		}
	}

	public BufferedWriter out;

	public void printCSV(int divisor) {
		try {
			out.write(numTVAssembled / divisor + ",");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void flushCSVline() {
		try {
			out.write("\n");
			out.flush();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
