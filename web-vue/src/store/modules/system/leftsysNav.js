const leftsysNav = {
  state: {
    system_leftVav_flag: null
  },
  mutations: {
    // 切换system左侧导航
    TOCHANGE_LEFTNAV: (state, params) => {
      state.system_leftVav_flag = params
      // console.log(state.system_leftVav_flag)
    }
  },
  actions: {
    changesysNavLeft ({ commit }, params) {
      console.log(params)
      commit('TOCHANGE_LEFTNAV', params)
    }
  }
}

export default leftsysNav
