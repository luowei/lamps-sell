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
					"<form action='${ctx }/head/message_add.php' method='post'>"+
					"<div style='float:left'>留言评价(<b style='color:#FC978F'>"+name+"</b>)：</div><br/>"+
					"<input type='hidden' name='productId' value='"+id+"'/>"+
					"<input type='hidden' name='userId' value='${sessionScope.user.id }'/>"+
					"<textarea name='detail' rows='10' cols='60'></textarea><br/>"+
					"<input type='submit' value='确定'/>"+
					"</form></div>"
					alertWin("添加留言评价", msg, 500, 280);
                }
            	function alterMsg(id,productId,name,detail){
					var msg="<div  style='text-align: center;padding-top: 20px;'>"+
					"<form action='${ctx }/head/message_alter.php' method='post'>"+
					"<div style='float:left'>修改留言评价(<b style='color:#FC978F'>"+name+"</b>)：</div><br/>"+
					"<input type='hidden' name='id' value='"+ id +"'/>"+
					"<input type='hidden' name='productId' value='"+ productId +"'/>"+
					"<input type='hidden' name='userId' value='${sessionScope.user.id }'/>"+
					"<textarea name='detail' rows='10' cols='60'>"+detail+"</textarea><br/>"+
					"<input type='submit' value='确定'/>"+
					"</form></div>"
					alertWin("修改留言评价", msg, 500, 280);
                }
            </script>
            <c:if test="${param.addMsg_flag eq 'success' }">
				<script type="text/javascript">
					var msg="<div style='text-align: center;padding-top: 10px;'>"+
						"<span style='font-weight:bolder;font-color:green'>添加留言成功！</span><br/></div>";
					alertWin("成功", msg, 200, 60);
				</script>
			</c:if>
			<c:if test="${param.alterMsg_flag eq 'success' }">
				<script type="text/javascript">
					var msg="<div style='text-align: center;padding-top: 10px;'>"+
						"<span style='font-weight:bolder;font-color:green'>修改留言成功！</span><br/></div>";
					alertWin("成功", msg, 200, 60);
				</script>
			</c:if>
			<c:if test="${param.delMsg_flag eq 'success' }">
				<script type="text/javascript">
					var msg="<div style='text-align: center;padding-top: 10px;'>"+
						"<span style='font-weight:bolder;font-color:green'>删除留言成功！</span><br/></div>";
					alertWin("成功", msg, 200, 60);
				</script>
			</c:if>
			<script type="text/javascript" language="javascript" src="${ctx }/js/window.js"></script>
			
            
            
           <div class="rows box01-wrap">
        	<div class="box-blue listbox">
            	<div class="title01">
                	<center><h1>${product.name }</h1></center>
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
                	
                    	当前位置:<a href="${ctx }/head/index.php" target=_blank>首页</a>&nbsp;>>&nbsp;
						<a href="${ctx }/head/product.php" target=_blank>产品库</a>&nbsp;>>&nbsp;
						<a href="${ctx }/head/product.php?categoryId=${pCategory.id}" target=_blank>${pCategory.name }</a>&nbsp;>>&nbsp;
						<a href="${ctx }/head/product.php?categoryId=${cCategory.id}" target=_blank>${cCategory.name }</a>&nbsp;>>&nbsp;${product.name }
                </div>
                <div class="productbox s_clear">
                	<c:if test="${complany ne null }">
                	<div class="linkman box02-wrap">
                    	<div class="box-ashen">
                        	<div class="title01 title01-ashen"><center><h1>${complany.name }</h1></center></div>
                            <div class="content">
                            <c:if test="${complany.contact ne null }">
                            	<p>联系人：${complany.contact.name }</p>
                                <p>电话：${complany.contact.tel }</p>
                                <p>手机：${complany.contact.phone }</p>
                                <p>传真：${complany.contact.fax }</p>
                                <p>邮编：${complany.contact.zip }</p>
                                <p>地址：${complany.contact.addr }</p>
                                </c:if>
                                <c:if test="${complany.contact eq null }">
                                	<p><b style="color:#E15F00;font-size: 16px;">此企业没有添加联系人信息</b></p>
                                </c:if>
                                <div class="btn">
                                    <table class="center"><tr><td>
                                    	<a href="${ctx }/head/product.php?complanyId=${complany.id }" target="_blank" class="icon-btn icon-com-btn"></a>
                                    </td></tr></table>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:if>
                    <div class="product">
                    	<div class="img">
                        	<a class="pd" href="${product.bigImg }" title="${product.name }" target="_blank">
                        		<img src="${product.bigImg }" alt="${product.name }" style="height: 180px;width: 180px"/></a>
                            <div><table class="center"><tr><td>
                            	<a class="view" href="${product.bigImg }" target="_blank">放大图片</a>
                            </td></tr></table></div>
                        </div>
                        <p>价格： ${product.price }￥&nbsp;&nbsp;
							<c:if test='${sessionScope.user ne null && sessionScope.user.isBetter ne 0 }'>
                        	<span class="numbtn numbtn-01" href="${ctx }/head/vip.php" target="_blank" 
                        		style="color:red;font-weight: bolder;
                        		background:url(../images/skin/numbtns.gif) no-repeat -2px ${(sessionScope.user.isBetter-1)*-24 }px;">
                        	<b style="color:#E15F00;padding-left: 90px;">${product.price*(100-sessionScope.user.isBetter)/100 }￥</b></span>
                        	</c:if>
						</p>
						<form id='pForm' name='pForm' action='' method='get'  style='displany:none'>
						<p>数量：<input id="num" name="num" value="1" style="width: 30px;height: 20px;"/>台</p>
                        <p>库存/已售:(${product.num }/${product.salesValume })
                            <span class="gray"><a href="${ctx }/head/collect.php?productId=${product.id }" target="_blank" style="color:#E15F00;font-weight: bolder">
                            [收藏]</a></span></p>
<%--                        <p>付款方式：--%>
<%--                        	<select name="payMode">--%>
<%--								  <option value="银行支付">银行支付</option>--%>
<%--								  <option value="第三方支付">第三方支付</option> --%>
<%--								  <option value="货到付款">货到付款</option>          	--%>
<%--                        	</select>--%>
<%--                        </p>--%>
                        <p>产地： ${product.produceArea }</p>
                        <input type='hidden' id="productId" name='productId' value='${product.id }'/>
						<input type='hidden' name='userId' value='${sessionScope.user.id }'/>
<%--						<input id='buySubmit' type='submit' value='' style='displany:none'/>--%>
						</form>
                        <p>生产日期： ${product.createtime }</p>
                        <div class="prompt box-b-orange"><span>想订购<b style="color: #E15F00;">${product.name }</b>，请点此
                        	 <a href="javascript:" target="_blank">填写订购信息</a></span></div>
                        <div class="btns">
                        	<a class="icon-btn" href="javascript:" onclick="buy()" ></a>
                            <a class="icon-btn icon-intro-btn" href="javascript:" onclick="addCart()" ></a>
                            <a class="book-btn" target="blank" href='javascript:' onclick="addMsg(${product.id},'${product.name }')"></a>
                            <a class="book-btn2" target="blank" href='${ctx }/cart/list.php'></a>
                        </div>
                        <script type="text/javascript">
                        function addCart(){
                        	var user="${sessionScope.user}";
                        	if(user==""){
                        		var msg="<div style='text-align: center;padding-top: 10px;'>"+
                    			"<span style='font-weight:bolder;font-color:green'>请先登录！</span><br/></div>";
                    			alertWin("您还未登录", msg, 200, 60);
                    			return;
                            }
                            var url="${ctx }/cart/add.php";
                            //document.pForm.action=url;
                            //document.getElementById("pForm").setAttribute("action",url);
                            document.pForm.attributes["action"].value=url
                            document.getElementById("pForm").submit();
                        }
                        function buy(){
                        	var user="${sessionScope.user}";
                        	if(user==""){
                        		var msg="<div style='text-align: center;padding-top: 10px;'>"+
                    			"<span style='font-weight:bolder;font-color:green'>请先登录！</span><br/></div>";
                    			alertWin("您还未登录", msg, 200, 60);
                    			return;
                            }
                        	var url="${ctx }/cart/buy.php";
                        	//document.pForm.action=url;
                        	//document.getElementById("pForm").setAttribute("action",url);
                        	document.pForm.attributes["action"].value=url
                            document.getElementById("pForm").submit();
                        }
                        </script>
                    </div>
                </div>
            </div>
        </div>
        <div class="rows box01-wrap">
        	<div class="box-blue databox">
                <div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>详细信息</a></li></ul>
                    </div>
                </div>
                 <div class="content">
					${product.detail }
                </div>

            </div>
        </div>
        <div class="rows box01-wrap">
        	<div class="box-blue list-table"">
        		<div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>留言评价</a></li></ul>
                    </div>
                </div>
                <table class="table" cellpadding="0">
                	<tr >
                    	<th class="show">详细信息</th>
                        <th class="price">用户名</th>
                        <th class="index">留言日期</th>
                    </tr>
                <c:set var="i" value="1" />
                <c:forEach items="${messageList}" var="message" varStatus="s">
                    <tr  <c:if test="${i%2 eq 0 }"> class="bluebg"</c:if> >
                    	<td class="show" style="width: 300px;">
                        	<div id="bj_cp">
                        		${message.detail }
                            </div>
                        </td>
                        <td class="price"><b style="color:green;">${message.user.userName }</b>
                        	<c:if test='${sessionScope.user.id eq message.user.id }'>
                        	<a class="numbtn numbtn-01" style="display: initial;"
                        		 href="javascript:" onclick="alterMsg(${message.id},${product.id },'${product.name }','${message.detail }')">
                        		<b style="color:#E15F00;padding-left: 5px;">修改</b></a>
                        	<a class="numbtn numbtn-01" style="display: initial;" 
                        	 href="javascript:if(confirm('您确定要删除这条留言吗？'))location.href='${ctx }/head/message_del.php?id=${message.id}';">
                        		<b style="color:#E15F00;padding-left: 5px;">删除</b></a>
                        	</c:if>
                        </td>
                        <td class="index">${message.createtime }</td>
                    </tr>
                    <c:set var="i" value="${i+1}" />
                 </c:forEach>
                </table>
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

