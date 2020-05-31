package ins.aifactory.service.adhrncMoneyPymnt;

import java.util.List;

import ins.core.service.InsBaseService;

public interface AdhrncMoneyPymntService extends InsBaseService<AdhrncMoneyPymnt, AdhrncMoneyPymntCriterion> {

    List<AdhrncMoneyPymnt> listStatus(AdhrncMoneyPymntCriterion criterion);
    
}
