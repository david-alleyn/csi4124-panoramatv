package simModel;

import java.io.BufferedWriter;

/**
 * 
 * @author mush
 *
 */
public class Output 
{
	PanoramaTV model;
	public BufferedWriter out;

	
	protected Output(PanoramaTV md) { model = md; }
    // Use OutputSequence class to define Trajectory and Sample Sequences
    // Trajectory Sequences

    // Sample Sequences

    // DSOVs available in the OutputSequence objects
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here

    // SSOVs
	public void flushCSVline()
	{
		try {
			out.write("\n");
			out.flush();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}