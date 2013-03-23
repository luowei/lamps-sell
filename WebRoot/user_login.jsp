<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/color.css" rel="stylesheet" type="text/css" />
<title>会员登陆</title>
</head>
<body>
<div id="head">
	<div id="logo" class="float_left"></div>
	<div class="float_right txt_right" style="margin-top:5px; line-height:24px;"><p><a href="index.html" target="_blank">返回首页</a>&nbsp;|&nbsp;<a href="about/vip.asp" target="_blank">帮助</a></p><p class="top_tel">&nbsp;</p></div>
    <div class="clearfloat"></div>
</div>
<div id="main" style="position:relative;">
    <div class="txt_left" style="position:relative; height:530px;">
		<div class="width_10px"></div>
		<div class="width_10px"></div>
		<div class="login_banner"></div>
    	<div class="graybg font_14px txt_left" style="line-height:30px; height:30px; padding-left:10px;"><strong>登陆后，您可以发布以下信息</strong></div>
    	<ul id="login_mleft">
        	<li class="icon_login1">
            	<p class="font_14px"><strong>发布供应和产品信息</strong></p>
                <p class="gray">图文并茂展示产品，让千万买家主动找到您！</p>
            </li>
            <li class="icon_login2">
            	<p class="font_14px"><strong>发布企业介绍</strong></p>
                <p class="gray">提升企业知名度，让生意火起来！</p>
            </li>
            <li class="icon_login3">
            	<p class="font_14px"><strong>申请企业旺铺</strong></p>
                <p class="gray">量身打造全能企业网站,塑造企业网络形象!</p>
            </li>
            <li class="icon_login4">
            	<p class="font_14px"><strong>招聘人才</strong></p>
                <p class="gray">展示企业实力，吸引优秀人才加盟！</p>
            </li>
        </ul>
    </div>
    <div class="login_content">
         <script language="JavaScript">
<!--
function on_forgetpwd()
{
     if (document.Login.Typ(0).checked ==true)
     {
        document.Login.action="individual/Chkadmin.asp";
        document.Login.submit();
     }
     else
     {
        document.Login.action="user/Chkadmin.asp";
        document.Login.submit();
     }
      return false;
 }
// -->
</script>
<script language="JavaScript">
<!--
function resetBtn(fm){
    fm.reset();
    return false;
}
// -->
</script>
<form  name="Login" method="post" action="">
<input name="regjm" type="hidden" value="6447">
    	<ul>
        	<li class="login_content1"></li>
            <li class="login_content2">
            	<h1>马上登陆，免费发布信息</h1>
                <p>用户名：<input name="user" type="text" class="input" style="width:148px;" /></p>
                <p>密&nbsp;&nbsp;码：<input type="password" name="pass" class="input" style="width:148px;" /></p>
                <p>验证码：<input name="regjm1" type="text" class="input" style="width:68px;" /><span class="yanzheng red">6447</span></p>
                <p><input name="Typ" type="radio" value="JobSek" onfocus=this.blur() />&nbsp;个人会员&nbsp;&nbsp;<input type="radio" name="Typ" value="comp" checked="checked" onfocus=this.blur() />&nbsp;企业会员</p>
            </li>
            <li class="login_content3">
            	<p><input name="image" type="image" style="border:0; vertical-align:middle;" onClick="return on_forgetpwd();" src="images/button_login.gif"  onfocus=this.blur()></p>
                <div class="login_line"></div>
                <div style="height:auto; overflow:auto; zoom:1;">
               	<h1 style="margin-bottom:10px;">网上成功三部曲</h1>
                <p class="login_suc1">&nbsp;不是会员&nbsp;&nbsp;<a href="reg.asp"><img src="images/reg.gif" width="100" height="30" /></a></p>
                <p class="login_suc2">已是会员&nbsp;&nbsp;免费宣传产品&nbsp;&nbsp;&nbsp;</p>
                <p class="login_suc3">推广公司&nbsp;&nbsp;让生意火起来&nbsp;&nbsp;&nbsp;</p>
                <p class="font_14px red" style="margin:20px 0 0 20px; background:url(images/tel.gif) no-repeat 110px center; text-align:left;"><strong>企业推广请咨询</strong></p>
                </div>
            </li>
            <li class="login_content4"></li>
        </ul>
</form>
    </div>
</div>
    <div id="footer"><table width="760" height="147" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><div align="center">
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0" width="800" height="150">
                      <param name="movie" value="/images/foot.swf" />
                      <param name="quality" value="high" />
                      <param name="wmode" value="opaque" />
                      <embed src="/images/foot.swf" quality="high" wmode="opaque" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="800" height="150"></embed>
</object></div>
  </tr></table><script src="http://s15.cnzz.com/stat.php?id=2670460&web_id=2670460&show=pic1" language="JavaScript"></script><br></div>
 </body>
</html>

