const crumbs = {
  state: {
    menuFlag: null,
    activeFlag: null,
    cropperFlag: null,
    activeFresh: null,
    picSpaceCropperFlag: null,
    integralDataNotice: null,
    langFlag: ''
  },
  mutations: {
    TOCHANGE_MENUALL: (state, params) => {
      state.menuFlag = params
      console.log(state.menuFlag)
    },
    TOCHANGE_ACTIVEMENUALL: (state, params) => {
      state.activeFlag = params
      console.log(state.activeFlag)
    },
    TOCHANGE_RECRUITMENTDIALOG: (state, params) => {
      state.cropperFlag = params
      console.log(state.cropperFlag)
    },
    TOCHANGE_AUTOREFRESH: (state, params) => {
      state.activeFresh = params
      console.log(state.activeFresh)
    },
    TOCHANGE_AUTOREFRESHPICSPACE: (state, params) => {
      state.picSpaceCropperFlag = params
      console.log(state.picSpaceCropperFlag)
    },
    TOCHANGE_NOTICEINTEGRALDATA: (state, params) => {
      state.integralDataNotice = params
      console.log(state.integralDataNotice)
    },
    TOCHANGE_LANGCURRENT: (state, params) => {
      state.langFlag = params
    }
  },
  actions: {
    // 权限相关处理
    judgeMenuAll ({ commit }, params) {
      console.log(params)
      commit('TOCHANGE_MENUALL', params)
    },
    // 主动调取判断权限
    judgeActiveMeunAll ({ commit }, params) {
      console.log(params)
      commit('TOCHANGE_ACTIVEMENUALL', params)
    },
    // 调取裁剪弹窗
    recruitMentandDialog ({ commit }, params) {
      commit('TOCHANGE_RECRUITMENTDIALOG', params)
    },
    // 主动刷新图片弹窗类数据
    autoRefresh ({ commit }, params) {
      commit('TOCHANGE_AUTOREFRESH', params)
    },
    // 图片空间主动刷新数据
    autoRefreshPicSpace ({ commit }, params) {
      commit('TOCHANGE_AUTOREFRESHPICSPACE', params)
    },
    // 积分管理模块保存通知子组件传递数据
    handleToNoticeIntegral ({ commit }, params) {
      commit('TOCHANGE_NOTICEINTEGRALDATA', params)
    }
  }
}

export default crumbs
