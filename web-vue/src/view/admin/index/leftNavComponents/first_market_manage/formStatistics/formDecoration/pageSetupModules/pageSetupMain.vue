<template>
  <div class="pageSetupMain">

  </div>
</template>
<script>
export default {
  props: {
    pageSet: Object
  },
  data () {
    return {
      ruleForm: {

      }
    }
  },
  watch: {
    lang () {
    },
    pageSet: {
      handler (newData) {
        console.log(newData)
        // 中部传递过来的初始表单配置数据初始回显
      },
      immediate: true,
      deep: true
    },
    ruleForm: {
      handler (newData) {
        console.log(newData)
        this.$emit('hanelToPageSet', newData)
      },
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {

  }
}
</script>
<style lang="scss" scoped>
</style>
