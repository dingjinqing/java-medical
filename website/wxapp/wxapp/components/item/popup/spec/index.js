var util = require("../../../../utils/util.js");
var base = require("../../../popup/base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    item_type: {
      type: String, // 活动商品类型 bargain,full,group,item,gift,pinlottery,integral,presale,seckill,
      value: "item",
      observer(newVal, oldVal, changedPath) {
        this.data.item_type = newVal || "item";
        this.init();
      }
    },
    use_gd_limit: { // 是否使用商品的数量限制
      type: Boolean,
      value: true,
    },
    hide_cell: { // 是否隐藏 选择单元
      type: Boolean,
      value: false,
    },
    show_buy_number: { // 是否显示购买数量
      type: Boolean,
      value: true,
    },
    exchang_block: { // 是否显示购买数量
      type: Number,
      value: 0,
    }, // 是否显示限次卡兑换选择 1为显示
    has_buy_btn: { // 是否含有购买按钮，只支持普通商品购买，为true时，<slot name="footer-nav" />失效，主要用于商品列表弹窗加购和购买
      type: Boolean,
      value: false,
    },
    gd: {
      type: Object,
      value: null,
      observer(newVal, oldVal, changedPath) {
        if (newVal) {
          console.log(newVal)
          this.data.gd = newVal;
          this.init();
        }
      }
    },
    default_prd_spec: String,
    stock_name: {
      type: String,
      value: "库存"
    }
  },
  data: {
    show_exchang: 1,
    select_card: {},
    is_stock_enough: true,
    isIpx: util.isIPhoneX() ? 1 : 0,
    is_min: true,
    is_max: false,
    goods_price: '0.00',
    goods_number: 1,
    spec_list: {},
    select_prd: null,
    select_specs: {},
    unselect_spec_names: '',
    select_prd_number: 0,
  },
  ready() {
    this._onChange();
    var that = this;
    var dd = that.data.hide_cell;
    if (dd == false) {
      util.api("/api/wxapp/pledge", function(res) {
        if (res.error == 0) {
          // console.log(res);
          var con = res.content;
          var pledge_list = con.pledge_list;
          var pledge_switch = con.pledge_switch;
          that.data.pledge_list = pledge_list;
          that.setData({
            pledge_list: pledge_list,
            pledge_switch: pledge_switch,
          })
        } else {
          util.showModal('提示', res.content)
        }
      })
    }

  },
  created() {

  },
  methods: {
    bindClickCell(e) {
      this.$emit("click_cell")
    },
    bindClickSpec(e) {
      var d = this.eventData(e);
      var gd = this.data.gd;
      this.data.goods_number = (this.data.use_gd_limit && gd.limit_buy_num > 0) ? gd.limit_buy_num : 1;
      this.check(d.spec_id, d.val_id);
      this.render();
    },
    bindMinus(e) {
      this.data.goods_number -= 1;
      this.setGoodsNumber();
    },
    bindPlus(e) {
      this.data.goods_number += 1;
      this.setGoodsNumber();
    },
    bindGoodsNumBlur(e) {
      var goods_number = parseInt(e.detail.value);
      this.setGoodsNumber(goods_number);
      if (goods_number > this.data.goods_number) {
        util.alert("不可超过限定数量!");
      } else if (goods_number < this.data.goods_number) {
        util.alert("小于最小数量");
      }
    },
    bindShowCard(e) {
      this.data.show_exchang = this.data.show_exchang == 0 ? 1 : 0;
      this.setData({
        show_exchang: this.data.show_exchang
      })
    },
    bindCheckCard(e) {
      var d = this.eventData(e);
      this.setData({
        select_card: this.data.select_card = this.data.gd.exchang_card[d.keys]
      })
      this._onChange();
    },
    preview: function(e) {
      var nowImgUrl = [];
      nowImgUrl.push(e.target.dataset.src);
      wx.previewImage({
        urls: nowImgUrl, // 当前显示图片的http链接
      })
    },
    addCart(choose_list, form_id, options) {
      util.api('/api/wxapp/cart/add', function(res) {
        util.getUserLocation(function(loc) {
          util.api('/api/wxapp/user_goods/record', function(res1) {}, {
            goods_id: choose_list['goods_id'],
            lat: loc.latitude || '',
            lng: loc.longitude || '',
            type: 2,
            prd_id: choose_list['prd_id'],
            num: choose_list['goods_number']
          })
        })
        if (res.error == 0) {
          util.toast_success('已添加至购物车');
        } else {
          util.toast_fail(res.message);
        }
      }, {
        choose_list: JSON.stringify(choose_list),
        form_id: form_id,
        open_id: util.getCache("open_id"),
        query_param: JSON.stringify(options)
      });
    },
    checkSelBuy() {
      if (this.data.has_spec && !this.data.select_prd) {
        util.alert("请选择规格！")
        return false;
      }

      if (!this.data.is_stock_enough) {
        util.alert("库存不足")
        return false;
      }
      return true;
    },
    getChooseList() {
      var d = this.data;
      var gd = this.data.gd;
      return {
        goods_id: gd.goods_id,
        goods_price: d.has_spec ? d.goods_price : gd.shop_price,
        grade: gd.grade_price != -1 ? 1 : undefined,
        goods_number: d.goods_number,
        prd_sn: d.has_spec ? d.select_prd.prd_sn : gd.goods_sn,
        prd_id: d.has_spec ? d.select_prd.prd_id : gd.prd_id,
        product_id: d.has_spec ? d.select_prd.prd_id : gd.prd_id,
        user_id: util.getCache('user_id')
      };
    },
    bindAddCart(e) {
      if (!this.checkSelBuy()) return;
      // 如果会员卡可购买，且会员卡未激活，先激活后才可购买
      var gd = this.data.gd;
      if (gd.card_can == true && gd.is_card_exclusive == 1) {
        let count = 0;
        if ((gd.user_grade_card != null && gd.user_grade_card.activation == 1 && gd.user_grade_card.activation_time != null) || (gd.user_grade_card && gd.user_grade_card.activation == 0)) {
          count++
        }
        gd.buy_card_list.forEach(function(e) {
          if (e.status == 2) count++;
        });
        if (count == 0) {
          util.showModal('提示', '请激活会员卡', function() {
            util.jumpLink('/pages/usercardlist/usercardlist')
          });
          return false;
        }
      }
      this.addCart(this.getChooseList(), e.detail.form_id, this._options);
      this.bindClose();
    },
    bindOneClickBuy: function(e) {
      var gd = this.data.gd;
      if (!this.checkSelBuy()) return;
      if (gd.business_state != 1) {
        util.showModal('提示', '店铺未营业，无法购买');
        return false;
      }
      // 如果会员卡可购买，且会员卡未激活，先激活后才可购买
      if (gd.card_can == true && gd.is_card_exclusive == 1) {
        let count = 0;
        if ((gd.user_grade_card != null && gd.user_grade_card.activation == 1 && gd.user_grade_card.activation_time != null) || (gd.user_grade_card && gd.user_grade_card.activation == 0)) {
          count++
        }
        gd.buy_card_list.forEach(function(e) {
          if (e.status == 2) count++;
        });
        if (count == 0) {
          util.showModal('提示', '请激活会员卡', function() {
            util.jumpLink('/pages/usercardlist/usercardlist')
          });
          return false;
        }
      }
      var choose_list = this.getChooseList();
      util.jumpLink("/pages/goodsCheckout/goodsCheckout?order_type=one_click_buy&choose_list=" + JSON.stringify(choose_list))
      this.bindClose();
    },
    bindTopMore(e) {
      var gd = this.data.gd;
      if (gd.is_card_exclusive == 1 && gd.buy_card_list.length <= 0 && gd.exclusive_grade_card != null) {
        util.showModal('提示', '您当前的会员等级不满足，仅拥有“' + gd.exclusive_grade_card.card_name + '”等级卡用户可购买此商品。可在“个人中心”查看会员卡权益');
        return false;
      }
      var goods_id = gd.goods_id;
      if (gd.buy_card_list != 'undefined' && gd.buy_card_list.length == 1) {
        util.navigateTo({
          url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + gd.buy_card_list[0].id + '&goods_id=' + goods_id,
        })
      } else {
        util.navigateTo({
          url: '/pages/buycardlist/buycardlist?goods_id=' + goods_id,
        })
      }
    },
    jumpLink(e) {
      util.jumpLink('/pages1/pledgeannounce/pledgeannounce?pledge_list=' + JSON.stringify(this.data.pledge_list));
    },
    init() {
      var d = {
        show_exchang: 1,
        select_card: {},
        is_stock_enough: true,
        isIpx: util.isIPhoneX() ? 1 : 0,
        is_min: true,
        is_max: false,
        goods_price: '0.00',
        goods_number: 1,
        spec_list: {},
        select_prd: null,
        select_specs: {},
        unselect_spec_names: '',
        select_prd_number: 0,
        goods_money: 0,
        show_buy_number: true
      };
      this.inited_spec = false;
      this.data = Object.assign({}, this.data, d);
      this.setData(d);
      var gd = this.data.gd;
      if (this.data.item_type == 'pinlottery' || this.data.item_type == 'bargain')
        this.data.show_buy_number = false;
      if (this.data.item_type == 'integral') {
        gd.specs = gd.integral_spec;
        gd.limit_buy_num = undefined;
        gd.limit_max_num = gd.max_exchange_num > 0 ? gd.max_exchange_num : undefined;
      }
      gd.specs = gd.specs || gd.spec; // 兼容
      this.getGoodsPrice();
      this.data.has_spec = !Array.isArray(gd.specs.prd_list);
      this.setData({
        has_spec: this.data.has_spec,
        show_buy_number: this.data.show_buy_number,
        goods_price: this.data.goods_price,
        goods_money: this.data.goods_money,
      })
      this.setGoodsNumber();
      if (this.data.has_spec) {
        this.defaultSelectSpec();
        this.render();
      } else {
        this._onChange();
      }
    },
    _onChange() {
      var d = this.data;
      if (!d.gd) return;
      var sel_spec = {
        spec_name: d.unselect_spec_names,
        prd_desc: d.select_prd ? d.select_prd.prd_name.replace("/s+/g", "") : false,
      };
      this.setData(sel_spec);
      if (d.gd.exchang_card) d.select_card = d.gd.exchang_card.length == 1 ? d.gd.exchang_card[0] : d.select_card;
      var is_card_surplus_enough = d.select_card.exchang_surplus >= d.goods_number;
      this.$emit('change', {
        exchang_block: d.exchang_block,
        select_card: d.select_card.id ? d.select_card : null,
        is_stock_enough: d.is_stock_enough,
        has_spec: d.has_spec,
        select_prd: d.select_prd,
        goods_number: d.goods_number,
        goods_price: d.goods_price,
        is_card_surplus_enough: is_card_surplus_enough
      });
    },
    defaultSelectSpec() {
      var specs = this.data.default_prd_spec;
      if (specs) {
        specs = specs.split("??");
        for (var i in specs) {
          var s = specs[i].split("!");
          this.check(s[0], s[1]);
        }
      }
    },
    render() {
      this.getUnSelSpecNames();
      this.refresh();
      this.getSelectPrd();
      this.getGoodsPrice();
      this.data.select_prd_number = this.data.select_prd ? this.data.select_prd[this.stockField()] : 0;
      if (this.data.select_prd != undefined && this.data.select_prd['limit_max_num'] != undefined){
        var gd = this.data.gd;
        gd.limit_max_num = gd.limit_max_num1 ? Math.min(gd.limit_max_num1, this.data.select_prd['limit_max_num']) : this.data.select_prd['limit_max_num'];
        this.setData({
          gd: gd,
        })
      }
      this.setData({
        goods_price: this.data.goods_price,
        goods_money: this.data.goods_money,
        goods_number: this.data.goods_number,
        spec_list: this.data.spec_list,
        select_prd: this.data.select_prd,
        select_specs: this.data.select_specs,
        unselect_spec_names: this.data.unselect_spec_names,
        select_prd_number: this.data.select_prd_number,
      })
      this._onChange();
    },
    stockField() {
      var map = {
        'seckill': 'stock',
        'integral_prd': 'stock',
        'integral_gd': 'integral_goods_num',
        'group_prd': 'stock',
        'group_gd': 'pin_goods_num',
        'presale_prd': 'presale_number',
        'bargain': 'goods_old_number'
      };
      if (this.data.has_spec) {
        return map[this.data.item_type] || map[this.data.item_type + "_prd"] || 'prd_number';
      } else {
        return map[this.data.item_type] || map[this.data.item_type + "_gd"] || 'goods_number';
      }
    },
    setGoodsNumber(goods_number) {
      goods_number = parseInt(goods_number);
      if (isNaN(goods_number) || goods_number <= 0)
        goods_number = this.data.goods_number;
      var gd = this.data.gd;
      var max = -1;
      if (this.data.has_spec) {
        if (this.data.select_prd) {
          max = this.data.select_prd[this.stockField()];
          // if (this.data.select_prd['limit_max_num'] != undefined) {
          //   gd.limit_max_num = Math.min(gd.limit_max_num, this.data.select_prd['limit_max_num']);
          //   var d = {
          //     gd: gd,
          //   };
          //   this.setData(d);
          // }
        } else {
          var prd_list = this.data.gd.specs.prd_list;
          for (var prd_specs in prd_list) {
            var number = parseInt(prd_list[prd_specs][this.stockField()]);
            if (max == -1 || max < number) max = number;
          }
        }
      } else {
        max = this.data.gd[this.stockField()];
      }

      var is_stock_enough = true;
      var min = 1;
      if (this.data.use_gd_limit && gd.limit_buy_num > 0) {
        min = Math.max(gd.limit_buy_num, min);
      }
      if (this.data.use_gd_limit && gd.limit_max_num > 0) {
        max = Math.min(max, gd.limit_max_num);
      }
      if (max < min) {
        is_stock_enough = false;
        goods_number = min;
      } else {
        goods_number = goods_number > max ? max : (goods_number < min ? min : goods_number);
      }
      var is_min = goods_number <= min;
      var is_max = goods_number >= max;
      var d = {
        is_max: is_max,
        is_min: is_min,
        is_stock_enough: is_stock_enough,
        goods_number: goods_number
      };
      this.data = Object.assign({}, this.data, d);
      this.setData(d);
      this._onChange();
    },
    /**
     * check spec btn 
     */
    check(spec_id, val_id) {
      var spec_list = this.data.gd.specs.spec_list;
      if (spec_list[spec_id].vals[val_id].gray) {
        console.error(`spec_id：{spec_id} val_id:{val_id} can not checked`);
        return;
      }
      var old_val_id = this.data.select_specs[spec_id]
      if (old_val_id) {
        delete this.data.select_specs[spec_id];
        spec_list[spec_id].vals[old_val_id].checked = false;
      }
      spec_list[spec_id].vals[val_id].checked = old_val_id != val_id;
      if (old_val_id != val_id) {
        this.data.select_specs[spec_id] = val_id;
      }
    },
    /**
     * 根据选中的规格select_specs，找到对应的prd
     */
    getSelectPrd() {
      this.data.select_prd = null;
      var spec_list = this.data.gd.specs.spec_list;
      if (Object.keys(this.data.select_specs).length == Object.keys(spec_list).length) {
        var prd_list = this.data.gd.specs.prd_list;
        var specs = this.data.select_specs
        for (var prd_specs in prd_list) {
          var prd_specs_arr = prd_specs.split("!!");
          var found = true;
          for (var sid in specs) {
            if (prd_specs_arr.indexOf(sid + ":" + specs[sid]) == -1) {
              found = false;
              break;
            }
          }
          if (found) {
            this.data.select_prd = prd_list[prd_specs];
            break;
          }
        }
      }
    },
    isGray(addSpec) {
      // 遍历产品列表，查询含有已选规格值与传入规格值组合的产品，如果找到且库存>=可购买最小数量，则可用（不为灰色显示），否则禁用（灰色显示）
      var min = (this.data.use_gd_limit && this.data.gd.limit_buy_num > 0) ? this.data.gd.limit_buy_num : 1;
      var specs = Object.assign({}, this.data.select_specs, addSpec);
      var prd_list = this.data.gd.specs.prd_list;
      for (var prd_specs in prd_list) {
        var prd_specs_arr = prd_specs.split("!!");
        var found = true;
        for (var sid in specs) {
          if (prd_specs_arr.indexOf(sid + ":" + specs[sid]) == -1) {
            found = false;
            break;
          }
        }
        if (found) {
          var stock = prd_list[prd_specs][this.stockField()];
          if (stock >= min) return false;
        }
      }
      return true;
    },
    getGoodsPrice() {
      var gd = this.data.gd;
      var d = this.data.select_prd || gd;
      if (this.data.item_type == 'seckill') {
        this.data.goods_price = d.seckill_price;
        return;
      }
      if (this.data.item_type == 'presale') {
        this.data.goods_price = d.shop_price || d.presale_price;
        return;
      }
      if (this.data.item_type == 'integral') {
        this.data.goods_price = d.goods_integral || d.score;
        this.data.goods_money = d.goods_money || d.money;
        return;
      }

      if (this.data.item_type == 'pinlottery') {
        this.data.goods_price = gd.group_draw.pay_money;
        return;
      }
      var price = d.prd_price || d.shop_price;
      if (d.reduce_price) {
        price = d.reduce_price;
      } else if (gd.grade_price && gd.grade_price != -1) {
        price = d.grade_price || d.grade_prd;
      }
      this.data.goods_price = price;
    },
    /**
     * 得到未选中的规格名
     */
    getUnSelSpecNames() {
      var d = this.data;
      var names = [];
      var spec_list = this.data.gd.specs.spec_list;
      for (var s1 in spec_list) {
        if (!d.select_specs[s1]) {
          names.push(spec_list[s1].spec_name);
        }
      }
      this.data.unselect_spec_names = names.join(" ");
    },
    /**
     * 初始化规格列表
     */
    initSpecList() {
      if (this.inited_spec) return
      var spec_list = this.data.gd.specs.spec_list;
      for (var spec_id in spec_list) {
        var vals = spec_list[spec_id].vals = spec_list[spec_id].vals || {};
        for (var val_id in spec_list[spec_id].spec_vals) {
          vals[val_id] = vals[val_id] || {
            name: spec_list[spec_id].spec_vals[val_id],
            val_id: val_id,
            checked: false,
            gray: false,
          }
        }
      }
      this.inited_spec = true;
    },
    /**
     * 规格选中或取消，重新计算规格列表
     */
    refresh() {
      this.initSpecList();
      var spec_list = this.data.gd.specs.spec_list;
      for (var spec_id in spec_list) {
        var vals = spec_list[spec_id].vals;
        for (var val_id in vals) {
          if (!vals[val_id].checked) {
            var spec = {};
            spec[spec_id] = val_id;
            vals[val_id].gray = this.isGray(spec);
          }
        }
      }
      this.data.spec_list = spec_list;
    }
  }
})