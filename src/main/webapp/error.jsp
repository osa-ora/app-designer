<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="favicon.ico"/>
        <title>Cloud Native Application Builder - Error Page</title>
</head>
<body>
	<br>&nbsp;You have encountered unexpected error, reason:
        <% if(session.getAttribute("ERROR_MSG")!=null) { %>            
        <br><br>&nbsp;&nbsp;Error Message: <font color="red"><%=session.getAttribute("ERROR_MSG") %></font>
        <%  session.removeAttribute("ERROR_MSG");
           } %>
        <br><br>
        &nbsp;Check with the system administrator or contact our support team.
	<br><br>      
    <%@include file="footer.jsp" %>
</body>
</html>