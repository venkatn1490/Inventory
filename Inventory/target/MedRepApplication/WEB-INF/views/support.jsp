<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src='<c:url value="/resources/js/support.js"/>'></script>
<c:if test="${not empty  feedbackMessage}">
   <div class="notificationmsg">${feedbackMessage}</div> 
</c:if>
       <div class="container">
		<div class="row nav-wrapper">
          <!--Logo-->
          <h1 class="login-logo"><img src='<c:url value="/resources/img/logo-new.png"/>' alt="MedRep"></h1>
          <!--Feedback Form-->
          <form:form id="loginForm" role="form" action="feedback.do" class="support-form"  method="post" commandName="feedbackObject">
            <div class="form-group">
                <i class="fa fa-user"></i>
              <form:input type="text" class="form-control" name="name" placeholder="Name" path="name" id="name"/>
            </div>
            <div class="form-group">
              <i class="fa fa-envelope"></i>
              <form:input type="email" class="form-control" name="EMAIL" placeholder="Email" path="email" id="email"/>
              </div>

              <div class="form-group">
              <i class="fa fa-phone" style="font-size: 24px;"></i>
              <form:input type="text" class="form-control" name="phone" placeholder="Mobile No." path="mobileNumber" id="mobileNumber"/>
              </div>                  
                                    
              <form:textarea class="form-control" placeholder="Message" rows="4" path="message" id="message"></form:textarea>              
              <button type="submit" id="submit" class="btn btn-lg btn-primary">Send Message</button>
          </form:form>
        </div>
      </div>

