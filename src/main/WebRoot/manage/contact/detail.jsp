<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>${contact.name }详细信息</TITLE>
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
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(../images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="${ctx }/manage/index/Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(../images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">${contact.name }的详细信息</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top><div style="float: right"><a href="javascript:history.go(-1)">返回</a></div>
        <table width="591" height="433" border="0" align="center">
          <tr>
            <td colspan="2"><div align="center"><span class="STYLE2">${contact.name }详细信息</span></div></td>
            </tr>
          <tr>
            <td width="150" align="right">联系ID:</td> 
     	 	<td >${contact.id }</td> 
          </tr>  
          <tr>
            <td width="150" align="right">联系名称:</td> 
     	 	<td >${contact.name }</td> 
          </tr>
          <tr>
            <td width="101" align="right">企业:</td> 
     		<td width="342">${contact.complany.name }</td> 
          </tr>
          <tr>
		  <td align="right">电话:</td> 
     	 <td>${contact.tel }</td> 
		</tr>
          <tr>
         <td align="right">手机:</td> 
     	  <td>${contact.phone }</td>
          </tr>
          <tr>
            <td align="right">email:</td> 
     	    <td>${contact.email }</td> 
          </tr>
          <tr>
            <td align="right">qq:</td> 
     	    <td>${contact.qq }</td> 
          </tr>
          <tr>
            <td align="right">网址:</td>
     	    <td>${contact.http }</td> 
          </tr>
          <tr>
            <td align="right">传真:</td>
     	    <td>${contact.fax }</td> 
          </tr>
          <tr>
            <td align="right">邮编:</td>
     	    <td>${contact.zip }</td> 
          </tr>
          <tr>
            <td align="right">地址:</td>
     	    <td>${contact.addr }</td> 
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
