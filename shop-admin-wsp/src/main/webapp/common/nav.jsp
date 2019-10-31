<%@ page import="com.fh.shop.admin.util.DistributedSession" %>
<%@ page import="com.fh.shop.admin.util.RedisUtil" %>
<%@ page import="com.fh.shop.admin.util.KeyUtil" %>
<%@ page import="com.fh.shop.admin.po.user.User" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %><%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/28
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--导航条--%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<style>
    .dropdown-submenu {
        position: relative;
    }

    .dropdown-submenu > .dropdown-menu {
        top: 0;
        left: 100%;
        margin-top: -6px;
        margin-left: -1px;
        -webkit-border-radius: 0 6px 6px 6px;
        -moz-border-radius: 0 6px 6px;
        border-radius: 0 6px 6px 6px;
    }

    .dropdown-submenu:hover > .dropdown-menu {
        display: block;
    }

    .dropdown-submenu > a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #ccc;
        margin-top: 5px;
        margin-right: -10px;
    }

    .dropdown-submenu:hover > a:after {
        border-left-color: #fff;
    }

    .dropdown-submenu.pull-left {
        float: none;
    }

    .dropdown-submenu.pull-left > .dropdown-menu {
        left: -100%;
        margin-left: 10px;
        -webkit-border-radius: 6px 0 6px 6px;
        -moz-border-radius: 6px 0 6px 6px;
        border-radius: 6px 0 6px 6px;
    }
</style>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">飞狐电商后台管理</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" id="wealth">

            </ul>
            </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="#">
                    <%
                        String sessionId = DistributedSession.getSessionId(request, response);
                        String userJson = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
                        User user = JSONObject.parseObject(userJson, User.class);
                    %>
                    欢迎<%=user.getRealName()%>登陆
                    <c:if test="<%=user.getLoginTime()!=null%>">
                        ，上次登录时间:<fmt:formatDate value="<%=user.getLoginTime()%>"
                                                pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </c:if>
                    ，今天登录次数:<%=user.getCount()%>
                </a></li>
                <li class="dropdown">
                    <a href="/user/logout.jhtml">退出</a>
                </li>
                <li class="dropdown">
                <a href="/user/toUpdatepassword.jhtml">修改密码</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script>
    $(function () {
        $.ajaxSetup({
            complete:function(res){
                var value = res.responseJSON;
                if(value.code!=200 && !!value.code){
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>"+value.msg,
                        size: 'small',
                        title: "提示信息"
                    });
                }
            }
        })
        findWealth();
        //findUserTou();
    });

    /*function findUserTou(){
        $.ajax({
            url: "/user/findUserTou.jhtml",
            data: "",
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == 200) {
                    console.log(res.data)
                }
            }
        })
    }*/

    function findWealth() {
        $.ajax({
            url: "/wealth/findWealthByUserId.jhtml",
            data: "",
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == 200) {
                    var menus = res.data;
                    var wealth = $('#wealth');
                    for (var i = 0; i < menus.length; i++) {
                        var str = "";
                        var menu = menus[i];
                        var id = menu.id;
                        var menuName = menu.memuName;
                        var fatherId = menu.fatherId;
                        var type = menu.memuType;
                        var url = menu.url;
                        var k = 0;
                        if(type!=2 && type!=null){
                            if (fatherId == 1){//父id为根节点id 说明此节点为根节点下的节点 直接展示
                                for(var j=0;j<menus.length;j++){
                                    if(id==menus[j].fatherId){//如果当前的菜单的下面有子节点，生成下拉框
                                        str += '<li class="dropdown">';
                                        str += '<a data-toggle="dropdown" href="#">';
                                        str += menuName + '<span class="caret"></span></a>';
                                        str += '<ul class="dropdown-menu" id="wealth'+id+'"></ul>';//动态生成下拉选项的id
                                        str += '</li>';
                                        wealth.append(str);
                                        k = 1;
                                        break;
                                    }
                                }
                                if(k!=1){
                                    str += '<li><a href="' + url + '">' + menuName + '</a></li>';
                                    wealth.append(str);
                                }
                            } else {//否则  说明此节点是根节点之外的节点
                                var wealthTo = $('#wealth'+fatherId);//通过下拉选项的id获取到ul
                                for(var k =0 ; k<menus.length;k++){
                                    if(id==menus[k].fatherId){
                                        str += '<li class="dropdown-submenu">';
                                        str += '<a href="#">';
                                        str += menuName + '</a>';
                                        str += '<ul class="dropdown-menu" id="wealth' + id + '"></ul>';
                                        str += '</li>';
                                        k=1;
                                        wealthTo.append(str);
                                        break;
                                    }
                                }
                                if(k!=1){
                                    str += '<li><a href="' + url + '">' + menuName + '</a></li>';
                                    wealthTo.append(str);
                                }
                            }
                        }
                    }
                }
            }
        })
    }
</script>
