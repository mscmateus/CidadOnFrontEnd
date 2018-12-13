var map, infoWindow, br = { lat : -15.867772243356159, lng : -48.117371676254095 }, cordenadasAtual, arrayPontos = [];
function initMap(){
	var mapOptions = { center : br, zoom : 4, mapTypeId : google.maps.MapTypeId.ROADMAP };
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	document.getElementById("formulario:btnInfoProblema").disabled = true;
	geolocalization()
}
//cria um ponto no mapa
function fazPonto(ponto,titulo,posicao) {
	arrayPontos[posicao] = new google.maps.Marker({
	    position: ponto,
	    map: map,
	    title: titulo,
	});
	arrayPontos[posicao].addListener('click', function() {
		document.getElementById("formulario:btnInfoProblema").disabled = false;
		document.getElementById("formulario:idProblema").value = arrayPontos[posicao].getTitle();
		map.setZoom(18);
	    map.setCenter(arrayPontos[posicao].getPosition());
	    return "#{problemacontrolador.instanciaProblema}"
	});
}
infoWindow = new google.maps.InfoWindow;
infoWindow = new google.maps.InfoWindow;
function geolocalization() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = { lat : position.coords.latitude, lng : position.coords.longitude };
			map.setCenter(pos);
			map.setZoom(18);
			infoWindow.setPosition(pos);
			infoWindow.setContent('Você esta aqui!');
			infoWindow.open(map);
		}, function() {
			handleLocationError(true, infoWindow, map.getCenter());
		});
	} else {
		// Browser doesn't support Geolocation
		handleLocationError(false, infoWindow, map.getCenter());
	}
}
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	infoWindow.setPosition(pos);
	infoWindow.setContent(browserHasGeolocation ? 'Erro: O serviço de golocalização falhou :(.': 'Seu navegador não suporta geolocalização..');
	infoWindow.open(map);
}