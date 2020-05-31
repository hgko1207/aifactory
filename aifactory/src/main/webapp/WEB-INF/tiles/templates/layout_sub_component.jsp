<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>

<!doctype html>
<html lang="ko">
<head>
    <tiles:insertAttribute name="head" />
    <title><tiles:getAsString name="title" /></title>
    <!-- open graph dynamic transfer-->
    <c:if test="${!empty task.cmmnFile && !empty task.cmmnFile.files && !empty task.taskNm}">
        <meta property="og:title" content="${task.taskNm}">
        <meta property="og:image" content="http://aifactory.space/download/byNameStream/${task.cmmnFile.files[0].fileStreCours}/${task.cmmnFile.files[0].streFileNm}/">
    </c:if>
    <c:if test="${!empty post.cmmnFile && !empty post.cmmnFile.files && !empty post.postNm}">
        <meta property="og:title" content="${post.postNm}">
        <meta property="og:image" content="http://aifactory.space/download/byNameStream/${post.cmmnFile.files[0].fileStreCours}/${post.cmmnFile.files[0].streFileNm}/">
    </c:if>
</head>

<body>
    <tiles:insertAttribute name="header" />
    <div class="page-content">  
        <div class="content-wrapper">
            <div class="container">
                <tiles:insertAttribute name="subHeader"/>
                <tiles:insertAttribute name="body-middle" />
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer" />
</body>
</html>