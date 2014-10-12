<!DOCTYPE html>
<html lang="en">
<head>
	<!-- META -->
	<title>Map</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta name="description" content="" />
	<link rel="stylesheet" type="text/css" href="css/style.css" media="all" /> 
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	
	<!--
	<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js'></script>
	
	<script type="text/javascript" src="http://www.blurjs.com/blur.js"></script>
	 <script type="text/javascript" src="http://gianlucaguarini.github.io/Vague.js/Vague.js"></script> -->

	<script type="text/javascript" src="js/jquery.slimscroll.min.js"></script>
	<!-- 
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script type="text/javascript" src="http://jquery-ui-map.googlecode.com/svn/trunk/ui/jquery.ui.map.js"></script>
	 -->

	<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<![endif]-->

	<!--[if IE 6]>
	<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
	<script>
	  DD_belatedPNG.fix('.nav, a ,img, .png24');
	</script>
	<![endif]-->
    
    
    <link rel="stylesheet" href="http://www.openlayers.org/api/theme/default/style.css" type="text/css">
    
   <style>
     .map {
       height: 700px;
       width: 100%;
	       }
   </style>
   <!--<script src="http://openlayers.org/en/v3.0.0/build/ol.js" type="text/javascript"></script>-->
   <script src="http://www.openlayers.org/api/OpenLayers.js" type="text/javascript"></script>
   
   
</head>
<body>

	<div class="map_page">

		<div class="main-header full_width fixed">
			<div class="header-wrapper">
				<div class="header-content">
					<nav class="top-nav" role="navigation">
						<ul id="navigation">
					<li class="home">
						<a href="index.php" title=""><h1>Karnataka Learning Partnership</h1></a>
					</li>
					<li class="school_map">
						<a href="map1.html" title="">School Map</a>
					</li>
					<li class="programmes">
						<a href="programmes.php" title="">Programmes</a>
						<ul>
							<li><a href="reading_programme.php" title=""><span></span>Reading</a></li>
							<li><a href="maths_programme.php" title=""><span></span>Math</a></li>
							<li><a href="library_programme.php" title=""><span></span>Library</a></li>
							<li><a href="preschool_programme.php" title=""><span></span>Pre-School</a></li>
						</ul>
					</li>
					<li class="volunteer"><a href="volunteer.php" title="">Volunteer</a></li>
					<li class="database">
						<a href="status_1.php" title="">Database</a>
						<ul>
							<li><a href="status_1.php" title=""><span></span>Status 1</a></li>
							<li><a href="status_2.php" title=""><span></span>Status 2</a></li>
							<li><a href="status_3.php" title=""><span></span>Status 3</a></li>
							<li><a href="#" title=""><span></span>Raw Data</a></li>
							<li><a href="#" title=""><span></span>Github</a></li>
						</ul>
					</li>
					<li class="reports"><a href="#" title="">Reports</a></li>
					<li class="about">
						<a href="about.php" title="">About</a>
						<ul>
							<li><a href="about.php" title=""><span></span>About</a></li>
							<li><a href="partners.php" title=""><span></span>Partners</a></li>
							<li><a href="disclaimer.php" title=""><span></span>Disclaimer</a></li>
						</ul>
					</li>
				</ul>
					</nav>
					<div class="clr"></div>
				</div>
			</div>

			<div class="subheader-wrapper">
				<div class="subheader-content">
					<div class="breadcrumb">
				    	<a href="">Home</a><span></span>
				    	<a href="">School map</a>
				    </div>
				</div>
			</div>
            
		</div>
        
        <div id="map" class="map fixed">

		</div>

        
</div>


<script type="text/javascript">

	//$('#map').ready(function(){
	  // page_init()	
	//});
		
     var map, layer;

            //function page_init(){
                map = new OpenLayers.Map("map");
                var mapnik         = new OpenLayers.Layer.OSM();
                var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
                var toProjection   = new OpenLayers.Projection("EPSG:3857"); // to Spherical Mercator Projection
                var position       = new OpenLayers.LonLat(80.2700,13.0839).transform( fromProjection, toProjection);
                var zoom           = 15;     
                map.addLayer(mapnik);
                map.setCenter(position, zoom );
            //}

            function putMarker(latMarca, lonMarca)
            {
                var lonLat = new OpenLayers.LonLat(lonMarca ,latMarca ).transform(new OpenLayers.Projection("EPSG:4326"),map.getProjectionObject());
                var zoom=16;
                var markers = new OpenLayers.Layer.Markers( "Markers" );
                map.addLayer(markers);
                markers.addMarker(new OpenLayers.Marker(lonLat));
                map.setCenter (lonLat, zoom);
            }
	//});
   </script>
   