<template >
  <div class="addingGoodsFooter">
    <!-- 保存后返回列表 -->
    <el-button
      class="btn"
      type="primary"
      size="small"
      @click.native.prevent="handleSave"
    >保存后返回列表</el-button>
    <!-- 上一步 -->
    <el-button
      v-show="active===2"
      class="btn"
      size="small"
      @click.native.prevent="handlePre"
    >上一步</el-button>
    <el-button
      v-show="active===3"
      class="btn"
      size="small"
      @click.native.prevent="handlePre1"
    >上一步</el-button>
    <!-- 下一步 -->
    <el-button
      v-show="active===1"
      size="small"
      class="btn"
      @click.native.prevent="handleNext"
    >下一步</el-button>
    <el-button
      v-show="active===2"
      size="small"
      class="btn"
      @click.native.prevent="handleNext1"
    >下一步</el-button>
    <!-- 继续添加 -->
    <el-button
      v-show="active===3||active===2"
      type="primary"
      size="small"
      class="btn"
      @click.native.prevent="handleKeepAdding"
    >保存后继续添加</el-button>
    <!-- 预览 -->
    <el-button
      v-show="active===3||active===2"
      type="primary"
      size="small"
      class="btn"
      @click.native.prevent="handlePreview"
    >保存后预览商品</el-button>

  </div>
</template>
<script>
export default {
  name: 'addingGoodsFooter',
  data () {
    return {
      active: 1
    }
  },
  // 钩子
  created () {
    this.receiveEventBus()
  },
  methods: {
    // 保存后返回列表
    handleSave () {

    },
    // 上一步
    handlePre () {
      this.active = 1
      this.$http.$emit('first', this.active)
    },
    handlePre1 () {
      this.active = 2
      this.$http.$emit('second', this.active)
    },
    // 下一步
    handleNext () {
      this.active = 2
      this.$http.$emit('second', this.active)
    },
    handleNext1 () {
      this.active = 3
      this.$http.$emit('third', this.active)
    },
    // 保存后继续添加
    handleKeepAdding () {

    },
    // 预览商品
    handlePreview () {

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
.addingGoodsFooter {
  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
.btn {
  margin: 0 10px;
}
</style>
