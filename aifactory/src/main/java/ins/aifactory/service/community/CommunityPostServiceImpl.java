package ins.aifactory.service.community;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import ins.aifactory.service.cmmnFile.CmmnFileService;
import ins.core.service.InsBaseServiceImpl;

@Service("CommunityPostService")
public class CommunityPostServiceImpl extends InsBaseServiceImpl<Post, PostCriterion> implements PostService {

	@Resource(name = "communityPostIdGnrService")
	private EgovIdGnrService idgenService;

	@Autowired
	private CmmnFileService cmmnFileService;

	public CommunityPostServiceImpl() {
		super(Post.class);
	}

	@Override
	public void insert(Post entity) {
		try {
			// ID 채번
			entity.setPostId(idgenService.getNextStringId());
			cmmnFileService.insert(entity.getCmmnFile());
			System.out.println("변지수변지수변지수변지수변지수변지수");
			System.out.println(this.getClass().getName());
			dao.insert(domainClass.getName() + ".insert", entity);

		} catch (FdlException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void update(Post entity) {
		// 첨부파일 변경시
		if (entity.isFileChange()) {
			cmmnFileService.update(entity.getCmmnFile());
		}

		// Task 수정
		dao.update(domainClass.getName() + ".update", entity);
	}
}
