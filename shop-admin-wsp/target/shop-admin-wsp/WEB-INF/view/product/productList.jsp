<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/23
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>商品展示页面</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">商品查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="productForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商品名称</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" name="productName" id="productName" placeholder="请输入商品名...">
                            </div>
                            <label class="col-sm-2 control-label">价格</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" name="price" id="price" placeholder="请输入价格...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">生产日期</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="minTime" id="minTime" placeholder="开始日期..." aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" name="maxTime" id="maxTime" placeholder="结束日期..." aria-describedby="basic-addon2">
                                </div>
                            </div>
                            <label  class="col-sm-2 control-label">品牌</label>
                            <div class="col-sm-4" id="selectBrandSelect">

                            </div>
                        </div>
                        <div class="form-group" id="selectCategory">
                            <label  class="col-sm-2 control-label">分类</label>

                        </div>
                        <div class="form-group">
                            <div align="center">
                                <button class="btn btn-primary" type="button" onclick="search()"><i class="glyphicon glyphicon-search"></i>查询</button>
                                <button class="btn btn-default" type="reset"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div style="background-color: #0facd2">
        <button class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i>添加</button>
        <button class="btn btn-success" onclick="exportExcel()"><i class="glyphicon glyphicon-plus"></i>导出excel</button>
        <button class="btn btn-success" onclick="exportPdf()"><i class="glyphicon glyphicon-plus"></i>导出pdf</button>
        <button type="button" class="btn btn-success" onclick="exportWord();">导出Word</button>
        <button class="btn btn-primary" onclick="sweepCache()"><i class="glyphicon glyphicon-refresh"></i>清除缓存</button>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">商品列表</div>
                <table id="productTable" class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">热销状态</th>
                        <th style="text-align: center;">上架状态</th>
                        <th style="text-align: center;">分类</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">热销状态</th>
                        <th style="text-align: center;">上架状态</th>
                        <th style="text-align: center;">分类</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<%--添加--%>
<div id="addProduct" style="display: none">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_productName" placeholder="请输入商品名称...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">价格</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_price" placeholder="请输入价格...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">生产日期</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_producedDate" placeholder="请选择生产日期...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">产品主图</label>
            <div class="col-sm-10">
                <input type="file" name="myfile" id="add_myfile" data-ref="add_mainImagePath" class="prfile"/>
                <input type="hidden" id="add_mainImagePath" value="">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_stock" placeholder="请输入库存...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">热销状态</label>
            <div class="col-sm-4">
                <input type="radio" name="add_sellState" value="1">热销
                <input type="radio" name="add_sellState" value="2">不热销
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌</label>
            <div class="col-sm-4" id="addBrandSelect">

            </div>
        </div>
        <div class="form-group" id="add_Category">
            <label  class="col-sm-2 control-label">分类</label>

        </div>
    </form>
</div>
<%--修改--%>
<div id="updateProduct" style="display: none;">
    <form class="form-horizontal">
        <input type="hidden" id="id">
        <div class="form-group">
            <label  class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_productName" placeholder="请输入商品名称...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">价格</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_price" placeholder="请输入价格...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">生产日期</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_producedDate" placeholder="请选择生产日期...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">产品主图</label>
            <div class="col-sm-10">
                <input type="file" name="myfile" data-ref="update_mainImagePath" id="update_myfile" class="prfile"/>
                <input type="hidden" id="update_mainImagePath" value="">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_stock" placeholder="请输入库存...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">热销状态</label>
            <div class="col-sm-4">
                <input type="radio" name="update_sellState" value="1">热销
                <input type="radio" name="update_sellState" value="2">不热销
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌</label>
            <div class="col-sm-4" id="updateBrandSelect">

            </div>
        </div>
        <div class="form-group" id="update_Category">
            <label  class="col-sm-2 control-label">分类</label>

        </div>
    </form>
</div>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var addProductDiv;
    var updateProductDiv;
    $(function(){
        toList();
        //备份
        addProductDiv = $("#addProduct").html()
        updateProductDiv = $("#updateProduct").html();
        addTime("minTime");
        addTime("maxTime");
        findBrandSelect("selectBrandSelect","select_brand");
        findCategorySelect("selectCategory",0);
    })
    var productTable;
    /*查询商品*/
    function toList(){
        var columName  = [
            {
                "data":"id",
                "render":function(data){//这个data就是咱们查到的pageInfo中的数据集合里的对象
                    return '<input name="ids" type="checkbox" value="'+data+'"/>'
                }
            },
            {"data":"id"},
            {"data":"productName"},
            {"data":"price"},
            {
                "data":"mainImagePath",
                "render":function(data,type,row,meta){
                    return '<img src="'+data+'" height="150px"/>'
                }
            },
            {"data":"producedDate"},
            {"data":"stock"},
            {"data":"brand.brandName"},
            {
                "data":"sellState",
                "render":function(data){
                    return data==1?"热销":"不热销";
                }
            },
            {
                "data":"status",
                "render":function(data){
                    return data==1?"正常":"下架";
                }
            },
            {"data":"categoryNames"},
            {
                "data":"id",
                "render":function(data,type,row,meta){//第一个参数 和data一样  第三个参数是所有数据
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteProduct(\''+data+'\',\''+row.mainImagePath+'\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span>删除</button>'+
                        '<button class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal" onclick="toUpdateProduct(\''+data+'\')"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>修改</button>' +
                        '<button class="'+(row.status==1?"btn btn-warning btn-sm":"btn btn-success btn-sm")+'" data-toggle="modal" data-target="#myModal" onclick="updateStatus('+row.status+','+data+')">'+(row.status==1?"下架":"上架")+'</button>' +
                        '</div>'
                }
            },
        ];
        /* 渲染datatables */
        productTable = $('#productTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ordering": false,//是否启用排序
            "ajax": {
                "url":"/product/productList.jhtml",
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
            searching: false,
            "lengthMenu": [ 5, 10, 20, 40, 80 ],
            language: {
                "url": "/js/DataTables-1.10.18/Chinese.json"
            },
            columns:columName,//设置列的初始化属性
        } );
    }

    /*手动清除缓存*/
    function sweepCache(){
        $.ajax({
            url:"/cache/sweepCacheProduct.jhtml",
            type:"post",
            success:function(res){
                if(res.code==200){
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>刷新成功",
                        size: 'small',
                        title: "提示信息"
                    });
                }
            }
        })
    }

    /*上架 下架*/
    function updateStatus(status,id){
        bootbox.confirm({
            message: status==1?"你确定要下架吗?":"你确定要上架吗?",
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
                    $.ajax({
                        url:"/product/updateStatus.jhtml",
                        type:"post",
                        data:{
                            "status":status,
                            "id":id
                        },
                        dataType:"json",
                        success:function(res){
                            if(res.code==200){
                                console.log(res.msg);
                                search();
                            }
                        }
                    })
                }
            }
        })
    }
    /*这是刷新方法*/
    function search(){
        var productName = $("#productName").val();
        var price = $("#price").val();
        var minTime = $("#minTime").val();
        var maxTime = $("#maxTime").val();
        var brandId = $("#select_brand").val();
        var v_cate1 = $($("[name='selectCategory']")[0]).val();
        var v_cate2 = $($("[name='selectCategory']")[1]).val();
        var v_cate3 = $($("[name='selectCategory']")[2]).val();
        var v_param = {};
        v_param.productName = productName;
        v_param.price = price;
        v_param.minTime = minTime;
        v_param.maxTime = maxTime;
        v_param.brandId = brandId;
        v_param.cate1 = v_cate1;
        v_param.cate2 = v_cate2;
        v_param.cate3 = v_cate3;
        productTable.settings()[0].ajax.data = v_param;
        productTable.ajax.reload();
    }
    /*添加商品*/
    var addFrom;
    function add(){
        addTime("add_producedDate");
        findCategorySelect("add_Category",0);
        findBrandSelect("addBrandSelect","add_brand");
        uploadFileproduct("add_myfile");
        addFrom = bootbox.dialog({
            title: '添加商品',
            message: $("#addProduct form"),
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
                        var v_productName = $("#add_productName",addFrom).val();
                        var v_price = $("#add_price",addFrom).val();
                        var v_producedDate = $("#add_producedDate",addFrom).val();
                        var v_mainImagePath = $("#add_mainImagePath").val();
                        var v_stock = $("#add_stock",addFrom).val();
                        var v_brandId = $("#add_brand",addFrom).val();
                        var v_sellState = $("[name='add_sellState']:checked",addFrom).val();
                        var v_cate1 = $($("[name='add_Category']",addFrom)[0]).val();
                        var v_cate2 = $($("[name='add_Category']",addFrom)[1]).val();
                        var v_cate3 = $($("[name='add_Category']",addFrom)[2]).val();
                        var str = [];
                        $("[name='add_Category'] option:selected",addFrom).each(function(){
                            if($(this).val()!=-1){
                                var cateName = $(this).text();
                                str.push(cateName)
                            }
                        })
                        str = str.join("--->")
                        var v_param = {};
                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.stock = v_stock;
                        v_param.sellState = v_sellState;
                        v_param.brandId = v_brandId;
                        v_param.cate1 = v_cate1;
                        v_param.cate2 = v_cate2;
                        v_param.cate3 = v_cate3;
                        v_param.categoryNames = str;
                        $.ajax({
                            url:"/product/add.jhtml",
                            data:v_param,
                            type:"post",
                            dataType:"json",
                            success:function(res){
                                if(res.code==200){
                                    console.log(res.msg);
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        });
        //还原
        $("#addProduct").html(addProductDiv);
    }
    /*图片上传*/
    function uploadFileproduct(fileId){
        var url='http://169.254.33.76/Public/'+$("#update_mainImagePath").val();
        var urlArr=[];
        urlArr.push(url);
        $("#"+fileId).fileinput({
            //上传的地址
            uploadUrl:"/file/uploadFile.jhtml",
            showUpload : false, //是否显示上传按钮,跟随文本框的那个
            showRemove : false, //显示移除按钮,跟随文本框的那个
            initialPreview:[urlArr],//回显图片
            initialPreviewAsData:true,
            dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
            allowedPreviewMimeTypes : [ 'jpg', 'png', 'gif' ],//控制被预览的所有mime类型
            language : 'zh'
        })
        //异步上传返回结果处理
        $("#"+fileId).on("fileuploaded", function(event, data, previewId, index) {
            var ref = $(this).attr("data-ref");
            $("#"+ref+"").val(data.response.data,update);
        });
    }
    /*时间插件*/
    function addTime(name){
        $("#"+name).datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        })
    }
    /*删除*/
    function deleteProduct(id,mainImagePath){
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
                        url:"/product/deleteProduct.jhtml",
                        type:"post",
                        data:{
                            "id":id,
                            "mainImagePath":mainImagePath
                        },
                        dataType:"json",
                        success:function(res){
                            if(res.code==200){
                                console.log(res.msg);
                                search();
                            }
                        }
                    })
                }
            }
        })
    }
    /*回显数据*/
    function toUpdateProduct(id){
        findBrandSelect("updateBrandSelect","update_brand");
        addTime("update_producedDate");
        $.ajax({
            url:"/product/toUpdateProduct.jhtml",
            data:{
                "id":id
            },
            dataType:"json",
            type:"post",
            success:function(res){
                if(res.code==200){
                    var data = res.data;
                    $("#id").val(data.id);
                    $("#update_productName").val(data.productName);
                    $("#update_price").val(data.price);
                    $("#update_producedDate").val(data.producedDate);
                    $("#update_mainImagePath").val(data.mainImagePath);
                    $("#update_stock").val(data.stock);
                    $("[name='update_sellState']").each(function(){
                        if ($(this).val() == data.sellState) {
                            $(this).attr("checked", true);
                        }
                    })
                    $("#update_brand").val(data.brand.id);
                    var categoryNames = data.categoryNames;
                    var categoryNameDiv = '<label class="col-sm-3 control-label" id="categoryNameDiv">';
                    categoryNameDiv += categoryNames;
                    categoryNameDiv +='</label>'
                    $("#update_Category").append(categoryNameDiv);
                    var str = '<button class="btn btn-info btn-sm" type="button" onclick="toUpdateCategoryName()"><i id="bTest">操作</i></button>'
                    $("#update_Category").append(str);
                    updateProduct();
                }
            }
        })
    }
    /*修改显示分类*/
    var flog = 0;
    function toUpdateCategoryName(){
        if(flog==1){
            $("#update_Category div",update).remove();
            $("#bTest",update).html("操作");
            $("#categoryNameDiv",update).css("display","inline");
            flog = 0;
        }else{
            $("#bTest",update).html("取消操作");
            $("#categoryNameDiv",update).css("display","none");
            findCategorySelect("update_Category",0);
            flog = 1 ;
        }
    }
    /*修改*/
    var update;
    function updateProduct(){
        flog = 0;
        uploadFileproduct("update_myfile");
        update = bootbox.dialog({
            title: '修改用户',
            message: $("#updateProduct form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function(){
                        console.log('Custom cancel clicked');
                    }
                },
                confirm: {
                    label: "修改",
                    className: 'btn-primary',
                    callback: function(){
                        var id = $("#id",update).val();
                        var v_productName = $("#update_productName",update).val();
                        var v_price = $("#update_price",update).val();
                        var v_producedDate = $("#update_producedDate",update).val();
                        var v_mainImagePath = $("#update_mainImagePath").val();
                        var v_stock = $("#update_stock",update).val();
                        var v_brandId = $("#update_brand",update).val();
                        var v_sellState = $("[name='update_sellState']:checked",update).val();
                        var category = $("[name='update_Category']",update).length;
                        if(category==3){
                            var v_cate1 = $($("[name='update_Category']",update)[0]).val();
                            var v_cate2 = $($("[name='update_Category']",update)[1]).val();
                            var v_cate3 = $($("[name='update_Category']",update)[2]).val();
                            var str = [];
                            $("[name='update_Category'] option:selected",update).each(function(){
                                if($(this).val()!=-1){
                                    var cateName = $(this).text();
                                    str.push(cateName)
                                }
                            })
                            str = str.join("--->")
                        }
                        var v_param = {};
                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.id = id;
                        v_param.stock = v_stock;
                        v_param.sellState = v_sellState;
                        v_param.brandId = v_brandId;
                        v_param.cate1 = v_cate1;
                        v_param.cate2 = v_cate2;
                        v_param.cate3 = v_cate3;
                        v_param.categoryNames = str;
                        $.ajax({
                            url:"/product/updateProduct.jhtml",
                            data:v_param,
                            dataType:"json",
                            type:"post",
                            success:function(res){
                                if(res.code==200){
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        })
        //还原
        $("#updateProduct").html(updateProductDiv);
    }
    /*导出excel*/
    function exportExcel(){
        var productForm = document.getElementById("productForm");
        productForm.action = "/product/exportExcel.jhtml";
        productForm.method = "post";
        productForm.submit();
    }
    /*导出pdf*/
    function exportPdf(){
        var productForm = document.getElementById("productForm");
        productForm.action = "/product/exportPdf.jhtml";
        productForm.method = "post";
        productForm.submit(); 
    }
    /*导出word*/
    function exportWord(){
        // 获取form表单
        var v_productForm = document.getElementById("productForm");
        // 动态设置action属性
        v_productForm.action = "/product/exportWord.jhtml";
        v_productForm.method = "post";
        v_productForm.submit();
    }
    /*品牌下拉框*/
    function findBrandSelect(divId,selectId){
        $.ajax({
            url:"/brand/findBrandList.jhtml",
            type:"post",
            async:false,
            success:function(res){
                console.log(res.data);
                var data = res.data;
                var str = "<select class=\"form-control\" id='"+selectId+"'>"
                str += "<option value='-1'>===请选择===</option>"
                for(var i=0;i<data.length;i++){
                    str += "<option value='"+data[i].id+"'>"+data[i].brandName+"</option>"
                }
                str += "</select>";
                $("#"+divId).html(str);
            }
        })
    }
    /*分类三级联动*/
    function findCategorySelect(divId, categoryId, obj){
        if (obj) {
            // parent  返回元素的第一个父节点
            // nexAll 返回被选元素之后的所有同级元素
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type:"post",
            url:"/category/findCategorySelect.jhtml",
            data:{"id":categoryId},
            success:function (data) {
                if (data.code == 200) {
                    if (data.data.length == 0) {
                        return;
                    }
                    // 在指定的div中追加select
                    var select = '<div class="col-sm-2"><select class="form-control" name="'+divId+'" onchange="findCategorySelect(\''+divId+'\', this.value, this)"><option value="-1">==请选择==</option>';
                    var data = data.data;
                    for (var i = 0; i < data.length; i++) {
                        select += "<option value='"+data[i].id+"'>"+data[i].categoryName+"</option>";
                    }
                    select += "</select></div>";
                    if (divId == "selectCategory") {
                        $("#"+divId).append(select);
                    }else if(divId == "add_Category"){
                        $("#"+divId,addFrom).append(select);
                    }else if(divId == "update_Category"){
                        $("#"+divId,update).append(select);
                    }
                }
            }
        })
    }
</script>
</html>
