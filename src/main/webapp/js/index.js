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
            type: "get",
            data: {
                username: username,
                password: password
            },
            success: function (results) {
                if (results != null && results != "") {
                    showMessage(results);
                    $('#messageDiv').css("display", "block");
                    $('#divId').css("display", "none");
                    $('#ifLoginBlock').css("display", "block");
                    alert("You are successfully logged in.");
                } else {
                    $('#messageDiv').css("display", "none");
                    $('#messageDiv').html("");
                    alert("Some exception occurred! Please try again.");
                }
            }
        });
    });
    $("#logoutHref").on('click', function () {
        $.ajax({
            url: "logout",
            type: "get",
            success: function (results) {
                if (results === "SUCCESS") {
                    alert("You are successfully logged out.");
                    $.ajax({
                        url: "/index.xhtml",
                        type: "get"
                    });
                }
            }
        });
    });
    //function to display message to the user
    function showMessage(results) {
        if (results == 'SUCCESS') {
            $('#messageDiv').html("<p style='color: green'>You are successfully logged in. </p>")
        } else if (results == 'FAILURE') {
            $('#messageDiv').html("<p style='color: red'>Username or password incorrect </p>")
        }
    }
});
