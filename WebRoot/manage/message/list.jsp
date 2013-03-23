<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD> 
<TITLE>留言管理</TITLE>
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
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(${ctx }/manage/images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">留言管理</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>

    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign="top">
     <form action="${ctx }/manage/message/list.do" method="post">
<table>
	<tr>
		<td height="23">名称</td>
		<td><input type="text" name="key" id="key" value="${key }"/></td>
		<td>开始时间</td>
		<td><input type="text" name="startDate" id="startDate" value="${startDate }"></td>
		<td>结束时间</td>
		<td><input type="text" name="endDate" id="endDate" value="${endDate }"></td>
		<td><input type="submit" value="搜索"/>
<%--		<a href="${ctx }/manage/message/toAdd.do">增加</a></td>--%>
	</tr>
	<tr>
	<td height="22" colspan="7">
		 <img alt="搜索" src="${ctx }/manage/images/searchbg1.gif">		
		 <c:forEach items="${filterList}" var="condition" varStatus="s">
			<c:if test="${s.count!=1}">
				+
			</c:if>
			${condition.displayName }<a href="${ctx }/manage/message/list.do${condition.url}">删除</a>
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
                  <TH class=gridViewHeader scope=col>Id 
                  <a href="${ctx }/manage/message/list.do${order }id:asc">升序</a>
                  <a href="${ctx }/manage/message/list.do${order }id:desc">降序</a></TH>
                  <TH class=gridViewHeader scope=col>类型</TH>
                  <TH class=gridViewHeader scope=col>详细</TH>
<%--                  <TH class=gridViewHeader scope=col>产品</TH>--%>
<%--                  <TH class=gridViewHeader scope=col>企业</TH>--%>
                  <TH class=gridViewHeader scope=col>留言时间</TH>
                  <TH class=gridViewHeader scope=col>状态</TH>
                  <TH class=gridviewHeader scope=col>
             	    排序序号<br/>
                  <a href="${ctx }/manage/message/list.do${order }orders:asc">升序</a>
                  <a href="${ctx }/manage/message/list.do${order }orders:desc">降序</a>				 </TH>
                  <TH class=gridviewHeader scope=col>更新</TH>
                  <TH class=gridviewHeader scope=col>删除</TH>
                </TR>
               
               	 <c:set var="i" value="1" />
                 <c:forEach items="${messageList}" var="message" varStatus="s">
                <TR id="rw${i}" onmouseover="changeCss(${i})" onmouseout="recoverCss(${i})">
                  <TD class=gridViewItem style="WIDTH: 50px"><c:out value="${index+s.index}"></c:out> </TD>
                  <TD class=gridViewItem>${message.id }</TD>
                  <TD class=gridViewItem>${message.type }</TD>
                  <TD class=gridViewItem><a href="${ctx }/manage/message/detail/${message.id}.do">
                  	<div style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; margin-left: 10px; width: 100px">${message.detail }</div>
                  </a></TD>
<%--                  <TD class=gridViewItem>${message.product.name }</TD>--%>
<%--                  <TD class=gridViewItem>${message.complany.name }</TD>--%>
                  <TD class=gridViewItem>${message.createtime }</TD>
                  <TD class=gridViewItem>${message.status }
                  	<c:if test="${message.status=='下线'}">
						<A class=cmdField
							href="${ctx }/manage/message/updateStatus.do?id=${message.id}&type=false"><img
								src="${ctx }/manage/img/icon_stop.gif"></img>
						</A>
					</c:if>
					<c:if test="${message.status=='上线'}">
						<A class=cmdField
							href="${ctx }/manage/message/updateStatus.do?id=${message.id}&type=true"><img
								src="${ctx }/manage/img/icon_run.gif"></img> </A>
					</c:if>
                  </TD>
                  <TD class=gridViewItem>${message.orders }
                  	<c:if test="${message.status!='下线'}">
						<a href="${ctx }/manage/message/changeOrder.do?id=${message.id }&type=up"><img
								src="${ctx }/manage/img/icon_up.gif"></img></a>
						<a href="${ctx }/manage/message/changeOrder.do?id=${message.id }&type=down"><img
								src="${ctx }/manage/img/icon_down.gif"></img> </a>
					</c:if>
                  </TD>
                  <TD class=gridViewItem><A class=cmdField 
            href="${ctx }/manage/message/edit/${message.id}.do">编辑</A></TD>
                  <TD class=gridViewItem><A class=cmdField 
            href="javascript:if(confirm('确实要删除吗?'))location.href='${ctx }/manage/message/delete/${message.id}.do';">删除</A> </TD>
                </TR>
                <c:set var="i" value="${i+1}" />
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
