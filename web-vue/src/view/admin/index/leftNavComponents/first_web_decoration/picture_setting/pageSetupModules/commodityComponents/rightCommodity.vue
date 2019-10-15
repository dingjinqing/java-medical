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
                  <el-checkbox v-model="commodityModule.price">商品名称</el-checkbox>
                  <el-checkbox v-model="commodityModule.label">商品名称</el-checkbox>
                </div>
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
        label: false // 商品标签
      },
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
    }
    // this.$emit('handleToBackData', obj) 数据回传调用
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
            span {
            }
          }
        }
      }
    }
  }
}
</style>
