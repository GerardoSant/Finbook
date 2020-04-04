function openWebSocket(textToSign){
    var textToSign=  textToSign;
    console.log(textToSign);
    let socket = new WebSocket("ws://localhost:8080/echo");

    socket.onopen = function(e) {
    };

    socket.onmessage = function(e){
        if(JSON.parse(e.data).id==textToSign){

            submitLogInForm();
        }
    }
    socket.onclose = function(e){
        alert("session closed")
    }

}

function submitLogInForm(){
    document.getElementById("logInForm").submit();
}