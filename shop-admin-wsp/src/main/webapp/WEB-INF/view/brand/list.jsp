<%--
  Created by IntelliJ IDEA.
  Brand: Ming
  Date: 2019/8/24
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>list</title>
    <style type="text/css" rel="stylesheet">
        tfoot input {
            width: 100%;
            padding: 3px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">品牌查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="userForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">品牌名</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="brandName"
                                       placeholder="请输入品牌名...">
                            </div>
                        </div>
                        <div class="form-group">
                            <div align="center">
                                <button class="btn btn-primary" type="button" onclick="search()"><i
                                        class="glyphicon glyphicon-search"></i>提交
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
    <div class="row">
        <div class="col-md-12">
            <div style="text-align: left; background: #e4b9b9;">
                <button type="button" class="btn btn-primary" onclick="showAddBrand()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
                </button>
            </div>
        </div>
    </div>

    <!-- datatables -->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">品牌列表</div>
                <table id="example" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>品牌名</th>
                        <th>品牌图片</th>
                        <th>热销状态</th>
                        <th>排序</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>id</th>
                        <th>品牌名</th>
                        <th>品牌图片</th>
                        <th>热销状态</th>
                        <th>排序</th>
                        <th>操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>
</div>

<span id="addBrandSpan" hidden>
    <form class="form-horizontal" action="" id="brandForm">
        <div class="form-group">
            <label for="add_brandName" class="col-sm-2 control-label">品牌名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="brandName" id="add_brandName" placeholder="brandName...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_brandImg" class="col-sm-2 control-label">品牌图片</label>
            <div class="col-sm-10">
                <input type="file" name="myfile" id="add_myfile" data-ref="add_brandImg" class="myfile"/>
                <input type="hidden" id="add_brandImg" value="">
            </div>
        </div>
    </form>

</span>

<span id="updateBrandSpan" hidden>
    <form class="form-horizontal" action="" id="updateBrandForm">
        <div class="form-group">
            <label for="update_brandName" class="col-sm-2 control-label">品牌名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="update_brandName" id="update_brandName" placeholder="brandName...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_brandImg" class="col-sm-2 control-label">品牌图片</label>
            <div class="col-sm-10">
                <input type="file" name="myfile" data-ref="update_brandImg" id="update_myfile" class="myfile"/>
                <input type="hidden" id="update_brandImg" value="">
                <input type="hidden" id="raw_brandImg" value="">
            </div>
        </div>
    </form>

</span>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>

    //备份form
    var addBrandSpan;
    var addBrandHtml;
    var updateBrandSpan;
    var updateBrandHtml;
    var table;
    $(document).ready(function () {
        initDataTables();
        //备份添加和修改span中的内容
        addBrandSpan = $('#addBrandSpan');
        addBrandHtml = addBrandSpan.html();
        addBrandSpan.html("");
        updateBrandSpan = $('#updateBrandSpan');
        updateBrandHtml = updateBrandSpan.html();
    });

    var dialog;
    //显示添加弹框
    function showAddBrand(){
        dialog = bootbox.dialog({
            title: '品牌添加',
            message: addBrandHtml,
            size: 'large',
            buttons: {
                cancel: {
                    label: "<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>&nbsp;取消",
                    className: 'btn-danger',
                    callback: function(){
                        console.log('Custom cancel clicked');
                    }
                },
                ok: {
                    label: "<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>&nbsp;确认",
                    className: 'btn-info',
                    callback: function(){
                        //obj.html("");
                        var brandName = $("[name=brandName]").val();//$("[name=brandName]")[1].value
                        var brandImg = $("#add_brandImg").val();
                        var param = {
                            brandName:brandName,
                            brandImg:brandImg,
                        };
                        $.ajax({
                            url: '/brand/addAjax.jhtml',
                            type: 'post',
                            data: param,
                            dataType:"json",
                            success: function (res) {
                                if(res.code==200){
                                    table.ajax.reload();
                                }
                            }
                        });
                        console.log('Custom OK clicked');
                    }
                }
            }
        });
        uploadFile("add_myfile");
    }

    //显示修改弹框
    function showUpdateBrand(id){
        $.ajax({
            url: '/brand/findBrand.jhtml',
            type: 'post',
            data: {'id':id},
            success: function (res) {
                if(res.code == 200){
                    var v_brand = res.data;
                    $('#raw_brandImg').val(v_brand.brandImg);
                    $('#update_brandName').val(v_brand.brandName);
                    uploadFile("update_myfile");
                    dialog = bootbox.dialog({
                        title: '品牌修改',
                        message: $('#updateBrandSpan form'),
                        size: 'large',
                        buttons: {
                            cancel: {
                                label: "<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>&nbsp;取消",
                                className: 'btn-danger',
                                callback: function(){
                                    console.log('Custom cancel clicked');
                                }
                            },
                            ok: {
                                label: "<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>&nbsp;确认",
                                className: 'btn-info',
                                callback: function(){
                                    var brandName = $("#update_brandName",dialog).val();//$("[name=brandName]")[1].value
                                    var brandImg = $("#update_brandImg").val();
                                    var rawbrandImg = $('#raw_brandImg',dialog).val();
                                    var param = {
                                        id:id,
                                        brandName:brandName,
                                        brandImg:brandImg,
                                        rawbrandImg:rawbrandImg
                                    };
                                    $.ajax({
                                        url: '/brand/updateAjax.jhtml',
                                        type: 'post',
                                        data: param,
                                        success: function (res) {
                                            if (res.code == 200) {
                                                table.ajax.reload();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                    //还原
                    updateBrandSpan.html(updateBrandHtml);
                }else if(res.code==1005){
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>"+res.msg,
                        size: 'small',
                        title: "提示信息"
                    });
                }else{
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>回显失败",
                        size: 'small',
                        title: "提示信息"
                    });
                }
            },
            error:function(res){
                alert(res.responseText);
            }
        });
    }

    //删除品牌
    function delBrand(id){
        bootbox.confirm({
            message: "确认要删除此品牌吗?",
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                console.log('This was logged in the callback: ' + result);
                if(result){
                    $.ajax({
                        url: '/brand/delete.jhtml',
                        type: 'post',
                        data: 'id=' + id,
                        success: function (res) {
                            if(res.code == 200){
                                table.ajax.reload();
                            }
                        },
                        error: function (res) {
                            alert(res.responseText);
                        }
                    });
                }
            }
        });
    }


    //初始化datatables
    function initDataTables() {
        table = $('#example').DataTable({
            //语言
            "language": {
                "url": "/js/DataTables-1.10.18/Chinese.json"
            },

            //自定义每页条数下拉框
            "lengthMenu": [5, 10, 15],

            //全局搜索框
            "searching": false,

            //排序按钮
            "ordering": false,


            //ajax请求从后台查询数据
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "/brand/list.jhtml",
                "type": "POST",
                //过滤后台传过来的数据 只拿自己需要的
                "dataSrc": function (json) {
                    //这个json 就是一个变量名
                    console.log(json.data);
                    json.draw = json.data.draw;
                    json.recordsTotal = json.data.recordsTotal;
                    json.recordsFiltered = json.data.recordsFiltered;
                    return json.data.data;
                },
            },
            "columns": [
                {"data": "id"},
                {"data": "brandName"},
                {
                    "data": "brandImg",
                    "render":function(data,type,row,meta){
                        return '<img src="'+data+'" height="150px"/>'
                    }
                },
                {
                    "data": "sellState",
                    "render":function(data){
                        return data==1?"热销":"非热销";
                    }
                },
                {
                    "data": "sort",
                    "render":function(data,type,row,meta){
                        var v_id = row.id;
                        return '<input type="email" class="form-control" id="sort_'+v_id+'" value="'+data+'">'+
                                '<button type="button" class="btn btn-info" onclick="updateBrandSort('+v_id+')">'+
                                '<span class="glyphicon glyphicon-refresh"  aria-hidden="true">刷新</span>'+
                                '</button>';
                    }
                },
                {
                    "data": "id",
                    "render": function ( data, type, row, meta ) {
                        return '<div class="btn-group" role="group" aria-label="...">' +
                            '<button type="button" class="btn btn-info">' +
                            '    <span class="glyphicon glyphicon-pencil" type="button" aria-hidden="true" onclick="showUpdateBrand(' + data + ')">修改</span>' +
                            '</button>' +
                            '<button type="button" class="btn btn-danger">' +
                            '    <span class="glyphicon glyphicon-trash" onclick="delBrand(' + data + ')" aria-hidden="true">删除</span>' +
                            '</button>' +
                            '<button type="button" class="'+(row.sellState==1?"btn btn-warning":"btn btn-success")+'">' +
                            '    <span class="'+(row.sellState==1?"glyphicon glyphicon-thumbs-down":"glyphicon glyphicon-thumbs-up")+'" onclick="updateSellState('+data+','+row.sellState+')" aria-hidden="true">'+(row.sellState==1?"不热销":"热销")+'</span>' +
                            '</button>' +
                            '</div>'
                            ;
                    }
                }
            ]
        });

    }

    function updateBrandSort(brandId){
        var v_sort = $("#sort_"+brandId).val();
        bootbox.confirm({
            message: "确定要更新吗?",
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
                if(result){
                    $.ajax({
                        url: '/brand/updateBrandSort.jhtml',
                        type: 'post',
                        data: {
                            "sort":v_sort,
                            "id":brandId
                        },
                        success: function (res) {
                            if(res.code == 200){
                                table.ajax.reload();
                            }
                        }
                    });
                }
            }
        });
    }

    function updateSellState(id,sellState){
        bootbox.confirm({
            message: sellState==1?"是否要取消热销":"是否要设热销",
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
                if(result){
                    if(sellState==1){
                        sellState=0;
                    }else{
                        sellState=1;
                    }
                    $.ajax({
                        url: '/brand/updateSellState.jhtml',
                        type: 'post',
                        data: {
                            "sellState":sellState,
                            "id":id
                        },
                        success: function (res) {
                            if(res.code == 200){
                                table.ajax.reload();
                            }
                        }
                    });
                }
            }
        });
    }

    /*图片上传*/
    function uploadFile(fileId){
        var url=$("#raw_brandImg").val();
        var urlArr=[];
        urlArr.push(url);
        $("#"+fileId).fileinput({
            //上传的地址
            uploadUrl:"/file/uploadFile.jhtml",
            showUpload : false, //是否显示上传按钮,跟随文本框的那个
            showRemove : false, //显示移除按钮,跟随文本框的那个
            dropZoneEnabled : true,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
            initialPreview:[urlArr],
            initialPreviewAsData:true,
            allowedPreviewMimeTypes : [ 'jpg', 'png', 'gif' ],//控制被预览的所有mime类型
            language : 'zh'
        })
        //异步上传返回结果处理
        $("#"+fileId).on("fileuploaded", function(event, data, previewId, index) {
            var ref = $(this).attr("data-ref");
            $("#"+ref+"").val(data.response.data);
        });
    }

    /*刷新*/
    function search(){
        var v_param = {};
        var v_brandName = $("#brandName").val();
        v_param.brandName = v_brandName;
        table.settings()[0].ajax.data = v_param;
        table.ajax.reload();
    }
</script>
</body>
</html>

