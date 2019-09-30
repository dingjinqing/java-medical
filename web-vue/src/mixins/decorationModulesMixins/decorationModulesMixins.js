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
          obj.name = '优惠卷'
          break
        case 8: // 商品模块
          obj.name = '商品'
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
          message: '页面分享按钮不能为空',
          showClose: true
        })
        return false
      }
      // 判断页面设置分享海报的分享图不能为空
      if (this.pageSetData.pictorial.is_add === '1' && !this.pageSetData.pictorial.share_img_path) {
        this.$message.error({
          message: '页面分享按钮不能为空',
          showClose: true
        })
        return false
      }
      data.forEach((item, index) => {
        switch (item.module_name) {
          case 'm_card': // 会员卡

            break
        }
      })
      return true
    }
  }
}
