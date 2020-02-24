<template>
  <div class="formStatisticsHome">
    <div class="formStatisticsTop">
      <el-button
        size="small"
        type="primary"
        @click="handleToAddForm()"
      >添加表单</el-button>
      <div class="tipsDiv">当前版本为旗舰版,不限制表单个数<img :src="this.$imageHost + '/image/admin/system_icon.png'">
        <div class="tipsHidden">
          <div class="tipsTop">
            <p>
              {{$t('pictureSetting.hiddenTips')}}
            </p>
          </div>
          <div class="tipsBottom">
            <el-button
              type="primary"
              size="small"
            >了解更多</el-button>
          </div>
        </div>
      </div>
    </div>
    <div class="formStatisticsBottom">

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
    // 添加表单点击
    handleToAddForm () {
      this.$router.push({
        name: 'formDecorationHome'
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.formStatisticsHome {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .formStatisticsTop {
    display: flex;
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 30px;
    .tipsDiv {
      font-size: 12px;
      position: relative;
      cursor: pointer;
      height: 30px;
      line-height: 30px;
      color: #999;
      padding: 0 12px;
      &:hover .tipsHidden {
        display: block;
      }
      img {
        margin-left: 5px;
        position: relative;
        top: 2px;
      }
      .tipsHidden {
        display: none;
        position: absolute;
        top: 32px;
        left: 0;
        width: 300px;
        padding: 10px;
        background: #fff;
        box-shadow: 0px 0px 10px #f0f0f0;
        z-index: 100;
        .tipsTop {
          line-height: 30px;
          margin-bottom: 35px;
          p {
            color: #666;
            font-size: 12px;
            border-bottom: 1px solid #efedee;
          }
        }
        .tipsBottom {
          display: flex;
          justify-content: center;
        }
      }
    }
  }
  .formStatisticsBottom {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
}
</style>
