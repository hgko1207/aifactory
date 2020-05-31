package ins.aifactory.service.lap;

import ins.core.service.InsBaseService;

public interface LapService extends InsBaseService<Lap, LapCriterion> {

    int deleteByTask(Lap entity);

    void updateEndProc();

    void updateStartProc();

    void updateRankProc();
    
}
