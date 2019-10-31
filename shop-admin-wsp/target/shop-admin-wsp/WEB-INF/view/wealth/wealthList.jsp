<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/25
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>Title</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">
                    菜单管理
                    <input type="button" class="btn btn-danger btn-sm" value="添加" onclick="addZtree()"/>
                    <input type="button" class="btn btn-danger btn-sm" value="删除" onclick="deleteZtree()"/>
                    <input type="button" class="btn btn-info btn-sm" value="修改" onclick="updateZtree()"/>
                </div>
                <ul id="tree" class="ztree" style="width: 230px; overflow: auto;"></ul>
            </div>
        </div>
    </div>
</div>
<%--新增--%>
<div id="addWealthDiv" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">菜单名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_memuName" placeholder="请输入地区名...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">类型</label>
            <div class="col-sm-4">
                <input type="radio" class="" name="add_memuType" value="1">菜单
                <input type="radio" class="" name="add_memuType" value="2">按钮
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">url</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_url" placeholder="请输入url路径...">
            </div>
        </div>
    </form>
</div>
<%--修改--%>
<div id="updateWealthDiv" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">地区名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_memuName" placeholder="请输入地区名...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">类型</label>
            <div class="col-sm-4">
                <input type="radio" class="" name="update_memuType" value="1">菜单
                <input type="radio" class="" name="update_memuType" value="2">按钮
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">url</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_url" placeholder="请输入url路径...">
            </div>
        </div>
    </form>
</div>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var addWealthDiv;
    var updateWealthDiv;
    $(function () {
        findZtreeList();
        addWealthDiv = $("#addWealthDiv").html();
        updateWealthDiv = $("#updateWealthDiv").html();
    })


    /**
     * 查询
     */
    function findZtreeList() {
        $.ajax({
            url: "/wealth/findZtreeList.jhtml",
            data: "",
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == 200) {
                    var setting = {
                        data: {
                            simpleData: {
                                enable: true,
                            },
                            key: {
                                url: "xUrl"
                            }
                        },
                        check: {
                            chkboxType:  { "Y" : "ps", "N" : "s" }
                        }
                    }
                    $.fn.zTree.init($("#tree"), setting, res.data);
                    var treeObj = $.fn.zTree.getZTreeObj("tree");
                    treeObj.expandAll(true);
                } else {
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>错误",
                        size: 'small',
                        title: "提示信息"
                    });
                }
            },
            error: function (res) {
                alert(res.responseText);
            }
        })
    }

    /**
     * 新增节点
     */
    var add;
    function addZtree() {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        //等于1说明选择了父节点
        if (nodes.length == 1) {
            //判断当前节点是否为按钮 如果是 禁止添加
            var memuTypeTo = nodes[0].memuType;
            if(memuTypeTo==2){
                bootbox.alert({
                    message: "<span class='glyphicon glyphicon-exclamation-sign'></span>当前节点为按钮禁止添加子节点",
                    size: 'small',
                    title: "提示信息"
                });
            }else{
                add = bootbox.dialog({
                    title: '添加资源',
                    message: $("#addWealthDiv form"),
                    size: 'large',
                    buttons: {
                        cancel: {
                            label: "关闭",
                            className: 'btn-danger',
                            callback: function () {
                                //console.log('Custom cancel clicked');
                            }
                        },
                        confirm: {
                            label: "添加",
                            className: 'btn-primary',
                            callback: function () {
                                var id = nodes[0].id;
                                var name = $("#add_memuName", add).val();
                                var memuType= $("[name='add_memuType']:checked",add).val();
                                var url = $("#add_url",add).val();
                                $.ajax({
                                    url: "/wealth/add.jhtml",
                                    data: {
                                        "fatherId": id,
                                        "memuName": name,
                                        "memuType":memuType,
                                        "url":url
                                    },
                                    dataType: "json",
                                    type: "post",
                                    success: function (res) {
                                        if (res.code == 200) {
                                            var node = {"id": res.data, "pId": id, "name": name,"url":url,"memuType":memuType};
                                            treeObj.addNodes(nodes[0], node);
                                        }
                                    }
                                })
                            }
                        }
                    }
                });
                $("#addWealthDiv").html(addWealthDiv);
            }
        } else if (nodes.length > 1) {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }

    /**
     * 删除
     * @param event
     * @param treeId
     * @param treeNode
     */
    function deleteZtree() {
        var newTree = $.fn.zTree.getZTreeObj("tree");
        var nodes = newTree.getSelectedNodes();
        if (nodes.length > 0) {
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
                        //获取被选中节点的所有子节点
                        var nodeArr = newTree.transformToArray(nodes);
                        var ids = [];
                        for (var i = 0; i < nodeArr.length; i++) {
                            ids.push(nodeArr[i].id);
                        }
                        $.ajax({
                            url: "/wealth/deleteZtree.jhtml",
                            type: "post",
                            data: {
                                "ids": ids,
                            },
                            dataType: "json",
                            success: function (res) {
                                if (res.code == 200) {
                                    for (var i = 0; i < nodeArr.length; i++) {
                                        newTree.removeNode(nodeArr[i]);
                                    }
                                }
                            }
                        })
                    }
                }
            })
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }

    //修改
    var update;
    function updateZtree() {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length == 1) {
            console.log(nodes)
            var name = nodes[0].name;
            $("#update_memuName").val(name);
            var urlUpdate = nodes[0].url;
            $("#update_url").val(urlUpdate);
            var memuType = nodes[0].memuType;
            $("[name='update_memuType']").each(function(){
                if(this.value==memuType){
                    $(this).attr("checked",true);
                    return;
                }
            })
            update = bootbox.dialog({
                title: '修改资源',
                message: $("#updateWealthDiv form"),
                size: 'large',
                buttons: {
                    cancel: {
                        label: "关闭",
                        className: 'btn-danger',
                        callback: function () {
                            //console.log('Custom cancel clicked');
                        }
                    },
                    confirm: {
                        label: "修改",
                        className: 'btn-primary',
                        callback: function () {
                            var id = nodes[0].id;
                            var name = $("#update_memuName", update).val();
                            var memuType = $("[name='update_memuType']:checked",update).val();
                            var url = $("#update_url",update).val();
                            $.ajax({
                                url: "/wealth/updateZtree.jhtml",
                                data: {
                                    "id": id,
                                    "memuName": name,
                                    "memuType":memuType,
                                    "url":url
                                },
                                dataType: "json",
                                type: "post",
                                success: function (res) {
                                    if (res.code == 200) {
                                        nodes[0].name = name;
                                        nodes[0].memuType = memuType;
                                        nodes[0].url = url;
                                        //更新
                                        treeObj.updateNode(nodes[0]);
                                    }
                                }
                            })
                        }
                    }
                }
            });
            $("#updateWealthDiv").html(updateWealthDiv);
        } else if (nodes.length > 1) {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }

</script>
</html>
