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
        <div class="error_page">
            <strong>요청하신 페이지를 찾을 수 없습니다.</strong>
            <p>페이지의 주소가 잘못 입력되었거나, 주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.</p>
            <p>입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.</p>
            <p class="call_number">관련 문의사항은 고객센터(042-862-2737)로 문의하시면 안내해 드리겠습니다.<br>감사합니다.</p>
            <div class="error_btn">
                <button type="button" class="default" onclick="fn_back();">이전페이지로 가기</button>
                <button type="button" class="blue" onclick="fn_goHome();">홈으로 가기</button>
            </div>
        </div>
    </div>
</body>
</html>
