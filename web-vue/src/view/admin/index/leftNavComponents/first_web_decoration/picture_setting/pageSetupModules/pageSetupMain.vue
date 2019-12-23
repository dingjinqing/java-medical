<template>
  <div class="pageSetupMain">
    <div class="top">
      <div>
        <span>{{$t('pageSetUp.pageName')}}：</span>
        <el-input
          v-model="ruleForm.page_name"
          :placeholder="$t('pageSetUp.placeInputPageName')"
          size="small"
        ></el-input>
      </div>
      <div>
        <span :class="pageClassification">{{$t('pageSetUp.pageClassification')}}：</span>
        <el-select
          v-model="pageClassify"
          :placeholder="$t('pageSetUp.pleaseChoose')"
          size="small"
        >
          <el-option
            v-for="item in classificationOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
      <div class="radio">
        <span :class="{pageClassification,navigation}">{{$t('pageSetUp.bottomNavigation')}}：</span>
        <el-radio
          v-model="ruleForm.has_bottom"
          label="1"
        >{{$t('pageSetUp.addTo')}}</el-radio>
        <el-radio
          v-model="ruleForm.has_bottom"
          label="0"
        >{{$t('pageSetUp.notAdd')}}</el-radio>
      </div>
      <div
        class="radio"
        style="align-items: flex-start"
      >
        <span :class="moduleSpacing"><i>{{$t('pageSetUp.moduleSpacing')}}：</i></span>
        <div style="display:block">
          <el-radio
            v-model="ruleForm.show_margin"
            label="1"
          >{{$t('pageSetUp.addTo')}}</el-radio>
          <el-radio
            v-model="ruleForm.show_margin"
            label="0"
          >{{$t('pageSetUp.notAdd')}}</el-radio>
          <div
            class="marginHeight"
            v-if="ruleForm.show_margin==='1'"
          >
            <span
              :class="moduleHeight"
              style="white-space:nowrap"
            >{{$t('pageSetUp.moduleHeight')}}：</span>
            <el-input
              size="small"
              v-model="ruleForm.margin_val"
            ></el-input>{{$t('pageSetUp.pixel')}}
          </div>
        </div>

      </div>
      <div class="radio">
        <span>{{$t('pageSetUp.sharePoster')}}：</span>
        <el-radio
          v-model="ruleForm.pictorial.is_add"
          label="1"
        >{{$t('pageSetUp.addTo')}}</el-radio>
        <el-radio
          v-model="ruleForm.pictorial.is_add"
          label="0"
        >{{$t('pageSetUp.notAdd')}}</el-radio>
        <div class="example">
          <span>{{$t('pageSetUp.viewExamples')}}</span>
          <div class="examHidden">
            <img :src="$imageHost+'/image/admin/pic_share2.jpg'">
          </div>
        </div>
      </div>
      <!--点击添加分享海报后出现的模块-->
      <div
        v-if="ruleForm.pictorial.is_add==='1'"
        class="radio"
        :class="pictorialVisibility"
      >
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="sharePosterRuleForm"
          class="ruleForm"
        >
          <el-form-item
            :label="$t('pageSetUp.userScope')"
            prop="pictorial.user_visibility"
          >
            <el-radio
              v-model="ruleForm.pictorial.user_visibility"
              label="1"
            >{{$t('pageSetUp.visibleToAllUsers')}}</el-radio>
            <el-radio
              v-model="ruleForm.pictorial.user_visibility"
              label="0"
            >{{$t('pageSetUp.visibleOnlyToDistributors')}}</el-radio>
          </el-form-item>
          <el-form-item
            :label="$t('pageSetUp.buttonName')"
            prop="pictorial.share_btn_name"
          >
            <div class="btnName">
              <el-input
                size="small"
                v-model="ruleForm.pictorial.share_btn_name"
                maxlength="4"
              ></el-input>
              <span class="popover">
                <el-popover
                  placement="top"
                  width="400"
                  trigger="hover"
                  :offset='0'
                  :content="$t('pageSetUp.sharedLanguage')"
                >
                  <img
                    slot="reference"
                    :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  >
                </el-popover>
              </span>

            </div>
          </el-form-item>
          <el-form-item
            :label="$t('pageSetUp.shareTitle')"
            prop="pictorial.share_desc"
            class="sharedLanguage"
          >
            <div class="shareContainer">
              <div style="display:block;">
                <el-input
                  size="small"
                  v-model="ruleForm.pictorial.share_desc"
                ></el-input>
                <div class="tips">
                  {{$t('pageSetUp.shareTips')}}
                </div>
              </div>

              <div
                class="example"
                style="margin-left:5px"
              >
                <span>{{$t('pageSetUp.viewExamples')}}</span>
                <div class="examHidden">
                  <img :src="$imageHost+'/image/admin/pic_share1.jpg'">
                </div>
              </div>

            </div>

          </el-form-item>
          <el-form-item
            :label="$t('pageSetUp.sharePic')"
            prop="pictorial.share_img_path"
            class="sharedLanguage shareP"
          >
            <div class="bottomDiv">
              <div class="bgBottom">

                <img
                  v-if="!ruleForm.pictorial.share_img_path"
                  :src="$imageHost+'/image/admin/add_img_bg.png'"
                  class="bgImgDiv"
                  @click="handleToAddImg(true)"
                />
                <img
                  v-else
                  style="width:100%;height:40px"
                  :src="ruleForm.pictorial.share_img_path"
                  @click="handleToAddImg(true)"
                >
              </div>
              <span class="sharePic">{{$t('pageSetUp.recommendedDimensions')}}:800px*800px</span>
            </div>

          </el-form-item>
        </el-form>
      </div>
      <!--隐藏模块end-->
    </div>
    <div class="bottom">
      <div>
        <div
          class="bottomLlist"
          :class="bgColorClass"
        >
          <el-radio
            v-model="ruleForm.bg_types"
            label="0"
          >{{$t('pageSetUp.bgColor')}}：</el-radio>
          <span class="colorSelect">
            <el-color-picker
              v-model="ruleForm.page_bg_color"
              show-alpha
              :predefine="predefineColors"
            >
            </el-color-picker>
          </span>
          <div style="margin-left:10px;margin-top:-1px">
            <el-button
              @click="handleToReset()"
              size="small"
            >{{$t('pageSetUp.reset')}}</el-button>
          </div>
        </div>
        <div
          class="bottomLlist"
          :class="bgColorClass"
        >
          <div style="margin-right:5px">
            <el-radio
              v-model="ruleForm.bg_types"
              label="1"
            >{{$t('pageSetUp.bgPic')}}：</el-radio>
          </div>

          <div class="bottomDiv">
            <div class="bgBottom">

              <img
                v-if="!ruleForm.page_bg_image"
                :src="$imageHost+'/image/admin/add_img_bg.png'"
                class="bgImgDiv"
                @click="handleToAddImg(false)"
              />
              <img
                v-else
                style="width:100%;height:40px"
                :src="ruleForm.page_bg_image"
                @click="handleToAddImg(false)"
              >
            </div>
            <el-button
              type="primary"
              size="small"
            >{{$t('pageSetUp.determine')}}</el-button>
          </div>
        </div>
      </div>

    </div>
    <!--图片弹窗-->
    <ImageDalog
      pageIndex='userCardAdd'
      :tuneUp='tuneUp'
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
import { getClassifyData } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting.js'
export default {
  props: {
    pageSet: Object
  },
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog')
  },
  data () {
    return {
      moduleSpacing: 'moduleSpacing', // 英文适配
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
      pageClassification: 'pageClassification', // 页面分类英文适配
      navigation: 'navigation', // 底部导航英文适配
      moduleHeight: 'moduleHeight', // 模块高度
      pictorialVisibility: 'pictorialVisibility', // 海报隐藏模块英文适配
      bgColorClass: 'bgColorClass', // 适配英文类
      tuneUp: false,
      defaultColorright: '#fff',
      cat_id: '',
      classificationOptions: [],
      ruleForm: {
        'is_ok': 1,
        'cat_id': '',
        'page_name': '',
        'bg_types': '0',
        'has_bottom': '0',
        'show_margin': '0',
        'margin_val': '10',
        'page_bg_color': '#ffffff',
        'page_bg_image': '',
        'pictorial': { // 分享海报相关配置
          'is_add': '0', // 是否添加分享海报
          'user_visibility': '1', // 是所有用户可见还是仅分销员可见
          'share_btn_name': '', // 按钮名称
          'share_desc': '', // 分享语
          'share_img_path': '', // 分享图片路径
          'name_length': 0 // 按钮名称长度
        }
      },
      rules: {
        'pictorial.user_visibility': [
          { required: true, message: '请选择用户范围', trigger: 'change' }
        ],
        'pictorial.share_btn_name': [
          { required: true, message: '页面分享按钮名称不能为空', trigger: 'blur' }
        ],
        'pictorial.share_desc': [
          { required: true, message: '页面分享语不能为空', trigger: 'blur' }
        ],
        'pictorial.share_img_path': [
          { required: true, message: '页面分享图不能为空', trigger: 'change' }
        ]
      },
      pageClassify: '请选择页面分类',
      picFlag: null
      // pageData: {
      //   'is_ok': 1,
      //   'page_name': '', // 页面名称
      //   'bg_types': '0', // 背景类型
      //   'has_bottom': '0', // 是否添加底部导航
      //   'page_bg_color': '#ffffff', // 背景颜色
      //   'page_bg_image': '', // 背景图片
      //   'show_margin': '0', // 是否添加模块边距
      //   'margin_val': '10', // 模块边距
      //   'pictorial': { // 分享海报相关配置
      //     'is_add': '0', // 是否添加分享海报
      //     'user_visibility': '0', // 是所有用户可见还是仅分销员可见
      //     'share_btn_name': '', // 按钮名称
      //     'share_desc': '', // 分享语
      //     'share_img_path': '', // 分享图片路径
      //     'name_length': 0 // 按钮名称长度
      //   }
      // }
    }
  },
  watch: {
    pageSet: {
      handler (newData) {
        console.log(newData, this.classificationOptions)
        if (JSON.stringify(newData) !== '{}') {
          getClassifyData().then((res) => {
            console.log(res)
            if (res.error === 0) {
              let obj = {
                id: null,
                name: '请选择页面分类'
              }
              res.content.unshift(obj)
              this.classificationOptions = res.content
              this.classificationOptions.forEach((res) => {
                if (Number(res.id) === Number(newData.cat_id)) {
                  this.pageClassify = res.name
                }
              })
            }
          })

          console.log(this.pageClassify)
          this.ruleForm = newData
        }
      },
      immediate: true,
      deep: true
    },
    ruleForm: {
      handler (newData) {
        console.log(newData)
        newData.pictorial.name_length = newData.pictorial.share_btn_name.length
        this.$emit('hanelToPageSet', newData)
      },
      deep: true
    },
    pageClassify (newData) {
      console.log(newData)
      // this.classificationOptions.forEach((res) => {
      //       if(res.id === newData.cat_id){
      //           this.pageClassify = res.name
      //       }

      //     })
      this.classificationOptions.forEach((res) => {
        if (res.name === newData) {
          this.ruleForm.cat_id = res.id
        }
      })
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 点击重置
    handleToReset () {
      this.ruleForm.page_bg_color = '#fff'
    },
    // 添加图片
    handleToAddImg (flag) {
      this.picFlag = flag
      this.tuneUp = !this.tuneUp
    },
    handleSelectImg ({ imgUrl }) {
      if (this.picFlag) {
        this.ruleForm.pictorial.share_img_path = imgUrl
      } else {
        this.ruleForm.page_bg_image = imgUrl
      }
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
      .pageClassification {
        text-align: center;
      }
      .navigation {
        padding-left: 16px;
      }
      .moduleSpacing {
        i {
          text-align: left !important;
          display: inline-block;
          width: 64px;
        }
      }
      .moduleHeight {
        width: 112px;
      }
      .moduleHeightCn {
        width: 70px;
      }
    }
    .radio {
      margin: 20px 0;
      .marginHeight {
        margin-top: 20px;
        margin-bottom: 0;
        /deep/ .el-input {
          width: 60px;
          margin-right: 5px;
        }
      }
      .ruleForm {
        padding-left: 22px;
        /deep/ .el-form-item__label {
          color: #000;
          padding: 0;
        }

        .btnName {
          /deep/ .el-input {
            width: 80px;
          }
          .popover {
            width: 20px !important;
            span {
              width: 20px !important;
            }
            img {
              margin: -4px 0 0 5px;
            }
          }
          /deep/ .el-form-item__error {
            white-space: nowrap;
          }
          /deep/ .el-form-item__error {
            padding-top: 0;
          }
        }
        /deep/ .is-error {
          margin-bottom: 18px;
          white-space: nowrap;
        }
        .sharedLanguage {
          padding-left: 15px;
          .shareContainer {
            display: flex;
            position: relative;
            .tips {
              height: 14px;
              position: absolute;
              bottom: -10px;
              font-size: 12px;
              color: #666;
            }
          }
          .sharePic {
            white-space: nowrap;
            color: #666;
            margin-left: 10px;
            font-size: 12px;
          }
        }
        .shareP {
          margin-top: 20px;
        }
      }
      /deep/ .example {
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
    // 英语适配css
    .pictorialVisibility {
      .ruleForm {
        // padding-left: 0px;
        .sharedLanguage {
          padding-left: 0;
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
          width: 90px;
          margin-right: 0;
          display: flex;
          align-items: center;
        }

        div {
          display: block;
        }
        /deep/ .colorBtn {
          width: 60px;
          height: 30px;
          border: 1px solid rgb(169, 169, 169);
        }
        /deep/ .m-colorPicker .box {
          top: -210px;
          z-index: 10000;
        }

        .colorSelect {
          margin-left: 5px;
          background-color: #fff;
          border: 1px solid #ccc;
          /deep/ .m-colorPicker {
            display: flex;
            justify-content: center;
            align-items: center;
            /deep/ .colorBtn {
              width: 50px;
              height: 20px;
              border: 1px solid #000;
            }
            .open {
              margin-top: 60px;
              z-index: 10000;
            }
          }
        }
      }
      .bgColorClass {
        /deep/ .el-radio {
          width: auto;
        }
      }
    }
  }
  .bottomDiv {
    .bgBottom {
      width: 70px;
      height: 70px;
      display: flex !important;
      justify-content: center;
      align-items: center;
      border: 1px solid #ccc;
      //   background-position: center;
      .bgImgDiv {
        width: 47px;
        height: 44px;

        cursor: pointer;
      }
    }
    /deep/ .el-button {
      // width: 70px;
      margin-top: 10px;
    }
  }
}
</style>
