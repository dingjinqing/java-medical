<template>
  <div class="program-auth">
    <Card title="绑定已有小程序">
      <div class="item1">
        <div class="card-text">{{$t('ShopConfiguration.UnauthorizedPages.title')}}</div>
        <div class="card-text">
          {{$t('ShopConfiguration.UnauthorizedPages.authToUs')}}
        </div>
      </div>
      <a
        class="link"
        :href="hrefDataOne"
        target="_blank"
      >
        {{$t('ShopConfiguration.UnauthorizedPages.OneAuthorization')}}
      </a>
    </Card>
    <Card title="注册小程序">
      <div class="item2">
        <div class="card-text">{{$t('ShopConfiguration.UnauthorizedPages.noSmallProcedures')}}</div>
        <div class="card-text">
          {{$t('ShopConfiguration.UnauthorizedPages.toApplyPro')}}
        </div>
        <div class="card-text">
          <a
            href="https://developers.weixin.qq.com/miniprogram/introduction/"
            target="_blank"
            class="help-link"
          >
            {{$t('ShopConfiguration.UnauthorizedPages.toCheckMes')}}
          </a>
        </div>
      </div>
      <a
        class="link"
        href="https://mp.weixin.qq.com/"
        target="_blank"
      >
        {{$t('ShopConfiguration.UnauthorizedPages.toApply')}}
      </a>
    </Card>

  </div>

</template>

<script>
import { grantAuthorizationRequest } from '@/api/admin/basicConfiguration/shopConfig'
import Card from '@/components/admin/card'
export default {
  name: 'program_auth',
  components: {
    Card
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

  .item1 {
    padding: 52px 0;
  }

  .item2 {
    padding: 40px 0;
  }

  .help-link {
    text-align: center;
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
    height: 30px;
    line-height: 30px;
    font-size: 12px;
    border: 1px dashed #5a8bff;
    text-align: center;
    color: #5a8bff;
    text-decoration: none;
  }
}
</style>
