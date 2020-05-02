<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!--HEADER-->
        <header class="l-header l-header-1 t-header-1">
          <div class="navbar navbar-ason">
            <div class="container-fluid">
              <div id="ason-navbar-collapse" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <!-- Search Widget-->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						  <ul class="nav navbar-nav " >
						    <li>
						      <a href="../admin/dashboard.do" style= "color: #bd200f; font-weight: bold; font-size: 18px; letter-spacing: 1px; border: 2px solid #bd200f;
    							margin: 8px; padding: 8px 35px; border-radius: 6px;"> DOCTOR</a>
						    </li>			
						  </ul>
						  <ul class="nav navbar-nav">
						    <li>
						      <a href="#"style= "background-color: #bd200f; color:#fff; font-weight: bold; font-size: 18px; letter-spacing: 1px; border: 2px solid #bd200f;
    							margin: 8px; padding: 8px 35px; border-radius: 6px;"> PATIENT</a>
						    </li>
						  </ul>
						</div>
                  </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                  <li class="hidden-sm">
                    <!-- Full Screen Toggle--><a href="#" class="full-screen-page sidebar-switcher switcher t-switcher-header"><i class="fa fa-expand"></i></a>
                  </li>
                  <li>
                    <!-- Profile Widget-->
                    <div class="widget-profile profile-in-header">
                      <button type="button" data-toggle="dropdown" class="btn dropdown-toggle"><span class="">${sessionScope.UserName} </span><img src="${ sessionScope.profiePicture.dPicture}" alt="Profile picture"/></button>
                      <ul role="menu" class="dropdown-menu">
                        <li><a href="javascript:void(0)"><i class="fa fa-user"></i>Profile</a></li>
                        <li><a href="javascript:void(0)"><i class="fa fa-envelope"></i>Inbox</a></li>
                        <li><a href="javascript:void(0)"><i class="fa fa-cog"></i>Settings</a></li>
                        <li class="lock"><a href="../logout.do"><i class="fa fa-lock"></i>Log Out</a></li>
                      </ul>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </header>