<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="include.jsp"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Go Video ： Enjoy watching and sharing</title>

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
				
				<!-- The most popular video -->
				<div class="lp_hotvideohomepad">
					<span class="lp_newvidit"><spring:message code="hotlinks"/></span>
					<img src="images/lp_newline.jpg" width="661" height="2" alt="" class="lp_newline" />
					<a><img src="images/lp_arro.jpg" width="13" height="20" alt="" class="lp_arro" />	</a>
					
					<c:forEach items="${top4List}" var="item">  
				    <span class="lp_vidpara">
						<a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="134" height="83" alt="" class="lp_newvid1" /></a>
						<img src="images/lp_newvidarro.jpg" width="4" height="6" alt="" class="lp_newvidarro" />
						<span class="lp_newdixt" style="color:#BEBEBE;">${item.title}</span>
						</span>
					</c:forEach> 
					
					<a><img src="images/lp_arro1.jpg" width="13" height="20" alt="" class="lp_arro" />	</a>
				</div>
				
				<div class="lp_featpad">
					<div class="lp_featnav">
						<a><b><spring:message code="top_music"/></b></a>
					</div>
					<c:forEach items="${random2MusicList}" var="item">  
					    <a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="152" height="92" alt="" class="lp_featimg1"/></a>		
						<span class="cp_featpara">
							<span style="float:left; width:250px; display:inline;"><span class="cp_featname"><Br /><b>${item.title}</b><Br /><spring:message code="publish"/>${item.userName}</span>
							<span class="cp_featxt">${item.description }</span><br />
							</span>
							<span class="cp_featview"><br/><spring:message code="upload_time"/> : ${fn:substring(item.uploadTime,0,10)}<br /><spring:message code="watch"/> : ${item.playCount }<br /><spring:message code="comment"/> : ${item.commentCount }<br />
							<a></a></span>
						</span>
						<img src="images/lp_featline.jpg" width="634" height="1" alt="" class="lp_featline" />
					</c:forEach> 
				</div>
				
				<div class="lp_featpad">
					<div class="lp_featnav">
						<a><b><spring:message code="top_amuse"/></b></a>
					</div>
					<c:forEach items="${random2AmuseList}" var="item">  
					    <a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="152" height="92" alt="" class="lp_featimg1"/></a>		
						<span class="cp_featpara">
							<span style="float:left; width:250px; display:inline;"><span class="cp_featname"><Br /><b>${item.title}</b><Br /><spring:message code="publish"/>${item.userName}</span>
							<span class="cp_featxt">${item.description }</span><br />
							</span>
							<span class="cp_featview"><br/><spring:message code="upload_time"/> : ${fn:substring(item.uploadTime,0,10)}<br /><spring:message code="watch"/> : ${item.playCount }<br /><spring:message code="comment"/> : ${item.commentCount }<br />
							<a></a></span>
						</span>
						<img src="images/lp_featline.jpg" width="634" height="1" alt="" class="lp_featline" />
					</c:forEach> 
				</div>
				
				<div class="lp_featpad">
					<div class="lp_featnav">
						<a><b><spring:message code="top_funny"/></b></a>
					</div>
					<c:forEach items="${random2FunnyList}" var="item">  
					    <a href='play/${item.videoId }'><img src='capture/${item.videoId}.jpg' width="152" height="92" alt="" class="lp_featimg1"/></a>		
						<span class="cp_featpara">
							<span style="float:left; width:250px; display:inline;"><span class="cp_featname"><Br /><b>${item.title}</b><Br /><spring:message code="publish"/>${item.userName}</span>
							<span class="cp_featxt">${item.description }</span><br />
							</span>
							<span class="cp_featview"><br/><spring:message code="upload_time"/> : ${fn:substring(item.uploadTime,0,10)}<br /><spring:message code="watch"/> : ${item.playCount }<br /><spring:message code="comment"/> : ${item.commentCount }<br />
							<a></a></span>
						</span>
						<img src="images/lp_featline.jpg" width="634" height="1" alt="" class="lp_featline" />
					</c:forEach> 
				</div>
				
				
			</div>
			
			<div id="rp_padd">
				
				<img src="images/rp_top.jpg" width="282" height="10" alt="" class="rp_upbgtop"  />
				<div class="rp_loginpad" style="padding-bottom:0px; border-bottom:none;">
					<span class="rp_titxt"><spring:message code="category"/></span>
				</div>
				
				<span style="float:left;"><img src="images/rp_catarro.jpg" width="5" height="5" alt="" class="rp_catarro" />
				<a href="category/music" class="rp_catxt"><spring:message code="music"/></a>
				<img src="images/rp_catline.jpg" width="262" height="1" alt="" class="rp_catline" /></span>
				
				<span style="float:left;"><img src="images/rp_catarro.jpg" width="5" height="5" alt="" class="rp_catarro" />
				<a href="category/amuse" class="rp_catxt"><spring:message code="amuse"/></a>
				<img src="images/rp_catline.jpg" width="262" height="1" alt="" class="rp_catline" /></span>
				
				<span style="float:left;"><img src="images/rp_catarro.jpg" width="5" height="5" alt="" class="rp_catarro" />
				<a href="category/funny" class="rp_catxt"><spring:message code="funny"/></a>
				<img src="images/rp_catline.jpg" width="262" height="1" alt="" class="rp_catline" /></span>
				
				<img src="images/rp_upbgtop.jpg" width="282" height="10" alt="" class="rp_upbgtop" />
				<div class="rp_uppad">
					<span class="rp_titxt"><spring:message code="recommend"/></span>
					<a href='play/${recommendedVideo.videoId }'><img src="${recommendedPreview}" width="87" height="54" alt="" class="rp_weekimg" /></a>
					<img src="images/rp_catarro.jpg" width="5" height="5" alt="" class="rp_catarro1" />
					<a href="#" class="rp_vidxt">${recommendedVideo.title }<br /><font style="color:#666666; text-decoration:none;">发布 : ${recommendedVideo.userName }</font></a>
				</div>
				
				<!-- 
				<img src="images/rp_upbgtop.jpg" width="282" height="10" alt="" class="rp_upbgtop" />
				<div class="rp_uppad"><span class="rp_titxt" style="color:#A0B92E;"></span></div>
				 -->
			</div>
		</div>	
		<!--content Panel ends here -->
		
		<!-- footer -->
		<%@ include file="footer.jsp" %>		
	
    </div> 
  </div>
</body>			
</html>
   
  	  
                   
					