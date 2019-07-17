<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="include.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Go Video ： Registration</title>

<script type="text/javascript" language="javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" language="javascript" src="layer/layer.js"></script>

<!-- check input -->
<script type="text/javascript"> 
	
	var flag = true;
	
	<!-- Check user name -->
	var isName;
	function checkUserName() {
		var name = $("#username").val();
		if (name.length == 0) {
			$("#name_result").text("<spring:message code="js_reg_username_not_null"/>");
			$("#name_result").css("color", "red");
			return;
		}
		$.ajax({
			async : false,
			data : "username=" + $("#username").val(),
			type : "GET",
			dataType : 'json',
			url : "checkUsername",
			success : function(data) {
				if (data.name_check == "success") {
					$("#name_result").text("<spring:message code="js_reg_name_available"/>");
					$("#name_result").css("color", "green");
					isName = true;
				} else {
					$("#name_result").text("<spring:message code="js_reg_name_unavailable"/>");
					$("#name_result").css("color", "red");
					isName = false;
				}
			}
		});
	}

	<!-- Check email -->
	var isEmail;
	function checkEmail(){  
    	var email = $("#email").val();
    	if(email.length == 0){
    		$("#email_result").text("<spring:message code="js_reg_email_not_null"/>");
    		$("#email_result").css("color","red");
    		return;
    	} 
    	var emailPat=/^(.+)@(.+)$/;
    	var matchArray=email.match(emailPat);
    	if (matchArray==null) {
    		$("#email_result").text("<spring:message code="js_reg_email_format"/>");
    		$("#email_result").css("color","red");
    		return;
    	} 
        $.ajax({  
        data:"email="+email,  
        type:"GET",  
        dataType: 'json',  
        url:"checkEmail",  
        success:function(data){ 
        	if(data.email_check=="success"){
        		$("#email_result").text("<spring:message code="js_reg_email_available"/>");
        		$("#email_result").css("color","green");
        		isEmail = true;
        	}else {
        		$("#email_result").text("<spring:message code="js_reg_email_unavailable"/>");
        		$("#email_result").css("color","red");
        		isEmail = false;
        	}
        }  
        });  
    }  
    
    <!-- Check password format -->
    var isPwd;
    function checkPwd(){  
    	var pwd = $("#pwd").val();
    	if(pwd.length == 0){
    		$("#pwd_result").text("<spring:message code="js_reg_pwd_not_null"/>");
    		$("#pwd_result").css("color","red");
    		return;
    	} else {
    		$("#pwd_result").text("");
    		isPwd = true;
    	}
    	var reg = /[\u4e00-\u9fa5]/g;
    	if(reg.test(pwd)){
    		$("#pwd_result").text("<spring:message code="js_reg_pwd_format"/>");
    		$("#pwd_result").css("color","green");
    		isPwd = false;
    		return;
    	} else {
    		$("#pwd_result").text("");
    		isPwd = true;
    	}
    }
    
    <!-- Check password repeat -->
    var isRepeat;
	function checkRepeat(){ 
		var pwd = $("#pwd").val();
		var repeatPwd = $("#repeatPwd").val();
		if(pwd == repeatPwd){
			$("#repeat_result").text("");
			isRepeat = true;
		} else {
			$("#repeat_result").text("<spring:message code="js_reg_pwd_not_match"/>");
    		$("#repeat_result").css("color","red");
    		isRepeat = false;
		}
    }
	
	<!-- Refresh verification code image -->
	function refresh(obj) {
	    obj.src = "verifyCode?"+Math.random();
	}

	<!-- Check verify code -->
	var isCode;
	function checkVerifyCode() {
		var code = $("#code").val();
		$.ajax({
			data : "code=" + code,
			type : "GET",
			dataType : 'json',
			url : "checkCode",
			success : function(data) {
				if (data.code_check == "success") {
					$("#code_result").text("<spring:message code="js_reg_verifycode_correct"/>");
					$("#code_result").css("color", "green");
					isCode = true;
				} else {
					$("#code_result").text("<spring:message code="js_reg_verifycode_incorrect"/>");
					$("#code_result").css("color", "red");
					isCode = false;
				}
			}
		});
	}
	
	function validate() {
		if(isName == null) checkUserName();
		if(isEmail == null) checkEmail();
		if(isPwd == null) checkPwd();
		if(isRepeat == null) checkRepeat();
		if(isCode == null) checkVerifyCode();
		if(isName==true && isEmail==true && isPwd==true
				&& isRepeat==true && isCode==true){
			return true;
		} else {
			return false;
		}
	}
	
	<!-- register -->
	function doRegister() {
		if(validate() == false) {
			return;
		} else {
			var user = {
		    		name:$("#username").val(),
		    		email:$("#email").val(),
		    		gender:$("#gender").val(),
		    		pwd:$("#pwd").val()
		        };
			$.ajax({
				data : user,
				type : "GET",
				dataType : 'json',
				url : "doRegister",
				success : function(data) {
					if (data.register == "success") {
						layer.msg('<spring:message code="js_reg_success"/>', {icon: 6}, function(){
							window.location.href="";
						});
					} else {
						layer.msg('<spring:message code="js_reg_failed"/>', {icon: 5}, function(){
							window.location.href="register";
						});
					}
				}
			});
		}
	}
</script> 

</head>

<body>
  <div id="main_block">
  	 <div id="innerblock">
                  
		<!--Top Panel starts here -->
		<%@ include file="header.jsp" %>	
        <!--Top Panel ends here -->
				   
		<!--content Panel starts here -->
		<div id="contentpanel">
		
			<!-- Registration -->
			<div id="lp_padd">
			     <span class="rp_registertitxt"><spring:message code="new_user_signup"/></span>
				 <form id="register" name="register" action="javascript:doRegister();" method="post" >
					<span class="rp_membrusr"><spring:message code="nickname"/></span>
					<input name="username" id="username" type="text" class="rp_register" value="" onblur="javascript:checkUserName();"/>
					<span id="name_result" class="rp_memresult"></span>
					
					<span class="rp_membrusr"><spring:message code="email"/></span>
					<input name="email" id="email" type="text" class="rp_register" value="" onblur="javascript:checkEmail();" />
					<span id="email_result" class="rp_memresult"></span>
					
					<span class="rp_membrusr"><spring:message code="male"/></span>
					<input name="gender" id="gender" type="radio" class="rp_gender" value="1" checked />
					<span class="rp_membrusr"><spring:message code="female"/></span>
					<input name="gender" id="gender" type="radio" class="rp_gender" value="0" />
						
					<span class="rp_membrpwd"><spring:message code="pwd"/></span>
					<input name="pwd" id="pwd" type="password" class="rp_register" onblur="javascript:checkPwd();" />
					<span id="pwd_result" class="rp_memresult"></span>
					
					<span class="rp_membrpwd"><spring:message code="confirm_pwd"/></span>
					<input name="repeatPwd" id="repeatPwd" type="password" class="rp_register" onblur="javascript:checkRepeat();" />
					<span id="repeat_result" class="rp_memresult"></span>
					
					<span class="rp_membrpwd"><spring:message code="verifyCode"/></span>
					<input type="text" name="code" id="code" class="rp_register" onblur="javascript:checkVerifyCode();" />
					<span class="rp_randomImage"><img id="codeimage" title="<spring:message code="refresh"/>" onclick="javascript:refresh(this);" src="verifyCode" ></img></span>
					<span id="code_result" class="rp_memresult"></span>
					
					<span class="rp_membrpwd"></span>
					<a class="rp_login"><input name="submit" type="submit" value="<spring:message code="dosignup"/>" /></a>
				 </form>
			</div>
			
			<!-- Random to show 3 users has registered -->
			<div id="rp_padd">
				<img src="images/rp_top.jpg" width="282" height="10" alt="" class="rp_upbgtop" />
				<div class="rp_loginpad" style="padding-bottom:0px; border-bottom:none;">
					<span class="rp_titxt"><spring:message code="registered_user" /></span>
				</div>
				
				<c:forEach items="${randomUserList}" var="item">  
					<img src='avatar/${item.avatar}' width="70" height="70" alt="" class="rp_inrimgavatar" />
					<span class="rp_inrimgxt" style="margin-top:28px; margin-left:24px; width:120px;">
					<span style="font:bold 11px/20px Arial, Helvetica, sans-serif;">${item.userName }</span><br /><spring:message code="gender" /><c:if test="${item.gender==0}"><spring:message code="female"/></c:if><c:if test="${item.gender==1}"><spring:message code="male" /></c:if><br/> ${fn:substring(item.joinTime,0,10)} </span>
					<img src="images/rp_catline.jpg" width="262" height="1" alt="" class="rp_catline1" /><br />
				</c:forEach>
			</div>
		</div>	
		<!--content Panel ends here -->
					
		<!-- footer -->
		<%@ include file="footer.jsp" %>
		
    </div> 
  </div>
</body>			
</html>
