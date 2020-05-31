<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>
<link href="${pageContext.request.contextPath}/resources/css/leaderboard.css" rel="stylesheet" type="text/css">

<script>
var g_pageNo = 0;
var g_totalPageCnt = 1;
var g_totalCnt;
$( document ).ready(function() {
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
    g_pageNo = 0;
    g_totalPageCnt = 1;
    g_totalCnt = 0;
    
    var row = '<tr><td colspan="4">No Data</td></tr>';
    $('#qaListArea *').remove();
    $('#qaListArea').append(row);
}

function getData(){
    if( g_pageNo == 0 || g_pageNo < g_totalPageCnt){
        $.ajax_url({
            url:contextName()+'/qna/searchAjax.do?'+makeSearchParam(), 
            success: function (data) {
                g_totalCnt = data.result.pagingInfo.totalElementCount - (data.result.pagingInfo.pageNo * data.result.pagingInfo.pageSize);
                g_totalPageCnt = data.result.pagingInfo.totalPages;
                if(g_pageNo == 0 && g_totalPageCnt > 0){
                    $('#qaListArea *').remove();
                }
                
                g_pageNo++;
                
                var row, json, teamNm;
                $.each(data.result.content, function(index, item){
                    json = {
                            'qnaSn':item.qnaSn,
                            'totalCnt':g_totalCnt,
                            'userNm':item.register.userNm,
                            'title':item.qnaSj,
                            'registDttm':item.registDttmStr
                    };
                    row = makeRow(json);
                    $('#qaListArea').append(row);
                    
                    g_totalCnt--;
                });
            }
        });
    }
}

function makeSearchParam(){
    param = 'taskId=${task.taskId}&pagingInfo.pageNo='+g_pageNo;
    return param;
}

function makeRow(item){                        
    var row = '<tr class="competition-table__row text-center" onclick="detailPopup(\''+item.qnaSn+'\');">';
    row += '<td class="text-right">'+item.totalCnt+'</td>';
    row += '<td class="font-weight-semibold text-left pl-5">'+item.title+'</td>';
    row += '<td class="font-weight-semibold text-left pl-5">'+item.userNm+'</td>';
    row += '<td>'+item.registDttm+'</td>';
    row += '</tr>';
    return row;
}

function insertPopup(){
    var title = 'Q & A';
    var url = contextName()+'/qna/insert.do?task.taskId=${task.taskId}';
    
    var options = {
        'title':title,
        'url':url,
        'width':'1000',
        'height':'600',
        'close':function(e, ui){
            location.reload();
        },
        'buttonDisplay':false
    };
    $.INS.popup('qnaInsertPopup', options);
}

function detailPopup(qnaSn){
    var title = 'Q & A';
    var url = contextName()+'/qna/detail.do?qnaSn='+qnaSn;
    
    var options = {
        'title':title,
        'url':url,
        'width':'1000',
        'height':'800',
        'close':function(e, ui){
            location.reload();
        },
        'buttonDisplay':false
    };
    $.INS.popup('qnaDetailPopup', options);
}

</script>
<div class="card">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-pencil"></i>&nbsp;&nbsp;&nbsp;Q & A
        </h5>
        <div class="text-right" style="margin-bottom:5px;">
            <div class="d-inline-flex align-items-center justify-content-center">
                <sec:authorize access="isAuthenticated()">
                    <a href="javascript:void(0);" onclick="insertPopup();" class="btn btn-info ml-3">
                        <i class="icon-pencil7 mr-2"></i>Write
                    </a>
                </sec:authorize>
             </div>
        </div>
    </div>
    <div class="tab-content">
        <div class="tab-pane fade active show" id="public-tab">
            <table class="table table-hover" id="leaderboardTable">
                <colgroup>
                    <col width="10%">
                    <col width="*">
                    <col width="15%">
                    <col width="15%">
                </colgroup>
                <thead>
                    <tr class="competition-table__header-row text-center">
                        <th class="text-right">No.</th>
                        <th class="text-left pl-5">Title</th>
                        <th class="text-left pl-5">Name</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody id="qaListArea" class="text-center">
                </tbody>
            </table>
        </div>
    </div>
</div>