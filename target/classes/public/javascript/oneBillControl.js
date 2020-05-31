function handleEmailSend(billUUID){
    jQuery(document).ready(function ($) {
        $("#send").on('click', function () {
            var str = billUUID;
            window.location = /bills/ + str + "/" + document.getElementById("email").value + "/";
        });
    });
}
