<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<link href="${contextName}/resources/css/competition.css" rel="stylesheet" type="text/css">

<script>
function taskPartcptPopup(){
     var options = {
         dialogClass: 'custom-dialog-style',
         modal: true,
         open: function () {
             // 모달 오버레이 설정
            $(".ui-widget-overlay").css({
                opacity: 0.5,
                filter: "Alpha(Opacity=50)",
                backgroundColor: "black"
            });
        },
        
        // 닫기 콜백
        close: function (e, ui) {
            $(this).empty();
            $(this).dialog('destroy');
        },
               
        height: "auto",
        width: 400,
        title: '대회 참가 동의 여부',
        buttons: {
            '동의 함': function() {
                var formData = new FormData();
                formData.append('task.taskId', '${task.taskId}');
                formData.append('agreYn', 'Y');
                $.ajax_data({
                    url:'<c:url value="/partcptAgre/insert.do" />',
                    data:formData,
                    success: function (data) {
                        $.INS.alert('참가 동의가 완료 되었습니다.');
                        location.reload();
                    }
                });
                $( this ).dialog( "close" );
            },
            '동의 안 함': function() {
                $( this ).dialog( "close" );
            }
        }
    };
     var msg = "아래의 '동의 함' 버튼을 클릭하면 대회 규칙에 동의하고 대회에 참가한다는 의미입니다. 참가하시겠습니까?";
    $('<div id="dialog" title="Confirm"><p>'+msg+'</p></div>').dialog(options);
}
</script>

<c:choose>
    <c:when test="${task != null && task.taskId != null }">
        <c:set var="title" value="${task.taskNm}" />
        <c:set var="sumry" value="${task.taskSumry}" />
    </c:when>
    <c:when test="${post != null && post.postId != null }">
        <c:set var="title" value="${post.postNm}" />
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Create Task" />
        <c:set var="sumry" value="Enter the task information to be created." />
    </c:otherwise>
</c:choose>

<div class="page-header page-header-light competition-header__top mt-4 mb-4">
    <div class="competition-header__top-image"></div>
    <div class="pageheader__top--safe">
        <div class="page-header-content header-elements-inline">
            <div class="page-title competition-header__meta">
                <h1 class="competition-header__title">${title}</h1>
                <h2 class="competition-header__subtitle">${sumry}</h2>
            </div>
        </div>
    </div>
    <c:if test="${task != null && task.taskId != null }">
        <div class="breadcrumb-line breadcrumb-line-light header-elements-md-inline">
            <div class="navbar navbar-expand-xl navbar-light navbar-component">
                <ul class="nav navbar-nav">
                    <li id="overview" class="nav-item"><a href="${contextName}/task/detail.do?taskId=${task.taskId}" class="navbar-nav-link mr-2 font-size-14"> <i class="icon-cube3 mr-2"></i>Overview
                    </a></li>
                    <sec:authorize access="isAuthenticated()">
                        <partcpt:isAgree taskId="${task.taskId}">
                            <li id="data" class="nav-item"><a href="${contextName}/taskData/detail.do?task.taskId=${task.taskId}" class="navbar-nav-link mr-2 font-size-14"> <i class="icon-database4 mr-2"></i>Data
                            </a></li>
                            <li id="leaderboard" class="nav-item"><a href="${contextName}/leaderboard/search.do?taskId=${task.taskId}" class="navbar-nav-link font-size-14"> <i class="icon-trophy2 mr-2"></i>Leaderboard
                            </a></li>
                            <li id="qna" class="nav-item"><a href="${contextName}/qna/search.do?taskId=${task.taskId}" class="navbar-nav-link font-size-14"> <i class="icon-pencil3 mr-2"></i>Q&A
                            </a></li>
                            <li id="submission" class="nav-item"><a href="${contextName}/submission/search.do?taskId=${task.taskId}" class="navbar-nav-link font-size-14"> <i class="icon-upload mr-2"></i>Submission
                            </a></li>
                        </partcpt:isAgree>
                        <partcpt:isNotAgree taskId="${task.taskId}">
                            <li id="taskPartcpt" class="nav-item"><a href="javascript:void(0);" onclick="taskPartcptPopup();" class="navbar-nav-link font-size-14"> <i class="icon-user-check mr-2"></i>Join Task
                            </a></li>
                        </partcpt:isNotAgree>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </c:if>
</div>