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
	final static int OP10_ID   = 0;
	final static int OP20_ID   = 1;
	final static int OP30_ID   = 2;
	
	final static int OP40A_ID  = 3;
	final static int OP40B_ID  = 4;
	final static int OP40C_ID  = 5;
	final static int OP40D_ID  = 6;
	final static int OP40E_ID  = 7;
	
	final static int TEST_ID   = 8;
	
	final static int REWORK_ID = 9;
	
	final static int OP50_ID   = 10;
	final static int OP60_ID   = 11;
	
	final static int[] CAPACITY = 
		{
			40, // OP10_ID ~ 40 - 120
			5,  // OP20_ID ~ 5-15
			5,  // OP30_ID ~ 5-15
			5,  // OP40ACID
			1,  // OP40BCID
			1,  // OP40CCID
			1,  // OP40DCID
			1,  // OP40ECID
			5,  // TEST_ID ~ 5-15
			10, // REWORK_ID ~ 10-30
			5,  // OP50_ID ~ 5-15
			5   // OP60_ID ~ 5-15
		};

}
