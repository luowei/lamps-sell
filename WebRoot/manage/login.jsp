<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../commons/head.jsp" %>
<%@ include file="../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>

<TITLE>后台登录</TITLE>
<LINK 
href="${pageContext.request.contextPath}/manage/images/public.css" type=text/css rel=stylesheet>
<LINK 
href="${pageContext.request.contextPath}/manage/images/login.css" type=text/css rel=stylesheet>
<STYLE type=text/css>
</STYLE>



<script type="text/javascript">
	$(document).ready(function(){
	//初始化信息
	$.formValidator.initConfig({formid:"form1",alertmessage:true,onerror:function(msg){alert

(msg)}});
		//校验脚本
		$("#userName").formValidator().inputValidator({min:1,onerror:"用户名不能为空,请确认"});
		
		$("#password").formValidator().inputValidator({min:1,onerror:"密码不能为空,请确认"});
	});</script>
</HEAD>
<BODY>
  <form action="${pageContext.request.contextPath}/manage/user/login.do" method="post" name="form1" id="form1" >
<DIV id=div1>

  <TABLE id=login height="100%" cellSpacing=0 cellPadding=0 width=800 
align=center>
    <TBODY>
      <TR id=main>
        <TD>
          <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
              <TR height=30>
                <TD width=380></TD>
                <TD>${message }</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD rowSpan=4>&nbsp;</TD>
                <TD>用户名：</TD>
                <TD>
                  <INPUT class="textbox" id="userName" name="userName">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD>密　码：</TD>
                <TD>
                  <INPUT class="textbox" id="password" type="password" 
            name="password">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
          
              <TR height="40">
                <TD></TD>
                <TD align=right>
                  <INPUT id="btnLogin" type="submit" value=" 登 录 " name="btnLogin">
                </TD>
                <TD width="120">&nbsp;</TD>
              </TR>
              <TR height="110">
                <TD colSpan="4">&nbsp;</TD>
              </TR>
            </TBODY>
          </TABLE>
        </TD>
      </TR>
      <TR id="root" height="104">
        <TD>&nbsp;</TD>
      </TR>
    </TBODY>
  </TABLE>
</DIV>
<DIV id="div2" style="DISPLAY: none"></DIV>
</CONTENTTEMPLATE>
</form>
</BODY>
</HTML>
