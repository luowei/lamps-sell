<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>留言详细信息</TITLE>
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

	

</HEAD>
<BODY 
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(../images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="${ctx }/manage/index/Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(../images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">留言详细信息</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top>
    <div style="float: right"><a href="javascript:history.go(-1)">返回</a></div>
        <table width="591" height="433" border="0" align="center">
          <tr>
            <td colspan="2"><div align="center"><span class="STYLE2">留言详细信息</span></div></td>
            </tr>
          <tr>
            <td width="101" align="right">Id:</td> 
     	 	<td width="120">${message.id }</td> 
          </tr>
          <tr>
            <td width="101" align="right">类型:</td> 
     	 	<td >${message.type }</td> 
     		 <td width="342"><div id="typeTip" style="width:250px"></div></td> 
          </tr>
          <tr>
         <td align="right">留言信息:</td> 
	     <td width="300">${message.detail }</td> 
          </tr>
          <tr>
            <td align="right">留言者:</td> 
    	    <td >${message.user.userName }</td> 
          </tr>
          <tr>
            <td align="right">产品名:</td>
            <td>${message.product.name }</td> 
          </tr>
          <tr>
            <td align="right">企业名:</td>
            <td>${message.complany.name }</td> 
          </tr>
          <tr>
            <td align="right">生产日期:</td>
            <td>${product.createtime }</td> 
          </tr>
          <tr>
         	<td align="right">状态:</td> 
	     	<td>${message.status }</td> 
          </tr>
          <tr>
            <td align="right">留言时间:</td>
     	    <td>${message.createtime }</td> 
          </tr>
          <tr>
            <td align="right">排列序号:</td>
     	    <td>${message.orders }</td> 
          </tr>
        </table>
      </TD>  
    </TR>
         <TR>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_bl.gif"></TD>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_bc.gif)"></TD>
      <TD width=10><IMG 
src="${ctx }/manage/index/Left.files/bg_left_br.gif"></TD>
    </TR>
      
  </TBODY>

</TABLE>

</BODY>
</HTML>
