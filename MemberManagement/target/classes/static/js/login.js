$(document).ready(function () {
    $("#loginbtn").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var baseUsername = username;
        var basePassword = password;
        var rememberMe;
        if($("#remembersel").is(":checked")){
            rememberMe = true;
        }
        else {
            rememberMe = false;
        }


        $.post("login",{baseUsername:baseUsername,basePassword:basePassword,rememberMe:rememberMe},function(json){
            if(json.code == 0){
                window.location.href="leftandtop.html";
            }
            else{
                alert("用户名或密码错误");
                window.location.href="login.html";
            }
        });
    });



});