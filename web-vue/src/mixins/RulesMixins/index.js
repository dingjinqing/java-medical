/**
 * 定义混入组件，存放公共验证部分
 */
export default {
  methods: {
    // 自定义验证规则/首件的件数为大于0的整数
    checkLimitParamFirstNum (rule, value, callback) {
      if (Number.isInteger(Number(value)) && Number(value) > 0) {
        callback()
      } else {
        callback(new Error('首件件数必须为大于0'))
      }
    },
    // 自定义验证规则/首件的运费为大于0的数
    checkLimitParamFirstFee (rule, value, callback) {
      if (Number(value) > 0) {
        callback()
      } else {
        callback(new Error('首件运费必须为大于0'))
      }
    },
    // 自定义验证规则/续重为大于0的整数
    ckeckLimitParamContinueNum (rule, value, callback) {
      if (Number.isInteger(Number(value)) && Number(value) > 0) {
        callback()
      } else {
        callback(new Error('续重必须为大于0'))
      }
    },
    // 自定义验证规则/续件运费为大于0的数
    ckeckLimitParamContinueFee (rule, value, callback) {
      if (Number(value) > 0) {
        callback()
      } else {
        callback(new Error('续件运费必须为大于0的数'))
      }
    },
    // 营销活动/消息推送/添加消息推送
    checkMessageName (rule, value, callback) {
      if (!value) {
        callback(new Error(`请填写消息名称`))
      } else {
        callback()
      }
    },
    // 营销活动/消息推送/添加消息推送
    checkMessageTitle (rule, value, callback) {
      if (!value) {
        callback(new Error(`请填写业务标题`))
      } else {
        callback()
      }
    },
    // 营销活动/消息推送/业务内容
    checkMessageContent (rule, value, callback) {
      if (!value) {
        callback(new Error(`请填写业务内容`))
      } else {
        callback()
      }
    }
  }
}
