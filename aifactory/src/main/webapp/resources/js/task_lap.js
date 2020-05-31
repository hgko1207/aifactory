var lapValidate;
var rankValidate;
var lapMode = "A"; // A : Add, E : Edit

// On Load
document.addEventListener('DOMContentLoaded', function() {
    // Lap 초기화
    initLapEndDate();
    addNoLapDataRow();
    addNoRankDataRow();
    
    // Lap 유효성 체크
    lapValidate = $('#frmLapInfo').validate({
        rules: {
            goalScre: {
                number: true,
                validLapScoreRequiredByAdd: true,
                validLapScoreSortByAdd:true
            },
            lapEndDttm:{
                date:true,
                validLapEndDateRequiredByAdd:true,
                validLapEndDateSortByAdd:true
            },
            chkPnttmCycle:{
                required: true,
                digits:true
            }
        }
    });
    
    // Lap Rank 관련 설정
    rankValidate = $('#frmRankInfo').validate({
        rules:{
            point: {
                digits:true
            }
        }
    });
});

/**************************************************************************************
 * Lap 관련
 **************************************************************************************/
// Lap 추가시 End Date Required Validate
$.validator.addMethod("validLapEndDateRequiredByAdd", function(value, element) {
    var lapEndCndCode = $('#frmLapInfo select[name="lapEndCndCode"] option:selected').val();
    // Lap 종료조건이 일시일 경우
    if( lapEndCndCode == '0000' ){
        return !isEmpty($('#frmLapInfo input[name="lapEndDttm"]').val());
    }
    return true;
}, "Lap의 종료조건이 End Date일 경우 End Date는 반드시 입력하셔야 합니다.");

// Lap 추가시 End Date Sort Validate
$.validator.addMethod("validLapEndDateSortByAdd", function(value, element) {
    if( lapObjs.size() >= 1 ){
        if( !isEmpty($('#frmLapInfo input[name="lapEndDttm"]').val()) ){
            var lastEndDttm, currLapIdx=lapObjs.size()-1;
            if(lapMode=="E"){
                var ele = $('.row-selected');
                currLapIdx = $('.row-selected').attr('index') - 2;
            }
                
            for( var i = currLapIdx ; i >= 0 ; i-- ){
                if( !isEmpty(lapObjs.getLap(i).endDttm) ){
                    lastEndDttm = lapObjs.getLap(i).endDttm;
                    break;
                }
            }
            if( !isEmpty(lastEndDttm) )
                return $('#frmLapInfo input[name="lapEndDttm"]').val() > lastEndDttm;
        }
    }
    return true;
}, "Lap의 End Date는 이전에 등록한 날자보다 큰 날자를 입력하셔야 합니다.");

// Lap 추가시 Score Required Validate
$.validator.addMethod("validLapScoreRequiredByAdd", function(value, element) {
    var lapEndCndCode = $('#frmLapInfo select[name="lapEndCndCode"] option:selected').val();
    // Lap 종료조건이 점수일 경우
    if( lapEndCndCode == '0001' ){
        return !isEmpty($('#frmLapInfo input[name="goalScre"]').val());
    }
    return true;
}, "Lap의 종료조건이 Score일 경우 Score는 반드시 입력하셔야 합니다.");

// Lap 추가시 Score Sort Validate
$.validator.addMethod("validLapScoreSortByAdd", function(value, element) {
    if( lapObjs.size() >= 1 ){
        if( !isEmpty($('#frmLapInfo input[name="goalScre"]').val()) ){
            var lastGoalScre = 0;
            var currLapIdx=lapObjs.size()-1;
                
            if(lapMode=="E"){
                var ele = $('.row-selected');
                currLapIdx = $('.row-selected').attr('index') - 2;
            }
            
           for( var i = currLapIdx ; i >= 0 ; i-- ){
                if( !isEmpty(lapObjs.getLap(i).goalScre) ){
                    lastGoalScre = lapObjs.getLap(i).goalScre;
                    break;
                }
            }
            
            if( !isEmpty(lastGoalScre) )
                return parseFloat($('#frmLapInfo input[name="goalScre"]').val()) > parseFloat(lastGoalScre);
        }
    }
    return true;
}, "Lap의 Score는 이전에 등록한 Score보다 큰 값을 입력하셔야 합니다.");

// 종료일자 초기화
function initLapEndDate(defaultDate){
    $('#frmLapInfo input[name="lapEndDttm"]').daterangepicker(
        {
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
        }
    ).val(defaultDate);
}
// Lap Modal Open
function openLapModal(mode){
    lapMode = mode;
    
    // 초기화
    $('input[name="goalScre"]').val('');
    $('select[name="lapEndCndCode"]').val('0000');
    $('input[name="goalScre"]').val('');
    $('input[name="chkPnttmCycle"]').val('');
    
    // 수정시
    if(lapMode=="E"){
        var ele = $('.row-selected');
        if( ele.length == 0 ){
            alert('먼저 수정할 행을 선택셔야 합니다.');
            return false;
        }else{
            var idx = ele.attr('index');
            var lap = lapObjs.getLap(idx-1);
            
            $('select[name="lapEndCndCode"]').val(lap.lapEndCndCode);
            $('input[name="goalScre"]').val(lap.goalScre);
            $('input[name="chkPnttmCycle"]').val(lap.chkPnttmCycle);
            
            initLapEndDate(lap.endDttm);
        }
    }
 
    $('#lapModal').modal('show');
}

// Lap Modal Close
function closeLapModal(){
    lapValidate.resetForm();
    $('#frmLapInfo')[0].reset();
    $('#lapModal').modal('hide');
}

// Add Lap Info
function addLapRow(goalScre, lapEndDttm, chkPnttmCycle, totalRank, totalPoint){
    goalScre = (goalScre == 0)?'-':goalScre;
    
    var row = '<tr class="clickable-row" onclick="selectLapRow(this);" index="'+lapObjs.size()+'">';
    row += '<td>'+lapObjs.size()+'</td>';
    row += '<td>'+goalScre+'</td>';
    row += '<td>'+lapEndDttm+'</td>';
    row += '<td>'+chkPnttmCycle+'</td>';
    row += '<td>'+totalRank+'</td>';
    row += '<td>'+totalPoint+'</td>';
    row += '<td></td>';
    row += '</tr>';
    
    if( lapObjs.size() == 1 )
        $('#lapTable tbody tr').remove();
    
    $('#lapTable tbody').append(row);
}

// Apply Lap Info
function applyLap(){
    if( $("#frmLapInfo").valid() ){
        var lapSn = $('input[name="lapSn"]').val();
        var goalScre = $('input[name="goalScre"]').val();
        var lapEndDttm = $('input[name="lapEndDttm"]').val();
        var chkPnttmCycle = $('input[name="chkPnttmCycle"]').val();
        var lapEndCndCode = $('select[name="lapEndCndCode"] option:selected').val();
        
        // Edit
        if( lapMode == "E" ){
            var lapIdx = $(".row-selected").attr('index');
            lapObjs.editLap(lapIdx-1, lapEndCndCode, goalScre, lapEndDttm, chkPnttmCycle);
            
            var row = $(".row-selected");
            row.children("td:eq(1)").text(goalScre);
            row.children("td:eq(2)").text(lapEndDttm);
            row.children("td:eq(3)").text(chkPnttmCycle);
        }else{
            lapObjs.addLap(lapSn, lapEndCndCode, goalScre, lapEndDttm, chkPnttmCycle);
            
            addLapRow(goalScre, lapEndDttm, chkPnttmCycle, 0, 0);
        }

        closeLapModal();
    }
}
// Select Lap Row
function selectLapRow(ele){
    if($(ele).hasClass("row-selected")){
        $(ele).removeClass('row-selected');
    }else{
        $(ele).addClass('row-selected').siblings().removeClass('row-selected');
    }
    
    // Rank 정보 새로 고침
    refreshRankArea();
}

// Remove Lap Row
function removeLapRow(){
    var ele = $('.row-selected');
    if( ele.length == 0 ){
        alert('먼저 삭제할 행을 선택하셔야 합니다.');
        return false;
    }else{
        var idx = ele.attr('index');
        lapObjs.removeLap(idx-1);
        ele.remove();
     
        // Rank 정보 새로 고침
        refreshRankArea();
     
        // Lap 정보 새로 고침
        refreshLap()
    }
}

// Lap 정보 새로 고침
function refreshLap(){
    if(lapObjs.size() >= 1){
        for(var i=0; i<lapObjs.size(); i++ ){
            $('#lapInfoArea tr:eq('+i+')').attr('index', i+1);
            $('#lapInfoArea tr:eq('+i+')').children("td:eq(0)").text(i+1);
            $('#lapInfoArea tr:eq('+i+')').children("td:eq(1)").text(lapObjs.getLap(i).goalScre);
            $('#lapInfoArea tr:eq('+i+')').children("td:eq(2)").text(lapObjs.getLap(i).endDttm);
            $('#lapInfoArea tr:eq('+i+')').children("td:eq(3)").text(lapObjs.getLap(i).chkPnttmCycle);
            
            var ranksCnt = lapObjs.getLap(i).ranks.length;
            var pointSum = 0;
            for(var j=0; j<ranksCnt; j++){
                pointSum += parseInt(lapObjs.getLap(i).ranks[j].point);
            }
            $('#lapInfoArea tr:eq('+i+')').children("td:eq(4)").text(ranksCnt);
            $('#lapInfoArea tr:eq('+i+')').children("td:eq(5)").text(pointSum);
        }
    }else{
        addNoLapDataRow();
    }
}

//Add No Lap Data Row
function addNoLapDataRow(){
    var row = '<tr id="noLapDataRow">';
    row += '<td colspan="7" class="text-center">Please Add Lap Data</td>';
    row += '</tr>';
    
    $('#lapTable tbody').append(row);
}

/**************************************************************************************
 * Lap Rank 관련
 **************************************************************************************/
// Show Rank Area
function refreshRankArea(){
    var lapIdx = $(".row-selected").attr("index");
    if(lapIdx != null){
        var lap = lapObjs.getLap(lapIdx-1);
        
        
        if(lap.ranks == null || lap.ranks.length == 0){
            addNoRankDataRow();
        }else{
            for(var i=0; i < lap.ranks.length; i++ ){
                addRankRow(i+1, lap.ranks[i].point);
            }
        }
    }else{
        addNoRankDataRow();
    }
}

//Rank Modal Open
function openRankModal(){
    if( $(".row-selected") == null || $(".row-selected").length == 0 )
        alert('먼저 Rank를 추가할 Lap을 선택하셔야 합니다.');
    else{
        var lapIdx = $(".row-selected").attr("index");
        $('#frmRankInfo input[name="rank"]').val( lapObjs.getLap(lapIdx-1).ranks.length + 1 );
        $('#rankModal').modal('show');
    }
}
function closeRankModal(){
    rankValidate.resetForm();
    $('#frmRankInfo')[0].reset();
    $('#rankModal').modal('hide');
}

//Add No Rank Data Row
function addNoRankDataRow(){
    $('#rankArea tbody tr').remove();
    
    var row = '<tr id="noLapDataRow">';
    row += '<td colspan="3" class="text-center">Please Add Rank</td>';
    row += '</tr>';
    
    $('#rankArea tbody').append(row);
}

//Apply Rank Info
function applyRank(){
    if( $("#frmRankInfo").valid() ){
        var lapIdx = $(".row-selected").attr('index');
        var rank = $('input[name="rank"]').val();
        var point = $('input[name="point"]').val();
        lapObjs.addRank(lapIdx-1, point);
        
        // Lap 정보 새로고침
        refreshLap();
        
        addRankRow(rank, point);
        
        closeRankModal();
    }
}

// Add Rank Row
function addRankRow(rank, point){
    var row = '<tr index="'+rank+'">';
    row += '<td>'+rank+'</td>';
    row += '<td>'+point+'</td>';
    row += '<td style="white-space: nowrap;"><i onclick="removeRankRow(this);" class="icon-subtract"></i></span></td>';
    row += '</tr>';
    
    if( rank == 1 )
        $('#rankArea tbody tr').remove();
    
    $('#rankArea tbody').append(row);
}

// Remove Rank Row
function removeRankRow(ele){
    var row = $(ele).parents("tr");
    var lapIdx = $(".row-selected").attr('index');
    var rankIdx = row.attr('index');
    lapObjs.removeRank(lapIdx-1, rankIdx-1);
    
    // Lap 정보 새로고침
    refreshLap();
    
    row.remove();
    
    // Rank 정보 새로 고침
    refreshRankArea();
}