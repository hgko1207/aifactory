var taskValidate;

// On Load
document.addEventListener('DOMContentLoaded', function() {
    // 등록폼 유효성 체크
    taskValidate = $('#task').validate({
        focusInvalid:false,
        ignore: '#taskDcViewer *, .te-image-file-input',
        onchange: function(element) {$(element).valid()},
        success: function(label) {
            label.remove();
        },
        
        rules: {
            taskNm: {
                required: true,
                maxlength: 60
            },
            taskSumry:{
                required: true,
                maxlength: 100
            },
            taskDc: {
                required: true
            },
            grdngFileCours:{
                required: true,
                maxlength: 500
            },
            beginDttm: {
                required: true,
                date:true,
                validTaskStartDate:true
            },
            endDttm: {
                date:true,
                validTaskEndDate:true
            },
            othbcYn:{
                required: true,
                validLapScore:true,
                validLapEndDate:true,
                validLapChkPnttmCycle:true
            }
        },
        messages: {
            taskNm: {
                required: "Title은 필수 입력값입니다.",
                maxlength: jQuery.validator.format("{0}자 이하로 입력해야 합니다.")
            },
            taskSumry:{
                required: "Summary는 필수 입력값입니다.",
                maxlength: jQuery.validator.format("{0}자 이하로 입력해야 합니다.")
            },
            taskDc: {
                required: "Description은 필수 입력값입니다."
            },
            grdngFileCours: {
                required: "채점파일 위치는 필수 입력값입니다."
            },
            beginDttm: {
                required: "Start Date는 필수 입력값입니다."
            }
        },
        submitHandler: function(form){
            submitForm();
        },
        errorPlacement: function(error, element) {
            error.appendTo($('#errArea'));
        }
    });
    
    /**************************************************************************************
     * 파일 업로드 관련 설정
     **************************************************************************************/
    $('#imageFile').fileinput({
        uploadUrl: contextName()+"/file/upload.do", // server upload action
        uploadAsync: true,
        initialPreview: [],
        maxFileCount: 1,
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
    
    $('#imageFile').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form,
            files = data.files,
            extra = data.extra,
            response = data.response,
            reader = data.reader;
        
        fileParam = 'cmmnFile.files[0].streFileNm='+response.upload[0].streFileNm;
        fileParam += '&cmmnFile.files[0].orignlFileNm='+response.upload[0].orignlFileNm;
        fileParam += '&cmmnFile.files[0].fileExtsn='+response.upload[0].fileExtsn;
        fileParam += '&cmmnFile.files[0].fileSize='+response.upload[0].fileSize;
        fileParam += '&cmmnFile.files[0].fileStreCours=TASK_IMG';
    });
    $('#imageFile').on('filebatchuploadcomplete', function(event, preview, config, tags, extraData) {
        $('#task').submit();
    });
    
});

// Task Start date 유효성 체크
$.validator.addMethod("validTaskStartDate", function(value, element) {
    if( !isEmpty( $('#beginDttm').val() ) ){
        var lapEndDttm;
        for(var i=0; i<lapObjs.size(); i++){
            lapEndDttm = lapObjs.getLap(i).endDttm;
            if( !isEmpty(lapEndDttm) )
                break;
        }
        if( !isEmpty(lapEndDttm) )
            return $('#beginDttm').val() < lapEndDttm;
    }
    return true;
}, "Lap의 End Date는 Task의 Start Date보다 큰 날자를 입력하셔야 합니다.");
 
 // Task End date 유효성 체크
$.validator.addMethod("validTaskEndDate", function(value, element) {
    if( !isEmpty( $('#endDttm').val() ) ){
        return $('#beginDttm').val() < $('#endDttm').val();
    }
    return true;
}, "Task의 End Date는 Start Date보다 큰 날자를 입력하셔야 합니다.");
 
// Lap Score 유효성 체크
$.validator.addMethod("validLapScore", function(value, element) {
    // score chk
    var prevScre = -1;
    var currScre;
    for( var i=0; i<lapObjs.size(); i++ ){
        currScre = lapObjs.getLap(i).goalScre;
        if( isEmpty(currScre) )
            continue;
        
        if(parseFloat(currScre) <= parseFloat(prevScre)){
            return false;
        }
        
        prevScre = currScre;
        
    }

    return true;
}, "Lap의 Score는 오름차순으로 입력하셔야 합니다.");

// Lap End date 유효성 체크
$.validator.addMethod("validLapEndDate", function(value, element) {
    var prevDate = 0;
    var currDate;
    for( var i=0; i<lapObjs.size(); i++ ){
        currDate = lapObjs.getLap(i).endDttm;
        if( isEmpty(currDate) )
            continue;
        
        if(currDate <= prevDate){
            return false;
        }
        
        prevDate = currDate;
    }

    return true;
}, "Lap의 End Date는 오름차순으로 입력하셔야 합니다.");

// Lap Check Point Time 유효성 체크
$.validator.addMethod("validLapChkPnttmCycle", function(value, element) {
    var chkPnttmCycle;
    for( var i=0; i<lapObjs.size(); i++ ){
        chkPnttmCycle = lapObjs.getLap(i).chkPnttmCycle;
        if( isEmpty(chkPnttmCycle) )
            return false;
    }

    return true;
}, "Lap의 Check Point Time은 필수 입력값입니다.");