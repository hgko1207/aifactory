<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Editor-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script type="text/javascript">
var qEditor, aEditor;
$( document ).ready(function() {
    /**************************************************************************************
     * Editor 설정
     **************************************************************************************/
    var qEditorOptions = {
        previewStyle: 'tab',
        height: '250px',  		
        initialEditType: 'wysiwyg',
        initialValue:unescape('${qna.qnaCn}')
    };
    qEditor = $.INS.editor.init('questionViewer', qEditorOptions);
    
    var aEditorOptions = {
        previewStyle: 'tab',
        height: '250px',
        events:{
            'change':function(){
                $('#qnaAnswer').val(aEditor.getHtml());
            }
        },  		
        initialEditType: 'wysiwyg'
    };
    aEditor = $.INS.editor.init('answerViewer', aEditorOptions);
    aEditor.setHtml(unescape('${qna.qnaAnswer}'));
    
    /**************************************************************************************
     * 
     **************************************************************************************/
    // 등록폼 유효성 체크
    $('#qna').validate({
        focusInvalid:false,
        ignore: '#questionViewer *,#answerViewer *',
        onchange: function(element) {$(element).valid()},
        success: function(label) {
            label.remove();
        },
        rules: {
            qnaAnswer: {
                required: true
            }
        },
        submitHandler: function(form){
            $.ajax_form({
                url:'<c:url value="/qna/updateAnswer.do" />',
                formId:'qna',
                success: function (data) {
                    alert('저장 되었습니다.');
                    $.INS.popup.close('qnaDetailPopup');
                }
            });
        }
    });
    
});
</script>
<div class="card">
    <form id="qna" name="qna" method="POST" class="form-validate">
        <input name="qnaSn" type="hidden" value="${qna.qnaSn}" />
        <div class="tab-pane fade active show" id="basic-tab">
            <div id="basic-content" class="markdown-converter__text--rendered">
                <div class="card-body row ml-3">
                    <div class="d-inline-flex" style="width: 98%">
                        <div class="col-2">
                            <label>Title</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback input-group">
                            ${qna.qnaSj}
                        </div>
                    </div>
                    <div class="d-inline-flex" style="width: 98%">
                        <div class="col-2">
                            <label>Question</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback">
                            <div id="questionViewer"></div>
                        </div>
                    </div>
                    <div class="d-inline-flex" style="width: 98%">
                        <div class="col-2">
                            <label>* Answer</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback">
                            <input type="hidden" id="qnaAnswer" name="qnaAnswer" class="form-control" required="required" />
                            <div id="answerViewer"></div>
                            <label id="qnaAnswer-error" class="validation-invalid-label" style="display:none;" for="qnaCn">This field is required.</label>
                        </div>
                    </div>
                    <div class="col-lg-12 text-right" style="width: 98%">
                        <button type="submit" id="btnQnaInsert" class="btn btn-primary">
                            Save<i class="icon-paperplane ml-2"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>