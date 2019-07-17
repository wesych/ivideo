<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="include.jsp"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Go Video ： Watching</title>

<script type="text/javascript" language="javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" language="javascript" src="layer/layer.js"></script>

<script type="text/javascript">
$(document).ready(function() {  
    
});
</script> 

<!-- Add Collection -->
<script type="text/javascript">
	function addCollection() {
		$.ajax({
			data : "video_id=${chosenVideo.videoId}&username=${loginUser.userName}",
			type : "GET",
			dataType : 'json',
			url : "collect",
			success : function(data) {
				if (data.col == "success") {
					document.getElementById('col_link').href='myspace/${loginUser.userId}';
					document.getElementById('col_image').src='images/heart-red16.png';
					document.getElementById('col_text').innerText='<spring:message code="collected"/>';
				}
			}
		});
	}
</script>

<!-- Add Comment -->
<script type="text/javascript">
	function addComment() {
		var count = $("#commentcount").text();
		var text = $("#comment_text").val();
		if(text == null || text.length <= 10){
			layer.msg('<spring:message code="js_content_length"/>',{
				offset : ''
			});
			return;
		}
	    var com = {
	    		video_id:${chosenVideo.videoId},
	    		user_id:${loginUser.userId},
	    		content:text
	        };
		$.ajax({
			data : com,
			type : "GET",
			dataType : 'json',
			url : "comment",
			success : function(data) {
				if (data.comment == "success") {
					// clear textarea
					$("#comment_text").val("");
					// show this comment
					$("#newAvatar").show();
					$("#newSpan").show();
					document.getElementById("time").innerHTML=data.time;
					document.getElementById("latestAddedComment").innerHTML=text;
					$("#commentcount").text(Number(count) + 1);
				}
			}
		});
	}
</script> 

</head>

<body>
  <div id="main_block">
  	 <div id="innerblock">

		<!--Top Panel starts here -->
		<jsp:include page="header.jsp"/>
        <!--Top Panel ends here -->
					   
		<!--content Panel starts here -->
		<div id="contentpanel">
			<div id="lp_padd">
				<!-- Html5 mp4 web player -->
				<span class="lp_newvidit1">${chosenVideo.title}</span>
				<video id="html5player" width="593" height="389" controls oncanplaythrough="myFunction()">
					   <source src="${videoSource}"  type="video/mp4" />
				</video>
				<script>
					document.getElementById("html5player").volume=0.3;
				</script>

				<div class="lp_newvidpad" style="margin-top:10px;">
					<!-- Video info -->
					<span class="lp_plyrxt"><b id="length"></b></span>
					<script>
					var vv = document.getElementById("html5player");
					function myFunction() {
						var time = parseInt(vv.duration);
						var hour = Math.floor(time / 3600);
						var minute = Math.floor((time / 60 % 60));
						var second = Math.floor((time % 60));
						var result;
						var min_prefix = "";
						var sec_prefix = "";
						if(minute < 10){
							min_prefix = "0";
						}
						if(second < 10) {
							sec_prefix = "0";
						}
						if (hour < 1) {
							result = minute + ":" + sec_prefix + second;
						} else {
							result = hour + ":" + min_prefix + minute + ":" + sec_prefix + second;
						}
						document.getElementById("length").innerText="<spring:message code="play_time"/>"+result;
					}
					
					vv.onplay = function() {
						var count = $("#playcount").text();
						var video = {
					    		video_id:${chosenVideo.videoId}
					        };
						$.ajax({
							data : video,
							type : "GET",
							dataType : 'json',
							url : "addplaycount",
							success : function(data) {
								if (data.plc == "success") {
									$("#playcount").text(Number(count) + 1);
								}
							}
						});
					};
					</script>
					<span class="lp_plyrxt"><b><spring:message code="play_count"/></b><span id="playcount">${chosenVideo.playCount}</span></span>
					<span class="lp_plyrxt"><b><spring:message code="play_comment_count"/></b><span id="commentcount">${chosenVideo.commentCount}</span></span>
					
					<c:if test="${isLogin}">
						<div id="add_collection">
						<c:if test="${isCollected==true}">
						<a id="col_link" href='myspace/${loginUser.userId}'>
							<span id="doCollect" class="lp_plyrxt" >
								<img id="col_image" src="images/heart-red16.png" align="top" width="16" height="16" alt="" />&nbsp;<b id="col_text"><spring:message code="collected"/></b>
							</span>
						</a>
						</c:if>
						
						<c:if test="${isCollected==false}">
						<a id="col_link" href="javascript:addCollection();">
							<span id="doCollect" class="lp_plyrxt" >
								<img id="col_image" src="images/heart-grey16.png" align="top" width="16" height="16" alt="" />&nbsp;<b id="col_text"><spring:message code="collect"/></b>
							</span>
						</a>
						</c:if>
						</div>
					</c:if>
					
				</div>
				
				<!-- Comment Adding Area -->
				<c:if test="${isLogin}">
				<div class="lp_newvidpad" style="margin-top:10px;">
					<img src='avatar/${loginUser.avatar}' width="80" height="80" alt="" class="lp_loginavatar" />
					<span class="cp_featparas">
						<span class="cp_ftparinrcomment">
						    <span class="cp_commentuser"><br>${loginUser.userName}</span>
						    <span class="cp_commenttime"><br></span>
						    <textarea id="comment_text" rows="10" cols="30" name="comment" style="max-width:450px;" class="rp_commentarea"></textarea>
						    <span class="rp_commentButton">
							<a class="rp_login"><input id="sub_com" name="submit" type="submit" value="<spring:message code="doComment"/>" onclick="javacript:addComment();" /></a>
							</span>
						</span>
					</span>
				</div>
				</c:if>
				
				<!-- Show all comments -->
				<div class="lp_newvidpad" style="margin-top:10px;">	
					<span class="lp_newvidit"><spring:message code="AllComments"/></span>
					<img src="images/lp_newline.jpg" id="" width="592" height="2" alt="" class="lp_newline" />
				   
				    <!--Show comment area when 'add comment' is clicked  -->
				    <!-- TODO -->
				    <img src='avatar/${loginUser.avatar}' id="newAvatar" width="80" height="80" alt="" style=display:none class="lp_commentavatar" />		
					<span class="cp_featparas" id="newSpan" style=display:none>
						<span class="cp_ftparinr1">
						    <span class="cp_commentuser"><br></span>
							<span class="cp_commentuser"><b>${loginUser.userName }</b>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span id="time" class="cp_commenttime">${fn:substring(comment.commentTime,0,10)}</span> 
							</span>
							<span class="cp_commentuser"><br></span>
							<span class="cp_featxt" id="latestAddedComment" style="width:500px;"></span><br />
						</span>
					</span><br />
					<img src="images/lp_featline.jpg" width="550" height="1" alt="" class="lp_commentline" />
				  
				    <!-- show all comments for this video with c:forEach -->
				    <c:forEach items="${commentList}" var="comment">
					<img src='avatar/${comment.avatar}'  width="80" height="80" alt="" class="lp_commentavatar" />
					<span class="cp_featparas">
						<span class="cp_ftparinr1">
						    <span class="cp_commentuser"><br></span>
							<span class="cp_commentuser"><b>${comment.userName }</b>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span class="cp_commenttime">${fn:substring(comment.commentTime,0,10)}</span> 
							</span>
							<span class="cp_commentuser"><br></span>
							<span class="cp_featxt" style="width:500px;">${comment.content }</span><br />
						</span>
					</span><br />
					<img src="images/lp_featline.jpg" width="550" height="1" alt="" class="lp_commentline" />
					</c:forEach>
				</div>
			</div>
			
			<div id="rp_padd">
			    <img src="images/rp_upbgtop.jpg" width="282" height="10" alt="" class="rp_upbgtop" />
			    
			    <!-- information of playing video -->
				<div class="rp_uppad">
					<img src='capture/${chosenVideo.videoId}.jpg' width="87" height="54" alt="" class="lp_inrfoto" />
					<span class="rp_inrimgxt" style="margin-top:47px; width:120px;"><span style="font:bold 11px/20px Arial, Helvetica, sans-serif;">${chosenVideo.title}</span><br /><spring:message code="publish"/>${chosenVideo.userName}<br /></span>
					<span class="rp_inrimgxt" style="margin-left:20px;"><span style="font:11px/20px Arial, Helvetica, sans-serif;"></span><br />${chosenVideo.description}</span>
				</div>
				
				<!-- Most popular videos -->
				<img src="images/rp_top.jpg" width="282" height="10" alt="" class="rp_upbgtop" />
				<div class="rp_loginpad" style="padding-bottom:0px; border-bottom:none;">
				<span class="rp_titxt"><spring:message code="hotlinks"/></span>
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
