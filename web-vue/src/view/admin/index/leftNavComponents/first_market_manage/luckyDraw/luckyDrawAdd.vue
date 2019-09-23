<template>
  <div class="luckyDrawAdd">
    <el-card>
      <div class="mainContent">
        <div class="mainContentLeft">
          <div class="top"></div>
        </div>
        <div class="mainContentRight"></div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: `luckyDrawAdd`
}
</script>

<style lang="scss" scoped>
.luckyDrawAdd {
  .mainContent {
    .mainContentLeft {
      width: 350px;
      height: 720px;
      border: 1px solid #ccc;
      background: #eee;
      overflow-x: hidden;
      .top {
        height: 55px;
        width: 320px;
        background: url(../../../../../../assets/adminImg/phone_tops.png)
          no-repeat;
      }
    }
  }
}
</style>>
