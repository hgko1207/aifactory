<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Editor-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script src="${contextName}/resources/js/lap.js"></script>
<script src="${contextName}/resources/js/task.js"></script>
<script src="${contextName}/resources/js/task_lap.js"></script>

<script type="text/javascript">
var lapValidate;
var rankValidate;
var lapMode = "A"; // A : Add, E : Edit
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
                $('#taskDc').val(editor.getHtml());
            }
        }
    };
    editor = $.INS.editor.init('taskDcViewer', editorOptions);
    editor.setHtml(unescape('${task.taskDc}'));
    
    /**************************************************************************************
     * lap 정보 셋팅
     **************************************************************************************/
    <c:forEach var="lap" items="${task.laps}" varStatus="lapStatus">
        <fmt:formatDate value="${lap.endDttm}" var="endDttm" pattern="yyyy-MM-dd" />
        lapObjs.addLap("${lap.lapSn}", "${lap.lapEndCndCode.cmmnCode}", "${lap.goalScre}", "${endDttm}", "${lap.chkPnttmCycle}");
        <c:forEach var="rank" items="${lap.lapRank}" varStatus="rankStatus">
            lapObjs.addRank("${lapStatus.index}", "${rank.money}");
        </c:forEach>
        
        addLapRow("${lap.goalScre}", "${endDttm}", "${lap.chkPnttmCycle}", "${lap.lapRank.size()}", "${lap.totalPoint}");
    </c:forEach>
    
    lapObjs.print();

    /**************************************************************************************
     * 태스크 관련 설정
     **************************************************************************************/
    // 태스크 기간 선택
    $('#beginDttm').daterangepicker({
        "singleDatePicker" : true,
        "locale" : {
            "format" : "YYYY-MM-DD",
            "separator" : " - ",
            "customRangeLabel" : "Custom",
            "weekLabel" : "W",
            "daysOfWeek" : [ "Su", "Mo", "Tu", "We", "Th","Fr", "Sa" ],
            "monthNames" : [ "January", "February","March", "April", "May", "June",
                             "July", "August", "September","October", "November", "December" ],
            "firstDay" : 1
        }
    });
    $('#endDttm').daterangepicker({
        "singleDatePicker" : true,
        "autoUpdateInput" :  false,
        "locale" : {
            "format" : "YYYY-MM-DD",
            "separator" : " - ",
            "customRangeLabel" : "Custom",
            "weekLabel" : "W",
            "daysOfWeek" : [ "Su", "Mo", "Tu", "We", "Th","Fr", "Sa" ],
            "monthNames" : [ "January", "February","March", "April", "May", "June",
                             "July", "August", "September","October", "November", "December" ],
            "firstDay" : 1
        }
    },
    function(start, end, label) {
        $(this.element[0]).val(start.format('YYYY-MM-DD'));
    });
    
    // 수정
    $('#btnTaskUpdate').click(function(){
        if($("#task").valid()){
            if($('#previewArea').is(':visible')){
                $('#task').submit();
            }else{
                fileUpload();
            }
        }
    });
    
    <c:choose>
        <c:when test="${!empty task.cmmnFile && !empty task.cmmnFile.files}">
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
    var param = lapObjs.toParam();
    if( !isEmpty(param) ) param += "&";
    param += fileParam;
    
    $.ajax_form({
        url:'<c:url value="/task/update.do" />',
        formId:'task',
        otherParam:param,
        success: function (data) {
            alert('저장 되었습니다.');
            location.href = '${contextName}/task/detail.do?taskId='+data.task.taskId;
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

/**************************************************************************************
 * Lap 관련 함수
 **************************************************************************************/
function deleteLapRow(){
    // lapSn 초기화
    $('#frmDeleteLap input[name=lapSn]').val('');
    
    var ele = $('.row-selected');
    if( ele.length == 0 ){
        alert('먼저 삭제할 행을 선택하셔야 합니다.');
        return false;
    }else{
        var okCallback = function(){
            var idx = ele.attr('index');
            var lapSn = lapObjs.getLap(idx-1).lapSn;
            $('#frmDeleteLap input[name=lapSn]').val(lapSn);
            $.ajax_form({
                url:'<c:url value="/lap/delete.do" />',
                formId:'frmDeleteLap',
                success: function (data) {
                    alert('삭제 되었습니다.');
                    removeLapRow();
                }
            });
        }
        var cancelCallback = function(){
             $('#frmDeleteLap input[name=lapSn]').val('');
        }
        $.INS.confirm('선택된 랩을 삭제하시겠습니까?<br>삭제하시면 복구할 수 없습니다.', okCallback, cancelCallback);
    }
}
</script>

<div class="card overview">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-cube3 mr-2"></i>Task
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
                    <form:form commandName="task" method="POST" cssClass="form-validate">
                        <form:input path="taskId" type="hidden"/>
                        <form:input path="cmmnFile.atchFileId" type="hidden"/>
                        <div class="tab-pane fade active show" id="basic-tab">
                            <div id="basic-content" class="markdown-converter__text--rendered">
                                <div class="card-body row ml-3">
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>*Title</label>
                                        </div>
                                        <div class="form-group col-9 form-group-feedback input-group">
                                            <form:input path="taskNm" type="text" cssClass="form-control" required="required" />
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>*Summary</label>
                                        </div>
                                        <div class="form-group col-9 form-group-feedback input-group">
                                            <form:input path="taskSumry" type="text" cssClass="form-control" required="required" />
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>채점파일위치</label>
                                        </div>
                                        <div class="form-group col-9 form-group-feedback input-group">
                                            <form:input path="grdngFileCours" type="text" cssClass="form-control" required="required" />
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>*Description</label>
                                        </div>
                                        <div class="form-group col-9 form-group-feedback">
                                            <form:hidden path="taskDc" cssClass="form-control" required="required" />
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
                                                        <img src="${contextName}/download/byNameStream/${task.cmmnFile.files[0].fileStreCours}/${task.cmmnFile.files[0].streFileNm}/" width="80"/>
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
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>*Start Date</label>
                                        </div>
                                        <div class="form-group col-6 form-group-feedback">
                                            <div class="input-group">
                                                <span class="input-group-prepend">
                                                    <span class="input-group-text"> <i class="icon-calendar22"></i></span>
                                                </span>
                                                <fmt:formatDate value="${task.beginDttm}" var="beginDttm" pattern="yyyy-MM-dd" />
                                                <input name="beginDttm" id="beginDttm" type="text" value="${beginDttm}" class="form-control"  required="required" autocomplete="off" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>End Date</label>
                                        </div>
                                        <div class="form-group col-6 form-group-feedback">
                                            <div class="input-group">
                                                <span class="input-group-prepend">
                                                    <span class="input-group-text"> <i class="icon-calendar22"></i></span>
                                                </span>
                                                <fmt:formatDate value="${task.endDttm}" var="endDttm" pattern="yyyy-MM-dd" />
                                                <input name="endDttm" id="endDttm" type="text" value="${endDttm}" class="form-control" autocomplete="off" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                        <div class="col-3">
                                            <label>Privacy</label>
                                        </div>
                                        <div class="form-group col-6">
                                            <div class="form-check form-check-inline">
                                                <label class="form-check-label">
                                                    <form:radiobutton path="othbcYn" value="Y" cssClass="form-check-input" checked="checked"/>Public
                                                </label>
                                                <label class="form-check-label ml-3">
                                                    <form:radiobutton path="othbcYn" value="N" cssClass="form-check-input" />Private
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row w-100">
                                        <div class="card w-100">
                                            <div class="card-header bg-white">
                                                <h6 class="card-title">
                                                    <i class="icon-chess-king mr-2"></i> Lap
                                                </h6>
                                            </div>
                                            <div class="card-body">
                                                <div class="row p-2">
                                                    <div class="table-responsive col-sm-12">
                                                        <table class="table" id="lapTable">
                                                            <col width="5%">
                                                            <col width="13%">
                                                            <col width="14%">
                                                            <col width="20%">
                                                            <col width="16%">
                                                            <col width="*">
                                                            <col width="5%">
                                                            <thead>
                                                                <tr>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">#</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Score</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">End date</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Check Point Time(Hour)</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Total Rank</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Total Point</th>
                                                                    <th style="white-space: nowrap;">
                                                                        <span id="detailButtons">
                                                                            <i onclick="openLapModal('A');" class="fa fa-plus-square fa-lg"></i>
                                                                            <i onclick="deleteLapRow();" class="fa fa-minus-square fa-lg"></i>
                                                                            <i onclick="openLapModal('E');" class="fa fa-edit fa-lg"></i>
                                                                        </span>
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="lapInfoArea">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <div class="row p-2">
                                                    <div class="table-responsive col-sm-7"></div>
                                                    <div class="table-responsive col-sm-5" id="rankArea">
                                                        <table class="table">
                                                            <col width="34%">
                                                            <col width="*">
                                                            <col width="1%">
                                                            <thead style="padding-bottom:100px;">
                                                                <tr>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom" style="padding-top:23px;padding-bottom:23px;">Rank</th>
                                                                    <th scope="col" data-popup="tooltip" data-placement="bottom">Point</th>
                                                                    <th style="white-space: nowrap;">
                                                                        <span id="detailButtons2">
                                                                            <i onclick="openRankModal();" class="fa fa-plus-square fa-lg"></i>
                                                                        </span>
                                                                    </th>
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
                                    <div class="col-lg-11 text-right">
                                        <button type="button" id="btnTaskUpdate" class="btn btn-primary">
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

<form name="frmDeleteLap" id="frmDeleteLap">
    <input name="task.taskId" id="taskId" type="hidden" value="${task.taskId}"/>
    <input name="lapSn" id="lapSn" type="hidden" />
</form>

<!-- Lap Modal Pop -->
<div class="modal fade" id="lapModal" tabindex="-1" role="dialog" aria-labelledby="lapModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Lap Info</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form name="frmLapInfo" id="frmLapInfo">
                    <input name="lapSn" id="lapSn" type="hidden" />
                    <div class="d-inline-flex" style="width: 100%">
                        <div class="col-3">
                            <label>End Condition</label>
                        </div>
                        <div class="form-group col-9 form-group-feedback input-group">
                            <select id="lapEndCndCode" name="lapEndCndCode" class="form-control">
                                <option value="0000">End Date</option>
                                <option value="0001">Score</option>
                            </select>
                        </div>
                    </div>
                    <div class="d-inline-flex" style="width: 100%">
                        <div class="col-3">
                            <label>Score</label>
                        </div>
                        <div class="form-group col-9 form-group-feedback input-group">
                            <input name="goalScre" id="goalScre" type="text" class="form-control" />
                        </div>
                    </div>
                    <div class="d-inline-flex" style="width: 100%">
                        <div class="col-3">
                            <label>End Date</label>
                        </div>
                        <div class="form-group col-9 form-group-feedback input-group">
                            <input type="text" name="lapEndDttm" class="form-control" autocomplete="off">
                        </div>
                    </div>
                    <div class="d-inline-flex" style="width: 100%">
                        <div class="col-3">
                            <label>Check Point Time(Hour)</label>
                        </div>
                        <div class="form-group col-9 form-group-feedback input-group">
                            <input type="text" name="chkPnttmCycle" class="form-control" autocomplete="off">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeLapModal();">Close</button>
                <button type="button" class="btn btn-primary" onclick="applyLap();">Apply</button>
            </div>
        </div>
    </div>
</div>

<!-- Rank Modal Pop -->
<div class="modal fade" id="rankModal" tabindex="-1" role="dialog" aria-labelledby="rankModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Rank</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form name="frmRankInfo" id="frmRankInfo">
                    <div class="d-inline-flex" style="width: 100%">
                        <div class="col-3">
                            <label>Rank</label>
                        </div>
                        <div class="form-group col-9 form-group-feedback input-group">
                            <input name="rank" id="rank" type="text" class="form-control" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="d-inline-flex" style="width: 100%">
                        <div class="col-3">
                            <label>Point</label>
                        </div>
                        <div class="form-group col-9 form-group-feedback input-group">
                            <input name="point" id="point" type="text" class="form-control" required="required" />
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeRankModal();">Close</button>
                <button type="button" class="btn btn-primary" onclick="applyRank();">Add</button>
            </div>
        </div>
    </div>
</div>