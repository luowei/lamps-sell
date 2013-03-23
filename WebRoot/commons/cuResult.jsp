<%@ page contentType="text/html; charset=UTF-8" %>

<%
  String path = request.getContextPath();
  String info = (String)request.getAttribute("info");
  String redirect = (String)request.getAttribute("redirect");
%>


<html>
<head>
<title><%=info%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
<!--
<%
  if (info!=null){
    if (!info.equals("")){ %>
      alert("<%=info%>");
  <%  }
  }
%>
<% if(redirect != null &&  !("").equals(redirect)){ %>

	window.location.href="<%=path%>/<%=redirect%>";
<%}%>
//-->
</script>

</head>
<body>
<noscript>
<iframe src="*.htm"></iframe>
</noscript>

</body>
</html>
