//Redirect when click on bill

jQuery(document).ready(function ($) {
    $('*[data-href]').on('click', function () {
        console.log("hey");
        window.location = /bills/ + $(this).data("href");
    })
});


const urlParams = new URLSearchParams(window.location.search);

function dateFromQuery() {
    if (urlParams.get('periodStart')) document.getElementById('pStart1').value = urlParams.get('periodStart');
    if (urlParams.get('periodEnd')) document.getElementById('pEnd1').value = urlParams.get('periodEnd');
}

function minMaxFromQuery() {
    document.getElementById('pMin').value = urlParams.get('min');
    document.getElementById('pMax').value = urlParams.get('max');
}

function billTypeFromQuery() {
    if (urlParams.get('incomes') == "true") document.getElementById("bIncomes").checked = true;
    if (urlParams.get('expenses') == "true") document.getElementById("bExpenses").checked = true;
    if (urlParams.get('investments') == "true") document.getElementById("bInvestments").checked = true;
    if (urlParams.get('salaries') == "true") document.getElementById("bSalaries").checked = true;

}

function orderFromQuery() {
    if (urlParams.get('ascendent') == true) {
        document.getElementById("ascendent").checked = true;
    } else {
        document.getElementById("descendent").checked = true;
    }
}
