<template>
  <div class="notFound">
    <div class="content">
      <img
        style="margin-left:-30px"
        :src="$imageHost+'/image/admin/page_404.png'"
        alt=""
      >
      <div class="tips">
        SORRY!您访问的页面找不到了，请重新加载或者点击下方的常用链接
      </div>
      <div class="link">
        <div @click="handleToClick(0)">system后台</div>
        <div
          style="margin: 0 20px"
          @click="handleToClick(1)"
        >admin后台</div>
        <div @click="$router.push({path:'/'})">官网</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  methods: {
    handleToClick (flag) {
      if (flag) {
        let routeData = this.$router.resolve({
          path: '/admin/login'
        })
        console.log(routeData)
        routeData.href = '/admin/index/login'
        window.open(routeData.href, '_blank')
      } else {
        let routeData1 = this.$router.resolve({
          path: '/system/login'
        })
        console.log(routeData1)
        routeData1.href = '/system/system/login'
        window.open(routeData1.href, '_blank')
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.notFound {
  width: 100%;
  height: 100%;
  background: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  img {
    width: 200px;
    height: auto;
  }
  .tips {
    font-size: 16px;
    color: #999;
    margin-bottom: 20px;
  }
  .link {
    display: flex;
    div {
      cursor: pointer;
      color: #5a8bff;
    }
  }
}
</style>
