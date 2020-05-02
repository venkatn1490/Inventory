<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

      <!--Main Content-->
      <div class="login-wrapper">
        <div class="login-container">
          <!--Logo-->
          <h1 class="login-logo"><img src='<c:url value="/resources/img/logo-new.png"/>' alt="MedRep"></h1>
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
            <div class="login-options"><a href="javascript:void(0)" id="fgpass" class="fl">FORGOT PASSWORD ?</a></div>
          </form>
        </div>
      </div>
    <!-- ===== JS =====-->
    <!-- jQuery-->

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
