<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" errorPage="errorpage.jsp" import = "com.avetti.simplemerce.Glob"%>
<%@ include file="common.jsp"%>

<script language="javascript">
 <!--  
 	// change  
	function checkinput() {
		var d=document.frm;
		if (d.j_username.value.length < 1) {
			alert('<spring:message code="jsp.loginadmin.require1"/>');
    	  	d.j_username.focus();
    	  	return false;
    	}
		if (d.j_password.value.length < 1) {
			alert('<spring:message code="jsp.loginadmin.require2"/>');
    	  	d.j_password.focus();
    	  	return false;
    	}
    	return true;
    }

 function changeLang(lang){

     if(lang!=null && lang!=""){      
 		  window.location.href="login.admin?ml="+lang;
       }

     } 

 function hideLang(){

	 var defaultLanguageForAdmin = document.getElementById("defaultLanguageForAdmin").value; 
	 var adminsupportlang = document.getElementById("adminsupportlang").value;
	 if(adminsupportlang == defaultLanguageForAdmin){
		 var language = document.getElementById("languageselect");
		 language.style.display="none";
		  return; 
		 }
	 
	 }
 
// -->
</script>

<c:if test="${passwordupdateDTO.redirectflg}">
<%	response.sendRedirect("storelist.admin"); %>
</c:if>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<META name="PRAGMA" content="nocache">
		<title><spring:message code="jsp.loginadmin.title"/></title>
		<style>
				.text {font-family: verdana,tahoma,sans-serif; font-size: 11px; margin-left: 15px;}
				td {font-family: verdana,tahoma,sans-serif; font-size: 11px;}
				</style>
	</head>
	<link rel=stylesheet href="/<%=Glob.appname()%>/assets/admin/css/fontstyles.css" type="text/css">
	<body onload="javascript:{document.frm.j_username.focus();};hideLang();">
      
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2" bgcolor="D6D7D9">
					<div align="right">
						<img src="assets/admin/images/top_05_s.gif">
					</div>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td align="right">
				<div id="languageselect" style="display:block;">
					<spring:message code="jsp.loginadmin.lang"/>
					<%
					 String defaultLanguageForAdmin = com.avetti.simplemerce.Glob.defaultLanguageForAdmin.toUpperCase();
					int adminsupportlang = com.avetti.simplemerce.Glob.getAdminSupportLanguage().size();
					String adminsupportlangName ="";
					if(adminsupportlang == 1){
						adminsupportlangName = com.avetti.simplemerce.Glob.getAdminSupportLanguage().get(0).toString().toUpperCase();
					}
					%>
					<input type="hidden" id="defaultLanguageForAdmin" value="<%=defaultLanguageForAdmin%>"/>
					<input type="hidden" id="adminsupportlang" value="<%=adminsupportlangName%>"/>
			<select name="j_language" STYLE="width: 150px" onChange="javascript:changeLang(this.value)">  
			    <option value=""><spring:message code="jsp.loginadmin.selectlang"/></option>			    
			    <option value="EN" <c:if test="${passwordupdateDTO.currentlang == 'EN'}">selected</c:if>><spring:message code="jsp.loginadmin.english"/></option>
				<c:forEach var="lang" items="${passwordupdateDTO.supportlang}">
				   <c:if test="${lang.ml_code!='EN'}">
					<option value="${lang.ml_code}" <c:if test="${lang.ml_code==passwordupdateDTO.currentlang}">selected</c:if>>${lang.language}</option>
					</c:if>
				</c:forEach>
			</select>
			</div>
				</td>
			</tr>
			<tr>
			
			
<td WIDTH=372>
<TABLE WIDTH=372 BORDER=0 CELLPADDING=0 CELLSPACING=0>
	<TR>
		<TD>
			<IMG SRC="assets/admin/images/left_11.gif" WIDTH=38 HEIGHT=165></TD>
		<TD>
			<IMG SRC="assets/admin/images/left_12.gif" WIDTH=206 HEIGHT=165></TD>
		<TD>
			<IMG SRC="assets/admin/images/left_13.gif" WIDTH=128 HEIGHT=165></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="assets/admin/images/left_16.gif" WIDTH=38 HEIGHT=97></TD>
		<TD background="assets/admin/images/left_17.gif" WIDTH=206 HEIGHT=97>
		<!-- 
<span class=text><spring:message code="jsp.loginadmin.help"/></span>
<br><span class=text>E-mail:&nbsp;<a href="mailto:ecommercesupport@avetti.com">ecommercesupport@avetti.com</a></span>
		-->
		</TD>
		<TD>
			<IMG SRC="assets/admin/images/left_18.gif" WIDTH=128 HEIGHT=97></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="assets/admin/images/left_21.gif" WIDTH=38 HEIGHT=57></TD>
		<TD>
			<IMG SRC="assets/admin/images/left_22.gif" WIDTH=206 HEIGHT=57></TD>
		<TD>
			<IMG SRC="assets/admin/images/left_23.gif" WIDTH=128 HEIGHT=57></TD>
	</TR>
</TABLE>
</td>

<td>	
	<form method="POST" action="<c:url value='simplemerce_admin_login.admin'/>"  name="frm" onsubmit="return checkinput();">
	<div align="center">
	<TABLE CELLPADDING=0 class="adminheader" cellspacing="0">
	   <TR>
			<TD>
				<spring:message code="jsp.loginadmin.welcome"/><BR>
		<spring:bind path="passwordupdateDTO.*">
	            <font color="#ff0000" face="Helvetica,Geneva,Arial" size="2" >
	             <ul>
	             <c:forEach var="error" items="${status.errorMessages}"> 
				  <li><c:out value='${error}' escapeXml='false'/>&nbsp; </li>
		         </c:forEach>		        
	         </ul>
		        </font></spring:bind>						
			</TD>
		</TR>
		</TABLE>
	<TABLE CELLPADDING=2 class="adminheader" width="465px">	
	<TR class="edittableborder">
			<TD ALIGN=LEFT><spring:message code="jsp.loginadmin.username"/></TD>		
			<TD ALIGN=CENTER><input type="text" name="j_username" value="<c:out value='${passwordupdateDTO.adminlogin}'/>" WIDTH="300" STYLE="width: 150px"></TD>
		</TR>
		<BR>
		<TR>
			<TD ALIGN=LEFT><spring:message code="jsp.loginadmin.password"/></TD>	
			<TD ALIGN=CENTER>
			<input type="password" name="j_password" value="" WIDTH="300"  autocomplete="off" onkeypress="if(event.keyCode==13){checkinput();document.frm.submit();}" STYLE="width: 150px">
			</TD>
		</TR>
		
<!--  
		<TR class="edittableborder">
			<TD ALIGN=LEFT><spring:message code="jsp.loginadmin.store"/></TD>		
			<TD ALIGN=CENTER><input type="text" name="j_usertype" value="" WIDTH="300" STYLE="width: 150px"></TD>
	</TR>
-->
	<BR>
		
		<TR>			
		<TD>					
		</TD>
		</TR>
		<TR>
		<TD>		
		<input type="hidden" name="_acegi_security_remember_me" value="on">
		</TD>
		</TR>
		<TR>
		    <TD ALIGN=LEFT></TD>	
			<TD ><input type="button" value="<spring:message code="button.CONTINUE"/>" onclick="javascript:checkinput();document.frm.submit();" style="width:120px;"></TD>
		</TR>
	</TABLE>
	</form>
</td>
</table>
	<script language="javascript">
		var userAgent = navigator.userAgent.toLowerCase();
		if (userAgent.indexOf('ie') != -1) {
				alert('We do not recommend use of Internet Explorer on this administration application. Please use a recent version of Chrome or Firefox.');
		}
	</script>
  </body>

</html>
