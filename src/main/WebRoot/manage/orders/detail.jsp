<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>订单详细信息</TITLE>
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
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(../images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">订单详细信息</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top>
    <div style="float: right"><a href="javascript:history.go(-1)">返回</a></div>
        <table width="591" height="433" border="0" align="center">
          <tr>
            <td colspan="2"><div align="center"><span class="STYLE2">订单详细信息</span></div></td>
            </tr>
          <tr>
            <td width="101" align="right">订单编号:</td> 
     	 	<td width="120">${orders.number }</td> 
          </tr>
          <tr>
		  <td align="right">付款方式:</td> 
     	 <td>${orders.payMode }</td> 
		</tr>
          <tr>
         <td align="right">付款状态:</td> 
	     <td>${orders.payStatus }</td> 
          </tr>
          <tr>
         <td align="right">订单日期:</td> 
	     <td>${orders.orderDate }</td> 
          </tr>
          <tr>
         	<td align="right">总计:</td> 
	     	<td>${orders.totalPrice }</td> 
          </tr>
          <tr>
            <td align="right">收货人:</td> 
    	    <td>${orders.consignee }</td> 
          </tr>
          <tr>
            <td align="right">联系电话:</td>
            <td>${orders.phone }</td> 
          </tr>
          <tr>
            <td align="right">邮编:</td>
            <td>${orders.zip }</td> 
          </tr>
          <tr>
            <td align="right">收货地址:</td>
            <td>${orders.shippingaddr }</td> 
          </tr>
          <tr>
            <td align="right">配送方式:</td>
            <td>${orders.deliverymode }</td> 
          </tr>
          
          <tr>
          	<td colspan="2">商品信息:</td>
          </tr>
          <tr>
            <td colspan="2">
				<table >
				<th>商品名称</th>
				<th>图片</th>
				<th>数量</th>
				<th>价格</th>
				<th>小计</th>
				<c:forEach items="${orders.orderRowSet }" var="orderRow"  varStatus="s">
				<tr>
					<td>${orderRow.product.name }</td>
					<td><img src="${orderRow.product.bigImg }" width="100px" height="100px"/></td>
					<td>${orderRow.productNum }</td>
					<td>${orderRow.vipPrice }</td>
					<td>${orderRow.subPrice }</td>
				</tr>
				</c:forEach>
				</table>
			</td>
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
