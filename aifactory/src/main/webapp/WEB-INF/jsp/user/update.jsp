<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<title>AI Factory</title>

<!-- Global stylesheets -->
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/icons/fontawesome/styles.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/bootstrap_limitless.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/layout.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/components.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/colors.min.css" rel="stylesheet" type="text/css">
<!-- /global stylesheets -->

<link href="${contextName}/resources/css/common.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/css/validation.css" rel="stylesheet" type="text/css">

<!-- Core JS files -->
<script src="${contextName}/resources/limitless/js/main/jquery.min.js"></script>
<script src="${contextName}/resources/limitless/js/main/bootstrap.bundle.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/loaders/blockui.min.js"></script>
<!-- /core JS files -->

<!-- Theme JS files -->
<script src="${contextName}/resources/limitless/js/plugins/forms/validation/validate.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/forms/styling/uniform.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/tables/datatables/datatables.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/forms/selects/select2.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/uploaders/fileinput/fileinput.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/notifications/sweet_alert.min.js"></script>

<script src="${contextName}/resources/limitless/js/app.js"></script>

<script src="${contextName}/resources/limitless/js/plugins/ui/moment/moment.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/pickers/daterangepicker.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/pickers/pickadate/picker.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/pickers/pickadate/picker.date.js"></script

<!-- /theme JS files -->

<script src="https://cdn.ckeditor.com/4.11.3/standard/ckeditor.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/showdown/1.9.0/showdown.min.js"></script>
<script src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>

<script src="${contextName}/resources/js/common.js"></script>
<script src="${contextName}/resources/js/table.js"></script> 

<script src="${contextName}/resources/common/js/common_ajax.js"></script>
<script src="${contextName}/resources/common/js/common_modal.js"></script>
<script src="${contextName}/resources/common/js/common_stringUtil.js"></script>
<script src="${contextName}/resources/common/js/common_file.js"></script>

<style>
.row {
    margin-right: 0;
}
.daterangepicker select.yearselect {
    width: 42%;
}
</style>

<fmt:formatDate value="${user.brthdy}" var="brthdy" pattern="yyyy-MM-dd" />
<script>
var userInfoModifyValidate;
$( document ).ready(function() {
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
    
    $('#brthdy').val('${brthdy}');
    
    $.validator.addMethod("validPhone", function(value, element) {
        value = value.replace(/\s+/g, "");
        return this.optional(element) || value.length > 9 &&
                value.match(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/);
    }, "Please specify a valid phone number");

    userInfoModifyValidate = $('#user').validate({
        rules: {
                userNm: {
                    required: true,
                    minlength: 2,
                    maxlength: 30
                },
                userEmail: {
                    required: true,
                    email: true,
                    maxlength: 100,
                    remote: {
                        url: "chkDuplicateEmail.do",
                        type: "post"
                    }
                },
                oldUserPwd: {
                    remote: {
                        url: "chkPwdModifyValid.do",
                        type: "post"
                    }
                },
                userPwd: {
                    minlength: 6,
                    maxlength: 100
                },
                confirmPassword: {
                    required: true,
                    equalTo: "#userPwd"
                },
                mbtlnum:{
                    required: true,
                    validPhone:true
                }
            },
            messages: {
                userNm: {
                    required: "성명을 입력하세요.",
                    minlength: jQuery.validator.format("{0}자 이상 입력하세요."),
                    maxlength: jQuery.validator.format("{0}자 이하로 입력하세요."),
                },
                userEmail: {
                    required: "이메일 주소를 입력하세요.",
                    minlength: jQuery.validator.format("{0}자 이상 입력하세요."),
                    maxlength: jQuery.validator.format("{0}자 이하로 입력하세요."),
                    remote: "이메일 주소가 이미 존재합니다.",
                    email: "유효한 이메일 주소를 입력하세요."
                },
                oldUserPwd: {
                    remote: "Incorrect Password.",
                },
                userPwd: {
                    required: "비밀번호를 입력하세요.",
                    minlength: jQuery.validator.format("{0}자 이상 입력하세요.")
                },
                confirmPassword: {
                    required: "비밀번호 확인을 입력하세요.",
                    minlength: "{0}자 이상 입력하세요.",
                    equalTo: "위와 동일한 비밀번호를 입력하세요."
                }
            },
            submitHandler: function(form){
                $.ajax_form({
                    url:'<c:url value="/user/update.do" />',
                    formId:'user',
                    success: function (data) {
                        userInfoModifyValidate.resetForm();
                        alert('Saved successfully.');
                    }
                });
            }
    });
    
    $("input[name='oldUserPwd']").keyup(function() {
        if( $("input[name='oldUserPwd']").val().length > 0 ){
            $("input[name='userPwd']").prop('disabled', false);
            $("input[name='confirmPassword']").prop('disabled', false);
        }else{
            userInfoModifyValidate.resetForm();
            $("input[name='userPwd']").val('');
            $("input[name='confirmPassword']").val('');
            $("input[name='userPwd']").prop('disabled', true);
            $("input[name='confirmPassword']").prop('disabled', true);
        }
    });
});

function updateUserInfo(){
    $('#user').submit();
}
</script>
</head>
<body class="bg-slate-800">
    <div class="page-content">
        <div class="content-wrapper">
            <div class="content d-flex justify-content-center align-items-center">
                <form:form commandName="user" cssClass="form-validate wmin-sm-500" action="${contextName}/user/update.do" method="post" onsubmit="return false;">
                    <div class="card mb-0">
                        <div class="card-body">
                            <div class="text-center mb-3">
                                <i class="icon-plus3 icon-2x text-success border-success border-3 rounded-round p-3 mb-3 mt-1"></i>
                                <h3 class="mb-0">회원정보수정</h3>
                                <span class="d-block text-muted"></span>
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
                                <input name="oldUserPwd" type="password" class="form-control" placeholder="비밀번호를 변경하시려면 기존 비밀번호를 입력하여 주십시오." />
                                <div class="form-control-feedback">
                                    <i class="icon-user-lock text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:input path="userPwd" type="password" cssClass="form-control" placeholder="새 비밀번호" disabled ="true" />
                                <div class="form-control-feedback">
                                    <i class="icon-user-lock text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <input type="password" class="form-control" placeholder="새 비밀번호 확인" name="confirmPassword" disabled ="true">
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
                                <form:select path="sexdstn" cssClass="form-control" required="required">
                                    <form:option value="" label="성별" />
                                    <form:option value="M" label="MALE" />
                                    <form:option value="F" label="FEMALE" />
                                </form:select>
                                <div class="form-control-feedback">
                                    <i class="icon-man-woman text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <input id="brthdy" name="brthdy" type="text" class="form-control" placeholder="생년월일" required="required" />
                                <div class="form-control-feedback">
                                    <i class="icon-gift text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group text-center text-muted content-divider">
                                <span class="px-2"></span>
                            </div>

                            <button type="button" onclick="updateUserInfo();" class="btn bg-dark btn-block">수정<i class="icon-circle-right2 ml-2"></i></button>

                            <a href="${contextName}/" class="btn btn-light btn-block mt-2">취 소<i class="icon-circle-left2 ml-2"></i></a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>