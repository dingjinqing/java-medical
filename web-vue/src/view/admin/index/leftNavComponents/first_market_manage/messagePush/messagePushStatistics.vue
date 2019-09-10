<template>
  <div class="pushStatistics">
    <el-card>
      <!-- header -->
      <div class="header">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            v-for="(item,index) in headers"
            :label="item.label"
            :name="item.name"
            :key="index"
          ></el-tab-pane>
        </el-tabs>
      </div>
      <!-- 测试封装的组件 -->
      <div>
        <chooseSelect :text="text" />
      </div>
    </el-card>
  </div>
</template>
<script>
import chooseSelect from '@/components/admin/chooseSelect/chooseSelect'
export default {
  name: `pushStatistics`,
  components: { chooseSelect },
  data () {
    return {
      activeName: `1`,
      /**
       * header 标签页的数据
       */
      headers: [
        {
          label: `全部消息推送`,
          name: `0`
        },
        {
          label: `推送统计`,
          name: `1`
        }
      ],
      /**
       * 测试组件的数据
       */
      text: {
        title: `属于`,
        placeholder: `请选择会员标签`,
        txt: `标签人群`
      }
    }
  },
  watch: {
    activeName: 'watchActive'
  },
  methods: {
    // 监听activeName
    watchActive (currentVal, oldVal) {
      console.log(currentVal)
      switch (currentVal) {
        case `0`:
          this.$router.push({
            path: `/api/admin/market/messagePush/all`
          })
          break
        case `1`:
          this.$router.push({
            path: `/api/admin/market/messagePush/pushStatistics`
          })
          break
        default:
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.pushStatistics {
  padding: 10px;
}
</style>
