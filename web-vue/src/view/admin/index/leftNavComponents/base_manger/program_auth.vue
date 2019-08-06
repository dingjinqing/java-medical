<template>
<div class="program-auth">
  <Card title="绑定已有小程序">
    <div class="item1">
      <div class="card-text">我已经拥有小程序</div>
      <div class="card-text">
        小程序管理员可将小程序一键授权给我们
      </div>
    </div>
    <a class="link" href="https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=wx3d913e30d1e52861&pre_auth_code=preauthcode@@@tdBZcVJBcp_7ctqn1Sv1SgS6LBm7dunLXHMBmm-7F0wwpZdBs8OQ3duF7o2cZr7O&redirect_uri=http%3A%2F%2Fjmpdev.weipubao.cn%2F%2Fwechat%2Fproxy%2Fauthorization%2Fcallback%3Fshop_id%3D245547&auth_type=2&biz_appid=wx3a6cbd7a7735b683"
       target="_blank">
      我已有小程序，一键授权
    </a>
  </Card>
  <Card title="注册小程序">
    <div class="item2">
      <div class="card-text">我还没有小程序</div>
      <div class="card-text">
        去微信平台申请小程序
      </div>
      <div class="card-text">
        <a href="https://developers.weixin.qq.com/miniprogram/introduction/"
           target="_blank"
           class="help-link">
          查看教程
        </a>
      </div>
    </div>
    <a class="link"
       href="https://mp.weixin.qq.com/"
       target="_blank"
    >
      去微信平台申请小程序，立即申请
    </a>
  </Card>
</div>
</template>

<script>
import Card from '@/components/admin/card'
export default {
  name: 'program_auth',
  components: {
    Card
  }
}
</script>

<style scoped lang="scss">
  .program-auth {
    padding: 15px;
    background: #e0e3ec;
    min-height: 100vh;

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
      &:link,&:visited{
        color: #5a8bff;
        text-decoration: none;
      }

      &:hover,&:active {
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
