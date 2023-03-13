// 약관동의서에 input 체크를 하지 않을경우 alert 창 띄우기

$(document).ready(function(){
		$("#chbox_all").click(function(){
					if($("#chbox_all").is(":checked")) $("input[name=chk]").prop("checked", true);
					else $("input[name=chk]").prop("checked", false);
					});
					$("input[name=chk]").click(function() {
				var total = $("input[name=chk]").length;
				var checked = $("input[name=chk]:checked").length;
				
				if(total != checked) $("#chbox_all").prop("checked", false);
				else $("#chbox_all").prop("checked", true); 
			});
		});

$('#com').click(function(){
	checkTerms();
});

function checkTerms(){
	if($('#chbox1').is(':checked') == false ||
		$('#chbox2').is(':checked') == false){
			alert("약관 동의에 체크해주세요")
		} else {
			location.href="/signup/company";
		}};
		
$('#per').click(function(){
	checkTerms1();
});

function checkTerms1(){
	if($('#chbox1').is(':checked') == false ||
		$('#chbox2').is(':checked') == false){
			alert("약관 동의에 체크해주세요")
		} else {
			location.href="/signup/personal";
		}};
		
