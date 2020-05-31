<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>
<link href="${pageContext.request.contextPath}/resources/css/leaderboard.css" rel="stylesheet" type="text/css">

<style>
.file-drop-zone-title{
    padding:0px;
}
</style>
<script>
var formData;
$( document ).ready(function() {
    /**************************************************************************************
     * 파일 업로드 관련 설정
     **************************************************************************************/
    $('#resultFile').fileinput({
        uploadUrl: "${contextName}/file/upload.do", // server upload action
        uploadAsync: true,
        initialPreview: [],
        maxFileCount: 1,
        browseIcon: '<i class="icon-file-plus mr-2"></i>',
        uploadIcon: '<i class="icon-file-upload2 mr-2"></i>',
        removeIcon: '<i class="icon-cross2 fontw-size-base mr-2"></i>',
        layoutTemplates: {
            icon: '<i class="icon-file-check"></i>',
            modal: modalTemplate
        },
        previewZoomButtonClasses: previewZoomButtonClasses,
        previewZoomButtonIcons: previewZoomButtonIcons,
        fileActionSettings: fileActionSettings
    });
    
    $('#resultFile').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form,
            files = data.files,
            extra = data.extra,
            response = data.response,
            reader = data.reader;
        formData = new FormData();
        formData.append('lap.task.taskId','${task.taskId}');
        formData.append('modelNm',$('input[name="modelNm"]').val());
        formData.append('adhrncSeCode.cmmnCode','0000');	// 개인 참가자
        formData.append('cmmnFile.files[0].streFileNm', response.upload[0].streFileNm);
        formData.append('cmmnFile.files[0].orignlFileNm', response.upload[0].orignlFileNm);
        formData.append('cmmnFile.files[0].fileExtsn', response.upload[0].fileExtsn);
        formData.append('cmmnFile.files[0].fileSize', response.upload[0].fileSize);
        formData.append('cmmnFile.files[0].fileStreCours', 'SUBMISSION');
    });
    
    $('#resultFile').on('filebatchuploadcomplete', function(event, preview, config, tags, extraData) {
        $.ajax({
            type: "POST",
            async: true,
            url:'${contextName}/lapAdhrnc/insert.do',
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
</script>
<div class="card">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-upload mr-2"></i>Submission
        </h5>
    </div>
    <div class="card-body">
        <div class="card w-100">
            <div class="card-header bg-white">
                <h6 class="card-title">
                    <i class="icon-file-upload2 mr-2"></i> Result File Submission
                </h6>
            </div>
            <div class="card-body">
                <div class="row ml-3">
                    <div class="col-2">
                        <label>Model Name</label>
                    </div>
                    <div class="form-group col-10">
                        <input name="modelNm" value="${modelNm}" type="text" class="form-control" />
                    </div>
                </div>
                <div class="row ml-3">
                    <div class="col-2">
                        <label>Result File</label>
                    </div>
                    <div class="col-10">
                        <div class="file-loading">
                            <input id="resultFile" name="files" type="file" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card w-100">
            <div class="card-header bg-white">
                <h6 class="card-title">
                    <i class="icon-history mr-2"></i> History
                </h6>
            </div>
            <div class="card-body">
                <table class="table table-hover">
                    <colgroup>
                        <col width="17%">
                        <col width="*">
                        <col width="10%">
                        <col width="10%">
                        <col width="10%">
                        <col width="10%">
                    </colgroup>
                    <thead>
                        <tr class="text-center">
                            <th>Date</th>
                            <th class="text-left pl-5">Model Name</th>
                            <th class="text-right pr-2">Pre Score</th>
                            <th class="text-right pr-2">Pre Rank</th>
                            <th class="text-right pr-2">Real Score</th>
                            <th class="text-right pr-2">Real Rank</th>
                        </tr>
                    </thead>
                    <tbody class="text-center">
                        <c:choose>
                            <c:when test="${fn:length(result.content) > 0}">
                                <c:forEach var="item" items="${result.content}" varStatus="idx">
                                    <fmt:formatDate value="${item.registDttm}" var="submissionDttm" pattern="yyyy-MM-dd HH:mm:ss" />
                                    <c:set var="preScore" value="-"></c:set>
                                    <c:set var="preRank" value="-"></c:set>
                                    <c:set var="realScore" value="-"></c:set>
                                    <c:set var="realRank" value="-"></c:set>
                                    <c:choose>
                                        <c:when test="${item.resultSbmisnMthdCode.cmmnCode=='0000'}">
                                            <c:set var="preScore" value="${item.scre}"></c:set>
                                            <c:if test="${!empty item.rank}">
                                                <c:set var="preRank" value="${item.rank}"></c:set>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="realScore" value="${item.scre}"></c:set>
                                            <c:if test="${!empty item.rank}">
                                                <c:set var="realRank" value="${item.rank}"></c:set>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    
                                    <tr class="text-center">
                                        <td class="text-right">${submissionDttm}</td>
                                        <td class="font-weight-semibold text-left pl-5">${item.modelNm}</td>
                                        <td class="font-weight-semibold text-left pl-5">${preScore}</td>
                                        <td>${preRank}</td>
                                        <td>${realScore}</td>
                                        <td>${realRank}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6">No Data</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>