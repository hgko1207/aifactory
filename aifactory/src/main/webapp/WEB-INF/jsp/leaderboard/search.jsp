<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>
<link href="${pageContext.request.contextPath}/resources/css/leaderboard.css" rel="stylesheet" type="text/css">

<script>
var g_pageNo = 0;
var g_totalPageCnt = 1;
var g_public = ${criterion.publc};
var g_rank = 0;
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
    
    search(g_public);
});

function search(publc){
    g_public = publc;
    
    initSearch();
    getData();
}

function initSearch(){
    $('#publicTab').removeClass("active");
    $('#privateTab').removeClass("active");
        
    if(g_public){
        $('#publicTab').addClass("active");
    }else{
        $('#privateTab').addClass("active");
    }
    
    g_pageNo = 0;
    g_rank = 0;
    g_totalPageCnt = 1;
    
    var row = '<tr><td colspan="6">No Data</td></tr>';
    $('#lbListArea *').remove();
    $('#lbListArea').append(row);
}

function getData(){
    if (g_pageNo == 0 || g_pageNo < g_totalPageCnt){
        $.ajax_url({
            url:contextName()+'/leaderboard/searchAjax.do?'+makeSearchParam(), 
            success: function (data) {
                g_totalPageCnt = data.result.pagingInfo.totalPages;
                if(g_pageNo == 0 && g_totalPageCnt > 0){
                    $('#lbListArea *').remove();
                }
                
                g_pageNo++;
                
                var row, json, teamNm;
                $.each(data.result.content, function(index, item){
                    if( !isEmpty(item.team) ){
                        teamNm = item.team.teamNm;
                    }else{
                        teamNm = '-';
                    }
                    
                    json = {
                            'userNm':item.user.userNm,
                            'teamNm':teamNm,
                            'scre':item.scre,
                            'registDttm':item.registDttmStr,
                            'lapSn':item.lap.lapSn
                    };
                    row = makeRow(json);
                    $('#lbListArea').append(row);
                });
            }
        });
    }
}

function makeSearchParam(){
    param = 'taskId=${task.taskId}&publc='+g_public+'&pagingInfo.pageNo='+g_pageNo;
    return param;
}

function makeRow(item){
    g_rank++;
    var row = '<tr class="competition-table__row text-center">';
    row += '<td class="text-right">'+g_rank+'</td>';
    row += '<td class="font-weight-semibold text-left pl-5">'+item.userNm+'</td>';
    row += '<td class="font-weight-semibold text-left pl-5">'+item.teamNm+'</td>';
    row += '<td>'+item.scre+'</td>';
    row += '<td>'+item.registDttm+'</td>';
    row += '<td>'+item.lapSn+'/${totalLapCount}</td>';
    row += '</tr>';
    return row;
}
</script>
<div class="card">
    <div class="card-header bg-white pb-0 pt-sm-0 pr-sm-0 header-elements-inline">
        <div id="leaderBoardTabs" class="header-elements">
            <ul class="nav nav-tabs nav-tabs-bottom card-header-tabs mx-0">
                <li class="nav-item"><a id="publicTab" href="javascript:search(true);" class="nav-link"> <i class="icon-trophy3 mr-2"></i>Public Leaderboard
                </a></li>
                <li class="nav-item"><a id="privateTab" href="javascript:search(false);" class="nav-link"> <i class="icon-trophy2 mr-2"></i>Private Leaderboard
                </a></li>
            </ul>
        </div>
    </div>
    <div class="tab-content">
        <div class="tab-pane fade active show" id="public-tab">
            <div class="card-footer d-flex justify-content-between align-items-center">
                <div class="text-muted font-size-14">
                    <div>이 점수 현황은 테스트 데이터의 약 50% 로 계산됩니다.</div>
                </div>
            </div>
            <table class="table table-hover" id="leaderboardTable">
                <colgroup>
                    <col width="5%">
                    <col width="15%">
                    <col width="*">
                    <col width="8%">
                    <col width="20%">
                    <col width="10%">
                </colgroup>
                <thead>
                    <tr class="competition-table__header-row text-center">
                        <th class="text-right">No.</th>
                        <th class="text-left pl-5">UserName</th>
                        <th class="text-left pl-5">TeamName</th>
                        <th>Score</th>
                        <th>Last</th>
                        <th>lap</th>
                    </tr>
                </thead>
                <tbody id="lbListArea" class="text-center">
                </tbody>
            </table>
        </div>
    </div>
</div>