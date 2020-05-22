<template>
  <div class="breadcrumb-container">
    <el-breadcrumb>
      <el-breadcrumb-item class="item-first">系统管理后台</el-breadcrumb-item>
      <el-breadcrumb-item class="item-child">{{calenderFlagEdit?'编辑日历':$store.state.breadcrumb.breadcrumbTitle}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  name: 'breadcrumb',
  data () {
    return {
      calenderFlagEdit: false
    }
  },
  watch: {
    '$route' (newData) {
      console.log(newData)
      // 处理营销日历
      if (newData.name === 'addEvent') {
        if (!newData.query.isAdd) {
          this.calenderFlagEdit = true
        } else {
          this.calenderFlagEdit = false
        }
      } else {
        this.calenderFlagEdit = false
      }
    }
  }
}
</script>

<style scoped lang="scss">
.breadcrumb-container {
  padding: 8px 15px;
  background: #f2f2f2;

  .item-first {
    /deep/ .el-breadcrumb__inner {
      // deep 更改组件原有样式，低版本vue兼容，高版本升级请去除
      color: #333;
    }
  }
  .item-child {
    /deep/ .el-breadcrumb__inner {
      color: #999;
    }
  }
}
</style>
