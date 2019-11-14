layui.define(['layer', 'carousel'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var carousel = layui.carousel;

    // 添加步骤条dom节点
    var renderDom = function (elem, stepItems, postion) {
        var stepDiv = '<div class="lay-step">';
        for (var i = 0; i < stepItems.length; i++) {
            stepDiv += '<div class="step-item">';
            // 线
            if (i < (stepItems.length - 1)) {
                if (i < postion) {
                    stepDiv += '<div class="step-item-tail"><i class="step-item-tail-done"></i></div>';
                } else {
                    stepDiv += '<div class="step-item-tail"><i class=""></i></div>';
                }
            }

            // 数字
            var number = stepItems[i].number;
            if (!number) {
                number = i + 1;
            }
            if (i == postion) {
                stepDiv += '<div class="step-item-head step-item-head-active"><i class="layui-icon">' + number + '</i></div>';
            } else if (i < postion) {
                stepDiv += '<div class="step-item-head"><i class="layui-icon layui-icon-ok"></i></div>';
            } else {
                stepDiv += '<div class="step-item-head "><i class="layui-icon">' + number + '</i></div>';
            }

            // 标题和描述
            var title = stepItems[i].title;
            var desc = stepItems[i].desc;
            if (title || desc) {
                stepDiv += '<div class="step-item-main">';
                if (title) {
                    stepDiv += '<div class="step-item-main-title">' + title + '</div>';
                }
                if (desc) {
                    stepDiv += '<div class="step-item-main-desc">' + desc + '</div>';
                }
                stepDiv += '</div>';
            }
            stepDiv += '</div>';
        }
        stepDiv += '</div>';

        $(elem).prepend(stepDiv);

        // 计算每一个条目的宽度
        var bfb = 100 / stepItems.length;
        $('.step-item').css('width', bfb + '%');
    };

    var step = {
        // 渲染步骤条
        render: function (param) {
            param.indicator = 'none';  // 不显示指示器
            param.arrow = 'always';  // 始终显示箭头
            param.autoplay = false;  // 关闭自动播放
            if (!param.stepWidth) {
                param.stepWidth = '400px';
            }

            // 渲染轮播图
            carousel.render(param);

            // 渲染步骤条
            var stepItems = param.stepItems;
            renderDom(param.elem, stepItems, 0);
            $('.lay-step').css('width', param.stepWidth);

            //监听轮播切换事件
            carousel.on('change(' + param.filter + ')', function (obj) {
                $(param.elem).find('.lay-step').remove();
                renderDom(param.elem, stepItems, obj.index);
                $('.lay-step').css('width', param.stepWidth);
            });

            // 隐藏左右箭头按钮
            $(param.elem).find('.layui-carousel-arrow').css('display', 'none');

            // 去掉轮播图的背景颜色
            $(param.elem).css('background-color', 'transparent');
        },
        // 下一步
        next: function (elem) {
            $(elem).find('.layui-carousel-arrow[lay-type=add]').trigger('click');
        },
        // 上一步
        pre: function (elem) {
            $(elem).find('.layui-carousel-arrow[lay-type=sub]').trigger('click');
        }
    };

    layui.link(layui.cache.base + 'step-lay/step.css');

    exports('step', step);
});
