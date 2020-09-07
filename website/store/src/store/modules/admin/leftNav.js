const leftNav = {
  state: {
    admin_leftVav_flag: null,
    showMenuData: null
  },
  mutations: {
    // 切换admin左侧导航
    TOCHANGE_LEFTNAV: (state, params) => {
      state.admin_leftVav_flag = params
      console.log(state.admin_leftVav_flag)
    },
    MENU_DATA: (state, params) => {
      localStorage.setItem('showMenuData', JSON.stringify(params))
      state.showMenuData = params
    }
  },
  actions: {
    changeNavLeft ({ commit }, params) {
      console.log(params)
      commit('TOCHANGE_LEFTNAV', params)
    },
    getShowMenuData ({ commit }, params) {
      console.log(params)
      commit('MENU_DATA', params)
    }
  }
}

export default leftNav
