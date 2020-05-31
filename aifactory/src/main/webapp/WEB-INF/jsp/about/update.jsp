<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Editor-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script src="${contextName}/resources/js/recruitPost.js"></script>

<script type="text/javascript">
var fileParam;
var editor;
$( document ).ready(function() {
    /**************************************************************************************
     * Task Dc 설정
     **************************************************************************************/
    var editorOptions = {
        previewStyle: 'vertical',
        height: '450px',
        events:{
            'change':function(){
                $('#postDc').val(editor.getHtml());
            }
        }
    };
    editor = $.INS.editor.init('taskDcViewer', editorOptions);
    editor.setHtml(unescape('${post.postDc}'));
    
    
   
    // 수정
    $('#btnUpdate').click(function(){
        if($("#post").valid()){
            if($('#previewArea').is(':visible')){
                $('#post').submit();
            }else{
                fileUpload();
            }
        }
    });
    
    <c:choose>
        <c:when test="${!empty post.cmmnFile && !empty post.cmmnFile.files}">
            $('#previewArea').show();
        </c:when>
        <c:otherwise>
            $('#imageFileArea').show();
        </c:otherwise>
    </c:choose>
});

/**************************************************************************************
 * Form Submit
 **************************************************************************************/
function submitForm(){
    var param = ""
    if( !isEmpty(param) ) param += "&";
    param += fileParam;
    
    $.ajax_form({
        url:'<c:url value="/about/update.do" />',
        formId:'post',
        otherParam:param,
        success: function (data) {
            alert('저장 되었습니다.');
            location.href = '${contextName}/about/detail.do?postId='+data.post.postId;
        }
    });
}

/**************************************************************************************
 * File Upload 관련 함수
 **************************************************************************************/
function fileUpload(){
    $("#imageFile").fileinput('upload');
}
function removeImage(){
    $('#previewArea').hide();
    $('#imageFileArea').show();
    $('#fileChange').val(true);
}



</script>

<div class="card overview">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-cube3 mr-2"></i>Post
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
                    <form:form commandName="post" method="POST" cssClass="form-validate">
                        <form:input path="postId" type="hidden"/>
                        <form:input path="cmmnFile.atchFileId" type="hidden"/>
                        <div class="tab-pane fade active show" id="basic-tab">
                            <div id="basic-content" class="markdown-converter__text--rendered">
                                <div class="card-body row ml-3">
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>*채용공고</label>
                                        </div>
                                        <div class="form-group col-9 form-group-feedback input-group">
                                            <form:input path="postNm" type="text" cssClass="form-control" required="required" />
                                        </div>
                                    </div>
                                    
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>*내용</label>
                                        </div>
                                        <div class="form-group col-9 form-group-feedback">
                                            <form:hidden path="postDc" cssClass="form-control" required="required" />
                                            <div id="taskDcViewer"></div>
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>*Image</label>
                                        </div>
                                        <div class="form-group col-9 form-group-feedback input-group">
                                            <div id="previewArea" class="container" style="display:none;">
                                                <div class="row">
                                                    <div class="col-2">
                                                        <img src="${contextName}/download/byNameStream/${post.cmmnFile.files[0].fileStreCours}/${post.cmmnFile.files[0].streFileNm}/" width="80"/>
                                                    </div>
                                                    <div class="col-2 ">
                                                        <button type="button" onclick="removeImage();" tabindex="500" title="Clear selected files" class="btn btn-default btn-secondary"><i class="icon-cross2 font-size-base mr-2"></i>  <span class="hidden-xs">Remove</span></button>
                                                    </div>
                                                    <div class="col-8"></div>
                                                </div>
                                            </div>
                                            <div id="imageFileArea" style="display:none;">
                                                <input type="hidden" name="fileChange" id="fileChange" value="false" />
                                                <input id="imageFile" name="files" type="file" data-show-upload="false" />
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div id="errArea"></div>
                                    <div class="col-lg-11 text-right">
                                        <button type="button" id="btnUpdate" class="btn btn-primary">
                                            Save<i class="icon-paperplane ml-2"></i>
                                        </button>
                                    </div>
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
