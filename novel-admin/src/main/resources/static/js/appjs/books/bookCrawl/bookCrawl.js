var prefix = "/books/bookCrawl"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    var queryParams = getFormJson("searchForm");
                    queryParams.limit = params.limit;
                    queryParams.offset = params.offset;
                    return queryParams;
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                responseHandler: function (rs) {

                    if (rs.code == 0) {
                        return rs.data;
                    } else {
                        parent.layer.alert(rs.msg)
                        return {total: 0, rows: []};
                    }
                },
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        title: '序号',
                        formatter: function () {
                            return arguments[2] + 1;
                        }
                    },
                    {
                        field: 'crawlWebName',
                        title: '爬虫源'
                    },
                    {
                        field: 'crawlWebUrl',
                        title: '爬虫源网站URL'
                    },
                    {
                        field: 'status',
                        title: '运行状态',
                        formatter: function (value, row, index) {
                            switch (value){
                                case 1:
                                    return '运行中';
                                case 0:
                                    return '运行停止';
                            }

                        }
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var d = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="开始运行" onclick="updateStatus('+row.id+','
                                + row.status
                                +','+row.crawlWebCode
                                + ',1)"><i class="fa fa-bug"></i></a> ';
                            var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="停止运行" onclick="updateStatus('+row.id+','
                                + row.status
                                +','+row.crawlWebCode
                                + ',0)"><i class="fa fa-dot-circle-o"></i></a> ';
                            return d + e ;
                        }
                    }]
            });
}
//防表单连续点击，需要等第一个点击有结果，才能进行第二次点击
var lock = false;
function updateStatus(id,cStatus,crawlWebCode,uStatus) {
    if (lock) {
        return;
    }
    lock = true;

    if(cStatus === 1 && uStatus === 1){
        lock = false;
        parent.layer.alert("正在运行中，无需重复运行");
        return;
    }
    if(cStatus === 0 && uStatus === 0){
        lock = false;
        parent.layer.alert("已经停止运行，无需重复执行");
        return;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : "/books/bookCrawl/updateStatus",
        data : {id:id,status:uStatus,crawlWebCode:crawlWebCode},// 你的formid
        async : false,
        error : function(request) {
            lock = false;
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            lock = false;
            if (data.code == 0) {
                layer.msg(data.msg);
                reLoad();

            } else {
                layer.msg(data.msg);
                reLoad();
            }

        }
    });

}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function detail(id) {
    layer.open({
        type: 2,
        title: '详情',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/detail/' + id // iframe的url
    });
}

function edit(){
    console.log('打开配置页面');
    layer.open({
        type : 2,
        title : '爬虫配置修改',
        maxmin : true,
        shadeClose : false,
        area : [ '800px', '520px' ],
        content : prefix + '/edit'
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}