<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  //String homeUrl = com.medrep.app.util.MedRepProperty.getInstance().getProperties("medrep.home");
String homeUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Venkat</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta HTTP-EQUIV="Cache-Control" CONTENT="No-Cache,Must-Revalidate,No-Store">
<!-- ===== FAVICON =====-->
<!-- <link rel="shortcut icon" href="favicon.ico">-->
<!-- ===== CSS =====-->
<!-- General-->
<link rel="stylesheet" href='<c:url value="/resources/css/basic.css"/>'>
<link rel="stylesheet" href='<c:url value="/resources/css/general.css"/>'>
<link rel="stylesheet" href='<c:url value="/resources/css/theme.css"/>' class="style-theme">
<link rel="stylesheet" href='<c:url value="/resources/css/multiselect.css" />'>
<!-- <link href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" rel='stylesheet' /> -->

<!-- Specific-->
<link rel="stylesheet" href='<c:url value="/resources/css/addons/fonts/artill-clean-icons.css"/>' />
<link rel="shortcut icon" href='<c:url value="/resources/img/fave-icon.png"/>'>
<!--[if lt IE 9]>
    <script src='<c:url value="/resources/js/basic/respond.min.js"/>'></script>
    <script src='<c:url value="/resources/js/basic/html5shiv.min.js"/>'></script>
    <![endif]-->
    <!-- ===== JS =====-->
	<!-- jQuery-->
	<script src='<c:url value="/resources/js/basic/jquery.min.js"/>'></script>
	<script src='<c:url value="/resources/js/basic/jquery-migrate.min.js"/>'></script>
	<script src='<c:url value="/resources/js/basic/jquery-ui.js"/>'></script>
	<!-- General-->
	<script src='<c:url value="/resources/js/basic/modernizr.min.js"/>'></script>
	<script src='<c:url value="/resources/js/basic/bootstrap.min.js"/>'></script>
	<script src='<c:url value="/resources/js/shared/jquery.asonWidget.js"/>'></script>
	<script src='<c:url value="/resources/js/plugins/plugins.js"/>'></script>
	<script src='<c:url value="/resources/js/general.js"/>'></script>
	<script src='<c:url value="/resources/js/basic/multiselect.js"/>'></script>
    <SCRIPT type="text/javascript">
    var homeUrl ="<%=homeUrl  %>";
	window.history.forward();
	
	function noBack() { window.history.forward(); }
</SCRIPT>
</head>
<body class="l-dashboard" onload="noBack();">

	<!--[if lt IE 9]>
    <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->

	<!--SECTION-->
	<section class="l-main-container">
		<tiles:insertAttribute name="side_menu" />
 --%>		<!--Main Content-->
		<section class="l-container">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="body" />
			<tiles:insertAttribute name="footer" />

		</section>
	</section>
	
	<!-- Semi general-->
	<script type="text/javascript">	
    $(document).ready(function(){ //1.11.2 
      $(".notificationmsg").stop(true, true).animate({top:'4px',opacity:0}, 5000).fadeOut('slow');	  
    
    $("#dataTableId1").DataTable();
  	$("#dataTableId2").DataTable();
  	$("#dataTableId3").DataTable();
  	
  	}); //jquery document end here document
  	
      var paceSemiGeneral = { restartOnPushState: false };
      if (typeof paceSpecific != 'undefined'){
      	var paceOptions = $.extend( {}, paceSemiGeneral, paceSpecific );
      	paceOptions = paceOptions;
      }else{
      	paceOptions = paceSemiGeneral;
      }
      
    </script>
	<script
		src='<c:url value="/resources/js/plugins/pageprogressbar/pace.min.js"/>'></script>
	<!-- Specific-->
	<script src='<c:url value="/resources/js/shared/classie.js"/>'></script>
	<script
		src='<c:url value="/resources/js/shared/jquery.cookie.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/shared/perfect-scrollbar.min.js"/>'></script>

	<script
		src='<c:url value="/resources/js/plugins/datetime/bootstrap-datetimepicker.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/datetime/daterangepicker.js"/>'></script>
	<script src='<c:url value="/resources/js/calls/ui.datetime.js"/>'></script>

	<script
		src='<c:url value="/resources/js/plugins/accordions/jquery.collapse.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/accordions/jquery.collapse_storage.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/accordions/jquery.collapse_cookie_storage.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/accordions/jquery.collapsible.min.js"/>'></script>
	<script src='<c:url value="/resources/js/calls/ui.accordions.js"/>'></script>

	<script
		src='<c:url value="/resources/js/plugins/forms/elements/jquery.bootstrap-touchspin.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/forms/elements/jquery.checkBo.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/forms/elements/jquery.switchery.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/table/jquery.dataTables.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/tabs/jquery.easyResponsiveTabs.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/tooltip/jquery.tooltipster.min.js"/>'></script>
	<script src='<c:url value="/resources/js/calls/part.header.1.js"/>'></script>
	<script src='<c:url value="/resources/js/calls/part.sidebar.2.js"/>'></script>
	<script
		src='<c:url value="/resources/js/calls/part.theme.setting.js"/>'></script>

	<script src='<c:url value="/resources/js/calls/table.data.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/table/dataTables.autoFill.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/table/dataTables.colReorder.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/table/dataTables.colVis.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/table/dataTables.responsive.min.js"/>'></script>

	<script src='<c:url value="/resources/js/shared/moment.min.js"/>'></script>

	<script
		src='<c:url value="/resources/js/plugins/datetime/bootstrap-datepicker.min.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/datetime/clockface.js"/>'></script>
	<script
		src='<c:url value="/resources/js/plugins/datetime/jqClock.min.js"/>'></script>

	<script
		src='<c:url value="/resources/js/plugins/notifications/jquery.jgrowl.min.js"/>'></script>
	<script src='<c:url value="/resources/js/calls/ui.datetime.js"/>'></script>

	<script type="text/javascript">
 
    $(document).on('change', '.btn-file :file', function() {
        var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
       // input.trigger('fileselect', [numFiles, label]);
       var input1 = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;

        if( input1.length ) {
            input1.val(log);
        } else {
            if( log ) alert(log);
        }
    });

    $('.btn-file :file').on('fileselect', function(event, numFiles, label) {

        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;

        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }

    });
    
    </script>
</body>
</html>