<template>
  <div
    class="EvaluationContent"
    :class="{goodsEvaluationPage:activeName === 'fourth'}"
  >
    <div class="EvaluationContent_main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          :label="$t('evaluation.evaluationTabs.evaluationRecord')"
          name="first"
        >
          <!-- <evaluationRecord v-if="activeName === 'first'" /> -->
        </el-tab-pane>
        <el-tab-pane
          :label="$t('evaluation.evaluationTabs.evaluationReview')"
          name="second"
        >
          <evaluationReview v-if="activeName === 'second'">
            <template v-slot:evaluationRecord>
              <evaluationRecord target="Record" />
            </template>
          </evaluationReview>
        </el-tab-pane>
        <el-tab-pane
          :label="$t('evaluation.evaluationTabs.productList')"
          name="third"
        >
          <evaluationGoodsList
            v-if="activeName === 'third'"
            @handleAddEvaluation="addGoodsEvaluation"
          />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('evaluation.evaluationTabs.addEvaluation')"
          name="fourth"
          v-if="activeName === 'fourth'"
        >
          <evaluationGoods
            :goods-info="goodsInfo"
            :target.sync="activeName"
          />
        </el-tab-pane>
      </el-tabs>
    </div>

    <evaluationRecord v-if="activeName === 'first'" />
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
  // watch: {
  //   lang () {
  //     this.langDefault()
  //   }
  // },
  mounted () {
    // 初始化国际语言
    this.langDefault()
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
.EvaluationContent {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  position: relative;
}
.EvaluationContent_main {
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
