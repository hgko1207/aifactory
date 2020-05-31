package ins.aifactory.service.adhrncMoneyPymnt;

import ins.aifactory.service.lapAdhrnc.LapAdhrnc;
import ins.aifactory.service.lapRank.LapRank;
import ins.core.entity.EntityInfo;

public class AdhrncMoneyPymnt extends EntityInfo {
	
	private LapAdhrnc lapAdhrnc;
	private LapRank lapRank;
	private Integer money;

	public LapAdhrnc getLapAdhrnc() {
		return lapAdhrnc;
	}

	public void setLapAdhrnc(LapAdhrnc lapAdhrnc) {
		this.lapAdhrnc = lapAdhrnc;
	}

	public LapRank getLapRank() {
		return lapRank;
	}

	public void setLapRank(LapRank lapRank) {
		this.lapRank = lapRank;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
}
