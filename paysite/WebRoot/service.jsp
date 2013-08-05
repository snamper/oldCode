<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<jsp:include page="headAndfoot.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>${currentAgent.fname}</title>
<link href="agentindex/ai_${currentAgent.fid}/images/style/master.css" rel="stylesheet" type="text/css" />
<link href="agentindex/ai_${currentAgent.fid}/images/style/layout.css" rel="stylesheet" type="text/css" />
<script src="images_news/js/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<body>
<!--页眉开始-->
<%out.println(request.getAttribute("headers")); %>
<!--页眉结束-->
<div id="column_A">
<table width="990" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" bgcolor="#FDFDFD" class="bk"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="font3">
      <tr>
        <td height="45"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50" valign="top"></td>
            <td align="left" class="link12">您所在的位置：首页 &gt; 服务条款 </td>
          </tr>
        </table></td>
      </tr>
    </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="50" align="center" bgcolor="#F8F8F8" class="col_A_font9">完善的游戏运营商服务</td>
          </tr>
          <tr>
            <td align="center"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="line_02">
              <tr>
                <td width="15" height="25">&nbsp;</td>
                <td width="400">&nbsp;</td>
                <td align="right">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="20"></td>
          </tr>
        </table>
        <table width="96%" border="0" align="center" cellpadding="12" cellspacing="0" class="service_tab_content">
          <tr>
            <td align="left" valign="top" class="col_A_font10"><p >              1.快捷结算 注：商户可按小时提交提现申请，结算当前未结算的实际款，到账时间：1小时、特殊结算使用费:单笔1元</p>
              <p>2.高效客服 平台承诺我们的客服QQ会7*24小时在线为您提供服务、解答疑问。订单问题5分钟内解决，卡单情况10分钟内解决，特殊情况30分钟内解决（不可抗拒因素除外），同时用户可以随时拨打我们的客服专线进行咨询</p>
              <p>3.数据报表 平台将在第二天为用户提供详细的数据报表，包含订单数量，结算金额，支付成功或失败的具体时间和说明</p>
              <p>4.查询系统 平台承诺给用户提供完善的页面查询系统，方便用户随时查询支付情况</p>
              <p>5.价格体系 平台将使用浮动的价格体系</p>
              <p>6.安全专业 平台采用最成熟的支付系统，配有最专业的技术队伍，保证交易环境的安全、稳定和效率</p>
              <p><br />
              </p>
              <p>支付平台配有着强大的技术团队，采用最简单化、最便捷化的支付服务，很短的时间内完成对订单的处理，给用户返回确认信息；同时以更稳定、安全的技术手段让商家在游戏运营迅速发展竞争越来越大的情况下节省商家的时间与精力，达到更多的赢利。 </p>
              <p>支付平台承诺用户我们的卡类支付是同类平台中结算费率最高的，同时平台将会不断的推出各种支付方式为用户提供更高效的服务。<br />
              </p></td>
          </tr>
        </table>
        <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="30" colspan="4" align="right"><a href="javascript:void(0);" class="col_A_link3" onclick="javascript:window.close();">【关闭此页面】</a></td>
          </tr>
      </table></td>
  </tr>
</table>
</div>

<!--内容区 开始-->

<!--内容区 结束-->

<!--页脚开始-->
<%out.println(request.getAttribute("footers")); %>
<!--页脚结束-->


</body>
</html>
