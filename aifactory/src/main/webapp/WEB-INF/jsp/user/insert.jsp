<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<title>AI Factory</title>
	
	<c:set var="contextName">${pageContext.request.contextPath}</c:set>
	
	<!-- Global stylesheets -->
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
	<link href="${contextName}/resources/limitless/css/icons/icomoon/styles.min.css" rel="stylesheet" type="text/css">
	<link href="${contextName}/resources/limitless/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${contextName}/resources/limitless/css/bootstrap_limitless.min.css" rel="stylesheet" type="text/css">
	<link href="${contextName}/resources/limitless/css/layout.min.css" rel="stylesheet" type="text/css">
	<link href="${contextName}/resources/limitless/css/components.min.css" rel="stylesheet" type="text/css">
	<link href="${contextName}/resources/limitless/css/colors.min.css" rel="stylesheet" type="text/css">
	<!-- /global stylesheets -->
	
	<!-- jQuery-UI CSS -->
	<link rel="stylesheet" href="${contextName}/resources/jquery-ui-1.12.1/jquery-ui.min.css" />
	
	<link href="${contextName}/resources/css/common.css" rel="stylesheet" type="text/css">
	<link href="${contextName}/resources/css/user.css" rel="stylesheet" type="text/css">
	
	<!-- Core JS files -->
	<script src="${contextName}/resources/limitless/js/main/jquery.min.js"></script>
	<script src="${contextName}/resources/limitless/js/main/bootstrap.bundle.min.js"></script>
	<script src="${contextName}/resources/limitless/js/plugins/loaders/blockui.min.js"></script>
	<!-- Core JS files -->
	
	<!-- Theme JS files -->
	<script src="${contextName}/resources/limitless/js/plugins/ui/moment/moment.min.js"></script>
	<script src="${contextName}/resources/limitless/js/plugins/pickers/daterangepicker.js"></script>
	<script src="${contextName}/resources/limitless/js/plugins/pickers/pickadate/picker.js"></script>
	<script src="${contextName}/resources/limitless/js/plugins/pickers/pickadate/picker.date.js"></script>
	
	<script src="${contextName}/resources/limitless/js/plugins/forms/validation/validate.min.js"></script>
	<script src="${contextName}/resources/limitless/js/plugins/forms/styling/uniform.min.js"></script>
	
	<script src="${contextName}/resources/limitless/js/app.js"></script>
	<!-- jquery-ui -->
	<script src="${contextName}/resources/jquery-ui-1.12.1/jquery-ui.min.js"></script>
	<!-- /theme JS files -->
	
	<script src="${contextName}/resources/common/js/common.js"></script>
	<script src="${contextName}/resources/common/js/common_popup.js"></script>
	
	<script src="${contextName}/resources/js/plugins/jquery.backstretch.min.js"></script>
	
	<script src="${contextName}/resources/js/signup.js"></script>
	
	<script type="text/javascript">
	// page context 
	function contextName() {
	    return "${contextName}";
	}
	
	document.addEventListener('DOMContentLoaded', function() {
		$.backstretch([
	    	contextName() + "/resources/images/bg1.png",
	    	contextName() + "/resources/images/bg2.jpg",
	    	contextName() + "/resources/images/bg3.jpg",
	    ], {duration: 3000, fade: 750});
	});
	</script>
<style>
.row {
    margin-right: 0;
}

.daterangepicker select.yearselect {
    width: 42%;
}

.checkbox input[type="checkbox"] {
    float: left;
    margin-top:3px;
    margin-left: 5px;
    margin-right: 5px;
}
</style>

<script>
$(document).ready(function() {
    $('#brthdy').daterangepicker({
        "singleDatePicker" : true,
        "autoUpdateInput" :  true,
        "showDropdowns" : true,
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
    });
    
    $('#brthdy').val('');
});

function docPopup(code){
    var title;
    var url = contextName()+'/resources/doc/';
    if (code == 'privacy') {
        title = '개인정보보호방침';
        url += 'privacy_policy.html'; 
    } else if (code == 'use') {
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
</head>
<body>
    <div class="page-content">
        <div class="content-wrapper">
            <div class="content d-flex justify-content-center align-items-center">
                <form:form commandName="user" cssClass="form-validate wmin-sm-500" action="${contextName}/user/insert.do" method="post">
                    <div class="card mb-0">
                        <div class="card-body">
                            <div class="text-center mb-3">
                                <i class="icon-plus3 icon-2x text-success border-success border-3 rounded-round p-3 mb-3 mt-1"></i>
                                <h3 class="mb-0">${type.name} 회원가입</h3>
                                <span class="d-block text-muted">모든 항목을 채워야합니다</span>
                            </div>
                            <div class="form-group text-center text-muted content-divider">
                                <span class="px-2">기본 정보</span>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:input path="userNm" type="text" cssClass="form-control" placeholder="성명" required="required" />
                                <div class="form-control-feedback">
                                    <i class="icon-user-check text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:input path="userEmail" type="email" cssClass="form-control" placeholder="이메일" required="required" />
                                <div class="form-control-feedback">
                                    <i class="icon-mail-read text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:input path="userPwd" type="password" cssClass="form-control" placeholder="비밀번호" required="required" />
                                <div class="form-control-feedback">
                                    <i class="icon-user-lock text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <input type="password" class="form-control" placeholder="비밀번호 확인" name="confirmPassword" required>
                                <div class="form-control-feedback">
                                    <i class="icon-user-lock text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:input path="mbtlnum" type="tel" cssClass="form-control" placeholder="이동전화번호" required="required" />
                                <div class="form-control-feedback">
                                    <i class="icon-phone text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:select path="sexdstn" cssClass="form-control" required="required" style="color:#999;">
                                    <form:option value="" label="성별" />
                                    <form:option value="M" label="MALE" />
                                    <form:option value="F" label="FEMALE" />
                                </form:select>
                                <div class="form-control-feedback">
                                    <i class="icon-man-woman text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:input path="brthdy" type="text" cssClass="form-control" placeholder="생년월일" required="required" />
                                <div class="form-control-feedback">
                                    <i class="icon-gift text-muted"></i>
                                </div>
                            </div>
                            <div class="checkbox align-middle">
                                <input type="checkbox"  required="required" id="agreeTerm" name="agreeTerm" value="Y"/>
                                <span>I accept the 
                                <a href="javascript:void(0);" onclick="docPopup('use');">Terms of Use</a> &amp; 
                                <a href="javascript:void(0);" onclick="docPopup('privacy');">Privacy Policy.</a></span>
                                <label id="agreeTerm-error" class="validation-invalid-label" for="agreeTerm"></label>
                            </div>
                            <div class="form-group text-center text-muted content-divider">
                                <span class="px-2"></span>
                            </div>
                            
                            <input type="hidden" value="${type}" name="role">

                            <button type="submit" class="btn bg-dark btn-block">회원 등록<i class="icon-circle-right2 ml-2"></i></button>

                            <a href="javascript:history.back(-1);" class="btn btn-light btn-block mt-2">취 소<i class="icon-circle-left2 ml-2"></i></a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>