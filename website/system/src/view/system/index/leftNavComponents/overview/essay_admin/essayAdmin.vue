<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
    @tab-click="handleClick"
  >
    <el-tab-pane
      name="first"
      label="文章列表"
    >
      <articleList
        @editShow="show"
        v-if="firstShow"
      />
    </el-tab-pane>
    <el-tab-pane
      name="second"
      label="文章分类"
    >
      <articleTypeList v-if="secondShow" />
    </el-tab-pane>
    <el-tab-pane
      name="third"
      label="文章发布"
    >
      <articleAdd
        @show="show"
        v-if="thirdShow"
      />
    </el-tab-pane>

    <el-tab-pane
      v-if="fourthShow"
      name="fourth"
      label="文章编辑"
    >
      <articleAdd
        :isEdit="isEdit"
        :articleId="articleId"
        @show="show"
        v-if="fourthShow"
      />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import articleList from './articleList.vue'
import articleTypeList from './articleTypeList.vue'
import articleAdd from './articleAdd.vue'

export default {
  name: 'accountList',
  components: {
    articleList,
    articleTypeList,
    articleAdd
  },
  data () {
    return {
      tabActive: 'first',
      isShowEditAccount: false,
      sendData: null,
      firstShow: true,
      secondShow: false,
      thirdShow: false,
      fourthShow: false,
      isEdit: false,
      articleId: null
    }
  },
  created () {
    console.log(this.$route.params)
    if (this.$route.params.flag === true) {
      this.tabActive = 'third'
      this.isShowShopList = true
    }
  },
  methods: {
    show (data) {
      console.log('接受')
      console.log(data)
      if (data.flag === 2) {
        this.tabActive = 'second'
        this.firstShow = false
        this.secondShow = true
        this.thirdShow = false
        this.fourthShow = false
      }
      if (data.flag === 4) {
        this.tabActive = 'fourth'
        this.firstShow = false
        this.secondShow = false
        this.thirdShow = false
        this.fourthShow = true
        this.isEdit = true
        this.articleId = data.articleId
      }
      if (data.flag === 1) {
        this.tabActive = 'first'
        this.firstShow = true
        this.secondShow = false
        this.thirdShow = false
        this.fourthShow = false
      }
    },
    handleClick (tab, event) {
      if (this.tabActive === 'first') {
        this.firstShow = true
        this.secondShow = false
        this.thirdShow = false
        this.fourthShow = false
      } if (this.tabActive === 'second') {
        this.firstShow = false
        this.secondShow = true
        this.thirdShow = false
        this.fourthShow = false
      }
      if (this.tabActive === 'third') {
        this.firstShow = false
        this.secondShow = false
        this.thirdShow = true
        this.fourthShow = false
      }
    }
  },
  watch: {
  }
}
</script>

<style scoped lang="scss">
.tab {
  margin-top: 18px;
  /deep/ .el-tabs__header .el-tabs__item.is-active {
    border-top: 3px solid #57889c;
    color: #333;
    font-size: 14px;
    font-weight: 700;
  }
  /deep/ .el-tabs__item {
    height: 46px;
    padding: 0 10px 3px 10px !important;
    border-top: 2px solid transparent;
    font-weight: 400;
  }
  /deep/ .el-tabs__content {
    padding: 10px 0px 0px 0px;
    background: #e0e3ec;
  }
}
</style>
