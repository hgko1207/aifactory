<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>

<!doctype html>
<html lang="ko">
<head>
    <tiles:insertAttribute name="head" />
    <title><tiles:getAsString name="title" /></title>
</head>

<body>
    <div class="container-fluid p-1">
        <tiles:insertAttribute name="body-middle" />
    </div>
</body>
</html>