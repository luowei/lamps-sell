<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>修改联系人</TITLE>
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
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(../images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">企业资料</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top><div style="float: right"><a href="javascript:history.go(-1)">返回</a></div>
      <form name="form1" id="form1" method="post" action="${ctx }/manage/contact/update.do">
        <table width="591" height="433" border="0" align="center">
          <tr>
            <td colspan="3"><div align="center"><span class="STYLE2">修改联系人</span></div></td>
            </tr>
          <tr>
            <td width="101" align="right">Id:</td> 
     	 <td width="120"><input type="text" id="Id" name="Id" style="width:120px" value="${contact.id }" readonly/></td> 
     		 <td width="342"><div id="idTip" style="width:250px"></div></td> 
          </tr> 
          <tr>
            <td width="101" align="right">名称:</td> 
     	 <td width="120"><input type="text" id="name" name="name" style="width:120px" value="${contact.name }" /></td> 
     		 <td width="342"><div id="nameTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td width="101" align="right">企业:</td> 
     	 <td width="120"><input type="hidden" name="complanyId" value="${contact.complany.id }"/>${contact.complany.name }
<%--     	 <c:if></c:if>--%>
<%--		 <select name="complanyId"><c:forEach var="complany" items="${complanyList }">--%>
<%--				<option value="${complany.id }">${complany.name }</option>--%>
<%--		</c:forEach></select>--%>
		 </td> 
     		 <td width="342"><div id="complanyTip" style="width:250px"></div></td> 
          </tr>
          <tr>
		  <td align="right">电话:</td> 
     	 <td><input type="text" id="tel" name="tel" style="width:120px" value="${contact.tel }"/></td> 
     	 <td><div id="telTip" style="width:250px"></div></td> 
		</tr>
          <tr>
         <td align="right">手机:</td> 
	     <td><input type="text" name="phone" id="phone" style="width:120px" value="${contact.phone }"/></td> 
     	  <td><div id="phoneTip" style="width:250px"></div></td>
          </tr>
          <tr>
            <td align="right">email:</td> 
    	    <td><input type="text" id="email" name="email" style="width:120px" value="${contact.email }"/></td> 
     	    <td><div id="qqTip" style="width:250px"></div></td> 
          </tr>
         <tr>
            <td align="right">qq:</td>
            <td><input type="text" id="qq" name="qq" style="width:120px" value="${contact.qq }"/></td> 
     	    <td><div id="qqTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">网址:</td>
            <td><input type="text" id="http" name="http" style="width:200px" value="${contact.http }"/></td> 
     	    <td><div id="httpTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">传真:</td>
            <td><input type="text" id="fax" name="fax" style="width:120px" value="${contact.fax }"/></td> 
     	    <td><div id="faxTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">邮编:</td>
            <td><input type="text" id="zip" name="zip" style="width:120px" value="${contact.zip }"/></td> 
     	    <td><div id="zipTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">地址:</td>
            <td><input type="text" id="addr" name="addr" style="width:250px" value="${contact.addr }"/></td> 
     	    <td><div id="addrTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">排列序号:</td>
            <td><input type="text" id="orders" name="orders" style="width:120px" value="${contact.orders }"/></td> 
     	    <td><div id="ordersTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><input type="submit" name="button" id="button" value="提交" /> </td>
            <td>&nbsp;</td>
          </tr>
  
        </table>
            </form>
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
