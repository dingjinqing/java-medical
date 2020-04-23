<template>
  <el-container class="container">
    <div class="version-upgrade-page">
      <div class="top">
        <el-button
          type="primary"
          size="small"
        >{{$t('versionUpgrade.applyUp')}}</el-button>
        <el-button
          type="primary"
          size="small"
        >{{$t('versionUpgrade.applyRenewal')}}</el-button>
        <span class="tips">
          {{$t('versionUpgrade.downView')}} <el-link type="primary"> {{$t('versionUpgrade.menu')}}.pdf</el-link>
        </span>
      </div>
      <div class="content">
        <div class="model">
          <div
            class="version-header"
            :style="'background-image:url(' + $imageHost + '/image/admin/version_basis.png)'"
          >
            <div class="vh-title">
              <span class="vh-title-btn vh-title-btn1">{{$t('versionUpgrade.basic')}}</span>
            </div>
            <div class="vh-con">
              <span class="vh-con-span">￥</span>
              <span class="vh-con-money">3800</span>
              <span class="vh-con-span">/{{$t('versionUpgrade.yearPeriod')}}</span></div>
          </div>
          <div class="version-content">
            <div class="version-wrap">
              <h3 style="margin-top:10px;">{{$t('versionUpgrade.versionDesc')}}</h3>
              <ul>
                <li>{{$t('versionUpgrade.basicDesc1')}}</li>
                <li>{{$t('versionUpgrade.basicDesc2')}}</li>
              </ul>
              <h3 style="margin-top:24px;">{{$t('versionUpgrade.functionDesc')}}</h3>
              <ul>
                <li>{{$t('versionUpgrade.bf1')}}</li>
                <li>{{$t('versionUpgrade.bf2')}}</li>
                <li>{{$t('versionUpgrade.bf3')}}</li>
                <li>{{$t('versionUpgrade.bf4')}}</li>
                <li>{{$t('versionUpgrade.bf5')}}</li>
                <li>{{$t('versionUpgrade.bf6')}}</li>
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
              <span class="vh-title-btn vh-title-btn2">{{$t('versionUpgrade.advanceEdition')}}</span>
            </div>
            <div class="vh-con">
              <span class="vh-con-span">￥</span>
              <span class="vh-con-money">5800</span>
              <span class="vh-con-span">/{{$t('versionUpgrade.yearPeriod')}}</span>
            </div>
          </div>
          <div class="version-content">
            <div class="version-wrap">
              <h3 style="margin-top:10px;">{{$t('versionUpgrade.versionDesc')}}</h3>
              <ul>
                <li>{{$t('versionUpgrade.ad1')}}</li>
              </ul>
              <h3 style="margin-top:24px;">{{$t('versionUpgrade.functionDesc')}}</h3>
              <ul>
                <li class="red">{{$t('versionUpgrade.af1')}}</li>
                <li>{{$t('versionUpgrade.af2')}}</li>
                <li>{{$t('versionUpgrade.af3')}}</li>
                <li>{{$t('versionUpgrade.af4')}}</li>
                <li>{{$t('versionUpgrade.af5')}}</li>
                <li>{{$t('versionUpgrade.af6')}}</li>
                <li>{{$t('versionUpgrade.af7')}}</li>
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
              <span class="vh-title-btn vh-title-btn3">{{$t('versionUpgrade.flagship')}}</span>
            </div>
            <div class="vh-con">
              <span class="vh-con-span">￥</span>
              <span class="vh-con-money">9800</span>
              <span class="vh-con-span">/{{$t('versionUpgrade.yearPeriod')}}</span>
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
              <h3 style="margin-top:10px;">{{$t('versionUpgrade.versionDesc')}}</h3>
              <ul>
                <li>{{$t('versionUpgrade.fd1')}}</li>
                <li>{{$t('versionUpgrade.fd2')}}</li>
              </ul>
              <h3 style="margin-top:24px;">{{$t('versionUpgrade.functionDesc')}}</h3>
              <ul>
                <li class="red">{{$t('versionUpgrade.ff1')}}</li>
                <li>{{$t('versionUpgrade.ff2')}}</li>
                <li>{{$t('versionUpgrade.ff3')}}</li>
                <li>{{$t('versionUpgrade.ff4')}}</li>
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
