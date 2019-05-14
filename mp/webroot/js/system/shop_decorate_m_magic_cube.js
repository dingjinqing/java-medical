

var m_magic_cube = {
    td_height: 0,
    start_select: null,
    end_select: null,
    table_size: {rows:4,cols:4},
    select_cube_block: {},
    init_ev_el: function (el, data) {
        this.select_cube_block = data && data.data ? data.data : {};
        this.init_ev_table(el,data);
        var el_tbl = el.find(".cube-ev-tbl");
        let has_image = false;
        for (var block_name in this.select_cube_block) {
            this.init_ev_block(el_tbl, block_name);
            if (this.select_cube_block[block_name].img_url) {
                has_image = true;
            }
        }
        if (has_image) {
            el_tbl.css('display','block');
            el.find('.no_img_style').hide();
        } else {
            el_tbl.css('display','none');
            el.find('.no_img_style').show();
        }
    },
    init_ev_table: function (el,data) {
        this.table_size = data && data.table_size ? data.table_size : {rows:4,cols:4};
        var tbl_el = el.find(".cube-ev-tbl").html("");
        // console.log(this.table_size)
        for (var i = 0; i < this.table_size.rows; i++) {
            var tr = $("<tr></tr>")
            for (var j = 0; j < this.table_size.cols; j++) {
                $("<td class='empty'></td>").appendTo(tr);
            }
            tr.appendTo(tbl_el);
        }
        this.td_height = $(tbl_el).width() / this.table_size.cols;
        $(tbl_el).find("td").width(this.td_height).height(0);
        $(tbl_el).find("tr").css("height",this.td_height);
    },
    init_ev_block: function (el, block_name) {
        var cube_block = this.select_cube_block[block_name];
        if (!cube_block) return false;
        var td_el = el.find("tr:nth-child(" + cube_block.x + ")").find("td:nth-child(" + cube_block.y + ")");
        el.find("td[block_name='" + block_name + "']").removeClass("hide").addClass("empty");
        td_el.attr("colspan", cube_block.cols);
        td_el.attr("rowspan", cube_block.rows);
        if(cube_block.img_url){
            var el = $("<img>").attr("src", cube_block.img_url);
            td_el.html(el[0].outerHTML);
            $(td_el).find("img").width(cube_block.cols * this.td_height).height(cube_block.rows * this.td_height)
            $(td_el).width(cube_block.cols * this.td_height).height(cube_block.rows * this.td_height);
        } else {
            $(td_el).width(cube_block.cols * this.td_height).height(cube_block.rows * this.td_height);
        }
        var tr = td_el.parent();
        var cur_col = this.get_cur_col(td_el);
        for (var i = 0; i < cube_block.rows; i++) {
            var start_col = i == 0 ? cur_col + 1 : cur_col;
            for (var j = start_col; j < cube_block.cols + cur_col; j++) {
                var td = tr.find("td:nth-child(" + j + ")");
                $(td).removeClass("empty").addClass("hide").attr("block_name", block_name);
            }
            tr = tr.next();
        }
    },

    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        this.table_size = d && d.table_size ? d.table_size : {rows:4,cols:4};
        this.select_cube_block = d && d.data ? d.data : {};
        let table_type = d.table_type ? parseInt(d.table_type) : 0;
        if (table_type > 0) {
            el.find(".cube_block_type > div").removeClass('blue_border');
            el.find(".img_list > div").eq(table_type - 1).addClass('blue_border');
        } else {
            el.find(".cube_block_type > div").removeClass('blue_border');
            el.find(".img_list > div").eq(7).addClass('blue_border');
        }
        this.init_model_table(el,table_type);

        var _this = this;
        el.find(".magic-cube-layout").mouseleave(function () {
            if (_this.start_select) {
                el.find(".magic-cube-layout .hover_td").removeClass('hover_td');
                _this.start_select = null; _this.end_select =null;
            }
        })
        el.find(".img_list > div").unbind("click").click(function () {
            var model_id = $(this).attr('model_id');
            var cur_model_id = el.find(".blue_border").attr('model_id');
            console.log(model_id + ' ' + cur_model_id)
            if (model_id != cur_model_id) {
                el.find(".img_list > div").removeClass('blue_border');
                $(this).addClass('blue_border');
                for (var block_name in _this.select_cube_block) {
                    _this.delete_cube_block(block_name);
                }
                _this.init_model_table(el,model_id)
            }

        })
    },
    init_cube_table: function(el,rows,cols) {
        this.table_size.rows = rows;
        this.table_size.cols = cols;
        var cube_table = el.find(".magic-cube-layout .cube-block-tbl").html("");
        if (rows < 1 || cols < 1) return false;
        for (var r = 0; r < rows; r++) {
            var tr = $("<tr></tr>")
            for (var c = 0; c < cols; c++) {
                $("<td class='empty' style='position:relative'><span>+</span></td>").appendTo(tr);
            }
            tr.appendTo(cube_table);
        }
        var _this = this;
        var td_height = $(cube_table).width() / cols;
        $(cube_table).find("td").width(td_height).height(td_height);
        $(cube_table).find("tr").css("height",td_height);
        $(cube_table).find("td").unbind("click").click(function () {
            hasSaved = false;
            if ($(this).hasClass("empty")) {
                if (_this.start_select) {
                    let cur_td = {
                        x: $(this).index() + 1,
                        y: $(this).parent().index() + 1,
                    };
                    var dst_rows = 0;
                    var dst_cols = 0;
                    let start_td = {};
                    if (_this.end_select.x >= _this.start_select.x) {
                        if (cur_td.y >= _this.start_select.y) {
                            start_td.x = _this.start_select.x;
                            start_td.y = _this.start_select.y;
                            dst_cols = _this.end_select.x - _this.start_select.x + 1;
                            dst_rows = _this.end_select.y - _this.start_select.y + 1;
                        } else {
                            start_td.x = _this.start_select.x;
                            start_td.y = _this.end_select.y;
                            dst_cols = _this.end_select.x - _this.start_select.x + 1;
                            dst_rows = _this.start_select.y - _this.end_select.y + 1;
                        }
                    } else {
                        if (_this.end_select.y >= _this.start_select.y) {
                            start_td.x = _this.end_select.x;
                            start_td.y = _this.start_select.y;
                            dst_cols = _this.start_select.x - _this.end_select.x + 1;
                            dst_rows = _this.end_select.y - _this.start_select.y + 1;
                        } else {
                            start_td.x = _this.end_select.x;
                            start_td.y = _this.end_select.y;
                            dst_cols = _this.start_select.x - _this.end_select.x + 1;
                            dst_rows = _this.start_select.y - _this.end_select.y + 1;
                        }
                    }

                    let td_el = el.find(".magic-cube-layout tr").eq(parseInt(start_td.y - 1)).find('td').eq(parseInt(start_td.x -1));
                    m_magic_cube.init_cube_block(td_el, dst_rows, dst_cols);
                    el.find(".magic-cube-layout .hover_td").removeClass('hover_td');
                    _this.start_select = null; _this.end_select =null;
                } else {
                    _this.start_select = {
                        x: $(this).index() + 1,
                        y: $(this).parent().index() + 1
                    };
                    _this.end_select = {
                        x: $(this).index() + 1,
                        y: $(this).parent().index() + 1
                    };
                    $(this).addClass('hover_td');
                }
                // m_magic_cube.show_select_block($(this));
            } else {
                if ($(this).hasClass("not-empty")) {
                    $(this).parents("table").find("td.current").removeClass("current");
                    $(this).addClass("current");
                    _this.add_delete_img(el,$(this))
                    m_magic_cube.show_cube_block_edit_el($(this).attr("block_name"));
                }
            }
        });
        $(cube_table).find(".empty").hover(function () {
            // console.log(_this.start_select);
            if (_this.start_select) {
                let this_index = {
                    x: $(this).index() + 1,
                    y: $(this).parent().index() + 1,
                };
                _this.get_end_td(el,this_index.x,this_index.y);
                _this.show_hover_area(el,_this.start_select.x,_this.start_select.y,_this.end_select.x,_this.end_select.y);
            }
        })
        $(cube_table).find("td").hover(function () {
            if ($(this).hasClass('current')) {
                // console.log('1234');
                _this.add_delete_img(el,$(this))
            }
        },function () {
            if ($(this).find('.del-cube-block').length > 0) {
                $(this).find('.del-cube-block').remove();
            }
        });
    },
    init_model_table: function (el,model_id) {
        var _this = this;
        if (model_id == 1) {
            _this.init_cube_table(el,2,4);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {
                    block_0: {name: "block_0", x: 1, y: 1, rows: 2, cols: 2},
                    block_1: {name: "block_1", x: 1, y: 3, rows: 2, cols: 2}
                };
            }
        } else if (model_id == 2) {
            _this.init_cube_table(el,2,6);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {
                    block_0: {name: "block_0", x: 1, y: 1, rows: 2, cols: 2},
                    block_1: {name: "block_1", x: 1, y: 3, rows: 2, cols: 2},
                    block_2: {name: "block_2", x: 1, y: 5, rows: 2, cols: 2}
                };
            }
        } else if (model_id == 3) {
            _this.init_cube_table(el,1,4);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {
                    block_0: {name: "block_0", x: 1, y: 1, rows: 1, cols: 1},
                    block_1: {name: "block_1", x: 1, y: 2, rows: 1, cols: 1},
                    block_2: {name: "block_2", x: 1, y: 3, rows: 1, cols: 1},
                    block_3: {name: "block_3", x: 1, y: 4, rows: 1, cols: 1}
                };
            }
        } else if (model_id == 4) {
            _this.init_cube_table(el,4,4);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {
                    block_0: {name: "block_0", x: 1, y: 1, rows: 2, cols: 2},
                    block_1: {name: "block_1", x: 3, y: 1, rows: 2, cols: 2},
                    block_2: {name: "block_2", x: 1, y: 3, rows: 2, cols: 2},
                    block_3: {name: "block_3", x: 3, y: 3, rows: 2, cols: 2}
                };
            }
        } else if (model_id == 5) {
            _this.init_cube_table(el,4,4);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {
                    block_0: {name: "block_0", x: 1, y: 1, rows: 4, cols: 2},
                    block_1: {name: "block_1", x: 1, y: 3, rows: 2, cols: 2},
                    block_2: {name: "block_2", x: 3, y: 3, rows: 2, cols: 2}
                };
            }
        } else if (model_id == 6) {
            _this.init_cube_table(el,4,4);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {
                    block_0: {name: "block_0", x: 1, y: 1, rows: 2, cols: 4},
                    block_1: {name: "block_1", x: 3, y: 1, rows: 2, cols: 2},
                    block_2: {name: "block_2", x: 3, y: 3, rows: 2, cols: 2}
                };
            }
        } else if (model_id == 7) {
            _this.init_cube_table(el,4,4);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {
                    block_0: {name: "block_0", x: 1, y: 1, rows: 4, cols: 2},
                    block_1: {name: "block_1", x: 1, y: 3, rows: 2, cols: 2},
                    block_2: {name: "block_2", x: 3, y: 3, rows: 2, cols: 1},
                    block_3: {name: "block_3", x: 3, y: 4, rows: 2, cols: 1},
                };
            }
        } else {
            _this.init_cube_table(el,4,4);
            if (!_this.select_cube_block || !util.count(_this.select_cube_block)) {
                _this.select_cube_block = {};
            }
        }
        if (util.count(_this.select_cube_block) > 0) {
            for (var block_name in _this.select_cube_block) {
                _this.fill_cube_element(block_name);
            }
        }
    },
    add_delete_img: function(el,cur_td) {
        if (!parseInt(el.find('.blue_border').attr('model_id'))){
            cur_td.append($('<a class="del-cube-block" href="javascript:void(0)" title="删除该模块"></a>'));
            cur_td.find(".del-cube-block").unbind("click").click(function () {
                let block_name = $(this).parent('td').attr('block_name');
                m_magic_cube.delete_cube_block(block_name);
                return false;
            });
        }
    },
    show_hover_area: function(el,start_x,start_y,end_x,end_y){
        el.find(".magic-cube-layout .hover_td").removeClass('hover_td');
        let min_x = start_x > end_x ? end_x : start_x;
        let max_x = start_x < end_x ? end_x : start_x;
        let min_y = start_y > end_y ? end_y : start_y;
        let max_y = start_y < end_y ? end_y : start_y;
        for(var x = min_x; x <= max_x; x++) {
            for(var y = min_y; y <= max_y; y++) {
                el.find(".magic-cube-layout tr").eq(parseInt(y - 1)).find('td').eq(parseInt(x -1)).addClass('hover_td');
            }
        }

    },
    get_end_td:function(el,end_x,end_y){
        if (Math.abs(this.end_select.y-this.start_select.y) >= Math.abs(end_y-this.start_select.y)) {
            this.end_select.y = end_y;
        } else {
            if (end_y >= this.start_select.y) {
                if (this.end_select.x >= this.start_select.x) {
                    for (var y = this.end_select.y; y <= end_y; y++) {
                        let flag = false;
                        // console.log(parseInt(y - 1))
                        let cur_row = el.find(".magic-cube-layout tr").eq(parseInt(y - 1));
                        for (var x = this.start_select.x; x <= this.end_select.x; x++) {
                            // console.log(cur_row.find('td').eq(parseInt(x - 1)).hasClass('not-empty'));
                            let cur_td = cur_row.find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')){
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.y = y - 1; break;
                        } else {
                            this.end_select.y = y;
                        }
                    }
                } else {
                    for (var y = this.end_select.y; y <= end_y; y++) {
                        let flag = false;
                        let cur_row = el.find(".magic-cube-layout tr").eq(parseInt(y - 1));
                        for (var x = this.start_select.x; x >= this.end_select.x; x--) {
                            let cur_td = cur_row.find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')) {
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.y = y - 1; break;
                        } else {
                            this.end_select.y = y;
                        }
                    }
                }
            } else {
                if (this.end_select.x >= this.start_select.x) {
                    for (var y = this.end_select.y; y >= end_y; y--) {
                        let flag = false;
                        let cur_row = el.find(".magic-cube-layout tr").eq(parseInt(y - 1));
                        for (var x = this.start_select.x; x <= this.end_select.x; x++) {
                            let cur_td = cur_row.find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')) {
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.y = y + 1; break;
                        } else {
                            this.end_select.y = y;
                        }
                    }
                } else {
                    for (var y = this.end_select.y; y >= end_y; y--) {
                        let flag = false;
                        let cur_row = el.find(".magic-cube-layout tr").eq(parseInt(y - 1));
                        for (var x = this.start_select.x; x >= this.end_select.x; x--) {
                            let cur_td = cur_row.find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')) {
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.y = y + 1; break;
                        } else {
                            this.end_select.y = y;
                        }
                    }
                }
            }
        }

        if (Math.abs(this.end_select.x-this.start_select.x) >= Math.abs(end_x-this.start_select.x)) {
            this.end_select.x = end_x;
        } else {
            if (end_x >= this.start_select.x) {
                if (this.end_select.y >= this.start_select.y) {
                    for (var x = this.end_select.x; x <= end_x; x++) {
                        let flag = false;
                        for (var y = this.start_select.y; y <= this.end_select.y; y++) {
                            let cur_td = el.find(".magic-cube-layout tr").eq(parseInt(y - 1)).find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')) {
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.x = x - 1; break;
                        } else {
                            this.end_select.x = x;
                        }
                    }
                } else {
                    for (var x = this.end_select.x; x <= end_x; x++) {
                        let flag = false;
                        for (var y = this.start_select.y; y >= this.end_select.y; y--) {
                            let cur_td = el.find(".magic-cube-layout tr").eq(parseInt(y - 1)).find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')) {
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.x = x - 1; break;
                        } else {
                            this.end_select.x = x;
                        }
                    }
                }
            } else {
                if (this.end_select.y >= this.start_select.y) {
                    for (var x = this.end_select.x; x >= end_x; x--) {
                        let flag = false;
                        for (var y = this.start_select.y; y <= this.end_select.y; y++) {
                            let cur_td = el.find(".magic-cube-layout tr").eq(parseInt(y - 1)).find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')) {
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.x = x + 1; break;
                        } else {
                            this.end_select.x = x;
                        }
                    }
                } else {
                    for (var x = this.end_select.x; x >= end_x; x--) {
                        let flag = false;
                        for (var y = this.start_select.y; y >= this.end_select.y; y--) {
                            let cur_td = el.find(".magic-cube-layout tr").eq(parseInt(y - 1)).find('td').eq(parseInt(x - 1));
                            if (cur_td.hasClass('not-empty') || cur_td.hasClass('hide')) {
                                flag =  true; break;
                            }
                        }
                        if (flag) {
                            this.end_select.x = x + 1; break;
                        } else {
                            this.end_select.x = x;
                        }
                    }
                }
            }
        }
        return true;
    },
    // show_select_block: function (td_el) {
    //     var tr = td_el.parent();
    //     var cur_col = this.get_cur_col(td_el);
    //     var cur_row = tr.index() + 1;
    //     var html = "";
    //     var row_idx = 1;
    //     var min_cols = 4;
    //     while (tr.length > 0) {
    //         var row = tr.index() + 1;
    //         var td = this.get_col_td(tr, cur_col);
    //         if (!(td && td.hasClass("empty"))) break;
    //         var cols = this.get_empty_cols(td);
    //         if (cols == 0) break;
    //         if (min_cols > cols) min_cols = cols;
    //         var row_block = this.get_row_block(row_idx, min_cols);
    //         html += row_block;
    //         row_idx++;
    //         tr = tr.next("tr");
    //     }
    //     var select_dlg = null;
    //     var el_select_block = $('<div class="select-cube-block"></div>').html(html);
    //     el_select_block.find(".select-row-block").unbind("click").click(function () {
    //         hasSaved = false;
    //         var dst_rows = parseInt($(this).attr("row-num"));
    //         var dst_cols = parseInt($(this).attr("col-num"));
    //         m_magic_cube.init_cube_block(td_el, dst_rows, dst_cols);
    //         if (select_dlg)
    //             select_dlg.hide();
    //     });
    //     $(el_select_block).find(".select-row-block").hover(function () {
    //         var el = $(this).parent().parent();
    //         el.find(".select-row-block").removeClass("sel");
    //         var row_idx = $(this).parent().index();
    //         var col_idx = $(this).index();
    //         el.find(".select-row-blocks:lt(" + (row_idx + 1) + ")").find(".select-row-block:lt(" + (col_idx + 1) + ")").addClass("sel");
    //     }, function () {
    //
    //     });
    //
    //     select_dlg = art.dialog({
    //         title: "选择区块",
    //         content: el_select_block[0],
    //         okVal: "确定",
    //         ok: function () {
    //             var el = el_select_block.find(".select-row-block.sel:last");
    //             if(el.length == 0) return true;
    //             var dst_rows = parseInt(el.attr("row-num"));
    //             var dst_cols = parseInt(el.attr("col-num"));
    //             m_magic_cube.init_cube_block(td_el, dst_rows, dst_cols);
    //             return true;
    //         },
    //         cancelVal: "取消",
    //         cancel: function () {
    //         }
    //     });
    //
    // },
    add_one_block: function (td_el, rows, cols) {
        var x = td_el.parent().index() + 1;
        var y = td_el.index() + 1;
        for (var i = 0; i < 16; i++) {
            var name = "block_" + i;
            if (!this.select_cube_block[name]) {
                this.select_cube_block[name] = {
                    name: name,
                    x: x, y: y, rows: rows, cols: cols
                };
                return name;
            }
        }
    },
    fill_cube_element: function (block_name) {
        var cube_block = this.select_cube_block[block_name];
        if (!cube_block) return false;
        var cols_width = parseFloat(750 / this.table_size.cols).toFixed(1);
        var dim = '';
        if(this.table_size.rows > 3) {
            dim += Math.ceil(cube_block.cols * cols_width) + "x" + Math.ceil(cube_block.rows * cols_width) + '像素<br/>或同等比例';
        } else {
            dim += '宽度' + Math.ceil(cube_block.cols * cols_width) + '像素';
        }
        $(".module_body .magic-cube-layout table").find("td.current").removeClass("current");
        var td_el = $(".module_body .magic-cube-layout table").find("tr:nth-child(" + cube_block.x + ")").find("td:nth-child(" + cube_block.y + ")");
        $(".module_body .magic-cube-layout table").find("td[block_name='" + block_name + "']").removeClass("hide").addClass("empty");
        let td_width = parseInt($(".magic-cube-layout .cube-block-tbl").width() / this.table_size.cols);
        td_el.attr("colspan", cube_block.cols);
        td_el.attr("rowspan", cube_block.rows);
        td_el.removeClass("empty");
        td_el.addClass("not-empty").addClass("current");
        td_el.attr("block_name", block_name).css('padding','0px');

        var tr = td_el.parent();
        var cur_col = this.get_cur_col(td_el);
        for (var i = 0; i < cube_block.rows; i++) {
            var start_col = i == 0 ? cur_col + 1 : cur_col;
            for (var j = start_col; j < cube_block.cols + cur_col; j++) {
                var td = tr.find("td:nth-child(" + j + ")");
                $(td).removeClass("empty").addClass("hide").attr("block_name", block_name);
            }
            tr = tr.next();
        }
        if(cube_block.img_url){
            var el = $("<img>").attr("src", cube_block.img_url).height(Math.ceil(cube_block.rows * td_width));
            td_el.html(el[0].outerHTML);
        }else{
            td_el.html("<span>" + dim + "</span>");
        }
        this.show_cube_block_edit_el(block_name);
    },
    init_cube_block: function (td_el, rows, cols) {
        rows = parseInt(rows);
        cols = parseInt(cols);
        var block_name = this.add_one_block(td_el, rows, cols);
        this.fill_cube_element(block_name);
        this.show_cube_block_edit_el(block_name);
    },
    modify_cube_block: function (block_name, rows, cols) {
        rows = parseInt(rows);
        cols = parseInt(cols);
        var cube_block = this.select_cube_block[block_name];
        if (!cube_block) return false;
        cube_block.rows = rows;
        cube_block.cols = cols;
        this.fill_cube_element(block_name);
        this.show_cube_block_edit_el(block_name);
    },
    show_cube_block_edit_el: function (block_name) {
        var cube_block = this.select_cube_block[block_name];
        if (!cube_block) return false;
        var cols_width = parseFloat(750 / this.table_size.cols).toFixed(1);
        var dim = '';
        if(this.table_size.rows > 3) {
            dim += Math.ceil(cube_block.cols * cols_width) + "x" + Math.ceil(cube_block.rows * cols_width) + '像素或同等比例';
        } else {
            dim += '宽度' + Math.ceil(cube_block.cols * cols_width) + '像素';
        }
        var el = $(".module_body .cube_block_edit");
        el.attr("block_name", block_name);
        el.find("img").attr("src", cube_block.img_url ? cube_block.img_url : "");
        if (cube_block.img_url) {
            el.find(".op-img").parent().addClass('deco_edit_img');
            el.find(".op-img").attr("has-img", 1).text("修改");
            el.find(".op-img").parent().parent().find(".first_add").css("display","block");
            el.find(".op-img").css("background-image","none");
        }
        else{
            el.find(".op-img").parent().parent().find(".first_add").css("display","none");
            el.find(".op-img").parent().removeClass('deco_edit_img');
            el.find(".op-img").removeAttr("has-img").text("添加");
            el.find(".op-img").css("background","url(/image/admin/shop_beautify/add_decorete.png) no-repeat");
            el.find(".op-img").css("background-position",'center center');
            el.find(".op-img").css("background-size","65% 65%");
        }
        el.find(".op-img").unbind("click").click(function () {
            $.jImageManager({
                // img_width: cube_block.cols * 160,
                // img_height: cube_block.rows * 160,
                ok_cb: function (img_arr) {
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    el.find("img").attr('src', path);
                    hasSaved = false;
                    el.find(".op-img").parent().addClass('deco_edit_img');
                    el.find(".op-img").attr("has-img", 1).text("修改");
                    el.find(".op-img").parent().parent().find(".first_add").css("display","block");
                    cube_block.img_url = path;
                    m_magic_cube.update_block_image(block_name);
                    manager.change_show_module();
                }
            });
        });

        el.find(".img-tip").text("建议尺寸：" + dim);
        this.init_select_dim_el(block_name);
        $(".cube_block_edit #select_dim").unbind("change").change(function () {
            var rows = $(this).val().split("_")[0];
            var cols = $(this).val().split("_")[1];
            m_magic_cube.modify_cube_block(block_name, rows, cols);

        });
        el.find("#select_dim").val(cube_block.rows + "_" + cube_block.cols);
        el.find(".select_links").unbind("click").click(function () {
            show_links_dlg(function (url) {
                cube_block.jump_link = url;
                $(".module_body .data_item #jump_link").val(url);
                hasSaved = false;
            });
        });
        el.find("#jump_link").val(cube_block.jump_link ? cube_block.jump_link : "");
        el.find("#jump_link").unbind("change").change(function () {
            cube_block.jump_link = $(this).val();
        });
        el.find(".del-cube-block").unbind("click").click(function () {
            hasSaved = false;
            m_magic_cube.delete_cube_block(block_name);
        });
        el.show();
        manager.change_show_module();
    },
    delete_cube_block: function (block_name) {
        var cube_block = this.select_cube_block[block_name];
        if (!cube_block) return false;
        $(".magic-cube-layout table").find("td[block_name='" + block_name + "']")
            .removeClass("hide").removeClass("not-empty").removeClass("current").addClass("empty")
            .removeAttr("colspan").removeAttr("rowspan").removeAttr("block_name").html("<span>+</span>");
        delete this.select_cube_block[block_name];
        $(".module_body .cube_block_edit").hide();
    },
    update_block_image: function (block_name) {
        var cube_block = this.select_cube_block[block_name];
        if (!cube_block) return false;
        var td_el = $(".module_body .magic-cube-layout table").find("tr:nth-child(" + cube_block.x + ")").find("td:nth-child(" + cube_block.y + ")");
        var el = $("<img>").attr("src", cube_block.img_url).height(Math.ceil(td_el.height()));
        td_el.html(el[0].outerHTML);
    },
    init_select_dim_el: function (block_name) {
        var cube_block = this.select_cube_block[block_name];
        if (!cube_block) return false;
        var td_el = $(".module_body .magic-cube-layout table").find("tr:nth-child(" + cube_block.x + ")").find("td:nth-child(" + cube_block.y + ")");
        var tr = td_el.parent();
        var cur_col = this.get_cur_col(td_el);
        var sel_el = $(".module_body .cube_block_edit #select_dim");
        sel_el.html("");
        var row = 1;
        var min_cols = 4;
        while (tr.length > 0) {
            var td = this.get_col_td(tr, cur_col);
            var cols = this.get_not_empty_cols(td, block_name);
            if (cols == 0) break;
            if (min_cols > cols) min_cols = cols;
            for (var col = 1; col <= min_cols; col++) {
                $("<option></option>").text(row + "行" + col + "列").val(row + "_" + col).appendTo(sel_el);
            }
            row++;
            tr = tr.next("tr");
        }
        sel_el.val(cube_block.rows + "_" + cube_block.cols);
    },
    get_row_block: function (row, cols) {
        var row_el = $('<div class="select-row-blocks"></div>').attr("row-num", row);
        for (var i = 0; i < cols; i++) {
            $('<div class="select-row-block"></div>').attr("row-num", row).attr("col-num", i + 1).appendTo(row_el);
        }
        return row_el[0].outerHTML;
    },
    get_col_td: function (tr, col) {
        return tr.find("td:nth-child(" + col + ")");
    },
    get_cur_col: function (td_el) {
        return td_el.index() + 1;
    },
    get_empty_cols: function (td_el) {
        var el = td_el;
        var cols = 0;
        while (el.length > 0) {
            cols++;
            el = el.next("td.empty");
        }
        return cols;
    },
    get_not_empty_cols: function (td_el, block_name) {
        var el = td_el;
        var cols = 0;
        while (el.length > 0) {
            if (el.attr("block_name") != undefined && el.attr("block_name") != block_name) break;
            cols++;
            el = el.next(":not(.not-empty)");
        }
        return cols;
    },
    show_edit_el: function (data) {
        var el = $("#template_list .d_m_magic_cube").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.table_type = el.find('.blue_border').attr('model_id');
        d.table_size = this.table_size;
        d.data = this.select_cube_block;
        console.log(d);
        return d;
    },
    check_module:function () {
        if ($(".module_body .data_item").find(".cube-block-tbl td").hasClass('empty')) {
            return '有为选中模块';
        }
    }
};