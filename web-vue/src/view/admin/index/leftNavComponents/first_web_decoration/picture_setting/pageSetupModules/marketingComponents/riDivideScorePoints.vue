<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>组团瓜分积分模块</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="list">
          <span>标题：</span>
          <div class="radioDiv">
            <div
              class="radio"
              style="margin-bottom:10px"
            >
              <el-radio
                v-model="data.pin_title"
                label="1"
              >默认标题：</el-radio>XX积分等你拿,购物可抵现金！
            </div>
            <div class="radio special">
              <el-radio
                v-model="data.pin_title"
                label="0"
              >自定义</el-radio>
              <el-input
                :disabled="data.pin_title==='1'?true:false"
                v-model="data.pin_title_text"
                size="small"
              ></el-input><i style="color: #999;font-size:12px">限制14字，为空不显示</i>
            </div>
          </div>
        </div>
        <div
          class="list"
          style="margin-top:20px"
        >
          <span>隐藏内容：</span>
          <div>
            <el-checkbox v-model="data.hide_active">活动内容</el-checkbox>
            <el-checkbox v-model="data.hide_time">有效期</el-checkbox>
          </div>
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
      predefineColors: [ // 颜色选择器预定义颜色池
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#FF0000'
      ],
      data: {

      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        if (this.modulesData) {
          console.log(typeof this.modulesData.hide_active)
          if (this.modulesData.hide_active === '1') {
            this.modulesData.hide_active = true
            console.log('触发')
          } else {
            this.modulesData.hide_active = false
          }
          if (this.modulesData.hide_time === '1') {
            this.modulesData.hide_time = true
          } else {
            this.modulesData.hide_time = false
          }
          console.log(this.modulesData)
          this.data = this.modulesData
        }
      },
      immediate: true
    },
    // 监听数据变换
    data: { // 模块公共
      handler (newData) {
        console.log(newData)
        if (newData.hide_active) {
          newData.hide_active = '1'
        } else {
          newData.hide_active = '0'
        }
        if (newData.hide_time) {
          newData.hide_time = '1'
        } else {
          newData.hide_time = '0'
        }
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    // 点击重置
    handleToReset (index) {

    }
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
      .list {
        display: flex;
        span {
          display: inline-block;
          width: 72px;
          text-align: right;
          .radioDiv {
            display: flex;
            flex-direction: column;
            .radio {
              display: flex;
            }
          }
        }
      }
      .special {
        /deep/ .el-radio {
          margin-right: 8px;
        }
        /deep/ .el-input {
          width: 136px;
          margin-right: 5px;
        }
      }
    }
    //end
  }
}
</style>
