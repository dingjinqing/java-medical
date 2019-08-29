const crumbs = {
  state: {
    crumbsTitle: null,
    // 省市区数据
    proAndUrData: null,
    // 会员卡数据
    cardholderData: null,
    // 会员卡充值明细数据
    refillDetails: null
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
    },
    TOCHANFE_FILLDETAILCRUMB: (state, params) => {
      state.refillDetails = params
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
    },
    // 会员卡充值明细独享数据
    handleTorefillDetCrumb ({ commit }, params) {
      commit('TOCHANFE_FILLDETAILCRUMB', params)
    }
  }
}

export default crumbs
