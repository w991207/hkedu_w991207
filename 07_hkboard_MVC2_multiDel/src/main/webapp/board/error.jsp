<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; cahrset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	String msg=request.getParameter("msg");
%>
<body>
<h1>시스템오류입니다.</h1>
<h2>오류내용:<%=msg %></h2>
<h3><a href="index.jsp">메인으로</a></h3>
</body>
</html>