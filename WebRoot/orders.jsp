<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单-灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
           
        <div class="rows box01-wrap">
        	<div class="box-blue listbox">
            	<div class="title01">
                	<center><h1>订单信息</h1></center>
                </div>
                <div class="path">
                	<c:if test="${sessionScope.user eq null ||sessionScope.user.isBetter eq 0}">
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
                    	<table>
<%--                    	<c:set var="orderListIndex" value="${orderListIndex }"/>--%>
<%--                    	<c:set var="order1" value="${ordersList[orderListIndex] }"/>--%>
                    	<tr>
                    		<td>订单编号：${orders.number } </td>
                    	</tr>
                    	<tr>
                    		<td>订购时间：${orders.orderDate }</td>
                    	</tr>
                    	<tr>
                    		<td>订单总额：${orders.totalPrice }￥</td>
                    	</tr>
                    	<tr>
                    		<td>收货人：&nbsp;&nbsp;${orders.consignee }</td>
                    	</tr>
                    	<tr>
                    		<td>联系电话：${orders.phone }</td>
                    	</tr>
                    	<tr>
                    		<td>邮编：&nbsp;&nbsp;&nbsp;&nbsp;${orders.zip }</td>
                    	</tr>
                    	<tr>
                    		<td colspan="3">收货地址：${orders.shippingaddr } </td>
                    	</tr>
                    	<tr>
							<td>
								配送方式：${orders.deliverymode }
							</td>
						</tr>
						<tr>
							<td>
								付款方式：${orders.payMode }
							</td>
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
				                <c:forEach items="${orderRowSet}" var="orderRow" varStatus="s">
				                    <tr  <c:if test="${i%2 eq 0 }"> class="bluebg"</c:if> >
				                    	<td class="show" style="width: 300px;">${orderRow.product.name }</td>
				                    	<td class="index" ><img src="${orderRow.product.bigImg }" width="80px" height="80px"/></td>
				                        <td class="index" >${orderRow.productNum }</td>
				                        <td class="index">${orderRow.product.price }</td>
				                        <td class="index">${orderRow.subPrice }</td>
				                    </tr>
				                    <c:set var="i" value="${i+1}" />
				                 </c:forEach>
				                </table>
<%--				                <div class="pages">--%>
<%--									<table class="center"><tr><td>--%>
<%--									${frontTag }--%>
<%--						            </td></tr></table>--%>
<%--				                </div>--%>
				            </div>
				        </div>
                        
                    </div>
                </div>
            </div>
        </div>
        
        
        <div class="rows box01-wrap">
        	<div class="box-blue list-table"">
        		<div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>订单列表</a></li></ul>
                    </div>
                </div>
                <table class="table" cellpadding="0">
                	<tr >
                    	<th class="show">订单编号</th>
                        <th class="index">订购时间</th>
                        <th class="index">订单总额</th>
                        <th class="index">交易状态</th>
                        <th class="index">删除</th>
                    </tr>
                <c:set var="i" value="0" />
                <c:forEach items="${ordersList}" var="orders" varStatus="s">
                    <tr  <c:if test="${i%2 eq 0 }"> class="bluebg"</c:if> >
                    	<td class="show" style="width: 300px;" onclick="location.href='${ctx }/orders/list.php?orderListIndex=${i }'">
                    	<b>${orders.number }</b></td>
                        <td class="index">${orders.orderDate }</td>
                        <td class="index">${orders.totalPrice }￥</td>
                        <td class="index">${orders.payStatus }</td>
                        <td class="index"><input type="button" name="delOrders" value="删除" 
                        	 onclick="if(confirm( '您确定要删除吗？ '))location.href='${ctx}/orders/del.php?ordersId=${orders.id }'"/></td>
                    </tr>
                    <c:set var="i" value="${i+1}" />
                 </c:forEach>
                </table>
                <div class="btns" align="center">
	               	<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onclick="history.go(-1)"/>
	               	<input type="button" value="结算" onclick="" />
	            </div>
                <div class="pages">
					<table class="center"><tr><td>
					${frontTag }
		            </td></tr></table>
                </div>
            </div>
        </div> 
        
        
       </div>
<jsp:include page="foot.jsp"></jsp:include></body>
</html>

