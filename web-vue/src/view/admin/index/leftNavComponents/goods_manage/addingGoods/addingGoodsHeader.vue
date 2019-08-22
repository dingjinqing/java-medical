<template>
  <div>
    <!-- 头部组件 -->
    <el-steps
      :active="active"
      finish-status="finish"
      simple
      style="margin-top: 0px"
    >
      <el-step
        @click.native="active=1"
        :title="titles.title1"
        icon="el-icon-edit"
      >
      </el-step>
      <el-step
        @click.native="active=2"
        :title="titles.title2"
        icon="el-icon-edit"
      ></el-step>
      <el-step
        @click.native="active=3"
        :title="titles.title3"
        icon="el-icon-edit"
      ></el-step>
    </el-steps>

  </div>
</template>
<script>
export default {
  name: 'addingGoodsHeader',
  // 数据
  data () {
    return {
      active: 1,
      titles: {
        title1: '编辑商品信息',
        title2: '编辑商品详情',
        title3: '编辑分销信息'
      }
    }
  },
  // 钩子
  created () {
    this.receiveEventBus()
  },
  // 监听
  watch: {
    // 监听头部val的变化
    active (val) {
      switch (val) {
        case 1: this.$http.$emit('first', val)
          break
        case 2: this.$http.$emit('second', val)
          break
        case 3: this.$http.$emit('third', val)
          break
      }
    }
  },
  // 方法
  methods: {
    // 编辑商品信息
    handleFirst () {

    },
    // 编辑商品详情
    handleSecond () {

    },
    // 编辑分销信息
    handleThird () {

    },
    // 监听派发来的事件
    receiveEventBus () {
      this.$http.$on('first', val => { this.active = val })
      this.$http.$on('second', val => { this.active = val })
      this.$http.$on('third', val => { this.active = val })
    }
  }
}
</script>
<style lang="css" scoped>
</style>
