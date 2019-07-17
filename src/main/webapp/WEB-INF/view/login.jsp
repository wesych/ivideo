<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="include.jsp"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" language="javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" language="javascript" src="layer/layer.js"></script>

<script type="text/javascript" language="javascript" src="js/security.js"></script>

<script type="text/javascript">
	function valuechange() {
		$("#errorinfo").text("");
	}
</script>

<script type="text/javascript">
	$(function() {
		$("#login_submit").click(function() {
			var name = $("#login_name").val();
			var pwd = $('#login_pwd').val();
			if (name == null || name.length==0) {
				$("#errorinfo").text("<spring:message code="js_name_not_null"/>");
				return;
			}
			if(pwd==null || pwd.length==0){
				$("#errorinfo").text("<spring:message code="js_pwd_not_null"/>");
				return;
			}
			$.getJSON('rsa', function(data) {
				var modulus = data.map.modulus,
					exponent = data.map.exponent;
				var publicKey = RSAUtils.getKeyPair(exponent, '', modulus);
				$('#login_pwd').val(RSAUtils.encryptedString(publicKey, pwd));
				$("#login").submit();
			});
		});
	});
</script>

<script type="text/javascript">
	function login() {
		var info = {
			username : $("#login_name").val(),
			password : $("#login_pwd").val()
		};
		$.ajax({
			data : info,
			type : "GET",
			dataType : 'json',
			url : "login",
			success : function(data) {
				if (data.login == "success") {
					parent.window.location.reload();
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				} else {
					$("#errorinfo").text("<spring:message code="js_name_or_pwd_error"/>");
				}
			}
		});
	}
</script>
</head>
<body>

	<img src="images/rp_top.jpg" width="282" height="10" alt="" style="float:left;" />
	<div class="rp_loginpad">
		<form id="login" name="login" action="javascript:login();" method="post">
			<span class="rp_titxt"><spring:message code="user_login"/></span>
			<span class="rp_membrusr"><spring:message code="user_account"/>:</span>
			<input id="login_name" type="text" class="rp_usrip" oninput="javascript:valuechange();" value="" />
			
			<span class="rp_membrpwd"><spring:message code="user_password"/>:</span>
			<input id="login_pwd" type="password" class="rp_pwdrip" oninput="javascript:valuechange();" />
			
			<!-- Login error info -->
			<span id="errorinfo" class="rp_loginresult">&nbsp;</span>
			
			<a class="rp_login">
				<input id="login_submit" style="margin-left:100px;" type="button" value="<spring:message code="user_confirm"/>" />
			</a>
			<span class="rp_notmem">
				<a href="#" style="font:13px Arial, Helvetica, sans-serif; color:#FFFFFF;"></a>
			</span>
		</form>
	</div>
</body>
</html>