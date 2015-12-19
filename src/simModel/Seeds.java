package simModel;
/**
 * @author mush
 */
import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
	int seed1;
	int seed2;
	int seed3;
	int seed4;
	int seed5;
	int seed6;
	int seed7;
	int seed8;
	int seed9;
	int seed10;
	int seed11;
	int seed12;
	int seed13;
	int seed14;

	public Seeds(RandomSeedGenerator rsg)
	{
		seed1=rsg.nextSeed();
		seed2=rsg.nextSeed();
		seed3=rsg.nextSeed();
		seed4=rsg.nextSeed();
		seed5=rsg.nextSeed();
		seed6=rsg.nextSeed();
		seed7=rsg.nextSeed();
		seed8=rsg.nextSeed();
		seed9=rsg.nextSeed();
		seed10=rsg.nextSeed();
		seed11=rsg.nextSeed();
		seed12=rsg.nextSeed();
		seed13=rsg.nextSeed();
		seed14=rsg.nextSeed();
	}
}
