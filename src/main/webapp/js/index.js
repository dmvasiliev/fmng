$(document).ready(function () {
    $("#login").on('click', function () {
        var username = $("#username").val();
        var password = $("#password").val();
        if (username == "") {
            $('#messageDiv').css("display", "none");
            alert("Username is required");
            return;
        }
        if (password == "") {
            $('#messageDiv').css("display", "none");
            alert("Password is required");
            return;
        }

        $.ajax({
            url: "login",
            type: "post",
            data: {
                username: username,
                password: password
            },
            success: function (results) {
                if (results != null && results != "") {
                    if (results === "SUCCESS") {
                        $('#divId').css("display", "none");
                        $('#ifLoginBlock').css("display", "block");
                        alert("You are successfully logged in.");
                    }else {
                        if (results === "FAILURE") {
                            alert("Username or Password incorrect.");
                        }
                    }
                } else {
                    alert("Some exception occurred! Please try again.");
                }
            }
        });
    });
    $("#logoutHref").on('click', function () {
        logout();
    });
});

function logout() {
    $.ajax({
        url: "logout",
        type: "get",
        success: function (results) {
            if (results === "SUCCESS") {
                alert("You are successfully logged out.");
                $('#divId').css("display", "block");
                $('#ifLoginBlock').css("display", "none");
            }
        }
    });
}
