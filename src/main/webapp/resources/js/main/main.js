
$(function(){
	
	//클릭시 border값 및 checked로 상태 전환
	$('#user_data_container').find('img[id!=dir_back_img]').click(function(){
		if($(this).attr('id')=='dir_img'){
			$(this).toggleClass('dir_clicked');
		}else if($(this).attr('id')=='file_img'){
			$(this).toggleClass('file_clicked');
		}else{
			$(this).parent().toggleClass("entity_outline_clicked");
		}
		var $checkboxItem =  $(this).parent().children().last();
		$checkboxItem.prop("checked",!$checkboxItem.prop("checked"));//체크박스 토글구현
	});
	
	//마우스 hover 관련 기능
	$('#user_data_container').find('img').not('.dir_back_img').hover(function(){
		$(this).addClass("entity_outline_hover");
	},function(){
		$(this).removeClass("entity_outline_hover");
	});
	
//---------------------------------------- 상단 버튼 관련 ------------------------------------------------
	//input file태그 숨김 및 change시 submit()
	$('#user_upload').click(function(e){
		e.preventDefault();
		$('#file').click();
	});
	
	$('#file').change(function(){
		$(this).parents().submit();
	});

	/* 다운로드 버튼 기능 */
	$('#user_download').click(function(){	
		//선택된 값 가져오기
		var selectedItem = $('input[type=checkbox]:checked');
		
		if(selectedItem.length > 0){
			var forLimit = selectedItem.length;
			var itemName = [];
			for(var i=0; i < forLimit; i++){
				itemName.push(selectedItem[i].value);
			}
			window.open('/download?file_names='+itemName,'_blank');
		}else{
			alert('선택된 항목이 없어요!');
		}
	});
	
	/* 삭제 버튼 기능 */
	$('#user_data_delete').click(function(){
			var selectedItem = $('input[type=checkbox]:checked');
			
			if(selectedItem.length > 0){
				var flag = confirm('정말 삭제하시겠어요? 되돌릴 수 없어요!');
				
				if(flag){
					var forLimit = selectedItem.length;
					var itemName = [];
					for(var i=0; i < forLimit; i++){
						itemName.push(selectedItem[i].value);
					}
					$.ajax({
						type : 'POST',
						traditional : true,
						data : {
							"file_name" : itemName
						},
						url : "/delete",
						success : function(){
							window.location.reload();
						},
						error : function(xhr, status, error) {
							alert("error : " + error + ", " + status);
						}
					});
				}
			}else{
				alert('선택된 항목이 없어요!');
			}
	});
	
//---------------------------------------- 파일 관련 ------------------------------------------------	
	/* 단일 파일 더블클릭 다운로드 기능 */
	$('img[id*=file_img]').dblclick(function(){	//GET방식으로 새 창 열어서 다운로드 
		var fileName = $(this).next().next().text();
		alert(fileName);
		window.open('/download?file_names='+fileName,'_blank');
	});
	

//---------------------------------------- 디렉토리 관련 ----------------------------------------------------
	
	//디렉토리 생성
	$('#mk_user_dir').click(function(){
	      dirName = prompt();
	      if(dirName != null){
	    	  dirController(dirName, "mkdir", "/mkDir");
	      }
	});
	
	//디렉토리 이동
	$('img[id*=dir_img]').dblclick(function(){
		var dirName = $(this).parents().children(':eq(2)').text();
		dirController(dirName, 1, "/moveDir");
	});
	
	//상위 디렉토리로 이동
	$('#dir_back_img').dblclick(function(){
		var dirName = $(this).parents().children(':eq(2)').text();
		dirController(dirName, 0, "/moveDir");
	});
	
	
//---------------------------------------- SNB 관련 ---------------------------------------------------
	$('img[id*=snb_]').click(function(){
		var type = $(this).prop('id').substring(4);
		
		location.href='/typeSelect?type='+type;
	});
	
	
	
//---------------------------------------- 클릭 메뉴 관련 ----------------------------------------------------
	$(document).bind("contextmenu", function(event) { 
	    event.preventDefault();
	    $("<div class='custom-menu'>Custom menu</div>")
	        .appendTo("body")
	        .css({top: event.pageY + "px", left: event.pageX + "px"});
	}).mousedown(function(event) {
			$("div.custom-menu").hide();
	});
});


function dirController(dirName, trigger, url){
	$.ajax({
        type : 'POST',
        data : {
           "dirName" : dirName,
           "trigger" : trigger
        },
        url : url,
        success : function(){
           window.location.reload();
        },
        error : function(xhr, status, error) {
           alert("error : " + error + ", " + status);
        }
   });
}

