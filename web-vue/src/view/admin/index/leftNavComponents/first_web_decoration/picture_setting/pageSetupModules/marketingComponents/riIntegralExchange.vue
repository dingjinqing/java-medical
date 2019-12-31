<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>积分兑换</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="listStyle">
          <div class="title">列表样式：</div>
          <el-radio
            v-model="moduleData.list_styles"
            label="1"
          >单列</el-radio>
          <el-radio
            v-model="moduleData.list_styles"
            label="2"
          >双列</el-radio>
        </div>
        <div class="listStyle">
          <div class="title">选择活动：</div>
          <div class="choose_goods">
            <img
              src="http://mpdevimg2.weipubao.cn/image/admin/icon_jia.png"
              alt=""
            >
            添加活动
          </div>
          <div class="question">
            <img :src="$imageHost+'/image/admin/analysis_tishi.png'">
            <div class="float-layer">
              <div
                class="float-layer-i"
                style="left:81px; top:-11px"
              ></div>
              <div>仅可以选择进行中以及未开始的积分兑换活动，将以活动商品形式展示在小程序前端，每个“积分兑换“组件最多可添加6个积分兑换活动。
                对积分兑换活动中商品进行更换时，当前组件商品将同步更新。</div>
            </div>
          </div>
        </div>
        <div class="listStyle">
          <div class="title">显示内容：</div>
          <el-checkbox v-model="moduleData.show_goods_price">商品员价</el-checkbox>
        </div>
      </div>
      <!--模块私有end-->
    </div>
  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      moduleData: {

      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.moduleData = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    moduleData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
  }
}
</script>
<style lang="scss" scoped>
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式  --------------
    .main {
      margin-top: 20px;
      .listStyle {
        display: flex;
        align-items: center;
        .title {
          height: 40px;
          line-height: 40px;
        }
        .choose_goods {
          width: 120px;
          height: 30px;
          line-height: 30px;
          text-align: center;
          color: #5a8bff;
          border: 1px solid #ccc;
          background: #fff;
          cursor: pointer;
        }
        /deep/ .el-radio {
          display: flex;
          align-items: center;
        }
        .question {
          position: relative;
          margin-left: 10px;
          cursor: pointer;
          .float-layer {
            position: absolute;
            top: 35px;
            left: -85px;
            z-index: 9999;
            width: 250px;
            /* height: 130px; */
            line-height: 30px;
            font-size: 14px;
            padding: 15px;
            border: 1px solid #fff;
            word-wrap: break-word;
            word-break: break-all;
            box-shadow: 0 0 20px rgba(150, 150, 150, 0.3);
            border-radius: 5px;
            background: #fff;
            display: none;
            .float-layer-i {
              position: absolute;
              display: inline-block;
              width: 0;
              height: 0;
              border-width: 12px;
              border-style: dashed;
              border-color: transparent;
              border-top-width: 0;
              border-bottom-color: #fff;
              display: inline-block;
              left: 81px;
              top: -11px;
            }
          }
          &:hover {
            .float-layer {
              display: block;
            }
          }
        }
      }
    }
    //end
  }
}
</style>
