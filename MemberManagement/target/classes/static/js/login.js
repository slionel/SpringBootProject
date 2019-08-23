$(document).ready(function () {
    $("#loginbtn").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var baseUsername = username;
        var basePassword = password;
        console.log(baseUsername);
        console.log(basePassword);
        $.getJSON("mc/login",{baseUsername:baseUsername,basePassword:basePassword},function(json){
            console.log(json);
            if(json.rs == "true"){
                window.location.href="leftandtop.html";
            }
            else {
                alert("用户名或密码不正确");
                window.location.href="login.html";
            }
        });
    });



});