const crumbs = {
  state: {
    crumbsTitle: null
  },
  mutations: {
    TOCHANGE_CRUMBSTITLE: (state, params) => {
      state.crumbsTitle = params
    }
  },
  actions: {
    // 切换crumbs title
    changeCrumbstitle ({ commit }, params) {
      console.log(params)
      commit('TOCHANGE_CRUMBSTITLE', params)
    }
  }
}

export default crumbs
