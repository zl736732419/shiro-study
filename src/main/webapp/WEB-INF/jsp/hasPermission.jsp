<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- css link-->

<!-- css link end -->


<!--js script -->

<!--js script end-->
<title></title>
</head>
<body>
	<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
<title></title>
</head>
<body>
${subject.principal}拥有权限。
</body>
</html>
</body>
</html>