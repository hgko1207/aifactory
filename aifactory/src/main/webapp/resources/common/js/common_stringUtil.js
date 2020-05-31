String.prototype.fill = function(len){
    var s = '', i = 0; 
    while (i++ < len) { 
        s += this; 
    } return s;
};

String.prototype.zf = function(len){
    return "0".fill(len - this.length) + this;
};

/*********************************************************
 * LPAD 처리
 *********************************************************/
String.prototype.lpad = function(padString, length) {
    var str = this;
    while (str.length < length)
        str = padString + str;
    return str;
}

/**
 * Convert a string to Camel Case (removing non alphabetic characters).
 */
String.prototype.toCamelCase = function() {
    var str = this;
    return str.replace(/^([A-Z])|[\s-_]+(\w)/g, function(match, p1, p2, offset) {
        if (p2) return p2.toUpperCase();
        return p1.toLowerCase();
    });
}

/**
 * Convert a string to Decamel Case (removing non alphabetic characters).
 */
String.prototype.toDeCamelCase = function(separator){
    var str = this;
    separator = typeof separator === 'undefined' ? '_' : separator;
    
    return str
        .replace(/([a-z\d])([A-Z])/g, '$1' + separator + '$2')
        .replace(/([A-Z]+)([A-Z][a-z\d]+)/g, '$1' + separator + '$2')
        .toLowerCase();
}

/**
 * Convert a string to Pascal Case (removing non alphabetic characters).
 */
String.prototype.toPascalCase = function () {
  return this.match(/[a-z]+/gi)
    .map(function (word) {
      return word.charAt(0).toUpperCase() + word.substr(1).toLowerCase()
    })
    .join('')
}

function isEmpty(value){ 
    if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){ 
        return true 
    }else{ 
        return false 
    } 
}