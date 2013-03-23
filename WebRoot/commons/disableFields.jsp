<%
request.setAttribute("id", request.getParameter("id"));
%>
<c:if test="${not empty id}">
<script type="text/javascript" src="${ctx}/scripts/disableField.js"></script>
<script>
	<c:forEach var="field" items="${disabledField}">
	disableField('${field}');
	</c:forEach>
</script>
</c:if>