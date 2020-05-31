<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>

<div class="card overview">
	<div class="card-header bg-white header-elements-inline">
		<h5 class="card-title">
			<i class="icon-cube3 mr-2"></i>Overview
		</h5>
	</div>
	<div class="card-body">
		<div class="d-md-flex">
			<ul class="nav nav-tabs nav-tabs-vertical flex-column mr-md-3 wmin-md-250 mb-md-0 border-bottom-0">
				<li class="nav-item">
					<a href="#basic-tab" class="nav-link active show" data-toggle="tab">Basic</a>
				</li>
				<li class="nav-item">
					<a href="#description-tab" class="nav-link" data-toggle="tab">Description</a>
				</li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane fade active show" id="basic-tab">
                    <div id="basic-content" class="markdown-converter__text--rendered">
                        <div class="card-body row">
            				<input type="hidden" name="username" value="">
            				<div class="d-inline-flex" style="width: 100%">
                                <div class="col-3">
            					   <label>Title</label>
                                </div>
                                <div class="form-group col-6">
            					   <input type="text" value="" name="" class="form-control">
                                </div>
            				</div>
            				<div class="d-inline-flex" style="width: 100%">
                                <div class="col-3">
                                   <label>*Description</label>
                                </div>
                                <div class="form-group col-6">
                                   <input type="text" value="" name="" class="form-control">
                                </div>
                            </div>
                            <div class="d-inline-flex" style="width: 100%">
                                <div class="col-3">
                                   <label>Title</label>
                                </div>
                                <div class="form-group col-6">
                                   <input type="text" value="" name="" class="form-control">
                                </div>
                            </div>
                            <div class="d-inline-flex" style="width: 100%">
                                <div class="col-3">
                                   <label>start date</label>
                                </div>
                                <div class="form-group col-6">
                                    <div class="input-group">
                                        <span class="input-group-prepend">
                                            <span class="input-group-text">
                                                <i class="icon-calendar22"></i>
                                            </span>
                                        </span>
        				                <input type="text" id="daterange-single" class="form-control daterange-single">
                                    </div>
                                </div>
                            </div>
                            <div class="d-inline-flex" style="width: 100%">
                                <div class="col-3">
                                   <label>end date</label>
                                </div>
                                <div class="form-group col-6">
                                    <div class="input-group">
                                        <span class="input-group-prepend">
                                            <span class="input-group-text">
                                                <i class="icon-calendar22"></i>
                                            </span>
                                        </span>
                                        <input type="text" id="daterange-single2" class="form-control daterange-single">
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
                                            <input type="radio" class="form-check-input" name="radio-unstyled-inline-left" checked="">
                                            Public
                                        </label>
                                        <div class="form-check form-check-inline ml-3">
                                        <label class="form-check-label">
                                            <input type="radio" class="form-check-input" name="radio-unstyled-inline-left">
                                            private
                                        </label>
                                    </div>
                                    </div>
                                </div>
                            </div>
                            <div class ="row">
                                <div class="card">
                                    <div class="card-header bg-white">
                                        <h6 class="card-title">
                                            <i class="icon-chess-king mr-2"></i>
                                            Lap
                                        </h6>
                                    </div>
                                    <div class="card-body">
                                        <div class ="row">
                                            <div class="table-responsive col-lg-10">
                                                <table class="table" id="jiraTable">
                                                    <col width="25%">
                                                    <col width="*%">
                                                    <col width="25%">
                                                    <col width="7%">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col" data-popup="tooltip" data-placement="bottom">score</th>
                                                            <th scope="col" data-popup="tooltip" data-placement="bottom">end date</th>
                                                            <th colspan="1" scope="col" data-popup="tooltip" data-placement="bottom">check Point time</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><input type="text" name="details[0].label" maxlength="245" class="form-control"
                                                                required="required"/></td>
                                                            <td><input type="text" id="details[0].field" class="form-control daterange-single"></td>
                                                            <td>
                                                            <input type="text" name="details[0].type" maxlength="245" class="form-control"
                                                                required="required"/></td>
                                                            <td style="white-space:nowrap;">
                                                                <span id="detailButtons">
                                                                    <i onclick="addNewRow(this);" class="icon-add"></i>
                                                                    <i onclick="deleteCurrentRow(this);" class="icon-subtract"></i>
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="table-responsive col-lg-5">
                                            </div>
                                            <div class="table-responsive col-lg-7">
                                                <table class="table">
                                                    <col width="25%">
                                                    <col width="*%">
                                                    <col width="7%">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col" data-popup="tooltip" data-placement="bottom">Rank</th>
                                                            <th scope="col" data-popup="tooltip" data-placement="bottom">Money</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><input type="text" name="details[0].label" maxlength="245" class="form-control"
                                                                required="required"/></td>
                                                            <td><input type="text" name="details[0].field" maxlength="245" class="form-control"
                                                                required="required"/></td>
                                                            <td style="white-space:nowrap;">
                                                                <span id="detailButtons2">
                                                                    <i onclick="addNewRow2(this);" class="icon-add"></i>
                                                                    <i onclick="deleteCurrentRow2(this);" class="icon-subtract"></i>
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                             <div class="col-lg-11 text-right">
                                <button type="submit" class="btn btn-primary" onclick="save()">Save<i class="icon-paperplane ml-2"></i></button>
                            </div>
                        </div>
                    </div>
				</div>
				<div class="tab-pane fade" id="description-tab">
					<div id="description-content" class="markdown-converter__text--rendered">
                    </div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	dateSingle();
    function dateSingle(){
        $('.daterange-single').daterangepicker({ 
            singleDatePicker: true,
            locale: {
                daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
                monthNames: ["1월","2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
            }
        });
        
        $('.daterange-single2').daterangepicker({ 
            singleDatePicker: true,
            locale: {
                daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
                monthNames: ["1월","2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
            }
        });
    }
    
var tableRowCount = 1;
	
function addNewRow(ele) {
    if (tableRowCount >= 15)
        return;
    var row = $(ele).parents("tr").clone();
    row.children("th").html(tableRowCount + 1);
    row.children("td:eq(0)").children("input").attr("name", "details[" + tableRowCount + "].label");
    row.children("td:eq(1)").children("input").attr("name", "details[" + tableRowCount + "].field");
    row.children("td:eq(2)").children("input").attr("name", "details[" + tableRowCount + "].type");
    $(ele).parents("tbody").append(row);
    //$(ele).parents("td").empty();
    $(ele).remove();
    document.getElementsByName('details['+tableRowCount+'].label')[0].value = "";	    
    document.getElementsByName('details['+tableRowCount+'].field')[0].value = "";	    
    document.getElementsByName('details['+tableRowCount+'].type')[0].value = "";	    
    tableRowCount++;

    dateSingle();
}

function deleteCurrentRow(ele) {
    if (tableRowCount <= 1)
        return;
    var buttons = $(ele).parent("span#detailButtons").clone();
    $(ele).parents("tr").remove();
    tableRowCount--;
}

var tableRowCount2 = 1;

function addNewRow2(ele) {
    if (tableRowCount2 >= 15)
        return;
    var row = $(ele).parents("tr").clone();
    row.children("th").html(tableRowCount2 + 1);
    row.children("td:eq(0)").children("input").attr("name", "details[" + tableRowCount2 + "].label");
    row.children("td:eq(1)").children("input").attr("name", "details[" + tableRowCount2 + "].field");
    $(ele).parents("tbody").append(row);
    //$(ele).parents("td").empty();
    $(ele).remove();
    document.getElementsByName('details['+tableRowCount2+'].label')[0].value = "";	    
    document.getElementsByName('details['+tableRowCount2+'].field')[0].value = ""; 
    tableRowCount2++;
}

function deleteCurrentRow2(ele) {
    if (tableRowCount2 <= 1)
        return;
    var buttons = $(ele).parent("span#detailButtons2").clone();
    $(ele).parents("tr").remove();
    tableRowCount2--;
}

</script>