const crumbs = {
  state: {
    crumbsTitle: null,
    // 省市区数据
    proAndUrData: null
  },
  mutations: {
    TOCHANGE_CRUMBSTITLE: (state, params) => {
      state.crumbsTitle = params
    },
    TOCHANGE_PROANDUR: (state, params) => {
      state.proAndUrData = params
    }
  },
  actions: {
    // 切换crumbs title
    changeCrumbstitle ({ commit }, params) {
      console.log(params)
      commit('TOCHANGE_CRUMBSTITLE', params)
    },
    // 传递省市区
    handleProAndUrData ({ commit }, params) {
      commit('TOCHANGE_PROANDUR', params)
    }
  }
}

export default crumbs
