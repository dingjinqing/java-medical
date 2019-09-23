<template>
  <div class="rightMembershipCard">
    <div class="rightMembershipCardMain">
      会员卡模块
      <el-button @click='handleToSelectCuopon()'>会员模块测试</el-button>
    </div>

  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {

    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
      },
      immediate: true
    }
  },
  methods: {
    // 弹窗确定事件
    handleToSelectCuopon () {
      let obj = {
        name: '帅飞会员卡',
        backgroundColor: '#000'
      }
      this.$emit('handleToBackData', obj)
    }
  }
}
</script>
<style lang="scss" scoped>
.rightMembershipCard {
  .rightMembershipCardMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
  }
}
</style>
