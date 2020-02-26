<template>
  <div class="formDecorationHome">
    <div class="main">
      <div class="decLeft">
        <div class="decLeftDrag">
          <div class="title">
            表单元素
          </div>
          <div class="dragContainer">
            <div
              class="dragLi"
              v-for="item in $t('formDecorationHome.formElements')"
              :key="item.id"
              :dataId="item.id"
            >
              <img :src="item.imgUrl">
              <span>{{item.text}}</span>
            </div>
          </div>
        </div>
        <div class="decLeftDrag">
          <div class="title">
            表单元素
          </div>
          <div class="dragContainer">
            <div
              class="dragLi"
              v-for="item in $t('formDecorationHome.graphicCategory')"
              :key="item.id"
              :dataId="item.id"
            >
              <img :src="item.imgUrl">
              <span>{{item.text}}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="decMiddle">
        decMiddle
      </div>
      <div class="decRight">
        decRight
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <div>
        <el-button
          type="primary"
          size="small"
        >保存</el-button>
        <el-button size="small">保存并发布</el-button>
        <span>发布后不可更改</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {

    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  }
}
</script>
<style lang="scss" scoped>
.formDecorationHome {
  padding: 10px;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 53px 20px;
    display: flex;
    justify-content: flex-start;
    .decLeft {
      width: 236px;
      .decLeftDrag {
        background-color: #fff;
        border: 1px solid transparent;
        border-color: #ddd;
        margin-bottom: 5px;
        .title {
          padding: 7px 14px;
          font-size: 14px;
          color: #333;
          background-color: #f5f5f5;
          border-color: #ddd;
        }
        .dragContainer {
          padding: 10px 11px 10px 0;
          display: flex;
          flex-wrap: wrap;
          .dragLi {
            border: 1px solid #eee;
            background: #f8f8f8;
            border-radius: 5px;
            line-height: 24px;
            width: 60px;
            height: 60px;
            cursor: move;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            margin-left: 14px;
            margin-bottom: 10px;
            img {
              width: 34px;
              height: 20px;
            }
          }
        }
      }
    }
    .decMiddle {
      width: 254px;
    }
    .decMiddle {
      width: 350px;
      border: 1px solid #e5e5e5;
      margin-left: 30px;
    }
    .decRight {
      z-index: 100;
      width: 41.4%;
      margin-left: 20px;
    }
  }
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
    span {
      color: #999;
      font-size: 12px;
    }
  }
}
</style>
