package ins.aifactory.service.qna;

import ins.core.service.InsBaseService;

public interface QnaService extends InsBaseService<Qna, QnaCriterion> {

    void updateAnswer(Qna entity);
    
}
