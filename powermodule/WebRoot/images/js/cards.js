
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
	var showText = "�Բ���û����Ϣ��";
	switch(switchId){
	    case 1:
		   showText =  "��ӭʹ���ۺϺ��̨����ϵͳ-��ҳ����";
		   break;
	    case 2:
		   showText =  "��ӭʹ�����Ϲ�������www.mayiadmin.cn����";
		   break;
	    case 3:
		   showText =  "֧��ƽ̨��www.zhifu.com������";
		   break;		   
	    case 4:
		   showText =  "��Ʊ����ϵͳ";
		   break;	
	    case 5:
		   showText =  "С��Ϸ��";
		   break;		   		   
	}
	getObject('show_text').innerHTML = showText;
}
 //��ȡ�������Լ��ݷ���
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
