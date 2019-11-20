<template>
  <div class="program-auth">
    <el-card class="box-card">
      <div
        slot="header"
        class="clearfix"
      >
        <span>{{$t('ShopConfiguration.UnauthorizedPages.topTitle')}}</span>
      </div>
      <div class="auth_list_content">
        <div class="content_text">
          <p>{{$t('ShopConfiguration.UnauthorizedPages.title')}}</p>
          <p>
            {{$t('ShopConfiguration.UnauthorizedPages.authToUs')}}
          </p>
        </div>
      </div>
      <div class="auth_footer">
        <a
          class="link"
          :href="hrefDataOne"
          target="_blank"
        >
          {{$t('ShopConfiguration.UnauthorizedPages.OneAuthorization')}}
        </a>
      </div>
    </el-card>

    <el-card class="box-card">
      <div
        slot="header"
        class="clearfix"
      >
        <span>{{$t('ShopConfiguration.UnauthorizedPages.topTitle2')}}</span>
      </div>
      <div class="auth_list_content">
        <div class="content_text">
          <p>{{$t('ShopConfiguration.UnauthorizedPages.noSmallProcedures')}}</p>
          <p>
            {{$t('ShopConfiguration.UnauthorizedPages.toApplyPro')}}
          </p>

          <a
            href="https://developers.weixin.qq.com/miniprogram/introduction/"
            target="_blank"
            class="help-link"
          >
            {{$t('ShopConfiguration.UnauthorizedPages.toCheckMes')}}
          </a>
        </div>
      </div>
      <div class="auth_footer">
        <a
          class="link"
          href="https://mp.weixin.qq.com/"
          target="_blank"
        >
          {{$t('ShopConfiguration.UnauthorizedPages.toApply')}}
        </a>
      </div>
    </el-card>

  </div>

</template>

<script>
import { grantAuthorizationRequest } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  name: 'program_auth',
  components: {
  },
  data () {
    return {
      hrefDataOne: null

    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    grantAuthorizationRequest().then((res) => {
      if (res.error === 0) {
        console.log(res.content)
        this.hrefDataOne = res.content
      }
    })
  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.program-auth {
  .card-text {
    margin-bottom: 10px;
    text-align: center;
    font-size: 12px;
    color: #666;

    &:last-child {
      margin-bottom: 0;
    }
  }

  .help-link {
    text-align: center;
    height: 30px;
    font-size: 12px;
    width: 100%;
    &:link,
    &:visited {
      color: #5a8bff;
      text-decoration: none;
    }

    &:hover,
    &:active {
      text-decoration: underline;
    }
  }

  .link {
    display: block;
    // height: 30px;
    line-height: 30px;
    font-size: 12px;
    border: 1px dashed #5a8bff;
    text-align: center;
    color: #5a8bff;
    text-decoration: none;
  }
  .content_text {
    margin: auto 0;
  }
  .content_text p:first-child {
    margin-bottom: 6px;
  }
  .content_text a {
    display: inline-block;
    margin-top: 10px;
    color: #5a8bff;
  }

  .box-card {
    width: 310px;
    height: 260px;
    float: left;
    margin-right: 10px;
  }
  .clearfix {
    text-align: center;
  }
  .auth_footer {
    padding: 0 20px;
  }
  .auth_list_content {
    padding: 0 20px;
    font-size: 12px;
    color: #666;
    height: 120px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .box-card /deep/ .el-card__header {
    background-color: #fafafa;
  }
}
</style>
