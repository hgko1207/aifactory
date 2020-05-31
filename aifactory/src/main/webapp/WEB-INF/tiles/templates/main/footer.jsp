<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>

<div class="navbar navbar-expand-lg navbar-light">
    <div class="text-center d-lg-none w-100">
        <button type="button" class="navbar-toggler dropdown-toggle" data-toggle="collapse" data-target="#navbar-footer">
            <i class="icon-unfold mr-2"></i>
        </button>
    </div>

    <div class="navbar-collapse collapse" id="navbar-footer">
        <span class="navbar-text font-weight-bold">
            (주)인공지능팩토리, 대전광역시 유성구 테크노1로 75, 508호, 대표 : 김태영, TEL : 010-3479-8364, MAIL : <a href="mailto:tykim@aifactory.page">tykim@aifactory.page</a>, 사업자번호 : 871-81-01735
        </span>

        <div class="navbar-nav  ml-lg-auto dropup">
            <a href="#" class="navbar-nav-link dropdown-toggle" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="icon-file-text2 mr-2"></i> 이용약관 및 개인정보보호방침</a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="javascript:void(0);" onclick="docPopup('privacy');">개인정보보호방침</a>
                <a class="dropdown-item" href="javascript:void(0);" onclick="docPopup('use');">이용약관</a>
<!--                 <div class="dropdown-divider"></div> -->
            </div>
        </div>
    </div>
</div>

<script>
function docPopup(code){
    var title;
    var url = contextName()+'/resources/doc/';
    if(code == 'privacy'){
        title = '개인정보보호방침';
        url += 'privacy_policy.html'; 
    }else if(code == 'use'){
        title = '이용약관';
        url += 'terms_of_use.html';
    }
    
    var options = {
        'title':title,
        'url':url,
        'width':'1000',
        'height':'890',
        'buttonDisplay':false
    };
    $.INS.popup('docPopup', options);
}
</script>