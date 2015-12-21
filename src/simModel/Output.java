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
			switch(j) {
				case Const.CS_OP10:
					System.out.print(j + " CS_OP10  ");
					break;
				case Const.CS_OP20:
					System.out.print(j + " CS_OP20  ");
					break;
				case Const.CS_OP30:
					System.out.print(j + " CS_OP30  ");
					break;
				case Const.CS_OP40A:
					System.out.print(j + " CS_OP40A ");
					break;
				case Const.CS_OP40B:
					System.out.print(j + " CS_OP40B ");
					break;
				case Const.CS_OP40C:
					System.out.print(j + " CS_OP40C ");
					break;
				case Const.CS_OP40D:
					System.out.print(j + " CS_OP40D ");
					break;
				case Const.CS_OP40E:
					System.out.print(j + " CS_OP40E ");
					break;
				case Const.CS_TEST:
					System.out.print(j + " CS_TEST  ");
					break;
				case Const.CS_REWORK:
					System.out.print(j + " CS_REWORK");
					break;
				case Const.CS_RETEST:
					System.out.print(j + " CS_RETEST");
					break;
				case Const.CS_OP50:
					System.out.print(j + " CS_OP50  ");
					break;
				case Const.CS_OP60:
					System.out.print(j + " CS_OP60  ");
					break;
			}
			System.out.print(" \t(" + model.conveyorSegments[j].getCapacity() + ")"
					+ ((model.conveyorSegments[j].getCapacity() < 10) ? "  " : " ") + "<");

			for (int i = model.conveyorSegments[j].getCapacity() - 1; i >= 0; i--) {
				int palletID = model.conveyorSegments[j].palletPositions[i];
				if (palletID == -1)
					System.out.print("_____");
				else {
					String tvType;
					switch (model.pallets[palletID].tvType) {
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
					System.out.print("[" + tvType + ((model.pallets[palletID].inMotion) ? "m" : " ")
							+ ((model.pallets[palletID].finishedProcessing) ? "s]" : " ]"));
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
