//Redirect when click on bill

$('#timeline').on('click', '*[data-href]', function () {
    window.location = /bills/ + $(this).data("href");
})


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
    if (urlParams.get('ascendent') == "true") {
        document.getElementById("ascendent").checked = true;
    } else {
        document.getElementById("descendent").checked = true;
    }
}


// AJAX

var position="right";
var userRFC= "";
var requestNumber = 1;
var endResponse=false;

function initRFC(RFC){
    userRFC=RFC;
}

function handleScroll(elem){
    var elemScrollPosition = elem.scrollHeight - elem.scrollTop - elem.clientHeight;
    if(elemScrollPosition<=1 && !endResponse) loadNewBillPage();
}
function loadNewBillPage(){
    ajaxRequest("/loadbills?page="+requestNumber)
        .then(function(result) {
            requestNumber++;
            processResponse(result);
        })
        .catch(function(){
            //Error with AJAX request.
        })
}


function processResponse(jsonResponse){
    if (jsonResponse == '"End"') {
        endResponse=true;
    } else {
        var billsArray = JSON.parse(jsonResponse);
        billsArray.forEach(appendToTimeline)
    }
}

function appendToTimeline(bill){
    position= newPosition();
    var billContainer= createBillContainer(bill,getContainerColor(bill));
    $('#timeline').append(billContainer)
}


function createBillContainer(bill,color){
    return "<div class='billContainer "+ position + "'><div class='box " +  color + "' data-href='" + bill.UUID + "'>" +
        "<div><h2>"+bill.date+"</h2></div><div style='background-color: white; opacity:0.9'>" +
        "<h4>"+bill.concept+"</h4><div class='billContainerAmount'><h4>"+formatToCurrency(bill.total)+"</h4></div></div></div></div>";
}

function getContainerColor(bill){
    if (bill.type!="nomina" && bill.issuerRFC==userRFC) return "green";
    if (bill.type=="nomina" && bill.issuerRFC==userRFC) return "orange";
    if (bill.use=="G01" || bill.use=="G02" || bill.use=="G03") return "red";
    return "blue";
}

function newPosition(){
    if (position=="right"){
        return "left"
    } else{
        return "right";
    }
}

function formatToCurrency(amount){
    var formatter = new Intl.NumberFormat('de-DE', {
        style: 'currency',
        currency: 'EUR',
    });
    return formatter.format(amount);
}
