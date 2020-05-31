package ins.aifactory.service.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ins.core.service.InsBaseServiceImpl;

/**
 * Q & A 관리 서비스 구현  클래스
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
@Service("QnaService")
public class QnaServiceImpl extends InsBaseServiceImpl<Qna, QnaCriterion> implements QnaService{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QnaServiceImpl.class);
    
    public QnaServiceImpl() {
        super(Qna.class);
    }

    @Override
    public void updateAnswer(Qna entity) {
        dao.update(domainClass.getName()+".updateAnswer", entity);
    }
    
}