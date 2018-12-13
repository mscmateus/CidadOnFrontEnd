var map, infoWindow,br = { lat : -15.867772243356159, lng : -48.117371676254095 }, cordenadasAtual, marca=null;
function initMap(){
	var mapOptions = { center : br, zoom : 4, mapTypeId : google.maps.MapTypeId.ROADMAP };
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	fazPontoHome();
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
	marca = new google.maps.Marker({ position : ponto, map : map, title: 'Novo endereço' });
	cordenadasAtual = ponto;
	setDocumentoInfo(ponto);
	map.setZoom(18);
	map.panTo(ponto);
}
function fazPontoHome() {
	var home = new google.maps.Marker({ position : getDocumentoInfo(), map : map ,title: 'Minha casa atual'});
	map.setZoom(18);
	map.panTo(getDocumentoInfo());
}
function setDocumentoInfo(ponto){
	document.getElementById("formulario:latitude").value = ponto.lat();
	document.getElementById("formulario:longitude").value = ponto.lng();
}
function getDocumentoInfo(){
	return new google.maps.LatLng(document.getElementById("formulario:latitude").value,document.getElementById("formulario:longitude").value);
}