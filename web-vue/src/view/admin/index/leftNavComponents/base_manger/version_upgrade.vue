<template>
  <el-container class="container">
    <div class="version-upgrade-page">
      <div class="top">
        <el-button
          type="primary"
          size="small"
        >申请升级</el-button>
        <el-button
          type="primary"
          size="small"
        >申请续费</el-button>
        <span class="tips">
          点击下载查看 <el-link type="primary">店+小程序版本功能表.pdf</el-link>
        </span>
      </div>
      <div class="content">
        <div class="model">
          <div
            class="version-header"
            :style="'background-image:url(' + $imageHost + '/image/admin/version_basis.png)'"
          ></div>
          <div class="version-content"></div>
        </div>
        <div class="model"></div>
        <div class="model"></div>
      </div>
    </div>
  </el-container>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>

<style lang="scss" scoped>
.container {
  background: #fff;
}
.version-upgrade-page {
  width: 74%;
  min-width: 998px;
  min-height: 700px;
  margin: 0 auto;
  .top {
    height: 70px;
    display: flex;
    align-items: center;
    border-bottom: 1px solid #eeeeee;
    .tips {
      margin-left: 30px;
      color: #999;
      font-size: 14px;
      .el-link {
        position: relative;
        top: -1px;
        margin-left: 5px;
      }
    }
  }
  .content {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    min-height: 550px;
    .model {
      width: 30%;
      min-height: 550px;
      box-shadow: 0 4px 4px rgba(204, 204, 204, 0.35);
      .version-header {
        width: 100%;
        height: 120px;
        overflow: hidden;
        background-size: 100% 100%;
        background-repeat: no-repeat;
      }
    }
  }
}
</style>
