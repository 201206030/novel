var E = window.wangEditor;
var editor2 = new E('#contentEditor');
// 自定义菜单配置
editor2.customConfig.menus = [
	'head',  // 标题
	'bold',  // 粗体
	'fontSize',  // 字号
	'fontName',  // 字体
	'italic',  // 斜体
	'underline',  // 下划线
	'strikeThrough',  // 删除线
	'foreColor',  // 文字颜色
	//'backColor',  // 背景颜色
	//'link',  // 插入链接
	'list',  // 列表
	'justify',  // 对齐方式
	'quote',  // 引用
	'emoticon',  // 表情
	'image',  // 插入图片
	//'table',  // 表格
	//'video',  // 插入视频
	//'code',  // 插入代码
	'undo',  // 撤销
	'redo'  // 重复
];
editor2.customConfig.onchange = function (html) {
	// html 即变化之后的内容
	$("#content").val(html);
}
editor2.create();



$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/books/book/index/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {


	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		ignore: "",
		rules : {
			indexName : {
				required : true
			},
			content : {
				required : true
			}
		},
		messages : {
			indexName : {
				required : icon + "请输入章节名"
			},
			content : {
				required : icon + "请输入章节内容"
			}
		}
	})
}