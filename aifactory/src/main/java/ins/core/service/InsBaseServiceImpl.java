package ins.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import ins.core.dao.InsBaseDAO;
import ins.core.entity.EntityCriterion;
import ins.core.entity.EntityInfo;
import ins.core.entity.EntityPage;
import ins.core.entity.PagingInfo;

public abstract class InsBaseServiceImpl<T extends EntityInfo, C extends EntityCriterion> extends EgovAbstractServiceImpl implements InsBaseService<T, C> {

    protected Log logger = LogFactory.getLog(getClass());
    
    @Resource
    protected InsBaseDAO dao;
    
    protected Class<T> domainClass;
    
    public InsBaseServiceImpl() {
    }
    
    protected InsBaseServiceImpl(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    @Override
    public void insert(T entity) {
        dao.insert(domainClass.getName()+".insert", entity);
    }

    @Override
    public void update(T entity) {
        dao.update(domainClass.getName()+".update", entity);
    }

    @Override
    public void insertOrUpdate(T entity) {
        int cnt = dao.selectOne(domainClass.getName()+".exist", entity);
        if(cnt > 0)
            dao.update(domainClass.getName()+".update", entity);
        else
            dao.insert(domainClass.getName()+".insert", entity);
    }

    @Override
    public int delete(T entity) {
        return dao.delete(domainClass.getName()+".delete", entity);
    }

    @Override
    public int deleteAll() {
        return dao.delete(domainClass.getName()+".deleteAll");
    }

    @Override
    public boolean exist(T entity) {
        Integer cnt = dao.selectOne(domainClass.getName()+".exist", entity);
        return ( cnt > 0 ) ? true : false;
    }

    @Override
    public int searchAllCount() {
        return dao.selectOne(domainClass.getName()+".searchAllCount");
    }

    @Override
    public EntityPage<T> searchAll(C criterion) {
        criterion.getPagingInfo().setPaging(true);
        int total = searchAllCount();
        List<T> content = dao.selectList(domainClass.getName() + ".searchAll", criterion);

        return new EntityPage<T>(content, new PagingInfo(criterion.getPagingInfo(), total));
    }
    
    @Override
    public int searchCount(C criterion) {
        return dao.selectOne(domainClass.getName() + ".searchCount", criterion);
    }

    @Override
    public EntityPage<T> search(C criterion) {
        criterion.getPagingInfo().setPaging(true);
        int total = searchCount(criterion);
        List<T> content = dao.selectList(domainClass.getName() + ".search", criterion);

        return new EntityPage<T>(content, new PagingInfo(criterion.getPagingInfo(), total));
    }

    @Override
    public List<T> listAll(C criterion) {
        criterion.getPagingInfo().setPaging(false);
        return dao.selectList(domainClass.getName() + ".searchAll", criterion);
    }

    @Override
    public List<T> list(C criterion) {
        criterion.getPagingInfo().setPaging(false);
        return dao.selectList(domainClass.getName() + ".search", criterion);
    }

    @Override
    public T detail(T entity) {
        return dao.selectOne(domainClass.getName() + ".detail", entity);
    }
}