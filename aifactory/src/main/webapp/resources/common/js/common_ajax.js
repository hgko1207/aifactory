$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader("AJAX", true);
    },
    error : function(xhr, textStatus, errorThrown) {
        console.log("xhr.status : " + xhr.status);
        if (xhr.status == 403) {
            alert("로그인이 필요합니다.");
            location.href = contextName() + "/login/login.do";
        }else if (xhr.status == 404) {
            alert("Page Not Found.");
        }
        
        var stats = xhr.readyState;
        if (stats != "0") {
            var code = xhr.responseJSON.code;
            var message = xhr.responseJSON.message;
            
            if (code == "10000") {
                alert(message);
                location.href = contextName();
            } else {
                alert(message);
            }
        } else {
            //alert("연결이 끊겼습니다.");
        }
    }
});

/**************************************************************************
 * ajax_form : form 전송
 * ajax_url  : json data 전송
***************************************************************************/
$.extend({
    ajax_form:function(options){
        options = options || {};
        
        if( options.formId == null || options.formId == undefined ){
            alert("전송할 폼의 ID을 입력하여 주십시오.");
            return false;
        }
        
        if( options.url == null || options.url == undefined ){
            alert("URL을 입력하여 주십시오.");
            return false;
        }
        
        options.data = $("#"+options.formId).serialize();
        
        if( options.otherParam != null && options.otherParam.length > 0 ){
            options.data += "&"+options.otherParam;
        }
        
        $.ajax_common(options);
    },
    ajax_data:function(options){
        options = options || {};
        
        if( options.url == null || options.url == undefined ){
            alert("URL을 입력하여 주십시오.");
            return false;
        }
        
        if( options.data == null || options.data == undefined ){
            alert("data를 입력하여 주십시오.");
            return false;
        }
        
        options.contentType = false;
        
        $.ajax_common(options);
    },
    ajax_url:function(options){
        options = options || {};
        
        options.type = (options.type == null || options.type == undefined)?"GET":options.type
        
        if( options.url == null || options.url == undefined ){
            alert("URL을 입력하여 주십시오.");
            return false;
        }
        
        $.ajax_common(options);
    },
    ajax_common:function(options){
        $.ajax({
            type: (options.type == null || options.type == undefined)?"POST":options.type,
            async: (options.async == null || options.async == undefined)?true:options.async,
            url:options.url,
            dataType: 'json',
            data: options.data,
            cache: false,
            contentType: (options.contentType == null || options.contentType == undefined)?"application/x-www-form-urlencoded":options.contentType,
            processData: (options.processData == null || options.processData == undefined)?false:options.processData,
            success: options.success,
            error: options.error
        });
    }
});

//AJAX 통신
function ajaxCommonJson(ajaxUrl, method, param) {
    var returnVal;
    
    jQuery.ajax({
        type: method,
        url: ajaxUrl,
        async: false,
        dataType: 'json',
        data: param,
        success: function (response) {
            returnVal = response;
        }
    });
    
    return returnVal;
}