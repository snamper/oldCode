<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>艺术签名</title>
<link href="images/style/master.css" rel="stylesheet" type="text/css" />
<link charset="utf-8" type="text/css" rel="stylesheet" href="https://img.tenpay.com/v2/res_mch_open/css/global/tid_min.css">
<script>
function subForm(){
	var vname = document.getElementById("signName").value;
	if(vname==''){
		//alert('请输入一个名字');
		return;
	}
	
	document.getElementById("subButten").disabled=true;
	//document.getElementById("notUsed").className='btn-disable';
	
	document.form1.submit();
}
</script>
</head>
<body>
<!--主体部分开始-->
<div id="top"></div>
<div id="main">
  <div id="main_left">
    <!--签名展示开始-->
    <div id="product_list">
      <dl>
        <dt>
          <p><a href="index.html"><img src="images/pic_01.jpg" border="0" /></a></p>
          <h2>杨澜</h2>
        </dt>
        <dd></dd>
      </dl>
      <dl>
        <dt>
          <p><a href="my_design.html"><img src="images/pic_02.jpg" border="0" /></a></p>
          <h2>杨澜</h2>
        </dt>
        <dd></dd>
      </dl>
      <dl>
        <dt>
          <p><a href="order_form.html"><img src="images/pic_03.jpg" width="110" height="85" border="0" /></a></p>
          <h2>杨澜</h2>
        </dt>
      </dl>
      <br clear="clear" />
    </div>
    <!--签名展示结束-->
    <!--指南说明开始-->
    <div id="guild">
      <div id="guild_p1"> 签名是一个人在社会、法律、条文契约等一切关系中，代表个人资信及个人审美情趣的视觉标志。<br />
        设计出图形风格的签名，具有很强的趣味性、欣赏性，字中有画、画中有字。书法艺术和平面设计的完美结合，让人过目不忘 </div>
      <div id="guild_p2"> <span class="font-14"><strong>签名设计特点: </strong></span><span class="red">表现风格轻快活泼，优雅随意。主要用在涉外场合的签名及时尚意味的自我标识。</span> </div>
    </div>
    <!--指南说明结束-->
    <!--广告开始-->
    <div id="banner"> <img src="images/banner.jpg" /> </div>
    <!--广告条结束-->
  </div>
  <form name="form1" id="form1" method="post" action="CreateOrder">
  <div id="main_right">
    <!--热点图片开始-->
    <div id="hot">
      <div id="hot_pic"><img src="images/01.jpg" /></div>
      <h2>李宇春武汉开唱跳肚皮舞</h2>
    </div>
    <!--热点图片结束-->
    <!--输入姓名开始-->
    <div id="enter_text">
      <h2><span class="font-14"><strong>请输入您的姓名： </strong></span></h2>
      <div id="enter_text_txt">
        <label>
          <input name="name" id="signName" class="text" text" high" size="12" type="text" style="height:31px; font-size:22px; line-height:31px; font-family:微软雅黑">
        </label>
      </div>
      <c:forEach items="${requestScope.proList}" var="pr">
      <div class="enter_text_list1">
        <input checked="checked" id="rdo_demo1" class="radio" type="radio" value="${pr.fid}" name="radiobutton">${pr.fname}:<span class="money">${pr.fpayMoney}</span>元（<span class="line-through">${pr.fadMoney}</span>）
      </div>
      </c:forEach>
      <div id="enter_text_login"><span id="notUsed" class="btn-oran-act">
        <button id="subButten"  onclick="subForm();" type="button" style="width:125px;">开始设计</button></span>
        </div>
    </div>
    <!--输入姓名结束-->
  </div>
  </form>
  <div class="clear"></div>
</div>
<!--主体部分结束-->
</body>
</html>

