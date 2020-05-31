(function ($) {
    $.INS.editor = {
        target :{
            tuiEdit : $('<div class="modal fade" id="modalTuiEditor" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
                        + '<div class="modal-dialog modal-lg modal-dialog-centered" role="document">'
                        + '<div class="modal-content">'
                        + '<div class="modal-body mb-0 p-0">'
                        + '<div class="embed-responsive embed-responsive-16by9 z-depth-1-half">'
                        + '<iframe class="embed-responsive-item" src="'+contextName()+'/resources/common/html/tuiEdit.html" allowfullscreen></iframe>'
                        + '</div>'
                        + '</div>'
                        + '<div class="modal-footer">'
                        + '<button type="button" class="btn btn-secondary btnClose">Close</button>'
                        + '<button type="button" class="btn btn-primary btnConfirm">Apply</button>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        )
        },
        modalObj:'',
        show:function(content, applyCallback){
            $.INS.editor.modalObj = $.INS.editor.target.tuiEdit.clone();
            
            // close button event
            $.INS.editor.modalObj.find(".btnClose").click(function(){
                $.INS.editor.hide();
            });
            
            // apply button event
            $.INS.editor.modalObj.find(".btnConfirm").click(function(){
                var mdData = $.INS.editor.modalObj.find("iframe").get(0).contentWindow.editor.getValue();
                var htmlData = $.INS.editor.modalObj.find("iframe").get(0).contentWindow.editor.getHtml();
                applyCallback(mdData, htmlData);
                $.INS.editor.hide();
            });
            
            // iframe onload event
            $.INS.editor.modalObj.find("iframe").get(0).onload = function(){
                // content load
                $.INS.editor.modalObj.find("iframe").get(0).contentWindow.contextName = contextName();
                $.INS.editor.modalObj.find("iframe").get(0).contentWindow.setHtml(content);
                $.INS.editor.modalObj.find("iframe").get(0).contentWindow.refreshContent();
            };
            
            $.INS.editor.modalObj.modal('show');
        },
        hide:function(){
            $.INS.editor.modalObj.modal('hide');
        },
        encodeForSave:function(data){
            data = escape(data);
            return data;
        },
        decodeForPrint:function(data){
            data = unescape(data);
            return data;
        },
        init:function(id, options){
            options = options || {};
            
            var defaultOptions = {
                el: document.querySelector('#'+id),
                previewStyle: 'vertical',       // tab|vertical
                height: '450px',
                initialEditType: 'markdown',    //markdown|wysiwyg
                hooks: {
                    addImageBlobHook: function (blob, callback) {
                        var formData = new FormData();
                        formData.append('files', blob);
                        $.ajax({
                            type: "POST",
                            async: true,
                            url:contextName() + '/file/uploadImageAndGetUrl.do',
                            dataType: 'json',
                            data: formData,
                            contentType: false,
                            processData: false,
                            success: function(data){
                                callback(data.uploadedImageURL, data.orignlFileNm);
                                return false;
                            }
                        });
                    }
                }
            };
            
            var _settings = $.extend(true, {}, defaultOptions, options);
            return new tui.Editor(_settings);
        }
    };
})(jQuery);