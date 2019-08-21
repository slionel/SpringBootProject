$(document).ready(function(){
    var username;
    var email;
    var password = $("#password").val();
    var invitecode = $("#invitecode").val();

    //注册按钮点击事件
    $("#registerbtn").click(function(){

        //base64加密
        var baseUsername = Base64.encode(username);
        var basePassword = Base64.encode(password);
        var baseEmail = Base64.encode(email);

        //调用controller注册
        $.getJSON("mc/register",{baseUsername:baseUsername, baseEmail:baseEmail, basePassword:basePassword,inviteCode:invitecode}, function (json) {
            if(json.message+"" == "success"){
                window.location.href="login.html";
            }
        });
    });

    //验证用户名是否已被注册
    $("#username").blur(function(){
        username = $("#username").val();
        console.log("username:"+username);
        var baseUsername = Base64.encode(username);
        console.log("baseUsername:"+baseUsername);
        $.getJSON("mc/find"+baseUsername,{},function (json) {
            console.log("999999999")
            console.log(json);
            if(json.rs == "true"){
                $("usernamewarn").show();
                $("#username").focus();
            }
            else{
                $("usernamewarn").hide();
            }
        })
    });

    //验证邮箱格式是否正确
    $("#email").blur(function(){
        email = $("#email").val()
        var myreg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/;
        if(!myreg.test(email)){
            $("#emailwarn").show();
            $("input[name='email']").focus();
        }
        else{
            $("#emailwarn").hide();
        }
    });

    //验证密码是否为空
    $("#password").blur(function () {
        if($("#password").val().length == 0){
            $("#pwdwarn").show();
            $("#password").focus();
        }
        else {
            $("#pwdwarn").hide();
        }
    });

    //验证两次密码是否相同
    $("#repassword").blur(function () {
        if($("#repassword").val() != $("#password").val()){
            $("#repwdwarn").show();
            $("#repassword").focus();
        }
        else{
            $("#repwdwarn").hide();
        }
    });

    //邀请码
    $("input[name='inviteradio']").click(function(){
        //邀请码
        if($("input[name='inviteradio'][type='radio']:checked").val() == "有"){
            console.log("you");
            $("#invitecode").removeAttr("disabled");
        }
        else if ($("input[name='inviteradio'][type='radio']:checked").val() == "无"){
            console.log("wu");
            $("#invitecode").attr("disabled","disabled");
        }
    });

    //邮箱验证函数
    function emailValidate(email){
        var myreg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/;
        if(!myreg.test(email)){
            alert("请输入有效的邮箱地址");
            return false;
        }
        else{
            return true;
        }
    }

    //base64
    var Base64 = {
        _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
        encode: function(e) {
            var t = "";
            var n, r, i, s, o, u, a;
            var f = 0;
            e = Base64._utf8_encode(e);
            while (f < e.length) {
                n = e.charCodeAt(f++);
                r = e.charCodeAt(f++);
                i = e.charCodeAt(f++);
                s = n >> 2;
                o = (n & 3) << 4 | r >> 4;
                u = (r & 15) << 2 | i >> 6;
                a = i & 63;
                if (isNaN(r)) {
                    u = a = 64
                } else if (isNaN(i)) {
                    a = 64
                }
                t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o) + this._keyStr.charAt(u) + this._keyStr.charAt(a)
            }
            return t
        },
        decode: function(e) {
            var t = "";
            var n, r, i;
            var s, o, u, a;
            var f = 0;
            e = e.replace(/[^A-Za-z0-9+/=]/g, "");
            while (f < e.length) {
                s = this._keyStr.indexOf(e.charAt(f++));
                o = this._keyStr.indexOf(e.charAt(f++));
                u = this._keyStr.indexOf(e.charAt(f++));
                a = this._keyStr.indexOf(e.charAt(f++));
                n = s << 2 | o >> 4;
                r = (o & 15) << 4 | u >> 2;
                i = (u & 3) << 6 | a;
                t = t + String.fromCharCode(n);
                if (u != 64) {
                    t = t + String.fromCharCode(r)
                }
                if (a != 64) {
                    t = t + String.fromCharCode(i)
                }
            }
            t = Base64._utf8_decode(t);
            return t
        },
        _utf8_encode: function(e) {
            e = e.replace(/rn/g, "n");
            var t = "";
            for (var n = 0; n < e.length; n++) {
                var r = e.charCodeAt(n);
                if (r < 128) {
                    t += String.fromCharCode(r)
                } else if (r > 127 && r < 2048) {
                    t += String.fromCharCode(r >> 6 | 192);
                    t += String.fromCharCode(r & 63 | 128)
                } else {
                    t += String.fromCharCode(r >> 12 | 224);
                    t += String.fromCharCode(r >> 6 & 63 | 128);
                    t += String.fromCharCode(r & 63 | 128)
                }
            }
            return t
        },
        _utf8_decode: function(e) {
            var t = "";
            var n = 0;
            var r = c1 = c2 = 0;
            while (n < e.length) {
                r = e.charCodeAt(n);
                if (r < 128) {
                    t += String.fromCharCode(r);
                    n++
                } else if (r > 191 && r < 224) {
                    c2 = e.charCodeAt(n + 1);
                    t += String.fromCharCode((r & 31) << 6 | c2 & 63);
                    n += 2
                } else {
                    c2 = e.charCodeAt(n + 1);
                    c3 = e.charCodeAt(n + 2);
                    t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
                    n += 3
                }
            }
            return t
        }
    }

});