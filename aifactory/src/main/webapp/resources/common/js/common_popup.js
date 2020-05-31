(function ($) {
    $.INS.popup = function(id, options){
        var obj = $('#'+id);
        
        var applyCallback = options.applyCallback; 
        var cancelCallback = options.cancelCallback; 
        
        var defaultOptions = {
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
            width: 800,
            title: '지상국 시스템',
            buttons: {
                Apply: function() {
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
        
        // 버튼을 사용자 정의 했다면 default button 삭제 
        if(!$.isEmptyObject(options.buttons) || options.buttonDisplay == false){
            defaultOptions.buttons = {};
        }
        
        var _settings = $.extend(true, {}, defaultOptions, options);
        
        // jQuery UI Dialog
        $('<div id="'+id+'" class="mx-1 my-2 p-0"><iframe onload="$.INS.popup.resizeIframe(this)" src="'+_settings.url+'" width="100%" frameborder=0 framespacing=0 marginheight=1 marginwidth=2 scrolling=no vspace=0></div>').dialog(_settings);        
    };
    $.INS.popup.resizeIframe = function(el) {
        el.height = el.contentWindow.document.body.scrollHeight + "px";
    };
    $.INS.popup.close = function(id, param) {
        // 현재 문서에 Dialog가 있을 경우
        if( $.INS.isEmpty($('#'+id)) == false ){
            obj = $('#'+id).dialog('close', {'a':'1'});
        }
        // 부모 문서에 Dialog가 있을 경우
        else if( $.INS.isEmpty($(parent.document).find('#'+id)) == false ){
            window.parent.$('#'+id).dialog('close', {'a':'1'});
        }
    }
    
})(jQuery);
