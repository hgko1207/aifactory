<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
    trimDirectiveWhitespaces="true"%>
<c:set var="contextName">${pageContext.request.contextPath}</c:set>

<script type="text/javascript">
function contextName(){
    return "${contextName}";
}
</script>

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
