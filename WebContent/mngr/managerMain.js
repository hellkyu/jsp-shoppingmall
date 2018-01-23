var status = true;

$(document).ready(function(){
	$("#registProduct").click(function(){//[상품등록] 버튼 클릭
		window.location.href = "jsp-shoppingmall/mg/bookRegisterForm.do";
	});
	
	$("#updateProduct").click(function(){//[상품수정/삭제] 버튼 클릭
		window.location.href = "jsp-shoppingmall/mg/bookList.do?book_kind=all";
	});
	
	$("#orderedProduct").click(function(){//[전체구매목록 확인] 버튼 클릭
		window.location.href = "jsp-shoppingmall/mg/orderList.do";
	});
	
	$("#qna").click(function(){//[상품 QnA답변] 버튼 클릭
		window.location.href = "jsp-shoppingmall/mg/qnaList.do";
	});
});