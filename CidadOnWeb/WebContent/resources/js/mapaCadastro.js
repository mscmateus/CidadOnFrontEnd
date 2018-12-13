var map, infoWindow, br = { lat : -15.867772243356159, lng : -48.117371676254095 }, cordenadasAtual, marca=null;
function initMap(){
	var mapOptions = { center : br, zoom : 4, mapTypeId : google.maps.MapTypeId.ROADMAP };
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	geolocalization()
	map.addListener('click', function(e) {
		if(marca==null){
			fazPonto(e.latLng);
		}else{
			marca.setMap(null);
			marca=null;
			fazPonto(e.latLng);
		}
	});
	
}
//cria um ponto no mapa
function fazPonto(ponto) {
	marca = new google.maps.Marker({ position : ponto, map : map });
	cordenadasAtual = ponto;
	setDocumentoInfo(ponto);
	map.panTo(ponto);
}
function setDocumentoInfo(ponto){
	document.getElementById("formulario:latitude").value = ponto.lat();
	document.getElementById("formulario:longitude").value = ponto.lng();
}
function getDocumentoInfo(){
	return new google.maps.LatLng(document.getElementById("formulario:latitude").value,document.getElementById("formulario:longitude").value);
}
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