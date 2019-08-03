const addingGoodsFooter = {
  state: {
    // 添加商品内容区域的active值
    addingGoodsContent_active: null
  },
  mutations: {
    CHANGE_ACTIVE: (state, params) => {
      state.addingGoodsContent_active = params
    }
  },
  actions: {
    changeActive ({ commit }, params) {
      commit('CHANGE_ACTIVE', params)
    }
  }
}
export default addingGoodsFooter
