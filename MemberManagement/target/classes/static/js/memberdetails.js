$(document).ready(function () {
    addressInit('cmbProvince', 'cmbCity', 'cmbArea');
    var username="";
    var sex;
    var tel;
    var id;
    var password;
    var email;
    var invitecode;
    var registerdate;
    var address;


    //获取登录名
    $.ajax({
        type:"POST",
        url:"mc/getcookie",
        dataType:"json",
        success:function (json) {
            username = json.loginUserName;
            console.log(username);

            //通过登录名获取所有值
            $.getJSON("mc/getallbyname",{userName:username},function (data) {
                console.log(data);
                id = data[0].id;
                password = data[0].password;
                email = data[0].email;
                invitecode = data[0].inviteCode;
                registerdate = data[0].registerDate;
                //开始在页面显示数据库中数据
                $("#username").val(data[0].userName);

                if(data[0].sex == "男"){
                    $("#male").attr("selected","selected");
                    $("#female").removeAttr("selected");
                    $("#null").removeAttr("selected");
                }
                else if (data[0].sex == "女"){
                    $("#female").attr("selected","selected");
                    $("#male").removeAttr("selected");
                    $("#null").removeAttr("selected");
                }
                else{
                    $("#null").attr("selected","selected");
                    $("#male").removeAttr("selected");
                    $("#female").removeAttr("selected");
                }

                $("#tel").val(data[0].tel);
                //结束显示页面数据库中数据

                $("#submitbtn").click(function () {
                    //开始修改数据库中数据
                    if($("#male").is(":checked")){
                        sex = "男";
                    }
                    else if($("#female").is(":checked")){
                        sex = "女";
                    }
                    else{
                        sex = "";
                    }
                    username = $("#username").val();
                    tel = $("#tel").val();

                    $.getJSON("mc/update",{password:password, id:id,sex:sex,tel:tel,username:username,email:email,invitecode:invitecode,registerdate:registerdate},function (update) {
                        if(update.rs == "true"){
                            alert("修改成功");
                            window.location.href="leftandtop.html";
                        }
                        else{
                            alert("修改失败");
                        }
                    });

                });

                //结束修改数据库中数据
            });

            $("#addressdetailbtn").click(function () {
                //获取新增联系地址并存入数据库
                var connector = $("#connector").val();
                var connectortel = $("#connectortel").val();
                var cmbProvince = $("#cmbProvince").val();
                var cmbCity = $("#cmbCity").val();
                var cmbArea = $("#cmbArea").val();
                var addressdetail = $("#addressdetail").val();
                address = cmbProvince+cmbCity+cmbArea+addressdetail;

                var data = JSON.stringify({"userId":id,"tel":connectortel,"connector":connector,"address":address});
                $.ajax({
                    url:"ac/createaddressdetail",
                    async:false,
                    type:"POST",
                    contentType:'application/json',
                    dataType:'json',
                    data:data,
                    success:function (add) {
                        if(add.rs == "true"){
                            alert("保存成功");
                            $("#myModal").modal("hide");
                            $('.modal-backdrop').remove();
                        }
                    }
                });
            });

        }
    });
});