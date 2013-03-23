<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>购物车-灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
		<div class="rows box01-wrap">
        	<div class="box-blue list-table"">
        		<div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>购物车信息</a></li></ul>
                    </div>
                </div>
                <table class="table" cellpadding="0">
                	<tr >
                    	<th class="show">商品名</th>
                    	<th class="index">数量</th>
                        <th class="price">价格</th>
                        <th class="index">优惠价</th>
                        <th class="index">小计</th>
                        <th class="index">操作</th>
                    </tr>
                <c:set var="i" value="1" />
<%--                <c:set var="totalPrice" value="0" />--%>
                <c:forEach items="${cartRowList}" var="cartRowValue" varStatus="s">
                    <tr  <c:if test="${i%2 eq 0 }"> class="bluebg"</c:if> >
                    <form id='pForm${i }' name='pForm' action='' method='get'  style='displany:none'>
                    	<td class="show" style="width: 200px;">${cartRowValue.product.name }</td>
                    	<td class="show" align="right" style="width: 80px;">
                    		<input name="num" style="width:40px" value="${cartRowValue.num }"/>台
                    	</td>
                    	<td class="show" align="right" style="width: 80px;">${cartRowValue.product.price }￥</td>
<%--                    	<c:set var="vipPrice" value="${cartRowValue.product.price *((100-sessionScope.user.isBetter)/100) }"/>--%>
                    	<td class="show" align="right" style="width: 80px;">${cartRowValue.vipPrice }￥</td>
<%--                    	<c:set var="subPrice" value="${cartRowValue.num * vipPrice}"/>--%>
                        <td class="price"  align="right"><b style="color:green;">${cartRowValue.subPrice }￥</b></td>
                        <td>
                        	<a href="javascript:" onclick="alterCart(${i })"><b style="color:#E15F00;padding-left: 5px;">修改</b></a>
                        	<a  href="javascript:if(confirm('您确定要删除吗？'))location.href='${ctx }/cart/del.php?productId=${cartRowValue.product.id}&userId=${sessionScope.user.id }';">
                        		<b style="color:#E15F00;padding-left: 5px;">删除</b></a>
                        </td>
                    <input type='hidden' id="productId" name='productId' value='${cartRowValue.product.id }'/>
					<input type='hidden' name='userId' value='${sessionScope.user.id }'/>
                    </form>
                    </tr>
                    
<%--                    <c:set var="totalPrice" value="${totalPrice + cartRowValue.subPrice }" />--%>
                    <c:set var="i" value="${i+1}" />
                 </c:forEach>
                
                 <script type="text/javascript">
                        function alterCart(i){
                        	var user="${sessionScope.user}";
                        	if(user==""){
                        		var msg="<div style='text-align: center;padding-top: 10px;'>"+
                    			"<span style='font-weight:bolder;font-color:green'>请先登录！</span><br/></div>";
                    			alertWin("您还未登录", msg, 200, 60);
                    			return;
                            }
                            var url="${ctx }/cart/alter.php";
                            var pForm=document.getElementById("pForm"+i);
                        	pForm.setAttribute("action",url);
                        	document.getElementById("pForm"+i).submit();
                        }
                </script>
                </table>
                <div style="float:right;">
                	总计：<b style="color: #E15F00">${cart.totalPrice }</b>￥
                </div>
<%--                <div class="pages">--%>
<%--					<table class="center"><tr><td>--%>
<%--					${frontTag }--%>
<%--		            </td></tr></table>--%>
<%--                </div>--%>
			<div align="center">
				<input type="button" value="继续购买" onclick="location.href='${ctx}/head/product.php'"/>
				<input type="button" value="生成订单" onclick="location.href='${ctx}/orders/preview.php'"/>
			</div>
            </div>
        </div>
	</div>
	
<jsp:include page="foot.jsp"></jsp:include></body>
</html>