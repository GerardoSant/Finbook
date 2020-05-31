function addPercentageChangeToTable(){
    var table = document.getElementById("myTable");
    for (var i = 1, row; row = table.rows[i]; i++) {
        var base1= getNumericValue(row.cells[1].innerText);
        var base2= getNumericValue(row.cells[2].innerText);
        var percentage= ((base2-base1)/base1)*100;
        if (percentage=="Infinity" || percentage=="-Infinity") {
            percentage= "-" ;
        } else{
            percentage= percentage.toFixed(2);
            if (percentage>=0) percentage="+" +percentage
        }
        if (percentage=="NaN") percentage= "+0.00";
        row.cells[3].childNodes[0].innerText = percentage + " %";
    }
    calculateBenefitsChange();
}

function calculateBenefitsChange(){
    var benefitsRow= document.getElementById("totalProfit");
    if (benefitsRow!=null){
        adjustBenefitsChange(benefitsRow)
    }
}

function adjustBenefitsChange(benefitsRow){
    if (benefitsRowCellValue(1)<0 && benefitsRowCellValue(2)>0) {
        benefitsRow.cells[3].childNodes[0].innerText = "+" + Math.abs(benefitsRowCellValue(3))/100 + " %";
    }
    if (benefitsRowCellValue(1)<0 && benefitsRowCellValue(2)<0) {
        if(benefitsRow.cells[3].childNodes[0].innerText.indexOf("+")!=-1){
            benefitsRow.cells[3].childNodes[0].innerText = benefitsRow.cells[3].childNodes[0].innerText.replace("+","-");
        } else{
            benefitsRow.cells[3].childNodes[0].innerText = benefitsRow.cells[3].childNodes[0].innerText.replace("-","+");
        }
    }
}

function benefitsRowCellValue(cellNumber){
    var benefitsRow= document.getElementById("totalProfit");
    return getNumericValue(benefitsRow.cells[cellNumber].childNodes[0].innerText)
}

function getNumericValue(number){
    return parseFloat(parseCurrency(number));
}



function parseCurrency(string){
    var newStr= string.replace(".","").replace(",",".");
    return newStr;
}

