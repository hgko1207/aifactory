/* ------------------------------------------------------------------------------
 *
 *  # Login form with validation
 *
 * ---------------------------------------------------------------------------- */

var LoginValidation = function() {
    // Uniform
    var _componentUniform = function() {
        if (!$().uniform) {
            console.warn('Warning - uniform.min.js is not loaded.');
            return;
        }

        $('.form-input-styled').uniform();
    };

    // Validation config
    var _componentValidation = function() {
        if (!$().validate) {
            console.warn('Warning - validate.min.js is not loaded.');
            return;
        }
        
        $.extend( $.validator.messages, {
        	required: "필수 항목입니다.",
        	number: "유효한 숫자가 아닙니다."
        });

        // Initialize
        var validator = $('.form-validate').validate({
            ignore: 'input[type=hidden], .select2-search__field', // ignore hidden fields
            errorClass: 'validation-invalid-label',
            successClass: 'validation-valid-label',
            validClass: 'validation-valid-label',
            highlight: function(element, errorClass) {
                $(element).removeClass(errorClass);
            },
            unhighlight: function(element, errorClass) {
                $(element).removeClass(errorClass);
            },
            success: function(label) {
                label.addClass('validation-valid-label').text('성공.'); // remove to hide Success message
            },

            // Different components require proper error label placement
            errorPlacement: function(error, element) {
                // Unstyled checkboxes, radios
                if (element.parents().hasClass('form-check')) {
                    error.appendTo(element.parents('.form-check').parent());
                }

                // Input with icons and Select2
                else if (element.parents().hasClass('form-group-feedback') || element.hasClass('select2-hidden-accessible')) {
                    error.appendTo(element.parent());
                }

                // Input group, styled file input
                else if (element.parent().is('.uniform-uploader, .uniform-select') || element.parents().hasClass('input-group')) {
                    error.appendTo(element.parent().parent());
                }

                // Other elements
                else {
                    error.insertAfter(element);
                }
            },
            rules: {
                loginid: {
                    required: true,
                    email: true
                },
                loginpwd: {
                    required: true,
                    minlength: 6
                }
            },
            messages: {
                loginid: {
                    required: "이메일을 입력하세요.",
                    email: "유효한 이메일 주소를 입력하세요."
                },
                loginpwd: {
                    required: "비밀번호를 입력하세요.",
                    minlength: jQuery.validator.format("{0} 자 이상 입력하세요.")
                }
            }
        });
    };

    return {
        init: function() {
            _componentUniform();
            _componentValidation();
        }
    }
}();

$(document).ready(function() {
    LoginValidation.init();
    
    var swalInit = swal.mixin({});
    
    $("#signUpBtn").click(function() {
    	swalInit.fire({
            title: '회원가입 유형을 선택하세요!',
            type: 'info',
            showCancelButton: true,
            confirmButtonText: '개인회원',
            cancelButtonText: '&nbsp;&nbsp;평가자&nbsp;&nbsp;',
            confirmButtonClass: 'btn btn-primary',
            cancelButtonClass: 'btn btn-success',
            width: '30%'
        }).then(function(result) {
        	 if (result.value) {
        		 console.log(111);
        		 location.href=contextPath + "/user/insert.do?type=USER";
        	 } else {
        		 console.log(222);
        		 location.href=contextPath + "/user/insert.do?type=RATER";
        	 }
        });
    });
    
	$.backstretch([
    	contextPath + "/resources/images/bg1.png",
    	contextPath + "/resources/images/bg2.jpg",
    	contextPath + "/resources/images/bg3.jpg",
    ], {duration: 3000, fade: 750});
});