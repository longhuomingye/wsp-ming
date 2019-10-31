<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/14
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>用户查询页面</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">用户查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="userForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="userName" name="userName"
                                       placeholder="请输入用户名...">
                            </div>
                            <label class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="realName" name="realName"
                                       placeholder="请输入真实姓名...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">年龄范围</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minAge" name="minAge"
                                           placeholder="开始年龄..." aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" id="maxAge" name="maxAge"
                                           placeholder="结束年龄..." aria-describedby="basic-addon2">
                                </div>
                            </div>
                            <label class="col-sm-2 control-label">薪资范围</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minPay" name="minPay"
                                           placeholder="最小薪资..." aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-yen"></i></span>
                                    <input type="text" class="form-control" id="maxPay" name="maxPay"
                                           placeholder="最大薪资..." aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">入职时间</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="开始时间..." id="minDemo"
                                           name="minDemo" aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                    <input type="text" class="form-control" placeholder="结束时间..." id="maxDemo"
                                           name="maxDemo" aria-describedby="basic-addon2">
                                </div>
                            </div>
                            <label class="col-sm-2 control-label">角色</label>
                            <div class="col-sm-4">
                                <div class="input-group" id="selectRoleDiv">

                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="selectArea">
                            <label class="col-sm-2 control-label">地区</label>

                        </div>
                        <div class="form-group">
                            <div align="center">
                                <button class="btn btn-primary" type="button" onclick="search()"><i
                                        class="glyphicon glyphicon-search"></i>查询
                                </button>
                                <button class="btn btn-default" type="reset" onclick="resetRoleSelect('select_role')"><i
                                        class="glyphicon glyphicon-refresh"></i>重置
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div style="background-color: #0facd2">
        <button class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i>添加</button>
        <button class="btn btn-danger" onclick="deleteUserByIds()"><i class="glyphicon glyphicon-trash"></i>批量删除
        </button>
        <button class="btn btn-danger" onclick="exportPdf()"><i class="glyphicon glyphicon-plus"></i>导出ftp</button>
        <button class="btn btn-danger" onclick="exportExcel()"><i class="glyphicon glyphicon-plus"></i>导出excel</button>
        <button class="btn btn-danger" onclick="exportWord()"><i class="glyphicon glyphicon-plus"></i>导出word</button>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">用户列表</div>
                <table id="table" class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">用户名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">性别</th>
                        <th style="text-align: center;">年龄</th>
                        <th style="text-align: center;">电话</th>
                        <th style="text-align: center;">邮箱</th>
                        <th style="text-align: center;">薪资</th>
                        <th style="text-align: center;">入职时间</th>
                        <th style="text-align: center;">角色</th>
                        <th style="text-align: center;">头像</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">地区</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;"></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">用户名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">性别</th>
                        <th style="text-align: center;">年龄</th>
                        <th style="text-align: center;">电话</th>
                        <th style="text-align: center;">邮箱</th>
                        <th style="text-align: center;">薪资</th>
                        <th style="text-align: center;">入职时间</th>
                        <th style="text-align: center;">角色</th>
                        <th style="text-align: center;">头像</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">地区</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
                <div>
                </div>
            </div>
        </div>
        <%--新增--%>
        <div id="addUser" style="display: none;">
            <form id="addForm" class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-4">
                        <input type="text" name="userNameTo" class="form-control" id="add_userName"
                               placeholder="请输入用户名...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">真实姓名</label>
                    <div class="col-sm-4">
                        <input type="text" name="realNameTo" class="form-control" id="add_realName"
                               placeholder="请输入真实姓名...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-4">
                        <input type="password" name="passwordTo" class="form-control" id="add_password"
                               placeholder="请输入密码...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-4">
                        <input type="password" name="passwordTos" class="form-control" placeholder="请输入密码...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-4">
                        <input type="radio" name="sex" value="1"/>男
                        <input type="radio" name="sex" value="0"/>女
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年龄</label>
                    <div class="col-sm-4">
                        <input type="text" name="ageTo" class="form-control" id="add_age" placeholder="请输入年龄...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">电话</label>
                    <div class="col-sm-4">
                        <input type="text" name="phoneTo" class="form-control" id="add_phone" placeholder="请输入电话...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">邮箱</label>
                    <div class="col-sm-4">
                        <input type="text" name="emailTo" class="form-control" id="add_email" placeholder="请输入邮箱...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">薪资</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="add_pay" placeholder="请输入薪资...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">入职时间</label>
                    <div class="col-sm-4">
                        <input type="text" name="add_entryTime" class="form-control" id="add_entryTime" placeholder="请选择入职时间...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">角色</label>
                    <div class="col-sm-10" id="roleDiv">

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">头像</label>
                    <div class="col-sm-10">
                        <input type="file" name="myfile" data-ref="add_headImagePath" id="add_myfile" class="myfile"/>
                        <input type="hidden" id="add_headImagePath"/>
                    </div>
                </div>
                <div class="form-group" id="addArea">
                    <label class="col-sm-2 control-label">地区</label>

                </div>
            </form>
        </div>
        <%--修改--%>
        <div id="updateUser" style="display: none;">
            <form id="updateForm" class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-4">
                        <input type="hidden" id="id">
                        <input type="text" name="userNameTo" class="form-control" id="update_userName"
                               placeholder="请输入用户名...">
                        <input type="hidden" class="form-control" id="update_salt">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">真实姓名</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="update_realName" placeholder="请输入真实姓名...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-4">
                        <input type="password" name="passwordTo" class="form-control" id="update_password"
                               placeholder="请输入密码...">
                    </div>
                </div>
                <%--<div class="form-group">
                    <label class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-4">
                        <input type="password" name="passwordTos" class="form-control" placeholder="请输入密码...">
                    </div>
                </div>--%>
                <div class="form-group">
                    <label class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-4">
                        <input type="radio" name="updateSex" value="1"/>男
                        <input type="radio" name="updateSex" value="0"/>女
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年龄</label>
                    <div class="col-sm-4">
                        <input type="text" name="ageTo" class="form-control" id="update_age" placeholder="请输入年龄...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">电话</label>
                    <div class="col-sm-4">
                        <input type="text" name="phoneTo" class="form-control" id="update_phone" placeholder="请输入电话...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">邮箱</label>
                    <div class="col-sm-4">
                        <input type="text" name="emailTo" class="form-control" id="update_email" placeholder="请输入邮箱...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">薪资</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="update_pay" placeholder="请输入薪资...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">入职时间</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="update_entryTime" placeholder="请选择入职时间...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">角色</label>
                    <div class="col-sm-10" id="roleUpdateDiv">

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">头像</label>
                    <div class="col-sm-10">
                        <input type="file" name="myfile" data-ref="update_headImagePath" id="update_myfile" class="myfile"/>
                        <input type="hidden" id="update_headImagePath" value="">
                    </div>
                </div>
                <div class="form-group" id="updateArea">
                    <label class="col-sm-2 control-label">地区</label>

                </div>
            </form>
        </div>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var v_addUser;
    var v_updateUser;
    $(function () {
        userList();
        //备份 不然关闭新增窗口之后 内容就没了
        v_addUser = $("#addUser").html();
        //备份 同理
        v_updateUser = $("#updateUser").html();
        //时间插件
        $("#minDemo").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
        $("#maxDemo").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
        //这是给每一行数据都添加一个单击事件
        deleteIds();
        findRoleCheckbox("roleDiv", "add_role");
        findRoleCheckbox("roleUpdateDiv", "update_role");
        findRoleCheckbox("selectRoleDiv", "select_role");
        updateTime();
        addTime();
        bootadd();
        //bootupdate();
        //bootSelect();
        //构造查询三级联动地区下拉框
        buildAreaSelect("selectArea",0);
    })

    /*新增验证*/
    function bootadd() {
        $("#addForm").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            //trigger: "blur",
            fields: {
                userNameTo: {
                    trigger: 'blur', //失去焦点就会触发
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
                emailTo: {
                    trigger: 'blur', //失去焦点就会触发
                    validators: {
                        notEmpty: {
                            message: '电子邮件地址是必填项，不能为空'
                        },
                        emailAddress: {
                            message: '电子邮件地址无效'
                        }
                    }
                },
                passwordTo: {
                    trigger: 'blur', //失去焦点就会触发
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
                passwordTos: {
                    trigger: 'blur', //失去焦点就会触发
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        identical: {
                            field: 'passwordTo',
                            message: '两次输入密码不一致'
                        }
                    }
                },
                phoneTo: {
                    trigger: 'blur', //失去焦点就会触发
                    validators: {
                        notEmpty: {
                            message: '手机号码不能为空'
                        },
                        stringLength: {
                            min: 11,
                            max: 11,
                            message: '请输入11位手机号码'
                        },
                        regexp: {
                            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                            message: '请输入正确的手机号码'
                        }
                    }
                },
                sex: {
                    validators: {
                        notEmpty: {
                            message: '性别为必选'
                        }
                    }
                },
                ageTo:{
                    trigger: 'blur', //失去焦点就会触发
                    validators:{
                        greaterThan: {
                            value: 1
                        },
                        lessThan: {
                            value: 100,
                            message: '年龄不存在'
                        },
                        digits: {
                            message: '该值只能包含数字。'
                        },
                        notEmpty: {
                            message: '年龄必填不能为空'
                        },
                    }
                },
                add_entryTime:{
                    trigger:"blur",
                    validators:{
                        notEmpty: {
                            message: '入职时间必填不能为空'
                        }
                    }
                }
            }
        });
    }

    /*修改验证
    function bootupdate(){
        $("#updateForm").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                /!*userNameTo: {
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
                            data: function(validator) {
                                return {
                                    id: $("#id",update).val(),//#id
                                    userName: $("#update_userName",update).val(),
                                    //method : "checkUserName"//UserServlet判断调用方法关键字。
                                }
                            },
                        }
                    }
                },*!/
                emailTo: {
                    validators: {
                        notEmpty: {
                            message: '电子邮件地址是必填项，不能为空'
                        },
                        emailAddress: {
                            message: '电子邮件地址无效'
                        }
                    }
                },
                passwordTo: {
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
                passwordTos: {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        identical: {
                            field: 'passwordTo',
                            message: '两次输入密码不一致'
                        }
                    }
                },
                phoneTo: {
                    validators: {
                        notEmpty: {
                            message: '手机号码不能为空'
                        },
                        stringLength: {
                            min: 11,
                            max: 11,
                            message: '请输入11位手机号码'
                        },
                        regexp: {
                            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                            message: '请输入正确的手机号码'
                        }
                    }
                },
                updateSex: {
                    validators: {
                        notEmpty: {
                            message: '性别为必选'
                        }
                    }
                },
                ageTo:{
                    validators:{
                        greaterThan: {
                            value: 1
                        },
                        lessThan: {
                            value: 100,
                            message: '年龄不存在'
                        },
                        digits: {
                            message: '该值只能包含数字。'
                        },
                        notEmpty: {
                            message: '年龄必填不能为空'
                        },
                    }
                }
            }
        });
    }*/

    /*条件查询验证*/
    /*function bootSelect(){
        $("#userForm").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                userName: {
                    trigger: 'blur', //失去焦点就会触发
                    validators: {
                        stringLength: {
                            min: 6,
                            max: 12,
                            message: '用户名长度为6-12位'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_]+$/,
                            message: '用户名只能包含字母和数字或下划线'
                        }
                    }
                },
            }
        });
    }*/

    /*这是刷新方法*/
    function search() {
        var v_userName = $("#userName").val();
        var v_realName = $("#realName").val();
        var v_minAge = $("#minAge").val();
        var v_maxAge = $("#maxAge").val();
        var v_minPay = $("#minPay").val();
        var v_maxPay = $("#maxPay").val();
        var v_minDemo = $("#minDemo").val();
        var v_maxDemo = $("#maxDemo").val();
        /*$("#select_role option:selected").each(function(){
            v_roleIds.push($(this).val());
        });*/
        var v_roleIds = $("#select_role").val();
        var v_area1 = $($("[name='selectArea']")[0]).val();
        var v_area2 = $($("[name='selectArea']")[1]).val();
        var v_area3 = $($("[name='selectArea']")[2]).val();
        var v_param = {};
        v_param.userName = v_userName;
        v_param.realName = v_realName;
        v_param.minAge = v_minAge;
        v_param.maxAge = v_maxAge;
        v_param.minPay = v_minPay;
        v_param.maxPay = v_maxPay;
        v_param.minDemo = v_minDemo;
        v_param.maxDemo = v_maxDemo;
        v_param.roleIds = v_roleIds.toString();
        v_param.area1 = v_area1;
        v_param.area2 = v_area2;
        v_param.area3 = v_area3;
        v_param.roleIdsLengTh = v_roleIds.length;
        table.settings()[0].ajax.data = v_param;
        table.ajax.reload();
    }

    /*时间插件*/
    function addTime() {
        $("#add_entryTime").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        })
    }

    function updateTime() {
        $("#update_entryTime").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        })
    }

    /* 这是查询的函数 */
    var table;
    function userList() {
        var columName = [
            {
                "data": "id",
                "render": function (data) {//这个data就是咱们查到的pageInfo中的数据集合里的对象
                    return '<input name="ids" type="checkbox" value="' + data + '"/>'
                }
            },
            {"data": "id"},
            {"data": "userName"},
            {"data": "realName"},
            {
                "data": function (data) {
                    return data.sex == 1 ? "男" : data.sex == 0 ? "女" : "未知";
                }
            },
            {"data": "age"},
            {"data": "phone"},
            {"data": "email"},
            {"data": "pay"},
            {"data": "entryTime"},
            {"data": "roleNames"},
            {
                "data": "headImagePath",
                "render": function (data, type, row, meta) {
                    return '<img src="' + data + '" alt="没有头像" height="150px"/>'
                }
            },
            {
                "data": "userLock",
                "render": function (data, type, row, meta) {
                    return data?"锁定":"正常";
                }
            },
            {"data": "areaName"},
            {
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteUser(\'' + data + '\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span>删除</button>' +
                        '<button class="btn btn-info btn-sm" data-target="#myModal" onclick="toUpdateUser(\'' + data + '\')"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>修改</button>' +
                        ''+(row.userLock?"<button class=\"btn btn-success btn-sm\" data-target=\"#myModal\" onclick=\"updateLock("+data+")\"><span class=\"glyphicon glyphicon-lock\"></span>解锁</button>":"")+''+
                        '<button class="btn btn-info btn-sm" data-target="#myModal" onclick="resetPasswordByUserId(\'' + data + '\')"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>重置密码</button>' +
                        '</div>'
                }
            },
        ];
        /* 渲染datatables */
        table = $('#table').DataTable({
            "processing": true,
            "serverSide": true,
            "ordering": false,//是否启用排序
            "ajax": {
                "url": "/user/userList.jhtml",
                "type": "POST",
                //过滤后台传过来的数据 只拿自己需要的
                "dataSrc": function (json) {
                    //这个json 就是一个变量名
                    json.draw = json.data.draw;
                    json.recordsTotal = json.data.recordsTotal;
                    json.recordsFiltered = json.data.recordsFiltered;
                    return json.data.data;
                },
            },
            searching: false,
            "lengthMenu": [5, 10, 20, 40, 80],
            language: {
                "url": "/js/DataTables-1.10.18/Chinese.json"
            },
            columns: columName,//设置列的初始化属性
            "drawCallback": function (settings) {
                $("#table tbody tr").each(function () {
                    var v_checkbox = $(this).find("[name='ids']")[0];
                    if (v_checkbox) {
                        var id = v_checkbox.value;
                        if (isExist(id)) {
                            v_checkbox.checked = true;
                            $(this).css("background-color", "red");
                        }
                    }
                })
            }
        });
    }

    /*解锁*/
    function updateLock(id){
        //阻止事件冒泡
        window.event.stopPropagation();
        bootbox.confirm({
            message: "你确定解锁吗?",
            size: 'small',
            title: "提示信息",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove"></span>取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                //这个result是boolean类型的 点确认是true 反之false
                if (result) {
                    //解锁
                    $.ajax({
                        url: "/user/updateLock.jhtml",
                        type: "post",
                        data: {
                            "id": id,
                        },
                        dataType: "json",
                        success: function (res) {
                            if (res.code == 200) {
                                search();
                            }
                        }
                    })
                }
            }
        })
    }

    /*重置密码*/
    function resetPasswordByUserId(id){
        //阻止事件冒泡
        window.event.stopPropagation();
        bootbox.confirm({
            message: "你确定重置吗?",
            size: 'small',
            title: "提示信息",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove"></span>取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                //这个result是boolean类型的 点确认是true 反之false
                if (result) {
                    //重置密码
                    $.ajax({
                        url: "/user/resetPasswordByUserId.jhtml",
                        type: "post",
                        data: {
                            "id": id,
                        },
                        dataType: "json",
                        success: function (res) {
                            if (res.code == 200) {
                                bootbox.alert({
                                    message: "<span class='glyphicon glyphicon-exclamation-sign'></span>重置成功",
                                    size: 'small',
                                    title: "提示信息"
                                });
                            }
                        }
                    })
                }
            }
        })
    }

    /*删除*/
    function deleteUser(id) {
        //阻止事件冒泡
        window.event.stopPropagation();
        bootbox.confirm({
            message: "你确定删除吗?",
            size: 'small',
            title: "提示信息",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove"></span>取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                //这个result是boolean类型的 点确认是true 反之false
                if (result) {
                    //删除
                    $.ajax({
                        url: "/user/deleteUser.jhtml",
                        type: "post",
                        data: {
                            "id": id,
                        },
                        dataType: "json",
                        success: function (res) {
                            if (res.code == 200) {
                                search();
                            }
                        }
                    })
                }
            }
        })
    }

    /*修改回显*/
    function toUpdateUser(id) {
        //阻止事件冒泡
        window.event.stopPropagation();
        $.ajax({
            url: "/user/toUpdateUser.jhtml",
            data: {
                "id": id
            },
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == 200) {
                    var data = res.data;
                    $("#id").val(data.id);
                    $("#update_userName").val(data.userName);
                    $("#update_realName").val(data.realName);
                    //$("#update_password").val(data.password);
                    $("[name='updateSex']").each(function () {
                        if ($(this).val() == data.sex) {
                            $(this).attr("checked", true);
                        }
                    })
                    $("#update_age").val(data.age);
                    $("#update_phone").val(data.phone);
                    $("#update_email").val(data.email);
                    $("#update_pay").val(data.pay);
                    $("#update_entryTime").val(data.entryTime);
                    var roleIds = data.roleIds;
                    $("#update_role").val(roleIds);
                    $("#update_salt").val(data.salt);
                    $("#update_headImagePath").val(data.headImagePath);
                    $("#update_role").selectpicker("render");//刷新框中显示的值
                    updateUser();
                }
            }
        })
    }

    /*修改方法*/
    var update;
    function updateUser() {
        uploadFile("update_myfile");
        update = bootbox.dialog({
            title: '修改用户',
            message: $("#updateUser form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function () {
                    }
                },
                confirm: {
                    label: "修改",
                    className: 'btn-primary',
                    callback: function () {
                        var id = $("#id", update).val();
                        var update_userName = $("#update_userName", update).val();
                        var update_realName = $("#update_realName", update).val();
                        var update_password = $("#update_password", update).val();
                        var updateSex = $("[name='updateSex']:checked", update).val();
                        var update_age = $("#update_age", update).val();
                        var update_phone = $("#update_phone", update).val();
                        var update_email = $("#update_email", update).val();
                        var update_pay = $("#update_pay", update).val();
                        var update_salt = $("#update_salt", update).val();
                        var update_entryTime = $("#update_entryTime", update).val();
                        var v_role = [];
                        $("#update_role option:selected", update).each(function () {
                            v_role.push($(this).val());
                        });
                        var v_headImagePath = $("#update_headImagePath").val();
                        var v_param = {
                            "id": id,
                            "userName": update_userName,
                            "realName": update_realName,
                            "password": update_password,
                            "sex": updateSex,
                            "age": update_age,
                            "phone": update_phone,
                            "email": update_email,
                            "pay": update_pay,
                            "entryTime": update_entryTime,
                            "roleIds": v_role.toString(),
                            "headImagePath": v_headImagePath,
                            "salt": update_salt
                        }
                        $.ajax({
                            url: "/user/updateUser.jhtml",
                            data: v_param,
                            dataType: "json",
                            type: "post",
                            success: function (res) {
                                if (res.code == 200) {
                                    console.log(res.msg);
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        });
        $("#updateUser").html(v_updateUser);
        //重新初始化修改时间插件
        updateTime();
        findRoleCheckbox("roleUpdateDiv", "update_role");
    }
    var addUser
    /*新增用户*/
    function add() {
        uploadFile("add_myfile");
        buildAreaSelect("addArea",0);
        addUser = bootbox.dialog({
            title: '添加用户',
            message: $("#addUser form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function () {
                    }
                },
                confirm: {
                    label: "确定",
                    className: 'btn-primary',
                    callback: function () {
                        var validator = $("#addForm", addUser).data("bootstrapValidator"); //获取validator对象
                        validator.validate(); //手动触发验证
                        var flag = validator.isValid();
                        if (flag) {
                            var v_userName = $("#add_userName", addUser).val();
                            var v_realName = $("#add_realName", addUser).val();
                            var v_password = $("#add_password", addUser).val();
                            var v_sex = $("[name='sex']:checked", addUser).val();
                            var v_age = $("#add_age", addUser).val();
                            var v_phone = $("#add_phone", addUser).val();
                            var v_email = $("#add_email", addUser).val();
                            var v_pay = $("#add_pay", addUser).val();
                            var v_entryTime = $("#add_entryTime", addUser).val();
                            var v_area1 = $($("[name='addArea']",addUser)[0]).val();
                            var v_area2 = $($("[name='addArea']",addUser)[1]).val();
                            var v_area3 = $($("[name='addArea']",addUser)[2]).val();
                            var v_role = [];
                            $("#add_role option:selected", addUser).each(function () {
                                v_role.push($(this).val());
                            });
                            var v_headImagePath = $("#add_headImagePath").val();
                            var v_param = {};
                            v_param.userName = v_userName;
                            v_param.realName = v_realName;
                            v_param.password = v_password;
                            v_param.sex = v_sex;
                            v_param.age = v_age;
                            v_param.phone = v_phone;
                            v_param.email = v_email;
                            v_param.pay = v_pay;
                            v_param.entryTime = v_entryTime;
                            v_param.roleIds = v_role.toString();
                            v_param.headImagePath = v_headImagePath;
                            v_param.area1 = v_area1;
                            v_param.area2 = v_area2;
                            v_param.area3 = v_area3;
                            $.ajax({
                                url: "/user/add.jhtml",
                                data: v_param,
                                type: "post",
                                dataType: "json",
                                success: function (res) {
                                    if (res.code == 200) {
                                        search();
                                    }
                                }
                            })
                        } else {
                            return false;
                        }
                    }
                }
            }
        });
        $("#addUser").html(v_addUser);
        //重新初始化新增时间插件
        addTime();
        findRoleCheckbox("roleDiv", "add_role");
    }

    /*批量删除获取ids*/
    var ids = [];

    function deleteIds() {
        $("#tbody").on("click", "tr", function () {
            //获取当前的jquery对象
            var v_checkbox = $(this).find("[name='ids']")[0];
            var wsp = $(this).attr("wsp");
            if (!!wsp) {
                //如果当前是被选中状态就清空他的背景颜色
                $(this).css("background-color", "");
                //更改选中状态
                v_checkbox.checked = false;
                $(this).removeAttr("wsp");
                //从数组中移除当前这行数据的id
                var value = v_checkbox.value;
                var i = $.inArray(value, ids);
                ids.splice(i, 1);
                /*for(var i = ids.length-1; i>=0;i--){
                    if(ids[i]==v_checkbox.value){
                        ids.splice(i,1);
                        break;
                    }
                }*/
            } else {
                $(this).attr("wsp", "ok");
                $(this).css("background-color", "red");
                v_checkbox.checked = true;
                ids.push(v_checkbox.value);
            }
        })
    }

    /*批量删除数据*/
    function deleteUserByIds() {
        if (ids.length > 0) {
            $.ajax({
                url: "/user/deleteUserByIds.jhtml",
                data: {
                    "ids": ids.toString()
                },
                dataType: "json",
                type: "post",
                success: function (res) {
                    if (res.code == 200) {
                        //刷新
                        ids = [];
                        search();
                    }
                }
            })
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择需要删除的数据",
                size: 'small',
                title: "提示信息"
            });
        }
    }

    /*判断复选框是否被选中*/
    function isExist(id) {
        for (var i = 0; i < ids.length; i++) {
            if (id == ids[i]) {
                return true;
            }
        }
        return false;
    }

    /*查询所有角色生成下拉框*/
    function findRoleCheckbox(roleDiv, roleName) {
        $.ajax({
            url: "/role/findRoleCheckbox.jhtml",
            data: "",
            dataType: "json",
            type: "post",
            success: function (res) {
                var str = "";
                str += '<select id="' + roleName + '" name="roleIds" multiple>';
                for (var i = 0; i < res.length; i++) {
                    str += '<option value="' + res[i].id + '">';
                    str += res[i].roleName;
                    str += '</option>';
                }
                str += '</select>';
                $('#' + roleDiv).html(str);
                $('#' + roleName).selectpicker();
            }
        })
    }

    /*地区三级联动*/
    function buildAreaSelect(divId,pid,obj) {
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            url: "/area/findAllAreaByPid.jhtml",
            data: {
                "pid": pid
            },
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.length == 0) {
                    return;
                }
                var str = '<div class="col-sm-2"><select class="form-control" name="'+divId+'" onchange="buildAreaSelect(\''+divId+'\', this.value, this)">';
                str += "<option value='-1'>===请选择===</option>";
                for (var i = 0; i < res.length; i++) {
                    str += "<option value='" + res[i].id + "'>" + res[i].areaName + "</option>";
                }
                str+="</select>"
                str+="</div>"
                if(divId=="selectArea"){
                    $("#"+divId).append(str);
                }else if(divId=="addArea"){
                    $("#"+divId,addUser).append(str);
                }else if(divId=="updateArea"){
                    $("#"+divId,update).append(str);
                }

            }
        })
    }

    /*图片上传*/
    function uploadFile(fileId) {
        var img = $("#update_headImagePath").val();
        var url='http://169.254.33.76/Public/'+img;
        var urlArr=[];
        urlArr.push(url);
        $("#"+fileId).fileinput({
            //上传的地址
            uploadUrl: "/file/uploadFile.jhtml",
            showUpload: false, //是否显示上传按钮,跟随文本框的那个
            showRemove: false, //显示移除按钮,跟随文本框的那个
            initialPreview:[urlArr],//回显图片
            initialPreviewAsData:true,
            dropZoneEnabled: false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
            allowedPreviewMimeTypes: ['jpg', 'png', 'gif'],//控制被预览的所有mime类型
            language: 'zh'
        })
        //异步上传返回结果处理
        $("#"+fileId).on("fileuploaded", function (event, data, previewId, index) {
            var ref = $(this).attr("data-ref");
            $("#" + ref + "").val(data.response.data);
        });
    }

    //重置复选下拉框
    function resetRoleSelect(selectId) {
        $('#' + selectId).selectpicker("deselectAll");
    }

    /*导出ftp*/
    function exportPdf() {
        //通过id获取form表单
        var v_userForm = document.getElementById("userForm")
        //设置action
        v_userForm.action = "/user/exportPdf.jhtml";
        //设置提交方式
        v_userForm.method = "post";
        //提交
        v_userForm.submit();
    }

    /**
     * 导出excel
     */
    function exportExcel() {
        //通过id获取form表单
        var v_userForm = document.getElementById("userForm")
        //设置action
        v_userForm.action = "/user/exportExcel.jhtml";
        //设置提交方式
        v_userForm.method = "post";
        //提交
        v_userForm.submit();
    }

    /**
     * 导出word
     */
    function exportWord() {
        //通过id获取form表单
        var v_userForm = document.getElementById("userForm")
        //设置action
        v_userForm.action = "/user/exportWord.jhtml";
        //设置提交方式
        v_userForm.method = "post";
        //提交
        v_userForm.submit();
    }
</script>
</html>
