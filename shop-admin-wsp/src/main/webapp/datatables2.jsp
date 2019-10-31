<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/15
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
<form id="form" method="post" class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 control-label">用户名</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" name="userName" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">邮箱</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" name="email" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">密码</label>
        <div class="col-sm-5">
            <input type="password" class="form-control" name="password" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">确认密码</label>
        <div class="col-sm-5">
            <input type="password" class="form-control" name="passwordTo" />
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-9 col-sm-offset-3">
            <button type="submit" class="btn btn-default">Sign up</button>
        </div>
    </div>
</div>
</form>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    $(function(){
        boot();
    })
   function boot(a){
       $('#form').bootstrapValidator({
           feedbackIcons: {
               valid: 'glyphicon glyphicon-ok',
               invalid: 'glyphicon glyphicon-remove',
               validating: 'glyphicon glyphicon-refresh'
           },
           fields: {
               userName: {
                   validators: {
                       notEmpty: {
                           message: '用户名是必填项，不能为空'
                       },
                       stringLength: {
                           min: 6,
                           max: 12,
                           message: '用户名长度为6-12位'
                       },
                       regexp: {
                           regexp: /^[a-zA-Z0-9_]+$/,
                           message: '用户名只能包含字母和数字或下划线'
                       },
                       threshold: 6,//有2字符以上才发送ajax请求
                       remote: {
                           url: "/user/loginUserName.jhtml",
                           message: '用户名已存在,请重新输入',
                           delay: 1000,//ajax刷新的时间是1秒一次
                           type: 'POST',
                       }
                   }
               },
               email: {
                   validators: {
                       notEmpty: {
                           message: '电子邮件地址是必填项，不能为空'
                       },
                       emailAddress: {
                           message: '电子邮件地址无效'
                       }
                   }
               },
               password: {
                   validators: {
                       notEmpty: {
                           message: '密码是必填项不能为空'
                       },
                       stringLength: {
                           min: 6,
                           max: 8,
                           message: '密码为6-8位之间'
                       },
                       regexp: {
                           regexp: /^[a-zA-Z0-9_]+$/,
                           message: '密码只能包含字母和数字或下划线'
                       }
                   }
               },
               passwordTo:{
                   validators:{
                       notEmpty: {
                           message: '确认密码不能为空'
                       },
                       identical:{
                           field: 'password',
                           message: '两次输入密码不一致'
                       }
                   }
               },
           }
       });
   }
</script>
</html>

