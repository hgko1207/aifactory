<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Viewer-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script type="text/javascript">
var qEditor, aEditor;
$( document ).ready(function() {
    /**************************************************************************************
     * Editor 설정
     **************************************************************************************/
    var qEditorOptions = {
        height: '100px',
        initialValue:unescape('${qna.qnaCn}')
    };
    qEditor = $.INS.editor.init('contentsViewer', qEditorOptions);
    
    var aEditorOptions = {
        height: '100px',
        initialValue:unescape('${qna.qnaAnswer}')
    };
    aEditor = $.INS.editor.init('answerViewer', aEditorOptions);
    
    $('#btnQnaUpdate').click(function(){
        updateForm();
    });
    
    $('#btnQnaDelete').click(function(){
        deleteAction();
    });
    
    $('#btnQnaAnswer').click(function(){
       answerForm();
    });
    
});

function updateForm(){
    var url = contextName()+'/qna/update.do?qnaSn=${qna.qnaSn}';
    location.href = url;
}

function deleteAction(){
    $.ajax_form({
        url:'<c:url value="/qna/delete.do" />',
        formId:'qna',
        success: function (data) {
            alert('삭제 되었습니다.');
            $.INS.popup.close('qnaDetailPopup');
        }
    });
}

function answerForm(){
    var url = contextName()+'/qna/updateAnswer.do?qnaSn=${qna.qnaSn}';
    location.href = url;
}
</script>
<div class="card">
    <form id="qna" name="qna" method="POST" class="form-validate">
        <input name="task.taskId" type="hidden" value="${qna.task.taskId}" />
        <input name="qnaSn" type="hidden" value="${qna.qnaSn}" />
        <div class="tab-pane fade active show" id="basic-tab">
            <div id="basic-content" class="markdown-converter__text--rendered">
                <div class="card-body row ml-3">
                    <div class="d-inline-flex border-bottom" style="width: 98%">
                        <div class="col-2">
                            <label>Title</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback input-group">
                            ${qna.qnaSj}
                        </div>
                    </div>
                    <div class="d-inline-flex border-bottom" style="width: 98%">
                        <div class="col-2 pt-2">
                            <label>Question</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback">
                            <div id="contentsViewer"></div>
                        </div>
                    </div>
                    <div class="d-inline-flex border-bottom" style="width: 98%">
                        <div class="col-2 pt-2">
                            <label>Answer</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback">
                            <div id="answerViewer"></div>
                        </div>
                    </div>
                    <div class="col-lg-12 text-right mt-2" style="width: 98%">
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication property="principal.username" var="loginUserId"/>
                        </sec:authorize>
                        <c:choose>
                            <c:when test="${loginUserId == qna.register.userId}">
                                <button type="button" id="btnQnaUpdate" class="btn btn-primary">Edit</button>
                                <button type="button" id="btnQnaDelete" class="btn btn-secondary">Delete</button>
                                <sec:authorize access="hasRole('ROLE_ADMN')">
                                    <button type="button" id="btnQnaAnswer" class="btn btn-secondary">Answer</button>
                                </sec:authorize>
                            </c:when>
                            <c:otherwise>
                                <sec:authorize access="hasRole('ROLE_ADMN')">
                                    <button type="button" id="btnQnaUpdate" class="btn btn-primary">Edit</button>
                                    <button type="button" id="btnQnaDelete" class="btn btn-secondary">Delete</button>
                                    <button type="button" id="btnQnaAnswer" class="btn btn-secondary">Answer</button>
                                </sec:authorize>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>