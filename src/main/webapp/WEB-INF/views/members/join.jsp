<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script>
	window.onload = function(){
		document.getElementById("address_kakao").addEventListener("click", function(){
			new daum.Postcode({
				oncomplete:function(data){
					document.getElementById("address_kakao").value = data.address;
				}
			}).open();
		});
	}
</script>

</head>
<body>
<h2>회원가입</h2>
<form name="frm" onsubmit="setMemberAddress()" method="POST" action="/members/join" >
	<input type="radio" name="chk_info" value="일반회원" checked />
	<label for="일반회원">일반회원</label>
	<input type="radio" name="chk_info" value="판매자" />
	<label for="판매자">판매자</label>
     id : <input type="text" name="memberId" /> <br />
         이름 : <input type="text" name="memberName" /> <br />
         이메일 : <input type="text" name="memberEmail" /> <br />
         비밀번호 : <input type="password" name="memberPwd" /> <br />
         권한 : <input type="text" name="memberLevel" /> <br />
         주소 : <input type="text" id="address_kakao" name="address" readonly/> <br />
         상세주소 : <input type="text" name="detailAddress"/> <br />
     <input type="hidden" name="memberAddress"/>
         전화번호 : <input type="text" name="memberTel" /> <br />
         생년월일 : <input type="date" name="memberBirth" /> <br />
     <div id="onlySeller" style="display: none;">
     	사업자명 : <input type="text" name="businessName" /> <br />
         사업자번호 : <input type="text" name="businessRegistrationNo" /> <br />
         </div>
     	  
     
         <input type="submit" value="가입하기">
</form>
<script>
//주소처리 
function setMemberAddress(){
	var j = document.frm;
	j.memberAddress.value = j.address.value +" "+j.detailAddress.value;
}

function change() {
	document.getElementById('onlySeller').style.display="none";
}
$(document).on()

</script>
</body>
</html>