<template>
  <div class="serviceAuth">
    <div class="title">
      <span>{{$t('serviceAuth.title1')}}/</span>
      <span>{{$t('serviceAuth.title2')}}</span>
    </div>
    <div class="main-container">
      <div class="wx_auth_success clearfix">
        <div class="auth_info">
          <el-row :gutter="gutterW">
            <el-col :span="span1">
              <div class="grid-content bg-purpleL">
                <span>{{$t('serviceAuth.alias')}}：</span>
              </div>
            </el-col>
            <el-col :span="span2">
              <div class="grid-content bg-purpleR">
                <span>{{data.appId}}</span>
              </div>
            </el-col>

            <el-col :span="span1">
              <div class="grid-content bg-purpleL">
                <span>{{$t('serviceAuth.qurl')}}：</span>
              </div>
            </el-col>
            <el-col :span="span2">
              <div class="grid-content bg-purpleR">
                <img
                  style="width:100px;margin-left:25px"
                  :src="data.qrcodeUrl"
                >
              </div>
            </el-col>

            <el-col :span="span1">
              <div class="grid-content bg-purpleL">
                <span>{{$t('serviceAuth.principalName')}}：</span>
              </div>
            </el-col>
            <el-col :span="span2">
              <div class="grid-content bg-purpleR">
                <span>{{data.principalName }}</span>
              </div>
            </el-col>

            <el-col :span="span1">
              <div class="grid-content bg-purpleL">
                <span>{{$t('serviceAuth.type')}}：</span>
              </div>
            </el-col>
            <el-col :span="span2">
              <div class="grid-content bg-purpleR tipsDiv">
                <span>
                  {{data.accountTypeTrans}}
                </span><a
                  class="link"
                  :href="hrefMpData"
                  target="_blank"
                >{{$t('serviceAuth.newButton')}}</a><img :src="iconUrl">
                <div class="tipsHidden">
                  <div class="tipsTop">
                    {{$t('serviceAuth.tipsTop4')}}
                  </div>
                  <div>
                    <p>{{$t('serviceAuth.tipsTop5')}}：</p>
                    {{$t('serviceAuth.tipsTop6')}}
                  </div>
                </div>
              </div>

            </el-col>

            <el-col :span="span1">
              <div class="grid-content bg-purpleL">
                <span>{{$t('serviceAuth.authStats')}}：</span>
              </div>
            </el-col>
            <el-col :span="span2">
              <div class="grid-content bg-purple">
                <span> {{data.isAuthOkTrans}}</span>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { oneListRequest, authorizationRequest } from '@/api/admin/serviceAuth'
export default {
  name: 'serviceAuthListDetail',
  data () {
    return {
      iconUrl: this.$imageHost + '/image/admin/system_icon.png',
      data: {},
      hrefMpData: null,
      gutterW: 24,
      span1: 3,
      span2: 21
    }
  },
  watch: {
    lang () {
      this.defaluteData()
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.defaluteData()
  },
  methods: {
    defaluteData () {
      authorizationRequest().then((res) => {
        if (res.error === 0) {
          this.hrefMpData = res.content
        }
      })

      let query = this.$route.query.appId_Detail
      let params = {
        'appId': query
      }
      oneListRequest(params).then((res) => {
        if (res.error === 0) {
          this.data = res.content
          this.chageMessage()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    chageMessage () {
      switch (this.data.accountType) {
        case 0:
          this.data.accountTypeTrans = this.$t('serviceAuth.Wechat0')
          break
        case 1:
          this.data.accountTypeTrans = this.$t('serviceAuth.Wechat1')
          break
        case 2:
          this.data.accountTypeTrans = this.$t('serviceAuth.Wechat2')
          break
        case 3:
          this.data.accountTypeTrans = this.$t('serviceAuth.Wechat3')
          break
      }
      switch (this.data.isAuthOk) {
        case 0:
          this.data.isAuthOkTrans = this.$t('serviceAuth.haveCancle')
          break
        case 1:
          this.data.isAuthOkTrans = this.$t('serviceAuth.haveAuth')
          break
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.serviceAuth {
  width: 100%;
  height: 100%;
  background-color: #fff;
  padding-top: 85px;
}
.prompt_mes {
  margin-top: -4px;
  cursor: pointer;
}
.detail_mes {
  width: 280px;
  padding: 10px;
  border: 1px solid #ccc;
  font-size: 12px;
  position: absolute;
  margin: -80px 0 0 300px;
  display: none;
}
.detail_mes_top {
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}
.detail_mes p {
  font-weight: bold;
  text-align: center;
}
ul li {
  float: none;
}
.again_sq {
  color: #5a8bff;
  text-decoration: none;
}
.again_sq {
  color: #5a8bff;
  text-decoration: underline;
}
.again_sq {
  color: #5a8bff;
}
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.grid-content {
  border-radius: 4px;
  min-height: 48px;
}
.bg-purple-dark {
  text-align: left;
}
.title {
  width: 100%;
  height: 55px;
  line-height: 55px;
  padding-left: 25px;
  font-size: 16px;
  color: #333;
  background: #fff;
  padding-right: 25px;
}
.bg-purpleL {
  text-align: right;
}
.bg-purpleR {
  text-align: left;
}
.main-container {
  padding: 10px;
  color: #333;
  box-sizing: border-box;
  border: 12px solid #e6e9f0;
}
.wx_auth_success {
  background: #fff;
  margin: 0;
  padding: 0px;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  line-height: 24px;
}
.tipsDiv {
  position: relative;
  cursor: pointer;
  &:hover .tipsHidden {
    display: block;
  }
  img {
    margin-left: 5px;
    position: relative;
    top: 2px;
  }
  .tipsHidden {
    width: 280px;
    padding: 10px;
    border: 1px solid #ccc;
    font-size: 12px;
    position: absolute;
    margin-top: -80px;
    margin-left: 400px;
    display: none;
    .tipsTop {
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    p {
      font-weight: bold;
      text-align: center;
    }
    .tipsBottom {
      display: flex;
      justify-content: center;
    }
  }
}
.auth_info {
  padding: 20px 25px;
  color: #333;
  min-height: 510px;
  box-sizing: border-box;
}
</style>
<style>
</style>
