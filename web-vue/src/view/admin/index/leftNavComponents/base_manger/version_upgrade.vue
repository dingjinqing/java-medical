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
          >
            <div class="vh-title">
              <span class="vh-title-btn">基础版</span>
            </div>
            <div class="vh-con">
              <span class="vh-con-span">￥</span>
              <span class="vh-con-money">3800</span>
              <span class="vh-con-span">/1年期</span></div>
          </div>
          <div class="version-content">
            <h3>版本描述</h3>
            <ul>
              <li>适用于大多数企业进行官网展示、门店小程序、 电商商城小程序平台搭建的需要</li>
              <li>享受小程序基础应用功能</li>
            </ul>
            <h3>功能描述</h3>
            <ul>
              <li>电商版小程序全部功能开放，商品限额500个</li>
              <li>支持小程序门店展示、服务预约、门店自提功能</li>
              <li>支持折扣会员卡功能</li>
              <li>大量装修模块免费开放使用，砍价、视频、会员卡模块不支持使用</li>
              <li>支持商品优惠券功能</li>
              <li>小程序概况统计数据一览</li>
            </ul>
          </div>
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
      .vh-title {
        height: 40px;
        margin-top: 15px;
        text-align: center;
      }
      .vh-title-btn {
        display: inline-block;
        width: 90px;
        height: 40px;
        text-align: center;
        line-height: 40px;
        border-radius: 20px;
        color: #fff;
        opacity: 35%;
        background-color: #fbcb46;
        box-shadow: 0 4px 4px rgba(251, 203, 70, 0.35);
      }
      .vh-con {
        height: 50px;
        line-height: 50px;
        margin-top: 2px;
        text-align: center;
        color: #333;
      }
      .vh-con-span {
        font-size: 14px;
      }
      .vh-con-money {
        font-size: 30px;
      }
    }
  }
}
</style>
