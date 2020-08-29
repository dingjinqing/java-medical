const leftNav = {
  state: {
    admin_leftVav_flag: null
  },
  mutations: {
    // 切换admin左侧导航
    TOCHANGE_LEFTNAV: (state, params) => {
      state.admin_leftVav_flag = params
      console.log(state.admin_leftVav_flag)
    }
  },
  actions: {
    changeNavLeft ({ commit }, params) {
      console.log(params)
      commit('TOCHANGE_LEFTNAV', params)
    }
  }
}

export default leftNav
