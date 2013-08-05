// check for XPath implementation
if (document.implementation.hasFeature("XPath", "3.0"))
{
    // prototying the XMLDocument
    XMLDocument.prototype.selectNodes = function(cXPathString, xNode)
    {
        if (!xNode) {
            xNode = this;
        }

        var oNSResolver = this.createNSResolver(this.documentElement)
        var aItems = this.evaluate(cXPathString, xNode, oNSResolver,
                XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null)
        var aResult = [];
        for (var i = 0; i < aItems.snapshotLength; i++)
        {
            aResult[i] = aItems.snapshotItem(i);
        }
        return aResult;
    }

    // prototying the Element
    Element.prototype.selectNodes = function(cXPathString)
    {
        if (this.ownerDocument.selectNodes)
        {
            return this.ownerDocument.selectNodes(cXPathString, this);
        }
        else {
            throw "For XML Elements Only";
        }
    }
}

// check for XPath implementation
if (document.implementation.hasFeature("XPath", "3.0"))
{
    // prototying the XMLDocument
    XMLDocument.prototype.selectSingleNode = function(cXPathString, xNode)
    {
        if (!xNode) {
            xNode = this;
        }
        var xItems = this.selectNodes(cXPathString, xNode);
        if (xItems.length > 0)
        {
            return xItems[0];
        }
        else
        {
            return null;
        }
    }

    // prototying the Element
    Element.prototype.selectSingleNode = function(cXPathString)
    {
        if (this.ownerDocument.selectSingleNode)
        {
            return this.ownerDocument.selectSingleNode(cXPathString, this);
        }
        else {
            throw "For XML Elements Only";
        }
    }
}



function getXMLDom() {
    return RicoUtil.createXmlDocument();
}

function getXMLHttp() {
    return Try.these(
            function() {
                return new ActiveXObject('Msxml2.XMLHTTP')
            },
            function() {
                return new ActiveXObject('Microsoft.XMLHTTP')
            },
            function() {
                return new XMLHttpRequest()
            }
            ) || false;
}
/**
 *字符串是否为空或全是空格
 */
function isBlank(str) {
    for (var i = 0; i < str.length; i++) {
        var ascii = str.charCodeAt(i);
        if (ascii != 32 && ascii != 13 && ascii != 10) return false;
    }
    return true;
}


/**
 *字符串是否全为英文字母
 */
function isAllChar(str) {
    for (var i = 0; i < str.length; i++)
        if ((str.charAt(i) < 'A' || str.charAt(i) > 'z') && (str.charAt(i) < '0' || str.charAt(i) > '9')) {
           // alert(str.charAt(i));
            return false;
        }
    return true;
}

/**
 *字符串是否全为数字
 */
function isAllNum(str) {
    for (var i = 0; i < str.length; i++)
        if (str.charAt(i) < '0' || str.charAt(i) > '9') return false;
    return true;
}

/**
 *字符串是否全为数字
 */
function isAllNumMe(str) {
	if(str.length == 0)
		return false;
    for (var i = 0; i < str.length; i++)
        if (str.charAt(i) < '0' || str.charAt(i) > '9') return false;
    return true;
}

/**
 *字符串是否为数值格式
 */
function isNum(str) {
    for (var i = 0; i < str.length; i++)
        if ((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '.') return false;
    return true;
}

/**
 *字符串是否为货币格式
 */
function isCurrency(str) {
    var offset;
    var afterpoint;
    if (!isNum(str)) return false;
    if ((offset = str.indexOf(".")) == -1) return true;
    afterpoint = str.substring(offset + 1);
    //return str.substring(offset + 1).length <= 3; 货币精确到三位小数
	return str.substring(offset + 1).length <= 2;
}

function isIP(str){
    var re=/(\d+)\.(\d+)\.(\d+)\.(\d+)/;//匹配IP地址的正则表达式
    var b = re.test(str);   
    if(b){
        for(var i = 1; i < 5; i++){
            var x = eval("RegExp.$" + i);
            if(x < 0 || x > 255) return false;
        }
        return true;
    }
    return false;
}

/**
 *弹出窗口
 */
function openwin(url, w, h) {
    var l = (screen.width - w) / 2;
    var t = (screen.height - h) / 2;
    return window.open(url, 'replace', 'top=' + t + ',left=' + l + ',width=' + w + ',height=' + h + ',scrollbars=no,resizable=no,status=no,location=no');
}
/**
 *弹出窗口
 */
function openWinLeft(url, name, w) {
    return window.open(url, name, 'top=0,left=0,width=' + w + ',height=' + (screen.height - 100) + ',scrollbars=no,resizable=no,status=no');
}

/**
 *弹出窗口,可以指定弹出窗口的名字,可以调节窗口大小
 *
 */
function OpenWin(url, name, w, h) {
    var l = (screen.width - w) / 2;
    var t = (screen.height - h) / 2;
    return window.open(url, name, 'top=' + t + ',left=' + l + ',width=' + w + ',height=' + h + ',scrollbars=no,resizable=yes,status=no');
}
function isValidCardno(value) {
    return !isBlank(value) && ((value.length == 15 && isAllNum(value)) || (value.length == 18 && isAllNum(value.substring(0, 17))));
}
/**
 *检查必填项
 */

function CheckField(ObjForm) {
	

    for (var x = 0; x < ObjForm.length; x++) {
        try {
            with (ObjForm[x]) {
//                if(ObjForm[x].disabled || ObjForm[x].style.display == 'none' || ObjForm[x].style.visibility == 'hidden' || ObjForm[x].readOnly){
//                    continue;
//                }
                if (className.indexOf('必填') >= 0 && isBlank(value)) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();  
                    }
                    return false;
                }
                if (className.indexOf('数字') >= 0 && (isBlank(value) || !isAllNum(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('数值') >= 0 && (isBlank(value) || !isNum(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('字符') >= 0 && (isBlank(value) || !isAllChar(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('整数') >= 0 && (isBlank(value) || !IsInteger(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('货币') >= 0 && (isBlank(value) || !isCurrency(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('邮件') >= 0 && (isBlank(value) || !isValidMail(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                 if (className.indexOf('IP') >= 0 && (isBlank(value) || !isIP(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('货币非必填') >= 0 && !isBlank(value) && !isCurrency(value)) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('身份证') >= 0 && !isValidCardno(value)) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('邮编') >= 0 && (isBlank(value) || value.length != 6 || !isAllNum(value))) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
                if (className.indexOf('密码') >= 0 && (isBlank(value) || value.length < 6 )) {
                    alert(title);
                    try {
                        select();
                    }
                    catch(e) {
                        focus();
                    }
                    return false;
                }
				if(className.indexOf('座机')>=0&&!isValidPhone(value)){
					  alert(title);
					  try{
					     select();	  
					}catch(e){
						  focus();
						}
					return false ;
					}
					if(className.indexOf('手机')>=0&&!isValidMobile(value)){
					  alert(title);
					  try{
					     select();	  
					}catch(e){
						  focus();
						}
					return false ;
				}
            }
        }
        catch(e) {

        }
    }
    return true;
}

/*
匹配座机号码，格式为：010-12345678或0394-2233445
*/

function isValidPhone(Phone)
{
   //	var exps = /^0\d{2}-\d{9}|0\d{3}-\d{8}$/;
   var exps = /^[0-9]{3,4}\-\d{7,8}(\(\d{1,6}\))?$/;
	return exps.test(Phone);
}

/*
匹配手机号码，格式为：
*/
function isValidMobile(Mobile)
{
	var exps = /^0?1[358][01236]\d{8}|0?1[358][0456789]\d{8}$/;
    return exps.test(Mobile);
}

/**
 *Email地址格式是否有效
 */
function isValidMail(Email) {

    var checked_status = true;
    var at_let = false;
    var point_let = false;
    var at_ps = -1;
    var str_length = Email.length - 1;
    Email = Email.toUpperCase();
    for (var i = 0; i <= str_length; i++) {
        var ch = Email.charAt(i);
        if ((ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9')) {
            switch (ch) {
                case '@' :
                {
                    if (i == 0 || i == str_length || at_let) {
                        checked_status = false;
                    }
                    at_let = true;
                    at_ps = i;
                    break;
                }
                case '.' :
                {
                    if (i == 0 || i == str_length || i == at_ps + 1 || i == at_ps - 1) {
                        checked_status = false;
                    }
                    point_let = true;
                    break;
                }
                case '_' :
                case '-' :
                {
                    if (i == 0 || i == str_length) {
                        checked_status = false;
                    }
                    break;
                }
                default :
                    checked_status = false;
                    break;
            }
        }
        if (!checked_status) {
            break;
        }
    }
    checked_status = checked_status & point_let & at_let;

    return checked_status;
}

//组合多选checkbox
function combineCheckBox(cbName, targetId) {
    var target = $(targetId);
    target.value = '';
    var checkboxes = document.getElementsByName(cbName);
    for (i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            target.value += checkboxes[i].value + ',';
        }
    }
    if(!isBlank(target.value)){
        target.value = target.value.substr(0,target.value.length - 1);
    }
}
//判断是否为整形数字:sign 为 - or +，代表正负
function IsInteger(string, sign) {
    var integer;
    if(!sign) sign = '+';
    if ((sign != '-') && (sign != '+')) {
        alert('IsInter(string,sign)的参数出错：\nsign为null或"-"或"+"');
        return false;
    }
    integer = parseInt(string);
    if (isNaN(integer)) {
        return false;
    }
    else if (integer.toString().length == string.length) {
        return (sign == null) || (sign == '-' && integer < 0) || (sign == '+' && integer > 0);
    } else {
        return false;
    }
}

/**
 补位达到定长
 src - 源字符串
 charfill - 不够定用charfill补
 len - 最终长度
 **/
function padding(src, charfill, len) {
    for (var i = src.length; i < len; i++) {
        src = charfill + src;
    }
    return src;
}

function fixImage(img, width, height) {
    var isIE = navigator.userAgent.toLowerCase().indexOf("msie") >= 0;
    if (!isIE)
        return;

    var currentSrc = img.src;

    var imgStyle = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + currentSrc + "', sizingMethod='scale')";
    img.src = 'images/clearpixel.gif';
    img.style.width = width + "px";
    img.style.height = height + "px";
    img.style.filter = imgStyle;
}

var navigationPages = [ "home.page", "eatures.page", "demos.page", "docs.page", "downloads.page", "about.page" ];
var navigationLinks = [ "homeLink", "featuresLink", "demosLink", "docsLink", "downloadsLink", "aboutLink" ];

function showMenuContext() {
    var currentLocation = document.location.href;
    for (var i = 0; i < navigationPages.length; i++)
        if (currentLocation.indexOf(navigationPages[i]) != -1) {
            setLinkStyle($(navigationLinks[i]));
            break;
        }
}

function setLinkStyle(link) {
    link.style.fontWeight = 'bold';
    var currentFontSize = parseInt(RicoUtil.getElementsComputedStyle(link, "fontSize", "font-size"));
    link.style.fontSize = (currentFontSize + 2) + "px";
    link.style.color = 'white';
}

function ricoSubmit(tagID, reqURL, paras, formObj) {
    if (arguments.length == 1) {
        reqURL = 'pagecontrol.do';
        formObj = document.forms[0];
        paras = null;
    }
    if (arguments.length == 2) {
        paras = null;
    }
    if (arguments.length == 3) {
        formObj = document.forms[0];
    }
    ajaxEngine.registerAjaxText(tagID);
    ajaxEngine.registerRequest(tagID, reqURL);
    ajaxEngine.sendRequestArrayAndUpdate(tagID, convertFormObjs2Array(paras, formObj));
}

function ricoSubmitXML(tagID, reqURL, paras, formObj) {
    if (arguments.length == 1) {
        reqURL = 'pagecontrol.do';
        formObj = document.forms[0];
        paras = null;
    }
    if (arguments.length == 2) {
        paras = null;
    }
    if (arguments.length == 3) {
        formObj = document.forms[0];
    }
    ajaxEngine.registerAjaxText(tagID);
    ajaxEngine.registerRequest(tagID, reqURL);
    ajaxEngine.sendRequestArrayAndUpdateXML(tagID, convertFormObjs2Array(paras, formObj));
}

function convertFormObjs2Array(paras, formObj) {
    var paramArray = new Array();
    var paraLen = 0;
    var d = -1;
    if (formObj) {
        if(formObj[0].tagName != "FORM"){
            formObj = new Array(formObj);
        }
        for(var x = 0; x < formObj.length; x++){
            for (var i = 0; i < formObj[x].length; i++) {
                ++d;
                var item = formObj[x][i];
                if(item.disabled){
                    paramArray[d] = 'null=null';
                    continue;
                }
                if (item.type == 'radio' || item.type == 'checkbox') {
                    if (item.checked) {
                        paramArray[d] = (item.name == ''?'undefined':item.name) + '=' + item.value.replace(/\%/g, 'ppppCCCeNNt');
                    } else {
                        paramArray[d] = 'null=null';
                    }
                } else {
                    paramArray[d] = (item.name == ''?'undefined':item.name) + '=' + item.value.replace(/\%/g, 'ppppCCCeNNt');
                }
                paramArray[d] = paramArray[d].replace(/&/g, 'ANDandAND');
            }
            paraLen += formObj[x].length;
        }
    }
    if (paras != null) {
        paras += '&';
        do {
            paramArray[paraLen++] = paras.substring(0, paras.indexOf('&'));
            paras = paras.substring(paras.indexOf('&') + 1, paras.length);
        }
        while (paras.indexOf('&') > 0)
    }
    return paramArray;
}

/**
 * 去除多余空格函数
 * trim:去除两边空格 lTrim:去除左空格 rTrim: 去除右空格
 * 用法：
 *     var str = "  hello ";
 *     str = str.trim();
 */
String.prototype.trim = function()
{
    return this.replace(/(^[\s]*)|([\s]*$)/g, "");
}
String.prototype.lTrim = function()
{
    return this.replace(/(^[\s]*)/g, "");
}
String.prototype.rTrim = function()
{
    return this.replace(/([\s]*$)/g, "");
}
/********************************** Integer *************************************/
/**
 *校验字符串是否为整型
 *返回值：
 *如果为空，定义校验通过，      返回true
 *如果字串全部为数字，校验通过，返回true
 *如果校验不通过，              返回false     参考提示信息：输入域必须为数字！
 */
function checkIsInteger(str)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    return /^(\\-?)(\\d+)$/.test(str);
}
//~~~
/**
 *校验整型最小值
 *str：要校验的串。  val：比较的值
 *
 *返回值：
 *如果为空，定义校验通过，                返回true
 *如果满足条件，大于等于给定值，校验通过，返回true
 *如果小于给定值，                        返回false              参考提示信息：输入域不能小于给定值！
 */
function checkIntegerMinValue(str, val)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    if (typeof(val) != "string")
        val = val + "";
    if (checkIsInteger(str))
    {
        return parseInt(str, 10) >= parseInt(val, 10);
    }
    return false;
}
//~~~
/**
 *校验整型最大值
 *str：要校验的串。  val：比较的值
 *
 *返回值：
 *如果为空，定义校验通过，                返回true
 *如果满足条件，小于等于给定值，校验通过，返回true
 *如果大于给定值，                        返回false       参考提示信息：输入值不能大于给定值！
 */
function checkIntegerMaxValue(str, val)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    if (typeof(val) != "string")
        val = val + "";
    if (checkIsInteger(str))
    {
        return parseInt(str, 10) <= parseInt(val, 10);
    }
    else
        return false;
}
//~~~
/**
 *校验整型是否为非负数
 *str：要校验的串。
 *
 *返回值：
 *如果为空，定义校验通过，返回true
 *如果非负数，            返回true
 *如果是负数，            返回false               参考提示信息：输入值不能是负数！
 */
function isNotNegativeInteger(str)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    if (checkIsInteger(str))
    {
        return parseInt(str, 10) >= 0;
    }
    else
        return false;
}
//~~~
/*--------------------------------- Integer --------------------------------------*/
/********************************** Double ****************************************/
/**
 *校验字符串是否为浮点型
 *返回值：
 *如果为空，定义校验通过，      返回true
 *如果字串为浮点型，校验通过，  返回true
 *如果校验不通过，              返回false     参考提示信息：输入域不是合法的浮点数！
 */
function checkIsDouble(str)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    //如果是整数，则校验整数的有效性
    if (str.indexOf(".") == -1)
    {
        return checkIsInteger(str);
    }
    else
    {
        return /^(\\-?)(\\d+)(.{1})(\\d+)$/g.test(str);
    }
}
//~~~
/**
 *校验浮点型最小值
 *str：要校验的串。  val：比较的值
 *
 *返回值：
 *如果为空，定义校验通过，                返回true
 *如果满足条件，大于等于给定值，校验通过，返回true
 *如果小于给定值，                        返回false              参考提示信息：输入域不能小于给定值！
 */
function checkDoubleMinValue(str, val)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    if (typeof(val) != "string")
        val = val + "";
    if (checkIsDouble(str))
    {
        return parseFloat(str) >= parseFloat(val);
    }
    else
        return false;
}
//~~~
/**
 *校验浮点型最大值
 *str：要校验的串。  val：比较的值
 *
 *返回值：
 *如果为空，定义校验通过，                返回true
 *如果满足条件，小于等于给定值，校验通过，返回true
 *如果大于给定值，                        返回false       参考提示信息：输入值不能大于给定值！
 */
function checkDoubleMaxValue(str, val)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    if (typeof(val) != "string")
        val = val + "";
    if (checkIsDouble(str))
    {
        return parseFloat(str) <= parseFloat(val);
    }
    else
        return false;
}
//~~~
/**
 *校验浮点型是否为非负数
 *str：要校验的串。
 *
 *返回值：
 *如果为空，定义校验通过，返回true
 *如果非负数，            返回true
 *如果是负数，            返回false               参考提示信息：输入值不能是负数！
 */
function isNotNegativeDouble(str)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    if (checkIsDouble(str))
    {
        return parseFloat(str) >= 0
    }
    else
        return false;
}
//~~~
/*--------------------------------- Double ---------------------------------------*/
/********************************** date ******************************************/
/**
 *校验字符串是否为日期型
 *返回值：
 *如果为空，定义校验通过，           返回true
 *如果字串为日期型，校验通过，       返回true
 *如果日期不合法，                   返回false    参考提示信息：输入域的时间不合法！（yyyy-MM-dd）
 */
function checkIsValidDate2(str)
{
     //如果为空，则通过校验
     if(str == "")
         return true;
     var pattern = /^((\d{4})|(\d{2}))-(\d{1,2})-(\d{1,2})$/g;
     if(!pattern.test(str))
         return false;
     var arrDate = str.split("-");
     if(parseInt(arrDate[0],10) < 100)
         arrDate[0] = 2000 + parseInt(arrDate[0],10) + "";
     var date =   new Date(arrDate[0],(parseInt(arrDate[1],10) -1)+"",arrDate[2]);
     if(date.getYear() == arrDate[0]
        && date.getMonth() == (parseInt(arrDate[1],10) -1)+""
        && date.getDate() == arrDate[2])
         return true;
     else
         return false;
}

function checkIsValidDate(str)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    var pattern = /^((\\d{4})|(\\d{2}))-(\\d{1,2})-(\\d{1,2})$/g;

    if (!pattern.test(str))
        return false;
    var arrDate = str.split("-");
    if (parseInt(arrDate[0], 10) < 100)
        arrDate[0] = 2000 + parseInt(arrDate[0], 10) + "";
    var date = new Date(arrDate[0], (parseInt(arrDate[1], 10) - 1) + "", arrDate[2]);
    return date.getYear() == arrDate[0]
            && date.getMonth() == (parseInt(arrDate[1], 10) - 1) + ""
            && date.getDate() == arrDate[2];
}
//~~~
/**
 *校验两个日期的先后
 *返回值：
 *如果其中有一个日期为空，校验通过,          返回true
 *如果起始日期早于等于终止日期，校验通过，   返回true
 *如果起始日期晚于终止日期，                 返回false    参考提示信息： 起始日期不能晚于结束日期。
 */
function checkDateEarlier(strStart, strEnd)
{
    //if(checkIsValidDate(strStart) == false || checkIsValidDate(strEnd) == false)
    //return false;
    //如果有一个输入为空，则通过检验
    if (( strStart == "" ) || ( strEnd == "" ))
        return true;
    var arr1 = strStart.split("-");
    var arr2 = strEnd.split("-");
    var date1 = new Date(arr1[0], parseInt(arr1[1].replace(/^0/, ""), 10) - 1, arr1[2]);
    var date2 = new Date(arr2[0], parseInt(arr2[1].replace(/^0/, ""), 10) - 1, arr2[2]);
    if (arr1[1].length == 1)
        arr1[1] = "0" + arr1[1];
    if (arr1[2].length == 1)
        arr1[2] = "0" + arr1[2];
    if (arr2[1].length == 1)
        arr2[1] = "0" + arr2[1];
    if (arr2[2].length == 1)
        arr2[2] = "0" + arr2[2];
    var d1 = arr1[0] + arr1[1] + arr1[2];
    var d2 = arr2[0] + arr2[1] + arr2[2];

    return parseInt(d1, 10) < parseInt(d2, 10);
}
//~~~
/*--------------------------------- date -----------------------------------------*/
/********************************** email *****************************************/
/**
 *校验字符串是否为email型
 *返回值：
 *如果为空，定义校验通过，           返回true
 *如果字串为email型，校验通过，      返回true
 *如果email不合法，                  返回false    参考提示信息：Email的格式不正確！
 */
function checkEmail(str)
{
    //如果为空，则通过校验
    if (str == "")
        return true;
    return !(str.charAt(0) == "." || str.charAt(0) == "@" || str.indexOf('@', 0) == -1 || str.indexOf('.', 0) == -1 || str.lastIndexOf("@") == str.length - 1 || str.lastIndexOf(".") == str.length - 1);
}
//~~~
/*--------------------------------- email ----------------------------------------*/
/********************************** chinese ***************************************/
/**
 *校验字符串是否为中文
 *返回值：
 *如果为空，定义校验通过，           返回true
 *如果字串为中文，校验通过，         返回true
 *如果字串为非中文，             返回false    参考提示信息：必须为中文！
 */
function checkIsChinese(str)
{
    //如果值为空，通过校验
    if (str == "")
        return true;
    var pattern = /^([\\u4E00-\\u9FA5]|[\\uFE30-\\uFFA0])*$/gi;
    return pattern.test(str);
}
//~~~
/**
 * 计算字符串的长度，一个汉字两个字符
 */
String.prototype.realLength = function()
{
    return this.replace(/[^\\x00-\\xff]/g, "**").length;
}
/*--------------------------------- chinese --------------------------------------*/
/********************************** mask ***************************************/
/**
 *校验字符串是否符合自定义正则表达式
 *str 要校验的字串  pat 自定义的正则表达式
 *返回值：
 *如果为空，定义校验通过，           返回true
 *如果字串符合，校验通过，           返回true
 *如果字串不符合，                   返回false    参考提示信息：必须满足***模式
 */
function checkMask(str, pat)
{
    //如果值为空，通过校验
    if (str == "")
        return true;
    var pattern = new RegExp(pat, "gi")
    return pattern.test(str);
}
//~~~
/*--------------------------------- mask --------------------------------------*/
/********************************** file ***************************************/
/**
 * added by LxcJie 2004.6.25
 * 得到文件的后缀名
 * oFile为file控件对象
 */
function getFilePostfix(oFile)
{
    if (oFile == null)
        return null;
    var pattern = /(.*)\\.(.*)$/gi;
    if (typeof(oFile) == "object")
    {
        if (oFile.value == null || oFile.value == "")
            return null;
        var arr = pattern.exec(oFile.value);
        return RegExp.$2;
    }
    else if (typeof(oFile) == "string")
    {
        var arr = pattern.exec(oFile);
        return RegExp.$2;
    }
    else
        return null;
}
//~~~
/*--------------------------------- file --------------------------------------*/

function getBirthdateFromIDCardNo(cardNo) {
    if (!isValidCardno(cardNo)) {
        alert('incorrect cardNo');
        return 'incorrect cardNo';
    }
    if (cardNo.substring(6, 8) == '19') {
        //alert(cardNo.substring(6,10) + '-' +  cardNo.substring(10,12) + '-' + cardNo.substring(12,14));
        return cardNo.substring(6, 10) + '-' + cardNo.substring(10, 12) + '-' + cardNo.substring(12, 14);
    }
    //alert('19' + cardNo.substring(6,8) + '-' +  cardNo.substring(8,10) + '-' + cardNo.substring(10,12));
    return '19' + cardNo.substring(6, 8) + '-' + cardNo.substring(8, 10) + '-' + cardNo.substring(10, 12);
}

function escapeHTML(html) {
    var div = document.createElement('div');
    var text = document.createTextNode(html);
    div.appendChild(text);
    return div.innerHTML;
}

var selectDisplayArray = new Array();
function showSelect(isShow) {
    var selects = document.getElementsByTagName("SELECT");
    if (isShow) {
        for (var i = 0; i < selectDisplayArray.length; i++) {
            selectDisplayArray[i].style.display = 'inline';
        }
        selectDisplayArray = new Array();
    } else {
        for (var i = 0; i < selects.length; i++) {
            if (selects[i].style.display == 'none') {
                continue;
            }
            selects[i].style.display = 'none';
            selectDisplayArray.push(selects[i]);
        }
    }
}

function abbreviate(src, length) {
    if (src.length <= length) {
        return src;
    }

    length = length - 3;
    return src.substring(0, length) + '...';
}


if (window.event) {// 修正Event的DOM
    /*
                                                            IE5                MacIE5                Mozilla                Konqueror2.2                Opera5
    event                                                yes                yes                        yes                        yes                                        yes
    event.returnValue                        yes                yes                        no                        no                                        no
    event.cancelBubble                        yes                yes                        no                        no                                        no
    event.srcElement                        yes                yes                        no                        no                                        no
    event.fromElement                        yes                yes                        no                        no                                        no

    */
    event.prototype.__defineSetter__("returnValue", function(b) {//
        if (!b)this.preventDefault();
        return b;
    });
    event.prototype.__defineSetter__("cancelBubble", function(b) {// 设置或者检索当前事件句柄的层次冒泡
        if (b)this.stopPropagation();
        return b;
    });
    event.prototype.__defineGetter__("srcElement", function() {
        var node = this.target;
        while (node.nodeType != 1)node = node.parentNode;
        return node;
    });
    event.prototype.__defineGetter__("fromElement", function() {// 返回鼠标移出的源节点
        var node;
        if (this.type == "mouseover")
            node = this.relatedTarget;
        else if (this.type == "mouseout")
            node = this.target;
        if (!node)return;
        while (node.nodeType != 1)node = node.parentNode;
        return node;
    });
    event.prototype.__defineGetter__("toElement", function() {// 返回鼠标移入的源节点
        var node;
        if (this.type == "mouseout")
            node = this.relatedTarget;
        else if (this.type == "mouseover")
            node = this.target;
        if (!node)return;
        while (node.nodeType != 1)node = node.parentNode;
        return node;
    });
    event.prototype.__defineGetter__("offsetX", function() {
        return this.layerX;
    });
    event.prototype.__defineGetter__("offsetY", function() {
        return this.layerY;
    });
}
if (window.Document) {// 修正Document的DOM
    /*
                                                            IE5                MacIE5                Mozilla                Konqueror2.2                Opera5
    document.documentElement        yes                yes                        yes                        yes                                        no
    document.activeElement                yes                null                no                        no                                        no

    */
}
if (window.Node) {// 修正Node的DOM
    /*
                                                            IE5                MacIE5                Mozilla                Konqueror2.2                Opera5
    Node.contains                                yes                yes                        no                        no                                        yes
    Node.replaceNode                        yes                no                        no                        no                                        no
    Node.removeNode                                yes                no                        no                        no                                        no
    Node.children                                yes                yes                        no                        no                                        no
    Node.hasChildNodes                        yes                yes                        yes                        yes                                        no
    Node.childNodes                                yes                yes                        yes                        yes                                        no
    Node.swapNode                                yes                no                        no                        no                                        no
    Node.currentStyle                        yes                yes                        no                        no                                        no

    */
    Node.prototype.replaceNode = function(Node) {// 替换指定节点
        this.parentNode.replaceChild(Node, this);
    }
    Node.prototype.removeNode = function(removeChildren) {// 删除指定节点
        if (removeChildren)
            return this.parentNode.removeChild(this);
        else {
            var range = document.createRange();
            range.selectNodeContents(this);
            return this.parentNode.replaceChild(range.extractContents(), this);
        }
    }
    Node.prototype.swapNode = function(Node) {// 交换节点
        var nextSibling = this.nextSibling;
        var parentNode = this.parentNode;
        Node.parentNode.replaceChild(this, Node);
        parentNode.insertBefore(Node, nextSibling);
    }
}
if (window.HTMLElement) {
    HTMLElement.prototype.__defineGetter__("all", function() {
        var a = this.getElementsByTagName("*");
        var node = this;
        a.tags = function(sTagName) {
            return node.getElementsByTagName(sTagName);
        }
        return a;
    });
    HTMLElement.prototype.__defineGetter__("parentElement", function() {
        if (this.parentNode == this.ownerDocument)return null;
        return this.parentNode;
    });
    HTMLElement.prototype.__defineGetter__("children", function() {
        var tmp = [];
        var j = 0;
        var n;
        for (var i = 0; i < this.childNodes.length; i++) {
            n = this.childNodes[i];
            if (n.nodeType == 1) {
                tmp[j++] = n;
                if (n.name) {
                    if (!tmp[n.name])
                        tmp[n.name] = [];
                    tmp[n.name][tmp[n.name].length] = n;
                }
                if (n.id)
                    tmp[n.id] = n;
            }
        }
        return tmp;
    });
    HTMLElement.prototype.__defineGetter__("currentStyle", function() {
        return this.ownerDocument.defaultView.getComputedStyle(this, null);
    });
    HTMLElement.prototype.__defineSetter__("outerHTML", function(sHTML) {
        var r = this.ownerDocument.createRange();
        r.setStartBefore(this);
        var df = r.createContextualFragment(sHTML);
        this.parentNode.replaceChild(df, this);
        return sHTML;
    });
    HTMLElement.prototype.__defineGetter__("outerHTML", function() {
        var attr;
        var attrs = this.attributes;
        var str = "<" + this.tagName;
        for (var i = 0; i < attrs.length; i++) {
            attr = attrs[i];
            if (attr.specified)
                str += " " + attr.name + '="' + attr.value + '"';
        }
        if (!this.canHaveChildren)
            return str + ">";
        return str + ">" + this.innerHTML + "</" + this.tagName + ">";
    });
    HTMLElement.prototype.__defineGetter__("canHaveChildren", function() {
        switch (this.tagName.toLowerCase()) {
            case "area":
            case "base":
            case "basefont":
            case "col":
            case "frame":
            case "hr":
            case "img":
            case "br":
            case "input":
            case "isindex":
            case "link":
            case "meta":
            case "param":
                return false;
        }
        return true;
    });

    HTMLElement.prototype.__defineSetter__("innerText", function(sText) {
        var parsedText = document.createTextNode(sText);
        this.innerHTML = parsedText;
        return parsedText;
    });
    HTMLElement.prototype.__defineGetter__("innerText", function() {
        var r = this.ownerDocument.createRange();
        r.selectNodeContents(this);
        return r.toString();
    });
    HTMLElement.prototype.__defineSetter__("outerText", function(sText) {
        var parsedText = document.createTextNode(sText);
        this.outerHTML = parsedText;
        return parsedText;
    });
    HTMLElement.prototype.__defineGetter__("outerText", function() {
        var r = this.ownerDocument.createRange();
        r.selectNodeContents(this);
        return r.toString();
    });
    HTMLElement.prototype.attachEvent = function(sType, fHandler) {
        var shortTypeName = sType.replace(/on/, "");
        fHandler._ieEmuEventHandler = function(e) {
            window.event = e;
            return fHandler();
        }
        this.addEventListener(shortTypeName, fHandler._ieEmuEventHandler, false);
    }
    HTMLElement.prototype.detachEvent = function(sType, fHandler) {
        var shortTypeName = sType.replace(/on/, "");
        if (typeof(fHandler._ieEmuEventHandler) == "function")
            this.removeEventListener(shortTypeName, fHandler._ieEmuEventHandler, false);
        else
            this.removeEventListener(shortTypeName, fHandler, true);
    }
    HTMLElement.prototype.contains = function(Node) {// 是否包含某节点
        do if (Node == this)return true;
        while (Node = Node.parentNode);
        return false;
    }
    HTMLElement.prototype.insertAdjacentElement = function(where, parsedNode) {
        switch (where) {
            case "beforeBegin":
                this.parentNode.insertBefore(parsedNode, this);
                break;
            case "afterBegin":
                this.insertBefore(parsedNode, this.firstChild);
                break;
            case "beforeEnd":
                this.appendChild(parsedNode);
                break;
            case "afterEnd":
                if (this.nextSibling)
                    this.parentNode.insertBefore(parsedNode, this.nextSibling);
                else
                    this.parentNode.appendChild(parsedNode);
                break;
        }
    }
    HTMLElement.prototype.insertAdjacentHTML = function(where, htmlStr) {
        var r = this.ownerDocument.createRange();
        r.setStartBefore(this);
        var parsedHTML = r.createContextualFragment(htmlStr);
        this.insertAdjacentElement(where, parsedHTML);
    }
    HTMLElement.prototype.insertAdjacentText = function(where, txtStr) {
        var parsedText = document.createTextNode(txtStr);
        this.insertAdjacentElement(where, parsedText);
    }
    HTMLElement.prototype.attachEvent = function(sType, fHandler) {
        var shortTypeName = sType.replace(/on/, "");
        fHandler._ieEmuEventHandler = function(e) {
            window.event = e;
            return fHandler();
        }
        this.addEventListener(shortTypeName, fHandler._ieEmuEventHandler, false);
    }
    HTMLElement.prototype.detachEvent = function(sType, fHandler) {
        var shortTypeName = sType.replace(/on/, "");
        if (typeof(fHandler._ieEmuEventHandler) == "function")
            this.removeEventListener(shortTypeName, fHandler._ieEmuEventHandler, false);
        else
            this.removeEventListener(shortTypeName, fHandler, true);
    }
}

if (window.addEventListener)
{
    FixPrototypeForGecko();
}
function FixPrototypeForGecko()
{
    HTMLElement.prototype.__defineGetter__("runtimeStyle", element_prototype_get_runtimeStyle);
    window.constructor.prototype.__defineGetter__("event", window_prototype_get_event);
    Event.prototype.__defineGetter__("srcElement", event_prototype_get_srcElement);
}
function element_prototype_get_runtimeStyle()
{
    //return style instead...
    return this.style;
}
function window_prototype_get_event()
{
    return SearchEvent();
}
function event_prototype_get_srcElement()
{
    var srcElement = this.target;
    if (srcElement.nodeType != 1) {
        srcElement = srcElement.parentNode;
    }
    return srcElement;
}

function SearchEvent()
{
    //IE
    if (document.all)
        return window.event;

    var func = SearchEvent.caller;
    while (func != null)
    {
        var arg0 = func.arguments[0];
        if (arg0)
        {
            if (arg0 instanceof Event)
                return arg0;
        }
        func = func.caller;
    }
    return null;
}


function URLEncoding(str) {
    return str;
}

var ISIE = navigator.userAgent.toLowerCase().indexOf("msie") >= 0;

/**
 * 插入新行
 * param table 表格对象,目的表
 * param classArray class数组, 成员为每一列的className名称
 * param toFirst //boolean 是否插入到第一行,没有此参数追加到最后一行
 */
function insertRow(table, classArray, toFirst) {
    var index = table.rows.length;
    if (toFirst) {
        index = 0;
    }
    var row = table.insertRow(index);
    var cells = new Array();
    for (var i = 0; i < classArray.length; i++) {
        cells[i] = row.insertCell(row.cells.length);
        cells[i].innerHTML = '&nbsp;';
        cells[i].className = classArray[i];
    }
    return row;
}

/**
 * 取得xml某个节点的文本数据
 * param node 节点对象
 * param tag 节点名称 如果没有该参数将直接取第一个参数的文本数据
 */
function getNodeText(node, tag, nullVal, returnNullVal) {
    if (tag) {
        node = node.selectSingleNode(tag);
    }
    if (!node) {
        return returnNullVal?returnNullVal:"&nbsp;";
    }
    var value = node.text || node.textContent;
    if(value == undefined || value.trim() == '' || value == nullVal){
        return returnNullVal?returnNullVal:"&nbsp;";
    }
    return value;
}


function cbClicked(ob){
    if(ob.checked){
		changeRoundColor($("listitem_" + ob.value),LIGHTCOLOR1);
	} else {
		changeRoundColor($("listitem_" + ob.value),LIGHTCOLOR);
	}
}

function toggleCheckAll(name){
    var items = document.getElementsByName(name);
    var checked = $("cbToggleAll").checked;
    for(var i = 0; i < items.length; i++){
        items[i].checked = checked;
        //cbClicked(items[i]);
    }
}

function getSelectedIds(){
    var items = document.all["cbListItem"];
    if(!items) return '';
    if(!items.length) {
        items = new Array(items);
    }
    var ids = '';
    for(var i = 0; i < items.length; i++){
        if(items[i].checked){
            ids += items[i].value + ",";
        }
    }
    if(ids == ''){
        return '';
    }
    return ids.substring(0, ids.length - 1);
}

function showDivs(divs){
    for(var i = 0; i <divs.length; i++){
        if($(divs[i]).tagName == 'DIV')
            $(divs[i]).style.display = 'block';
        else
            $(divs[i]).style.display = 'inline';
    }
}

function hideDivs(divs){
     for(var i = 0; i <divs.length; i++){
        $(divs[i]).style.display = 'none';
    }
}

function disableButtons(buttons){
    for(var i = 0; i < buttons.length; i++){
        $(buttons[i]).disabled = true;
    }
}

function enableButtons(buttons){
     for(var i = 0; i < buttons.length; i++){
        $(buttons[i]).disabled = false;
    }
}


function showValue(value, nullVal){
    if(!nullVal){
        nullVal = '';
    }
    if(value.trim() == nullVal){
        return "&nbsp;";
    }
    return value;
}

function changeDivsVisible(divs, visible){
    for(var i = 0; i < divs.length; i++){
        $(divs[i]).style.display = visible?"block":"none";        
    }
}

function setSelectVisible(display) {
    var sels = document.getElementsByTagName("SELECT");
    for (var i = 0; i < sels.length; i++) {
        sels[i].style.display = display;
    }
}
function getPosition(obj){
    var top=obj.offsetTop; 
	var left=obj.offsetLeft; 
	var width=obj.offsetWidth; 
	var height=obj.offsetHeight; 
	while(obj=obj.offsetParent) 
	{ 
		top+=obj.offsetTop; 
		left+=obj.offsetLeft; 
	}
	var position = [top,left,width,height]; 
	return position;
} 
function Wa_SetImgAutoSize(MaxWidth, MaxHeight, img)
{
	img.style.display = "block";
	var HeightWidth=img.offsetHeight/img.offsetWidth;//设置高宽比
	var WidthHeight=img.offsetWidth/img.offsetHeight;//设置宽高比
	if(img.readyState!="complete")return false;//确保图片完全加载
	if(img.offsetWidth>MaxWidth){	
		img.width=MaxWidth;
		img.height=MaxWidth*HeightWidth;
	}
	if(img.offsetHeight>MaxHeight){
		img.height=MaxHeight;
		img.width=MaxHeight*WidthHeight;
	}
	img.style.margin = (MaxHeight - img.height) / 2 + "px, 0, " + (MaxHeight - img.height) / 2 + "px, 0";
}
function SendMsg(QQ){
	var url="Tencent://Message/?Menu=YES&amp;Exe=QQ&amp;Uin="+QQ;
	try{   //支持
		var xmlhttp=new ActiveXObject("TimwpDll.TimwpCheck");
		this.location.href=url;        
	}catch(e){}
}
function getElementsByClassNameScope(scope,eleClassName){
		var getEleClass = [];
		var myclass = new RegExp("\\b"+eleClassName+"\\b");
		var elem = $(scope).getElementsByTagName("*");
		for(var h=0;h<elem.length;h++){
			var classes = elem[h].className;
			if (myclass.test(classes) || elem[h].id == eleClassName) 
				getEleClass.push(elem[h]);
		}
		return getEleClass;
}

function getElementsBySpecielClassNameScope(scope,eleClassName){
		var getEleClass = [];
		var myclass = new RegExp("\\b"+eleClassName+"\\b");
		var elem = $(scope).getElementsByTagName("*");
		for(var h=0;h<elem.length;h++){
			var classes = elem[h].className;
			if (myclass.test(classes) || elem[h].id == eleClassName) 
				if(elem[h].code.indexOf("left") != -1 || elem[h].code.indexOf("center") != -1 || elem[h].code.indexOf("right") != -1)
					getEleClass.push(elem[h]);
		}
		return getEleClass;
}

function getElementsBySpecielClassNameScope2(scope,eleClassName){
		var getEleClass = [];
		var myclass = new RegExp("\\b"+eleClassName+"\\b");
		var elem = $(scope).getElementsByTagName("*");
		for(var h=0;h<elem.length;h++){
			var classes = elem[h].className;
			if (myclass.test(classes) || elem[h].id == eleClassName) 
				if(elem[h].code.indexOf("left") != -1 || elem[h].code.indexOf("center") != -1 || elem[h].code.indexOf("right") != -1){
					getEleClass.push(elem[h]);
					break;
				}
		}
		return getEleClass;
}

function getElementsByClassNameScopeDis(scope,eleClassName){
		var getEleClass = [];
		var myclass = new RegExp("\\b"+eleClassName+"\\b");
		var elem = $(scope).getElementsByTagName("*");
		for(var h=0;h<elem.length;h++){
			var classes = elem[h].className;
			if (elem[h].style.display != "none" && myclass.test(classes)) 
				getEleClass.push(elem[h]);
		}
		return getEleClass;
}

function getElementsByClassNamesScope(scope,eleClassName){
		var getEleClass = [];
		var myclass1 = new RegExp("\\b"+eleClassName[0]+"\\b");
		var myclass2 = new RegExp("\\b"+eleClassName[1]+"\\b");
		var Scope = $(scope);
		var elem = Scope.getElementsByTagName("*");
		for(var h=0;h<elem.length;h++){
			var classes = elem[h].className;
			if (myclass1.test(classes) || myclass2.test(classes)) 
				getEleClass.push(elem[h]);
		}
		return getEleClass;
}

function byteToHex(byteStr){
    var xmldom = new ActiveXObject("Microsoft.XMLDOM")
    var byteObj = xmldom.createElement("byteObj")
    byteObj.dataType = 'bin.hex'
    byteObj.nodeTypedValue = byteStr
    return (String(byteObj.text))
}

function hexToByte(hexStr){
    var xmldom = new ActiveXObject("Microsoft.XMLDOM")
    var byteObj = xmldom.createElement("byteObj")
    byteObj.dataType = "bin.hex"
    byteObj.nodeTypedValue = hexStr
    return (byteObj.nodeTypedValue)
}

function strToByte(str){
    return hexToByte(strToHex(str))
}

function byteToStr(byteStr){
    return hexTostr(byteToHex(byteStr))
}

function loadFile(path){
	try{
		var stream = new ActiveXObject("ADODB.Stream");
	    stream.Type = 1;
	    stream.Open();
	    stream.LoadFromFile(path);
	    var binaryStream = stream.Read();
	    stream.close();
	    return binaryStream;
	}catch(e){
		alert("图片不存在！");
		return false;
	} 
}

function ElementHide(obj){
	var hideObj = $(obj);
	if(hideObj.currentStyle.display == "none")
		hideObj.style.display = "block";
	else
		hideObj.style.display = "none"
}
function checkAll(objsName){
	var objs = document.getElementsByName(objsName);
	var srcChecked = window.event.srcElement.checked;
	for(var i = 0; i < objs.length; i++){
		if(srcChecked)
			objs[i].checked = true;
		else 
			objs[i].checked = false;		
	}
}

function getCheckboxValue(name){
    var cbArray = document.getElementsByName(name);
    if(cbArray.length == 0) return "";
    var ids = [];
    for(var i = 0; i < cbArray.length; i++){
        if(cbArray[i].checked == true){
            ids.push(cbArray[i].value);
        }
    }
    return ids;
}

function getCheckboxesValue(name){
    var cbArray = document.getElementsByName(name);
    if(cbArray.length == 0) return "";
    var ids = "";
    for(var i = 0; i < cbArray.length; i++){
        if(cbArray[i].checked){
            ids += cbArray[i].value + ",";
        }
    }
    if(ids != "") ids = ids.substring(0, ids.length - 1);
    return ids;
}
	
Array.prototype.shellSort = function(){
    for (var step = this.length >> 1; step > 0; step >>= 1) {
        for (var i = 0; i < step; ++i) {
            for (var j = i + step; j < this.length; j += step) {
                var k = j, value = this[j];
                while (k >= step && this[k - step] > value) {
                    this[k] = this[k - step];
                    k -= step;
                }
                this[k] = value;
            }
        }
    }
}
function seaorder(field){
	var PageForm = $("PageForm");
	if(PageForm){
		if(PageForm.seaorder.value==field+" desc"){
			PageForm.seaorder.value=field+" asc";
		}else{
			PageForm.seaorder.value=field+" desc";
		}
		PageForm.submit();
	}
}

function isOverBytes(s, maxbytes) { 
    var i = 0; 
    var bytes = 0; 
    var uFF61 = parseInt("FF61", 16); 
    var uFF9F = parseInt("FF9F", 16); 
    var uFFE8 = parseInt("FFE8", 16); 
    var uFFEE = parseInt("FFEE", 16); 
    while (i < s.length) 
    { 
        var c = parseInt(s.charCodeAt(i)); 
        if (c < 256) { 
            bytes = bytes + 1; 
        } 
        else { 
            if ((uFF61 <= c) && (c <= uFF9F)) { 
                bytes = bytes + 1; 
            } else if ((uFFE8 <= c) && (c <= uFFEE)) { 
                               bytes = bytes + 1; 
                       } 
                       else { 
                bytes = bytes + 2; 
            } 
        } 
        if (bytes > maxbytes) { 
            return true; 
        } 
        i = i + 1; 
    } 
    return false; 
}

function getSheetByTitle(title){
    var sheets = document.styleSheets;
    var rLength = sheets.length;
	for (var i = 0; i < rLength; i++) {
        if (sheets[i].title == title) 
            return sheets[i];
    }
}

function getScrollPos(){
    var scrollPosW = null;
    var scrollPosH = null;
    if (typeof window.pageYOffset != 'undefined') {
        scrollPosH = window.pageYOffset;
        scrollPosW = window.pageXOffset;
    }
    else 
        if (typeof document.compatMode != 'undefined' &&
        document.compatMode != 'BackCompat') {
            scrollPosH = document.documentElement.scrollTop;
            scrollPosW = document.documentElement.scrollLeft;
        }
        else 
            if (typeof document.body != 'undefined') {
                scrollPosH = document.body.scrollTop;
                scrollPosW = document.body.scrollLeft;
            }
    return [scrollPosW, scrollPosH];
}
//设为首页
function SetHome(obj){
var url=window.location.href;
try{
obj.style.behavior='url(#default#homepage)';obj.setHomePage(url);
}
catch(e){
if(window.netscape) {
try {
netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
}
catch (e) {
alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
}
var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
prefs.setCharPref('browser.startup.homepage',url);
}
}
}
//添加到收藏夹
function AddFavorite()
{
var url=window.location.href;
try
{
window.external.addFavorite(url, "****");
}
catch (e)
{
try
{
window.sidebar.addPanel("****", url, "");
}
catch (e)
{
alert("加入收藏失败，请使用Ctrl+D进行添加");
}
}
}