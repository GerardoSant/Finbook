//Script para abrir listas con facturas

jQuery(document).ready(function ($) {
    $('*[data-href]').on('click', function () {
        window.location = /bills/ + $(this).data("href");
    });
    $('.opener').on('click', function () {
        var itemsToToggleClass = "." + jQuery(this).attr("id");
        jQuery(this).toggleClass("opened");
        $(itemsToToggleClass).toggleClass("hidden");
        //$(itemsToToggleClass).last().css( "border-bottom", "groove" );
    });
});

//Handler del menu lateral

// Cuando se abre el menu lateral, se cambia a static la position del leftbox
$(document).ready(function () {
    $('#menuBtn').on('click', function () {
        document.getElementsByClassName("leftbox")[0].classList.add("static");
    });
});

document.getElementsByClassName('overlay')[0].addEventListener('transitionend', function (event) {
    if (event.propertyName !== 'width') return;
    //Apertura del panel
    if (innerWidth - 250 > 992 && innerHeight > 650 && document.getElementById("myNav").style.width == "250px") {
        document.getElementsByClassName("leftbox")[0].classList.add("fixed");
        document.getElementsByClassName("leftbox")[0].classList.remove("static");
        console.log("end");
    }
    //Cierre del panel
    if (document.getElementById("myNav").style.width == "0px") {
        document.getElementsByClassName("leftbox")[0].classList.remove("fixed");
        document.getElementsByClassName("leftbox")[0].classList.remove("static");
        console.log("end");
    }
});

window.addEventListener("resize", fixBoxes);

//Cuando se hace resize de la ventana, si el panel está abierto position de "leftbox" pasa a ser static.
function fixBoxes() {
    if (document.getElementById("myNav").style.width == "250px") {
        if (innerWidth - 250 < 1250) {
            document.getElementsByClassName("leftbox")[0].classList.add("static");
            document.getElementsByClassName("leftbox")[0].classList.remove("fixed");
        }
    }
}



// Obtener periodo del informe

function parseDate(year, month, day) {
    var parsedYear = year + 1900;
    var parsedMonth = month + 1;
    var parsedDay = day + 0;
    if (parsedDay<10) parsedDay = "0"+parsedDay;
    if (parsedMonth<10) parsedMonth = "0" + parsedMonth;
    return parsedYear + "-" + parsedMonth + "-" + parsedDay;
}

function setPeriodStartDate(year, month, day) {
    console.log(parseDate(year,month,day));
    document.getElementById('pStart').value =parseDate(year,month,day);
}

function setPeriodEndDate(year, month, day) {
    document.getElementById('pEnd').value =parseDate(year,month,day);
}
