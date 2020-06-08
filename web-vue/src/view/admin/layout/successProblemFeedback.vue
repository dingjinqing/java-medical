<template>
  <div class="success_container">
    <div class="successContainerMain">
      <img :src="`${$imageHost}/image/admin/question_success.png`">
      <div class="success_text">感谢您的反馈！我们会努力做得更好！</div>
      <div
        @click="handleToClickBack()"
        class="success_back"
      >返回首页&nbsp;&gt;</div>
    </div>
  </div>
</template>
<script>
export default {
  methods: {
    // 点击返回首页
    handleToClickBack () {
      this.$router.push({
        name: 'shop_view'
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.success_container {
  display: flex;
  justify-content: center;
  background-color: #e6e9f0;
  min-height: 600px;
  .successContainerMain {
    width: 1000px;
    background-color: #fff;
    padding: 10px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    img {
      width: 400px;
      height: 200px;
    }
    .success_text {
      margin-top: 60px;
      font-size: 20px;
      color: #333;
    }
    .success_back {
      margin-top: 18px;
      font-size: 14px;
      color: #5a8bff;
      cursor: pointer;
      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
