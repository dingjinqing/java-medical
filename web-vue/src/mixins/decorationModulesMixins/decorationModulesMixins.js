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
            'module_name': 'm_goods', // 模块名称
            'title': '', // 标题  y
            'title_link': '', // 标题链接 y
            'tit_center': '1', // 标题位置 0 不选中标题居中  1选中标题居中
            'recommend_type': '0', // 模块推荐 0自动推荐  1手动推荐
            'goods_items': [ // 商品列表数据

            ],
            'col_type': '4', // 4  1  2  3  0
            'goods_display': '0',
            'goods_num': '4', // 模块推荐-商品数量
            'min_price': '', // 模块推荐-商品价格最低价格
            'max_price': '', // 模块推荐-商品价格最高价格
            'keywords': '', // 模块推荐-关键词
            'sort_type': '1', // 模块推荐-排序规则
            'img_url': '', // 图标
            'goods_module_title': '0', // 模块标题类型  0不设置  1文字标题 2图片标题  y
            'img_title_url': '', // 标题图片
            'hide_name': 0, // 商品名称
            'hide_price': 0, // 商品价格
            'hide_label': 0, // 商品标签
            'cart_btn': 1, // 购买按钮
            'other_message': 0, // 其它信息
            'if_radius': '0', // 模块角度 0直角  1圆角
            'goods_module_style': '0', // 0白底无边框  1边框投影 2白底有边框
            'goods_area': 'all', // 模块推荐-商品范围
            'goods_area_data': '',
            'goods_type': '0', // 活动商品 0请选择 ..
            'show_market': '1', // 其他信息选中 隐藏radion字段  1市场价 2销量 3评价数
            'goods_module_bg': '0', // 背景颜色 0与页面一致  1自定义
            'goods_bg_color': '#f5f5f5' // 背景自定义颜色
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
