package ins.aifactory.service.lapRank;

import org.springframework.stereotype.Service;

import ins.core.service.InsBaseServiceImpl;

/**
 * 랩 순위 관리 서비스 구현  클래스
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
@Service("LapRankService")
public class LapRankServiceImpl extends InsBaseServiceImpl<LapRank, LapRankCriterion> implements LapRankService{
    
    public LapRankServiceImpl() {
        super(LapRank.class);
    }

    @Override
    public int deleteByLap(LapRank entity) {
        return dao.delete(domainClass.getName()+".deleteByLap", entity);
    }

    @Override
    public int deleteByTask(LapRank entity) {
        return dao.delete(domainClass.getName()+".deleteByTask", entity);
    }

}
