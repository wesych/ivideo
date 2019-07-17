<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" language="javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" language="javascript" src="layer/layer.js"></script>

<script type="text/javascript">
	function land() {
		layer.open({
			type : 2,
			title : false,
			maxmin : false,
			scrollbar : false,
			offset : '',
			skin : 'layui-layer-hei',
			shadeClose : true,
			area : [ '282px', '200px' ],
			content : 'loginpage'
		});
	}
</script>

<script type="text/javascript">
	function logout(){
		$.ajax({
			type : "GET",
			dataType : 'json',
			url : "logout",
			success : function(data) {
				if (data.logout == "success") {
					location.reload(); 
				} else {
					alert("<spring:message code="js_logout_failed"/>");
				}
			}
		});
	}
</script>

<script type="text/javascript">
	function switchLang(){
		$.ajax({
			type : "GET",
			dataType : 'json',
			url : "switch",
			success : function(data) {
				if (data.swl == "success") {
					location.reload();
				} else {
					alert("<spring:message code="js_switch_lang_failed"/>");
				}
			}
		});
	}
</script>

</head>
<body>
    <!--Top Panel starts here -->
	<div id="top_panel">
		<a href="" class="logo"><img src="images/logo.png" width="255" height="36" alt="" /></a><br />
		<div class="tp_navbg">
			<a href=""><spring:message code="index"/></a>
			<a href="category/music"><spring:message code="music"/></a>
			<a href="category/amuse"><spring:message code="amuse"/></a>
			<a href="category/funny"><spring:message code="funny"/></a>
			<c:if test="${isLogin}">
				<a href="upload"><spring:message code="upload"/></a>
			</c:if>
		</div>
		
		<div class="tp_smlgrnbg">
			<span class="tp_sign">
				<a id="" href="register" class="tp_txt"><spring:message code="register"/></a>
				
				<span class="tp_divi">|</span>
			
				<c:if test="${isLogin==false}">
				<a id="userlogin" href="javascript:land()" class="tp_txt"><spring:message code="login"/></a>
				</c:if>
				<c:if test="${isLogin}">
				<a href="javascript:logout()" class="tp_txt"><spring:message code="logout"/></a>
				</c:if>
				
				<span class="tp_divi">|</span>
				
				<c:if test="${isUS==false}">
				<a href="javascript:switchLang()" class="tp_txt"><spring:message code="en"/></a>
				</c:if>
				<c:if test="${isUS}">
				<a href="javascript:switchLang()" class="tp_txt"><spring:message code="zh"/></a>
				</c:if>
			</span>
		</div>
		
		<div class="tp_barbg">
		  <form id="search" name="search" action="search" method="post" >
			<input name="key" id="key" type="text" class="tp_barip" />
			<input type="image" name="submit_key" class="tp_search" src="images/tp_search.png" onclick="this.form.submit()" />
			<c:if test="${isLogin==false}">
				<span class="tp_welcum"><spring:message code="welcome"/><b><spring:message code="guest"/></b></span>
			</c:if>
			<c:if test="${isLogin}">
				<a href='myspace/${loginUser.userId}'><img src='avatar/${loginUser.avatar}' width="40" height="40" alt="" class="tp_homeavatar" /></a>
				<span class="tp_homename"><b>${loginUser.userName}</b></span>
			</c:if>
		  </form> 
		</div>
	</div>
       <!--Top Panel ends here -->
</body>
</html>
