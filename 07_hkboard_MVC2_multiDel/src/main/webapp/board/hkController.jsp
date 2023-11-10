<%@page import="java.net.URLEncoder"%>
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
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
   //1단계:command 값 받기 - 어떤 요청인지 확인을 위해 값을 받는다
   String command=request.getParameter("command");

   //2단계:DAO 객체 생성 - DB관련 작업 수행을 위해 준비..
   HkDao dao=new HkDao();
   
   //3단계:command값 확인해서 분기 실행(요청분기)
   if(command.equals("boardList")){//글목록 요청 처리
      //5단계:dao메서드 실행
      List<HkDto> list=dao.getAllList();
      //6단계:Scope객체에 담기
      request.setAttribute("list", list);//requestScope에 저장: "list":list
      //7단계:페이지 이동
      pageContext.forward("boardList.jsp");
   }else if(command.equals("insertBoadrForm")){ //글추가폼으로 이동
      response.sendRedirect("insertBoadrForm.jsp");
   }else if(command.equals("insertBoard")){ //글추가하기 실행
      //4단계 : 파라미터 받기
      String id=request.getParameter("id");
      String title=request.getParameter("title");
      String content=request.getParameter("content");
      
      boolean isS=dao.insertBoard(new HkDto(id,title,content));
      if(isS){
         %>
         <script type="text/javascript">
            alert("글을추가는할건데생각은하고작성했는지오늘하루정말행복하게보냈는지부모님께연락은드렸는지다시한번생각해보아요일상은공기처럼소중하지만평소에는소중함을자각하지못한채로살아가는경우가참많답니다항상주어진것에만족하고감사하는습관을가질수있도록노력하는사람이되도록항상명심합시다");
            location.href="hkController.jsp?command=boardList";//글추가하고, 글목록 페이지로 이동하기
         </script>
         <%
      }else{
         %>
         <script type="text/javascript">
            alert("글추가실패!");
            location.href="hkController.jsp?command=insertBoadrForm";//글추가 실패하면, 글추가 페이지로 이동하기
         </script>
         <%
      }
   }else if(command.equals("detailBoard")){
      int seq = Integer.parseInt(request.getParameter("seq"));
      HkDto dto = dao.getBoard(seq);
      request.setAttribute("dto", dto);
      pageContext.forward("detailBoard.jsp");
   }else if(command.equals("updateBoardForm")){
      int seq = Integer.parseInt(request.getParameter("seq"));
      HkDto dto = dao.getBoard(seq);
      request.setAttribute("dto", dto);
      pageContext.forward("updateBoardForm.jsp");
   }else if(command.equals("updateBoard")){
      int seq = Integer.parseInt(request.getParameter("seq"));
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      
      boolean isS = dao.updateBoard(new HkDto(seq,title,content));
      if(isS){
         //수정하고 상세조회페이지로 이동
         response.sendRedirect("hkController.jsp?command=detailBoard&seq="+seq);
      }else{
         response.sendRedirect("error.jsp?msg=글수정실패");
      }
   }else if(command.equals("deleteBoard")){
	   int seq=Integer.parseInt(request.getParameter("seq"));
	   boolean isS=dao.deleteBoard(seq);
	   if(isS){
		   response.sendRedirect("hkController.jsp?command=boardList");
	   }else{
		   response.sendRedirect("error.jsp?msg=글삭제 실패");
	   }
   }else if(command.equals("muldel")){
	   String[]seqs= request.getParameterValues("chk");
	   
	   //유효값을 서버쪽에서 처리할 수 있다.
//	   if(seqs==null||seqs.length==0){
		   %>
<!-- 		<script type="text/javascript">
		   	alert("최소 하나이상 체크하세요");
		   	location.href="hkController.jsp?command=boardList";
		   </script> -->		   
		   <%
//	   }else{
		   boolean isS=dao.mulDel(seqs);
		   if(isS){
			   response.sendRedirect("hkController.jsp?command=boardList");
		   }else{
			   response.sendRedirect("Error.jsp?msg="+ URLEncoder.encode("여러글 삭제 실패","utf-8"));
		   }
		   
//	   }
	   
   }
%>
</body>
</html>
