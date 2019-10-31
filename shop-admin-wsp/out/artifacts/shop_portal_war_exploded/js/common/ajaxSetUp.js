var loginFlag = false;

$(function () {
    var v_html = "<nav class=\"navbar navbar-inverse\">\n" +
        "    <div class=\"container-fluid\">\n" +
        "        <div class=\"navbar-header\">\n" +
        "            <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\"\n" +
        "                    data-target=\"#bs-example-navbar-collapse-1\" aria-expanded=\"false\">\n" +
        "                <span class=\"sr-only\">Toggle navigation</span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "            </button>\n" +
        "            <a class=\"navbar-brand\" href=\"#\">明夜黑商站</a>\n" +
        "        </div>\n" +
        "\n" +
        "        <!-- Collect the nav links, forms, and other content for toggling -->\n" +
        "        <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">\n" +
        "            <ul class=\"nav navbar-nav\">\n" +
        "                <li class=\"dropdown\">\n" +
        "                    <a href='index.html'>首页</a>\n" +
        "                </li>\n" +
        "            </ul>\n" +
        "            <ul class=\"nav navbar-nav navbar-right\">\n" +
        "                <li class=\"dropdown\">\n" +
        "                    <a href=\"#\" ></a>\n" +
        "                </li>\n" +
        "                <li class=\"dropdown\" id=\"memberNameLi\">\n" +
        "                    <a href=\"login.html\">登录</a>\n" +
        "                </li>\n" +
        "                <li class=\"dropdown\" id=\"logoutLi\">\n" +
        "\n" +
        "                </li>\n" +
        "                <li class=\"dropdown\">\n" +
        "                    <a href=\"reg.html\">注册</a>\n" +
        "                </li>\n" +
        "                <li class=\"dropdown\">\n" +
        "                    <a href=\"cart-student.html\">购物车</a>\n" +
        "                </li>\n" +
        "            </ul>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "</nav>";
    $("#navDiv").html(v_html);

    $.ajaxSetup({
        beforeSend:function(request){
            var key = $.cookie("key");
            request.setRequestHeader("x-auth",key);
        }
    })

    $.ajax({
        url:"http://127.0.0.1:8080/members/findMember",
        type:"get",
        async:false,
        success:function(res){
            if(res.code==200){
                loginFlag = true;
                var data = res.data
                $("#memberNameLi").html('<a href="#">欢迎用户：'+data.realName+' 登录!!'+'</a>');
                $("#logoutLi").html(' <a href="#" onclick="logout()">退出</a>');
            }else{
                loginFlag = false;
            }
        }
    })

});
//退出登录
function logout(){
    $.ajax({
        url:"http://127.0.0.1:8080/members/logout",
        type:"get",
        success:function(res){
            if(res.code==200){
                $.removeCookie("key");
                location.reload();
            }
        }
    })
}

var loginDiv =
    '    <form class="form-horizontal">\n' +
    '        <div class="form-group">\n' +
    '            <label class="col-sm-2 control-label">会员名</label>\n' +
    '            <div class="col-sm-4">\n' +
    '                <input type="text" class="form-control" id="memberName" placeholder="请输入会员名称...">\n' +
    '            </div>\n' +
    '        </div>\n' +
    '        <div class="form-group">\n' +
    '            <label class="col-sm-2 control-label">密码</label>\n' +
    '            <div class="col-sm-4">\n' +
    '                <input type="password" class="form-control" id="password" placeholder="请输入密码...">\n' +
    '            </div>\n' +
    '        </div>\n' +
    '    </form>\n';

//购买
var loginBox;
function addCartItem(productId,obj){
    var o = $(obj).text();
    var count = 1;
    if(o=="-"){
        count = -1;
    }
    if(loginFlag){
        $.ajax({
            url:"http://127.0.0.1:8080/carts",
            data:{
                productId:productId,
                count:count
            },
            type:"post",
            success:function(res){
                if(res.code==200){
                    var ji = $("[name='ji']").val();
                    if(o){
                        findCart();
                    }else{
                        location.href = "/cart-student.html";
                    }
                }
        }
        })
    }else{
        loginBox = bootbox.dialog({
            title: '用户登录',
            message: loginDiv,
            size: 'large',
            backdrop:false,
            buttons: {
                confirm: {
                    label: "确定",
                    className: 'btn-primary',
                    callback: function () {
                        login(productId);
                    }
                },
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function () {
                    }
                }
            }
        });
        $("#login").html(loginDiv);
    }
}
//登录
function login(productId){
    var memberName = $("#memberName",loginBox).val();
    var password = $("#password",loginBox).val();
    $.ajax({
        url:"http://127.0.0.1:8080/members/login",
        data:{
            memberName:memberName,
            password:password,
            productId:productId
        },
        type:"post",
        success:function(res){
            if(res.code==200){
                loginFlag = true;
                var data = res.data;
                $.cookie("key",data);
                if(productId){
                    addCartItem(productId);
                }else{
                    location.href = "/";
                }
            }
        }
    })
}
//发送验证码
var time;
var j = 60;
function auth(key){
    var phone = $("#phone").val();
    if(phone){
        $.ajax({
            url:"http://127.0.0.1:8080/sms/"+phone+"/"+key,
            type:"get",
            success:function(res){
                if(res.code==200){
                    time = setInterval (function(){
                        if(j==60){
                            $("#sendNode").attr("disabled","disabled");
                        }
                        if(j<0){
                            $("#sendNode").removeAttr("disabled")
                            $("#sendNode").html("发送验证码");
                            j=60;
                            clearInterval(time)
                        }else{
                            $("#sendNode").html(j+"秒后可重新发送");
                            j--;
                        }
                    },1000)
                }else{
                    alert(res.msg);
                }
            }
        })
    }else{
        alert("手机号不能为空");
    }
}

