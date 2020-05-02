<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>MedRep - Login</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <!-- ===== FAVICON =====-->
    <link rel="shortcut icon" href="favicon.ico">
    <!-- ===== CSS =====-->
    <!-- General-->
    <link rel="stylesheet" href='<c:url value="/resources/css/basic.css"/>'>
    <link rel="stylesheet" href='<c:url value="/resources/css/general.css"/>'>
    <link rel="stylesheet" href='<c:url value="/resources/css/theme.css"/>' class="style-theme">
    
    <!--[if lt IE 9]>
    <script src='<c:url value="/resources/js/basic/respond.min.js"/>'></script>
    <script src='<c:url value="/resources/js/basic/html5shiv.min.js"/>'></script>
    <![endif]-->
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
          <h1 class="login-logo"><img src='<c:url value="/resources/img/login-logo.png"/>' alt="MedRep"></h1>
          <!--Login Form-->
          <form id="loginForm" role="form" action="../../j_spring_security_check" class="login-form" method="post">
          	<label id="login-error" class="error">${error}</label>
            <div class="form-group">
              <input id="j_username" type="email" name="j_username" placeholder="Email" class="form-control">
            </div>
            <div class="form-group">
              <input id="j_password" type="password" name="j_password" placeholder="Password" class="form-control">
            </div>
            <button type="submit" class="btn btn-dark btn-block btn-login" value="Login">Sign In</button>
            <div class="login-options"><a href="javascript:void(0)" id="fgpass" class="fl">FORGOT PASSWORD ?</a><a href="#" class="fr">SIGN UP</a></div>
          </form>
        </div>
      </div>
    </section>
    <!-- ===== JS =====-->
    <!-- jQuery-->
    <script src='<c:url value="/resources/js/basic/jquery.min.js"/>'></script>
    <script src='<c:url value="/resources/js/basic/jquery-migrate.min.js"/>'></script>
    <!-- General-->
    <script src='<c:url value="/resources/js/basic/modernizr.min.js"/>'></script>
    <script src='<c:url value="/resources/js/basic/bootstrap.min.js"/>'></script>
    <script src='<c:url value="/resources/js/shared/jquery.asonWidget.js"/>'></script>
    <script src='<c:url value="/resources/js/plugins/plugins.js"/>'></script>
    <script src='<c:url value="/resources/js/general.js"/>'></script>
    <!-- Semi general-->
    <script type="text/javascript">
    $(document).ready(function(){
    	
    	$("#fgpass").click(function(){
 			var uname = $("#j_username").val(); 			
 			if(uname != ''){
 				$.ajax({
 		  		   url: '${pageContext.request.contextPath}/web/auth/sendFPLinkToEmail.do',
 		  		   type: 'GET',
 		  		   data: 'email='+uname,
 		  		   dataType: "text",
 		  		   success: function(data) { 		  			  
 		  			if(data=='true'){
 		  				alert("Mail sent successfully");
 		  			}else{
 		  				$("#login-error").html("You have not entered an valid User Name");
 		  			}
 		  		   } 		  		  
 				});
 				
 				
 			}else{
 				$("#login-error").html("You have entered an invalid User Name");
 			}
 			
 			 

    	});
    	
    });
      var paceSemiGeneral = { restartOnPushState: false };
      if (typeof paceSpecific != 'undefined'){
      	var paceOptions = $.extend( {}, paceSemiGeneral, paceSpecific );
      	paceOptions = paceOptions;
      }else{
      	paceOptions = paceSemiGeneral;
      }
      
      function sendFPLinkToMail(emailId){
    	  
      }
    </script>
    <script src='<c:url value="/resources/js/plugins/pageprogressbar/pace.min.js"/>'></script>
    <!-- Specific-->
    <script src='<c:url value="/resources/js/plugins/forms/validation/jquery.validate.min.js"/>'></script>
    <script src='<c:url value="/resources/js/plugins/forms/validation/jquery.validate.additional.min.js"/>'></script>
    <script src='<c:url value="/resources/js/calls/page.login.js"/>'></script>
    
    <!-- Forgot Passowrd Hidden form  -->
					<form action="sendFPLink.do" id="fpform" method="post">
						<input type="hidden" id="fpemail" name="fpemail" value="" />
					</form>
					<!-- Forgot Password Hidden form END  -->
  </body>
</html>