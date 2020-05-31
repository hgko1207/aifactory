<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<%-----------------------------------------------------------------------------------------
    -- CSS                                                                                 
    ------------------------------------------------------------------------------------------%>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/err/error.css' />">
    
    <script type="text/javascript">
    function fn_back(){
        history.back();
    }

    function fn_goHome() {
        location.href = '<c:out value = "${contextName}"/>/task/search.do';
    }
    </script>
    
</head>
<body>
	<div class="wrapper">
        <header>
            <div class="topR">
            </div>
        </header>
        <div class="error_page" style="width:1000px">
            <strong>서버 프로그램에서 예기치 않은 오류가 발생했습니다.</strong>
            <p>서버 점검 중 이거나 서버 내부 오류 문제일 수 있습니다.</p>
            <p>잠시후 다시 시도해보십시오.</p>
            <p class="call_number">관련 문의사항은 고객센터(042-862-2737)로 문의하시면 안내해 드리겠습니다.<br>감사합니다.</p>
            <div class="error_btn">
                <button type="button" class="default" onclick="fn_back();">이전페이지로 가기</button>
                <button type="button" class="blue" onclick="fn_goHome();">홈으로 가기</button>
            </div>
        </div>
    </div>
</body>
</html>
