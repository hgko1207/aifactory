package ins.aifactory.service.cmmnFileDetail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.core.service.InsBaseServiceImpl;
import ins.core.web.multipart.MultipartFileHandler;

/**
 * 공통파일상세 서비스 구현  클래스
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
@Service("CmmnFileDetailService")
public class CmmnFileDetailServiceImpl extends InsBaseServiceImpl<CmmnFileDetail, CmmnFileDetailCriterion>
		implements CmmnFileDetailService {

	@Autowired
	private MultipartFileHandler handler;

	public CmmnFileDetailServiceImpl() {
		super(CmmnFileDetail.class);
	}

	@Override
	public void insert(CmmnFileDetail entity) {
		dao.insert(domainClass.getName() + ".insert", entity);
		moveToRealDir(entity);
	}

	@Override
	public int delete(CmmnFileDetail entity) {
		CmmnFileDetail detail = dao.selectOne(domainClass.getName() + ".detail", entity);
		deleteReal(detail);
		return dao.delete(domainClass.getName() + ".delete", entity);
	}

	@Override
	public int deleteByAtchFileId(String atchFileId) {
		CmmnFileDetailCriterion criterion = new CmmnFileDetailCriterion();
		criterion.setAtchFileId(atchFileId);
		criterion.getPagingInfo().setPaging(false);
		List<CmmnFileDetail> fileList = dao.selectList(domainClass.getName() + ".search", criterion);
		for (CmmnFileDetail file : fileList) {
			deleteReal(file);
		}

		return dao.delete(domainClass.getName() + ".deleteByAtchFileId", atchFileId);
	}

	private void deleteReal(CmmnFileDetail file) {
		try {
			handler.deleteFile(file.getStreFileNm(), file.getFileStreCours());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void moveToRealDir(CmmnFileDetail file) {
		if (file != null) {
			String domainName;
			String storageFileName;
			domainName = file.getFileStreCours();
			storageFileName = file.getStreFileNm();
			File s = new File(handler.getLocation() + File.separator + "TEMP" + File.separator + storageFileName);
			File t = new File(handler.getLocation() + File.separator + domainName + File.separator + storageFileName);
			try {
				if (!t.getParentFile().exists()) {
					t.getParentFile().mkdirs();
				}
				IOUtils.copyLarge(new FileInputStream(s), new FileOutputStream(t));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
}
