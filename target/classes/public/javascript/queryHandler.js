function getFirstPeriodString(){
    return getFirstPeriodStart() + " - \n" + getFirstPeriodEnd();
}

function getSecondPeriodString(){
    return getSecondPeriodStart() +  " - \n" + getSecondPeriodEnd();
}

function getFirstPeriodStart(){
    return getQueryParam("periodStart");
}

function getFirstPeriodEnd(){
    return getQueryParam("periodEnd");
}

function getSecondPeriodStart(){
    return getQueryParam("periodStart1");
}
function getSecondPeriodEnd(){
    return getQueryParam("periodEnd1");
}

function getQueryParam(param){
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}