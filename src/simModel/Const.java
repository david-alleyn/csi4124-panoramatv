package simModel;

public class Const 
{
	/* Constants */
	// Define constants as static
	// Example: protected final static double realConstant = 8.0;
	public static final int ELECTRONIC_ON_SIZE = 1;
	public static final int numPallets = 40;
	
	/**
	 * Conveyer ID.
	 */
	final static int CS_OP10   = 0;
	final static int CS_OP20   = 1;
	final static int CS_OP30   = 2;
	
	final static int CS_OP40A  = 3;
	final static int CS_OP40B  = 4;
	final static int CS_OP40C  = 5;
	final static int CS_OP40D  = 6;
	final static int CS_OP40E  = 7;
	
	final static int CS_TEST   = 8;
	
	final static int CS_REWORK = 9;
	
	final static int CS_OP50   = 10;
	final static int CS_OP60   = 11;
	
	/**
	 * AutoNode ID.
	 */
	final static int OP20   = 0;
	final static int OP30   = 1;
	final static int TEST   = 2;
	final static int OP50   = 3;
	
	/**
	 * ManualNode ID.
	 */
	final static int OP10   = 0;
	final static int OP40a   = 1;
	final static int OP40b   = 2;
	final static int OP40c   = 3;
	final static int OP40d   = 4;
	final static int OP40e   = 5;
	final static int REWORK = 6;
	final static int OP60 = 7;
	
	final static int[] CAPACITY = 
		{
			40, // CS_OP10 ~ 40 - 120
			5,  // CS_OP20 ~ 5-15
			5,  // CS_OP30 ~ 5-15
			5,  // CS_OP40A
			1,  // CS_OP40B
			1,  // CS_OP40C
			1,  // CS_OP40D
			1,  // CS_OP40E
			5,  // CS_TEST ~ 5-15
			10, // CS_REWORK ~ 10-30
			5,  // CS_RETEST
			5,  // CS_OP50 ~ 5-15
			5   // CS_OP60 ~ 5-15
		};

}
