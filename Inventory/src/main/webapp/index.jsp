<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<html lang="en" class="no-js">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>MedRep</title>

		
		<!-- Bootstrap -->
		<script src='<c:url value="/resources/js/modernizr.custom.js"/>'></script>
		<link href='<c:url value="/resources/landing_page/css/bootstrap.min.css"/>' rel="stylesheet">
		<link href='<c:url value="/resources/landing_page/css/jquery.fancybox.css"/>' rel="stylesheet">
		<link href='<c:url value="/resources/landing_page/css/flickity.css"/>' rel="stylesheet" >
		<link href='<c:url value="/resources/landing_page/css/animate.css"/>' rel="stylesheet">
		<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
		<link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>
		<link href='<c:url value="/resources/landing_page/css/styles.css"/>' rel="stylesheet">
		<link href='<c:url value="/resources/landing_page/css/queries.css"/>' rel="stylesheet">
		<link rel="shortcut icon" href='<c:url value="/resources/img/fave-icon.png"/>'>
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
		<!--[if lt IE 7]>
		<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
		<![endif]-->
		<!-- open/close -->
		<header>
			<section class="hero">
				<div class="texture-overlay"></div>
				<div class="container">
					<div class="row nav-wrapper">
						<!-- <div class="col-md-6 col-sm-6 col-xs-6 text-left">
							<a href="index.html"><img src="img/logo.png" alt="Boxify Logo"></a>
						</div> -->
						<div class="col-md-12 col-sm-12 col-xs-12 text-right navicon">
							<p>MENU</p><a id="trigger-overlay" class="nav_slide_button nav-toggle" href="#"><span></span></a>
						</div>
					</div>
					<div class="row hero-content">
						<div class="col-md-12">
							<h1 class="animated fadeInDown"><span>Integrating The</span><br> Healthcare Ecosysytem</h1>
						</div>
						<div class="col-md-12 btn-bottom">
							<a href="#" class="use-btn animated fadeInUp">Download</a>
							<a href="./web/auth/login.do" class="use-btn animated fadeInUp">Login</a></div>
						</div>
					</div>
				</div>
			</section>
		</header>
		
		<!-- <footer>
			<div class="container">
				<div class="row">
					<div class="col-md-5">
						<h1 class="footer-logo">
						<img src="img/logo-blue.png" alt="Footer Logo Blue">
						</h1>
						<p>© Boxify 2015 - <a href="http://tympanus.net/codrops/licensing/">Licence</a> - Designed &amp; Developed by <a href="http://www.peterfinlan.com/">Peter Finlan</a></p>
					</div>
					<div class="col-md-7">
						<ul class="footer-nav">
							<li><a href="#about">About</a></li>
							<li><a href="#features">Features</a></li>
							<li><a href="#screenshots">Screenshots</a></li>
							<li><a href="#download">Download</a></li>
						</ul>
					</div>
				</div>
			</div>
		</footer> -->
		<div class="overlay overlay-boxify">
			<nav>
				<ul>
					<li><a href="http://erfolglifesciences.com/" target="_blank"><i class="fa fa-heart"></i>About</a></li>
					<li><a href="./web/auth/support.do"><i class="fa fa-flash"></i>Support</a></li>
				</ul>
				<!-- <ul>
					<li><a href="#screenshots"><i class="fa fa-desktop"></i>Screenshots</a></li>
					 <li><a href="#download"><i class="fa fa-download"></i>Download</a></li> 
				</ul> -->
			</nav>
		</div>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src='<c:url value="/resources/landing_page/js/min/toucheffects-min.js"/>'></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src='<c:url value="/resources/landing_page/js/flickity.pkgd.min.js"/>'></script>
		<script src='<c:url value="/resources/landing_page/js/jquery.fancybox.pack.js"/>'></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src='<c:url value="/resources/landing_page/js/retina.js"/>'></script>
		<script src='<c:url value="/resources/landing_page/js/waypoints.min.js"/>'></script>
		<script src='<c:url value="/resources/landing_page/js/bootstrap.min.js"/>'></script>
		<script src='<c:url value="/resources/landing_page/js/min/scripts-min.js"/>'></script>

	</body>
</html>
