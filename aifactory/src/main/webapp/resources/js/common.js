

/*******************************************************************************************************
 * 위젯 공통 기능
*******************************************************************************************************/
var CommonWidget = function() {
	var _componentSelect2 = function() {
		if (!$().select2) {
        	console.warn('Warning - select2.min.js is not loaded.');
            return;
        }
    	
    	// Initialize
        var $select = $('.form-control-select2').select2({
            minimumResultsForSearch: Infinity,
            width: '100%'
        });
        
        // Trigger value change when selection is made
        $select.on('change', function() {
            $(this).trigger('blur');
        });
    };
    
    var _componentForm = function() {
    	/** form 데이터들을 JSON 형식으로 변환 */
    	jQuery.fn.serializeObject = function() { 
    		var obj = null; 
    		try { 
    			if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) { 
				var arr = this.serializeArray(); 
				if(arr){ 
					obj = {}; 
					jQuery.each(arr, function() { 
						obj[this.name] = this.value; }); 
					} 
				} 
			} catch(e) { 
				alert(e.message); 
			} finally {
				
			} 
			return obj; 
		}
    };
    
	return {
        init: function() {
        	_componentSelect2();
        	_componentForm();
        }
    }
}();

document.addEventListener('DOMContentLoaded', function() {
	CommonWidget.init();
});

var swalInit = swal.mixin({
    buttonsStyling: false,
    confirmButtonClass: 'btn btn-primary',
    cancelButtonClass: 'btn btn-light'
});

/*******************************************************************************************************
 * 수식 표시 기능
*******************************************************************************************************/
/*MathJax.Hub.Config({
    "HTML-CSS": {
        preferredFont: "TeX",
        availableFonts: ["STIX", "TeX"],
        linebreaks: {
            automatic: true
        },
        EqnChunk: (MathJax.Hub.Browser.isMobile ? 10 : 50)
    },
    tex2jax: {
        inlineMath: [["\\(", "\\)"], ["\\\\(", "\\\\)"]],
        displayMath: [["$$", "$$"], ["\\[", "\\]"]],
        processEscapes: true,
        ignoreClass: "tex2jax_ignore|dno"
    },
    TeX: {
        noUndefined: {
            attributes: {
                mathcolor: "red",
                mathbackground: "#FFEEEE",
                mathsize: "90%"
            }
        }
    },
    Macros: {
        href: "{}"
    },
    skipStartupTypeset: false,
    messageStyle: "none"
});*/

/*MathJax.Hub.Config({
    tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}
});*/

/** 버퍼 변환 */
function base64ToArrayBuffer(base64) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
       var ascii = binaryString.charCodeAt(i);
       bytes[i] = ascii;
    }
    return bytes;
}

function fileDownload(data) {
  	var file = base64ToArrayBuffer(data.content);
   	var a = document.createElement('a');
   	a.href = window.URL.createObjectURL(new Blob([file]));
   	a.download = data.fileName;
   	// Firefox에서 다운로드 안되는 문제 수정용 코드
   	// (Firefox는 a가 화면에 실존할 때만 다운로드 가능)
   	document.body.appendChild(a);
   	a.click();
   	document.body.removeChild(a); 
}

/**************************************************************************
 * jquery validate set
 **************************************************************************/
$.validator.setDefaults({
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
        label.addClass('validation-valid-label').text('Success.'); // remove to hide Success message
    }
});

/**************************************************************************
 * jquery RegEx
 **************************************************************************/
jQuery.expr[':'].regex = function(elem, index, match) {
    var matchParams = match[3].split(','),
        validLabels = /^(data|css):/,
        attr = {
            method: matchParams[0].match(validLabels) ? 
                        matchParams[0].split(':')[0] : 'attr',
            property: matchParams.shift().replace(validLabels,'')
        },
        regexFlags = 'ig',
        regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g,''), regexFlags);
    return regex.test(jQuery(elem)[attr.method](attr.property));
}

/**************************************************************************
 * Select Row Style 변경
 **************************************************************************/
document.addEventListener('DOMContentLoaded', function() {
    $(".clickable-row").click(function(){
        if($(this).hasClass("row-selected"))
            $(this).removeClass('row-selected');
        else
            $(this).addClass('row-selected').siblings().removeClass('row-selected');
    });
});
