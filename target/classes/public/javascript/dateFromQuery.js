console.log("hey");

function dateFromQueryToModal(){
    var arr = location.search.substring(0).split("&");
    document.getElementById('pStart1').value = arr[0].split("=")[1];
    document.getElementById('pEnd1').value = arr[1].split("=")[1];
    document.getElementById('pStart2').value = arr[2].split("=")[1];
    document.getElementById('pEnd2').value = arr[3].split("=")[1];
}