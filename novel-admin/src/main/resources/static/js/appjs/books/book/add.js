$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});

function save() {
    $.ajax({
        cache: true,
        type: "POST",
        url: "/books/book/save",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
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
        rules: {
            catid: {
                required: true
            },
            picUrl: {
                required: true
            },
            bookName: {
                required: true
            },
            author: {
                required: true
            },
            bookDesc: {
                required: true
            },
            score: {
                required: true
            },
            bookStatus: {
                required: true
            }

        },
        messages: {
            catid: {
                required: icon + "请选择分类"
            },
            picUrl: {
                required: icon + "请选择封面"
            },
            bookName: {
                required: icon + "请填写书名"
            },
            author: {
                required: icon + "请填写作者"
            },
            bookDesc: {
                required: icon + "请填写简介"
            },
            score: {
                required: icon + "请填写评分"
            },
            bookStatus: {
                required: icon + "请填写更新状态"
            }
        }
    })
}