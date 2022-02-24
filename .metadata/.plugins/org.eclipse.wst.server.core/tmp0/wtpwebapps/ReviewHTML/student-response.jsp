<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	The student is confirmed:
	<%-- <%=
		request.getParameter("firstName")
	%>
	<%=
		request.getParameter("lastName")
	%> --%>
	${ param.firstName } <br />
	 ${ param.lastName } <br />
	  ${ param.country } <br />
	
	<%
		String[] langs = request.getParameterValues("favoriteLang");
		for (int i = 0; i < langs.length; ++i){
			if (langs[i] != null){
				out.println("<b>" + langs[i] + "</b>");
			}
		}
		
	%>
	
</body>
</html>