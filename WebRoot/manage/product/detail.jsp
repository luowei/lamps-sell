<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>${product.name }详细信息</TITLE>
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
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(../images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">${product.name }详细信息</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top>
    <div style="float: right"><a href="javascript:history.go(-1)">返回</a></div>
        <table width="591" height="433" border="0" align="center">
          <tr>
            <td colspan="2"><div align="center"><span class="STYLE2">${product.name }详细信息</span></div></td>
            </tr>
          <tr>
            <td width="101" align="right">名称:</td> 
     	 	<td width="120">${product.name }</td> 
          </tr>
          <tr>
		  <td align="right">产品分类:</td> 
     	 <td>${product.category.name }</td> 
		</tr>
          <tr>
         <td align="right">状态:</td> 
	     <td>${product.status }</td> 
          </tr>
          <tr>
         <td align="right">销量:</td> 
	     <td>${product.salesValume }</td> 
          </tr>
          <tr>
            <td align="right">生产地:</td> 
    	    <td>${product.produceArea }</td> 
          </tr>
          <tr>
            <td align="right">小图:</td>
            <td><img src="${product.smallImg }" width="100px" height="100px"/></td> 
          </tr>
          <tr>
            <td align="right">大图:</td>
            <td><img src="${product.bigImg }" width="200px" height="200px"/></td> 
          </tr>
          <tr>
            <td align="right">生产日期:</td>
            <td>${product.createtime }</td> 
          </tr>
          <tr>
            <td align="right">价格:</td>
            <td>${product.price }</td> 
          </tr>
          <tr>
            <td align="right">数量:</td>
     	    <td>${product.num }</td> 
          </tr>
          <tr>
            <td align="right">最小数量:</td>
     	    <td>${product.minNum }</td> 
          </tr>
          <tr>
            <td align="right">排列序号:</td>
     	    <td>${product.orders }</td> 
          </tr>
          <tr>
            <td align="right">详细信息:</td>
     	    <td>${product.detail }</td> 
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
