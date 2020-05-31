<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<!-- TUI Editor -->
<script src="${contextName}/resources/tui-editor/dist/tui-editor-Viewer-full.js"></script>
<script src="${contextName}/resources/common/js/common_tuiEditor.js"></script>

<script>
$( document ).ready(function() {
    new tui.Editor({
        el: document.querySelector('#postDc'),
        height: '450px',
        initialValue:unescape('${post.postDc}')
    });
  // 수정 Form
    $('#btnUpdateForm').click(function(){	
        $('#post').submit();
    });
});

</script>
<div class="card overview">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-cube3 mr-2"></i>Post
        </h5>
    </div>
    <div class="card-body">
        <div class="d-md-flex">
            <div class="container">
                <!-- Left Menu
                <ul class="nav nav-tabs nav-tabs-vertical flex-column mr-md-3 wmin-md-250 mb-md-0 border-bottom-0">
                    <li class="nav-item"><a href="#basic-tab" class="nav-link active show" data-toggle="tab">Basic</a></li>
                    <li class="nav-item"><a href="#description-tab" class="nav-link" data-toggle="tab">Description</a></li>
                </ul>
                -->

                <div class="tab-content">
                    <form:form commandName="post" method="POST" cssClass="form-validate" action="${contextName}/community/updateForm">
                       <form:input path="postId" type="hidden"/>
                        <div class="tab-pane fade active show" id="basic-tab">
                            <div id="basic-content" class="markdown-converter__text--rendered">
                                <div class="card-body row ml-3">
                                    <div class="d-inline-flex" style="width: 100%">
                          
                                        <div class="form-group form-group-feedback input-group">
                                            <h1>${post.postNm}</h1>
                                        </div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                  
                                        <div id="postDc" class="form-group form-group-feedback input-group"></div>
                                    </div>
                                    <div class="d-inline-flex" style="width: 100%">
                                    
                                        <div class="form-group form-group-feedback input-group">
                                            <c:if test="${!empty post.cmmnFile && !empty post.cmmnFile.files}">
                                                <img src="${contextName}/download/byNameStream/${post.cmmnFile.files[0].fileStreCours}/${post.cmmnFile.files[0].streFileNm}/" width="80"/>
                                            </c:if>
                                        </div>
                                    </div>
                                  
                                    <div id="errArea"></div>
                                    <sec:authorize access="isAuthenticated()">
                                        <sec:authentication property="principal.username" var="loginUserId"/>
                                    </sec:authorize>
                                    <c:choose>
                                        <c:when test="${loginUserId == post.register.userId}">
                                            <div class="col-lg-11 text-right">
                                                <button type="button" id="btnUpdateForm" class="btn btn-primary">
                                                    Edit<i class="icon-paperplane ml-2"></i>
                                                </button>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <sec:authorize access="hasRole('ROLE_ADMN')">
                                                <div class="col-lg-11 text-right">
                                                    <button type="button" id="btnUpdateForm" class="btn btn-primary">
                                                        Edit<i class="icon-paperplane ml-2"></i>
                                                    </button>
                                                </div>
                                            </sec:authorize>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="description-tab">
                            <div id="description-content" class="markdown-converter__text--rendered"></div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>