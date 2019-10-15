<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>商品模块</h2>
      <div class="main">
        <div class="title">
          <span>模块标题:</span>
          <div>
            <el-radio
              v-model="titleRadio"
              label="1"
            >不设置</el-radio>
            <el-radio
              v-model="titleRadio"
              label="2"
            >文字标题</el-radio>
            <el-radio
              v-model="titleRadio"
              label="3"
            >图片标题</el-radio>
          </div>
        </div>
        <!--模块标题隐藏模块-->
        <div
          class="titleHidden"
          v-if="titleRadio==='2' || titleRadio==='3'"
        >
          <div class="titleHiddenMain">
            <div class="topTitle">
              <span>标题：</span>
              <el-input
                v-model="titleInput"
                size="small"
              ></el-input>
              <span>最多10个字</span>
            </div>
            <div class="topLink">
              <span>标题链接：</span>
              <el-input
                v-model="titleLinkInput"
                size="small"
              ></el-input>
              <el-button
                size="small"
                @click="tuneUpSelectLink = !tuneUpSelectLink"
              >选择链接</el-button>
            </div>
            <div class="topPosition">
              <span>标题位置：</span>
              <el-checkbox v-model="positionChecked">标题居中</el-checkbox>
            </div>
            <div class="bgImg">
              <span>{{titleRadio==='2'?'图标':titleRadio==='3'?'标题图片':''}}：</span>
              <div class="bgIcon">

                <img
                  v-if="!iconImgUrl"
                  :src="$imageHost+'/image/admin/add_img_bg.png'"
                  class="bgImgDiv"
                  @click="tuneUp = !tuneUp"
                />
                <img
                  v-else
                  style="width:100%;height:40px"
                  :src="iconImgUrl"
                  @click="tuneUp = !tuneUp"
                >
              </div>
            </div>
          </div>
        </div>
        <!--模块标题隐藏模块end-->
        <!--列表样式模块-->
        <div class="listStyle">
          <div class="title">列表样式</div>
          <div class="content">
            <div
              class="typeContainer"
              v-for="(item,index) in listTypeData"
              :key="index"
              :style="item.isChecked?'border: 1px solid #5c81f4;':''"
              @click="handleToClickType(index)"
            >
              <div class="type">
                <div class="typeTop">
                  <span class="odd_left"></span>
                  <div class="odd_right">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                  </div>
                </div>
                <p>{{item.typeName}}</p>
              </div>
            </div>
          </div>
        </div>
        <!--商品模块-->
        <div class="commodityModule">
          <span style="margin-bottom:10px">商品模块</span>
          <div class="commodityMain">
            <div class="commodityContent">
              <span>显示内容：</span>
              <div class="contentRight">
                <div class="contentRightTop">
                  <el-checkbox v-model="commodityModule.name">商品名称</el-checkbox>
                  <el-checkbox v-model="commodityModule.price">商品价格</el-checkbox>
                  <el-checkbox v-model="commodityModule.label">商品标签</el-checkbox>
                </div>
                <div class="contentDiv">
                  <el-checkbox v-model="commodityModule.buyBtn">购买按钮</el-checkbox>
                  <span style="color:#999;white-space:nowrap">显示购买按钮时将不显示其他信息</span>
                </div>
                <!--选中购买按钮隐藏模块-->
                <div
                  class="buyBtnHidden"
                  v-if="commodityModule.buyBtn"
                >
                  <el-radio
                    v-model="commodityModule.hiddenRadio"
                    label="1"
                  >
                    <i
                      class="iconfont icontianjia icon_font_size new_class"
                      style="color: rgb(177, 78, 105);"
                    ></i>
                  </el-radio>
                  <el-radio
                    v-model="commodityModule.hiddenRadio"
                    label="2"
                  ><i
                      class="iconfont icongouwuche1 icon_font_size new_class"
                      style="color: rgb(177, 78, 105);"
                    ></i></el-radio>
                  <el-radio
                    v-model="commodityModule.hiddenRadio"
                    label="3"
                  >
                    <i
                      class="right_buy new_back"
                      style="background-color: rgb(177, 78, 105);"
                    >
                      马上抢
                    </i>
                  </el-radio>
                  <el-radio
                    v-model="commodityModule.hiddenRadio"
                    label="4"
                  >
                    <i
                      class="cart_buy"
                      style="color: rgb(177, 78, 105); border-color: rgb(177, 78, 105);"
                    >购买</i>
                  </el-radio>
                </div>
                <!--end-->
                <div class="contentDiv">
                  <el-checkbox v-model="commodityModule.otherInfoFlag">其他信息</el-checkbox>
                  <span style="color:#999;white-space:nowrap">后台数据仅为参考请以实际显示为准</span>
                </div>
                <div
                  class="contentDiv"
                  v-if="commodityModule.otherInfoFlag"
                >
                  <el-radio
                    v-model="commodityModule.contentRadio"
                    label="1"
                  >市场价</el-radio>
                  <el-radio
                    v-model="commodityModule.contentRadio"
                    label="2"
                  >销量</el-radio>
                  <el-radio
                    v-model="commodityModule.contentRadio"
                    label="3"
                  >评价数</el-radio>
                </div>
              </div>
            </div>
            <div class="commodityAngle">
              <span>模块角度：</span>
              <div class="angleDiv">
                <el-radio
                  v-model="commodityModule.angleRadio"
                  label="1"
                >直角</el-radio>
                <el-radio
                  v-model="commodityModule.angleRadio"
                  label="2"
                >圆角</el-radio>
              </div>
            </div>
            <div class="commodityAngle">
              <span>模块样式：</span>
              <div class="angleDiv">
                <el-radio
                  v-model="commodityModule.styleRadio"
                  label="1"
                >白底无边框</el-radio>
                <el-radio
                  v-model="commodityModule.styleRadio"
                  label="2"
                >边框投影</el-radio>
                <el-radio
                  v-model="commodityModule.styleRadio"
                  label="3"
                >白底有边框</el-radio>
              </div>
            </div>
            <div class="commodityAngle">
              <span>背景颜色：</span>
              <div class="bgColorDiv">
                <el-radio
                  v-model="commodityModule.bgColorRadio"
                  label="1"
                >与页面背景一致</el-radio>
                <div class="customBgColor">
                  <el-radio
                    v-model="commodityModule.bgColorRadio"
                    label="2"
                  >自定义</el-radio>
                  <span class="colorSelect">
                    <colorPicker
                      v-model="commodityModule.bgColor"
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

              </div>
            </div>
          </div>
        </div>
        <!--模块推荐-->
        <div class="moduleRecommendation">
          <span style="margin-bottom:10px">模块推荐：</span>
          <el-radio
            v-model="commodityModule.RecommendationRadio"
            label="1"
          >自动推荐</el-radio>
          <el-radio
            v-model="commodityModule.RecommendationRadio"
            label="2"
          >手动推荐</el-radio>
          <div class="moduleRecMain">
            <div class="manual">
              <div class="goodsNum">
                <span>商品数量：</span>
                <el-select
                  v-model="commodityModule.goodsNum"
                  placeholder="请选择"
                  size="small"
                >
                  <el-option
                    v-for="item in commodityModule.goodsOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
              <div>
                <span>商品价格：</span>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath='handleToSelectLinkPath'
    />
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp='tuneUp'
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'), // 图片弹窗组件
    SelectLinks: () => import('@/components/admin/selectLinks')
  },
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      titleRadio: '1', // 模块标题radio
      titleInput: '', // 标题输入框
      titleLinkInput: '', // 标题链接输入框
      positionChecked: false, // 标题位置复选按钮
      iconImgUrl: '', // 图标icon url
      tuneUpSelectLink: false, // 调起选择链接弹窗
      tuneUp: false, // 调起选择图片弹窗
      commodityModule: {
        name: false, // 商品名称
        price: false, // 商品价格
        label: false, // 商品标签
        buyBtn: false, // 购买按钮
        contentRadio: '1', // 显示内容底部radio
        angleRadio: '1', // 模块角度radio
        styleRadio: '1', // 模块样式radio
        bgColorRadio: '1', // 背景颜色radio
        bgColor: '#f5f5f5', // 背景颜色自定义
        hiddenRadio: '1', // 选中购买按钮隐藏模块里radio
        otherInfoFlag: false, // 其它信息选中
        RecommendationRadio: '1', // 模块推荐  自动推荐  手动推荐
        goodsNum: '',
        goodsOptions: [{
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }, {
          value: '6',
          label: '6'
        },
        {
          value: '7',
          label: '7'
        },
        {
          value: '8',
          label: '8'
        },
        {
          value: '9',
          label: '9'
        }, {
          value: '10',
          label: '10'
        }, {
          value: '11',
          label: '11'
        }, {
          value: '12',
          label: '12'
        }, {
          value: '13',
          label: '13'
        }, {
          value: '14',
          label: '14'
        }, {
          value: '15',
          label: '15'
        }, {
          value: '16',
          label: '16'
        }, {
          value: '17',
          label: '17'
        }, {
          value: '18',
          label: '18'
        }, {
          value: '19',
          label: '19'
        }, {
          value: '20',
          label: '20'
        }]
      },
      defaultColorright: '#f5f5f5', // 背景颜色自定义默认颜色
      listTypeData: [ // 列表样式数据
        {
          typeName: '单列',
          isChecked: true
        },
        {
          typeName: '双列',
          isChecked: false
        },
        {
          typeName: '三列',
          isChecked: false
        },
        {
          typeName: '横向滑动',
          isChecked: false
        },
        {
          typeName: '大图',
          isChecked: false
        }
      ]
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
      },
      immediate: true
    },
    'commodityModule.buyBtn' (newData) {
      if (newData) {
        this.commodityModule.otherInfoFlag = false
      }
    },
    'commodityModule.otherInfoFlag' (newData) {
      if (newData) {
        this.commodityModule.buyBtn = false
      }
    }
  },
  methods: {
    // 点击列表样式
    handleToClickType (index) {
      this.listTypeData.forEach(item => {
        item.isChecked = false
      })
      this.listTypeData[index].isChecked = true
    },
    // 模块标题图标点击
    handleToAddImg () {

    },
    // 选择链接选中回传
    handleToSelectLinkPath (path) {
      this.titleLinkInput = path
      console.log(path)
    },
    // 选择图片选中回传
    handleSelectImg (res) {
      console.log(res)
      this.iconImgUrl = res.imgUrl
    },
    // 商品模块颜色自定义重置点击
    handleToReset () {
      this.commodityModule.bgColor = this.defaultColorright
    }
    // this.$emit('handleToBackData', obj) 数据回传调用
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
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
    .main {
      margin-top: 20px;
      .title {
        span {
          display: inline-block;
          margin-right: 10px;
        }
        display: flex;
        /deep/ .el-radio {
          margin-right: 10px;
        }
      }
      .titleHidden {
        background: #fff;
        border: 1px solid #ddd;
        height: 253px;
        margin-top: 10px;
        .titleHiddenMain {
          height: 46px;
          padding-top: 20px;
          .topTitle,
          .topLink,
          .topPosition,
          .bgImg {
            margin-bottom: 10px;
            display: flex;
            justify-content: flex-start;
            span {
              display: inline-block;
              width: 100px;
              height: 32px;
              line-height: 32px;
            }
            span {
              white-space: nowrap;
              &:nth-of-type(1) {
                text-align: right;
              }
              &:nth-of-type(2) {
                text-align: left;
                color: #a7a7a7;
                margin-left: 3px;
              }
            }
            /deep/ .el-input {
              width: 252px;
            }
            /deep/ .el-button {
              margin-left: 3px;
            }
            /deep/ .el-checkbox {
              display: flex;
              justify-content: center;
              align-items: center;
              .el-checkbox__input {
                margin-top: 2px;
              }
            }
            .bgIcon {
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
          }
        }
      }
      .listStyle {
        margin-top: 10px;
        .title {
          margin-bottom: 10px;
        }
        .content {
          display: flex;
          flex-wrap: wrap;
          background-color: #fff;
          padding: 8px 10px 0;
          border: 1px solid #e5e5e5;
          cursor: pointer;
          .typeContainer {
            padding: 8px 10px;
            width: 30%;
            margin-right: 3%;
            height: 96px;
            border: 1px solid #e5e5e5;
            margin-bottom: 10px;
            .type {
              p {
                margin-top: 10px;
                text-align: center;
              }
              .typeTop {
                padding: 5px 5px 0 5px;
                display: flex;
                justify-content: space-between;
                span {
                  background: #e9f8fd;
                }
                .odd_left {
                  width: 50%;
                  height: 50px;
                }
                .odd_right {
                  height: 50px;
                  width: 44%;
                  span {
                    display: block;
                    &:nth-of-type(1) {
                      height: 12px;
                    }
                    &:nth-of-type(2) {
                      width: 60%;
                      height: 12px;
                      margin-top: 8px;
                    }
                    &:nth-of-type(3) {
                      height: 12px;
                      width: 60%;
                      margin-top: 6px;
                      display: inline-block;
                    }
                    &:nth-of-type(4) {
                      height: 12px;
                      width: 20%;
                      height: 12px;
                      display: inline-block;
                    }
                  }
                }
              }
            }
          }
        }
      }
      .commodityModule {
        margin-top: 10px;
        .commodityMain {
          background-color: #fff;
          padding: 8px 10px 0;
          border: 1px solid #e5e5e5;
          margin: 10px 0;
          .commodityContent {
            display: flex;
            padding-top: 20px;
            span {
              display: inline-block;
              width: 97px;
              height: 18px;
              line-height: 18px;
              text-align: right;
            }
            .contentRight {
              .contentRightTop {
                /depp/ .el-checkbox {
                  margin-right: 5px;
                }
              }
              .contentDiv {
                margin: 5px 0;
                /deep/ .el-radio {
                  margin-right: 5px;
                }
                /deep/ .el-checkbox {
                  margin-right: 22px;
                }
              }
              .buyBtnHidden {
                padding-left: 45px;
                /deep/ .el-radio__label {
                  padding-left: 3px;
                }
                /deep/ .el-radio {
                  margin-right: 0px;
                }
                .new_class {
                  position: relative;
                  top: 2px;
                  font-size: 32px !important;
                }
                .right_buy {
                  width: 70px;
                  height: 30px;
                  text-align: center;
                  line-height: 30px;
                  background: rebeccapurple;
                  color: white;
                  font-size: 12px;
                  border-radius: 15px;
                  display: inline-block;
                }
                .cart_buy {
                  width: 55px;
                  height: 30px;
                  text-align: center;
                  line-height: 30px;
                  border: 1px solid rebeccapurple;
                  color: rebeccapurple;
                  font-size: 12px;
                  border-radius: 15px;
                  background: white;
                  display: inline-block;
                }
              }
            }
          }
          .commodityAngle {
            display: flex;
            margin: 10px 0;
            span {
              display: inline-block;
              width: 97px;
              height: 18px;
              line-height: 18px;
              text-align: right;
            }
            .angleDiv {
              /deep/ .el-radio {
                margin-right: 5px;
              }
            }
            .bgColorDiv {
              display: flex;
              flex-direction: column;
              /deep/ .el-radio {
                margin-bottom: 10px;
              }
              .customBgColor {
                display: flex;
                /deep/ .el-radio {
                  margin-right: 5px;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  padding-top: 5px;
                }
                .colorSelect {
                  display: inline-block;
                  height: 32px;
                  width: 62px;
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
        }
      }
      .moduleRecommendation {
        margin-top: 10px;
        /deep/ .el-radio {
          margin-right: 5px;
        }
        .moduleRecMain {
          background-color: #fff;
          padding: 8px 10px 0;
          border: 1px solid #e5e5e5;
          margin: 10px 0;
          .manual {
            .goodsNum {
              span {
                display: inline-block;
                width: 97px;
                text-align: right;
              }
            }
          }
        }
      }
    }
  }
}
</style>
