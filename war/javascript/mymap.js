	var ville = [{"name":"Rouen","latitude":49.433,"commercial_name":"cy'clic","longitude":1.083},{"name":"Paris","latitude": 48.833,"commercial_name":"Velib","longitude":"FR"},{"name":"Toulouse","latitude":43.617,"commercial_name":"Vélô´","longitude":1.450},{"name":"Luxembourg","latitude":49.617,"commercial_name":"Veloh","longitude":6.133},{"name":"Valence","latitude":44.933,"commercial_name":"Valenbisi","longitude":4.900},{"name":"Stockholm","latitude":59.333,"commercial_name":"Cyclocity","longitude":18.083},{"name":"Goteborg","latitude":57.7,"commercial_name":"Göteborg","longitude":11.933333},{"name":"Santander","latitude":43.467,"commercial_name":"Tusbic","longitude":-3.800},{"name":"Amiens","latitude":49.900,"commercial_name":"Velam","longitude":2.300},{"name":"Mulhouse","latitude":47.75,"commercial_name":"VéloCité","longitude":7.35},{"name":"Lyon","latitude":45.767,"commercial_name":"Vélo'V","longitude":4.833},{"name":"Ljubljana","latitude":46.067,"commercial_name":"Bicikelj ","longitude":14.5},{"name":"Seville","latitude":4.267,"commercial_name":"SEVICI","longitude":-75.967},{"name":"Nancy","latitude":48.7,"commercial_name":"vélOstan'lib","longitude":6.2},{"name":"Namur","latitude":50.467,"commercial_name":"Li bia velo","longitude":4.867},{"name":"Creteil","latitude": 	48.791,"commercial_name":"Cristolib","longitude":2.462},{"name":"Cergy-Pontoise","latitude": 49.036,"commercial_name":"Velo2","longitude":2.063},{"name":"Bruxelles-Capitale","latitude": 50.8466,"commercial_name":"villo","longitude":4.352},{"name":"Toyama","latitude":36.695,"commercial_name":"cyclocity","longitude":137.213},{"name":"Marseille","latitude":43.30,"commercial_name":"Le vélo","longitude":5.367},{"name":"Nantes","latitude":47.233,"commercial_name":"Bicloo","longitude":-1.583},{"name":"Besancon","latitude":47.250,"commercial_name":"VéloCité","longitude":5.983}];

	// Toutes mes données de villes avec les latitudes.
	// clé : 76935431113936f44c94bef44b30e13675540270

	function initialize() {
		var mapOptions = {
			zoom: 7,
			center: new google.maps.LatLng(48.84049, 2.324),
			mapTypeId: google.maps.MapTypeId.ROADMAP
		}
		var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
		var bounds = new google.maps.LatLngBounds();
		var bound1 = new google.maps.LatLng(50.650, 3.083);
		var bound2 = new google.maps.LatLng(42.700, 2.90);
		var bound3 = new google.maps.LatLng(48.383, -4.500);
		var bound4 = new google.maps.LatLng(48.583, 7.750);
		bounds.extend(bound1);
		bounds.extend(bound2);
		bounds.extend(bound3);
		bounds.extend(bound4);
		map.fitBounds(bounds);
		var info_window = new google.maps.InfoWindow({ content: 'loading' });
		
		

		var i=0;
		for (i; i <= 22; i++) {


			var m = new google.maps.Marker(
			{
				animation: google.maps.Animation.DROP,
				title:     ville[i].name,
				position : new google.maps.LatLng(
					ville[i].latitude,
					ville[i].longitude),
				map : map,
				html:"<h1>Ville de "+ville[i].name+"</h1>"+"<br>Nom du service : "+ville[i].commercial_name+"</br>"
			});
			google.maps.event.addListener(m, 'click', function() {
				info_window.setContent(this.html);
				info_window.open(map, this);
			});
		};

	}

	google.maps.event.addDomListener(window, 'load', initialize);

