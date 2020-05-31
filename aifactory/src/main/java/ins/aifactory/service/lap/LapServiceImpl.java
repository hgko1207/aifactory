package ins.aifactory.service.lap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.aifactory.service.lapAdhrnc.LapAdhrncService;
import ins.aifactory.service.lapRank.LapRank;
import ins.aifactory.service.lapRank.LapRankService;
import ins.core.service.InsBaseServiceImpl;

/**
 * 랩 관리 서비스 구현  클래스
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
@Service("LapService")
public class LapServiceImpl extends InsBaseServiceImpl<Lap, LapCriterion> implements LapService{
    
    @Autowired
    private LapRankService lapRankService;
    
    @Autowired LapAdhrncService lapAdhrncService;
    
    public LapServiceImpl() {
        super(Lap.class);
    }
    
    @Override
    public void insert(Lap entity) {
        dao.insert(domainClass.getName()+".insert", entity);
        
        // Lap Rank 등록
        if(entity.getLapRank() != null ){
            for(LapRank lapRank : entity.getLapRank()){
                lapRank.setLap(entity);
                lapRankService.insert(lapRank);
            }
        }
    }
    
    @Override
    public void update(Lap entity) {
        dao.update(domainClass.getName()+".update", entity);
        
        // Lap Rank 삭제
        LapRank delLapRank = new LapRank();
        delLapRank.setLap(entity);
        lapRankService.deleteByLap(delLapRank);
        
        // Lap Rank 등록
        if(entity.getLapRank() != null ){
            for(LapRank lapRank : entity.getLapRank()){
                lapRank.setLap(entity);
                lapRankService.insert(lapRank);
            }
        }
    }

    @Override
    public int deleteByTask(Lap entity) {
        // Rank 삭제
        LapRank lapRank = new LapRank();
        lapRank.setLap(entity);
        lapRankService.deleteByTask(lapRank);
        
        // Lap 삭제
        return dao.delete(domainClass.getName()+".deleteByTask", entity);
    }

    /*
     * Lap의 종료 처리
     *   1. 활성상태(ACTVTY_YN = 'Y')인 랩
     *   2. END_DTTM의 값이 있을 경우 END_DTTM의 값이 CURRENT_DATE보다 작거나 같은 랩
     *   3. 체크 타임에 GOAL_SCRE이상인 참가자가 있는 랩
     */
    @Override
    public void updateEndProc() {
        // 종료 Lap 조회
        List<Lap> lapList = dao.selectList(domainClass.getName()+".searchEndLap");
        
        if(lapList.size() > 0){
            // 종료 처리
            dao.update(domainClass.getName()+".updateEndProc", lapList);
        
            // 종료된 Lap의 Rank 업데이트
            lapAdhrncService.updateRank(lapList);
        }
    }

    // Lap의 시작 처리
    @Override
    public void updateStartProc() {
        dao.update(domainClass.getName()+".updateStartProc");
    }

    // Lap의 랭크 업데이트
    @Override
    public void updateRankProc() {
        List<Lap> lapList = dao.selectList(domainClass.getName()+".searchChkLap");
        lapAdhrncService.updateRank(lapList);
    }
}
