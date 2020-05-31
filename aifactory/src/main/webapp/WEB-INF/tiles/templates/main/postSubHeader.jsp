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
    <c:when test="${recruitPost != null && recruitPost.postId != null }">
        <c:set var="title" value="${recruitPost.postNm}" />
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Create Post" />
        <c:set var="sumry" value="Enter the post information to be created." />
    </c:otherwise>
</c:choose>

<div class="page-header page-header-light competition-header__top mt-4 mb-4">
    <div class="competition-header__top-image"></div>
    <div class="pageheader__top--safe">
        <div class="page-header-content header-elements-inline">
            <div class="page-title competition-header__meta">
                <h1 class="competition-header__title">${title}</h1>
            </div>
        </div>
    </div>
</div>