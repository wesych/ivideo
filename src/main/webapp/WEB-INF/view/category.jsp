<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="include.jsp"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Categories Show</title>
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
				<div class="lp_featpad">
					<div class="lp_featnav">
						<a><b><spring:message code="${label}"/></b></a>
					</div>
					
					<c:forEach items="${categoryList}" var="item"> 
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
		</div>
		<!--content Panel ends here -->
					
		<!-- footer -->
		<%@ include file="footer.jsp" %>
      </div> 
   </div>
</body>
</html>
			
  
   
  	  
                   
					