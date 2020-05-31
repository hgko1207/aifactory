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
<link href="${contextName}/resources/limitless/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/bootstrap_limitless.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/layout.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/components.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/colors.min.css" rel="stylesheet" type="text/css">
<!-- /global stylesheets -->

<link href="${contextName}/resources/css/user.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
// page context 
function contextName(){
    return "${contextName}";
}
</script>

<!-- Core JS files -->
<script src="${contextName}/resources/limitless/js/main/jquery.min.js"></script>
<script src="${contextName}/resources/limitless/js/main/bootstrap.bundle.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/loaders/blockui.min.js"></script>
<!-- Core JS files -->

<!-- Theme JS files -->
<script src="${contextName}/resources/limitless/js/plugins/forms/validation/validate.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/forms/styling/uniform.min.js"></script>

<script src="${contextName}/resources/limitless/js/app.js"></script>
<!-- /theme JS files -->

<script src="${contextName}/resources/js/login.js"></script>

<script type="text/javascript">
$( document ).ready(function() {
    if( "${securityexceptionmsg}" == "Bad credentials" ){
        alert("로그인 ID나 비밀번호가 일치하지 않습니다.");
    } else if( "${securityexceptionmsg}".indexOf("Could not open JDBC Connection for transaction") > -1){
        alert("데이터 베이스 연결 오류가 발생하였습니다.");
    } else if( "${securityexceptionmsg}" != "" ){
        alert("알 수 없는 오류가 발생하였습니다.");
    }
});
</script>
</head>
<body class="bg-slate-800">
    <!-- Page content -->
    <div class="page-content">
        <div class="content-wrapper">
            <div class="content d-flex justify-content-center align-items-center">

                <!-- Login form -->
                <form class="form-validate wmin-sm-400" action="${contextName}/j_spring_security_check" method="POST">
                    <div class="card mb-0">
                        <div class="card-body">
                            <div class="text-center mb-3">
                                <i class="icon-reading icon-2x text-slate-300 border-slate-300 border-3 rounded-round p-3 mb-3 mt-1"></i>
                                <h3 class="mb-1">계정 로그인</h3>
                                <span class="d-block text-muted">자격 증명</span>
                            </div>

                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <input type="email" name="loginid" class="form-control" placeholder="이메일" required="required" />
                                <div class="form-control-feedback">
                                    <i class="icon-mail-read text-muted"></i>
                                </div>
                            </div>

                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <input type="password" class="form-control" placeholder="비밀번호" autocomplete="off" name="loginpwd" required>
                                <div class="form-control-feedback">
                                    <i class="icon-lock2 text-muted"></i>
                                </div>
                            </div>

                            <div class="form-group d-flex align-items-center">
                                <div class="form-check mb-0">
                                    <label class="form-check-label"> <input type="checkbox" name="remember-me" class="form-input-styled" checked> 아이디 저장
                                    </label>
                                </div>

                                <a href="#" class="ml-auto text-indigo">비밀번호 찾기</a>
                            </div>

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                            <div class="form-group">
                                <button type="submit" class="btn btn-primary btn-block">로그인<i class="icon-circle-right2 ml-2"></i>
                                </button>
                            </div>

                            <div class="form-group text-center text-muted content-divider">
                                <span class="px-2">계정이 없습니까?</span>
                            </div>

                            <div class="form-group">
                                <a href="${contextName}/user/insert.do" class="btn bg-grey btn-block"> 회원가입<i class="icon-circle-right2 ml-2"></i>
                                </a>
                            </div>
                            <hr>
                            <div class="form-group mb-0">
                                <a href="javascript:history.back(-1);" class="btn btn-light btn-block">취 소</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>