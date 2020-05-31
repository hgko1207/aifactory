(function ($) {
    $.INS =  {
        'isEmpty':function(value){
            if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
                return true
            }else{
                return false
            } 
        },
        'alert':function(msg){
            $('<div id="dialog" title="Alert"><p>'+msg+'</p></div>').dialog();
            
        },
        'confirm':function(msg, applyCallback, cancelCallback){
             var options = {
                // 커스텀 스타일을 줍니다.
                dialogClass: 'custom-dialog-style',
                
                // 모달 다이얼로그로 생성합니다.
                modal: true,
                
                // 열기 콜백
                open: function () {
                    // 모달 오버레이 설정
                    $(".ui-widget-overlay").css({
                        opacity: 0.5,
                        filter: "Alpha(Opacity=50)",
                        backgroundColor: "black"
                    });
                },
                
                // 닫기 콜백
                close: function (e, ui) {
                    $(this).empty();
                    $(this).dialog('destroy');
                },
               
                height: "auto",
                width: 400,
                title: 'Confirm',
                buttons: {
                    Ok: function() {
                        if(!$.INS.isEmpty(applyCallback))
                            applyCallback();
                        $( this ).dialog( "close" );
                    },
                    Cancel: function() {
                        if(!$.INS.isEmpty(cancelCallback))
                            cancelCallback();
                        $( this ).dialog( "close" );
                    }
                }
            };
            $('<div id="dialog" title="Confirm"><p>'+msg+'</p></div>').dialog(options);
        },
        'log':function(msg, level){
            console.log(msg);
        }
    };
})(jQuery)