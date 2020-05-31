<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<div class="page-header page-header-dark">
    <div class="page-header-content bg-blue-700 header-elements-inline">
        <div class="container">
            <div class="page-title">
                <h1 class="font-weight-semibold">Main</h1>
            </div>
        </div>
    </div>
</div>

<div class="content container">
    <div class="list-group">
        <a href="${contextName}/task/search.do" class="list-group-item">Task</a>
        <a href="${contextName}/task/search.do" class="list-group-item">Leaderboard</a>
    </div>
</div>