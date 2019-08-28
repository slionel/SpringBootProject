$(document).ready(function () {
    addressInit('cmbProvince', 'cmbCity', 'cmbArea');
    var username="";
    var sex;
    var tel;
    //id是userId
    var id;
    var password;
    var email;
    var invitecode;
    var registerdate;
    var address;
    var grade;
    var inviterId;

    //获取登录名
    $.ajax({
        type:"POST",
        url:"getlogin",
        dataType:"json",
        success:function (json) {
            username = json.userName;

            //通过登录名获取所有值
            $.getJSON("mc/getallbyname",{userName:username},function (data) {
                console.log(data);
                id = data[0].id;
                password = data[0].password;
                email = data[0].email;
                invitecode = data[0].inviteCode;
                registerdate = data[0].registerDate;
                grade = data[0].grade;
                inviterId = data[0].inviterId;
                //开始在页面显示数据库中数据
                $("#username").val(data[0].userName);
                $("#myinvitecode").text(invitecode);

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

                //开始修改数据库中数据
                $("#submitbtn").click(function () {
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

                    $.getJSON("mc/update",{id:id,sex:sex,tel:tel},function (update) {
                        if(update.rs == "1"){
                            alert("修改成功");
                            window.location.href="leftandtop.html";
                        }
                        else{
                            alert("修改失败");
                        }
                    });

                });
                //结束修改数据库中数据

                getdata();


                //判断是否已经填写过邀请码
                if(inviterId.length != 0 && inviterId != ""){
                    $("#writeinvitecode").val("邀请码只能输入一次");
                    $("#writeinvitecode").attr("disabled","disabled");
                    $("#coderesetbtn").attr("disabled","disabled");
                    $("#codesubmitbtn").attr("disabled","disabled");
                }
                else{
                    $("#codesubmitbtn").click(function () {
                        var writeInviteCode = $("#writeinvitecode").val();
                        $.get("mc/findbyinvitecodeval?invitecodeval="+writeInviteCode,function (json) {
                            console.log(json);
                            inviterId = json.id;
                            $.get("mc/updatememberinviterid?inviterId="+inviterId+"&id="+id,function (json) {
                                if(json.rs == 1){
                                    alert("邀请码添加成功");
                                    $.get("mc/findbyinvitecodeval?invitecodeval="+writeInviteCode,function (json) {
                                        inviterId = json.id;
                                        var inviterGrade = json.grade + 1;
                                        $.get("mc/updatemembergrade?id="+inviterId+"&grade="+inviterGrade,function (json){

                                        });
                                    });
                                }
                            });
                        });
                    });
                }



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
                            getdata();
                            alert("保存成功");
                            $("#myModal").modal("hide");
                            $('.modal-backdrop').remove();
                        }
                    }
                });
            });
        }
    });




    //按照userId获取数据库中地址信息
    function getdata(){
        $.getJSON("ac/getaddressdetail",{userId:id},function (json) {
            $("#addressdetailtbody").empty();
            for(var i = 0; i < json.length; i++){
                $("#addressdetailtbody").append(
                    "<tr>" +
                    "<td>"+json[i].connector+"</td>" +
                    "<td>"+json[i].tel+"</td>" +
                    "<td>"+json[i].address+"</td>" +
                    "<td><button type='button' data-toggle='modal' data-target='#editModal' class='btn btn-primary' name='editaddressdetailbtn' id='btn"+json[i].id+"'>编辑</button></td>" +
                    "<td><button type='button' class='btn btn-danger' name='deladdressdetailbtn' id='delbtn"+json[i].id+"'>删除</button></td>" +
                    "</tr>"
                );
            }
            $("button[name='editaddressdetailbtn']").click(function () {
                //通过联系地址详情id寻找对应数据并显示在模态框中
                var btnid = this.id;
                var addressId = btnid.substr(3);
                $.getJSON("ac/getaddressdetailbyaddressid",{addressId:addressId},function (json) {
                    $("#editconnector").val(json.connector);
                    $("#editconnectortel").val(json.tel);
                    $("#editaddress").val(json.address);
                });

                $("#saveaddressdetailbtn").click(function () {
                    $.getJSON("ac/updateaddressdetail",{addressId:addressId,tel:$("#editconnectortel").val(),connector:$("#editconnector").val(),address:$("#editaddress").val()},function (json) {
                        if(json.rs == 1){
                            alert("修改成功");
                            getdata();
                            $("#editModal").modal("hide");
                            $('.modal-backdrop').remove();
                        }
                    });
                });
            });

            //删除详细地址信息
            $("button[name='deladdressdetailbtn']").click(function () {
                var btnid = this.id;
                var addressId = btnid.substr(6);

                $.get("ac/deleteaddressdetail?addressId="+addressId,function (json) {
                    if(json.rs == 1){
                        alert("删除成功");
                        getdata();
                    }
                });
            });

        });
    }
});