package ins.aifactory.service.taskData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.aifactory.service.cmmnFile.CmmnFileService;
import ins.core.service.InsBaseServiceImpl;

/**
 * Task Data 관리 서비스 구현  클래스
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
@Service("TaskDataService")
public class TaskDataServiceImpl extends InsBaseServiceImpl<TaskData, TaskDataCriterion> implements TaskDataService{
    
    @Autowired
    private CmmnFileService cmmnFileService;
    
    public TaskDataServiceImpl() {
        super(TaskData.class);
    }
    
    @Override
    public TaskData detail(TaskData entity) {
        TaskData taskData = dao.selectOne(domainClass.getName() + ".detail", entity);
        if(taskData != null){
            CmmnFile cmmnFile = cmmnFileService.detail(taskData.getCmmnFile());
            taskData.setCmmnFile(cmmnFile);
        }
        return taskData;
    }
    
    @Override
    public void insert(TaskData entity) {
        cmmnFileService.insert(entity.getCmmnFile());
        dao.insert(domainClass.getName()+".insert", entity);
    }
    
    @Override
    public void update(TaskData entity) {
        cmmnFileService.update(entity.getCmmnFile());
        dao.update(domainClass.getName()+".update", entity);
    }

}
