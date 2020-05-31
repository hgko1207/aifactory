<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<script>
var g_mode = 'all';
var g_othbcYn='${taskCriterion.othbcYn}';
var g_keyword='${taskCriterion.keyword}';
var g_pageNo = ${taskCriterion.pagingInfo.pageNo};
var g_totalPageCnt = 1;
$( document ).ready(function() {
    $('#othbcYn').val('${taskCriterion.othbcYn}');
    $('#keyword').val('${taskCriterion.keyword}');
    g_mode = '${taskCriterion.mode}';
    if(g_mode == 'host'){
        $('#hostedTap').addClass("active");
    }else if(g_mode == 'join'){
        $('#jointedTap').addClass("active");
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
    g_othbcYn=$('select[name="othbcYn"]').val();
    g_keyword=$('input[name="keyword"]').val();
    g_pageNo=0;
    g_totalPageCnt = 1;
    
    $('#taskCnt').text('0개의 Task');
    var row = '<tr><td colspan="2">No Data</td></tr>';
    $('#taskListArea *').remove();
    $('#taskListArea').append(row);
}

function getData(){
    if( g_pageNo == 0 || g_pageNo < g_totalPageCnt){
        $.ajax_url({
            url:contextName()+'/task/searchAjax.do?'+makeSearchParam(), 
            success: function (data) {
                g_totalPageCnt = data.result.pagingInfo.totalPages;
                $('#taskCnt').text(data.result.pagingInfo.totalElementCount + '개의 태스크'); 
                if(g_pageNo == 0 && g_totalPageCnt > 0){
                    $('#taskListArea *').remove();
                }
                
                g_pageNo++;
                
                var row, json, imgPath;
                $.each(data.result.content, function(index, item){
                    imgPath = contextName() + "/resources/images/no-image-available-icon-11.jpg";
                    if(!isEmpty(item.cmmnFile) && item.cmmnFile.files){
                        imgPath = contextName() + '/download/byNameStream/'+item.cmmnFile.files[0].fileStreCours+'/'+item.cmmnFile.files[0].streFileNm + '/'; 
                    }
                    json = {
                            'taskId':item.taskId,
                            'imgPath':imgPath,
                            'taskNm':item.taskNm,
                            'taskSumry':item.taskSumry,
                            'beginDttm':item.beginDttmStr,
                            'endDttm':item.endDttmStr,
                            'modelNm':isEmpty(item.map.modelNm)?'-':item.map.modelNm,
                            'scre':item.map.scre,
                            'actvtyLapSn':isEmpty(item.map.actvtyLapSn)?'-':item.map.actvtyLapSn,
                            'totalLapCnt':item.map.totalLapCnt,
                            'nextTargetScre':isEmpty(item.map.nextTargetScre)?'-':item.map.nextTargetScre,
                            'nextDueDate':isEmpty(item.map.nextDueDate)?'-':item.map.nextDueDate,
                            'pymntMoney':item.map.pymntMoney
                    };
                    row = makeRow(json);
                    $('#taskListArea').append(row);
                });
            }
        });
    }
}

function makeSearchParam(){
    param = 'mode='+g_mode+'&othbcYn='+g_othbcYn+'&keyword='+g_keyword+'&pagingInfo.pageNo='+g_pageNo;
    return param;
}

function makeRow(item){
    var row = '<tr onclick="detailAction(\''+item.taskId+'\');">"';
    row += '<td class="text-right">';
    row += '<img class="table-image" style="width:80px;height:80px" src="'+item.imgPath+'"/>';
    row += '</td>';
    row += '<td class="py-3">';
    row += '<div class="font-weight-bold font-size-16">'+item.taskNm+'</div>';
    row += '<div>'+item.taskSumry+'</div>';
    row += '<div>date('+item.beginDttm+'~'+item.endDttm+')</div>';
    row += '<div>model name : '+item.modelNm+', last score : '+item.scre;
    row += ', lab status: '+item.actvtyLapSn+' / '+item.totalLapCnt;
    row += ', next step target Score : '+item.nextTargetScre+', next step Due Date : '+item.nextDueDate;
    row += ', points : '+item.pymntMoney+')</div>';
    row += '</td></tr>';
    return row;
}

function detailAction(taskId){
    location.href="${contextName}/task/detail.do?taskId="+taskId;
}
</script>

<div class="page-header page-header-dark">
    <div class="page-header-content header-elements-inline" style="background-color:#F9CA24;">
        <div class="container">
            <div class="page-title">
                 <h1 class="font-weight-semibold custom-font">태스크</h1>
            </div>
        </div>
    </div>
</div>

<div class="content container">
    <div class="card">
        <div class="card-body">
            <ul class="nav nav-tabs nav-tabs-highlight custom-font">
                <li class="nav-item"><a id="allTap" href="${contextName}/task/search.do?mode=all" class="nav-link" >모두 보기</a></li>
                <li class="nav-item"><a id="hostedTap" href="${contextName}/task/search.do?mode=host" class="nav-link">주최한 대회</a></li>
                <li class="nav-item"><a  id="jointedTap" href="${contextName}/task/search.do?mode=join" class="nav-link">참가한 대회</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade active show" id="highlighted-tab1">
                    <div class="text-right" style="margin-bottom:5px;">
                        <div class="d-inline-flex align-items-center justify-content-center">
                            <select id="othbcYn" name="othbcYn" class="form-control">
                                <option value="">All</option>
                                <option value="Y">Public</option>
                                <option value="N">Private</option>
                            </select>
                            <input type="text" name="keyword" id="keyword" class="form-control ml-1" placeholder="검색"/>
                            <button id="search_button" type="button" class="btn btn-info ml-2" onclick="search();">
                                <i class="icon-search4 mr-2"></i>검 색
                            </button>
                            <sec:authorize access="hasAnyRole('ROLE_BIZC','ROLE_ADMN')">
                                <a href="${pageContext.request.contextPath}/task/insert.do" class="btn btn-info ml-3">
                                    <i class="icon-pencil7 mr-2"></i>Create Task
                                </a>
                            </sec:authorize>
                         </div>
                    </div>
                    <div class="p-3 font-size-16" style="background-color:#273246;color:white;">
                        <span class="pl-2" id="taskCnt"></span>
                    </div>
                    <table class="table table-hover">
                        <colgroup>
                            <col width="14%">
                            <col>
                            <col width="18%">
                        </colgroup>
                        <tbody id="taskListArea">
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