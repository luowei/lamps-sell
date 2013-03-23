<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>新增用户</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css> 
{
	FONT-SIZE: 12px
}
#menuTree A {
	COLOR: #566984; TEXT-DECORATION: none
}
.STYLE2 {font-size: x-large}
</STYLE>

<style type="text/css" media="all"> 
body,div{font-size:12px;}
</style> 

<META content="MSHTML 6.00.2900.5848" name=GENERATOR>


	

</HEAD>
<BODY 
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(${ctx}/manage/images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="${ctx }/manage/index/Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">分配角色</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_ls.gif)"></TD>
      <TD id=menuTree 
    style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top></TD>
      <TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_rs.gif)"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top>
      <form name="form1" id="form1" method="post" action="${ctx }/manage/user/updateRole.do">
        <table width="591" height="346" border="0" align="center">
          <tr>
            <td height="38" colspan="2"><div align="center"><span class="STYLE2">分配角色</span></div></td>
            </tr>
          <tr>
            <td width="148" height="45"  align="right">用户名:</td> 
     	 <td width="433">${user.userName}
     	 <input type="hidden" name="id" value="${user.id }"/>
     	 </td> 
   		    </tr>
          <tr>
 
      <td height="48" align="right">注册时间:</td> 
      <td>${user.createTime}</td> 
      </tr>
          <tr>
            <td height="173" align="right" valign="top">分配角色:</td> 
    	    <td valign="top">
			<c:forEach items="${roleList}" var="role" varStatus="s">
	   	        <input name="roles" type="checkbox"  value="${role.id}"  
		   	        <c:forEach items="${user.roles}" var="roleInfo" varStatus="st">
			   	            <c:if test="${role.id==roleInfo.id }">
			   	       		 checked
			   	       		 </c:if>
		   	        </c:forEach>
	   	        >${role.roleName}<br/>
	   	        
   	        </c:forEach>   	
   	         </td>
		</tr>
           
          <tr>
            <td height="30">&nbsp;</td>
            <td><input type="submit" name="button" id="button" value="提交" /></td>
            </tr>
        </table>
        ：
      </form>
      </TD>  
    </TR>
         <TR>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_bl.gif"></TD>
      <TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_bc.gif)"></TD>
      <TD width=10><IMG 
src="${ctx }/manage/index/Left.files/bg_left_br.gif"></TD>
    </TR>
      
  </TBODY>

</TABLE>

</BODY>
</HTML>
