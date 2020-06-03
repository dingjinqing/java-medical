<template>
  <div class="messagePush">
    <el-card>
      <!-- header -->
      <div>
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
      <!-- 路由出口 -->
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </el-card>

  </div>
</template>

<script>
export default {
  name: `messagePush`,
  data () {
    return {
      activeName: ``,
      headers: [
        {
          label: this.$t(`messagePush.fullMessagePush`),
          name: `/admin/market/messagePush/all`
        },
        {
          label: this.$t(`messagePush.pushTheStatistics`),
          name: `/admin/market/messagePush/pushStatistics`
        }
      ]
    }
  },
  created () {
    this.activeName = this.$route.path
  },
  watch: {
    lang () {
      this.headers = [
        {
          label: this.$t(`messagePush.fullMessagePush`),
          name: `/admin/market/messagePush/all`
        },
        {
          label: this.$t(`messagePush.pushTheStatistics`),
          name: `/admin/market/messagePush/pushStatistics`
        }
      ]
    }
  },
  mounted () {
    // 初始化国际化
    this.langDefault()
  },
  updated () {
    this.activeName = this.$route.path
  },
  // 方法
  methods: {
    handleClick (tab) {
      this.$router.push(tab.name)
    }
  }
}
</script>

<style lang="scss" scoped>
.messagePush {
  padding: 10px;
}
</style>
