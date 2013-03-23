<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp"%>
<%@ include file="../../commons/js.jsp"%>
<%@ include file="../../commons/dtree.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>无标题页</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css> 
{
	FONT-SIZE: 12px
}
#menuTree A {
	COLOR: #566984; TEXT-DECORATION: none
}
</STYLE>
<SCRIPT src="Left.files/TreeNode.js" type=text/javascript></SCRIPT>
<SCRIPT src="Left.files/Tree.js" type=text/javascript></SCRIPT>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
</HEAD>
<BODY 
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(../images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url('../images/bg_left_tc.gif'); COLOR: white; FONT-FAMILY: system">主菜单</TD>
      <TD width=10><IMG src="Left.files/bg_left_tr.gif">
  

      </TD>
    </TR>
    <TR>
      <TD style="BACKGROUND-IMAGE: url('../images/bg_left_ls.gif')"></TD>
      <TD id=menuTree 
    style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top>
	                <div class="dtree" align="left">

<p align="left">
	<a href="javascript: d.openAll();">展开</a> |
	<a href="javascript: d.closeAll();">关闭</a>

<script type="text/javascript"> 		
		d = new dTree('d');
		d.icon = {
		root				: '${ctx}/manage/images/tree/folder_closed.gif',
		folder			: '${ctx}/manage/images/tree/folder_closed.gif',
		folderOpen	: '${ctx}/manage/images/tree/folder_open.gif',
		node				: '${ctx}/manage/images/tree/tree_node.gif',
		empty				: '${ctx}/manage/img/empty.gif',
		line				: '${ctx}/manage/img/line.gif',
		join				: '${ctx}/manage/img/join.gif',
		joinBottom	: '${ctx}/manage/img/joinbottom.gif',
		plus				: '${ctx}/manage/img/plus.gif',
		plusBottom	: '${ctx}/manage/img/plusbottom.gif',
		minus				: '${ctx}/manage/img/minus.gif',
		minusBottom	: '${ctx}/manage/img/minusbottom.gif',
		nlPlus			: '${ctx}/manage/img/nolines_plus.gif',
		nlMinus			: '${ctx}/manage/img/nolines_minus.gif'
	};
		d.add(0,-1,"权限分配");
		<c:forEach items="${userMenuMap}" var="entity" varStatus="s"> 
		    var url=null;
		   
		   <c:if test="${entity.value.url!='' }">
		     url="${ctx}/${entity.value.url}";
		   </c:if>
			d.add(${entity.value.id },${entity.value.parentId },"${entity.value.menuName }",url,"${entity.value.menuName }","mainFrame");			
		</c:forEach>	 
		document.write(d);
	</script>
</p>
</div>
	</TD>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_rs.gif)">
      
 
      
      </TD>
    </TR>
    <TR>
      <TD width=10><IMG src="Left.files/bg_left_bl.gif"></TD>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_bc.gif)">

      </TD>
      <TD width=10><IMG 
src="Left.files/bg_left_br.gif"></TD>

    </TR>
  </TBODY>
</TABLE>
    
   
</BODY>
</HTML>
