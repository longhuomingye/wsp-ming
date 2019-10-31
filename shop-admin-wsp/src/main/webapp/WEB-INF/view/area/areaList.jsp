<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/22
  Time: 21:39
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
                    地区管理
                    <input type="button" class="btn-danger btn-sm" value="添加" onclick="addZtree()" />
                    <input type="button" class="btn-danger btn-sm" value="删除" onclick="zTreeOnRemove()" />
                    <input type="button" class="btn-info btn-sm" value="修改" onclick="zTreeOnRename()" />
                </div>
                <ul id="tree" class="ztree" style="width: 230px; overflow: auto;"></ul>
            </div>
        </div>
    </div>
</div>
    <%--新增--%>
    <div id="addAreaDiv" style="display: none;">
        <form class="form-horizontal">
            <div class="form-group">
                <label  class="col-sm-2 control-label">地区名</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_areaName" placeholder="请输入地区名...">
                </div>
            </div>
        </form>
    </div>
    <%--修改--%>
    <div id="updateAreaDiv" style="display: none;">
        <form class="form-horizontal">
            <div class="form-group">
                <label  class="col-sm-2 control-label">地区名</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_areaName" placeholder="请输入地区名...">
                </div>
            </div>
        </form>
    </div>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var addAreaDiv;
    var updateAreaDiv;
    $(function(){
        findLayTree();
        //备份
        addAreaDiv = $("#addAreaDiv").html();
        updateAreaDiv = $("#updateAreaDiv").html();
    })
    //查询
    function findLayTree(){
        $.ajax({
            url:"/area/findLayTree.jhtml",
            data : "",
            dataType : "json",
            type : "post",
            success : function(res) {
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
                        chkboxType:  { "Y" : "p", "N" : "ps" }
                    }
                }
                var nodes = res;
                $.fn.zTree.init($("#tree"), setting,nodes);
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                treeObj.expandAll(true);
            }
        })
    }
    //修改
    function zTreeOnRename(){
        var newTree=$.fn.zTree.getZTreeObj("tree");
        //获取当前被选中的节点数据集合
        var nodes = newTree.getSelectedNodes();
        if(nodes.length==1){
            var name = nodes[0].name;
            $("#update_areaName").val(name);
            var update = bootbox.dialog({
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
                            var name = $("#update_areaName", update).val();
                            $.ajax({
                                url:"/area/updateZtree.jhtml",
                                type:"post",
                                data:{"id":id,"areaName":name},
                                success:function(res){
                                    if(res.code==200){
                                        nodes[0].name = name;
                                        //更新
                                        newTree.updateNode(nodes[0]);
                                    }
                                }
                            })
                        }
                    }
                }
            });
            $("#updateAreaDiv").html(updateAreaDiv)
        }else if(nodes.length>1){
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }else{
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }
    //删除
    function zTreeOnRemove() {
        var newTree=$.fn.zTree.getZTreeObj("tree");
        var nodes = newTree.getSelectedNodes();
        if(nodes.length>0){
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
                        var nodeArr = newTree.transformToArray(nodes);
                        var ids = [];
                        for(var i=0;i<nodeArr.length;i++){
                            ids.push(nodeArr[i].id);
                        }
                        $.ajax({
                            url:"/area/deleteZtreeByPash.jhtml",
                            type : "post",
                            data : {
                                "ids" : ids.toString(),
                            },
                            success : function(res) {
                                if(res.code==200){
                                    for (var i=0; i < nodeArr.length; i++) {
                                        newTree.removeNode(nodeArr[i]);
                                    }
                                }
                            }
                        })
                    }
                }
            })
        }else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }
    //新增
    function addZtree(){
        var newTree=$.fn.zTree.getZTreeObj("tree");
        //获取当前被选中的节点数据集合
        var nodes = newTree.getSelectedNodes();
        if(nodes.length==1){
            var add = bootbox.dialog({
                title: '添加地区',
                message: $("#addAreaDiv form"),
                size: 'large',
                buttons: {
                    cancel: {
                        label: "关闭",
                        className: 'btn-danger',
                        callback: function(){
                            //console.log('Custom cancel clicked');
                        }
                    },
                    confirm: {
                        label: "添加",
                        className: 'btn-primary',
                        callback: function(){
                            var id = nodes[0].id;
                            var name = $("#add_areaName",add).val();
                            $.ajax({
                                url:"/area/saveZtree.jhtml",
                                type : "post",
                                data : {
                                    "pid" : id,
                                    "areaName" : name
                                },
                                success : function(res) {
                                    if(res.code==200){
                                        var node = {"id":res.data,"name":name,"pId":id}
                                        newTree.addNodes(nodes[0],node);
                                    }
                                }
                            })
                        }
                    }
                }
            });
        }else if(nodes.length>1){
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }else{
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
        $("#addAreaDiv").html(addAreaDiv);
    }
</script>
</html>
