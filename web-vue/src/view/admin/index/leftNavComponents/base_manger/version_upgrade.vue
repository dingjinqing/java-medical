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
              <span class="vh-title-btn vh-title-btn1">基础版</span>
            </div>
            <div class="vh-con">
              <span class="vh-con-span">￥</span>
              <span class="vh-con-money">3800</span>
              <span class="vh-con-span">/1年期</span></div>
          </div>
          <div class="version-content">
            <div class="version-wrap">
              <h3 style="margin-top:10px;">版本描述</h3>
              <ul>
                <li>适用于大多数企业进行官网展示、门店小程序、 电商商城小程序平台搭建的需要</li>
                <li>享受小程序基础应用功能</li>
              </ul>
              <h3 style="margin-top:24px;">功能描述</h3>
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
        </div>
        <div class="model">
          <div
            class="version-header"
            :style="'background-image:url(' + $imageHost + '/image/admin/version_high.png)'"
          >
            <div class="vh-title">
              <span class="vh-title-btn vh-title-btn2">高级版</span>
            </div>
            <div class="vh-con">
              <span class="vh-con-span">￥</span>
              <span class="vh-con-money">5800</span>
              <span class="vh-con-span">/1年期</span>
            </div>
          </div>
          <div class="version-content">
            <div class="version-wrap">
              <h3 style="margin-top:10px;">版本描述</h3>
              <ul>
                <li>适用于官网展示、电商商城、预约到店、 营销互动、多门店等小程序平台搭建需要</li>
              </ul>
              <h3 style="margin-top:24px;">功能描述</h3>
              <ul>
                <li class="red">包含基础版所有功能</li>
                <li>商品数量无限量</li>
                <li>支持门店买单和技术预约</li>
                <li>支持会员标签，充值和限次会员卡</li>
                <li>支持会员卡、视频装修模块</li>
                <li>支持拼团、限时降价等近30种营销玩法</li>
                <li>用户画像、访问分析、来源分析均可实时查询</li>
              </ul>
            </div>
          </div>
        </div>
        <div class="model">
          <div
            class="version-header"
            :style="'background-image:url(' + $imageHost + '/image/admin/version_flagship.png)'"
          >
            <div class="vh-title">
              <span class="vh-title-btn vh-title-btn3">旗舰版</span>
            </div>
            <div class="vh-con">
              <span class="vh-con-span">￥</span>
              <span class="vh-con-money">9800</span>
              <span class="vh-con-span">/1年期</span>
            </div>
            <span class="recommend-span">
              <img
                :src="$imageHost+ '/image/admin/version_recommend.png'"
                alt=""
              >
            </span>
          </div>
          <div class="version-content">
            <div class="version-wrap">
              <h3 style="margin-top:10px;">版本描述</h3>
              <ul>
                <li>全功能版本，提供超大容量数据存储空间</li>
                <li>一对一专家咨询服务</li>
              </ul>
              <h3 style="margin-top:24px;">功能描述</h3>
              <ul>
                <li class="red">包含高级版全部功能</li>
                <li>全量装修模块</li>
                <li>海量营销功能</li>
                <li>更全面数据查询功能</li>
              </ul>
            </div>
          </div>
        </div>
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
  font: 14px Helvetica Neue, Helvetica, PingFang SC, \5fae\8f6f\96c5\9ed1,
    Tahoma, Arial, sans-serif;
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
    margin-bottom: 85px;
    .model {
      width: 30%;
      min-height: 550px;
      box-shadow: 0 4px 4px rgba(204, 204, 204, 0.35);
      .version-header {
        position: relative;
        width: 100%;
        height: 120px;
        background-size: 100% 100%;
        background-repeat: no-repeat;
        .recommend-span {
          display: block;
          position: absolute;
          top: -10px;
          left: -6px;
        }
      }
      .vh-title {
        height: 55px;
        padding-top: 15px;
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
        font-size: 14px;
      }
      .vh-title-btn1 {
        background-color: #fbcb46;
        box-shadow: 0 4px 4px rgba(251, 203, 70, 0.35);
      }
      .vh-title-btn2 {
        background-color: #edb855;
        box-shadow: 0 4px 4px rgba(237, 184, 85, 0.35);
      }
      .vh-title-btn3 {
        background-color: #e4a640;
        box-shadow: 0 4px 4px rgba(228, 166, 64, 0.35);
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
      .version-content {
        color: #333;
        font-size: 14px;
        min-height: 500px;
        .version-wrap {
          width: 90%;
          margin: 0 auto;
        }
        h3 {
          font-weight: 700;
        }
        li {
          margin-top: 10px;
          list-style: disc;
          line-height: 25px;
          margin-left: 15px;
          color: #666;
          margin-bottom: -6px;
        }
        .red {
          color: #ff4343;
          font-weight: 600;
        }
      }
    }
  }
}
</style>
