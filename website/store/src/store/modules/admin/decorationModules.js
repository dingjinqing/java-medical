const crumbs = {
  state: {
    // 接收模块后动态添加边框类
    decDorderColor: [],
    // 页面装修模块数据
    ModulesData: {},
    // 页面装修右侧部分修改模块数据
    modifyModulesData: {}
  },
  mutations: {
    TOCHANGE_DECDORDERCOLOR: (state, params) => {
      console.log(params)
      state.decDorderColor = params
    },
    TOCHANGE_SENDMODULESDATA: (state, params) => {
      state.ModulesData = params
    },
    // 页面装修右侧部分修改模块数据
    TOSEND_MODIFYMODULESDATA: (state, params) => {
      state.modifyModulesData = params
    }
  },
  actions: {
    // 动态改变接收模块的边框
    changeDecDorderColor ({ commit }, params) {
      commit('TOCHANGE_DECDORDERCOLOR', params)
    },
    // 页面装修中部与右侧部分通信
    handleToSendModulesData ({ commit }, params) {
      commit('TOCHANGE_SENDMODULESDATA', params)
    }
  }
}

export default crumbs
