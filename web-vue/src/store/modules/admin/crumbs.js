const crumbs = {
  state: {
    crumbsTitle: null,
    // 省市区数据
    proAndUrData: null,
    // 会员卡数据
    cardholderData: null
  },
  mutations: {
    TOCHANGE_CRUMBSTITLE: (state, params) => {
      state.crumbsTitle = params
    },
    TOCHANGE_PROANDUR: (state, params) => {
      state.proAndUrData = params
    },
    TOCHANFE_CARDCRUMDATA: (state, params) => {
      state.cardholderData = params
    }
  },
  actions: {
    // 切换crumbs title
    changeCrumbstitle ({ commit }, params) {
      commit('TOCHANGE_CRUMBSTITLE', params)
    },
    // 传递省市区
    handleProAndUrData ({ commit }, params) {
      commit('TOCHANGE_PROANDUR', params)
    },
    // 传递会员卡独享数据
    handleToChangeCardCrumb ({ commit }, params) {
      commit('TOCHANFE_CARDCRUMDATA', params)
    }
  }
}

export default crumbs
