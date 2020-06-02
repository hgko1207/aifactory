<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>

<!doctype html>
<html lang="ko">
<head>
    <tiles:insertAttribute name="head" />
    <title><tiles:getAsString name="title" /></title>
</head>

<body>
    <tiles:insertAttribute name="header" />
    
    <div class="page-content">
        <div class="content-wrapper">
            <tiles:insertAttribute name="body-middle" />
        </div>
    </div>
    
    <tiles:insertAttribute name="footer" />
</body>
</html>