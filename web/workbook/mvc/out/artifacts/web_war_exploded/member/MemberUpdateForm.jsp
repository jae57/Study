<%--
  Created by IntelliJ IDEA.
  User: ggaps
  Date: 2019-01-14
  Time: 오후 4:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>회원정보</title>
</head>
<body>
<h1>회원정보</h1>
<form action='update.do' method='post'>
    번호: <input type='text' name='no' value='${member.no}' readonly><br>
    이름: <input type='text' name='name' value='${requestScope.member.name}'><br>
    이메일: <input type='text' name='email' value='${member.email}'><br>
    가입일: ${member.createdDate}<br>
    <input type='submit' value='저장'>
    <input type='button' value='삭제' onclick='location.href="delete.do?no=${member.no}"'>
    <input type='button' value='취소' onclick='location.href="list.do"'>
</form>
</body>
</html>
