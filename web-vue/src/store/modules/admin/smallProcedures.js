const smallProcedures = {
  state: {
    clickNode: null,
    allNodes: null
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
    }
  }
}

export default smallProcedures
