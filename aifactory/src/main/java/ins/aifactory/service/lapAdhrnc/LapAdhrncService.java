package ins.aifactory.service.lapAdhrnc;

import java.util.List;

import ins.aifactory.service.lap.Lap;
import ins.core.service.InsBaseService;

public interface LapAdhrncService extends InsBaseService<LapAdhrnc, LapAdhrncCriterion> {

    void updateRank(List<Lap> lapList);

}
