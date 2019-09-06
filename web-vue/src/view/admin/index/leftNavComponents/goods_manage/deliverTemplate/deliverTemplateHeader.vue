<template>
  <!-- 运费模板头部内容 -->
  <div class="deliverTemplateHeader">
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
    >
      <!-- 运费模板列表 -->
      <el-tab-pane
        label="运费模板列表"
        name="0"
      >
      </el-tab-pane>
      <!-- 重量运费模板列表 -->
      <el-tab-pane
        label="重量运费模板列表"
        name="1"
      >
      </el-tab-pane>
      <!-- 添加运费模板 -->
      <el-tab-pane
        label="添加运费模板"
        name="2"
      >
      </el-tab-pane>
      <!-- 添加重量运费模板-->
      <el-tab-pane
        label="添加重量运费模板"
        name="3"
      >
      </el-tab-pane>
      <!-- 运费模板编辑-->
      <section v-show="isShowDeliverTemplateEdit">
        <el-tab-pane
          label="运费模板编辑"
          name="4"
        />
      </section>

    </el-tabs>
  </div>
</template>
<script>
export default {
  name: 'deliverTemplateHeader',
  data () {
    return {
      activeName: '0',
      isShowDeliverTemplateEdit: false,
      active: this.$route.query.active
    }
  },
  created () {
    this.refresh()
  },
  watch: {
    // 'activeName' (val) {

    // }
  },
  methods: {
    // 刷新的时候锁定el-tab-pane
    refresh () {
      console.log(this.$route.query.active)
      // switch (this.$route.name) {
      //   case 'deliverTemplateList': this.activeName = `0`; break
      //   case 'deliverTemplateWeightList': this.activeName = `1`; break
      //   case 'deliverTemplateAdd': this.activeName = `2`; break
      //   case 'deliverTemplateWeightAdd': this.activeName = `3`; break
      //   case 'deliverTemplateEdit': this.activeName = `3`; break
      //   default: break
      // }
      if (this.active === `0`) {
        this.activeName = '0'
      }
    },
    // 点击头部跳转路由
    handleClick (tab, event) {
      // console.log(tab)
      switch (tab.index) {
        case '0': this.$router.push({ name: `deliverTemplateList` }); break
        case '1': this.$router.push({ name: `deliverTemplateWeightList` }); break
        case '2': this.$router.push({ name: `deliverTemplateAdd` }); break
        case '3': this.$router.push({ name: `deliverTemplateWeightAdd` }); break
        case '4': this.$router.push({ name: `deliverTemplateEdit` }); break

        default: break
      }
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
