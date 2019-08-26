$(document).ready(function () {
    //设置页面用户名显示
    /*$.post("getlogin",function (data) {
        console.log(data);
        $("#loginusername").text(data.userName);
    },"json");*/

    $.ajax({
        type:"POST",
        url:"getlogin",
        dataType:"json",
        success:function (json) {
            console.log("leftandtop::::"+json);
            $("#loginusername").text(json.userName);
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