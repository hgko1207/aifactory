package ins.aifactory.service.cmmnCode;

import org.springframework.stereotype.Service;

import ins.core.service.InsBaseServiceImpl;

/**
 * 공통코드 서비스 구현  클래스
 * @author 전인택
 * @since 2019.10.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일                       수정자           수정내용
 *  -------    --------    ---------------------------
 *   2019.10.01  전인택          최초 생성
 *
 * </pre>
 */
@Service("CmmnCodeService")
public class CmmnCodeServiceImpl extends InsBaseServiceImpl<CmmnCode, CmmnCodeCriterion> implements CmmnCodeService{
    
    public CmmnCodeServiceImpl() {
        super(CmmnCode.class);
    }
}
