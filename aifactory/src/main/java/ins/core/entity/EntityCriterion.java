package ins.core.entity;

public class EntityCriterion {

    private PagingInfo pagingInfo;

    public PagingInfo getPagingInfo() {
        if( pagingInfo == null ){
            pagingInfo = new PagingInfo();
        }
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }
}