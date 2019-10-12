export default {
  methods: {
    // 页面装修模块数据填充处理
    handleToAddModules (index) {
      let obj = {}
      switch (index) {
        case 1: // 会员卡模块
          obj = {
            'module_name': 'm_card', // 模块名称
            'card_id': '', // 会员卡id
            'hidden_card': 0, // 是否用户领取后隐藏会员卡
            'card_name': 'V1代理卡', // 会员卡名称
            'card_state': '使用中', // 会员卡使用状态
            'card_grade': 'v1', // 会员卡等级
            'receive_day': '有效期:永久有效', // 有效期
            'card_type': '0', // 会员卡等级
            'legal': '会员折扣9折', // 会员卡描述
            'exchang_count_legal': '', // 会员卡折扣描述
            'bg_type': '0', // 背景类型
            'bg_color': '#ecc98f', // 背景颜色
            'bg_img': '', // 背景图片
            'is_pay': '2',
            'pay_type': '0',
            'pay_fee': '0.00'
          }
          break
        case 2: // 优惠卷模块
          obj = {
            'module_name': 'm_coupon', // 模块名称
            'coupon_arr': [ // 选中的优惠卷数组
              {
                'act_code': 'voucher', // 是否是打折卷  discount：打折卷   voucher不是打折卷
                'denomination': '5', // 面额
                'consume_text': '无门槛', // 使用门槛
                'receive_text': '剩余93张', // 卡卷剩余数
                'coupon_id': -1, // 优惠卷id
                'use_score': '0', // 是否可以积分兑换
                'score_number': '' // 需要积分数
              }
            ]
          }
          break
        case 8: // 商品模块
          obj = {
            'module_name': 'm_goods',
            'title': '',
            'title_link': '',
            'recommend_type': '0',
            'goods_items': [

            ],
            'col_type': '4', // 4  1  2  3  0
            'goods_display': '0',
            'goods_num': '4',
            'min_price': '',
            'max_price': '',
            'keywords': '',
            'sort_type': '1',
            'img_url': '',
            'goods_module_title': '0',
            'img_title_url': '',
            'hide_name': 0,
            'hide_price': 0,
            'hide_label': 0,
            'cart_btn': 1,
            'other_message': 0,
            'if_radius': '0',
            'goods_module_style': '0',
            'goods_area': 'all',
            'goods_area_data': '',
            'goods_type': '0',
            'show_market': '1',
            'goods_module_bg': '0',
            'goods_bg_color': '#f5f5f5'
          }
          break
        case 9: // 商品搜索模块
          obj = {
            'module_name': 'm_goods_search', // 模块名称
            'search_style': '1', // 框体样式
            'search_font': '1', // 框体高度
            'box_color': '#eee', // 框体颜色
            'back_color': '#fff', // 背景颜色
            'search_sort': '0', // 商家分类是否显示
            'sort_bg_color': '#666666' // 图标颜色
          }
          break
        case 11: // 轮播图模块
          obj = {
            'module_name': 'm_scroll_image', // 模块名称
            'img_items': [], // 轮播图片列表
            'is_preview': '0' // 是否在小程序中可以预览原图
          }
          break
        case 12: // 图片导航模块
          obj = {
            'module_name': 'm_image_guide', // 模块名称
            'nav_style': '1', // 导航样式  1 为样式一   2 为样式二
            'font_color': '#92b0e4', // 字体颜色
            'bg_color': '#ffffff', // 背景颜色
            'nav_group': [ // 导航列表
              {
                'nav_name': '导航一', // 导航名称
                'nav_link': '', // 导航链接
                'nav_src': '' // 导航左侧图片路径
              },
              {
                'nav_name': '导航二',
                'nav_link': '',
                'nav_src': ''
              },
              {
                'nav_name': '导航三',
                'nav_link': '',
                'nav_src': ''
              },
              {
                'nav_name': '导航四',
                'nav_link': '',
                'nav_src': ''
              }
            ]
          }
      }
      return obj
    },
    // 对保存模块数据进行校验
    handleToJudgeModulesData (data) {
      console.log(data, this.pageSetData)
      // 判断页面设置页面名称是否为空
      if (!this.pageSetData.page_name) {
        this.$message.error({
          message: '页面名称不能为空',
          showClose: true
        })
        return false
      }
      // 判断页面设置分享海报的按钮名称是否为空
      if (this.pageSetData.pictorial.is_add === '1' && !this.pageSetData.pictorial.share_btn_name) {
        this.$message.error({
          message: '页面分享按钮不能为空',
          showClose: true
        })
        return false
      }
      // 判断页面设置分享海报的分享语是否为空
      if (this.pageSetData.pictorial.is_add === '1' && !this.pageSetData.pictorial.share_desc) {
        this.$message.error({
          message: '页面分享语不能为空',
          showClose: true
        })
        return false
      }
      // 判断页面设置分享海报的分享图不能为空
      if (this.pageSetData.pictorial.is_add === '1' && !this.pageSetData.pictorial.share_img_path) {
        this.$message.error({
          message: '页面分享图不能为空',
          showClose: true
        })
        return false
      }
      //  模块私有校验
      data.forEach((item, index) => {
        switch (item.module_name) {
          case 'm_card': // 会员卡相关校验
            console.log(item)
            if (!item.card_id) {
              this.$message.error({
                message: '请选择会员卡',
                showClose: true
              })
              return false
            }
            break
          case 'm_image_guide': // 图片导航相关校验
            item.nav_group.forEach((item, index) => {
              if (!item.nav_src) {
                this.$message.error({
                  message: '请上传图片导航模块图片',
                  showClose: true
                })
                return false
              }
            })
            break
          case 'm_scroll_image': // 轮播图相关校验
            console.log(item)
            if (item.img_items.length <= 0) {
              this.$message.error({
                message: '请添加轮播图片',
                showClose: true
              })
              return false
            }
        }
      })
      return true
    },
    // 处理保存数据
    handleToSaveModulesData (data, pageSetData) {
      console.log(data, pageSetData)
      let obj = {}
      data.forEach(item => {
        obj[`c_${item.cur_idx}`] = item
      })
      obj['page_cfg'] = pageSetData
      return obj
    }
  }
}
