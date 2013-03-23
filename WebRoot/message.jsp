<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>留言评价-灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
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
                        		 href="javascript:" onclick="alterMsg(${message.id},'${message.detail }')">
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
                 <script type="text/javascript">
                 function alterMsg(id,detail){
 					var msg="<div  style='text-align: center;padding-top: 20px;'>"+
 					"<form action='${ctx }/head/message_alter.php' method='post'>"+
 					"<div style='float:left'>修改留言评价：</div><br/>"+
 					"<input type='hidden' name='id' value='"+ id +"'/>"+
 					"<input type='hidden' name='userId' value='${sessionScope.user.id }'/>"+
 					"<textarea name='detail' rows='10' cols='60'>"+detail+"</textarea><br/>"+
 					"<input type='submit' value='确定'/>"+
 					"</form></div>"
 					alertWin("修改留言评价", msg, 500, 280);
                 }
                 </script>
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