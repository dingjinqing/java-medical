const crumbs = {
  state: {
    menuFlag: null,
    activeFlag: null
  },
  mutations: {
    TOCHANGE_MENUALL: (state, params) => {
      state.menuFlag = params
    },
    TOCHANGE_ACTIVEMENUALL: (state, params) => {
      state.activeFlag = params
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
    }
  }
}

export default crumbs
