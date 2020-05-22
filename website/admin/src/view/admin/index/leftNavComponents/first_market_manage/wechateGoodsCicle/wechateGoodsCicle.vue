<template>
  <div class="WeChatGoodCircle">
    <div class="mainContent">
      <div class="articleTitle">{{$t('wechateGoodsCicle.articleTitle')}}</div>
      <div class="date">2019-05-30</div>

      <!-- 开头致辞部分 -->
      <article class="headContent common">
        <div>{{$t('wechateGoodsCicle.dear')}}</div>
        <div class="content">{{$t('wechateGoodsCicle.generalize')}}</div>
      </article>

      <!-- 什么是好物圈 -->
      <article class="explanateGoodCircle common">
        <div class="title">{{$t('wechateGoodsCicle.explanateGoodCircle.title')}}</div>
        <div class="content">
          <div>{{$t('wechateGoodsCicle.explanateGoodCircle.content1')}}</div>
          <div>{{$t('wechateGoodsCicle.explanateGoodCircle.content2')}}</div>
          <div>{{$t('wechateGoodsCicle.explanateGoodCircle.content3')}}</div>
        </div>
        <div class="explanateImg">
          <img
            :src="$imageHost + '/image/admin/wemini_img3s.png'"
            alt=""
          >
        </div>
      </article>

      <!-- 如何开启好物圈 -->
      <article class="openGoodCicle common">
        <div class="title">{{$t('wechateGoodsCicle.openGoodCicle.title')}}</div>
        <div class="content">{{$t('wechateGoodsCicle.openGoodCicle.content1')}}</div>
        <div class="content tips">{{$t('wechateGoodsCicle.openGoodCicle.content2')}}</div>
      </article>
      <div class="explanateImg">
        <img
          :src="$imageHost + '/image/admin/wemini_img3s.png'"
          alt=""
        >
      </div>

      <!-- 使用场景 -->
      <section class="useScenes common">
        <div class="title">{{$t('wechateGoodsCicle.useScenes.title')}}</div>
        <div class="content">
          <div>{{$t('wechateGoodsCicle.useScenes.content1')}}</div>
          <div>{{$t('wechateGoodsCicle.useScenes.content2')}}</div>
          <div>{{$t('wechateGoodsCicle.useScenes.content3')}}</div>
        </div>
        <div class="explanateImg">
          <img
            :src="$imageHost + '/image/admin/wemini_img4s.png'"
            alt=""
          >
        </div>
      </section>

      <!-- 用户使示例 -->
      <section class="useCase common">
        <div class="title">{{$t('wechateGoodsCicle.useCase.title')}}</div>
        <div class="content">
          <div>{{$t('wechateGoodsCicle.useCase.content1')}}</div>
          <div>{{$t('wechateGoodsCicle.useCase.content2')}}</div>
          <div class="remind">{{$t('wechateGoodsCicle.useCase.content3')}}</div>
          <div>{{$t('wechateGoodsCicle.useCase.content4')}}</div>
          <div>{{$t('wechateGoodsCicle.useCase.content5')}}</div>
          <div class="remind">{{$t('wechateGoodsCicle.useCase.content6')}}</div>
        </div>
      </section>

      <!-- 好物推荐 -->
      <section class="recommon common">
        <div class="title">{{$t('wechateGoodsCicle.recommon.title')}}</div>
        <div class="content">{{$t('wechateGoodsCicle.recommon.content1')}}</div>
        <div class="explanateImg">
          <img
            :src="$imageHost + '/image/admin/wemini_img5s.png'"
            alt=""
          >
        </div>
      </section>

    </div>

    <!-- 开通微信好物圈 -->
    <div class="save">
      <el-button
        size="small"
        type="primary"
        @click="wechateGoodsCicle"
      >
        {{$t('wechateGoodsCicle.save')}}
      </el-button>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {

    }
  },

  methods: {
    wechateGoodsCicle () {
      this.$router.push({
        name: 'config_list',
        params: {
          isAuth: true
        }
      })
    }
  }

}
</script>

<style lang="scss" scoped>
.WeChatGoodCircle {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  .mainContent {
    background: #fff;
    padding: 30px 8% 96px;
    text-align: center;
    width: 100%;
    line-height: 24px;
    font-size: 14px;
    color: #333;
    .articleTitle {
      font-size: 25px;
      font-weight: bold;
    }
    .date {
      margin: 20px 0;
      font: 14px Helvetica Neue, Helvetica, PingFang SC, \5fae\8f6f\96c5\9ed1,
        Tahoma, Arial, sans-serif;
      color: #666;
    }
    .common {
      width: 800px;
      margin: 0 auto;
      text-align: left;
      div {
        line-height: 1.8;
      }
      .title {
        margin-top: 25px;
        color: #2e8b57;
        margin-bottom: 5px;
      }
      .content {
        text-indent: 2em;
        .remind {
          color: #ff0000;
          margin: 20px 0;
        }
      }
      .explanateImg {
        margin: 20px 0;
      }
      .tips {
        color: #ff0000;
        margin: 20px 0;
      }
    }
    .headContent {
      :nth-child(1) {
        margin-bottom: 25px;
      }
    }
  }
  .save {
    border-top: 1px solid #f2f2f2;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    bottom: 0;
    z-index: 2;
    right: 20px;
    left: 160px;
    height: 50px;
    background: #f8f8fa;
  }
}
</style>
