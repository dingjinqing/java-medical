<template>
  <div class="allDepartmentHeaderTab">
    <div
      class="tabItem"
      @click="itemClicked('officesList')"
      :class="{tabItemActive: tabIndex === 0}"
    >科室列表</div>
    <!-- <div
      class="tabItem"
      @click="itemClicked('allGoodsRecommendSort')"
      :class="{tabItemActive: tabIndex === 1}"
    >{{$t('goodsRecommendSorts.recommendSortsList')}}</div> -->
    <div
      class="tabItem tabItemActive"
      v-if="tabIndex === 2"
    >添加科室</div>
    <!-- <div
      class="tabItem tabItemActive"
      v-if="tabIndex === 3"
    >{{$t('goodsRecommendSorts.recommendSortsAdd')}}</div> -->
    <div
      class="tabItem tabItemActive"
      v-if="tabIndex === 4"
    >编辑科室</div>
    <!-- <div
      class="tabItem tabItemActive"
      v-if="tabIndex === 5"
    >{{$t('goodsRecommendSorts.recommendSortsEdit')}}</div> -->
  </div>
</template>

<script>
export default {
  name: 'allDepartmentHeaderTab',
  props: {
    tabIndex: Number
  },
  methods: {
    itemClicked (routerName) {
      this.$router.push({ name: routerName })
      console.log(this.$router)
    }
  },
  mounted () {
    this.langDefault()
  }
}
</script>

<style scoped>
.allDepartmentHeaderTab {
  border-bottom: 1px solid #eeeeee;
  color: #666;
  display: flex;
}
.tabItem {
  font-size: 14px;
  min-width: 50px;
  line-height: 40px;
  /* margin: 0px 5px; */
  margin-right: 20px;
  cursor: pointer;
}
.tabItemActive {
  border-bottom: 1.5px solid #5a8bff;
}
</style>
