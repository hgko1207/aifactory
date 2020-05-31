package ins.aifactory.service.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import ins.aifactory.service.cmmnFile.CmmnFileService;
import ins.aifactory.service.lap.Lap;
import ins.aifactory.service.lap.LapService;
import ins.core.service.InsBaseServiceImpl;

/**
 * 태스크 관리 서비스 구현  클래스
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
@Service("TaskService")
public class TaskServiceImpl extends InsBaseServiceImpl<Task, TaskCriterion> implements TaskService{
    
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Resource(name = "taskIdGnrService")
	private EgovIdGnrService idgenService;

	@Autowired
	private LapService lapService;

	@Autowired
	private CmmnFileService cmmnFileService;

	public TaskServiceImpl() {
		super(Task.class);
	}

	@Override
	public void insert(Task entity) {
		try {
			// ID 채번
			entity.setTaskId(idgenService.getNextStringId());
			System.out.println("qusaslsdlfjksalfj");
			System.out.println(entity.getCmmnFile());
			cmmnFileService.insert(entity.getCmmnFile());
			dao.insert(domainClass.getName() + ".insert", entity);

			// Lap 등록
			if (entity.getLaps() != null) {
				Lap lap;
				for (int i = 0; i < entity.getLaps().size(); i++) {
					lap = entity.getLaps().get(i);
					lap.setTask(entity);

					// 첫번째 Lap이면 Task 시작일자 를 Lap의 시작일자로
					if (i == 0) {
						lap.setBeginDttm(entity.getBeginDttm());
					}
					lap.setLapSn(i + 1);
					lapService.insert(lap);
				}
			}

		} catch (FdlException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void update(Task entity) {
		// 첨부파일 변경시
		if (entity.isFileChange()) {
			cmmnFileService.update(entity.getCmmnFile());
		}

		// Task 수정
		dao.update(domainClass.getName() + ".update", entity);

		try {
			// Lap 정보 수정
			if (entity.getLaps() != null) {
				Lap lap;
				Integer prevLapSn = 0;
				for (int i = 0; i < entity.getLaps().size(); i++) {
					lap = entity.getLaps().get(i);
					lap.setTask(entity);

					// 첫번째 Lap이면 Task 시작일자 를 Lap의 시작일자로
					if (i == 0) {
						lap.setBeginDttm(entity.getBeginDttm());
					}
					// // 이전 lAP의 종료일자 를 Lap의 시작일자로
					// else{
					// lap.setBeginDttm(entity.getLaps().get(i-1).getEndDttm());
					// }

					if (lap.getLapSn() == null) {
						prevLapSn++;
						lap.setLapSn(prevLapSn);
						lapService.insert(lap);
					} else {
						lapService.update(lap);
						prevLapSn = lap.getLapSn();
					}
				}
			}
		} catch (Exception e) {
			LOGGER.debug("LAP UPDATE ERROR", e);
		}
	}

}
