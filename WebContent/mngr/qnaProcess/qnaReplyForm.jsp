<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="/jsp-shoppingmall/css/style.css"/>
<script src="/jsp-shoppingmall/js/jquery-3.2.1.min.js"></script>
<script src="/jsp-shoppingmall/mngr/qnaProcess/qnawrite.js"></script>

<c:if test="${empty sessionScope.id}">
  <meta http-equiv="Refresh" content="0;url=/jsp-shoppingmall/mg/managerMain.do" >
</c:if>

<input type="hidden" id="qna_writer" value="manager">
<input type="hidden" id="qna_id" value="${qna_id}">
<input type="hidden" id="book_id" value="${book_id}">
<input type="hidden" id="book_title" value="${book_title}">
<input type="hidden" id="qora" value="${qora}">

<div id="writeForm" class="box">
   <ul>
      <li><p>[${book_title}] 의 QnA </p>
          <p>QnA내용:${qna_content}</p>
      <li><label for="rContent">답변</label>
          <textarea id="rContent" rows="13" cols="50"></textarea>
      <li class="label2">
          <button id="replyPro">답변하기</button>
          <button id="cancle">취소</button> 
   </ul>
</div>