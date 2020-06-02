<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/resources/taglib.jsp"%>

<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<sec:authentication property="principal" var="user"></sec:authentication>

<div class="page-header">
    <div class="navbar navbar-expand-md navbar-dark border-transparent">
    	<div class="d-md-none" style="padding:0px 12px">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-mobile" aria-expanded="true">
                <i class="icon-paragraph-justify3"></i>
            </button>
        </div>
		
        <div class="navbar-brand wmin-0 mr-5">
            <a href="${pageContext.request.contextPath}/task/search.do" class="d-inline-block">
                <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="">
            </a>
        </div>
      
        <div class="collapse navbar-collapse" id="navbar-mobile">
        	<ul class="navbar-nav navbar-nav-highlight">
				<li id="task" class="nav-item">
					<a href="${contextName}/task/search.do?mode=all" class="navbar-nav-link">
						<span class="font-weight-bold px-1">태스크</span>
					</a>
				</li>
				<li id="community" class="nav-item">
					<a href="${contextName}/community/search.do?mode=all" class="navbar-nav-link">
					 	<span class="font-weight-bold px-1">커뮤니티</span>
				 	</a>
				</li>
				<li id="about" class="nav-item">
					<a href="${contextName}/about/search.do?mode=all" class="navbar-nav-link">
						<span class="font-weight-bold px-1">더보기</span>
					</a>
				</li>
			</ul>
        </div>
       
        <div class="navbar-collapse search-login-left"> 
<!--             <ul class="navbar-nav navbar-nav-highlight"> -->
<!--                 <li id="main" class="nav-item"> -->
<%--                     <a href="${pageContext.request.contextPath}/task/search.do" class="navbar-nav-link"> --%>  
<!--                         <i class="icon-list mr-3"></i>&nbsp;태스크 전체 리스트 조회 -->
<!--                     </a> -->
<!--                 </li> -->
<!--             </ul> -->
            
<!--             <ul class="navbar-nav navbar-nav-highlight"> -->
<!--                 <li id="sub" class="nav-item"> -->
<%--                     <a href="${pageContext.request.contextPath}/task/insert.do" class="navbar-nav-link"> --%>
<!--                         <i class="icon-list mr-3"></i>&nbsp;태스크 생성 -->
<!--                     </a> -->
<!--                 </li> -->
<!--             </ul> -->
            
            <ul class="navbar-nav ml-md-auto search-login">
                <li class="nav-item search-mobile" >
                    <form action="#">
                        <div class="form-group form-group-feedback form-group-feedback-right">
                            <input type="search" class="form-control bg-light-alpha border-transparent wmin-md-100" placeholder="검색">
                            <div class="form-control-feedback">
                                <i class="icon-search4 font-size-sm"></i>
                            </div>
                        </div>
                    </form>
                </li>
                <sec:authorize access="isAnonymous()">
                    <li class="nav-item ml-md-4 login-logout">
                        <a href="${contextName}/login/login.do" class="btn bg-info px-3 mr-1">로그인</a>
                        <button type="button" id="signUpBtn" class="btn bg-secondary px-3">회원가입</button>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item dropdown dropdown-user ml-md-4">
                        <a href="#" class="navbar-nav-link d-flex align-items-center dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            <span>${user.name}</span>
                        </a>

                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:openPwdChkModal();" class="dropdown-item">
                                <i class="icon-user-plus mr-2"></i>사용자 프로필
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="${contextName}/j_spring_security_logout" class="dropdown-item">
                                <i class="icon-switch2 mr-2"></i>로그아웃
                            </a>
                        </div>
                    </li>
                </sec:authorize>    
            </ul>
        </div>
    </div>
</div>

<!-- Password Check Modal Pop -->
<div class="modal fade" id="pwdChkModal" tabindex="-1" role="dialog" aria-labelledby="pwdChkModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Please enter a password.</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form name="frmPwdChk" id="frmPwdChk">
                    <input type="hidden" name="userPwd" />
                    <div class="d-inline-flex" style="width: 100%">
                        <div class="col-3">
                            <label class="form-control">Password</label>
                        </div>
                        <div class="form-group col-9 form-group-feedback input-group">
                            <input name="pwd" id="pwd" type="password" class="form-control" />
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closePwdChkModal();">Close</button>
                <button type="button" class="btn btn-primary" onclick="chkPwd();">Submit</button>
            </div>
        </div>
    </div>
</div>

<script>
var pathName = this.location.pathname;
var menuName = pathName.split("/")[2];
if(menuName==""){
    $("li#" + "main").children().addClass("active");
}else{
    $("li#" + menuName).children().addClass("active");
}

// Password Check Modal Open
function openPwdChkModal(){
    $('#pwdChkModal').modal('show');
}
function closePwdChkModal(){
    $('#frmPwdChk')[0].reset();
    $('#pwdChkModal').modal('hide');
}
function chkPwd(){
    $.ajax_form({
        url:'<c:url value="/user/chkPwd.do" />',
        formId:'frmPwdChk',
        success: function (data) {
            $('input[name="userPwd"]').val($('input[name="pwd"]').val());
            $("#frmPwdChk").attr("action", "${contextName}/user/updateForm.do");
            $('#frmPwdChk').submit();
        }
    });
}

$("#signUpBtn").click(function() {
	swalInit.fire({
        title: '회원가입 유형을 선택하세요!',
        type: 'info',
        showCancelButton: true,
        confirmButtonText: '개인회원',
        cancelButtonText: '&nbsp;&nbsp;평가자&nbsp;&nbsp;',
        confirmButtonClass: 'btn btn-primary',
        cancelButtonClass: 'btn btn-success',
        width: '30%'
    }).then(function(result) {
    	 if (result.value) {
    		 console.log(111);
    		 location.href=contextPath + "/user/insert.do?type=USER";
    	 } else {
    		 console.log(222);
    		 location.href=contextPath + "/user/insert.do?type=RATER";
    	 }
    });
});
</script>