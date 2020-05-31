package ins.aifactory.service.lapRank;

import ins.core.service.InsBaseService;

public interface LapRankService extends InsBaseService<LapRank, LapRankCriterion> {
    
    int deleteByLap(LapRank entity);

    int deleteByTask(LapRank entity);
}
