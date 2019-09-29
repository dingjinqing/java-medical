export default {
  methods: {
    // 模块数据填充处理
    handleToAddModules (index) {
      let obj = {}
      switch (index) {
        case 1:
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
        case 2:
          obj.name = '优惠卷'
          break
        case 8:
          obj.name = '商品'
          break
        case 9:
          obj = {
            'module_name': 'm_goods_search', // 模块名称
            'search_style': '1', // 框体样式
            'search_font': '1', // 框体高度
            'box_color': '#eee', // 框体颜色
            'back_color': '#fff', // 背景颜色
            'search_sort': '0', // 商家分类是否显示
            'sort_bg_color': '#666666' // 图标颜色
          }
      }

      return obj
    }
  }
}
