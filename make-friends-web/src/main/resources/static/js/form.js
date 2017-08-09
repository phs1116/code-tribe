/**
 * Created by hwatu on 2017. 3. 9..
 */
$(function () {
    var items;
    $("#inputPlace").autocomplete({
        source: function (request, response) {
            $.ajax({
                type: 'get',
                url: "https://apis.daum.net/local/v1/search/keyword.json?apikey=3419ea623aa8f38277d0238630747bc0&query=" + request.term
                + "&callback=?",
                dataType: "json",
                success: function (data) {
                    items = data.channel.item;
                    response(
                        $.map(items, function (item) {
                            console.log(item);
                            return {
                                label: item.title + " (" + item.newAddress + ")",
                                value: item.title,
                                longitude: item.longitude,
                                latitude: item.latitude,
                                placeId: item.id
                            }
                        })
                    );
                }
            });
        }, minLength: 2
        , select: function (event, ui) {
            console.log(ui.item.value, ui.item.label);
            var container = document.getElementById("map");
            var position = new daum.maps.LatLng(ui.item.latitude, ui.item.longitude);
            container.style.height = "250px";
            var option = {
                center : position
            }
            var map = new daum.maps.Map(container, option);
            var marker = new daum.maps.Marker({position : position});
            marker.setMap(map);
            $("#inputLongitude").val(ui.item.longitude);
            $("#inputLatitude").val(ui.item.latitude);
        }
    })
});
