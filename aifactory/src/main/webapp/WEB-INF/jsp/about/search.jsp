<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<script>
var g_mode = 'all';
var g_keyword='${postCriterion.keyword}';
var g_pageNo = ${postCriterion.pagingInfo.pageNo};
var g_totalPageCnt = 1;
$( document ).ready(function() {
    $('#keyword').val('${postCriterion.keyword}');
    g_mode = '${postCriterion.mode}';
    if(g_mode == 'news'){
        $('#newsTap').addClass("active");
    }else if(g_mode == 'recruit'){
        $('#recruitTap').addClass("active");
    }else if(g_mode == 'member'){
        $('#memberTap').addClass("active");
    }else{
    	$('#allTap').addClass("active");
    }
    
    // 스크롤 하단에 도달시 다음 리스트 불러오기
    $(window).scroll(function() {
        var scrolltop = $(document).scrollTop();
        var height = $(document).height();
        var height_win = $(window).height();
        
        if (Math.round( $(window).scrollTop()) == $(document).height() - $(window).height()) {
            getData();
        }
    });
    
    search();
});

function search(){
    initSearch();
    getData();
}

function initSearch(){
    g_keyword=$('input[name="keyword"]').val();
    g_pageNo=0;
    g_totalPageCnt = 1;
    
    $('#postCnt').text('0개의 Post');
    var row = '<tr><td colspan="2">No Data</td></tr>';
    $('#postListArea *').remove();
    $('#postListArea').append(row);
}

function getData(){
    if( g_pageNo == 0 || g_pageNo < g_totalPageCnt){
        $.ajax_url({
            url:contextName()+'/about/searchAjax.do?'+makeSearchParam(), 
            success: function (data) {
                g_totalPageCnt = data.result.pagingInfo.totalPages;
                $('#postCnt').text(data.result.pagingInfo.totalElementCount + '개의 게시물'); 
                if(g_pageNo == 0 && g_totalPageCnt > 0){
                    $('#postListArea *').remove();
                }
                
                g_pageNo++;
                
                var row, json, imgPath;
                $.each(data.result.content, function(index, item){
                    imgPath = contextName() + "/resources/images/no-image-available-icon-11.jpg";
                    if(!isEmpty(item.cmmnFile) && item.cmmnFile.files){
                        imgPath = contextName() + '/download/byNameStream/'+item.cmmnFile.files[0].fileStreCours+'/'+item.cmmnFile.files[0].streFileNm + '/'; 
                    }
                    json = {
                            'postId':item.postId,
                            'imgPath':imgPath,
                            'postNm':item.postNm,
                            
                    };
                    row = makeRow(json);
                    $('#postListArea').append(row);
                });
            }
        });
    }
}

function makeSearchParam(){
    param = 'mode='+g_mode+'&keyword='+g_keyword+'&pagingInfo.pageNo='+g_pageNo;
    return param;
}

function makeRow(item){
    var row = '<tr onclick="detailAction(\''+item.postId+'\');">"';
    row += '<td class="text-right">';
    row += '<img class="table-image" style="width:80px;height:80px" src="'+item.imgPath+'"/>';
    row += '</td>';
    row += '<td class="py-3">';
    row += '<div class="font-weight-bold font-size-16">'+item.postNm+'</div>';
    row += '</td></tr>';
    return row;
}

function detailAction(postId){
    location.href="${contextName}/about/detail.do?postId="+postId;
}
</script>

<div class="page-header page-header-dark">
    <div class="page-header-content header-elements-inline" style="background-color:#F9CA24;">
        <div class="container">
            <div class="page-title">
                 <h1 class="font-weight-semibold custom-font">더보기</h1>
            </div>
        </div>
    </div>
</div>

<div class="content container">
    <div class="card">
        <div class="card-body">
            <ul class="nav nav-tabs nav-tabs-highlight custom-font">
                <li class="nav-item"><a id="allTap" href="${contextName}/about/search.do?mode=all" class="nav-link">모두 보기</a></li>
                <li class="nav-item"><a id="newsTap" href="${contextName}/about/search.do?mode=news" class="nav-link">새소식</a></li>
                <li class="nav-item"><a id="recruitTap" href="${contextName}/about/search.do?mode=recruit" class="nav-link">채용공고</a></li>
                <li class="nav-item"><a id="memberTap" href="${contextName}/about/search.do?mode=member" class="nav-link">구성원</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade active show" id="highlighted-tab1">
                    <div class="text-right" style="margin-bottom:5px;">
                        <div class="d-inline-flex align-items-center justify-content-center">
                            <select id="othbcYn" name="othbcYn" class="form-control">
                                <option value="">All</option>
                                <option value="Y">제목</option>
                                <option value="N">내용</option>
                            </select>
                            <input type="text" name="keyword" id="keyword" class="form-control ml-1" placeholder="검색"/>
                            <button id="search_button" type="button" class="btn btn-info ml-2" onclick="search();">
                                <i class="icon-search4 mr-2"></i>검 색
                            </button>
                            <sec:authorize access="hasAnyRole('ROLE_BIZC','ROLE_ADMN')">
                                <a href="${pageContext.request.contextPath}/about/insert.do?mode=${param.mode}" class="btn btn-info ml-3">
                                    <i class="icon-pencil7 mr-2"></i>Create Post
                                </a>
                            </sec:authorize>
                         </div>
                    </div>
                    <div class="p-3 font-size-16" style="background-color:#273246;color:white;">
                        <span class="pl-2" id="postCnt"></span>
                    </div>
                    <table class="table table-hover">
                        <colgroup>
                            <col width="14%">
                            <col>
                            <col width="18%">
                        </colgroup>
                        <tbody id="postListArea">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<%-- <c:set var="contextName">${pageContext.request.contextPath}</c:set>

<div class="container-fruid text-light align-middle menu_banner">
    <div class="container menu_banner_inside">
        <div class="row">
            <div class="col menu_banner_inside_text"><h2>AI Factory</h1></div>
        </div>
    </div>
</div> --%>