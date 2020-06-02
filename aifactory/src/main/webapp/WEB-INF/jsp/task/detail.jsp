<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Viewer-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script src="${contextName}/resources/js/lap.js"></script>

<script>
$(document).ready(function() {
    new tui.Editor({
        el: document.querySelector('#taskDc'),
        height: '450px',
        initialValue:unescape('${task.taskDc}')
    });

    addNoLapDataRow();
    addNoRankDataRow();
    
    <c:forEach var="lap" items="${task.laps}" varStatus="lapStatus">
        <fmt:formatDate value="${lap.endDttm}" var="endDttm" pattern="yyyy-MM-dd" />
        lapObjs.addLap("${lap.lapSn}", "${lap.lapEndCndCode.cmmnCode}", "${lap.goalScre}", "${endDttm}", "${lap.chkPnttmCycle}");
        <c:forEach var="rank" items="${lap.lapRank}" varStatus="rankStatus">
            lapObjs.addRank("${lapStatus.index}", "${rank.money}");
        </c:forEach>
        
        addLapRow("${lap.goalScre}", "${endDttm}", "${lap.chkPnttmCycle}", "${lap.lapRank.size()}", "${lap.totalPoint}");
    </c:forEach>
    
    selectLapRow($(".clickable-row")[0]);
    
    // 수정 Form
    $('#btnTaskUpdateForm').click(function(){
        $('#task').submit();
    });
});

/**************************************************************************************
 * Lap 관련 함수
 **************************************************************************************/
// Add Lap Info
function addLapRow(goalScre, lapEndDttm, chkPnttmCycle, totalRank, totalPoint){
    goalScre = (goalScre == 0)?'-':goalScre;
        
    var row = '<tr class="clickable-row" onclick="selectLapRow(this);" index="'+lapObjs.size()+'">';
    row += '<td>'+lapObjs.size()+'</td>';
    row += '<td>'+goalScre+'</td>';
    row += '<td>'+lapEndDttm+'</td>';
    row += '<td>'+chkPnttmCycle+'</td>';
    row += '<td>'+totalRank+'</td>';
    row += '<td>'+totalPoint+'</td>';
    row += '</tr>';
    
    if( lapObjs.size() == 1 )
        $('#lapTable tbody tr').remove();
    
    $('#lapTable tbody').append(row);
}

// Select Lap Row
function selectLapRow(ele){
    if ($(ele).hasClass("row-selected")){
        $(ele).removeClass('row-selected');
    } else {
        $(ele).addClass('row-selected').siblings().removeClass('row-selected');
    }
    
    // Rank 정보 새로 고침
    refreshRankArea();
}

//Add No Lap Data Row
function addNoLapDataRow(){
    var row = '<tr id="noLapDataRow">';
    row += '<td colspan="6" class="text-center">No Lap Data</td>';
    row += '</tr>';
    
    $('#lapTable tbody').append(row);
}
/**************************************************************************************
 * Lap Rank 관련 함수
 **************************************************************************************/
// Show Rank Area
function refreshRankArea(){
    var lapIdx = $(".row-selected").attr("index");
    if(lapIdx != null){
        var lap = lapObjs.getLap(lapIdx-1);
        
        if(lap.ranks == null || lap.ranks.length == 0){
            addNoRankDataRow();
        }else{
            for(var i=0; i < lap.ranks.length; i++ ){
                addRankRow(i+1, lap.ranks[i].point);
            }
        }
    }else{
        addNoRankDataRow();
    }
}
//Add Rank Row
function addRankRow(rank, point){
    var row = '<tr index="'+rank+'">';
    row += '<td>'+rank+'</td>';
    row += '<td>'+point+'</td>';
    row += '</tr>';
    
    if( rank == 1 )
        $('#rankArea tbody tr').remove();
    
    $('#rankArea tbody').append(row);
}
//Add No Rank Data Row
function addNoRankDataRow(){
    $('#rankArea tbody tr').remove();
    
    var row = '<tr id="noLapDataRow">';
    row += '<td colspan="2" class="text-center">No Rank Data</td>';
    row += '</tr>';
    
    $('#rankArea tbody').append(row);
}


</script>
<div class="card overview">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-cube3 mr-2"></i>Overview
        </h5>
    </div>
    <div class="card-body">
        <div class="d-md-flex">
            <div class="container">
                <!-- Left Menu
                <ul class="nav nav-tabs nav-tabs-vertical flex-column mr-md-3 wmin-md-250 mb-md-0 border-bottom-0">
                    <li class="nav-item"><a href="#basic-tab" class="nav-link active show" data-toggle="tab">Basic</a></li>
                    <li class="nav-item"><a href="#description-tab" class="nav-link" data-toggle="tab">Description</a></li>
                </ul>
                -->

                <div class="tab-content">
                    <form:form commandName="task" method="POST" cssClass="form-validate" action="${contextName}/task/updateForm">
                        <form:input path="taskId" type="hidden"/>
                        <div class="tab-pane fade active show" id="basic-tab">
                            <div id="basic-content" class="markdown-converter__text--rendered">
                                <div class="card-body row ml-3">
                                    <div class="d-inline-flex" style="width: 100%">
                                      
                                        <div class="form-group form-group-feedback input-group">
                                            <h1>${task.taskNm}</h1>
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                     
                                        <div class="form-group form-group-feedback input-group">${task.taskSumry}</div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                     
                                        <div id="taskDc" class="form-group form-group-feedback input-group"></div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                      
                                        <div class="form-group form-group-feedback input-group">
                                            <c:if test="${!empty task.cmmnFile && !empty task.cmmnFile.files}">
                                                <img src="${contextName}/download/byNameStream/${task.cmmnFile.files[0].fileStreCours}/${task.cmmnFile.files[0].streFileNm}/" width="80"/>
                                            </c:if>
                                        </div>
                                    </div>
                                 </div>
                                 <div class="row ml-3" >
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>Period</label>
                                        </div>
                                        <div class="form-group col-6 form-group-feedback">
                                            <div class="input-group">
                                                <fmt:formatDate value="${task.beginDttm}" var="beginDttm" pattern="yyyy-MM-dd" />
                                                <fmt:formatDate value="${task.endDttm}" var="endDttm" pattern="yyyy-MM-dd" />
                                                ${beginDttm} ~ ${endDttm} 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>Privacy</label>
                                        </div>
                                        <div class="form-group col-6">
                                            <div class="form-check form-check-inline">
                                                <c:choose>
                                                    <c:when test="${task.othbcYn=='Y'}">Public</c:when>
                                                    <c:otherwise>Private</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row w-100">
                                        <div class="card w-100">
                                            <div class="card-header bg-white">
                                                <h6 class="card-title">
                                                    <i class="icon-chess-king mr-2"></i> Lap Status
                                                </h6>
                                            </div>
                                            <div class="card-body">
                                                <div class="row p-2">
                                                    <div class="table-responsive col-lg-12">
                                                        <table class="table">
                                                            <col width="6%">
                                                            <col width="10%">
                                                            <col width="13%">
                                                            <col width="16%">
                                                            <col width="8%">
                                                            <col width="16%">
                                                            <col width="*">
                                                            <thead>
                                                                <tr>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">#</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Score</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Start date</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">End date</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Rank</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Point</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">User</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="lapStatusArea">
                                                                <c:choose>
                                                                    <c:when test="${fn:length(lapStatus) > 0}">
                                                                        <c:forEach var="item" items="${lapStatus}" varStatus="idx">
                                                                            <fmt:formatDate value="${item.lapAdhrnc.lap.beginDttm}" var="beginDttm" pattern="yyyy-MM-dd" />
                                                                            <fmt:formatDate value="${item.lapAdhrnc.lap.endDttm}" var="endDttm" pattern="yyyy-MM-dd" />
                                                                            <tr>
                                                                                <td>${item.lapAdhrnc.lap.lapSn }</td>
                                                                                <td>${item.lapAdhrnc.scre}</td>
                                                                                <td>${beginDttm}</td>
                                                                                <td>${endDttm}</td>
                                                                                <td>${item.lapRank.rank}</td>
                                                                                <td>${item.money}</td>
                                                                                <td>${item.lapAdhrnc.user.userNm}</td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <tr class="text-center">
                                                                            <td colspan="7">No Data</td>
                                                                        </tr>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row w-100">
                                        <div class="card w-100">
                                            <div class="card-header bg-white">
                                                <h6 class="card-title">
                                                    <i class="icon-flag7 mr-2" style="margin-top:3px;"></i> Lap
                                                </h6>
                                            </div>
                                            <div class="card-body">
                                                <div class="row p-2">
                                                    <div class="table-responsive col-lg-9">
                                                        <table class="table" id="lapTable">
                                                            <col width="10%">
                                                            <col width="13%">
                                                            <col width="16%">
                                                            <col width="20%">
                                                            <col width="16%">
                                                            <col width="*">
                                                            <thead>
                                                                <tr>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">#</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Score</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">End date</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Check<br/> Point Time(Hour)</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Total Rank</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Total Point</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="lapInfoArea">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    
                                                    <div class="table-responsive col-lg-3" id="rankArea">
                                                        <table class="table">
                                                            <col width="34%">
                                                            <col width="*%">
                                                            <thead>
                                                                <tr>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom" style="padding-top:23px;padding-bottom:23px;">Rank</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Point</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody> 
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="errArea"></div>
                                    <sec:authorize access="isAuthenticated()">
                                        <sec:authentication property="principal.username" var="loginUserId"/>
                                    </sec:authorize>
                                    <c:choose>
                                        <c:when test="${loginUserId == task.register.userId}">
                                            <div class="col-lg-11 text-right">
                                                <button type="button" id="btnTaskUpdateForm" class="btn btn-primary">
                                                    Edit<i class="icon-paperplane ml-2"></i>
                                                </button>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <sec:authorize access="hasRole('ROLE_ADMN')">
                                                <div class="col-lg-11 text-right">
                                                    <button type="button" id="btnTaskUpdateForm" class="btn btn-primary">
                                                        Edit<i class="icon-paperplane ml-2"></i>
                                                    </button>
                                                </div>
                                            </sec:authorize>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="description-tab">
                            <div id="description-content" class="markdown-converter__text--rendered"></div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>