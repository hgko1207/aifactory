package ins.core.entity;

public class PagingInfo {
    
    // 페이징 처리 여부
    private boolean paging;
    
    // 전체 데이타 갯수
    private int totalElementCount;
    
    // 페이지 사이즈( maxRow/page )
    private int pageSize;
    
    // 블럭 : 화면에 표출되는 페이지 리스트 단위 ( << < 1 2 3 ... > >> )
    // 블럭 사이즈
    private int blockSize;
    
    // 현재 페이지 번호(zero index)
    private int pageNo;
    
    public PagingInfo(){
        this.blockSize = 10;
        this.pageSize = 20;
        this.pageNo = 0;
        this.paging = true;
    }
    public PagingInfo(PagingInfo pagingInfo, int totalEleCount){
        this.pageSize = pagingInfo.getPageSize();
        this.blockSize = pagingInfo.getBlockSize();
        this.pageNo = pagingInfo.getPageNo();
        this.totalElementCount = totalEleCount;
    }
    
    public PagingInfo(int pageSize, int blockSize, int pageNo, int totalEleCount){
        this.pageSize = pageSize;
        this.blockSize = blockSize;
        this.pageNo = pageNo;
        this.totalElementCount = totalEleCount;
    }
    
    public boolean isPaging() {
        return paging;
    }
    public void setPaging(boolean paging) {
        this.paging = paging;
    }
    public int getTotalElementCount() {
        return totalElementCount;
    }
    public void setTotalElementCount(int totalElementCount) {
        this.totalElementCount = totalElementCount;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    // 전체 페이지 수
    public int getTotalPages() {
        return (int) Math.ceil((double)totalElementCount / (double)pageSize);
    }
    
    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    // 페이지 리스트 블럭의 시작 페이지 번호
    public int getBlockBeginPageNo() {
        int page = 0;
        if ( getTotalPages() > pageNo )
            page = (int) (Math.floor((double)pageNo / (double) getBlockSize()) * getBlockSize());

        return page;
    }

    public int getBlockEndPageNo() {
        return ((getBlockBeginPageNo() + getBlockSize()) < getTotalPages()) ? (getBlockBeginPageNo() + getBlockSize() - 1) : getTotalPages() - 1;
    }

    // 이전 블럭의 마지막 페이지 번호
    public int getPrevBlockPageNo() {
        return getBlockBeginPageNo() - getBlockSize();
    }

    public int getNextBlockPageNo() {
        return getBlockEndPageNo() + 1;
    }

    public boolean isNextPageBlock() {
        return (getBlockBeginPageNo() - 1) + getBlockSize() < getTotalPages() - 1;
    }

    public boolean isPrevPageBlock() {
        return getBlockBeginPageNo() > 1;
    }
    
    public int getOffset() {
        return getPageSize() * getPageNo();
    }

    public int getLimit() {
        return getOffset() + getPageSize();
    }
}
