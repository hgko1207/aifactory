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
        },  		
        initialValue:unescape('${task.taskDc}')
    };
    editor = $.INS.editor.init('taskDcViewer', editorOptions);
    
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
    
    // 등록
    $('#btnTaskInsert').click(function(){
        if($("#task").valid()){
			fileUpload();
        }
    });
    
    
});

/**************************************************************************************
 * Form Submit
 **************************************************************************************/
function submitForm(){
    var param = lapObjs.toParam();
    if( !isEmpty(param) ) param += "&";
    param += fileParam;
    
    $.ajax_form({
        url:contextName()+'/task/insert.do',
        formId:'task',
        otherParam:param,
        success: function (data) {
            alert('저장 되었습니다.');
            location.href = contextName()+'/task/detail.do?taskId='+data.task.taskId;
        }
    });
}

/**************************************************************************************
 * File Upload 관련 함수
 **************************************************************************************/
function fileUpload(){
    $("#imageFile").fileinput('upload');
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
                                            <input id="imageFile" name="files" type="file" data-show-upload="false" />
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
                                                <form:input path="beginDttm" type="text" cssClass="form-control"  required="required" autocomplete="off" />
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
                                                <form:input path="endDttm" type="text" cssClass="form-control" autocomplete="off" />
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
                                                                            <i onclick="removeLapRow();" class="fa fa-minus-square fa-lg"></i>
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
                                        <button type="button" id="btnTaskInsert" class="btn btn-primary">
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