<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Error Page</title>
	<script src="<c:url value="/scripts/prototype.js"/>" type="text/javascript"></script>
	<script language="javascript">
		function showDetail()
		{
			$('detail_error_msg').toggle();
		}
	</script>
</head>

<body>

<div id="content">
	<%
		//Exception from JSP didn't log yet ,should log it here.
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		LogFactory.getLog(requestUri).error(exception.getMessage(), exception);
	%>

	<h3>System Runtime Error: <br><%=exception.getMessage()%>
	</h3>
	<br>

	<button onclick="history.back();">Back</button>
	<br>

	<p><a href="#" onclick="showDetail();">Administrator click here to get the detail.</a></p>

	<div id="detail_error_msg" style="display:none">
		<pre><%exception.printStackTrace(new java.io.PrintWriter(out));%></pre>
	</div>
</div>
</body>
</html>