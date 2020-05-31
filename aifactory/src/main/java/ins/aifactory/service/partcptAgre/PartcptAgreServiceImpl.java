package ins.aifactory.service.partcptAgre;

import org.springframework.stereotype.Service;

import ins.core.service.InsBaseServiceImpl;

/**
 * 참가 관리 서비스 구현  클래스
 * @author 전인택
 * @since 2020.01.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2020.01.09  전인택          최초 생성
 *
 * </pre>
 */
@Service("PartcptAgreService")
public class PartcptAgreServiceImpl extends InsBaseServiceImpl<PartcptAgre, PartcptAgreCriterion> implements PartcptAgreService{

    public PartcptAgreServiceImpl() {
        super(PartcptAgre.class);
    }
    
}
