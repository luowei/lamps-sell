<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>产品库-灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
   		<div class="cols-left04">
        	<div class="rows box01-wrap"><div class="box-blue listbox">
                <div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>选择大类查询</a></li></ul>
                    </div>
                </div>
                <div class="hor-list01 s_clear">
                	<ul><c:forEach items="${pCategoryList}" var="pCategory" varStatus="s">
                    	<li><div><A  href="${ctx }/head/product.php?categoryId=${pCategory.id }">${pCategory.name }</A> <span class="orange">(${pCategory.productCount })</span></div></li>
                    	</c:forEach>
                    </ul>
                </div>
            </div></div>
            <script type="text/javascript">
            	function addMsg(id,name){
                	var user="${sessionScope.user}";
                	if(user==""){
                		var msg="<div style='text-align: center;padding-top: 10px;'>"+
            			"<span style='font-weight:bolder;font-color:green'>请先登录！</span><br/></div>";
            			alertWin("您还未登录", msg, 200, 60);
            			return;
                    }
					var msg="<div  style='text-align: center;padding-top: 20px;'>"+
					"<form action='${ctx }/head/message_add.php?productId="+id+"' method='post'>"+
					"<div style='float:left'>评价留言(<b style='color:#FC978F'>"+name+"</b>)：</div><br/>"+
					"<input type='hidden' name='userId' value='${sessionScope.user.id }'/>"+
					"<textarea name='detail' rows='10' cols='60'></textarea><br/>"+
					"<input type='submit' value='确定'/>"+
					"</form></div>"
					alertWin("添加留言评价", msg, 500, 280);
                }
            </script>
            
<%--            <div><form action='${ctx }/head/message/add.php?productId=' method='post'>--%>
<%--            	评价：<br/>--%>
<%--            	<input type='hidden' name='userId' value='${sessionScope.user.id }'/>--%>
<%--            	<textarea name='detail' rows='30' cols='100'></textarea><br/>--%>
<%--            	<input type='submit' value='确定'/>--%>
<%--            </form></div>--%>
			<script type="text/javascript" language="javascript" src="${ctx }/js/window.js"></script>
            
            
            <div class="rows box01-wrap"><div class="box-blue list-table">
            	<div class="title01 title01-sapphire">
                	<div class="right"><span class="member"><a href="#" target="_blank">vip会员</a></span></div>
                </div>
                <table class="table" cellpadding="0">
                	<tr >
                    	<th class="show">产品展示</th>
                        <th>产品名称/介绍</th>
                        <th class="price">价格</th>
                        <th class="index">产生地址</th>
                    </tr>
                <c:set var="i" value="1" />
                <c:forEach items="${productList}" var="product" varStatus="s">
                    <tr  <c:if test="${i%2 eq 0 }"> class="bluebg"</c:if> >
                    	<td class="show">
                        	<div id="bj_cp">
                        	<DIV class=photo80_80><a href="${ctx }/head/product_detail.php?id=${product.id }" target="_blank">
                        		<span><img src="${product.bigImg }" /></span>
                        	</a></DIV>
                            </div>
                        </td>
                        <form id='pForm${i }' name='pForm' action='' method='post'  style='displany:none'>
                        <td>
                        	<h1><a href="${ctx }/head/product_detail.php?id=${product.id }" target=_blank>${product.name }</a></h1>
                            <p class="header">
                            <div style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; width: 100px">
                            ${product.detail }</div>
                            </p>
                            <p>库存/已售:(${product.num }/${product.salesValume })
                            <span class="gray"><a href="javascript:" target="_blank" style="color:#E15F00;font-weight: bolder">
                            [收藏]</a></span></p>
                            <div class="btns">
                            <span style="float:left">数量：<input id="num" name="num" value="1" style="width: 30px;height: 20px;"/>台</span>
                            <span style="float:left">&nbsp;&nbsp;</span>
                            <a class="button01 button01-blue" href="javascript:" onclick="buy(${i })" target="_blank">购买</a>
                            <a href="javascript:" target="_blank" class="button01 button01-blue" onclick="addMsg(${product.id },'${product.name }')">发送留言</a></div>
                        </td>
                        <td class="price"><b style="color:green;">${product.price }￥</b><br />
                        	<c:if test='${sessionScope.user ne null && c ne 0 }'>
                        	<a class="numbtn numbtn-01" href="${ctx }/head/vip.php" target="_blank" 
                        		style="color:red;font-weight: bolder;
                        		background:url(../images/skin/numbtns.gif) no-repeat -2px ${(sessionScope.user.isBetter-1)*-24 }px;">
<%--                        	vip<span style="color:#E15F00;font-weight: bold;">价格：</span>--%>
                        	<b style="color:#E15F00;padding-left: 80px;">${product.price*(100-sessionScope.user.isBetter)/100 }￥</b></a>
                        	</c:if>
                        </td>
                        <td class="index">${product.produceArea }</td>
                        <input type='hidden' name='productId' value='${product.id }'/>
						<input type='hidden' name='userId' value='${sessionScope.user.id }'/>
<%--						<input id='buySubmit' type='submit' value='' style='displany:none'/>--%>
						</form>
                    </tr>
                    <c:set var="i" value="${i+1}" />
                    </c:forEach>
                    <script type="text/javascript">
                    	function buy(i){
                    		var user="${sessionScope.user}";
                        	if(user==""){
                        		var msg="<div style='text-align: center;padding-top: 10px;'>"+
                    			"<span style='font-weight:bolder;font-color:green'>请先登录！</span><br/></div>";
                    			alertWin("您还未登录", msg, 200, 60);
                    			return;
                            }
                        	var url="${ctx }/cart/buy.php";
                        	//document.getElementById("pForm").setAttribute("action",url);;
                        	var pForm=document.getElementById("pForm"+i);
                        	pForm.setAttribute("action",url);
                        	document.getElementById("pForm"+i).submit();
                        }
                    </script>
                    
                    
                </table>
                <div class="pages">
					<table class="center"><tr><td>
					${frontTag }
		            </td></tr></table>
                </div>
            </div></div>
        </div>
        <div class="cols-right04">
        	<div class="rows box02-wrap"><div class="box-ashen lists">
            	<div class="title01 title01-ruby title">
                	<h1>最热产品</h1>
                </div>
                <div class="list">
                    <ul><c:forEach items="${hotProductList}" var="product" varStatus="s">
                    	<li><A href="${ctx }/head/product_detail.php?id=${product.id }" target="_blank">${product.name }<span>(${product.num })</span></a></li>
                    </c:forEach></ul>
                </div>
            </div></div>
    	</div>
<jsp:include page="foot.jsp"></jsp:include></body>
</html>

