<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="include.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Upload Video</title>

<style type="text/css">  
    #parent{width:350px; height:15px; border:1px solid #6F7475;}  
    #son {width:0; height:100%; background-color:#6F7475; text-align:center; line-height:10px; font-size:16px; font-weight:bold;}  
</style>

<script type="text/javascript" language="javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" language="javascript" src="layer/layer.js"></script>

<!-- Checker -->
<script type="text/javascript">

$( function() {
    $( "#progressbar" ).progressbar({
      value: 37
    });
  } );

function getFileName(file) {
	var pos=file.lastIndexOf("\\");
	return file.substring(pos+1);
}

var isVideo;
function checkVideo(path) {
	$("#filepath").val(getFileName(path));
	if(path.endsWith(".mp4")){
		$('#videoresult').text("");
		isVideo = true;
	} else {
		$('#videoresult').text("<spring:message code="js_upload_video_format"/>");
		$("#videoresult").css("color", "red");
		isVideo = false;
	}
}

var hasTitle;
function checkTitle() {
	var title=$("#title").val();
	if(title==null || title.length==0){
		$("#titleresult").text("<spring:message code="js_upload_video_title_not_null"/>");
		$("#titleresult").css("color", "red");
		hasTitle = false;
	}else{
		$("#titleresult").text("");
		hasTitle = true;
	}
}

var hasDesc;
function checkDescription() {
	var desc=$("#description").val();
	if(desc==null || desc.length==0){
		$("#descresult").text("<spring:message code="js_upload_video_desp_not_null"/>");
		$("#descresult").css("color", "red");
		hasDesc = false;
	}else{
		$("#descresult").text("");
		hasDesc = true;
	}
}

function validate() {
	if(isVideo==null) checkVideo($("#filepath").val());
	if(hasTitle==null) checkTitle();
	if(hasDesc==null) checkDescription();
	if(isVideo==true && hasTitle==true && hasDesc==true){
		return true;
	} else {
		return false;
	}
}

function doUpload() {
	if(validate() == false) {
		return;
	} else {
		$.ajax({
			data : new FormData($('#uploadvideo')[0]),
			type : "POST",
			dataType : 'json',
			url : "doUpload",
			contentType: false,  
	        processData: false,  
	        
	        xhr: function(){
	          document.getElementById("parent").style.display="";
	      　　　　　　var xhr = $.ajaxSettings.xhr();
	      　　　　　　if(onprogress && xhr.upload) {
	      　　　　　　　　xhr.upload.addEventListener("progress" , onprogress, false);
	      　　　　　　　　return xhr;
	      　　　　　　}
	        },
	      　　　　 
			success : function(data) {
				if (data.upl == "success") {
					layer.msg('<spring:message code="js_upload_video_success"/>', {offset : ''}, function(){
						location.reload(); 
					});
				} 
			}
		});
	}
	}

function onprogress(evt) {
	var loaded = evt.loaded; 
	var tot = evt.total; 
	var per = Math.floor(100 * loaded / tot); 
	$("#son").html( per +"%" );
	$("#son").css("width" , per +"%");
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
			<div id="lp_padd">
			     <span class="rp_registertitxt"><spring:message code="upload_head"/></span>
				 <form id="uploadvideo" name="uploadvideo" action="javascript:doUpload();" enctype="multipart/form-data" method="post" >
					<span class="rp_membrusr"><spring:message code="upload_file"/></span>
					<input name="filepath" id="filepath" type="text" class="rp_register"  />
					<input name="video" id="video" type="file" class="rp_uploadButton" onchange="checkVideo(this.value);" />
					<span id="videoresult" class="rp_videonote"></span>
					<span class="rp_membrusr"><spring:message code="upload_category"/></span>
					  <select name="category" id="category" class="rp_selection" >
					     <option value="music"><spring:message code="music"/></option>
                         <option value="amuse"><spring:message code="amuse"/></option>
                         <option value="funny"><spring:message code="funny"/></option>
					  </select>
					<span class="rp_membrusr"><spring:message code="upload_title"/></span>
					<input name="title" id="title" type="text" class="rp_register" onblur="checkTitle();" />
					<span id="titleresult" style="width:150px;padding-left:70px;" class="rp_membrpwd"></span>
					
					<span class="rp_membrpwd"><spring:message code="upload_desp"/></span>
					<textarea rows="10" cols="30" name="description" id="description" onblur="checkDescription();" class="rp_videoDiscription"></textarea>
					<span id="descresult" style="width:150px;padding-left:70px;" class="rp_membrpwd"></span>
					
					<span class="rp_membrpwd"></span>
					<span class="rp_membrpwd">
						<div id="progressbar" style="display:none;" class="progressbar" data-perc="100">
							<div class="bar"><span></span></div>
						</div>
						<div id="parent" style="display:none;">
						    <div id="son"></div>
						</div>
					</span>
					<span class="rp_membrpwd"></span>
					<a class="rp_login"><input name="submit" type="submit" value="<spring:message code="upload_confirm"/>" /></a>
				 </form>
			</div>
		
			<div id="rp_padd">
				<img src="images/rp_top.jpg" width="282" height="10" alt="" class="rp_upbgtop" />
				<div class="rp_loginpad" style="padding-bottom:0px; border-bottom:none;">
					<span class="rp_titxt"><spring:message code="videos_from_user"/></span>
				</div>
				
				<c:forEach items="${top4List}" var="item">
					<a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="87" height="54" alt="" class="rp_inrimg1" /></a>
					<span class="rp_inrimgxt" style="margin-top:24px; width:120px;">
					<span style="font:bold 11px/20px Arial, Helvetica, sans-serif;">${item.title}</span><br /><spring:message code="publish"/>${item.userName}</span>
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
