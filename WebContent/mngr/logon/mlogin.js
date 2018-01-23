$(document).ready(function(){
	//[로그인]버튼을 클릭하면 자동실행	
	$("#login").click(function(){
		var query = {id:$("#id").val(), 
				     passwd:$("#passwd").val()};
		$.ajax({
		   type: "POST",
		   url: "/jsp-shoppingmall/mg/managerLoginPro.do",
		   data: query,
		   success: function(data){
		   	window.location.href = "/jsp-shoppingmall/mg/managerMain.do";	
		   }
		});

	});
	
	//[로그아웃]버튼을 클릭하면 자동실행
	$("#logout").click(function(){
		$.ajax({
		   type: "POST",
		   url: "/jsp-shoppingmall/mg/managerLogout.do",
		   success: function(data){
			   window.location.href = "/jsp-shoppingmall/mg/managerMain.do";
		   }
		});
	});
 });