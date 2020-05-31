<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<script>
function detailAction(){
	
}
</script>

<div class="container-fruid text-light  menu_banner">
    <div class="container menu_banner_inside">
        <div class="row">
            <div class="col menu_banner_inside_text">
                <h2>
                    Notebooks
                </h2>
            </div>
            <div class="col-4 menu_banner_inside_button text-right">
                <button type="button" class="btn btn-light">Create</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <ul class="list-unstyled">
        <li class="media">
            <img src="<c:url value='/resources/common/images/avatars/avatar_1.png' />" class="mr-3 border border-dark" alt="...">
            <div class="media-body">
                <h5 class="mt-0 mb-1"><a href="<c:url value='/notebook/detail.do'/>">Test Notebook 1</a></h5>
                blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ 
            </div>
        </li>
        <li class="media my-4">
            <img src="<c:url value='/resources/common/images/avatars/avatar_2.png' />" class="mr-3 border border-dark" alt="...">
            <div class="media-body">
                <h5 class="mt-0 mb-1"><a href="<c:url value='/notebook/detail.do'/>">Test Notebook 1</a></h5>
                blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ 
            </div>
        </li>
        <li class="media">
            <img src="<c:url value='/resources/common/images/avatars/avatar_3.png' />" class="mr-3 border border-dark" alt="...">
            <div class="media-body">
                <h5 class="mt-0 mb-1"><a href="<c:url value='/notebook/detail.do'/>">Test Notebook 1</a></h5>
                blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ 
            </div>
        </li>
    </ul>
</div>