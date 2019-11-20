const myMixin = {
  data () {
    return {
      lang: '',
      currency: [],
      currencyPool: {
        'CNY': {
          'zh_CN': ['元', '￥', 'CNY'],
          'en_US': ['yuan', '￥', 'CNY']
        },
        'USD': {
          'zh_CN': ['美元', '$', 'USD'],
          'en_US': ['dollar', '$', 'USD']
        }
      }
    }
  },
  methods: {
    // 初始化语言
    langDefault () {
      console.log('初始化语言')
      this.$http.$on('CHANGE_LANGUAGE', res => {
        console.log('feifeifei')
        this.lang = localStorage.getItem('WEPUBAO_LANGUAGE')
        // 人民币'CNY' 美元USD
        this.adaptation()
      })
      this.lang = localStorage.getItem('WEPUBAO_LANGUAGE')
      // console.log(localStorage.getItem('WEPUBAO_LANGUAGE'))
      this.adaptation()
    },
    adaptation () {
      console.log(this.lang)
      if (localStorage.getItem('V-Currency')) {
        this.currency = this.currencyPool[localStorage.getItem('V-Currency')][this.lang]
      }
      console.log(this.currency)
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en_US') {
        // this.$i18n.locale = 'en'
        this.Recommend_class = 'Recommend_class'

        this.lang_with = 'width:63px'
        this.langData_show = this.langData_en
        this.loginData_show = this.loginData_en
        this.mar_class = 'mar_class'

        this.imgUrlData = this.imgUrlData_en

        this.contactData = this.contactData_en

        // admin contact
        this.en_phone = 'admin_en_phone_width'
        this.en_line = 'admin_en_onLine_width'
        // admin menu width
        this.menu_width = 'admin_menu_width'

        // admin head width
        this.modifyWidth = 'headWidth'

        // admin 修改头像遮罩span高度适配
        this.maskspanheight = 'maskspanheight'

        // admin imageDalog tips 行高
        this.imageDalogTip_lineHeight = 'imageDalogTip_lineHeight'

        // admin imagrDalog p 适配
        this.imageDalog_p_height = 'imageDalog_p_height'

        // admin imageDalog totle适配
        this.admin_imageDalog_totle = 'admin_imageDalog_totle'

        // admin shopStyle
        this.en_ch_title_width_one = 'en_ch_title_width_one'
        this.en_ch_title_width_two = 'en_ch_title_width_two'

        // admin 会员管理  会员列表
        this.memberListliNav = 'memberListliNav'
        this.memberListliLast = 'memberListliLast'
        this.minixLabel = 'minixLabel'
        this.specielNav = 'specielNav'
        this.specialliNavTwo = 'specialliNavTwo'
        this.minx_bottom_select = 'minx_bottom_select'
        this.assetsUl = 'assetsUl'
        this.mixinleftDiv = 'mixinleftDiv'

        // system小程序版本 会员详情兼容
        this.payDialogDivEn = 'payDialogDivEn'
        this.payDialogDivEnLast = 'payDialogDivEnLast'
        this.payDialogDivEnhiddenTwo = 'payDialogDivEnhiddenTwo'

        // admin页面框架
        this.headerNavEn = 'headerNavEn'
        this.leftMenuEn = 'leftMenuEn'

        // admin页面装修部分适配
        this.leftComClass = 'leftComClass'
        this.pageClassification = 'pageClassification'
        this.navigation = 'navigation'
        this.moduleSpacing = 'moduleSpacing'
        this.moduleHeight = 'moduleHeight'
        this.pictorialVisibility = 'pictorialVisibility'
        this.bgColorClass = 'bgColorClass'
        this.hoverTips = 'hoverTips'
        this.guideCircleClass = 'guideCircleClass'

        this.specialLi = 'specialLi'
        this.magic = 'magic'

        this.columnFlag = true
      } else {
        // this.$i18n.locale = 'cn'
        this.Recommend_class = ''

        this.langData_show = this.langData_cn
        this.loginData_show = this.loginData_cn
        this.mar_class = ''

        this.imgUrlData = this.imgUrlData_cn

        this.contactData = this.contactData_cn

        // admin contact
        this.en_phone = ''
        this.en_line = ''
        // admin menu width
        this.menu_width = ''

        // admin head width
        this.modifyWidth = ''

        // admin 修改头像遮罩span高度适配
        this.maskspanheight = ''

        // admin imageDalog tips 行高
        this.imageDalogTip_lineHeight = ''

        // admin imagrDalog p 适配
        this.imageDalog_p_height = ''

        // admin imageDalog totle适配
        this.admin_imageDalog_totle = ''

        // admin shopStyle
        this.en_ch_title_width_one = ''
        this.en_ch_title_width_two = ''

        // admin 会员管理  会员列表
        this.memberListliNav = ''
        this.memberListliLast = ''
        this.minixLabel = ''
        this.specielNav = ''
        this.specialliNavTwo = ''
        this.minx_bottom_select = ''
        this.assetsUl = ''
        this.mixinleftDiv = ''

        // system小程序版本 会员详情兼容
        this.payDialogDivEn = ''
        this.payDialogDivEnLast = ''
        this.payDialogDivEnhiddenTwo = ''

        // admin页面框架
        this.headerNavEn = ''
        this.leftMenuEn = ''

        // admin页面装修部分适配
        this.leftComClass = ''
        this.pageClassification = ''
        this.navigation = ''
        this.moduleSpacing = ''
        this.moduleHeight = 'moduleHeightCn'
        this.pictorialVisibility = ''
        this.bgColorClass = ''
        this.hoverTips = null
        this.guideCircleClass = ''
        this.magic = false
        this.specialLi = ''

        this.columnFlag = false
      }
    },
    // 取营销活动状态字符串（进行中、已过期、未开始、已停用），status==0代表停用，status==1代表启用
    getActStatusString (status, startTime, endTime) {
      let d = new Date()
      if (status === 1) {
        if (d < new Date(startTime)) {
          return this.$t('marketCommon.unstarted')
        } else if (d > new Date(endTime)) {
          return this.$t('marketCommon.ended')
        } else {
          return this.$t('marketCommon.ongoing')
        }
      } else {
        return this.$t('marketCommon.deactivated')
      }
    },
    // 处理平台分类、商家分类下拉框数据函数  data是接口原始数据 idName：catId是平台分类  sortId是商家分类
    disposeGoodsSortAndCatData (data, idName) {
      let retObj = {}

      for (let i = 0; i < data.length; i++) {
        let item = data[i]

        // 是否自身节点被创建过（子节点先遍历到了）
        let selfItem = retObj[item[idName]]
        if (selfItem === undefined) {
          // 未遍历到则初始化自己
          retObj[item[idName]] = {
            'item': item,
            children: []
          }
          selfItem = retObj[item[idName]]
        } else {
          // 已创建过，（因提前遍历了子节点而创建）
          selfItem.item = item
        }

        let parentItem = retObj[item.parentId]
        // 有父亲直接插入
        if (parentItem !== undefined) {
          parentItem.children.push(selfItem)
        } else {
          // 没有则创建临时父亲
          retObj[item.parentId] = {
            'item': null,
            children: [selfItem]
          }
        }
      }

      let retArr = []

      if (data.length === 0) {
        return retArr
      }
      let rootArr = retObj['0'].children
      // 处理结果将对象变为数组
      for (let i = 0; i < rootArr.length; i++) {
        let retItem = rootArr[i]
        retArr.push(retItem.item)
        if (retItem.children.length > 0) {
          rootArr.splice(i + 1, 0, ...(retItem.children))
        }
      }
      return retArr
    }
  }
}
export default myMixin
