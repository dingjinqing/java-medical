<template>
  <div class="templateMessage">
    <el-card>
      微信消息模板 因微信平台限制，公众号消息最多可发送25类，小程序消息最多可发送25类，请谨慎选择
    </el-card>
  </div>
</template>

<script>
import { templateQueryApi } from '@/api/admin/basicConfiguration/templateMessage'
export default {
  name: `templateMessage`,
  props: {

  },
  components: {

  },
  data () {
    return {

    }
  },
  computed: {

  },
  created () {
    this.fetchData()
  },
  mounted () {

  },
  watch: {

  },
  methods: {
    // 模板消息查询数据初始化
    async fetchData () {
      const response = await templateQueryApi()
      console.log(response)
    }
  }
}
</script>

<style scoped lang="scss">
.templateMessage {
  padding: 10px;
}
</style>
