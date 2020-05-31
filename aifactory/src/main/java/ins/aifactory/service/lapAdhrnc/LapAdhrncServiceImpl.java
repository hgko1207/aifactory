package ins.aifactory.service.lapAdhrnc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.aifactory.service.cmmnFile.CmmnFileService;
import ins.aifactory.service.lap.Lap;
import ins.core.service.InsBaseServiceImpl;

/**
 * 랩 참가자 관리 서비스 구현  클래스
 * @author 전인택
 * @since 2019.10.02
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2019.10.02  전인택          최초 생성
 *
 * </pre>
 */
@Service("LapAdhrncService")
public class LapAdhrncServiceImpl extends InsBaseServiceImpl<LapAdhrnc, LapAdhrncCriterion> implements LapAdhrncService{
    
    @Autowired
    private CmmnFileService cmmnFileService;
    
    public LapAdhrncServiceImpl() {
        super(LapAdhrnc.class);
    }
    
    @Override
    public void insert(LapAdhrnc entity) {
        cmmnFileService.insert(entity.getCmmnFile());
        dao.insert(domainClass.getName()+".insert", entity);
    }

    @Override
    public void updateRank(List<Lap> lapList) {
        dao.update(domainClass.getName()+".updateRank", lapList);
    }

}
