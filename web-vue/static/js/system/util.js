$.ajaxSetup({
    headers: {
        'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    }
});

var util = {

    is_email: function ($email) {
        var search_str = /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
        return search_str.test($email);
    },

    show_loading: true,

    ajax_json: function (url, cb, params, failcb) {
        if (params == undefined) params = {};
        if (this.show_loading)
            $("#j_ajax_loading").show();
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
            }
        });
        $.ajax({
            type: 'post',
            url: url,
            data: params,
            dataType: 'json',
            success: function (data) {
                try {
                    $("#j_ajax_loading").fadeOut(500);
                    if (data.error == -9999) {
                        layer.msg("你无权访问，请检查登录是否过期或者权限受限");
                        return false;
                    }
                    cb(data);
                } catch (e) {

                    if (e && e.message)
                        layer.msg(e.message);
                    else
                        layer.msg("访问失败！");

                    if (failcb)
                        failcb();
                    $("#j_ajax_loading").fadeOut(500);
                }
            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
                $("#j_ajax_loading").fadeOut(500);
            }
        });
    },
    mobile_alert: function (msg, title) {
        if (window.layer == undefined) {
            var js_url_arr = ["/js/layer/layer.js"];
            util.syn_load_js(js_url_arr, function () {
                layer.ready(function () {
                    layer.msg(msg, {time: 3000});
                });
            });
        } else {
            layer.msg(msg, {time: 3000});
        }
    },
    load_js: function (js_url, cb) {
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.src = js_url;
        script.onload = function () {
            if (cb)
                cb();
        };
        var head = $('head')[0];
        head.appendChild(script);
    },
    syn_load_js: function (js_url_arr, cb) {
        if (!(js_url_arr instanceof Array))
            return false;
        if (js_url_arr.length == 1) {
            util.load_js(js_url_arr[0], cb);
        } else {
            util.load_js(js_url_arr[0], function () {
                js_url_arr.shift();
                util.syn_load_js(js_url_arr, cb);
            });
        }
    },
    count: function (o) {
        if (typeof(o) == 'object') {
            var ct = 0;
            for (var i in o) {
                ct++;
            }
            return ct;
        }
        if (typeof(o) == 'array' || typeof(o) == 'string') {
            return o.length;
        }
        return 0;
    },

    is_http_url: function (str_url) {
        var strRegex = '^((https|http)?://)'
            + '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@
            + '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184
            + '|' // 允许IP和DOMAIN（域名）
            + '([0-9a-z_!~*\'()-]+.)*' // 域名- www.
            + '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名
            + '[a-z]{2,6})' // first level domain- .com or .museum
            + '(:[0-9]{1,4})?' // 端口- :80
            + '((/?)|' // a slash isn't required if there is no file name
            + '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
        strRegex = "^(https|http)://.*";
        var re = new RegExp(strRegex);
        if (re.test(str_url)) {
            return (true);
        } else {
            return (false);
        }
    },

    goods_dlg: null,

    random_str: function (len) {
        len = len || 32;
        var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
        var maxPos = chars.length;
        var rnd = "";
        for (i = 0; i < len; i++) {
            rnd += chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return rnd;
    },

    init_ajaxfileupload: function (upload_btn_id, file_el_id, cb, formData, accept, multiple, fileSizeLimit) {
        if (fileSizeLimit == undefined) fileSizeLimit = 5 * 1024000;
        if (formData == undefined) formData = {};
        formData.elname = file_el_id;
        $.ajaxSetup({headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')}});
        formData['X-CSRF-TOKEN'] = $('meta[name="csrf-token"]').attr('content');
        var button = $('#' + upload_btn_id);
        var url = location.href;
        var action = '/system/image/upload';
        new window.AjaxUpload(button, {
            action: action,
            name: file_el_id,
            data: formData,
            responseType: 'json',
            multiple: multiple,
            accept: accept,
            onChange: function (file, extension) {
                if (formData.dynamic_data) {
                    for (var i in formData.dynamic_data) {
                        var t = formData.dynamic_data[i];
                        formData[t.name] = $(t.selector).val();
                    }
                    this.setData(formData);
                }
            },
            onSubmit: function (file, ext) {
                this.disable();
            },
            onComplete: function (file, response) {//上传成功的函数；response代表服务器返回的数据
                this.enable();
                try {
                    if (cb) {
                        cb(response);
                    }
                } catch (e) {
                    if (cb) {
                        cb(false, e);
                    }
                }
            }
        });
    },
    init_ajax_video_upload: function (upload_btn_id, file_el_id, cb, formData, accept, multiple, fileSizeLimit) {
        if (fileSizeLimit == undefined) fileSizeLimit = 5 * 1024000;
        if (formData == undefined) formData = {};
        formData.elname = file_el_id;
        $.ajaxSetup({headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')}});
        formData['X-CSRF-TOKEN'] = $('meta[name="csrf-token"]').attr('content');
        var button = $('#' + upload_btn_id);
        // var url = location.href;
        var action = '/system/manage/video/upload';
        // if (url.indexOf("/admin/frame/video/dialog") != -1) {
        //     action = '/admin/public/image/account/upload';
        // }

        new window.AjaxUpload(button, {
            action: action,
            name: file_el_id,
            data: formData,
            responseType: 'json',
            multiple: multiple,
            accept: accept,
            onChange: function (file, extension) {
                if (formData.dynamic_data) {
                    for (var i in formData.dynamic_data) {
                        var t = formData.dynamic_data[i];
                        formData[t.name] = $(t.selector).val();
                    }
                    this.setData(formData);
                }
            },
            onSubmit: function (file, ext) {
                this.disable();
            },
            onComplete: function (file, response) {//上传成功的函数；response代表服务器返回的数据
                this.enable();
                try {
                    if (cb) {
                        cb(response);
                    }
                } catch (e) {
                    if (cb) {
                        cb(false, e);
                    }
                }
            }
        });
    },
    init_image_upload: function (upload_btn_id, cb, formData, multiple) {
        var file_el_id = "ajax_file_id_" + parseInt(1000 * Math.random());
        this.init_ajaxfileupload(upload_btn_id, file_el_id, cb, formData, 'image/gif,image/jpeg,image/x-png', multiple);
    },

    init_video_upload: function (upload_btn_id, cb, formData, multiple) {
        var file_el_id = "ajax_file_id_" + parseInt(1000 * Math.random());
        // console.log(formData)
        this.init_ajax_video_upload(upload_btn_id, file_el_id, cb, formData, 'video/H264,video/mp4', multiple);
    },
    add_share_param: function (url) {
        return url.indexOf("?") != -1 ? url + "&ispcshared=1" : url + "?ispcshared=1";
    },
    init_zero_clipboard: function (el) {
        var clip = new ZeroClipboard($(el));
        clip.on("ready", function () {
            this.on("aftercopy", function (event) {
                layer.msg(window.jslang.util.copy_success);
            });
        });
        clip.on("error", function (event) {
            layer.msg(window.jslang.util.please_install_flash);
            ZeroClipboard.destroy();
        });
    },

    replaceParamVal: function (oldUrl, paramName, replaceWith) {
        var re = eval('/(' + paramName + '=)([^&]*)/gi');
        var nUrl = oldUrl.replace(re, paramName + '=' + replaceWith);
        return nUrl;
    },
    hasParameter: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },
    alert: function (msg) {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + msg + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
            });
        });
    },
    systemNotice: function (obj_n, con2, mod) {//该弹框可关闭，只做一个提示作用
        /*
        *  参数说明，obj_n为1 是功能提示
        *          obj_n为2，和con2配合使用， 作为数量提示
        *          其中，con2的内容一般如下事例，商品数量达最大2个
        *          mod 为调整链接时url带的参数，为当前版本的名称，例如：砍价
        * */
        var content;
        var content1 = '此功能需要更高版本才可使用。如需了解详情我们的产品顾问将尽快与您联系！！！';
        var content2 = '您当前' + con2 + '限制，扩大限制额度需要更高版本才可使用。如需了解详情，我们的产品顾问将尽快与您联系！！';
        if (obj_n == 1) {
            content = content1;
        }
        if (obj_n == 2) {
            content = content2;
        }
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.closeAll();
            layer.open({
                type: 1
                , title: ['系统通知', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '360px'
                , content: content //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['了解更多', '下次再说']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , skin: 'demo-class'
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    //window.location.href = '/admin/version/notice?route=aaa&name=bbb'; //当前页面打开
                    window.open('/admin/version/notice?mod_name=' + mod);//新标签打开
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });

    },
    systemNotice2: function (obj_n, con2, mod) {//该弹框参数说明同上，区别在于该弹框不可关闭，用于直接跳转页面
        var content;
        var content1 = '此功能需要更高版本才可使用。如需了解详情我们的产品顾问将尽快与您联系！！！';
        var content2 = '您当前' + con2 + '限制，扩大限制额度需要更高版本才可使用。如需了解详情，我们的产品顾问将尽快与您联系！！';
        if (obj_n == 1) {
            content = content1;
        }
        if (obj_n == 2) {
            content = content2;
        }
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['系统通知', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '360px'
                , content: content //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['了解更多']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , skin: 'demo-class'
                , closeBtn: 0
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    window.location.href = '/admin/version/notice?mod_name=' + mod; //当前页面打开
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });

    },
    getUrlParam: function (name) { //获取url的参数，util.getUrlParam(name)
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return decodeURI(r[2]);
        return null; //返回参数值
    },
    radioChange: function (obj) {
        var radio_name = $('input[name="' + obj + '"]:checked').val();
        $('input[name="' + obj + '"]').click(function () {
            if ($(this).val() != radio_name) {
                //alert('修改过radio');
                hasSaved = false;
            }
        });
    },
    selectChange: function () {
        $('select').each(function () {
            var this_val = $(this).find('option:selected');
            $(this).change(function () {
                var last_val = $(this).find('option:selected');
                if (last_val != this_val) {
                    //alert('修改过select');
                    hasSaved = false;
                }
            });
        });
    },
    inputChange: function () {
        var temp;
        $("input,textarea").bind({
            focusin: function () {
                // alert("获得焦点时的值是："+$(this).val());
                temp = $.trim($(this).val());
            },
            focusout: function () {
                // alert("失去焦点时的值是："+$(this).val());
                var lastValue = $.trim($(this).val());
                if (temp != lastValue && null != lastValue && "" != lastValue) {
                    //alert('修改过input');
                    hasSaved = false;
                }
            }
        });
    },
    checkboxChange: function () {
        var checkbox_len = $('input[type="checkbox"]:checked').length;
        $(document).click(function () {
            if (checkbox_len != $('input[type="checkbox"]:checked').length) {
                //alert('修改过checkbox');
                hasSaved = false;
            }
        });
    },
    objectToArray:function (o) {
        if (typeof o == 'object') {
            var arr = [];
            for(var i in o){
                arr[i] = o[i];
            }
            return arr;
        } else if (typeof o == 'array') {
            return o;
        } else {
            return [];
        }
    },
    /***
     * ajax分页 可复写定义其它参数
     * @param total     总条数
     * @param currPage  当前页
     * @param callback  回调
     * @param elem      加载page的节点容器 默认 test1
     */
    loadPage: function(total, currPage, callback, elem) {
        elem = arguments[3] == undefined ? 'test1' : elem;
        function pageInit() {
            laypage.render({
                elem: elem,
                count: total,
                limit: 20,
                curr: currPage,
                groups: 3,
                first: '首页',
                last: '末页',
                layout: ['first', 'prev', 'page', 'next', 'last', 'skip'],
                jump: function(obj, first){
                    if (!first) {
                        callback(obj);
                    }
                }
            });
        }
        if (typeof laypage == 'undefined') {
            layui.use('laypage', function(){
                window.laypage = layui.laypage;
                pageInit();
            });
        } else {
            pageInit();
        }
    },

    /**
     * 合并数字
     * @param arr1
     * @param arr2
     * @returns {*}
     */
    mergeArray: function (arr1,arr2){
        if (!Array.isArray(arr1) || !Array.isArray(arr2))
            return [];
        //不要直接使用var arr = arr1，这样arr只是arr1的一个引用，两者的修改会互相影响
        var arr = arr1.concat();
        //或者使用slice()复制，var arr = arr1.slice(0)
        for(var i = 0;i < arr2.length; i++){
            if ($.inArray(arr2[i],arr) === -1) arr.push(arr2[i]);
        }
        return arr;
    },

    /**
     * 判断子数组
     * @param sub_array
     * @param array
     * @returns {boolean}
     */
    isSubArray: function (sub_array,array) {
        // if the other array is a false value, return
        if (!Array.isArray(sub_array) || !Array.isArray(array))
            return false;
        // compare lengths - can save a lot of time
        if (sub_array.length > array.length)
            return false;
        for (var i = 0, l = sub_array.length; i < l; i++) {
            // Check if we have nested arrays
            if ($.inArray(sub_array[i],array) === -1) {
                return false;
            }
        }
        return true;
    }
};
    if (typeof(layui) != 'undefined') {
    layui.use('layer', function () {
        window.layer = layui.layer;
    });
}
