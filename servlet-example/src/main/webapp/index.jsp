<%
    // get data from request:
    String message = (String) request.getAttribute("message");
%>

<html>
<body>
<h2>Hello World!</h2>
<!-- Use the <% %> syntax to display the message from our request on the page: -->
<h3 >This is your message:  <%=message%></h3>
</body>
</html>
