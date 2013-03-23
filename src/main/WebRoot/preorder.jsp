<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>预览订单-灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
	
        <div class="rows box01-wrap">
        	<div class="box-blue listbox">
            	<div class="title01">
<%--                	<center><h1>${cartRowList[0].product.name }</h1></center>--%>
					<center><h1>预览订单</h1></center>
                </div>
                <div class="path">
                	<c:if test="${sessionScope.user eq null || sessionScope.user.isBetter eq 0}">
                		<div class="right">
							<span class="member1" style="width: 90px;"><a href="${ctx }/head/vip.php" target="_blank"><b>加入本站vip</b></a>
						</div>
                	</c:if>
                	<c:if test="${sessionScope.user ne null && sessionScope.user.isBetter ne 0 }">
		            	<div class="right">
							<span class="member1"><a href="${ctx }/head/vip_detail.php?id=${sessionScope.user.id }" target="_blank"><b>${sessionScope.user.userName }</b></a>
							</span> 积分<b>${sessionScope.user.score }</b>
						</div>
		            </c:if>
                	
                </div>
                <div class="productbox s_clear">
                    <div class="product">
	                	<div class="tab01">
	                    	<ul><li class="hover">收货信息(填写)</li></ul>
	                    </div>
                    <form name="ordersForm" action="${ctx }/orders/add.php" method="post">
                    	<table>
                    	<tr>
                    		<td>收货人：&nbsp;&nbsp;<input name="consignee" value="${sessionScope.user.userName }"/></td>
                    	</tr>
                    	<tr>
                    		<td>联系电话：<input name="contact" value=""/></td>
                    	</tr>
                    	<tr>
                    		<td>邮编：&nbsp;&nbsp;&nbsp;&nbsp;<input name="zip" /></td>
                    	</tr>
                    	<tr>
                    		<td >收货地址：<input name="shippingaddr" value="" style="width:500px"/> </td>
                    	</tr>
                    	<tr>
							<td>
								配送方式：<select name="deliverymode">
		                        	<option value="圆通快递">圆通快递</option>
		                        	<option value="申通快递">申通快递</option>
		                        	<option value="韵达快递">韵达快递</option>
		                        	<option value="中通快递">中通快递</option>
		                        	<option value="EMS">EMS</option>
		                        </select>
							</td>
						</tr>
						<tr>
							<td>
								付款方式：<select name="payMode">
								  <option value="银行支付">银行支付</option>
								  <option value="第三方支付">第三方支付</option> 
								  <option value="货到付款">货到付款</option>          	
			                    </select>
							</td>
							<td>&nbsp;</td>
                    	</tr>
                    	</table>
                    	<br/>
                        <div class="rows box01-wrap">
				        	<div class="box-blue list-table"">
				        		<div class="title01">
				                	<div class="tab01">
				                    	<ul><li class="hover"><a>商品信息</a></li></ul>
				                    </div>
				                </div>
				                <table class="table" cellpadding="0">
				                	<tr >
				                    	<th class="show">商品名称</th>
				                    	<th class="index">图片</th>
				                        <th class="index">购买数量</th>
				                        <th class="index">单价</th>
				                         <th class="index">金额小计</th>
				                    </tr>
				                <c:set var="i" value="1" />
<%--				                <c:set var="totalPrice" value="0" />--%>
				                <c:forEach items="${cartRowList }" var="cartRow" varStatus="s">
				                    <tr  <c:if test="${i%2 eq 0 }"> class="bluebg"</c:if> >
				                    	<td class="show" style="width: 300px;">${cartRow.product.name }</td>
				                    	<td class="index" ><img src="${cartRow.product.bigImg }" width="80px" height="80px"/></td>
				                        <td class="index" >${cartRow.num }</td>
<%--				                        <c:set var="vipPrice" value="${cartRow.product.price *((100-sessionScope.user.isBetter)/100) }"/>--%>
				                        <td class="index">${cartRow.vipPrice }</td>
<%--				                        <c:set var="subPrice" value="${cartRow.num * vipPrice}"/>--%>
				                        <td class="index">${cartRow.subPrice }</td>
				                    </tr>
<%--				                    <c:set var="totalPrice" value="${totalPrice + cartRow.subPrice }" />--%>
				                    <c:set var="i" value="${i+1}" />
				                 </c:forEach>
				                </table>
				                <div style="float:right;">
				                	总计：<b style="color: #E15F00">${cart.totalPrice }</b>￥
				                </div>
<%--				                <div class="pages">--%>
<%--									<table class="center"><tr><td>--%>
<%--									${frontTag }--%>
<%--						            </td></tr></table>--%>
<%--				                </div>--%>
				            </div>
				        </div>
                        
                        <div class="btns" align="center">
                        	<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onclick="history.go(-1)"/>
                        	<input type="submit" value="提交订单" />
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
      </div>
<jsp:include page="foot.jsp"></jsp:include></body>
</html>

