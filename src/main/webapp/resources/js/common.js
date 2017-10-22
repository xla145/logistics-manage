/** common.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */
layui.define(['layer'], function(exports) {
	"use strict";

	var $ = layui.jquery,
		layer = layui.layer;

	var common = {
		/**
		 * 抛出一个异常错误信息
		 * @param {String} msg
		 */
		throwError: function(msg) {
			throw new Error(msg);
			return;
		},
		/**
		 * 弹出一个错误提示
		 * @param {String} msg
		 */
		msgError: function(msg) {
			layer.msg(msg, {
				icon: 5
			});
			return;
		},
		/**
		 *   将post的数据转换成key-val的格式，支持基本数据类型的数组参数转换为多个key-val，不支持对象转换需自己转换后调用请求
		 * */
        arrayTokeyval: function (data) { // 将post的数据转换成key-val的格式，支持基本数据类型的数组参数转换为多个key-val，不支持对象转换需自己转换后调用请求
            let ret = ''
            for (let it in data) {
                if (data[it] instanceof Array) {
                    for (let arr of data[it]) {
                        ret += encodeURIComponent(it) + '=' + encodeURIComponent(arr) + '&'
                    }
                } else {
                    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
                }
            }
            return ret
        },
        layer_shows: function (url,title,isFull) {//弹出新窗口
            var index = layer.open({
                type: 2,
                title: title,
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                offset: ['0px'],
                area: ['1000px', '600px'],
                content: url
            });
            if (isFull){
                layer.full(index)
            }
        },
        layer_show: function (url,id,title) {//弹出窗口 类型为1
            $.get(url, {id: id}, function (html) {
                //打开编辑页
                var index = layer.open({
                    type: 1,
                    title: title,
                    shadeClose: true,
                    shade: false,
                    area: ['893px', '600px'],
                    content: html
                });
                layer.full(index)
            }, "html");
        },
        closeWindow: function () {
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        }
	};

	exports('common', common);
});