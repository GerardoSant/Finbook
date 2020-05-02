//Script to know display where you are on side menu
$(document).ready(function ($) {
    console.log(window.location.pathname);
    if (window.location.pathname == "/dashboard") $('#dashboardNavRow').toggleClass('overlay-content-row-selected');
    if (window.location.pathname == "/bills") {
        $('#billsNavRow').toggleClass('overlay-content-row-selected');
        $('#billsNavRow').find('.righticon').toggleClass('fa-angle-left');
        $('#billsNavRow').find('.righticon').toggleClass('fa-angle-down');
        var itemsToToggleClass = "." + $('#billsNavRow').attr("id");
        $(itemsToToggleClass).toggleClass("hidden");
        $('#myBillsSubRow').toggleClass('overlay-subrow-selected');
    }
    if (window.location.pathname == "/timeline") {
        $('#billsNavRow').toggleClass('overlay-content-row-selected');
        $('#billsNavRow').find('.righticon').toggleClass('fa-angle-left');
        $('#billsNavRow').find('.righticon').toggleClass('fa-angle-down');
        var itemsToToggleClass = "." + $('#billsNavRow').attr("id");
        $(itemsToToggleClass).toggleClass("hidden");
        $('#timelineSubRow').toggleClass('overlay-subrow-selected');
    }
    if (window.location.pathname.indexOf("/reports") != -1) {
        $('#reportsNavRow').toggleClass('overlay-content-row-selected');
        $('#reportsNavRow').find('.righticon').toggleClass('fa-angle-left');
        $('#reportsNavRow').find('.righticon').toggleClass('fa-angle-down');
        var itemsToToggleClass = "." + $('#reportsNavRow').attr("id");
        $(itemsToToggleClass).toggleClass("hidden");
        if (window.location.pathname.indexOf("investments") != -1) $('#investmentsSubRow').toggleClass('overlay-subrow-selected');
        if (window.location.pathname.indexOf("profitandlosses") != -1) $('#profitSubRow').toggleClass('overlay-subrow-selected');
        if (window.location.pathname.indexOf("amortization") != -1) $('#amortizationSubRow').toggleClass('overlay-subrow-selected');
    }
    if (window.location.pathname == "/uploadbills/") $('#uploadNavRow').toggleClass('overlay-content-row-selected');
    if (window.location.pathname == "/exampleshiny") $('#statisticsNavRow').toggleClass('overlay-content-row-selected');

})


//Script for opening/closing subrows in side menu

$(document).ready(function ($) {
    $('.subrow-opener').on('click', function () {
        var itemsToToggleClass = "." + $(this).attr("id");
        $(this).find('.righticon').toggleClass('fa-angle-left');
        $(this).find('.righticon').toggleClass('fa-angle-down');
        $(itemsToToggleClass).toggleClass("hidden");
    });
});


//Script for opening and closing side menu

var sideMenu = document.getElementById("myNav");
var sideMenuOpened = false;
var menuButton = document.getElementById("menuBtn");

function navHandler() {
    if (!sideMenuOpened) {
        sideMenuOpened = true;
        menuButton.src = "/img/closeMenu.png"
        openNav();
    } else {
        sideMenuOpened = false;
        menuButton.src = "/img/openMenu.png"
        closeNav()
    }
}

function openNav() {
    if (innerWidth < 650) {
        sideMenu.style.width = "100%";
    } else {
        sideMenu.style.width = "250px";
        document.getElementsByClassName("main")[0].style.marginLeft = "250px";
    }
}

function closeNav() {
    menuButton.src = "/img/openMenu.png"
    sideMenu.style.width = "0";
    document.getElementsByClassName("main")[0].style.marginLeft = 0;
    document.getElementsByClassName("main")[1].style.marginLeft = 0;

}


