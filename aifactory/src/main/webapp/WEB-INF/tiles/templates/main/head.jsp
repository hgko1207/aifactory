<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/resources/taglib.jsp"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no" />


<script type="text/javascript">
var contextPath = "${pageContext.request.contextPath}";

//page context 
function contextName(){
	return "${contextName}";
}


</script>

<!-- Global stylesheets -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/icons/fontawesome/styles.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/bootstrap_limitless.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/layout.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/components.min.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/limitless/css/colors.min.css" rel="stylesheet" type="text/css">
<!-- /global stylesheets -->

<!-- TUI Editor -->
<link rel="stylesheet" href="${contextName}/resources/codemirror/lib/codemirror.css">
<link rel="stylesheet" href="${contextName}/resources/highlight.js/styles/github.css">
<link rel="stylesheet" href="${contextName}/resources/tui-editor/dist/tui-editor.css">
<link rel="stylesheet" href="${contextName}/resources/tui-editor/dist/tui-editor-contents.css">

<!-- jQuery-UI CSS -->
<link rel="stylesheet" href="${contextName}/resources/jquery-ui-1.12.1/jquery-ui.min.css" />

<link href="${contextName}/resources/css/common.css" rel="stylesheet" type="text/css">
<link href="${contextName}/resources/css/validation.css" rel="stylesheet" type="text/css">

<!-- Core JS files -->
<script src="${contextName}/resources/limitless/js/main/jquery.min.js"></script>
<script src="${contextName}/resources/limitless/js/main/bootstrap.bundle.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/loaders/blockui.min.js"></script>
<!-- /core JS files -->

<!-- Theme JS files -->
<script src="${contextName}/resources/limitless/js/plugins/forms/validation/validate.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/forms/styling/uniform.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/tables/datatables/datatables.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/forms/selects/select2.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/uploaders/fileinput/fileinput.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/notifications/sweet_alert.min.js"></script>

<script src="${contextName}/resources/limitless/js/app.js"></script>

<script src="${contextName}/resources/limitless/js/plugins/ui/moment/moment.min.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/pickers/daterangepicker.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/pickers/pickadate/picker.js"></script>
<script src="${contextName}/resources/limitless/js/plugins/pickers/pickadate/picker.date.js"></script>

<!-- /theme JS files -->

<script src="https://cdn.ckeditor.com/4.11.3/standard/ckeditor.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/showdown/1.9.0/showdown.min.js"></script>
<script src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>

<!-- jquery-ui -->
<script src="${contextName}/resources/jquery-ui-1.12.1/jquery-ui.min.js"></script>

<script src="${contextName}/resources/js/common.js"></script>
<script src="${contextName}/resources/js/table.js"></script> 

<script src="${contextName}/resources/common/js/common.js"></script>
<script src="${contextName}/resources/common/js/common_popup.js"></script>
<script src="${contextName}/resources/common/js/common_ajax.js"></script>
<script src="${contextName}/resources/common/js/common_modal.js"></script>
<script src="${contextName}/resources/common/js/common_stringUtil.js"></script>
<script src="${contextName}/resources/common/js/common_file.js"></script>

<!-- favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" />





<%-----------------------------------------------------------------------------------------
-- CSS                                                                                 
<%@ include file="/resources/csslib.jsp"%>
------------------------------------------------------------------------------------------%>

<%-----------------------------------------------------------------------------------------
-- SCRIPT                                                                                 
<%@ include file="/resources/scriptlib.jsp"%>
------------------------------------------------------------------------------------------%>