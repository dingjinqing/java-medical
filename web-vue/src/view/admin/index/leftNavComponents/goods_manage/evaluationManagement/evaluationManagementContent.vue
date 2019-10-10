<template>
  <div
    class="brandManagementContent"
    :class="{goodsEvaluationPage:activeName === 'fourth'}"
  >
    <div class="brandManagementContent_main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="评价记录"
          name="first"
        >
          <evaluationRecord v-if="activeName === 'first'" />
        </el-tab-pane>
        <el-tab-pane
          label="评价审核"
          name="second"
        >
          <evaluationReview v-if="activeName === 'second'">
            <template v-slot:evaluationRecord>
              <evaluationRecord />
            </template>
          </evaluationReview>
        </el-tab-pane>
        <el-tab-pane
          label="商品列表"
          name="third"
        >
          <evaluationGoodsList
            v-if="activeName === 'third'"
            @handleAddEvaluation="addGoodsEvaluation"
          />
        </el-tab-pane>
        <el-tab-pane
          label="添加评价"
          name="fourth"
          v-if="activeName === 'fourth'"
        >
          <evaluationGoods :goods-info="goodsInfo" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    evaluationRecord: () => import('./evaluationRecord'),
    evaluationReview: () => import('./evaluationReview'),
    evaluationGoodsList: () => import('./evaluationGoodsList'),
    evaluationGoods: () => import('./evaluationGoods')
  },
  data () {
    return {
      activeName: 'first',
      goodsInfo: null
    }
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      console.log(tab, event)
    },
    addGoodsEvaluation (goodsInfo) {
      this.goodsInfo = goodsInfo
      this.activeName = 'fourth'
    }
  }
}
</script>
<style lang="scss" scoped>
.brandManagementContent {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  position: relative;
}
.brandManagementContent_main {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
  padding: 10px;
}
.goodsEvaluationPage {
  padding-bottom: 62px;
}
/deep/ .table_box {
  background-color: #fff;
  .operation {
    display: flex;
    flex-wrap: wrap;
    margin-left: -5px;
    justify-content: space-between;
    > .item {
      font-size: 14px;
      color: #66b1ff;
      cursor: pointer;
      margin-left: 5px;
    }
  }
}
/deep/ .filters {
  display: flex;
  line-height: 32px;
  margin-left: -15px;
  margin-bottom: 10px;
  .filters_item {
    max-width: 250px;
    display: flex;
    margin-left: 15px;
    > span {
      min-width: 80px;
      font-size: 14px;
    }
  }
}
</style>
