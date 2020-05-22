const membershipList = {
  state: {
    membershipdetailflag: 0,
    toHandleSetUpMemflag: null,
    toHandleSelectingUsersflag: null
  },
  mutations: {
    TOCTURN_MEMEBERSHIPDETAIL: (state, params) => {
      state.membershipdetailflag = params
    },
    TOHANDLE_SETUPMEMDIALOG: (state, params) => {
      state.toHandleSetUpMemflag = params
    },
    TOHANDLE_SELECTINGUSERDIALOG: (state, params) => {
      state.toHandleSelectingUsersflag = params
    }
  },
  actions: {
    // 切换会员详情及会员列表其它子页面
    ToTurnMemberShipDetail ({ commit }, params) {
      console.log(params)
      commit('TOCTURN_MEMEBERSHIPDETAIL', params)
    },
    // 控制会员列表弹窗
    toHandleSetUpMemDialog ({ commit }, params) {
      commit('TOHANDLE_SETUPMEMDIALOG', params)
    },
    // 控制选择用户弹窗
    toHandleSelectingUsersDialog ({ commit }, parmas) {
      commit('TOHANDLE_SELECTINGUSERDIALOG', parmas)
    }
  }
}

export default membershipList
