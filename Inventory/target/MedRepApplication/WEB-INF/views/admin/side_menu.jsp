<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--Left Sidebar Content-->
<aside id="sb-left" class="l-sidebar l-sidebar-1 t-sidebar-1">
	<!--Switcher-->
	<div class="l-side-box">
		<a href="#" data-ason-type="sidebar" data-ason-to-sm="sidebar"
			data-ason-target="#sb-left"
			class="sidebar-switcher switcher t-switcher-side ason-widget"><i
			class="fa fa-bars"></i></a>
	</div>
	<div class="l-side-box">
		<!--Logo-->
		<div class="widget-logo logo-in-side">
			<h1>
				<a href="javascript:void(0)"><span
					class="logo-default visible-default-inline-block"><img
						src='<c:url value="/resources/img/logo.png"/>' alt="MedRep"></span><span
					class="logo-medium visible-compact-inline-block"><img
						src='<c:url value="/resources/img/logo_medium.png"/>' alt="MedRep"
						title="MedRep"></span> <spanl
						class="logo-small visible-collapsed-inline-block">
					<img src='<c:url value="/resources/img/logo_small.png"/>'
						alt="MedRep" title="MedRep"></spanl></a>
			</h1>
		</div>
	</div>
	<!--Main Menu-->
	<div class="l-side-box">
		<!--MAIN NAVIGATION MENU-->
		<ul data-ason-type="menu" class="ason-widget">
			<li><a href="../admin/dashboard.do"><i
					class="icon fa fa-dashboard"></i><span class="title">Dashboard</span></a></li>
			<li><a href="../admin/notifications.do"><i
					class="icon fa fa-bell"></i><span class="title">Notifications</span></a></li>
			<li><a href="../admin/surveys.do"><i class="icon fa fa-edit"></i><span
					class="title">Surveys</span></a></li>
			<li><a href="../admin/doctors.do"><i class="icon fa fa-user"></i><span
					class="title">Doctors</span></a></li>
			<li><a href="../admin/pharma-company.do"><i
					class="icon fa fa-building"></i><span class="title">Pharma
						Companies</span></a></li>
			<li><a href="../admin/pharma-reps.do"><i
					class="icon fa fa-user"></i><span class="title">Pharma
						Representatives</span></a></li>
			<li><a href="../admin/news.do"><i
					class="icon fa fa-bell"></i><span class="title">News
						</span></a></li>	
			<li><a href="../admin/transform.do"><i
					class="icon fa fa-bell"></i><span class="title">Transform
						</span></a></li>
			<li><a href="../admin/devices.do"><i
					class="icon fa fa-bell"></i><span class="title">Medical Devices
						</span></a></li>			
		</ul>
		</nav>
	</div>
</aside>