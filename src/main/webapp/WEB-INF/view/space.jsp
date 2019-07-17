<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="include.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Personal Home</title>

<script type="text/javascript" language="javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" language="javascript" src="layer/layer.js"></script>

<!-- Remove video collection -->
<script type="text/javascript">
	function removeCollection(videoId){
		var info = {
				video_id:videoId,
				username:'${loginUser.userName}'
	        };
		$.ajax({
			data : info,
			type : "GET",
			dataType : 'json',
			url : "detach",
			success : function(data) {
				if (data.det == "success") {
					document.getElementById("video_"+videoId).style.display="none";
				}
			}
		});
	}
</script>

<!-- show edit personal info script -->
<script type="text/javascript">
    function editInfo() {
        document.getElementById("personInfo").style.display="none";
        document.getElementById("edit").style.display="";
    }
</script>

<!-- cancel editing personal info -->
<script type="text/javascript">
    function cancelEdit() {
        document.getElementById("personInfo").style.display="";
        document.getElementById("edit").style.display="none";
    }
</script>

<!-- Checker -->
<script type="text/javascript">
	var isImage;
    function checkImage() {
        var file = $("#newAvatar").val();
        if(file == null || file.length==0) {
        	isImage = true;
        } else {
        	if(!file.endsWith(".jpg") && !file.endsWith(".JPG")){
            	$("#imageformatchecker").text("<spring:message code="js_upload_image"/>");
    			$("#imageformatchecker").css("color", "red");
    			isImage = false;
            } else {
            	$("#imageformatchecker").text("");
            	isImage = true;
            }
        }
        
    }
    
    var isOldPwd;
    function checkOriginPwd() {
    	var pwd = $("#oldPwd").val();
    	if(pwd==null || pwd.length==0){
    		$("#oldpwdchecker").text("<spring:message code="js_reg_pwd_not_null"/>");
			$("#oldpwdchecker").css("color", "red");
			isOldPwd = false;
    	} else {
    		$("#oldpwdchecker").text("");
    		isOldPwd = true;
    	}
    }
    
    var isNewPwd;
    function checkNewPwd() {
    	var pwd = $("#newPwd").val();
    	if(pwd==null || pwd.length==0){
    		$("#newpwdchecker").text("<spring:message code="js_reg_pwd_not_null"/>");
			$("#newpwdchecker").css("color", "red");
			isNewPwd = false;
    	} else {
    		$("#newpwdchecker").text("");
    		isNewPwd = true;
    	}
    }

    var isRepeat;
    function checkRepeatPwd() {
        var newpwd = $("#newPwd").val();
        var repeatpwd = $("#confirmNewPwd").val();
        if(repeatpwd != newpwd){
        	$("#pwdchecker").text("<spring:message code="js_reg_pwd_not_match"/>");
			$("#pwdchecker").css("color", "red");
			isRepeat = false;
        } else {
        	$("#pwdchecker").text("");
        	isRepeat = true;
        }
    }
    
    function validate() {
		if(isImage == null) checkImage();
		if(isOldPwd == null) checkOriginPwd();
		if(isNewPwd == null) checkNewPwd();
		if(isRepeat == null) checkRepeatPwd();
		
		if(isImage==true && isOldPwd==true && isNewPwd==true && isRepeat==true){
			return true;
		} else {
			return false;
		}
	}

    function doModify() {
    	if(validate() == false) {
			return;
		} else {
			$.ajax({
				data : new FormData($('#newinfo')[0]),
				type : "POST",
				dataType : 'json',
				url : "doModify",
				contentType: false,  
		        processData: false,  
				success : function(data) {
					if (data.mod == "success") {
						layer.msg('<spring:message code="js_modify_success"/>', {offset : ''}, function(){
							location.reload(); 
						});
					} else {
						var msg = '<spring:message code="js_modify_failed"/>';
						if(data.pwd != null && data.pwd == "failed"){
							msg="<spring:message code="js_pwd_error"/>";
						}
						layer.msg(msg, {offset : ''}, function(){
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
	 
	 <!--Content Panel starts here -->
	 <div id="contentpanel">
		<div id="lp_padd">
			
			<!-- my play record -->
			<div class="lp_featpad">
				<div class="lp_featnav">
					<a><b><spring:message code="playRecord"/></b></a>
				</div>
				<c:forEach items="${recordList}" var="item">
					<a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="152" height="92" alt="" class="lp_featimg1" />
					<span class="cp_featpara">
		            <span style="float:left; width:250px; display:inline;">...</span>
					<span style="float:left; width:250px; display:inline;"><span class="cp_featname"><b>${item.title } </b><Br /><spring:message code="${item.label}"/></span>
					
					<span class="cp_featxt">${item.description }</span>
					</span>
					<span class="cp_spaceview"><spring:message code="upload_time"/> : ${fn:substring(item.uploadTime,0,10)}<br /><spring:message code="watch"/> : ${item.playCount }<br /><spring:message code="comment"/> : ${item.commentCount}<br />
					</span>
					</span>
					<img src="images/lp_featline.jpg" width="634" height="1" alt="" class="lp_featline" />
				    </a>
			    </c:forEach>
			</div>
			
			<!-- my collection -->
			<div class="lp_featpad">
				<div class="lp_featnav">
					<a><b><spring:message code="myCollection"/></b></a>
				</div>
				<c:forEach items="${collectionList}" var="item">
				<div id='video_${item.videoId }'>
				<a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="152" height="92" alt="" class="lp_featimg1" /></a>
				<span class="cp_featpara">
	            	<span style="float:left; width:250px; display:inline;">...</span>
					<span style="float:left; width:250px; display:inline;">
						<span class="cp_featname"><b>${item.title } </b><Br /><spring:message code="${item.label}"/></span>
						<span class="cp_featxt">${item.description }</span>
					</span>
					<span class="cp_spaceview">
						<a href='javascript:removeCollection(${item.videoId})'><img id="delimage" src="images/delete_video.png" align="top" width="24" height="24" alt="" />
						</a>
						<br /><spring:message code="upload_time"/> : ${fn:substring(item.uploadTime,0,10)}<br /><spring:message code="watch"/> : ${item.playCount }<br /><spring:message code="comment"/> : ${item.commentCount}<br />
					</span>
				</span>
				<img src="images/lp_featline.jpg" width="634" height="1" alt="" class="lp_featline" />
				</div>
				</c:forEach>
			</div>
			
			<!-- my upload -->
			<div class="lp_featpad">
				<div class="lp_featnav">
					<a><b><spring:message code="myUpload"/></b></a>
				</div>
				<c:forEach items="${uploadList}" var="item">
				<a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="152" height="92" alt="" class="lp_featimg1" />
				<span class="cp_featpara">
	            <span style="float:left; width:250px; display:inline;">...</span>
				<span style="float:left; width:250px; display:inline;"><span class="cp_featname"><b>${item.title } </b><Br /><spring:message code="${item.label}"/></span>
				
				<span class="cp_featxt">${item.description }</span>
				</span>
				<span class="cp_spaceview"><spring:message code="upload_time"/> : ${fn:substring(item.uploadTime,0,10)}<br /><spring:message code="watch"/> : ${item.playCount }<br /><spring:message code="comment"/> : ${item.commentCount}<br />
				</span>
				</span>
				<img src="images/lp_featline.jpg" width="634" height="1" alt="" class="lp_featline" />
			    </a>
			    </c:forEach>
			</div>
		</div>
		
		<!-- Personal Information Panel -->
		<div id="rp_padd" class="rp_loginpad">
			<!-- base info -->
			<div id="personInfo">
	    		<span class="rp_registertitxt"><spring:message code="baseinfo"/>
	    			<!--  -->
	     			<a href="javascript:editInfo();" class="editlink"><span  onmouseover="this.style.color='#ff0000'" onmouseout="this.style.color='#666666'"  style="color:#666666;"><spring:message code="edit"/></span></a>
	     		</span>
		 		<form id="info" name="info" action="" method="post" >
				    <img src='avatar/${loginUser.avatar}' width="80" height="80" alt="" class="rp_spaceavatar" />
					<span class="rp_spaceinfo"></span>
					<span class="rp_spaceinfo"><spring:message code="nickname"/>&nbsp;${loginUser.userName}</span>
					<span class="rp_spaceinfo"><spring:message code="email"/>&nbsp;${loginUser.email}</span>
					<span class="rp_spaceinfo"><spring:message code="gender"/>&nbsp;<c:if test="${loginUser.gender==0}"><spring:message code="female"/></c:if><c:if test="${loginUser.gender==1}"></c:if><spring:message code="male"/></span>
					<span class="rp_spaceinfo"><spring:message code="join"/>&nbsp;${fn:substring(loginUser.joinTime,0,10)}</span>
		 		</form>
	   		</div>
			
			<!-- edit start -->
	   		<div id="edit" style="display:none">
	    		<span class="rp_registertitxt"><spring:message code="modify_person_info"/></span>
		 		<form id="newinfo" name="newinfo" action="javaScript:doModify();" enctype="multipart/form-data" method="post" >
				    <span class="rp_spacemodify"><spring:message code="avatar"/></span>
				    <img src='avatar/${loginUser.avatar}' width="90" height="90" alt="" class="rp_spacemodifyavatar" />
					
					<!-- upload new avatar -->
					<span class="rp_uploadfile"></span>
					<a class="">
						<input name="newAvatar" id="newAvatar" type="file" style="margin-left:24px;" onchange="javascript:checkImage();"  />
					</a>
					<span id="imageformatchecker" class="rp_avatarnote"></span>
					
					<span class="rp_spacemodify"><spring:message code="oldpwd"/></span>
					<input name="oldPwd" id="oldPwd" type="password" onblur="checkOriginPwd();" class="effect-16" />
					<span id="oldpwdchecker" class="rp_modifypwdchecker"></span>
						
					<span class="rp_spacemodify"><spring:message code="newpwd"/></span>
					<input name="newPwd" id="newPwd" type="password" onblur="checkNewPwd();" class="effect-16" />
					<span id="newpwdchecker" class="rp_modifypwdchecker"></span>
					
					<span class="rp_spacemodify"><spring:message code="confirm_pwd"/></span>
					<input name="confirmNewPwd" id="confirmNewPwd" type="password" class="effect-16" onblur="javascript:checkRepeatPwd();" />
					<span id="pwdchecker" class="rp_modifypwdchecker"></span>
					
					<span id="result" class="rp_spacemodify"></span>
					<a class="rp_modify"><input name="info_submit" type="submit" value="<spring:message code="confirm"/>" /></a>
					<a class="rp_modify">&nbsp;&nbsp;&nbsp;&nbsp;</a>
					<a class="rp_modify"><input name="info_cancel" type="button" value="<spring:message code="cancel"/>" onclick="javascript:cancelEdit()" /></a>	
		 		</form>
	   		</div>
	   		<!-- edit end -->
	   		
		</div>
	</div>	
	<!--content Panel ends here -->
					
	<!-- footer -->
		<%@ include file="footer.jsp" %>
    </div> 
  </div>
</body>
</html>
