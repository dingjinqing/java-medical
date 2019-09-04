const crumbs = {
  state: {
    // 接收模块后动态添加边框类
    decDorderColor: []
  },
  mutations: {
    TOCHANGE_DECDORDERCOLOR: (state, params) => {
      console.log(params)
      state.decDorderColor = params
    }
  },
  actions: {
    // 动态改变接收模块的边框
    changeDecDorderColor ({ commit }, params) {
      commit('TOCHANGE_DECDORDERCOLOR', params)
    }
  }
}

export default crumbs
