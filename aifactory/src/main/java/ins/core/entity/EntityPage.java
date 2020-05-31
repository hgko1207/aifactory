package ins.core.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityPage<T> {

    // 페이징정보 
    private PagingInfo pagingInfo;
    
    // 검색 결과
    private List<T> content = new ArrayList<T>();
    
    public EntityPage(List<T> content, PagingInfo pagingInfo) {
        this.content = content;
        this.pagingInfo = pagingInfo;
    }

    public PagingInfo getPagingInfo() {
        if( this.pagingInfo == null ) this.pagingInfo = new PagingInfo();
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
    
}
