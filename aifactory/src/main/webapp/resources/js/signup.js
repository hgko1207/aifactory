/* ------------------------------------------------------------------------------
 *
 *  # signup form with validation
 *
 * ---------------------------------------------------------------------------- */

var SignupValidation = function() {
    var _componentValidation = function() {
        if (!$().validate) {
            console.warn('Warning - validate.min.js is not loaded.');
            return;
        }
        
        $.extend( $.validator.messages, {
        	required: "필수 항목입니다.",
        	number: "유효한 숫자가 아닙니다."
        });
        
        $.validator.addMethod("validPhone", function(value, element) {
            value = value.replace(/\s+/g, "");
            return this.optional(element) || value.length > 9 &&
                    value.match(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/);
        }, "Please specify a valid phone number");
        
        var validator = $('.form-validate').validate({
            ignore: 'input[type=hidden], .select2-search__field', // ignore hidden fields
            errorClass: 'validation-invalid-label',
            successClass: 'validation-valid-label',
            validClass: 'validation-valid-label',
            focusCleanup: true,
            highlight: function(element, errorClass) {
                $(element).removeClass(errorClass);
            },
            unhighlight: function(element, errorClass) {
                $(element).removeClass(errorClass);
            },
            success: function(label) {
                label.addClass('validation-valid-label').text('성공.'); // remove to hide Success message
            },
            
            rules: {
                userNm: {
                    required: true,
                    minlength: 2,
                    maxlength: 30
                },
                userEmail: {
                    required: true,
                    email: true,
                    maxlength: 100,
                    remote: {
                        url: "chkDuplicateEmail.do",
                        type: "post"
                    }
                },
                userPwd: {
                    required: true,
                    minlength: 6,
                    maxlength: 100
                },
                confirmPassword: {
                    required: true,
                    equalTo: "#userPwd"
                },
                mbtlnum:{
                    required: true,
                    validPhone:true
                }
            },
            messages: {
                userNm: {
                    required: "성명을 입력하세요.",
                    minlength: jQuery.validator.format("{0}자 이상 입력하세요."),
                    maxlength: jQuery.validator.format("{0}자 이하로 입력하세요."),
                },
                userEmail: {
                    required: "이메일 주소를 입력하세요.",
                    minlength: jQuery.validator.format("{0}자 이상 입력하세요."),
                    maxlength: jQuery.validator.format("{0}자 이하로 입력하세요."),
                    remote: "이메일 주소가 이미 존재합니다.",
                    email: "유효한 이메일 주소를 입력하세요."
                },
                userPwd: {
                    required: "비밀번호를 입력하세요.",
                    minlength: jQuery.validator.format("{0}자 이상 입력하세요.")
                },
                confirmPassword: {
                    required: "비밀번호 확인을 입력하세요.",
                    minlength: "{0}자 이상 입력하세요.",
                    equalTo: "위와 동일한 비밀번호를 입력하세요."
                }
            }
        });
	 }
	
	return {
        init: function() {
            _componentValidation();
        }
    }
}();

document.addEventListener('DOMContentLoaded', function() {
	SignupValidation.init();
});