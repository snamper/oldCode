<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>����ǩ��</title>
<link href="images/style/master.css" rel="stylesheet" type="text/css" />
<link charset="utf-8" type="text/css" rel="stylesheet" href="https://img.tenpay.com/v2/res_mch_open/css/global/tid_min.css">
<script>
function subForm(){
	var vname = document.getElementById("signName").value;
	if(vname==''){
		//alert('������һ������');
		return;
	}
	
	document.getElementById("subButten").disabled=true;
	//document.getElementById("notUsed").className='btn-disable';
	
	document.form1.submit();
}
</script>
</head>
<body>
<!--���岿�ֿ�ʼ-->
<div id="top"></div>
<div id="main">
  <div id="main_left">
    <!--ǩ��չʾ��ʼ-->
    <div id="product_list">
      <dl>
        <dt>
          <p><a href="index.html"><img src="images/pic_01.jpg" border="0" /></a></p>
          <h2>����</h2>
        </dt>
        <dd></dd>
      </dl>
      <dl>
        <dt>
          <p><a href="my_design.html"><img src="images/pic_02.jpg" border="0" /></a></p>
          <h2>����</h2>
        </dt>
        <dd></dd>
      </dl>
      <dl>
        <dt>
          <p><a href="order_form.html"><img src="images/pic_03.jpg" width="110" height="85" border="0" /></a></p>
          <h2>����</h2>
        </dt>
      </dl>
      <br clear="clear" />
    </div>
    <!--ǩ��չʾ����-->
    <!--ָ��˵����ʼ-->
    <div id="guild">
      <div id="guild_p1"> ǩ����һ��������ᡢ���ɡ�������Լ��һ�й�ϵ�У�����������ż�����������Ȥ���Ӿ���־��<br />
        ��Ƴ�ͼ�η���ǩ�������к�ǿ��Ȥζ�ԡ������ԣ������л����������֡��鷨������ƽ����Ƶ�������ϣ����˹�Ŀ���� </div>
      <div id="guild_p2"> <span class="font-14"><strong>ǩ������ص�: </strong></span><span class="red">���ַ�������ã��������⡣��Ҫ�������ⳡ�ϵ�ǩ����ʱ����ζ�����ұ�ʶ��</span> </div>
    </div>
    <!--ָ��˵������-->
    <!--��濪ʼ-->
    <div id="banner"> <img src="images/banner.jpg" /> </div>
    <!--���������-->
  </div>
  <form name="form1" id="form1" method="post" action="CreateOrder">
  <div id="main_right">
    <!--�ȵ�ͼƬ��ʼ-->
    <div id="hot">
      <div id="hot_pic"><img src="images/01.jpg" /></div>
      <h2>����人��������Ƥ��</h2>
    </div>
    <!--�ȵ�ͼƬ����-->
    <!--����������ʼ-->
    <div id="enter_text">
      <h2><span class="font-14"><strong>���������������� </strong></span></h2>
      <div id="enter_text_txt">
        <label>
          <input name="name" id="signName" class="text" text" high" size="12" type="text" style="height:31px; font-size:22px; line-height:31px; font-family:΢���ź�">
        </label>
      </div>
      <c:forEach items="${requestScope.proList}" var="pr">
      <div class="enter_text_list1">
        <input checked="checked" id="rdo_demo1" class="radio" type="radio" value="${pr.fid}" name="radiobutton">${pr.fname}:<span class="money">${pr.fpayMoney}</span>Ԫ��<span class="line-through">${pr.fadMoney}</span>��
      </div>
      </c:forEach>
      <div id="enter_text_login"><span id="notUsed" class="btn-oran-act">
        <button id="subButten"  onclick="subForm();" type="button" style="width:125px;">��ʼ���</button></span>
        </div>
    </div>
    <!--������������-->
  </div>
  </form>
  <div class="clear"></div>
</div>
<!--���岿�ֽ���-->
</body>
</html>

