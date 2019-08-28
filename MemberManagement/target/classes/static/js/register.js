$(document).ready(function () {
    var username;
    var email;
    var password;
    var repassword;
    var invitecode;
    var grade;
    var inviterId;

    $("#registerbtn").click(function(){
        if(username.length == 0 || username == null || username == undefined || username == ""){
            $("#registerbtn").attr("disabled","none");
        }
        if(email.length == 0 || email == null || email == undefined || email == ""){
            $("#registerbtn").attr("disabled","none");
        }
        if(password.length == 0 || password == null || password == undefined || password == "" || password.length > 20){
            $("#registerbtn").attr("disabled","none");
        }
        if(repassword.length == 0 || repassword == null || repassword == undefined || repassword == "" || repassword.length > 20){
            $("#registerbtn").attr("disabled","none");
        }
        if(!$("#agree").is(':checked')){
            $("#registerbtn").attr("disabled","none");
        }


        $.getJSON("mc/register",{baseUsername:username,inviteCode:invitecode,baseEmail:email,basePassword:password,grade:grade, inviterId:inviterId},function (json) {
            console.log(json);
            if(json.message == "success"){
                window.location.href = "login.html";
            }
            else{
                alert("注册失败");
                window.location.href = "register.html";
            }
        });
    });

    //用户名输入判断
    $("#username").blur(function () {
        username = $("#username").val();
        if(username.length == 0 || username == null || username == undefined || username == ""){
            $("#usernamenullwarn").show();
            $("#registerbtn").attr("disabled","none");
        }
        else{
            $("#usernamenullwarn").hide();
            $("#registerbtn").removeAttr("disabled");
        }
    });

    //邮箱输入判断
    $("#email").blur(function(){
        email = $("#email").val()
        var myreg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/;
        if(email.length == 0 || email == null || email == undefined || email == ""){
            $("#emailnullwarn").show();
            $("#registerbtn").attr("disabled","none");
        }
        else {
            $("#emailnullwarn").hide();
            if(!myreg.test(email)){
                $("#emailwarn").show();
            }
            else{
                $("#emailwarn").hide();
                $("#registerbtn").removeAttr("disabled");
            }
        }
    });

    //验证密码是否为空
    $("#password").blur(function () {
        password = $("#password").val();
        if(password.length == 0 || password == null || password == undefined || password == "" || password.length > 20){
            $("#pwdlengthwarn").show();
            $("#registerbtn").attr("disabled","none");
        }
        else {
            $("#pwdlengthwarn").hide();
            $("#registerbtn").removeAttr("disabled");
        }
    });

    //验证两次密码是否相同
    $("#repassword").blur(function () {
        repassword = $("#repassword").val();
        if(repassword != password){
            $("#repwdwarn").show();
            $("#registerbtn").attr("disabled","none");
        }
        else{
            $("#repwdwarn").hide();
            $("#registerbtn").removeAttr("disabled");
        }
    });

    //验证是否选中同意协议
    $("#agree").blur(function () {
        if($("#agree").is(':checked')){
            $("#registerbtn").removeAttr("disabled");
        }
        else {
            $("#registerbtn").attr("disabled","none");
        }
    });

    //邀请码
    $("input[name='inviteradio']").click(function(){
        if($("input[name='inviteradio'][type='radio']:checked").val() == "有"){
            $("#invitecode").removeAttr("disabled");
            $("#invitecode").blur(function () {
                var invitecodeval = $("#invitecode").val();
                $.get("mc/findbyinvitecodeval?invitecodeval="+invitecodeval,function (json) {
                    grade = 1;
                    inviterId = json.id;
                    var inviterGrade = json.grade + 1;
                    $.get("mc/updatemembergrade?id="+inviterId+"&grade="+inviterGrade,function (json){

                    });
                });
            });

        }
        else if ($("input[name='inviteradio'][type='radio']:checked").val() == "无"){
            grade = 1;
            $("#invitecode").attr("disabled","disabled");
            inviterId = null;
        }
    });

    if ($("input[name='inviteradio'][type='radio']:checked").val() == "无"){
        grade = 1;
        $("#invitecode").attr("disabled","disabled");
        inviterId = null;
    }


});