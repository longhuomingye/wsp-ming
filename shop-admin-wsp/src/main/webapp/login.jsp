<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/14
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>欢迎光临登录界面</title>
</head>
<body class="show-grid">
<div class="col-md-12">
    <div class="panel panel-info">
        <div class="panel-body">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-2">
                        <input type="email" class="form-control" id="userName" placeholder="请输入用户名...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-2">
                        <input class="form-control" type="password" id="password" placeholder="请输入用户名...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="imgCode" placeholder="请输入验证码...">
                    </div>
                    <div class="col-sm-1">
                        <img src="/img/Code" alt="" id="code">
                    </div>
                    <div class="col-sm-1">
                        <a href="javascript:buildCode()">看不清换一张</a>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary btn-sm" type="button" onclick="toPassword()">忘记密码</button>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="button" onclick="login()">登录</button>
                        <button class="btn btn-default" type="reset"d>重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>

    function buildCode(){
        var t = new Date().getTime();
        $("#code").attr("src","/img/Code?"+t);
    }
    function login(){
        var userName = $("#userName").val();
        var password = $("#password").val();
        var imgCode = $("#imgCode").val();
        if(!!userName && !!password){
            $.ajax({
                url:"<%=request.getContextPath()%>/user/login.jhtml",
                data:{
                    "userName":userName,
                    "password":password,
                    "imgCode":imgCode
                },
                dataType:"json",
                type:"post",
                success:function(res) {
                    if (res.code == 200) {
                        location.href = "<%=request.getContextPath()%>/index/indexJsp.jhtml"
                    }else{
                        bootbox.alert({
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>"+res.msg,
                            size: 'small',
                            title: "提示信息"
                        });
                    }
                }
            })
        }
    }

    function forgetPassword(){

    }

    function toPassword(){
        location.href = "/forgetPassword.jsp";
    }
</script>
</html>
