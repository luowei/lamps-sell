<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>--%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${ctx }/css/common.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${ctx }/css/new_style.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript">
function setTab(name,cursel,n){
	for(i=1;i<=n;i++)
	{
		if(cursel==1){
			var pForm=document.getElementById("searchForm_1");
         	pForm.setAttribute("action","${ctx }/head/product.php");
			//document.searchForm.action="${ctx }/head/product.php";
			//searchForm.setAttribute( "action","${ctx }/head/product.php");
		}
		if(cursel==2){
			var pForm=document.getElementById("searchForm_2");
         	pForm.setAttribute("action","${ctx }/head/complany.php");
			//document.searchForm.action="${ctx }/head/complany.php";
			//searchForm.setAttribute( "action","${ctx }/head/complany.php");
		}
		if(cursel==3){
			var pForm=document.getElementById("searchForm_3");
         	pForm.setAttribute("action","${ctx }/head/tradinfo.php");
			//document.searchForm.action="${ctx }/head/tradinfo.php";
			//searchForm.setAttribute( "action","${ctx }/head/tradinfo.php");
		}
		if(cursel==4){
			var pForm=document.getElementById("searchForm_3");
         	pForm.setAttribute("action","${ctx }/head/price.php");
			//document.searchForm.action="${ctx }/head/price.php";
			//searchForm.setAttribute( "action","${ctx }/head/price.php");
		}
		var menu=document.getElementById(name+i);
		var con=document.getElementById("con_"+name+"_"+i);
		
		menu.className=i==cursel?"hover":"";
		if(con != null)
			con.style.display=i==cursel?"block":"none";
	}
}
function show(name)
{
	var popup = document.getElementById(name);
	popup.style.display = "block";
}
function hide(name)
{
	var popup = document.getElementById(name);
	popup.style.display = "none";
}

function login(){
	var msg="<div style='text-align: center;padding-top: 20px;'>"+
		"<form action='${ctx }/head/login.php'method='post'>"+
		"用户名:<input name='userName' style='width: 150px'/><br/>"+
		"密码:&nbsp;&nbsp;<input type='password' name='userPassword' style='width: 150px'/><br/><br/>"+
		"<input type='submit' value='确定'/>"+
		"</form></div>";
	alertWin("用户登录", msg, 400, 160);
}

function register(){
	var msg = "<form name='regForm' action='${ctx }/head/reg.php' method='post'>"+
	"<table align='center' cellpadding='0' >"+
	"<tr ><td align='right'>用户名：</td><td align='left'><input type='text' name='userName' style='width:160px;'/></td></tr>"+
	"<tr ><td align='right'>密码：</td><td align='left'><input type='password' name='userPassword' style='width:160px;'/></td></tr>"+
	"<tr ><td align='right'>确定密码：</td><td align='left'><input type='password' name='repassword' style='width:160px;'/></td></tr>"+
	"<tr ><td align='right'>性别：</td><td align='left'><select name='sex'><option value='男'>男</option>"+
	"<option value='女'>女</option></select></td></tr>"+
	"<tr ><td align='right'>email：</td><td align='left'><input type='text' name='email' style='width:160px;;'/></td></tr>"+
	"<tr ><td align='right'>qq：</td><td align='left'><input type='text' name='qq' style='width:160px;'/></td></tr>"+
	"<tr ><td align='right'>简介：</td  style='width:160px;'>"+
	"<td align='left'><textarea name='content' style='width:160px;height=100px;' rows='6'></textarea></td></tr>"+
	"<td colspan='2' align='center'><input type='submit' value='提交'/></td>"+
	"</table></form>";
	alertWin("用户注册",msg,300,300);
}

function myCart(){
	var user="${sessionScope.user}";
	if(user==""){
		var msg="<div style='text-align: center;padding-top: 10px;'>"+
		"<span style='font-weight:bolder;font-color:green'>请先登录！</span><br/></div>";
		alertWin("您还未登录", msg, 200, 60);
		return;
    }
	location.href='${ctx }/cart/list.php';
}

function myOrder(){
	var user="${sessionScope.user}";
	if(user==""){
		var msg="<div style='text-align: center;padding-top: 10px;'>"+
		"<span style='font-weight:bolder;font-color:green'>请先登录！</span><br/></div>";
		alertWin("您还未登录", msg, 200, 60);
		return;
    }
	location.href="${ctx }/orders/list.php";
}

</script>

<script type="text/javascript" language="javascript" src="${ctx }/js/window.js"></script>
	
<script src="${ctx }/js/scripts/jquery-latest.js" type="text/javascript"></script>    
<script src="${ctx }/js/scripts/Jquery.L.Message.js" type="text/javascript"></script>
</head>
<body class="topbar-bg01">
<%--<c:if test="${login_flag eq 'success' }">--%>
<%--	<div id="loginMsg" style="height: 350px;"><ul>--%>
<%--      <li><a href="javascript:$('#loginMsg').messagebox('${sessionScope.user.name }<br/>登录成功', '', 1, 20000);">...</a></li>--%>
<%--      <li><a href="javascript:$('#loginMsg').messagebox('错误提示<br/>错误提示', '', 0, 1000);">失败，1秒消失</a></li>--%>
<%--      <li> <a href="javascript:$('body').messagebox('这是警告<br/>这是警告', 'back', 2);">警告，默认时间5秒后后退</a></li>--%>
<%--     </ul></div>--%>
<%--</c:if>--%>
<%--<c:if test="${login_flag eq null }">--%>
<%--	<div id="loginMsg" style="height: 350px;"><ul>--%>
<%--       <li> <a href="javascript:$('#loginMsg').messagebox('登录失败', '', 0, 1000);">失败，1秒消失</a></li>--%>
<%--     </ul></div>--%>
<%--</c:if>--%>
<c:if test="${param.login_flag eq 'success' }">
	<script type="text/javascript">
		var msg="<div style='text-align: center;padding-top: 10px;'>"+
			"<span style='font-weight:bolder;font-color:green'>登录成功！</span><br/></div>";
		alertWin("成功", msg, 200, 60);
	</script>
</c:if>
<c:if test="${param.login_flag eq 'failed' }">
	<script type="text/javascript">
		var msg="<div style='text-align: center;padding-top: 10px;'>"+
			"<span style='font-weight:bolder;font-color:green'>用户名或密码错误！</span><br/></div>";
		alertWin("登录失败", msg, 200, 60);
	</script>
</c:if>
<c:if test="${param.register_flag eq 'success' }">
	<script type="text/javascript">
		var msg="<div style='text-align: center;padding-top: 10px;'>"+
			"<span style='font-weight:bolder;font-color:green'>注册成功！</span><br/></div>";
		alertWin("成功", msg, 200, 60);
	</script>
</c:if>
<c:if test="${param.register_flag eq 'failed' }">
	<script type="text/javascript">
		var msg="<div style='text-align: center;padding-top: 10px;'>"+
			"<span style='font-weight:bolder;font-color:green'>注册失败！</span><br/></div>";
		alertWin("登录失败", msg, 200, 60);
	</script>
</c:if>
	<div id="topbar01">
        <div id="name">欢迎访问本站！</div>
        <div class="right">
            <div id="nav"class="top_tel">
                <a href="javascript:" target="_blank">网站介绍</a> ┊
                <a href="javascript:" target="_blank">联络本站</a> ┊
                <c:if test="${sessionScope.user ne null}">
                <a href="${ctx }/head/user_exit.php">退出</a>
                </c:if>
            </div>
            <div id="btns">
            <c:if test="${sessionScope.user eq null || sessionScope.user.id eq null}">
                <a class="button01" href="javascript:" onclick="register()">免费注册</a>
                <a class="button01" href="javascript:"  onclick="login()">立即登陆</a>
            </c:if>
            <c:if test="${sessionScope.user ne null && sessionScope.user.id ne null}">
            	<a class="button01" style="font-weight: bold;background:url()" href="${ctx }/head/user_detail.php" target="_blank">${user.userName }</a>
                <c:if test="${sessionScope.user.isBetter ne 0 }">
                <a class="button01" href="${ctx }/head/user_vip.php?${user.id }" target="_blank" 
                 style="background:url(${ctx }/images/skin/numbtns.gif) no-repeat  -2px ${(sessionScope.user.isBetter-1)*-24-2 }px;">
                </a>
                </c:if>
            </c:if>
            </div>
        </div>
    </div>
    <div id="header">
    	<table cellpadding="0">
        	<tr>
                <td id="logo"><a href="${ctx }/head/index.php"><img src="${ctx }/images/logo.gif" alt="" /></a></td>
                <td id="nav">
                	<div class="row s_clear">
                        <div class="left">
                            <div class="searchbar"><div class="bar-wrap"><div class="bar-body">
                                <div class="search-tab">
                                    <ul>
                                    <li id="one1"  class="hover" onClick="setTab('one',1,4)"><a>产品库</a></li>
                                     <li id="one2" onClick="setTab('one',2,4)"><a>企业库</a></li>
                                     <li id="one3" onClick="setTab('one',3,4)"><a>供求信息</a></li>
                                     <li id="one4" onClick="setTab('one',4,4)"><a>报价中心</a></li>
                                    </ul>
                                </div>
								<c:forEach var="i" begin="1" step="1" end="5">
								<c:choose>
									<c:when test="${i eq 1}">
										<div class="search-form" id="con_one_${i}">
									</c:when>
									<c:otherwise>
										<div class="search-form" id="con_one_${i }" style="display:none;">
									</c:otherwise>
								</c:choose>
										<form id="searchForm_${i }" name="searchForm_${i }" method="post" action="${ctx }/head/product.php" target="_blank">
<%--								          <input name="key" type="hidden" id="key" value="sca" />--%>
								              <div class="fldinput">
								                  <div class="search-input"><div class="input-wrap">
								                      <input name="key" id="key" />
								                  </div></div>
								              </div>
								              <div class="right">
								                  <input type="submit" class="button01 button01-blue" value="搜 索" />
								              </div>
								          </form>
								    </div>
								</c:forEach></div>
                            </div></div>
                        </div>
                        <div class="weather"><div id="cart" style="padding-left: 20px;float: left;padding-top: 5px;">
                        	<span style="font-size: 16px;color: green;font-weight: bolder">
                        	<a href="#" onclick="myCart()"><img src="${ctx }/images/cart.jpg"/>我的购物车</a></span>
                        	<span style="font-size: 16px;color: green;font-weight: bolder">
                        	<a href="#" onclick="myOrder()"><img src="${ctx }/images/favor.jpg"/>我的订单</a></span>
                        </div></div>
                        
                        
                    </div>
                    <div class="search-key">热门关键词：
                    <a href="${ctx }/head/product.php?categoryId=2">室内灯具</a> 
                    <a href="${ctx }/head/product.php?categoryId=3">户外灯具</a> 
                    <a href="${ctx }/head/product.php?categoryId=4">彩灯照明</a> 
                    <a href="${ctx }/head/product.php?categoryId=5">电光源类</a> 
                    </div>
                </td>
            </tr>
        </table>        
		<div class="navgation"><div class="nav-wrap"><div class="nav-body">
            <div class="menu">
                <ul>
                	<li id="menu1"><a href="${ctx }/head/index.php"  class="hover" onClick="setTab('menu',1,6)">首页</a></li>
                    <li id="menu3"><a href="${ctx }/head/complany.php" onClick="setTab('menu',3,6)">企业库</a></li>
                    <li id="menu4"><a href="${ctx }/head/product.php" onClick="setTab('menu',4,6)">产品库</a></li>
                    <li id="menu2"><a href="#" onClick="setTab('menu',2,6)">供求信息</a></li>
                    <li id="menu5"><a href="#" onClick="setTab('menu',5,6)">新闻资讯</a></li>
                    <li id="menu6"><a href="#" onClick="setTab('menu',6,6)">报价中心</a></li>
                </ul>
            </div>
            <div class="submenu">
            	<a class="right" href="#" style="cursor:hand;" mce_href="#" 
            		onClick="this.style.behavior='url(#default#homepage)';this.setHomePage('http://blog.163.com/luowei505050@126');">加入我的博客</a>
				<b>标签导航</b>
                <label>
                    <a href="${ctx }/head/category/list.php" target="_blank">类别</a>
                    <a href="${ctx }/head/product.php" target="_blank">产品</a>
                    <a href="${ctx }/head/complany.php" target="_blank">企业</a>
                    <a href="${ctx }/head/message.php" target="_blank">留言</a>
                    <a href="#" target="_blank">信息</a>
                </label>
            </div>
            <div class="affichebox">
            	<c:if test="${sessionScope.user eq null || sessionScope.user.isBetter eq 0 }">
            	<div class="btns">
                	<a class="button02" href="javascript:"><span><span>加入本站<b class="orange">vip</b>会员！</span></span></a>
                </div>
                </c:if>
                <c:if test="${sessionScope.user ne null && sessionScope.user.isBetter ne 0 }">
            	<div class="btns">
                	<a class="button02" href="javascript:"><span><span>升<b class="orange">vip</b>等级</span></span></a>
                </div>
                </c:if>
            	<ul>
                  <li><A href="${ctx }/head/index.php" target=_blank>灯具销售网欢迎您！！！...</A></li>
                </ul>
            </div>
        </div></div></div>
    </div>
