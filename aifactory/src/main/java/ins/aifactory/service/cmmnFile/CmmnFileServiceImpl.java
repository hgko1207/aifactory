package ins.aifactory.service.cmmnFile;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import ins.aifactory.service.cmmnFileDetail.CmmnFileDetail;
import ins.aifactory.service.cmmnFileDetail.CmmnFileDetailCriterion;
import ins.aifactory.service.cmmnFileDetail.CmmnFileDetailService;
import ins.core.service.InsBaseServiceImpl;

/**
 * 공통파일 서비스 구현  클래스
 * @author 전인택
 * @since 2019.10.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일                       수정자           수정내용
 *  -------    --------    ---------------------------
 *   2019.10.08  전인택          최초 생성
 *
 * </pre>
 */
@Service("CmmnFileService")
public class CmmnFileServiceImpl extends InsBaseServiceImpl<CmmnFile, CmmnFileCriterion> implements CmmnFileService {

    @Resource(name = "fileIdGnrService")
    private EgovIdGnrService idgenService;

    @Autowired
    private CmmnFileDetailService fileDetailService;

    public CmmnFileServiceImpl() {
        super(CmmnFile.class);
    }

    @Override
    public CmmnFile detail(CmmnFile entity) {
        CmmnFile cmmnFile = new CmmnFile();
        if (entity != null) {
            cmmnFile = dao.selectOne(domainClass.getName() + ".detail", entity);
            CmmnFileDetailCriterion fileDetailCriterion = new CmmnFileDetailCriterion();
            fileDetailCriterion.setAtchFileId(cmmnFile.getAtchFileId());
            List<CmmnFileDetail> fileDetailList = fileDetailService.list(fileDetailCriterion);
            cmmnFile.setFiles(fileDetailList);
        }
        return cmmnFile;
    }

    @Override
    public void insert(CmmnFile entity) {
        try {
            // ID 채번
            entity.setAtchFileId(idgenService.getNextStringId());
            dao.insert(domainClass.getName() + ".insert", entity);

            // File Detail 등록
            for (CmmnFileDetail fileDetail : entity.getFiles()) {
                fileDetail.setCmmnFile(entity);
                fileDetailService.insert(fileDetail);
            }

        } catch (FdlException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void update(CmmnFile entity) {
        try {
            if (entity == null) {
                entity = new CmmnFile();
            }

            if (StringUtils.isBlank(entity.getAtchFileId())) {
                // ID 채번
                entity.setAtchFileId(idgenService.getNextStringId());
                dao.insert(domainClass.getName() + ".insert", entity);
            }

            fileDetailService.deleteByAtchFileId(entity.getAtchFileId());

            // File Detail 등록
            CmmnFileDetail fileDetail;
            for (int i = 0; entity.getFiles() != null && i < entity.getFiles().size(); i++) {
                fileDetail = entity.getFiles().get(i);
                fileDetail.setCmmnFile(entity);
                fileDetailService.insert(fileDetail);
            }
        } catch (FdlException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public int delete(CmmnFile entity) {
        fileDetailService.deleteByAtchFileId(entity.getAtchFileId());
        return dao.delete(domainClass.getName() + ".delete", entity);
    }
}
