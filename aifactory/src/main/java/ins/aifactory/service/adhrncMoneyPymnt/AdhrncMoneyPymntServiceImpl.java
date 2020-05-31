package ins.aifactory.service.adhrncMoneyPymnt;

import java.util.List;

import org.springframework.stereotype.Service;

import ins.core.service.InsBaseServiceImpl;

/**
 * 참가자 지급 관리 서비스 구현  클래스
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
@Service("AdhrncMoneyPymntService")
public class AdhrncMoneyPymntServiceImpl extends InsBaseServiceImpl<AdhrncMoneyPymnt, AdhrncMoneyPymntCriterion> implements AdhrncMoneyPymntService{
    
    public AdhrncMoneyPymntServiceImpl() {
        super(AdhrncMoneyPymnt.class);
    }
    
    @Override
    public List<AdhrncMoneyPymnt> listStatus(AdhrncMoneyPymntCriterion criterion) {
        criterion.getPagingInfo().setPaging(false);
        return dao.selectList(domainClass.getName() + ".listStatus", criterion);
    }

}
