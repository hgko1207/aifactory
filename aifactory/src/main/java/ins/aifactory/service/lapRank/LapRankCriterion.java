package ins.aifactory.service.lapRank;

import ins.aifactory.service.lap.Lap;
import ins.core.entity.EntityCriterion;

public class LapRankCriterion extends EntityCriterion {

	private Lap lap;

	public LapRankCriterion() {
	}

	public Lap getLap() {
		return lap;
	}

	public void setLap(Lap lap) {
		this.lap = lap;
	}

}
