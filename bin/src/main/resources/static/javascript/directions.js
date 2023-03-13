//카카오 지도 api

var container = document.getElementById('map');
var options = {
        center: new kakao.maps.LatLng(34.750053, 127.748136),
        level: 3
    };
var map = new kakao.maps.Map(container, options);
var markerPosition  = new kakao.maps.LatLng(34.750053, 127.748136); 
var marker = new kakao.maps.Marker({
        position: markerPosition
    });
    marker.setMap(map);