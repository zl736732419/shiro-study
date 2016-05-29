<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- css link-->

<!-- css link end -->


<!--js script -->

<!--js script end-->
<title></title>
</head>
<body>
	<h1 style="color:green">登录成功</h1>
	
	<p>欢迎 <span>${subject.principal}!</span><a href="${pageContext.request.contextPath }/logout">退出</a></p>

</body>
</html>