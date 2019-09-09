<template>
  <div class="container">
    <!-- 运费模板容器卡片 -->
    <el-card>
      <section>
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <!-- 运费模板 -->
          <el-tab-pane
            v-for="(item,i) in labels"
            :label="item[`label`]"
            :name="item[`name`]"
            :key="i"
          >
          </el-tab-pane>
          <!-- 编辑运费模板 -->
          <el-tab-pane
            v-if="isShowUpdate"
            label="运费模板编辑"
            name="4"
          >
          </el-tab-pane>
        </el-tabs>
      </section>
      <router-view></router-view>
    </el-card>
  </div>
</template>
<script>
// import deliverTemplateHeader from './deliverTemplateHeader'
// import headerTabs from '@/components/admin/headerTabs/headerTabs'
// import deliverTemplateMain from './deliverTemplateMain'
import deliverTemplateList from './deliverTemplateList'
import deliverTemplateWeightList from './deliverTemplateWeightList'
import deliverTemplateAdd from './deliverTemplateAdd'
import deliverTemplateWeightAdd from './deliverTemplateWeightAdd'
import deliverTemplateEdit from './deliverTemplateEdit'
export default {
  // 组件名
  name: 'deliverTemplate',
  components: { deliverTemplateEdit, deliverTemplateList, deliverTemplateWeightList, deliverTemplateAdd, deliverTemplateWeightAdd },
  // data 数据
  data () {
    return {
      activeName: `0`,
      isShowUpdate: false,
      labels: [
        {
          label: `运费模板列表`,
          name: `0`
        },
        {
          label: `重量运费模板列表`,
          name: `1`
        },
        {
          label: `添加运费模板`,
          name: `2`
        },
        {
          label: `添加重量运费模板`,
          name: `3`
        }
      ]

    }
  },
  // 生命周期钩子
  created () {
    this.$http.$on(`showUpDate`, (val) => {
      this.isShowUpdate = val
    })
  },
  mounted () {
    this.$http.$on(`activeName`, (val) => { this.activeName = val })
    this.$http.$on(`showUpDate`, (val) => {
      this.isShowUpdate = val
      this.activeName = `4`
    })
    // this.refresh()
  },
  watch: {
    'activeName': 'fun'
  },
  // 方法
  methods: {
    fun (val) {
      if (val !== `4`) {
        this.isShowUpdate = false
      }
    },
    handleClick (tab) {
      switch (tab.index) {
        case '0': this.$router.push({ name: `deliverTemplateList` }); break
        case '1': this.$router.push({ name: `deliverTemplateWeightList` }); break
        case '2': this.$router.push({ name: `deliverTemplateAdd` }); break
        case '3': this.$router.push({ name: `deliverTemplateWeightAdd` }); break
        case '4': this.$router.push({ name: `deliverTemplateEdit` }); break
        default: break
      }
    },
    refresh () {
      console.log(this.$route.name)
      switch (this.$route.name) {
        case 'deliverTemplateList': this.activeName = `0`; break
        case 'deliverTemplateWeightList': this.activeName = `1`; break
        case 'deliverTemplateAdd': this.activeName = `2`; break
        case 'deliverTemplateWeightAdd': this.activeName = `3`; break
        case 'deliverTemplateEdit': this.activeName = `4`; this.isShowUpdate = true; break
        default: break
      }
    }
  }

}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
}
</style>
