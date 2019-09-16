<template>
  <div class="decRightContainer">
    <div class="decRightTop">
      <div>页面设置</div>
      <div
        @click="handleToChangeIcon"
        class="topRight"
      >
        <span>{{topIconFlag?'收起':'展开'}}</span>
        <img :src="topIconFlag?topIconUp:topIconDown">
      </div>
    </div>
    <!--页面设置模块-->
    <div
      v-if="topIconFlag"
      class="pageSetContent"
    >
      <pageSetupMain />
    </div>
    <!--模块配置-->
    <div
      v-if="!topIconFlag&&modulesShow"
      class="pageSetContent"
    >
      模块配置
    </div>
  </div>
</template>
<script>
export default {
  components: {
    pageSetupMain: () => import('./pageSetupModules/pageSetupMain')
  },
  data () {
    return {
      topIconUp: this.$imageHost + '/image/admin/shop_deco/icon_up.png',
      topIconDown: this.$imageHost + '/image/admin/shop_deco/icon_down.png',
      topIconFlag: true,
      modulesShow: false
    }
  },
  methods: {
    // 点击顶部icon
    handleToChangeIcon () {
      this.topIconFlag = !this.topIconFlag
    }
  }
}
</script>
<style lang="scss" scoped>
.decRightContainer {
  .decRightTop {
    background-color: #f8f8f8;
    border: 1px solid #e5e5e5;
    padding: 10px 2%;
    margin-bottom: 10px;
    border-radius: 4px;
    display: flex;
    justify-content: space-between;
    .topRight {
      color: #5a8bff;
      margin-right: 20px;
      display: flex;
      align-items: center;
      cursor: pointer;
      span {
        display: inline-block;
        margin-right: 5px;
      }
    }
  }
  .pageSetContent {
    height: 415px;
    background-color: #f8f8f8;
    border: 1px solid #e5e5e5;
    padding: 10px 2%;
    border-radius: 4px;
    overflow-y: auto;
  }
}
</style>
