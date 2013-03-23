<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
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
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(../images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">Main 
        Menu</TD>
      <TD width=10><IMG src="Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_ls.gif)"></TD>
      <TD id=menuTree 
    style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top></TD>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_rs.gif)"></TD>
    </TR>
    <TR>
      <TD width=10><IMG src="Left.files/bg_left_bl.gif"></TD>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_bc.gif)"></TD>
      <TD width=10><IMG 
src="Left.files/bg_left_br.gif"></TD>

    </TR>
  </TBODY>
</TABLE>
<SCRIPT type=text/javascript>
d = new dTree('d');
d.add(0,-1,"权限分配");
document.write(d);
<!--
var tree = null;
var root = new TreeNode('系统菜单');

var fun1 = new TreeNode('用户管理');
root.add(new TreeNode('人员管理', '${ctx}/manage/user-manage.action', 'tree_node.gif', null, 'tree_node.gif', null));
var fun3 = new TreeNode('角色管理', '${ctx}/manage/role-manage.action', 'tree_node.gif', null, 'tree_node.gif', null);fun1.add(fun3);
var fun4 = new TreeNode('模块管理', '${ctx}/manage/module-manage.action', 'tree_node.gif', null, 'tree_node.gif', null);fun1.add(fun4);
var fun112 = new TreeNode('权限管理', '${ctx}/manage/action-manage.action', 'tree_node.gif', null, 'tree_node.gif', null);fun1.add(fun112);
var fun113 = new TreeNode('菜单管理', '${ctx}/manage/menu-manage.action', 'tree_node.gif', null, 'tree_node.gif', null);fun1.add(fun113);root.add(fun1);
tree = new Tree(root);tree.show('menuTree');
-->

</SCRIPT>
</BODY>
</HTML>
