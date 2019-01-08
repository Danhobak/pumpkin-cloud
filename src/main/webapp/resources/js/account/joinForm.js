/**
 *
 *join.jsp를 위한 script
 * 
 */
var idck = 0;	//id중복검사 여부 확인용
var pwck = 0;	//pw일치검사 여부 확인용

$(function(){
	
	//ID중복 체크 함수
	$("#id_chk_btn").click(function() {
		//userid 를 param.
		var userid = $("#join_id_input").val();

		if(!userid || userid.length == 0){
			alert("아이디를 입력하세요!");
			$("#join_id_input").focus();
		}else{
			$.ajax({					// ajax로 비동기 요청 처리
				async : true,
				type : 'POST',
				data : {
					"id" : userid
				},
				url : "/joinIdCheck",		// controller의 /joinIdCheck로 요청 전달
				success : function(cnt) {
					cnt = parseInt(cnt);
					if (cnt > 0) {		// (아이디 중복일 경우 = 0 , 중복이 아닐경우 = 1 )
						alert("아이디가 존재합니다. 다른 아이디를 입력해주세요.");
						$("#join_id_input").focus();
					} else {
						alert("사용가능한 아이디입니다.");
						$("#join_password_input").focus();
						idck = 1;		//아이디가 중복하지 않으면  idck = 1
					}
				},
				error : function(xhr, status, error) {			// xhr : XMLhttprequest객체(Ajax)관련 자바스크립트 객체
					alert("error : " + error + ", " + status);	// error : error msg, status : error status
				}
			});
		}
	});
	
	$("#join_pwchk_input").focusout(function(){
		var originPw = $("#join_password_input").val();
		var checkPw = $("#join_pwchk_input").val();
		if(!(originPw == checkPw)){
			$(".pwchk_errMsg").html("<span>비밀번호가 일치하지 않습니다.</span>");
		}else{
			pwck = 1;
		}
	});
});


//유효성 검사 체크 함수
function checkJoinValidity() {
	var validOK = true;
	var validChecks = {
		id : {
			result : /^\S{4,8}$/.test($("#join_id_input").val()),
			errormsg : "공백없이 4~8자를 입력하세요."
		},
		password : {
			result : /^\S{4,12}$/.test($("#join_password_input").val()),
			errormsg : "패스워드는 4자~12자로 입력해주세요."
		},
		name : {
			result : /^\S{2,6}$/.test($("#join_name_input").val()),
			errormsg : "이름은 2~6자로 입력해주세요."
		},
		phone : {
			result : /^\d{10,11}$/.test($("#join_phone_input").val()),
			errormsg : "'-'없이 숫자만 입력해주세요."
		},
		email : {
			result : /^[\S]+@[\S]+\.[a-zA-Z]+/.test($("#join_email_input").val()),
			errormsg : "메일형식을 준수하여 작성해주세요."
		}
	};
	var validInfo = null;
	var $validErrMsg = null;
	console.log(idck+", "+pwck);
	if(idck < 1 || pwck < 1){	//ID중복, PW일치 여부확인 후 validation 진행
		if(idck < 1){
			alert("중복확인을 해주세요!");
			$("#id_chk_btn").focus();
		}else{
			alert("비밀번호가 일치하지 않습니다!");
			$("#join_pwchk_input").focus();
		}
	}else{
		for(var key in validChecks) {
			validInfo = validChecks[key];
			$validErrMsg = $("#join_"+key+"_input").parent("div").next(".errMsg");
			if(!validInfo.result) {
				if(validOK)
					validOK = false;
				$validErrMsg.html("<span>"+validInfo.errormsg+"</span>");
			} else {
				$validErrMsg.html("");
			}
		}
		console.log(validOK);
		if(validOK)
			$("#joinForm").submit();
	}
}
