//�ַ�����
function cchar(showname,na,a,b){
	var val = document.getElementById(na).value.length;
	if(val<a || val>b){
		//document.getElementById(na).focus();
		alert(showname+':�ַ�������'+a+'-'+b+'֮��');
		return false;
	}
	return true;
}
//�������
function cpassword(showname,na,a,b){
	var val = document.getElementById(na).value.length;
	if(val<a || val>b){
		//document.getElementById(na).focus();
		alert(showname+':����λ����'+a+'-'+b+'֮��');
		return false;
	}
	return true;
}
//���μ���
function cint(showname,na,a,b){
	var val = document.getElementById(na).value;
	var ck = /^-?\d+$/;
	if(val.match(ck)){
		if(val<a || val>b){
			//document.getElementById(na).focus();
			alert(showname+':����������'+a+'-'+b+'֮��');
			return false;
		}else{
			return true;
		}
	}else{
		//document.getElementById(na).focus();
		alert(showname+":����������");
		return false;

	}
}
//С������
function cdecimal(showname,na){
	var val = document.getElementById(na).value;
	 var ck = /^(-?\d+)(\.\d+)?$/;��
	 if(!val.match(ck)){
		// document.getElementById(na).focus();
		 alert(showname+":�����Ǹ�����");
		 return false;
	 }
	 return true;
}
//���ڼ���
function cdatetime(showname,na){
	var val = document.getElementById(na).value;
	var ck = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))(\s\d{1,2}:\d{1,2}:\d{1,2})(.\d{1,3})?$/;
	if(val.match(ck) || val==""){
		return true;
	}else{
		//document.getElementById(na).focus();
		alert(showname+":���������ڸ�ʽ");
		return false;
	}
}
//�ı�����
function ctext(showname,na,a,b){
	var val = document.getElementById(na).value.length;
	if(val<a || val>b){
		//document.getElementById(na).focus();
		alert(showname+':�ı�������'+a+'-'+b+'֮��');
		return false;
	}
	return true;
}
//ͼƬ����
function cimage(){
	alert(7);
}

function totals(sb){
	if(sb=="")return true;
	var abc=sb.substring(0,sb.length-1);
	var s=abc.split("|");
	var len = s.length;
	for(var i=0;i<len;i++){
		if(eval(s[i])){
			//alert("OK");
		}else{
			return false;
		}
	}

	 return true;
}
