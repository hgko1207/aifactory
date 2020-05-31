<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Editor-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script type="text/javascript">
var editor;
$( document ).ready(function() {
    /**************************************************************************************
     * Editor 설정
     **************************************************************************************/
    var editorOptions = {
        previewStyle: 'tab',
        height: '250px',
        events:{
            'change':function(){
                $('#qnaCn').val(editor.getHtml());
            }
        },  		
        initialEditType: 'wysiwyg'
    };
    editor = $.INS.editor.init('contentsViewer', editorOptions);
    
    /**************************************************************************************
     * 
     **************************************************************************************/
    // 등록폼 유효성 체크
    $('#qna').validate({
        focusInvalid:false,
        ignore: '#contentsViewer *',
        onchange: function(element) {$(element).valid()},
        success: function(label) {
            label.remove();
        },
        rules: {
            qnaSj: {
                required: true,
                maxlength: 100
            },
            qnaCn: {
                required: true
            }
        },
        submitHandler: function(form){
            $.ajax_form({
                url:'<c:url value="/qna/insert.do" />',
                formId:'qna',
                success: function (data) {
                    alert('저장 되었습니다.');
                    $.INS.popup.close('qnaInsertPopup');
                }
            });
        }
    });
    
});
</script>
<div class="card">
    <form id="qna" name="qna" method="POST" class="form-validate">
        <input name="task.taskId" type="hidden" value="${qna.task.taskId}" />
        <div class="tab-pane fade active show" id="basic-tab">
            <div id="basic-content" class="markdown-converter__text--rendered">
                <div class="card-body row ml-3">
                    <div class="d-inline-flex" style="width: 98%">
                        <div class="col-2">
                            <label>*Title</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback input-group">
                            <input id="qnaSj" name="qnaSj" type="text" class="form-control" required="required" />
                        </div>
                    </div>
                    <div class="d-inline-flex" style="width: 98%">
                        <div class="col-2">
                            <label>*Contents</label>
                        </div>
                        <div class="form-group col-10 form-group-feedback">
                            <input type="hidden" id="qnaCn" name="qnaCn" class="form-control" required="required" />
                            <div id="contentsViewer" style="min-height:300px;"></div>
                            <label id="qnaCn-error" class="validation-invalid-label" style="display:none;" for="qnaCn">This field is required.</label>
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