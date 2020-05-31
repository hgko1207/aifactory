<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="loginUserId"/>
</sec:authorize>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Viewer-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script>
var formData;
var fileCnt;
var dataSetDcEditor;
var dataSetDcVal;
var fileDcEditor
var fileDcVal;
$( document ).ready(function() {
    /**************************************************************************************
     * tui editor 관련 설정
     **************************************************************************************/
    $.ajax_url({
        url:contextName()+'/taskData/detailJsonView.do?task.taskId=${task.taskId}',
        success: function (data) {
            dataSetDcVal = unescape(data.taskData.dataSetDc);
            dataSetDcEditor = new tui.Editor({
                el: document.querySelector('#dataSetDc'),
                height: '450px',
                initialValue:dataSetDcVal
            });
            
            fileDcVal = unescape(data.taskData.fileDc);
            fileDcEditor = new tui.Editor({
                el: document.querySelector('#fileDc'),
                height: '450px',
                initialValue:fileDcVal
            });
        }
    });
    
    /**************************************************************************************
     * 파일 업로드 관련 설정
     **************************************************************************************/
    $('#file-input').fileinput({
        uploadUrl: "${contextName}/file/upload.do", // server upload action
        uploadAsync: true,
        initialPreview: [],
        browseIcon: '<i class="icon-file-plus mr-2"></i>',
        uploadIcon: '<i class="icon-file-upload2 mr-2"></i>',
        removeIcon: '<i class="icon-cross2 font-size-base mr-2"></i>',
        layoutTemplates: {
            icon: '<i class="icon-file-check"></i>',
            modal: modalTemplate
        },
        previewZoomButtonClasses: previewZoomButtonClasses,
        previewZoomButtonIcons: previewZoomButtonIcons,
        fileActionSettings: fileActionSettings
    });
    
    $('#file-input').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form,
          files = data.files,
          extra = data.extra,
          response = data.response,
          reader = data.reader;
        
        for(var i=0; i < response.upload.length; i++ ){
            formData.append('cmmnFile.files['+fileCnt+'].streFileNm', response.upload[i].streFileNm);
            formData.append('cmmnFile.files['+fileCnt+'].orignlFileNm', response.upload[i].orignlFileNm);
            formData.append('cmmnFile.files['+fileCnt+'].fileExtsn', response.upload[i].fileExtsn);
            formData.append('cmmnFile.files['+fileCnt+'].fileSize', response.upload[i].fileSize);
            formData.append('cmmnFile.files['+fileCnt+'].fileStreCours', 'DATA_SET');
        }
        
        fileCnt++;
    });
    
    $('#file-input').on('filebatchuploadcomplete', function(event, preview, config, tags, extraData) {
        $.ajax({
            type: "POST",
            async: true,
            url:'${contextName}/taskData/updateFileInfo.do',
            dataType: 'json',
            data: formData,
            processData: false,
            contentType: false,
            success: function(data){
                refresh();
            }
        });
    });
});

function refresh(){
    location.reload();
}

function openUploadModal(){
    $("#file-input").fileinput('clear');
    $('#addDataFileModal').modal('show');
}

function fileUpload(){
    fileCnt = 0;
    formData = new FormData();
    formData.append('task.taskId','${task.taskId}');
    $("#file-input").fileinput('upload');
}

function deleteFile(atchFileId, fileSn){
    var deleteFileForm = new FormData();
    deleteFileForm.append('cmmnFile.atchFileId',atchFileId);
    deleteFileForm.append('fileSn',fileSn);
    $.ajax({
        type: "POST",
        async: true,
        url:'${contextName}/cmmnFileDetail/deleteFile.do',
        dataType: 'json',
        data: deleteFileForm,
        processData: false,
        contentType: false,
        success: function(data){
            refresh();
        }
    });
}

function downloadAll(atchFileId){
    if( isEmpty(atchFileId) ){
        alert('파일을 선택해 주세요.');
    }else{
        location.href="${contextName}/cmmnFileDetail/downloadAll.do?atchFileId="+atchFileId;
    }
}

function openTuiEditor(id){
    if( id == 'fileDc' ){
        $.INS.editor.show(fileDcVal, fileDcCallback);
    }else if( id == 'dataSetDc' ){
        $.INS.editor.show(dataSetDcVal, taskDcCallback);
    }
}

function taskDcCallback(mdData, htmlData){
    $.ajax({
        type: "POST",
        async: true,
        url:contextName()+'/taskData/updateDc.do',
        dataType: 'json',
        data: {
            'task.taskId':'${task.taskId}',
            'dataSetDc':htmlData
        },
        cache: false,
        success: function (data) {
            refresh();
        }
    });
}

function fileDcCallback(mdData, htmlData){
    $.ajax({
        type: "POST",
        async: true,
        url:contextName()+'/taskData/updateDc.do',
        dataType: 'json',
        data: {
            'task.taskId':'${task.taskId}',
            'fileDc':htmlData
        },
        cache: false,
        success: function (data) {
            refresh();
        }
    });
}
</script>

<div class="card">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-database4 mr-2"></i>Data
        </h5>
    </div>
    <div class="card-body">
        <div class="card">
            <div class="card-header bg-white header-elements-inline">
                <h5 class="card-title">
                    <i class="icon-file-text mr-2"></i>데이터 셋 설명
                </h5>
                <div class="header-elements">
                    <c:choose>
                        <c:when test="${loginUserId == task.register.userId}">
                            <button type="button" class="btn bg-blue" data-toggle="modal" onclick="openTuiEditor('dataSetDc');">Edit</button>
                        </c:when>
                        <c:otherwise>
                            <sec:authorize access="hasRole('ROLE_ADMN')">
                                <button type="button" class="btn bg-blue" data-toggle="modal" onclick="openTuiEditor('dataSetDc');">Edit</button>
                            </sec:authorize>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="card-body">
                <div id="dataSetDc"></div>
            </div>
        </div>
        <div class="card">
            <div class="card-header bg-white header-elements-inline">
                <h5 class="card-title">
                    <i class="icon-file-text mr-2"></i>파일 설명
                </h5>
                <div class="header-elements">
                    <c:choose>
                        <c:when test="${loginUserId == task.register.userId}">
                            <button type="button" class="btn bg-blue" data-toggle="modal" onclick="openTuiEditor('fileDc');">Edit</button>
                        </c:when>
                        <c:otherwise>
                            <sec:authorize access="hasRole('ROLE_ADMN')">
                                <button type="button" class="btn bg-blue" data-toggle="modal" onclick="openTuiEditor('fileDc');">Edit</button>
                            </sec:authorize>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="card-body">
                <div id="fileDc">${taskData.fileDc}</div>
            </div>
        </div>
        <div class="card">
            <div class="card-header bg-white header-elements-inline">
                <h5 class="card-title">
                    <i class="icon-floppy-disks mr-2"></i>데이터 셋
                </h5>
                <div class="header-elements">
                    <c:choose>
                        <c:when test="${loginUserId == task.register.userId}">
                            <button type="button" class="btn bg-blue" data-toggle="modal" onclick="openUploadModal();">Upload Data</button>
                        </c:when>
                        <c:otherwise>
                            <sec:authorize access="hasRole('ROLE_ADMN')">
                                <button type="button" class="btn bg-blue" data-toggle="modal" onclick="openUploadModal();">Upload Data</button>
                            </sec:authorize>
                        </c:otherwise>
                    </c:choose>
                    <sec:authorize access="isAuthenticated()">
                        <button type="button" class="btn bg-blue ml-3" onclick="downloadAll('${taskData.cmmnFile.atchFileId}');">Download All</button>
                    </sec:authorize>
                </div>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table">
                        <colgroup>
                            <col width="*" />
                            <col width="10%" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>Filename</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${fn:length(taskData.cmmnFile.files) > 0}">
                                    <c:forEach var="file" items="${taskData.cmmnFile.files}">
                                        <tr style="cursor:default;">
                                            <td>
                                                <sec:authorize access="isAuthenticated()">
                                                    <a href="${contextName}/download/byName/${file.fileStreCours}/${file.orignlFileNm}/${file.streFileNm}/">${file.orignlFileNm}</a>
                                                </sec:authorize>
                                                <sec:authorize access="isAnonymous()">
                                                    ${file.orignlFileNm}
                                                </sec:authorize>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${loginUserId == task.register.userId}">
                                                        <i class="icon-trash"  style="cursor:pointer;" onclick="deleteFile('${file.cmmnFile.atchFileId}','${file.fileSn}');"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <sec:authorize access="hasRole('ROLE_ADMN')">
                                                            <i class="icon-trash"  style="cursor:pointer;" onclick="deleteFile('${file.cmmnFile.atchFileId}','${file.fileSn}');"></i>
                                                        </sec:authorize>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="2">No Data</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
        <!--                     <tr> -->
        <!--                         <td>train.zip</td> -->
        <!--                         <td><i class="icon-trash"></i></td> -->
        <!--                     </tr> -->
        <!--                     <tr> -->
        <!--                         <td>train.csv</td> -->
        <!--                         <td><i class="icon-trash"></i></td> -->
        <!--                     </tr> -->
        <!--                     <tr> -->
        <!--                         <td>test.zip</td> -->
        <!--                         <td><i class="icon-trash"></i></td> -->
        <!--                     </tr> -->
        <!--                     <tr> -->
        <!--                         <td>sample_submission.csv</td> -->
        <!--                         <td><i class="icon-trash"></i></td> -->
        <!--                     </tr> -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="addDataFileModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i class="icon-file-excel mr-2"></i>데이터 파일 추가
                </h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <form id="fileUploadForm" action="${contextName}/taskData/uploadFiles.do" class="form-horizontal" method="POST" enctype="multipart/form-data">
                <div class="modal-body">
                    <input type="hidden" name="taskId" value="${task.taskId}">
                    <label>첨부파일 : </label>
                    <div class="form-group">
                        <input type="file" id="file-input" class="file-input" data-show-preview="true" name="files" data-show-upload="false" multiple>
                    </div>
                </div>
    
                <div class="modal-footer">
                    <button type="button" onclick="fileUpload();" class="btn btn-primary mr-2">&nbsp;&nbsp;&nbsp;업로드&nbsp;&nbsp;&nbsp;</button>
                    <button type="button" class="btn btn-light" data-dismiss="modal">&nbsp;&nbsp;닫 기&nbsp;&nbsp;</button>
                </div>
            </form>
        </div>
    </div>
</div>