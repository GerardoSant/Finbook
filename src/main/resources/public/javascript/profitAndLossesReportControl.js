function redirectToSeeMoreBills(field){
    var query;
    if (field=="grossSales"){
        query='&billType=income'
    }
    if(field=="salesReturns"){
        query='&billType=egress'
    }
    if (field=="grossPurchases"||field=="purchasesReturns"){
        query='&class=received&billType=purchases'
    }
    if (field=="externalServices"){
        query='&class=received&billType=services'
    }
    if (field=="salariesAndWages"){
        query='&billType=payroll'
    }
    window.location ="/bills" + "?redirected=true"+ "&periodStart=" + document.getElementById('pStart').value + "&" + "periodEnd=" + document.getElementById('pEnd').value+query;
}