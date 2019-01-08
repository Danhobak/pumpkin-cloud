/**
 *
 *login.jsp를 위한 script
 * 
 */

$(function(){
	$('#login_btn').click(function(){
		go_Login();
	});
	
	$(document).keypress(function(e){
		if(e.which == 13){
			go_Login();
		}
	})
});

function go_Login(){
	var uid = $('#login_id_input').val();
	var upw = $('#login_pw_input').val();
	
	if(uid < 0 || uid.length == 0 || !uid){
		alert('아이디를 입력해주세요!');
	}else{
		if(upw < 0 || upw.length == 0 || !upw){
			alert('비밀번호를 입력해주세요!');
		}else{
			$('#loginForm').submit();
		}
	}
}