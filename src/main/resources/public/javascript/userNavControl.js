var userPanel = $("#userPanel");
var userIcon = $("#userIcon");
var languageIcon = $("#languageIcon");
var languagePanel = $("#languagePanel");
var panel = $(".popover-content");

userIcon.click(function () {
    userPanel.css("visibility", "visible");
    return false;
});

languageIcon.click(function () {
    userPanel.css("visibility", "hidden");
    languagePanel.css("visibility", "visible");
    return false;
});


// When any place of the document is clicked, both panels are hidden.
$(document).click(function () {
    userPanel.css("visibility", "hidden");
    languagePanel.css("visibility", "hidden");
});

// When clicking inside any of the panels, panels won't hide

panel.click(function () {
    return false;
})

function submitLogOutForm() {
    document.getElementById("logOutForm").submit();

}

function submitLanguageForm(language) {
    if (language == 'es') document.getElementById("esLanguageForm").submit();
    if (language == 'en') document.getElementById("enLanguageForm").submit();
}