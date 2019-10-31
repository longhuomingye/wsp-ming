<%--
  Created by IntelliJ IDEA.
  User: ZYT
  Date: 2019/10/21
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员列表</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">会员查询</div>
            <div class="panel-body">
                <form class="form-horizontal" id="shopSearchFrom">
                    <input type="hidden" name="classify1" id="classify1">
                    <input type="hidden" name="classify2" id="classify2">
                    <input type="hidden" name="classify3" id="classify3">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label" >会员名</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="memberName"  placeholder="请输入会员名...">
                        </div>
                        <label  class="col-sm-2 control-label">手机号</label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input type="text" class="form-control" id="phone"  placeholder="请输入手机号...">

                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">生产日期</label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input type="text" class="form-control" id="minDate"  placeholder="开始时间..." >
                                <span class="input-group-addon" ><i class="glyphicon glyphicon-calendar"></i></span>
                                <input type="text" class="form-control" id="maxDate"  placeholder="结束时间..." >
                            </div>
                        </div>
                        <label  class="col-sm-2 control-label" >地区</label>
                        <div id="search_area">
                        </div>
                    </div>


                    <div style="text-align: center">
                        <button class="btn btn-primary" type="button" onclick="search()">
                            <span class="glyphicon glyphicon-search" ></span>查询
                        </button>
                        <button class="btn btn-default" type="reset">
                            <span class="glyphicon glyphicon-refresh" ></span>重置
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">会员列表</div>
            <table id="example" class="table table-striped table-bordered" style="width:100%">
                <thead>
                <tr>
                    <td>id</td>
                    <td>会员名称</td>
                    <td>真实姓名</td>
                    <td>手机号</td>
                    <td>邮箱</td>
                    <td>生日</td>
                    <td>地区</td>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    $(function () {
        initMemberTable();
        initAreaSelect("search_area",0);
        ininSearchDate();
    })

    //日期
    function initDate(dateName) {
        $('#'+dateName).datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
    }
    function ininSearchDate() {
        initDate('minDate');
        initDate('maxDate');
    }

    var table;
    function initMemberTable() {
        table=$('#example').DataTable( {
            "processing": true,
            "serverSide": true,
            "searching":false,
            "lengthMenu":[5,10,15],
            "ajax": {
                "url": "/members/queryMemberJson.jhtml",
                "type": "POST",
                "dataSrc": function (result) {
                    //console.log(result.data);
                    result.draw=result.data.draw;
                    result.recordsTotal=result.data.recordsTotal;
                    result.recordsFiltered=result.data.recordsFiltered;
                    return result.data.data;
                }
            },
            "language":{
                "url":"/js/DataTables/Chinese.json"
            },
            "columns": [
                {"data":"id"},
                {"data":"memberName"},
                {"data":"realName"},
                {"data":"phone"},
                {"data":"email"},
                {"data":"brithdy"},
                {"data":"areanames"},
            ],
        })
    }

    function search() {
        var memberName=$("#memberName").val();
        var phone=$("#phone").val();
        var minDate=$("#minDate").val();
        var maxDate=$("#maxDate").val();
        var v_area1=$($("select[name='areaSelect']")[0]).val();
        var v_area2=$($("select[name='areaSelect']")[1]).val();
        var v_area3=$($("select[name='areaSelect']")[2]).val();

        var data={
            "memberName":memberName,
            "phone":phone,
            "minDate":minDate,
            "maxDate":maxDate,
            "area1": v_area1,
            "area2": v_area2,
            "area3": v_area3,

        }
        table.settings()[0].ajax.data=data;
        table.ajax.reload();
    }


    function initAreaSelect(paramDiv,id,obj) {
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.post({
            url:"/area/findAllAreaByPid.jhtml",
            data:{"pid": id},
            success:function (result) {
                if (result.code == 200) {
                    //console.log(result.data);
                    if (result.data.length!=0) {
                        // 在指定的div中追加select
                        var v_str = '<div class="col-sm-1"><select class="form-control" name="areaSelect" onchange="initAreaSelect(\''+paramDiv+'\', this.value,this)"><option value="-1">请选择</option>';
                        var v_areaArr = result.data;
                        for (var i = 0; i < v_areaArr.length; i++) {
                            str += "<option value='" + v_areaArr[i].id + "'>" + v_areaArr[i].areaName + "</option>";
                        }
                        v_str += "</select></div>";
                        if (paramDiv == "search_area") {
                            $("#"+paramDiv).append(v_str);
                        }
                    }
                }
            }
        })
    }
</script>
</body>
</html>
