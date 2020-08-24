const smallProcedures = {
  state: {
    clickNode: null,
    allNodes: null,
    Micropage: null
  },
  mutations: {
    // tree点击节点
    TOCLICK_NODE: (state, params) => {
      console.log(params)
      state.clickNode = params
      console.log(state.clickNode)
    },
    GETALL_NODES: (state, params) => {
      state.allNodes = params
    },
    TOCALLMICROPAGE: (state, params) => {
      state.Micropage = params
    }
  },
  actions: {
    // 点击节点
    changeTreeNode ({ commit }, params) {
      console.log(params, 's')

      commit('TOCLICK_NODE', params)
    },
    // 初始化获取tree所有节点数据
    allNodes ({ commit }, params) {
      console.log(params)
      commit('GETALL_NODES', params)
    },
    // 调起新建微页面弹窗
    handleToCallMicropage ({ commit }, params) {
      console.log(params)
      commit('TOCALLMICROPAGE', params)
    }
  }
}

export default smallProcedures
