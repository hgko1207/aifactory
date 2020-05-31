//팝업열기
$.fn.showModalPop = function (id, title) {
    var html = '<div class="modal" tabindex="-1" role="dialog" id="'+id+'">';
    html += '<div class="modal-dialog modal-dialog-centered" role="document">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h5 class="modal-title">'+title+'</h5>';
    html += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
    html += '<span aria-hidden="true">&times;</span>';
    html += '</button>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p>Modal body text goes here.</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" class="btn btn-primary">Save changes</button>';
    html += '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    $('body').append(html);
    
    $('#'+id).modal('show')   
    
    return this;
}