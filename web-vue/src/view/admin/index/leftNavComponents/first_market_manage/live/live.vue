<template>
  <div class="content">
    <div class="showInfo">
      <div  v-if="auditState!==null&&auditState===0" style="height: 500px;">
      <el-row>
         <el-col :span="7"></el-col>
         <el-col :span="10">
          <div> <img class="imgs" :src="$imageHost +'/image/admin/not_authorize_live.png'" /></div>
          <div>
           <span v-if="!hasLiveFunc" class="spcss">小程序直播暂未授权</span>
           <span v-if="hasLiveFunc" class="spcss">请提交代码审核</span>
          </div>
          </el-col>
         <el-col :span="7"></el-col>
      </el-row>
         <!-- 下面的字-->
      <el-row>
         <el-col :span="4"></el-col>
         <el-col :span="16">
           <span v-if="!hasLiveFunc">请重新授权小程序，并勾选<span class="spcss2">“小程序直播权限”</span>，授权完成后需重新提交代码审核， 待微信审核通过后可使用小程序直播。</span>
           <span v-if="hasLiveFunc">已授权<span class="spcss2">“小程序直播权限”</span>，请确保在<a href="https://mp.weixin.qq.com" target="_blank" style="color: #5A8BFF;">微信公众平台</a>开通直播后重新提交代码审核，待微信审核通过后可使用小程序直播。</span>
           </el-col>
         <el-col :span="4"></el-col>
      </el-row>
      <el-row>
         <el-col :span="5"></el-col>
         <el-col :span="14">
           <el-button type="primary" v-if="!hasLiveFunc" @click="refAuthSubmit">重新授权小程序</el-button>
           <el-button type="primary"  @click="uploadAudit" >提交代码审核</el-button>
           </el-col>
         <el-col :span="5"></el-col>
      </el-row>
      </div>
      <div style="height: 500px;" v-if="(auditState!==null&&auditState===1)||(auditState===null&&isAuthLive)">
      <el-row v-if="auditState!==null&&auditState===1" >
         <el-col :span="7"></el-col>
         <el-col :span="10">
           <div>
              <img class="imgs" :src="$imageHost +'/image/admin/now_authoring_icon.gif'" />
           </div>
            <div>
              <span class="spcss">小程序审核中</span>
           </div>
           </el-col>
         <el-col :span="7"></el-col>
      </el-row>
      <el-row v-if="auditState===null&&isAuthLive">
         <el-col :span="7"></el-col>
         <el-col :span="10">
           <div> <img class="imgs" :src="$imageHost +'/image/admin/not_authorize_live.png'" /></div>
          <div>
           <span class="spcss">请去基础配置-店铺基础配置中申请/绑定小程序</span>
           </div>
              <el-button type="primary"  @click="torouter" style="margin-top: 20px">申请/绑定小程序</el-button>

           </el-col>
         <el-col :span="7"></el-col>
      </el-row>
      </div>
    </div>
    <el-dialog
      :title="$t('ShopConfiguration.SmallProgramAuthorizationPage.prompt')"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <span>{{$t('ShopConfiguration.SmallProgramAuthorizationPage.ReauthorizationInfo')}}</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <a
          class="link"
          :href="hrefDataOne"
          target="_blank"
        >
          <el-button
            @click="centerDialogVisible = false"
            type="primary"
          >{{$t('ShopConfiguration.SmallProgramAuthorizationPage.Reauthorization')}}</el-button>
        </a>
        <el-button @click="centerDialogVisible = false">{{$t('ShopConfiguration.SmallProgramAuthorizationPage.cancel')}}</el-button>
      </span>

    </el-dialog>

      <liveBroadcast v-if="(!isAuthLive)&&(auditState!==0&&auditState!==1)"/>
  </div>
</template>
<script>
import liveBroadcast from './liveBroadcast'
import { getLiveList } from '@/api/admin/marketManage/live'
import { grantAuthorizationRequest, publishSetRequest, queryAuthdritionRequest } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  components: {
    liveBroadcast
  },
  mounted () {
    this.langDefault()
    this.getList()
  },
  data () {
    return {
      isAuthLive: false,
      auditState: null,
      hasLiveFunc: false,
      hrefMpData: null,
      centerDialogVisible: false,
      flag: false,
      data: [],
      hrefDataOne: null
    }
  },
  methods: {
    getList () {
      let params = {
        currentPage: 1,
        pageRows: 1
      }
      getLiveList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.isAuthLive = res.content.isAuthLive
          this.auditState = res.content.auditState
          this.hasLiveFunc = res.content.hasLiveFunc
        } else {
          this.$message.error(res.message)
        }
      })
    },
    refAuthSubmit () {
      this.centerDialogVisible = true
      grantAuthorizationRequest().then((res) => {
        if (res.error === 0) {
          console.log(res.content)
          this.hrefDataOne = res.content
        }
      })
    },
    // 提交代码审核
    uploadAudit () {
      this.getMaApp()
      if (!this.flag) {
        return
      }
      let data2 = {
        appId: this.data.appId,
        templateId: this.data.currentTemplateId
      }
      publishSetRequest(data2).then((res) => {
        if (res.error === 0) {
          console.log(res.content)
          this.$message.success(res.message)
          this.reflushData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    getMaApp () {
      queryAuthdritionRequest().then(res => {
        if (res.error === 170016) {
          this.$message.error('请先授权小程序')
          this.flag = false
        }
        if (res.error === 0) {
          this.data = res.content
          this.flag = true
        }
      })
    },
    torouter () {
      this.$router.push({
        name: 'config_list'
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  // padding: 10px;
  // min-width: 100%;
  // font-size: 14px;
  margin: 0,auto;
  .showInfo{
    margin-top: 15px;
    text-align: center;
    background-color: white;
    //  height: 500px;
  }
  .el-row {
     margin-bottom: 20px
  }
    .el-col {
      min-height: 1px
    }
    .spcss{
      font-size: 32px;
      font-weight: bold;
    }
    .imgs{
       margin-top: 70px;
       margin-bottom: 20px
    }
    .spcss2{
      font-weight: bold;
    }
}
</style>
