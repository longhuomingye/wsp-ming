<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/9/10
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>Title</title>
</head>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label">用户名</label>
        <div class="col-sm-4">
            <input type="email" class="form-control" id="userName" placeholder="请输入用户名...">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">邮箱地址</label>
        <div class="col-sm-4">
            <input type="email" class="form-control" id="email" placeholder="请输入邮箱地址...">
        </div>
    </div>
    <div class="form-group">
        <div align="center">
            <button class="btn btn-primary" type="button" onclick="resetPassword()">请求重置密码</button>
            <button class="btn btn-default" type="reset">重置</button>
        </div>
    </div>
</form>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    function resetPassword(){
        var v_email = $("#email").val();
        var v_userName = $("#userName").val();
        $.ajax({
            url:"/user/resetPassword.jhtml",
            data:{
                email:v_email,
                userName:v_userName,
            },
            type:"post",
            success:function(res){
                if(res.code==200){
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>"+res.msg,
                        size: 'small',
                        title: "提示信息",
                        callback:function(){
                            console.log("aaa");
                            location.href = "login.jsp";
                        }
                    });
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
</script>
</html>
