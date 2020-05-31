<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<script type="text/javascript">
$( document ).ready(function() {
    $( '#notebook-lnav-tab a' ).on( 'click', function () {
        $( '#notebook-lnav-tab' ).find( 'a.active' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
    });
});
</script>

<div class="container-fruid border-bottom border-dark">
    <div class="row mt-2 mr-2 mb-0 ml-2">
        <div class="col">
            <ul class="list-unstyled">
                <li class="media">
                    <img src="<c:url value='/resources/common/images/avatars/avatar_1.png' />" class="mr-3 border border-dark" alt="...">
                    <div class="media-body">
                        <h5>Test Notebook 1</h5>
                        blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~ blah~
                    </div>
                </li>
            </ul>
        </div>
        <div class="col-3 text-right align-middle">
            <button type="button" class="btn btn-info">Copy and Edit</button>
        </div>
    </div>
</div>

<div class="container mt-3">
    
    <div class="row">
        <div class="col-3" id="sticky-sidebar">
            <div class="sticky-top pt-5">
                <div class="nav flex-column" id="notebook-lnav-tab">
                    <a class="nav-link active" href="#top">Notebook</a>
                    <a class="nav-link" href="#data">Data</a>
                    <a class="nav-link" href="#output">Output</a>
                    <a class="nav-link" href="#comments">Comments</a>
                </div>
            </div>
        </div>
        <div class="col" id="main">
            <div class="tab-content" id="notebook-tabContent">
                <div id="notebook">
                    Notebook
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
                <div id="data">
                    Data
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
                <div id="output">
                    Output
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
                <div id="comments">
                    Comments
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
            </div>
        </div>
    </div>
    
    <!--  
    <div class="row">
        <div class="col-3">
            <div class="nav flex-column" id="notebook-lnav-tab">
                <a class="nav-link active" href="#notebook">Notebook</a>
                <a class="nav-link" href="#data">Data</a>
                <a class="nav-link" href="#output">Output</a>
                <a class="nav-link" href="#comments">Comments</a>
            </div>
        </div>
        <div class="col-9">
            <div class="tab-content" id="notebook-tabContent">
                <div id="notebook">
                    Notebook
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
                <div id="data">
                    Data
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
                <div id="output">
                    Output
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
                <div id="comments">
                    Comments
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
            </div>
        </div>
    </div>
    -->
    
<!--  
<div class="row">
  <div class="col-3">
    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
      <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true">Home</a>
      <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false">Profile</a>
      <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab" aria-controls="v-pills-messages" aria-selected="false">Messages</a>
      <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false">Settings</a>
    </div>
  </div>
  <div class="col-9">
    <div class="tab-content" id="v-pills-tabContent">
    
      <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">...</div>
      <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">...</div>
      <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">...</div>
      <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">...</div>
    </div>
  </div>
</div>
-->
</div>