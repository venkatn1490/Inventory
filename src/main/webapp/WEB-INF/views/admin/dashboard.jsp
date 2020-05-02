       <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!-- Page Summary Widget-->
        <div class="widget-page-summary">
          <div class="l-col-lg-4" style="width: 69em;">
            <h2 class="page-title">Welcome to <span> ${FirstName}<c:if test="${not empty MiddleName}"> ${MiddleName}</c:if> ${LastName}</span>.</h2>
            <h4 class="page-sub-title">Your <span id="rotating-text">Dashboard</span></h4><a href="#" class="page-summary-info-switcher"><i class="fa fa-bars"></i></a>
          </div>

        </div>
         <!-- slider -->
          <div class="l-spaced">
                      <div class="row"><a href="" class="col-md-12"><img style= "width:100%" src='<c:url value="/resources/img/slider-img.jpg"/>'></a></div>
          
          </div>
        <!-- slider -->
      <!-- Row 5 - Gender, Age-->
        

