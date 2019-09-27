<template>
  <div class="pageSetupMain">
    <div class="top">
      <div>
        <span>页面名称：</span>
        <el-input
          v-model="pageData.page_name"
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
          v-model="pageData.has_bottom"
          label="1"
        >添加</el-radio>
        <el-radio
          v-model="pageData.has_bottom"
          label="0"
        >不添加</el-radio>
      </div>
      <div
        class="radio"
        style="align-items: flex-start"
      >
        <span>模块间距：</span>
        <div style="display:block">
          <el-radio
            v-model="pageData.show_margin"
            label="1"
          >添加</el-radio>
          <el-radio
            v-model="pageData.show_margin"
            label="0"
          >不添加</el-radio>
          <div
            class="marginHeight"
            v-if="pageData.show_margin==='1'"
          >
            <span style="width:70px;white-space:nowrap">间距高度：</span>
            <el-input
              size="small"
              v-model="pageData.margin_val"
            ></el-input>像素
          </div>
        </div>

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
      <!--点击添加分享海报后出现的模块-->
      <div
        v-if="posterRadio==='1'"
        class="radio"
      >
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="sharePosterRuleForm"
          class="ruleForm"
        >
          <el-form-item
            label="用户范围："
            prop="rangeRadio"
          >
            <el-radio
              v-model="ruleForm.rangeRadio"
              label="1"
            >所有用户可见</el-radio>
            <el-radio
              v-model="ruleForm.rangeRadio"
              label="2"
            >仅分销员可见</el-radio>
          </el-form-item>
          <el-form-item
            label="按钮名称："
            prop="btnName"
          >
            <div class="btnName">
              <el-input
                size="small"
                v-model="ruleForm.btnName"
              ></el-input>
              <span class="popover">
                <el-popover
                  placement="top"
                  width="400"
                  trigger="hover"
                  :offset='0'
                  content="最多可填写4个汉字，小程序审核通过前不显示此按钮。因微信官方限制，请避免填写带有”分享“等字样的内容。因文案带有【诱导分享】性质导致的审核延期请自行承担。"
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
            label="分享语："
            prop="sharedLanguage"
            class="sharedLanguage"
          >
            <div class="shareContainer">
              <div style="display:block;">
                <el-input
                  size="small"
                  v-model="ruleForm.sharedLanguage"
                ></el-input>
                <div class="tips">
                  最多可填写20个汉字
                </div>
              </div>

              <div
                class="example"
                style="margin-left:5px"
              >
                <span>查看示例</span>
                <div class="examHidden">
                  <img :src="$imageHost+'/image/admin/pic_share1.jpg'">
                </div>
              </div>

            </div>

          </el-form-item>
          <el-form-item
            label="分享图："
            prop="sharedLanguage"
            class="sharedLanguage shareP"
          >
            <div class="bottomDiv">
              <div class="bgBottom">

                <img
                  v-if="!shareImgUrl"
                  :src="$imageHost+'/image/admin/add_img_bg.png'"
                  class="bgImgDiv"
                  @click="handleToAddImg(true)"
                />
                <img
                  v-else
                  style="width:100%;height:40px"
                  :src="shareImgUrl"
                  @click="handleToAddImg(true)"
                >
              </div>
              <span class="sharePic">建议尺寸:800px*800px</span>
            </div>

          </el-form-item>
        </el-form>
      </div>
      <!--隐藏模块end-->
    </div>
    <div class="bottom">
      <div>
        <div class="bottomLlist">
          <el-radio
            v-model="pageData.bg_types"
            label="0"
          >背景颜色：</el-radio>
          <span class="colorSelect">
            <colorPicker
              v-model="colorRight"
              :defaultColor="defaultColorright"
              style="width:60px;height:30px;"
            />
          </span>
          <div style="margin-left:10px;margin-top:-1px">
            <el-button
              @click="handleToReset()"
              size="small"
            >重置</el-button>
          </div>
        </div>
        <div class="bottomLlist">
          <div style="margin-right:5px">
            <el-radio
              v-model="pageData.bg_types"
              label="1"
            >背景图片：</el-radio>
          </div>

          <div class="bottomDiv">
            <div class="bgBottom">

              <img
                v-if="!bgImgUrl"
                :src="$imageHost+'/image/admin/add_img_bg.png'"
                class="bgImgDiv"
                @click="handleToAddImg(false)"
              />
              <img
                v-else
                style="width:100%;height:40px"
                :src="bgImgUrl"
                @click="handleToAddImg(false)"
              >
            </div>
            <el-button
              type="primary"
              size="small"
            >确定</el-button>
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
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  props: {
    pageSet: Object
  },
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog')
  },
  data () {
    return {
      tuneUp: false,
      defaultColorright: '#fff',
      colorRight: '',
      classificationValue: '',
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
      posterRadio: '2',
      ruleForm: {
        rangeRadio: '1',
        btnName: '',
        sharedLanguage: ''
      },
      rules: {
        rangeRadio: [
          { required: true, message: '请选择用户范围', trigger: 'change' }
        ],
        btnName: [
          { required: true, message: '页面分享按钮名称不能为空', trigger: 'blur' }
        ],
        sharedLanguage: [
          { required: true, message: '页面分享语不能为空', trigger: 'blur' }
        ]
      },
      picFlag: null,
      shareImgUrl: '',
      bgImgUrl: '',
      pageData: {
        'is_ok': 1,
        'page_name': '',
        'bg_types': '0',
        'has_bottom': 0,
        'page_bg_color': '#ffffff',
        'page_bg_image': '',
        'show_margin': '1',
        'margin_val': '10',
        'pictorial': {
          'is_add': '0',
          'user_visibility': '0',
          'share_btn_name': '',
          'share_desc': '',
          'share_img_path': '',
          'name_length': 0
        }
      }
    }
  },
  watch: {
    pageSet: {
      handler (newData) {
        console.log(newData)
        this.pageData = newData
      },
      immediate: true,
      deep: true
    },
    pageData: {
      handler (newData) {
        console.log(newData)
        this.$emit('hanelToPageSet', newData)
      },
      deep: true
    }
  },
  methods: {
    // 点击重置
    handleToReset () {
      this.colorRight = '#fff'
    },
    // 添加图片
    handleToAddImg (flag) {
      this.picFlag = flag
      this.tuneUp = !this.tuneUp
    },
    handleSelectImg ({ imgUrl }) {
      if (this.picFlag) {
        this.shareImgUrl = imgUrl
      } else {
        this.bgImgUrl = imgUrl
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
      width: 70px;
      margin-top: 10px;
    }
  }
}
</style>
