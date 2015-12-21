package simModel;

/**
 * 
 * @author mush
 *
 */
class DVPs {
	PanoramaTV model; // for accessing the clock

	TvType tvType = TvType.Small;
	int numCurrentTV = 0;

	/**
	 * 
	 * @param model
	 *            :
	 */
	protected DVPs(PanoramaTV model) {
		this.model = model;
	}

	// Translate deterministic value procedures into methods
	/*
	 * ------------------------------------------------- Example protected
	 * double getEmpNum() // for getting next value of EmpNum(t) { double
	 * nextTime; if(model.clock == 0.0) nextTime = 90.0; else if(model.clock ==
	 * 90.0) nextTime = 210.0; else if(model.clock == 210.0) nextTime = 420.0;
	 * else if(model.clock == 420.0) nextTime = 540.0; else nextTime = -1.0; //
	 * stop scheduling return(nextTime); }
	 * ------------------------------------------------------------
	 */

	public TvType uTvType() {
		if (tvType == TvType.Small) {
			if (numCurrentTV < Const.numSmallTvs) {
				numCurrentTV++;
			} else {
				tvType = TvType.Medium;
				numCurrentTV = 1;
			}
		} else if (tvType == TvType.Medium) {
			if (numCurrentTV < Const.numMediumTvs) {
				numCurrentTV++;
			} else {
				tvType = TvType.Large;
				numCurrentTV = 1;
			}
		} else if (tvType == TvType.Large) {
			if (numCurrentTV < Const.numLargeTvs) {
				numCurrentTV++;
			} else {
				tvType = TvType.Flat;
				numCurrentTV = 1;
			}
		} else if (tvType == TvType.Flat) {
			if (numCurrentTV < Const.numFlatTvs) {
				numCurrentTV++;
			} else {
				tvType = TvType.Small;
				numCurrentTV = 1;
			}
		}

		return tvType;
	}

	public double uAutomaticProcessTime(int autoNodeId) {
		switch(autoNodeId)
		{
		case Const.OP10:
			return 2.1;
		case Const.OP30:
			return 2;
		case Const.OP50:
			return 2.1;
		case Const.TEST:
			return 1.5;
		default:
			return 0;
				
		}
	}

	public double uElectronicAssemblyTime() {
		return 1.5;
	}
}
