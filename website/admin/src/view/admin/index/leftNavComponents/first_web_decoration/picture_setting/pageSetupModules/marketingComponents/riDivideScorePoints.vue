<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('divideScorePoints.divisionIntegralModule')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="list">
          <span>{{$t('divideScorePoints.title')}}</span>
          <div class="radioDiv">
            <div
              class="radio"
              style="margin-bottom:10px"
            >
              <el-radio
                v-model="data.pin_title"
                :label="1"
              >{{$t('divideScorePoints.defaultTitle')}}</el-radio>{{$t('divideScorePoints.defaultTitleTip')}}
            </div>
            <div class="radio special">
              <el-radio
                v-model="data.pin_title"
                :label="0"
              >{{$t('divideScorePoints.custom')}}</el-radio>
              <el-input
                :disabled="data.pin_title===1?true:false"
                v-model="data.pin_title_text"
                :maxlength="14"
                size="small"
              ></el-input><i style="color: #999;font-size:12px">{{$t('divideScorePoints.customTip')}}</i>
            </div>
          </div>
        </div>
        <div
          class="list"
          style="margin-top:20px"
        >
          <span>{{$t('divideScorePoints.hiddenContent')}}</span>
          <div>
            <el-checkbox v-model="hide_active">{{$t('divideScorePoints.activityContent')}}</el-checkbox>
            <el-checkbox v-model="hide_time">{{$t('divideScorePoints.termOfValidity')}}</el-checkbox>
          </div>
        </div>
        <div
          class="list"
          style="margin-top:20px"
        >
          <span>{{$t('divideScorePoints.activityMap')}}</span>
          <div>
            <el-radio
              v-model="data.module_bg"
              :label="0"
            >{{$t('divideScorePoints.defaultBaseMap')}}</el-radio>
            <el-radio
              v-model="data.module_bg"
              :label="1"
            >{{$t('divideScorePoints.custom')}}</el-radio>
          </div>
        </div>
        <!--活动底图选择自定义显示的隐藏模块-->
        <div
          class="list"
          style="margin-top:20px"
          v-if="data.module_bg===1"
        >
          <span></span>
          <div
            class="add_bgs"
            @click="handleToAddActPic()"
          >
            <div style="color: #5a8bff;margin: 27px 0 5px 0;"><img :src="$imageHost+'/image/admin/icon_jia.png'">{{$t('divideScorePoints.backgroundImage')}}</div>
            <div style="color: #999;font-size: 12px">{{$t('divideScorePoints.backgroundImageTip')}}</div>
            <img
              v-if="data.module_img"
              :src="data.module_img"
              class="pin_ig"
            >
            <div
              class="change-img2"
              v-if="data.module_img"
            >{{$t('divideScorePoints.replacePictures')}}</div>
          </div>
          <div></div>
        </div>
        <div
          class="list"
          style="margin-top:20px"
        >
          <span style="height:32px;line-height:32px">{{$t('divideScorePoints.fontColor')}}</span>
          <el-color-picker
            v-model="data.font_color"
            show-alpha
            :predefine="predefineColors"
            size="small"
          >
          </el-color-picker>
          <el-button
            style="margin-left:5px"
            size="small"
            @click="handleToReset()"
          >{{$t('divideScorePoints.reset')}}</el-button>
        </div>
        <div class="selectClass">
          <el-form
            :model="ruleForm"
            :rules="rules"
            ref="selectAct"
            label-width="100px"
            class="demo-ruleForm"
          >
            <el-form-item
              :label="$t('divideScorePoints.divisionPointsActivity')"
              prop="act_id"
            >
              <el-select
                size="small"
                v-model="ruleForm.act_id"
                :placeholder="$t('divideScorePoints.pleaseChoose')"
                @visible-change="handlevisibleChange"
              >
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <!--模块私有end-->
    </div>
    <ImageDalog
      pageIndex='pictureSpace'
      :imageSize="[720,330]"
      :tuneUp="tuneUp"
      @handleSelectImg="handleSelectImg"
    />
  </div>
</template>
<script>
import { getDropDownBox } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting.js'
export default {
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog') // 添加图片弹窗
  },
  data () {
    var validatePass = (rule, value, callback) => {
      console.log(value)
      if (value === -1 && !this.isFirstClick) {
        callback(new Error(this.pleaseSelectGroup))
      } else {
        callback()
      }
    }
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
      hide_active: false, // 隐藏活动flag
      hide_time: false, // 隐藏日期flag
      isFirstClick: false, // 是否是初始化打开
      ruleForm: {
        act_id: ''
      },
      rules: {
        act_id: [
          { required: true, validator: validatePass, trigger: 'change' }
        ]
      },
      options: [{
        id: -1,
        name: '请选择'
      }],
      data: {

      },
      tuneUp: false // 添加图片弹窗flag
    }
  },
  computed: {
    pleaseSelectGroup () {
      return this.$t('divideScorePoints.pleaseSelectGroup')
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData)
        if (this.modulesData) {
          // 处理隐藏内容字段
          this.hide_active = this.handleToData(this.modulesData.hide_active)
          this.hide_time = this.handleToData(this.modulesData.hide_time)
          this.data = this.modulesData
          // 初始化下拉框数据
          getDropDownBox().then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.options = [...this.options, ...res.content]
              console.log(this.modulesData, this.options)
              this.options.forEach((item, index) => {
                if (this.modulesData.act_id === item.id) {
                  this.ruleForm.act_id = this.modulesData.act_id
                }
              })
              this.$forceUpdate()
            }
          })
        }
      },
      immediate: true
    },
    // 监听数据变换
    data: { // 模块公共
      handler (newData) {
        newData.hide_active = this.handleToData(this.hide_active)
        newData.hide_time = this.handleToData(this.hide_time)
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    },
    hide_active (newVal) {
      this.handleToSendData()
    },
    hide_time (newVal) {
      this.handleToSendData()
    },
    'ruleForm.act_id' (newVal) {
      this.modulesData.act_id = newVal
    },
    lang () {
      this.options = this.$t('divideScorePoints.options')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.isFirstClick = true
    console.log(this.$refs, this.isFirstClick)
    this.$http.$on('isMpinintegration', () => {
      console.log('触发', this.$refs)
      this.isFirstClick = false
      this.$refs['selectAct'].validate((valid) => {
        console.log(valid)
        if (!valid) {
          return false
        }
      })
    })

    this.langDefault()
  },
  methods: {
    // 处理隐藏内容字段
    handleToData (res) {
      let flag = null
      switch (res) {
        case 1:
          flag = true
          break
        case 0:
          flag = false
          break
        case true:
          flag = 1
          break
        case false:
          flag = 0
          break
      }
      return flag
    },
    // 处理传递左侧的值
    handleToSendData () {
      this.data.hide_active = this.handleToData(this.hide_active)
      this.data.hide_time = this.handleToData(this.hide_time)
      console.log(this.data)
      this.$emit('handleToBackData', this.data)
    },
    // 点击重置
    handleToReset () {
      this.data.font_color = null
    },
    // 下拉框变化
    handlevisibleChange (res) {
      console.log(res)
      this.isFirstClick = false
    },
    // 调用图片弹窗
    handleToAddActPic () {
      this.tuneUp = !this.tuneUp
    },
    // 图片弹窗选中回传
    handleSelectImg (res) {
      console.log(res)
      this.data.module_img = res.imgUrl
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
        .add_bgs {
          width: 300px;
          height: 100px;
          text-align: center;
          background: #fff;
          border: 1px dashed #e5e5e5;
          position: relative;
          cursor: pointer;
          .pin_ig {
            position: absolute;
            top: -2px;
            left: -2px;
            width: 300px;
            height: 100px;
          }
          .change-img2 {
            color: #fff;
            z-index: 3;
            position: absolute;
            bottom: 0px;
            left: -2px;
            width: 101%;
            height: 25px;
            text-align: center;
            line-height: 25px;
            font-size: 12px;
            cursor: pointer;
            background: rgba(0, 0, 0, 0.5);
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
      .selectClass {
        margin-top: 20px;
        /deep/ .el-form-item__label {
          width: auto !important;
        }
      }
    }
    //end
  }
}
</style>
