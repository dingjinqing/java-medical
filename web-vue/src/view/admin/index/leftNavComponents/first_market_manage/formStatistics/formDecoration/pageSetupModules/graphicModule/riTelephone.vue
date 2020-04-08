<template>
  <div class="riName">
    <div class="riNameMain">
      <!--模块私有区域-->
      <h2>{{$t('formDecorationModel.phoneNumber')}}</h2>
      <div class="main">
        <div class="text">
          <span>{{$t('formDecorationModel.phoneNumberTitle')}}</span>
          <el-input
            size="small"
            v-model="modulesSaveData.title"
            :maxlength='14'
          ></el-input>
          <div style="margin-left:10px;color:#999">{{$t('textModule.tips')}}</div>
        </div>
        <div class="text">
          <span>{{$t('formDecorationModel.selectaStyle')}}</span>
          <el-radio
            v-model="modulesSaveData.show_type"
            label="0"
          >{{$t('formDecorationModel.ordinary')}}</el-radio>
          <el-radio
            v-model="modulesSaveData.show_type"
            label="1"
          >{{$t('formDecorationModel.suspension')}}</el-radio>
        </div>
        <div class="text">
          <span></span>
          <div
            class="rightContainer"
            v-if="modulesSaveData.show_type==='0'"
          >
            <div class="list">
              <span>{{$t('formDecorationModel.centerOrNot')}}</span>
              <el-radio
                v-model="modulesSaveData.align_type"
                label="1"
              >{{$t('formDecorationModel.centered')}}</el-radio>
              <el-radio
                v-model="modulesSaveData.align_type"
                label="0"
              >{{$t('formDecorationModel.atTheLeft')}}</el-radio>
            </div>
            <div class="list">
              <span>{{$t('textModule.fontColor')}}：</span>
              <el-color-picker
                v-model="modulesSaveData.color"
                show-alpha
                :predefine="predefineColors"
              >
              </el-color-picker>
              <el-button
                size="small"
                @click="handleToReset(0)"
              >{{$t('textModule.reset')}}</el-button>
            </div>
            <div class="list">
              <span>{{$t('textModule.backgroundColor')}}：</span>
              <el-color-picker
                v-model="modulesSaveData.background_color"
                show-alpha
                :predefine="predefineColors"
              >
              </el-color-picker>
              <el-button
                size="small"
                @click="handleToReset(1)"
              >{{$t('textModule.reset')}}</el-button>
            </div>
          </div>
          <div
            class="rightContainer special"
            v-if="modulesSaveData.show_type==='1'"
          >
            <div
              class="li"
              v-for="(item,index) in iconData"
              :key="index"
            >
              <img :src="item.imgUrl">
              <el-radio
                v-model="modulesSaveData.align_type"
                :label="item.id"
              >&nbsp;</el-radio>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      imageTuneUp: false, // 图片选择弹窗调起
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
        '#c7158577'
      ],
      iconData: [
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/p_gray.png`,
          id: '0'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/p_blue.png`,
          id: '1'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/p_deeppink.png`,
          id: '2'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/p_pink.png`,
          id: '3'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/p_green.png`,
          id: '4'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/p_aqua.png`,
          id: '5'
        }
      ],
      modulesSaveData: {

      } // 模块保存数据
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        if (this.modulesData !== -1) {
          this.modulesSaveData = this.modulesData
        }
      },
      immediate: true
    },
    // 监听数据变换
    modulesSaveData: { // 模块公共
      handler (newData) {
        console.log(newData)
        let data = JSON.parse(JSON.stringify(newData))
        this.iconData.forEach((item, index) => {
          if (item.id === data.align_type) {
            data.sps_icon = item.imgUrl
          }
        })
        this.$emit('handleToBackData', data)
      },
      deep: true
    }
  },
  methods: {
    // 点击重置
    handleToReset (index) {
      switch (index) {
        case 0:
          this.modulesSaveData.color = '#333333'
          break
        case 1:
          this.modulesSaveData.background_color = '#ffffff'
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.riName {
  .riNameMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 20px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式  --------------
    .main {
      .text {
        margin: 20px 0;
        display: flex;
        align-items: center;
        /deep/ .el-input {
          width: 150px;
        }
        span {
          display: inline-block;
          width: 80px;
          text-align: right;
        }
        /deep/ .el-button {
          margin-left: 10px;
        }
        .rightContainer {
          border: 1px solid #ddd;
          background: #fff;
          padding: 20px 10px;
          width: 340px;
          .list {
            margin-bottom: 20px;
            display: flex;
            align-items: center;
          }
        }
        .special {
          display: flex;
          flex-wrap: wrap;
          .li {
            width: 48px;
            margin-right: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            /deep/ .el-radio {
              margin: 10px 0 0 14px;
            }
            img {
              width: 40px;
              height: 40px;
            }
          }
        }
      }
    }
    //end
  }
}
</style>
