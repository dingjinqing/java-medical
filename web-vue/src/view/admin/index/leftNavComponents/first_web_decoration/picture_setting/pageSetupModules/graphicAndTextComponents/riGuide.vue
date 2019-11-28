<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <!--模块私有区域-->

      <!--模块私有end-->
    </div>
  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      value1: ''
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.data = this.modulesData
      },
      immediate: true
    },
    value1 (newData) {
      this.$nextTick(() => {
        console.log(this.$refs['time'].$el.children[0].value.split(' ')[2])
      })
    }
    // 监听数据变换
  },
  methods: {
    handleToGet () {
      console.log(this.$refs['time'].$el.children[0].value.split(' ')[2])
    }
  }
}
</script>
