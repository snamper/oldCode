
var preClassName = "man_nav_1";
function list_sub_nav(Id,sortname){
   if(preClassName != ""){
      getObject(preClassName).className="bg_image";
   }
   if(getObject(Id).className == "bg_image"){
      getObject(Id).className="bg_image_onclick";
      preClassName = Id;
	  showInnerText(Id);
   }
}

function showInnerText(Id){
    var switchId = parseInt(Id.substring(8));
	var showText = "对不起没有信息！";
	switch(switchId){
	    case 1:
		   showText =  "欢迎使用综合后后台管理系统-首页管理！";
		   break;
	    case 2:
		   showText =  "欢迎使用蚂蚁供货网（www.mayiadmin.cn）！";
		   break;
	    case 3:
		   showText =  "支付平台（www.zhifu.com）管理";
		   break;		   
	    case 4:
		   showText =  "机票管理系统";
		   break;	
	    case 5:
		   showText =  "小游戏网";
		   break;		   		   
	}
	getObject('show_text').innerHTML = showText;
}
 //获取对象属性兼容方法
 function getObject(objectId) {
    if(document.getElementById && document.getElementById(objectId)) {
	// W3C DOM
	return document.getElementById(objectId);
    } else if (document.all && document.all(objectId)) {
	// MSIE 4 DOM
	return document.all(objectId);
    } else if (document.layers && document.layers[objectId]) {
	// NN 4 DOM.. note: this won't find nested layers
	return document.layers[objectId];
    } else {
	return false;
    }
}
