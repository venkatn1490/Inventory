       <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!-- Page Summary Widget-->
        <div class="widget-page-summary">
          <div class="l-col-lg-4" style="width: 69em;">
            <h2 class="page-title">Welcome to <span> ${FirstName}<c:if test="${not empty MiddleName}"> ${MiddleName}</c:if> ${LastName}</span>.</h2>
            <h4 class="page-sub-title">Your <span id="rotating-text">Dashboard</span></h4><a href="#" class="page-summary-info-switcher"><i class="fa fa-bars"></i></a>
          </div>
          <div class="l-col-lg-8 page-summary-info">
            <div class="l-row">
              <!-- Page Summary Clock-->
              <div class="summary-time-status clock-wrapper l-col-md-8 l-col-sm-6">
                <div id="clock"></div>
              </div>
              <!-- Page Summary Weather-->
              <div class="summary-time-status weather-wrapper l-col-md-4 l-col-sm-6">
                <div id="weather">
                  <div class="l-span-sm-6 l-span-xs-12">
                    <div class="weather-location">India</div>
                    <div class="weather-description">Hyderabad</div>
                  </div>
                  <div class="l-span-sm-3 l-span-xs-9">
                    <div class="weather-temp">65°F</div>
                  </div>
                  <div class="l-span-sm-3 l-span-xs-3">
                    <div class="weather-icon"><i class="ac ac-0"></i></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
         <!-- slider -->
          <div class="l-spaced">
            <div class="row"><a href="" class="col-md-12"><img src='<c:url value="/resources/img/slider1.jpg"/>'></a></div>
          </div>
        <!-- slider -->
      <!-- Row 5 - Gender, Age-->
        <div class="l-spaced">
          <div class="l-row">
          <div class="l-col-lg-8 l-col-md-12">
              <!-- Widget Statistic - Activity-->
              <div class="widget-statistic statistic-last is-statistic-right is-statistic-left">
                <div class="statistic-header">
                  <div class="l-span-sm-6">
                    <div class="statistic-title">Registered Pharma Companies</div>
                  </div>
                  <div class="l-span-sm-6">
                    <ul class="statistic-options">
                      <li><a id="statisticFullScreen_2" href="#" title="Fullscreen" data-ason-type="fullscreen" data-ason-target=".widget-statistic" data-ason-content="true" class="ason-widget tt-top"></a></li>
                      <li><a href="#" title="Refresh" data-ason-type="refresh" data-ason-target=".widget-statistic" data-ason-duration="1000" class="ason-widget tt-top"><i class="fa fa-rotate-right"></i></a></li>
                      <li><a href="#" title="Toggle" data-ason-type="toggle" data-ason-find=".widget-statistic" data-ason-target=".statistic-body" data-ason-content="true" data-ason-duration="200" class="ason-widget tt-top"></a></li>
                      <li class="last"><a href="#" title="Delete" data-ason-type="delete" data-ason-target=".widget-statistic" data-ason-content="true" data-ason-animation="fadeOut" class="ason-widget tt-top"></a></li>
                    </ul>
                  </div>
                </div>
                <div class="statistic-body">
                  <div data-ason-type="scrollbar" data-ason-min-height="320px" class="statistic-activity ason-widget">
                    <c:forEach var="pharmaCompany" items="${Companies}">
                    <div class="activity-row">
                    <div class="l-col-xs-2 activity-img"><a href="${pharmaCompany.companyUrl}" target="_blank"><span><img src = "${pharmaCompany.displayPicture.dPicture}" alt="${pharmaCompany.companyName} Logo" style="max-width: 60px;"/></span></a></div>
                      <div class="l-col-xs-9 activity-info">
                        <h5><a href="#">${pharmaCompany.companyName}</a> - ${pharmaCompany.status}.
                        </h5>
                        <div>${pharmaCompany.companyDesc}</div>
                        <div class="time">${pharmaCompany.contactName} - ${pharmaCompany.contactNo}</div>
                        <ul class="activity-settings">
                          <li><a href="javascript:void(0)"><i class="fa fa-edit"></i></a></li>
                          <li><a href="#" data-ason-type="delete" data-ason-target=".activity-row" data-ason-content="true" data-ason-animation="fadeOut" class="ason-widget"></a></li>
                        </ul>
                      </div>
                    </div>
                   </c:forEach>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

