var m_hot_area = {
    init_ev_el: function (el, data) {
        this.select_hot_area = data && data.data ? data.data : {bg_img_url: null, rectangles: []};
        el.find(".show-hot-image").attr("src", this.select_hot_area.bg_img_url);
        if(el.find(".show-hot-image").attr("src") != null){
            el.find(".hot-middle").addClass("hide");
        }
    },
    select_hot_area: {
        bg_img_url: null,
        rectangles: [],
    },
    /**
     * 临时选择区域，点击保存后，赋值给select_hot_area
     */
    tmp_select_hot_area: {
        bg_img_url: null,
        rectangles: [],
    },
    fill_edit_el: function (el, d) {
        var that = this;
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        this.select_hot_area = d && d.data ? d.data : {bg_img_url: null, rectangles: []};
        el.find(".hot-number-span").text(this.select_hot_area.rectangles.length + "个");
        el.find(".hot-image").attr("src", this.select_hot_area.bg_img_url);
        if( el.find(".hot-image").attr("src") != null){
            el.find(".start-area-image").addClass("hide");
        }
        el.find(".set-hot-area").unbind("click").click(function () {
            if (!that.select_hot_area.bg_img_url) {
                layer.msg("请先设置图片");
                return;
            }
            that.show_select_hot_dialog(el);
        });
        el.find(".set-bg-image").unbind("click").click(function () {
            $.jImageManager({
                ok_cb: function (img_arr) {
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    el.find(".hot-image").attr('src', path);
                    if(el.find(".hot-image").attr("src") != null){
                        el.find(".start-area-image").addClass("hide");
                    }
                    that.select_hot_area.bg_img_url = path;
                    that.get_image_dim(path);
                    hasSaved = false;
                    manager.change_show_module();
                }
            });
        });
    },
    get_image_dim: function (url) {
        var that = this;
        $("<img/>").attr("src", url).load(function () {
            that.select_hot_area.bg_img_width = 533.0;
            that.select_hot_area.bg_img_height = 533.0 / this.width * this.height;
            hasSaved = false;
        });
        /**
         * 前端：f_w f_h f_x f_y
         * 后端：bg_img_width  bg_img_height rectangles
         * 判断是哪个rectangle，从后向前遍历，如果找到根据链接跳转，否则无动作
         * function is_in_rectangle(point,rect){
         *      newX = f_x * bg_img_width/f_w;
         *      newY = f_y * bg_img_height/f_h;
         *      if(newX >= rect.x && newX < rect.x+rect.width
         *      && newY >= rect.y && newY < rect.y+rect.height){
         *          return true;
         *      }
         *      return false;
         * }
         */
    },
    show_select_hot_dialog: function (el) {
        var that = this;
        var dialog_el_html = el.find(".hot-area-dialog")[0].outerHTML;
        this.tmp_select_hot_area = $.parseJSON($.toJSON(this.select_hot_area)); // 深度拷贝
        layer.open({
            type: 1
            , title: ["编辑图片热区", 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: "582px"
            , content: dialog_el_html
            , btn: ['添加热区','保存']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                var dialog_el = $(layero.find(".hot-area-dialog"));
                that.init_hot_dialog(dialog_el);
            }
            , yes: function (index, layero) {
                var dialog_el = $(layero.find(".hot-area-dialog"));
                that.add_hot_area(dialog_el);
                hasSaved = false;
                return true;

            }, btn2: function (index, layero) {
                //按钮保存
                that.select_hot_area = $.parseJSON($.toJSON(that.tmp_select_hot_area));  // 深度拷贝
                el.find(".hot-number-span").text(that.select_hot_area.rectangles.length + "个");
                //console.log(that.select_hot_area);
            }, cancel: function (index, layero) {
                layer.close(index);
                el.find(".hot-number-span").text( that.select_hot_area.rectangles.length + "个");
                return false;
            }
        });
    },

    /**
     * 添加热区
     *
     * @param dialog_el
     * @param hot_area
     */
    add_hot_area: function (dialog_el, hot_area) {
        var that = this;
        var hot_el = dialog_el.find(".drop-template .drop-hot-area").clone();
        hot_el.appendTo(dialog_el.find(".edit-hot-image"));
        if (hot_area == undefined) {
            var hot_idx = this.tmp_select_hot_area.rectangles.length;
            hot_area = {
                hot_idx: hot_idx,
                x: $(dialog_el).find(".edit-hot-image-container").scrollLeft(),
                y: $(dialog_el).find(".edit-hot-image-container").scrollTop(),
                w: 115, h: 115
            };
            this.tmp_select_hot_area.rectangles.push(hot_area);
            hasSaved = false;
        }

        hot_el.attr("hot_idx", hot_area.hot_idx);
        hot_el.css({left: hot_area.x, top: hot_area.y, width: hot_area.w, height: hot_area.h});
        if (hot_area.link_text)
            $(hot_el).find(".link-text").text(hot_area.link_text);

        $(hot_el).find(".close").click(function () {
            that.remove_hot_area(dialog_el, hot_el);
        });
        $(hot_el).find(".modify").click(function () {
            show_links_dlg(function (url, name) {
                if (url == undefined || url == "") {
                    name = "设置链接";
                }
                $(hot_el).find(".link-text").text(name);
                that.set_hot_area_link_data(hot_area.hot_idx, url, name);
            });
        });
        $(hot_el).find(".link-text").click(function () {
            show_links_dlg(function (url, name) {
                if (url == undefined || url == "") {
                    name = "设置链接";
                }
                $(hot_el).find(".link-text").text(name);
                that.set_hot_area_link_data(hot_area.hot_idx, url, name);
            });
        });
        $(hot_el).draggable({
            //containment: "document",
            scroll: false,
            refreshPositions: true,
            stop: function () {
                that.update_hot_area_el_and_data(hot_el);
            }
        });
        $(hot_el).resizable({
            containment: "parent",
            minWidth: 20,
            minHeight: 20,
            stop: function () {
                that.update_hot_area_el_and_data(hot_el);
            }
        });
        if ($(hot_el).position().top + $(hot_el).outerHeight() > $(dialog_el).find(".edit-hot-image").height()) {
            $(dialog_el).find(".edit-hot-image").css({height: $(hot_el).position().top + $(hot_el).outerHeight()});
        }
        return hot_el;
    },
    update_hot_area_el_and_data: function (hot_el) {
        var that = this;
        var hot_idx = $(hot_el).attr("hot_idx");
        if ($(hot_el).position().left < 0) $(hot_el).css({left: 0});
        if ($(hot_el).position().top < 0) $(hot_el).css({top: 0});
        if ($(hot_el).outerWidth() > that.tmp_select_hot_area.bg_img_width) {
            $(hot_el).css({width: that.tmp_select_hot_area.bg_img_width});
        }
        if ($(hot_el).outerHeight() > that.tmp_select_hot_area.bg_img_height) {
            $(hot_el).css({height: that.tmp_select_hot_area.bg_img_height});
        }
        if ($(hot_el).position().left + $(hot_el).outerWidth() > that.tmp_select_hot_area.bg_img_width) {
            $(hot_el).css({left: that.tmp_select_hot_area.bg_img_width - $(hot_el).outerWidth()});
        }

        var dialog_el = hot_el.parents(".hot-area-dialog");
        var edit_image_el = $(dialog_el).find(".edit-hot-image");

        // 考虑由于更换背景图后，图片区域的高度有可能大于新更换图片的高度。
        if ($(hot_el).position().top + $(hot_el).outerHeight() > that.tmp_select_hot_area.bg_img_height) {
            if ($(hot_el).position().top < that.tmp_select_hot_area.bg_img_height) {
                // 热区上边沿不超出图片区域底部，则热区底部对齐图片底部
                $(hot_el).css({top: that.tmp_select_hot_area.bg_img_height - $(hot_el).outerHeight()});
            } else if ($(hot_el).position().top + $(hot_el).outerHeight() > $(edit_image_el).height()) {
                // 如果热区底部超出图片区域底部，则热区调整底部后，上边沿在图片内部，则与图片底部对齐，否则与图片区域底部对齐
                var new_top = $(edit_image_el).height() - $(hot_el).outerHeight();
                if (new_top < that.tmp_select_hot_area.bg_img_height) {
                    $(hot_el).css({top: that.tmp_select_hot_area.bg_img_height - $(hot_el).outerHeight()});
                } else {
                    $(hot_el).css({top: new_top});
                }
            }
        }

        var area = {
            hot_idx: hot_idx,
            x: $(hot_el).position().left,
            y: $(hot_el).position().top,
            w: $(hot_el).outerWidth(),
            h: $(hot_el).outerHeight()
        };
        that.update_hot_area_data(area.hot_idx, area.x, area.y, area.w, area.h);
        this.change_edit_hot_image_height(dialog_el);
    },
    update_hot_area_data: function (hot_idx, x, y, w, h) {
        for (var i in this.tmp_select_hot_area.rectangles) {
            var o = this.tmp_select_hot_area.rectangles[i];
            if (o.hot_idx == hot_idx) {
                this.tmp_select_hot_area.rectangles[i].x = x;
                this.tmp_select_hot_area.rectangles[i].y = y;
                this.tmp_select_hot_area.rectangles[i].w = w;
                this.tmp_select_hot_area.rectangles[i].h = h;
                hasSaved = false;
                break;
            }
        }
        //console.log(this.tmp_select_hot_area);
    },
    set_hot_area_link_data: function (hot_idx, link_url, link_text) {
        for (var i in this.tmp_select_hot_area.rectangles) {
            var o = this.tmp_select_hot_area.rectangles[i];
            if (o.hot_idx == hot_idx) {
                this.tmp_select_hot_area.rectangles[i].link_url = link_url;
                this.tmp_select_hot_area.rectangles[i].link_text = link_text;
                hasSaved = false;
                break;
            }
        }
    },

    remove_hot_area: function (dialog_el, hot_el) {
        var hot_idx = parseInt(hot_el.attr("hot_idx"));
        for (var i in this.tmp_select_hot_area.rectangles) {
            var o = this.tmp_select_hot_area.rectangles[i];
            if (o.hot_idx == hot_idx) {
                this.tmp_select_hot_area.rectangles.splice(i, 1);
                hasSaved = false;
                break;
            }
        }
        hot_el.remove();
        this.change_edit_hot_image_height(dialog_el);
    },
    change_edit_hot_image_height: function (dialog_el) {
        if ($(dialog_el).find(".edit-hot-image").height() > this.tmp_select_hot_area.bg_img_height) {
            var max_bottom_el = null;
            var max_bottom = 0;
            $(dialog_el).find(".edit-hot-image .drop-hot-area").each(function () {
                var bottom = $(this).position().top + $(this).outerHeight();
                if (bottom > max_bottom) {
                    max_bottom = bottom;
                    max_bottom_el = $(this);
                }
            });
            if(max_bottom < this.tmp_select_hot_area.bg_img_height)
                max_bottom = this.tmp_select_hot_area.bg_img_height;
            $(dialog_el).find(".edit-hot-image").css({height: max_bottom});
        }
    },

    init_hot_dialog: function (el) {
        var that = this;
        if (this.tmp_select_hot_area.bg_img_width == undefined) {
            $("<img/>").attr("src", this.tmp_select_hot_area.bg_img_url).load(function () {
                that.tmp_select_hot_area.bg_img_width = 533.0;
                that.tmp_select_hot_area.bg_img_height = 533.0 / this.width * this.height;
                hasSaved = false;
                var css = {
                    width: that.tmp_select_hot_area.bg_img_width,
                    height: that.tmp_select_hot_area.bg_img_height,
                    "background-size": that.tmp_select_hot_area.bg_img_width + "px "
                    + that.tmp_select_hot_area.bg_img_height + "px",
                    "background-image": "url(" + that.tmp_select_hot_area.bg_img_url + ")"
                };
                el.find(".edit-hot-image").css(css);
            });
        } else {
            var css = {
                width: that.tmp_select_hot_area.bg_img_width,
                height: that.tmp_select_hot_area.bg_img_height,
                "background-size": that.tmp_select_hot_area.bg_img_width + "px "
                + that.tmp_select_hot_area.bg_img_height + "px",
                "background-image": "url(" + that.tmp_select_hot_area.bg_img_url + ")"
            };
            el.find(".edit-hot-image").css(css);
        }
        el.find(".edit-hot-image .drop-hot-area").remove();
        for (var i in this.tmp_select_hot_area.rectangles) {
            var o = this.tmp_select_hot_area.rectangles[i];
            var hot_el = this.add_hot_area(el, o);
        }
        if (this.tmp_select_hot_area.rectangles.length == 0) {
            this.add_hot_area(el);
        }
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_hot_area").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.data = this.select_hot_area;
        return d;
    }
};