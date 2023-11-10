<%@page import="com.hk.board.dtos.HkDto"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.board.daos.HkDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; cahrset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>글목록조회</title>
<script type="text/javascript">
	function insertBoardForm(){
		location.href="insertBoardForm.board";
	}
	
	function allSel(bool){
		var chks=document.getElementsByName("chk");
		for(var i=0; i<chks.length; i++){
			chks[i].checked=bool;//true면 체크, false 체크해제
		}
	}
	//체크박스의 체크여부 확인하고 submit실행하기
	function isAllCheck(){
		if(confirm("정말 삭제할꺼야?")){
			var chks=document.getElementhByName("chk");
			for (var i=0; i<chks.length; i++){
				if(chks[i].checked){
					count++
				}
			}
			if(count==0){
				alert("최소 하나이상 체크하세요");
			}
			return count>0?true:false;
		}
		return false;
	}
</script>
</head>
<%//실행부 : java코드 실행
	List<HkDto> list = (List<HkDto>)request.getAttribute("list");
%>
<body>
<h1>게시판</h1>
<h2>글목록</h2>
<form action="muldel.board" method="post" onsubmit="return isAllCheck()">
<table border="1">
	<col width="50px"/>
	<col width="50px"/>
	<col width="100px"/>
	<col width="300px"/>
	<col width="200px"/>
	<tr>
		<th><input type="checkbox" name="all" onclick="allSel(this.checked)"/></th>
		<th>seq</th><th>작성자</th><th>제목</th><th>작성일</th>
	</tr>
	<%
		for(int i=0; i<list.size(); i++){
			HkDto dto = list.get(i);
			%>
			<tr>
				<td><input type="checkbox" name="chk" value="<%=dto.getSeq()%>"/></td>
				<td><%=dto.getSeq() %></td>
				<td><%=dto.getId() %></td>
				<td><a href="detailBoard.board?seq=<%=dto.getSeq()%>"><%=dto.getTitle()%></a></td>
				<td><%=dto.getRegDate()%></td>
			</tr>
			<%
		}
	%>
	<tr>
		<td colspan="5">
			<button type="button" onclick="insertBoardForm()">글추가</button>
			<input type="submit" value="삭제"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>