$(document).ready(function () {
    //设置页面用户名显示
    $.ajax({
        type:"POST",
        url:"mc/getcookie",
        dataType:"json",
        success:function (json) {
            var username = json.loginUserName;
            console.log(username);
            $("#loginusername").text(username);
        }
    });


    $("#main").load("index.html");
    $("#index").click(function () {
        $("#main").load("index.html");
    });
    $("#memberdetails").click(function () {
        $("#main").load("memberdetails.html");
    });
});