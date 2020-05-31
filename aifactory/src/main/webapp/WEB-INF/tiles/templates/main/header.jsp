<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/resources/taglib.jsp"%>

<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<sec:authentication property="principal" var="user"></sec:authentication>

<script>

</script>

<div class="page-header">
    <!-- Main navbar -->
    <div class="navbar navbar-expand-md navbar-dark border-transparent">
       
        <div class="d-md-none" style="padding:0px 12px">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-mobile">
                <img src="${contextName}/resources/images/menu.png" width="20" height="15">
            </button>
        </div>
        <div class="navbar-brand wmin-0 ">
            <a href="${pageContext.request.contextPath}/task/search.do" class="d-inline-block">
                <img src="${pageContext.request.contextPath}/resources/images/logo.png" class="my-1">
            </a>
        </div>
       

      
        <div class="collapse navbar-collapse" id="navbar-mobile">
            <a href="${contextName}/task/search.do?mode=all" style="color:white"><h1 class="header-category">태스크</h1></a>
            <a href="${contextName}/community/search.do?mode=all" style="color:white"><h1 class="font-weight-semibold header-category">커뮤니티</h1></a>
            <a href="${contextName}/about/search.do?mode=all" style="color:white"><h1 class="font-weight-semibold header-category">더보기</h1></a>
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
                        <a href="${contextName}/login/login.do" class="btn bg-info">&nbsp;로그인&nbsp;</a>
                        <a href="${contextName}/user/insert.do" class="btn bg-secondary">&nbsp;회원가입&nbsp;</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item dropdown dropdown-user ml-md-4">
                        <a href="#" class="navbar-nav-link d-flex align-items-center dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            <span>${user.name}</span>
                        </a>

                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:openPwdChkModal();" class="dropdown-item">
                                <i class="icon-user-plus"></i> 사용자 프로필
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="${contextName}/j_spring_security_logout" class="dropdown-item">
                                <i class="icon-switch2"></i> 로그아웃
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
                            <label>Password</label>
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
</script>