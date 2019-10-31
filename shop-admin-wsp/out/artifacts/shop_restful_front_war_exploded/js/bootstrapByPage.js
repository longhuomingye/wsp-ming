/**
 * bootstrap特效的分页js工具
 */
$(document).ready(function(){
	var htmlCode = '<ul class="pager pagination-sm">'+
					   '<li><a onclick="goFirst()">首页</a></li>'+
					   '<li><a onclick="goPri()">上一页</a></li>'+
					   '<li>第<span id="cpage">1</span>页&nbsp; 共<span id="totalPage">0</span>页 </li>'+
					   '<li><a onclick="goNext()">下一页</a></li>'+
					   '<li><a onclick="goLast()">尾页</a>展示</li>'+
					   '<li>'+
						   '<select id="pageSize" onchange="changePageSize()">'+
						   '<option value="5">5</option>'+
						   '<option value="10">10</option>'+
						   '<option value="15">15</option>'+
						   '</select>条'+
					   '</li>'+
					   '<li><input type="text" id="targetPage" style="width: 60px"></li>'+
					   '<li><button class="btn btn-sm btn-info" onclick="goToPage()">Go</button></li>'+
					   '<li><font id="pager_tips" style="color: red;"></font></li>'+
				   '</ul>';
	$("#pager_tools").html(htmlCode);
});

function goNext(){
	var cpage = Number($("#cpage").html());
	var totalPage = Number($("#totalPage").html());
	if(cpage + 1 > totalPage){
		$("#pager_tips").html("已经是最后一页");
	}else{
		$("#pager_tips").html("");
		$("#cpage").html(cpage+1);
		getData();
	}
}

function goPri(){
	var cpage = Number($("#cpage").html());
	if(cpage - 1 < 1){
		$("#pager_tips").html("已经是第一页");
	}else{
		$("#pager_tips").html("");
		$("#cpage").html(cpage-1);
		getData();
	}
}

function goFirst(){
	$("#pager_tips").html("");
	$("#cpage").html(1);
	getData();
}

function goLast(){
	$("#pager_tips").html("");
	var totalPage = Number($("#totalPage").html());
	$("#cpage").html(totalPage);
	getData();
}

function changePageSize(){
	$("#pager_tips").html("");
	$("#cpage").html(1);
	getData();
}

function goToPage(){
	var targetPage = Number($("#targetPage").val());
	var totalPage = Number($("#totalPage").html());
	if( targetPage > totalPage){
		targetPage = totalPage;
	}
	$("#pager_tips").html("");
	$("#cpage").html(targetPage);
	$("#targetPage").val(targetPage);
	getData();
}