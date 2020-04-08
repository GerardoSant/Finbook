var messageBundle;

function initMessageBundle(map){
    messageBundle=map;
}




//       -----   ANIMATIONS  -------



function allowDrop(ev) {
    ev.preventDefault();
}


function animateOnDrop() {
    $(document).ready(function () {
        $('#dropContainer').animate({backgroundColor: '#add8e6'}, 1000);
        $('#uploadForm').animate({borderBottomColor: '#90ee90'}, 1000);
        $('#dragAndDropText').animate({
            'opacity': 0
        }, 400, function () {
            $(this).html(messageBundle.get("UPLOAD_FILESATTACHED")+ '<br> '+messageBundle.get("UPLOAD_PRESSSTART")).animate({'opacity': 1}, 400);
        });
    });
}




$(document).ready(function () {
    $("#dropContainer").on("dragover", function () {
        $(this).css('background-color', '#a9a9a9');
    });

    $("#fileInput").on("change", function () {
        $('#uploadForm').animate({borderBottomColor: '#90ee90'}, 1000);
        $('#dropContainer').animate({backgroundColor: '#add8e6'}, 1000);
        $('#dragAndDropText').animate({
            'opacity': 0
        }, 400, function () {
            $(this).html(messageBundle.get("UPLOAD_FILESATTACHED")+ '<br> '+messageBundle.get("UPLOAD_PRESSSTART")).animate({'opacity': 1}, 400);
        });
    });

    $("#dropContainer").on("dragleave", function () {
        $(this).animate({backgroundColor: '#FFFFFF'}, 0);
    });
    $("#modalClose").on("click", function () {
        $('#dropContainer').animate({backgroundColor: 'rgb(247,250,252)'}, 0);
        $('#uploadForm').animate({borderBottomColor: '#FFFFFF'}, 0);
        $('#dragAndDropText').animate({
            'opacity': 0
        }, 400, function () {
            $(this).html(messageBundle.get("UPLOAD_MSG")).animate({'opacity': 1}, 400);
        });
        document.getElementById("progressdiv").style.visibility = "hidden";
        document.getElementById("fileInput").value = "";
    });

    $("#startUploadButton").on("click", function () {
        $('#dropContainer').animate({backgroundColor: '#add8e6'}, 0);
        $('#dragAndDropText').animate({
            'opacity': 0
        }, 400, function () {
            $(this).html(messageBundle.get("UPLOAD_UPLOADING")).animate({'opacity': 1}, 400);
        });
    });
});



function initInterface() {
    document.getElementById("right").style.visibility = "hidden";
    $(document).ready(function () {
        $("#right").hide(0);
    });
}

initInterface();

function doFirstUploadInterfaceTransition() {
    document.getElementById("left").classList.add("leftbox")
    document.getElementById("right").style.visibility = "visible";
    $(document).ready(function () {
        $("#right").show(1000);
    });
    $(document).ready(function () {
        document.getElementById("left").classList.add("half");
    });
}




// AJAX and file input handlers

function dropFiles(ev) {
    for (var i = 0; i < ev.dataTransfer.files.length; i += 1) {
        if (ev.dataTransfer.files[i].type != "text/xml") {
            ev.preventDefault();
            openModalError();
            return;
        }
    }
    fileInput.files = ev.dataTransfer.files;
    animateOnDrop();
    ev.preventDefault();
}

function openModalError() {
    document.getElementById("modalTitle").innerText = "Error";
    document.getElementById("modalContent").innerText = messageBundle.get("UPLOAD_NOTXML");
    $(document).ready(function () {
        $("#myModal").modal()
    });
}


var initInterface = true;

var loadBtn = document.getElementById("startUploadButton");

loadBtn.addEventListener("click", function (e) {
    this.disabled = true;
    this.innerHTML = "Uploading...";
    upload(buildFormData());
});

function upload(data) {
    document.getElementById("progressdiv").style.visibility = "visible";

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/upload", true);
    if (xhr.upload) {
        xhr.upload.onprogress = function (e) {
            if (e.lengthComputable) {

                document.getElementById("progressbar").style.width = ((e.loaded / e.total) * 100) + "%";
                document.getElementById("progressbar").innerHTML = Math.round(((e.loaded / e.total) * 100)) + "%";
            }
        }
        xhr.upload.onloadstart = function (e) {
            document.getElementById("progressbar").style.width = 0 + "%";
            document.getElementById("progressbar").innerHTML = 0 + "%";
        }
        xhr.upload.onloadend = function (e) {
            loadBtn.disabled = false;
            loadBtn.innerHTML = 'Start uploading';
            document.getElementById("progressbar").innerHTML = "Completed";
        }
        xhr.onreadystatechange = function () {
            if (this.readyState == 4) {
                $(document).ready(function () {
                    $("#myModal").modal()
                });
                if (initInterface) {
                    doFirstUploadInterfaceTransition();
                    initInterface = false;
                }
                processResponseText(this.responseText);
                document.getElementById("modalContent").innerText = splitResponseText(this.responseText);
            }
        }
    }


    xhr.send(data);
}

function buildFormData() {
    var fd = new FormData();

    for (var i = 0; i < fileInput.files.length; i += 1) {
        fd.append("sampleFile", fileInput.files[i]);
    }

    return fd;
}



function processResponseText(responseText) {
    var responses = responseText.split('|');
    addBadgeUploadInfo(responses);
    for (var i = 0; i < responses.length; i++) {
        var responseSplit = responses[i].split(":");
        console.log(responseSplit[0]);
        console.log(responseSplit[1]);
        if (responseSplit[1] == 'Yes') {
            appendOnTable(responseSplit[0], messageBundle.get("UPLOAD_SUCCESSFUL"), "Uploaded", true, true);
        }
        if (responseSplit[1] == 'Already') {
            appendOnTable(responseSplit[0], messageBundle.get("UPLOAD_ALREADY"), "Already", false, true);
        }
        if (responseSplit[1] == 'Invalid') {
            appendOnTable(responseSplit[0], messageBundle.get("UPLOAD_ERROR"), "Invalid", false);
        }

    }
}

function appendOnTable(filename, content, status, valid, already) {
    $(document).ready(function () {
        if (valid) {
            var popover = '<a style="color:darkgreen" href="#" data-toggle="popover" data-placement="bottom" data-trigger="hover" title="'+messageBundle.get("UPLOAD_UPLOADINFO")+'" data-content="' + content + '">' + filename + '</a>'
            var append = '<tr class="table-success"><td>' + popover + '</td><td>' + '<div class="text-center"><i style="color:darkgreen" class="fas fa-check"></i></div>' + '</td></tr>';
        } else {

            if (already) {
                var popover = '<a style="color:rgb(156,101,36);" href="#" data-toggle="popover" data-placement="bottom" data-trigger="hover" title="'+messageBundle.get("UPLOAD_UPLOADINFO")+'" data-content="' + content + '">' + filename + '</a>'
                var append = '<tr class="table-warning"><td>' + popover + '</td><td>' + '<div class="text-center"><i style="color:rgb(166,101,36)" class="fas fa-exclamation-circle"></i></div>' + '</td></tr>';
            } else {
                var popover = '<a style="color:darkred" href="#" data-toggle="popover" data-placement="bottom" data-trigger="hover" title="'+messageBundle.get("UPLOAD_UPLOADINFO")+'" data-content="' + content + '">' + filename + '</a>'
                var append = '<tr class="table-danger"><td>' + popover + '</td><td>' + '<div class="text-center"><i style="color:darkred" class="fas fa-times"></i></div>' + '</td></tr>';
            }

        }
        $('#fileTable').find('tbody:last').append(append);
        $('[data-toggle="popover"]').popover();
    });
}

function addBadgeUploadInfo(response) {
    var uploads = response.length;
    document.getElementById("fileUploadsNum").title = uploads + " files uploaded";
    document.getElementById("fileUploadsNum").innerHTML = uploads;
}

function splitResponseText(responseText) {
    var res = responseText.split('||');
    var fixedString = "";
    for (var i = 0; i < res.length; i++) {
        fixedString += res[i] + "\n";
    }
    return fixedString;
}


$(document).ready(function () {
    $('[data-toggle="popover"]').popover();
});
