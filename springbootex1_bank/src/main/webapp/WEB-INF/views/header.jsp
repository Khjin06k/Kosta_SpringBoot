<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kosta.bank.dto.Member" %>
<%
	Member sessionMember=(Member)session.getAttribute("member");
	Member requestMember = (Member) request.getAttribute("member");
	System.out.println("Session Member: " + sessionMember);
	System.out.println("Request Member: " + requestMember);
%>

<!DOCTYPE html>
<style>
* {margin:0 auto;}
a {text-decoration:none;}
.outerDiv {
	width:100%;
	height:100px;
	background-color:white;
}
</style>
<div class="outerDiv" style="border-bottom: 5px solid cadetblue">
	<i><h1 style="text-align:center; color: black; margin-top: 10px">kosta bank</h1></i><br>
	<div class ="innerDiv">
	<div style="float:left; margin-left:10px;">
		<a href="makeaccount" style="color: black">계좌개설</a>&nbsp;&nbsp;
		<a href="deposit" style="color: black">입금</a>&nbsp;&nbsp;
		<a href="withdraw" style="color: black">출금</a>&nbsp;&nbsp;
		<a href="accountinfo" style="color: black">계좌조회</a>&nbsp;&nbsp;
		<a href="allaccountinfo" style="color: black">전체계좌조회</a>&nbsp;&nbsp;
	</div>
	<div style="float:right; margin-right:10px;">
		<c:choose>
			<c:when test="${member eq Enpty}">
				<a href="login" style="color: black">로그인</a>&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				${member.getName()}님 환영합니다.&nbsp;&nbsp;<a href="logout">로그아웃</a>&nbsp;&nbsp;
			</c:otherwise>
		</c:choose>
		<a href="join" style="color: black">회원가입</a>&nbsp;&nbsp;
	</div>
	</div>	
</div>