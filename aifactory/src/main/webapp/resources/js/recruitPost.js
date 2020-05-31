var postValidate;

// On Load
document.addEventListener('DOMContentLoaded', function() {
    // 등록폼 유효성 체크
    postValidate = $('#post').validate({
        focusInvalid:false,
        ignore: '#taskDcViewer *, .te-image-file-input',
        onchange: function(element) {$(element).valid()},
        success: function(label) {
            label.remove();
        },
        
        rules: {
            postNm: {
                required: true,
                maxlength: 60
            },
            postDc: {
                required: true
            }          
        },
        messages: {
            postNm: {
                required: "Title은 필수 입력값입니다.",
                maxlength: jQuery.validator.format("{0}자 이하로 입력해야 합니다.")
            },
           
            postDc: {
                required: "Description은 필수 입력값입니다."
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
        $('#post').submit();
    });
    
});

// Task Start date 유효성 체크

 
 // Task End date 유효성 체크

// Lap Score 유효성 체크

// Lap End date 유효성 체크

// Lap Check Point Time 유효성 체크
