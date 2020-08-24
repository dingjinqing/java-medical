const goodsManagement = {
  state: {
    goodsIds: [],
    editGoodsId: null
  },
  mutations: {
    TRANSIT_GOODSIDS: (state, params) => {
      console.log(params)
      state.goodsIds = params
    },
    TRANSIT_EDITGOODSID: (state, params) => {
      console.log(params)
      state.editGoodsId = params
    }
  },
  actions: {
    // 传递选择商品弹窗的商选中的商品id
    transmitGoodsIds ({ commit }, params) {
      console.log(params)
      commit('TRANSIT_GOODSIDS', params)
    },
    // 编辑回显商品id传递
    transmitEditGoodsId ({ commit }, params) {
      console.log(params)
      commit('TRANSIT_EDITGOODSID', params)
    }
  }
}

export default goodsManagement
