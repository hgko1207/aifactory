<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>
<script>
$( document ).ready(function() {
    $('#othbcYn').val('${taskCriterion.othbcYn}'); 
    $('#keyword').val('${taskCriterion.keyword}');
    g_mode = '${taskCriterion.mode}';
    if(g_mode == 'recruit'){
        $('#hostedTap').addClass("active");
    }else if(g_mode == 'member'){
        $('#jointedTap').addClass("active");
    }else{
        $('#allTap').addClass("active");
    }
    
    // 스크롤 하단에 도달시 다음 리스트 불러오기
    $(window).scroll(function() {
        var scrolltop = $(document).scrollTop();
        var height = $(document).height();
        var height_win = $(window).height();
        
        if (Math.round( $(window).scrollTop()) == $(document).height() - $(window).height()) {
            getData();
        }
    });
    
    search();
});
</script>
<div class="page-header page-header-dark">
    <div class="page-header-content header-elements-inline" style="background-color:#F9CA24;">
        <div class="container">
            <div class="page-title">
                  <h1 class="font-weight-semibold">About</h1>
            </div>
        </div>
    </div>
</div>
<div class="content container">
    <div class="card">
        <div class="card-body">
            <ul class="nav nav-tabs nav-tabs-highlight">
                <li class="nav-item"><a id="allTap" href="${contextName}/about/search.do?mode=news" class="nav-link">News</a></li>
                <li class="nav-item"><a id="hostedTap" href="${contextName}/about/recruit.do" class="nav-link">Recruit</a></li>
                <li class="nav-item"><a  id="jointedTap" href="${contextName}/about/search.do?mode=member" class="nav-link">Member</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade active show" id="highlighted-tab1">
                    <div class="text-right" style="margin-bottom:5px;">
                        <div class="d-inline-flex align-items-center justify-content-center">
                            <select id="othbcYn" name="othbcYn" class="form-control">
                                <option value="">All</option>
                                <option value="title">제목</option>
                                <option value="content">내용</option>
                            </select>
                            <input type="text" name="keyword" id="keyword" class="form-control ml-1" placeholder="검색"/>
                            <button id="search_button" type="button" class="btn btn-info ml-2">
                                <i class="icon-search4 mr-2"></i>검 색
                            </button>
                            <sec:authorize access="hasAnyRole('ROLE_BIZC','ROLE_ADMN')">
                                <a href="${pageContext.request.contextPath}/about/insert.do" class="btn btn-info ml-3">
                                    <i class="icon-pencil7 mr-2"></i>Create Post    
                                </a>
                            </sec:authorize>
                         </div>
                    </div>
                    <div class="p-3 font-size-16" style="background-color:#273246;color:white;">
                        <span class="pl-2" id="taskCnt"></span>
                    </div>
                    <table class="table table-hover">
                        <colgroup>
                            <col width="14%">
                            <col>
                            <col width="18%">
                        </colgroup>
                        <tbody id="taskListArea">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>