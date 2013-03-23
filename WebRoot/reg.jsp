<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>用户注册-灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
		<div class="rows box01-wrap">
        	<div class="box-blue list-table"">
        		<div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>用户注册</a></li></ul>
                    </div>
                </div>
                <table cellpadding='0' width='956px' >
                	<tr ><td align='right'>用户名：</td><td align='left'><input type='text' name='userName' style='width:100px;'/></td></tr>
                	<tr ><td align='right'>密码：</td><td><input align='left' type='text' name='password' style='width:100px;'/></td></tr>
                	<tr ><td align='right'>确定密码：</td><td><input align='left' type='text' name='repassword' style='width:100px;'/></td></tr>
                	<tr ><td align='right'>性别：</td><td><input align='left' type='text' name='sex' style='width:100px;'/></td></tr>
                	<tr ><td align='right'>email：</td><td><input align='left' type='text' name='email' style='width:100px;'/></td></tr>
                	<tr ><td align='right'>qq：</td><td><input align='left' type='text' name='qq' style='width:100px;'/></td></tr>
                	<tr ><td align='right'>简介：</td><td><input align='left' type='text' name='content' style='width:100px;'/></td></tr>
                </table>
            </div>
        </div>
	</div>
	
<jsp:include page="foot.jsp"></jsp:include></body>
</html>