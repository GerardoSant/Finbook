//Script para que se abra la factura de venta cuando clickas

$(document).ready(function ($) {
    $('*[data-href]').on('click', function () {
        window.location = /bills/ + $(this).data("href");
    });
});

//Redireccionar con los clicks en boxes a algun reporte.
function redirectWithPeriod(route) {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('periodStart') == null) window.location = route;
    else window.location = route + "?periodStart=" + urlParams.get('periodStart') + "&" + "periodEnd=" + urlParams.get('periodEnd');
}


//Resizing de charts

