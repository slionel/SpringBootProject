$(document).ready(function () {
    $.ajax({
        type:"POST",
        url:"getlogin",
        dataType:"json",
        success:function (json) {
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