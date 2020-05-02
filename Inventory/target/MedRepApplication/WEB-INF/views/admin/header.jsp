<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!--HEADER-->
        <header class="l-header l-header-1 t-header-1">
          <div class="navbar navbar-ason">
            <div class="container-fluid">
              <div id="ason-navbar-collapse" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <!-- Search Widget-->
                    <div class="widget-search search-in-header is-search-right t-search-1">
                      <form role="form" action="">
                        <input type="text" placeholder="Search..." class="form-control">
                        <button type="submit" class="btn"><i class="fa fa-search"></i></button>
                      </form>
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