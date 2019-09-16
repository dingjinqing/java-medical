<template>
  <div class="pageSetupMain">
    <div class="top">
      <div>
        <span>页面名称：</span>
        <el-input
          v-model="pageName"
          placeholder="请输入页面名称"
          size="small"
        ></el-input>
      </div>
      <div>
        <span>页面分类：</span>
        <el-select
          v-model="classificationValue"
          placeholder="请选择"
          size="small"
        >
          <el-option
            v-for="item in classificationOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="radio">
        <span>底部导航：</span>
        <el-radio
          v-model="navigationRadio"
          label="1"
        >添加</el-radio>
        <el-radio
          v-model="navigationRadio"
          label="2"
        >不添加</el-radio>
      </div>
      <div class="radio">
        <span>模块间距：</span>
        <el-radio
          v-model="spacingRadio"
          label="1"
        >添加</el-radio>
        <el-radio
          v-model="spacingRadio"
          label="2"
        >不添加</el-radio>
      </div>
      <div class="radio">
        <span>分享海报：</span>
        <el-radio
          v-model="posterRadio"
          label="1"
        >添加</el-radio>
        <el-radio
          v-model="posterRadio"
          label="2"
        >不添加</el-radio>
        <div class="example">
          <span>查看示例</span>
          <div class="examHidden">
            <img :src="$imageHost+'/image/admin/pic_share2.jpg'">
          </div>
        </div>
      </div>
    </div>
    <div class="bottom">
      <div>
        <div
          class="bottomLlist"
          @click="handleToSelect()"
        >
          <el-radio
            v-model="backgroundRadio"
            label="1"
          >背景颜色：</el-radio>
          <div :class="colorSelectFlag?'colorSelect':''">
            <colorPicker
              v-model="colorRight"
              :defaultColor="defaultColorright"
              v-on:change="headleChangeColorRight"
            />
          </div>
        </div>
        <div class="bottomLlist">
          <el-radio
            v-model="backgroundRadio"
            label="2"
          >背景图片：</el-radio>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  data () {
    return {
      defaultColorright: '#fff',
      colorRight: '',
      pageName: '',
      classificationOptions: [{
        value: null,
        label: '请选择页面分类'
      }, {
        value: '0',
        label: '测试页面1'
      }, {
        value: '1',
        label: '测试页面2'
      }, {
        value: '2',
        label: '测试页面3'
      }],
      classificationValue: null,
      navigationRadio: '2',
      spacingRadio: '2',
      posterRadio: '2',
      backgroundRadio: '1',
      colorSelectFlag: false
    }
  },
  methods: {
    // 自定义颜色改Right
    headleChangeColorRight () {
      console.log('颜色选择')
    },
    // 颜色点击
    handleToSelect () {
      this.colorSelectFlag = true
    }
  }
}
</script>
<style lang="scss" scoped>
.pageSetupMain {
  .top {
    div {
      display: flex;
      align-items: center;
      margin-bottom: 5px;
      span {
        display: inline-block;
        width: 100px;
        text-align: right;
      }
      /deep/ .el-input {
        width: 195px;
      }
      /deep/ .el-radio {
        margin-right: 10px;
      }
    }
    .radio {
      margin: 20px 0;
      .example {
        position: relative;
        z-index: 1;
        span {
          text-align: left;
          cursor: pointer;
          color: #5a8bff;
        }

        .examHidden {
          display: none;
          position: absolute;
          left: 70px;
          top: -115px;
          padding: 20px;
          background-color: #fff;
          border-radius: 5px;

          img {
            width: 200px;
            height: 355.74px;
            border: 1px solid #eee;
          }
          &::before {
            content: "";
            position: absolute;
            top: 118px !important;
            left: -7px;
            width: 14px;
            height: 14px;
            background-color: #fff;
            transform: rotate(-45deg);
            z-index: 4;
            box-shadow: -3px -3px 3px #e5e5e5;
          }
        }
        &:hover {
          .examHidden {
            display: block !important;
          }
        }
      }
    }
  }
  .bottom {
    padding-left: 10px;
    div {
      display: flex;
      flex-direction: column;
      .bottomLlist {
        display: flex;
        flex-direction: row;
        margin-bottom: 20px;
        /deep/ .el-radio {
          width: 100px;
          margin-right: 10px;
        }
        div {
          display: block;
        }
        /deep/ .m-colorPicker .box {
          display: none;
        }
        .colorSelect /deep/ .m-colorPicker .box {
          display: block !important;
        }
      }
    }
  }
}
</style>
