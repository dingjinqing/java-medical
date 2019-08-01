const membershipList = {
  state: {
    membershipdetailflag: 0
  },
  mutations: {
    TOCTURN_MEMEBERSHIPDETAIL: (state, params) => {
      state.membershipdetailflag = params
    }
  },
  actions: {
    // 切换会员详情及会员列表其它子页面
    ToTurnMemberShipDetail ({ commit }, params) {
      console.log(params)
      commit('TOCTURN_MEMEBERSHIPDETAIL', params)
    }
  }
}

export default membershipList
