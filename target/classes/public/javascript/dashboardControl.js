//Script para que se abra la factura de venta cuando clickas

$(document).ready(function ($) {
    $('*[data-href]').on('click', function () {
        window.location = /bills/ + $(this).data("href");
    });
});




//Redireccionar con los clicks en boxes a algun reporte.

const urlParams = new URLSearchParams(window.location.search);

function redirectWithPeriod(route) {
    if (urlParams.get('periodStart') == null) window.location = route;
    else window.location = route + "?periodStart=" + urlParams.get('periodStart') + "&" + "periodEnd=" + urlParams.get('periodEnd');
}


//Actualizar modal

function updateModal(){
    if (urlParams.get('periodStart') != null) {
        document.getElementById("pStart").value=urlParams.get('periodStart');
        document.getElementById("pEnd").value=urlParams.get('periodEnd');
    }
}

updateModal()

//Resizing de charts

