const smallProgramManagement = {
  state: {
    selectlinksIndex: null,
    selectlinksLevelOneBottom: null,
    choisePath: null,
    afferentPath: null
  },
  mutations: {
    CHANGE_SELECTLINKS: (state, params) => {
      state.selectlinksIndex = params
      console.log(state.selectlinksIndex)
    },
    CHANGE_SELECTLEFT: (state, params) => {
      console.log(params)
      state.selectlinksLevelOneBottom = params
    },
    CHIOSE_PAGEPATH: (state, params) => {
      state.choisePath = params
    },
    AFFERENT_PATHTOPAGE: (state, params) => {
      state.afferentPath = params
    }
  },
  actions: {
    // 选择链接弹窗点击左侧菜单
    changeSelectlink ({ commit }, params) {
      console.log(params)
      commit('CHANGE_SELECTLINKS', params)
    },
    // 选择链接弹窗左侧菜单最后两项flag
    changeSelectLinkLeft ({ commit }, params) {
      console.log(params)
      commit('CHANGE_SELECTLEFT', params)
    },
    // 选中链接
    choisePagePath ({ commit }, params) {
      commit('CHIOSE_PAGEPATH', params)
    },
    // 链接传入调用选择链接弹窗页面
    afferentPathToPage ({ commit }, params) {
      commit('AFFERENT_PATHTOPAGE', params)
    }
  }
}

export default smallProgramManagement
