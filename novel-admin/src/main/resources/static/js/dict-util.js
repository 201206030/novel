var dictList = parent.dictList;
$(function () {

    $(".chosen-select").each(function (index, domEle) {

        var dictType = $(domEle).attr("dict-type");
        var dictValue = $(domEle).attr("dict-value");
        var changeFunc = $(domEle).attr("dict-change-func");
        if (dictType) {
            var html = "";
            // 加载数据
            for (var i = 0; i < dictList.length; i++) {
                if (dictList[i].type == dictType) {
                    html += '<option value="' + dictList[i].value + '">' + dictList[i].name + '</option>'

                }
            }
            $(domEle).append(html);
            $(domEle).chosen({
                maxHeight: 200
            });
            $(domEle).val(dictValue);
            $(domEle).trigger("chosen:updated");
            // 点击事件
            $(domEle).on('change', function (e, params) {
                eval(changeFunc+'()');
            });
        }


    });


});


function formatDict(dictType, value) {
    var name = "";
    // 加载数据
    for (var i = 0; i < dictList.length; i++) {

        if (dictList[i].type == dictType && dictList[i].value == value) {
            name = dictList[i].name;
        }
    }

    return name;


}


