function ajaxRequest(requestUrl){
    return new Promise(function(resolve,reject){
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                resolve(this.responseText)
            }
        };
        xhttp.open("POST", requestUrl, true);
        xhttp.send();
    })
}