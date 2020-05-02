<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>MedRep - Update Password</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<!-- ===== FAVICON =====-->
<link rel="shortcut icon" href="favicon.ico">
<!-- ===== CSS =====-->
<!-- General-->
<link rel="stylesheet" href='<c:url value="/resources/css/basic.css"/>'>
<link rel="stylesheet"
	href='<c:url value="/resources/css/general.css"/>'>
<link rel="stylesheet" href='<c:url value="/resources/css/theme.css"/>'
	class="style-theme">

<!--[if lt IE 9]>
    <script src='<c:url value="/resources/js/basic/respond.min.js"/>'></script>
    <script src='<c:url value="/resources/js/basic/html5shiv.min.js"/>'></script>
    <![endif]-->
    <style>
 
    </style>
</head>
<body class="login-bg">
	<!--[if lt IE 9]>
    <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->                         
	<!--SECTION-->
	<section class="l-main-container">
		<!--Main Content-->
		<div class="login-wrapper">
			<div class="login-container">
				<!--Logo-->
				<h1 class="login-logo">
					<img src='<c:url value="/resources/img/login-logo.png"/>'
						alt="MedRep">
				</h1>
				<!--Login Form-->
				<c:choose>
					<c:when test="${statusMsg == 'success'}">
								
						<form id="loginForm" role="form" action="updatePassword.do" class="login-form" method="post" onsubmit="return validateForm()">
          					<label id="login-error1" class="lbclass" style="display: block;"></label>
          					<input type="hidden" name="email" value="${email}"> 
            				<div class="form-group">
              					<input id="newPassword" type="password" name="newPassword" placeholder="New Password" class="form-control">
            				</div>
            <div class="form-group">
              <input id="confirmPassword" type="password" name="confirmPassword" placeholder="Confirm New Password" class="form-control">
            </div>
            <button type="submit" class="btn btn-dark btn-block btn-login" value="Update" style="width: 378px;">Submit</button>           
          </form>
					</c:when>
					<c:otherwise>
						<h2>OTP Expired. Please generate another OTP</h2>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</section>
	<!-- ===== JS =====-->
	<!-- jQuery-->
	<script src='<c:url value="/resources/js/basic/jquery.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/basic/jquery-migrate.min.js"/>'></script>
	<!-- General-->
	<script src='<c:url value="/resources/js/basic/modernizr.min.js"/>'></script>
	<script src='<c:url value="/resources/js/basic/bootstrap.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/shared/jquery.asonWidget.js"/>'></script>
	<script src='<c:url value="/resources/js/plugins/plugins.js"/>'></script>
	<script src='<c:url value="/resources/js/general.js"/>'></script>
	<!-- Semi general-->
	<script type="text/javascript">
    $(document).ready(function(){
    	
    	
    	
    });
      var paceSemiGeneral = { restartOnPushState: false };
      if (typeof paceSpecific != 'undefined'){
      	var paceOptions = $.extend( {}, paceSemiGeneral, paceSpecific );
      	paceOptions = paceOptions;
      }else{
      	paceOptions = paceSemiGeneral;
      }
      
      function validateForm(){
    	  var np=document.getElementById("newPassword").value;
 		  var cnp=document.getElementById("confirmPassword").value;
 		 if(np == ''){
 			document.getElementById("login-error1").style.display="block";
 			document.getElementById("login-error1").innerHTML  = '<font color="red">Please Enter New Password</font>';
 			document.getElementById("newPassword").focus();
 			 return false;
 		 }
 		 if(cnp == ''){
 			document.getElementById("login-error1").style.display="block";
 			document.getElementById("login-error1").innerHTML = '<font color="red">Please Enter Confirm New Password';
 			document.getElementById("confirmPassword").focus();
 			 return false;
 		 }
 	 	 if(np == cnp){
 	 		document.getElementById("login-error1").innerHTML = 'Password reset successfully';
 	 		 return true;
 	 	 }else{
 	 		document.getElementById("login-error1").style.display="block";
 	 		document.getElementById("login-error1").innerHTML = '<font color="red">Passwords are not match.Please try again!'; 			 
 			 return false;
 		 } 
    	  
      }
    </script>
	<script
		src='<c:url value="/resources/js/plugins/pageprogressbar/pace.min.js"/>'></script>
	<!-- Specific-->
	<script
		src='<c:url value="/resources/js/plugins/forms/validation/jquery.validate.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/forms/validation/jquery.validate.additional.min.js"/>'></script>
	<script src='<c:url value="/resources/js/calls/page.login.js"/>'></script>
	
	<!-- Forgot Password Hidden form END  -->
</body>
</html>