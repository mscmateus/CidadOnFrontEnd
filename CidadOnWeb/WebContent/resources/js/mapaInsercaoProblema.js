var map, infoWindow, ufac = { lat : -9.954090, lng : -67.863422 }, cordenadasAtual, marca=null, arrayPontos = [];
function initMap(){
	var mapOptions = { center: getDocumentoInfoCasa() , zoom : 16, mapTypeId : google.maps.MapTypeId.ROADMAP };
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	fazPontoHome();
	geolocalization()
	map.addListener('click', function(e) {
		if(marca==null){
			document.getElementById("formulario:idProblema").disabled = true;
			fazPonto(e.latLng);
			return "#{problemacontrolador.instanciaProblema}"
		}else{
			document.getElementById("formulario:idProblema").disabled = true;
			marca.setMap(null);
			marca=null;
			fazPonto(e.latLng);
			return "#{problemacontrolador.instanciaProblema}"
		}
	});
}
//cria um ponto no mapa

function fazPonto(ponto) {
	marca = new google.maps.Marker({ position : ponto, map : map });
	document.getElementById("formulario:idProblema").value = "";
	document.getElementById("formulario:btnIncluir").disabled = false;
	document.getElementById("formulario:btnEditar").disabled = true;
	document.getElementById("formulario:btnExcluir").disabled = true;
	cordenadasAtual = ponto;
	setDocumentoInfo(ponto);
	map.panTo(ponto);
}
function fazPontoHome() {
	var home = new google.maps.Marker({ position : getDocumentoInfoCasa(), map : map ,title: 'Minha casa',});
	document.getElementById("formulario:btnIncluir").disabled = true;
	document.getElementById("formulario:btnEditar").disabled = true;
	document.getElementById("formulario:btnExcluir").disabled = true;
}
function fazTodosPontos(ponto,titulo,posicao) {
	arrayPontos[posicao] = new google.maps.Marker({
	    position: ponto,
	    map: map,
	    title: titulo,
	});
	arrayPontos[posicao].addListener('click', function() {
		document.getElementById("formulario:btnIncluir").disabled = true;
		document.getElementById("formulario:btnEditar").disabled = false;
		document.getElementById("formulario:btnExcluir").disabled = false;
		if(marca!=null){
			marca.setMap(null);
			marca=null;			
		}
		document.getElementById("formulario:idProblema").value = arrayPontos[posicao].getTitle();
		setDocumentoInfo(arrayPontos[posicao].getPosition());
		map.setZoom(18);
	    map.setCenter(arrayPontos[posicao].getPosition());
	    return "#{problemacontrolador.instanciaProblema}"
	});
}
function setDocumentoInfo(ponto){
	document.getElementById("formulario:latitude").value = ponto.lat();
	document.getElementById("formulario:longitude").value = ponto.lng();
}
function getDocumentoInfo(){
	var auxpoint = new google.maps.LatLng(document.getElementById("formulario:latitude").value,document.getElementById("formulario:longitude").value);
	return auxpoint;
}
function getDocumentoInfoCasa(){
	return new google.maps.LatLng(document.getElementById("formulario:latitudeCasa").value , document.getElementById("formulario:longitudeCasa").value);
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