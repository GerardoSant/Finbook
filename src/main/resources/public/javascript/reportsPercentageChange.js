function addPercentageChangeToTable(){
    var table = document.getElementById("myTable");
    for (var i = 1, row; row = table.rows[i]; i++) {
        var base1= parseFloat(row.cells[1].innerText);
        var base2= parseFloat(row.cells[2].innerText);
        var percentage= ((base2-base1)/base1)*100;
        if (percentage=="Infinity" || percentage=="-Infinity") {
            percentage= "-" ;
        } else{
            percentage= percentage.toFixed(2);
            if (percentage>=0) percentage="+" +percentage
        }
        if (percentage=="NaN") percentage= 0;
        row.cells[3].innerText = percentage + " %";
    }
}

