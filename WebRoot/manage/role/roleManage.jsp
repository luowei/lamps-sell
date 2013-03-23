<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD> 
<TITLE>角色管理</TITLE>
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


<script type="text/javascript" charset="utf-8">
			jQuery(function($){
			$('#startDate').datepicker({
					yearRange: '1900:2099', //取值范围.
					showOn: 'both', //输入框和图片按钮都可以使用日历控件。
					buttonImage: '${ctx}/js/date/calendar.gif', //日历控件的按钮
					buttonImageOnly: true,
					showButtonPanel: true
				});	
			$('#endDate').datepicker({
					yearRange: '1900:2099', //取值范围.
					showOn: 'both', //输入框和图片按钮都可以使用日历控件。
					buttonImage: '${ctx}/js/date/calendar.gif', //日历控件的按钮
					buttonImageOnly: true,
					showButtonPanel: true
				});	
			//$('#userName').attr("value","aa");//填充内容	
			});
		</script>


	

</HEAD>
<BODY 
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(${ctx }/manage/images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="${ctx }/manage/index/Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(${ctx }/manage/images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">角色管理</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>

    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign="top">
     <form action="${ctx }/manage/role/list.do" method="post">
<table>
	<tr>
		<td height="23">角色名</td>
		<td><input type="text" name="key" id="roleName" value="${key }"/></td>
		<td>开始时间</td>
		<td><input type="text" name="startDate" id="startDate" value="${startDate }"></td>
		<td>结束时间</td>
		<td><input type="text" name="endDate" id="endDate" value="${endDate }"></td>
		<td><input type="submit" value="搜索"/>
		<a href="${ctx }/manage/role/addRole.jsp">增加</a></td>
	</tr>
	<tr>
	<td height="22" colspan="7">
		 <img alt="搜索" src="${ctx }/manage/images/searchbg1.gif">		
		 <c:forEach items="${filterList}" var="condition" varStatus="s">
			<c:if test="${s.count!=1}">
				+
			</c:if>
			${condition.displayName }<a href="${ctx }/manage/role/list.do${condition.url}">删除</a>
		 </c:forEach>
	</td>
	</tr>
</table>
</form>
     <TABLE class=gridView id=ctl00_ContentPlaceHolder2_GridView1 
      style="WIDTH: 100%; BORDER-COLLAPSE: collapse" cellSpacing=0 rules=all 
      border=0>
                <TR >                
                  <TH>序号</TH>
                  <TH class=gridViewHeader scope=col>角色Id 
                  <a href="${ctx }/manage/role/list.do${order }id:asc">升</a>
                  <a href="${ctx }/manage/role/list.do${order }id:desc">降</a></TH>
                  <TH class=gridViewHeader scope=col>角色名</TH>
                  <TH class=gridViewHeader scope=col>角色详情</TH>
                  <TH class=gridViewHeader scope=col>权限</TH>
                  <TH class=gridviewHeader scope=col>
             	     时间
                  <a href="${ctx }/manage/role/list.do${order }createTime:asc">升</a>
                  <a href="${ctx }/manage/role/list.do${order }createTime:desc">降</a>
				 </TH>
				  
                  <TH class=gridviewHeader scope=col>更新</TH>
                  <TH class=gridviewHeader scope=col>删除</TH>
                </TR>
               
               <c:set var="i" value="1" />
                <c:forEach items="${roleList}" var="role" varStatus="s">
                <TR id="rw${i}" onmouseover="changeCss(${i})" onmouseout="recoverCss(${i})">
                  <TD class=gridViewItem style="WIDTH: 50px"><c:out value="${index+s.index}"></c:out> </TD>
                  <TD class=gridViewItem>${role.id}</TD>
                  <TD class=gridViewItem>${role.roleName}</TD>
                  <TD class=gridViewItem>${role.roleInfo}</TD>
                  <TD class=gridViewItem><A class=cmdField 
            href="${ctx }/manage/role/editAction/${role.id}.do">权限</A>
            -<A class=cmdField 
            href="${ctx }/manage/role/editMenu/${role.id}.do">菜单</A>
            </TD>
                  <TD class=gridViewItem>${role.createTime}</A></TD>          
            </TD>
                  <TD class=gridViewItem><A class=cmdField 
            href="${ctx }/manage/role/edit/${role.id}.do">编辑</A></TD>
                  <TD class=gridViewItem><A class=cmdField 
            href="javascript:if(confirm('确实要删除吗?'))location.href='${ctx }/manage/role/delete/${role.id}.do';">删除</A> </TD>
                </TR><c:set var="i" value="${i+1}" />
             </c:forEach>
            </TABLE>
            ${tag }
      </TD>  
    </TR>
         <TR>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_bl.gif"></TD>
      <TD style="BACKGROUND-IMAGE: url(${ctx }/manage/images/bg_left_bc.gif)"></TD>
      <TD width=10><IMG 
src="${ctx }/manage/index/Left.files/bg_left_br.gif"></TD>
    </TR>
      
  </TBODY>

</TABLE>  

</BODY>
</HTML>
