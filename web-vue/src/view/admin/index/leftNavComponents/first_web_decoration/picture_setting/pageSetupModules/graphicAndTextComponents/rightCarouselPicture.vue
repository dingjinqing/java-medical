<template>
  <div class="rightPictureNavigation">
    <div class="rightPictureNavigationMain">
      <h2>{{$t('carouselPicture.carouselPictureModule')}}<span>{{$t('carouselPicture.carouselPictureModuleTip')}}</span></h2>
      <div class="main">
        <p class="tips">{{$t('carouselPicture.titleTips')}}</p>
        <div class="mainList">
          <label class="left">{{$t('carouselPicture.previewOriginal')}}</label>
          <div class="right">
            <div class="right-radios">
              <el-radio-group v-model="data.is_preview">
                <el-radio :label="0">{{$t('carouselPicture.no')}}</el-radio>
                <el-radio :label="1">{{$t('carouselPicture.yes')}}</el-radio>
              </el-radio-group>
            </div>
            <p class="tips">{{$t('carouselPicture.tips')}}</p>
          </div>
        </div>
        <!-- 图片添加组件 -->
        <div class="add_Imgs">
          <div
            class="chiose"
            v-for="(item,index) in data.img_items"
            :key="index"
          >
            <div class="add_img_top">
              <div
                class="add_img_left"
                :style="`background:url('${$imageHost}/image/admin/shop_beautify/add_decorete.png') no-repeat;background-size: 35%;background-position: center`"
                @click="handleToCallImgDialog(index)"
              >
                <el-image
                  style="width:70px; height:35px;"
                  :src="item.img_url"
                  fit="fill"
                >
                  <div
                    slot="error"
                    class="image-slot"
                  >
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
              </div>
              <p class="add_img_center">{{$t('carouselPicture.links')}}</p>
              <div class="add_img_right">
                <div class="add_img_rt">
                  <el-input
                    size="small"
                    style="width:50%;"
                    v-model="item.title_link"
                  ></el-input>
                  <el-button
                    size="small"
                    @click="handleToCallLinkDialog(index)"
                  >{{$t('carouselPicture.selectLink')}}</el-button>
                </div>
                <div class="add_img_rb">
                  <el-button
                    size="small"
                    :title="$t('carouselPicture.upward')"
                    @click="handleToClickIcon(index,0)"
                  >↑</el-button>
                  <el-button
                    size="small"
                    :title="$t('carouselPicture.down')"
                    @click="handleToClickIcon(index,1)"
                  >↓</el-button>
                  <el-button
                    size="small"
                    :title="$t('carouselPicture.remove')"
                    @click="handleToClickIcon(index,2)"
                  >×</el-button>
                  <a
                    class="add_img_more"
                    @click="item.showmore=!item.showmore"
                  >{{!item.showmore?$t('carouselPicture.showMoreConfigurations'):$t('carouselPicture.moreConfigurations')}}</a>
                </div>
              </div>
            </div>
            <div
              v-show="!!item.showmore"
              class="add_img_bottom"
            >
              <p class="add_img_bl">{{$t('carouselPicture.displaySettings')}}</p>
              <el-radio-group
                class="add_img_br"
                v-model="item.can_show"
              >
                <el-radio :label="0">{{$t('carouselPicture.visibleUsers')}}</el-radio>
                <el-radio :label="1">{{$t('carouselPicture.storeVisibleUsers')}}</el-radio>
              </el-radio-group>
            </div>
          </div>
        </div>
        <!-- 添加列表 -->
        <div
          class="add_list"
          @click="handleToClickAddList"
        >
          <span>+{{$t('carouselPicture.addList')}}</span>
        </div>
      </div>
      <!--end-->
    </div>
    <!--图片弹窗-->
    <ImageDialog
      :tuneUp='tuneUp'
      pageIndex='pictureSpace'
      :isDraggable='isDraggable'
      @handleSelectImg='handleToGetImgUrl'
    />
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath='handleToGetLinkPath'
    />
  </div>
</template>
<script>
export default {
  components: {
    ImageDialog: () => import('@/components/admin/imageDalog'),
    SelectLinks: () => import('@/components/admin/selectLinks')
  },
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      tuneUpSelectLink: false,
      tuneUp: false, // 选择图片弹窗
      isDraggable: false, // 选择图片弹窗是否多选
      changeLeft: true, // 标识是点击“添加列表”(false)还是点击图片模块左侧的“加号”(true)
      changeLeftIndex: null, // 图片模块的索引
      nowPathClick: null, // 当前点击的选择链接按钮所在列表的下标
      // 模块保存数据
      data: {
        is_preview: 0, // 是否可以在小程序中预览原图
        img_items: []
      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log('newData: ', newData)
        if (this.modulesData) {
          this.data = this.modulesData
        }
      },
      immediate: true
    },
    // 监听数据变换
    data: {
      handler (newData) {
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 导航配置列表右上角icon点击统一处理
    handleToClickIcon (index, flag) {
      let item = this.data.img_items[index]
      switch (flag) {
        case 0:
          if (index !== 0) {
            let data = JSON.parse(JSON.stringify(this.data))
            data.img_items[index] = data.img_items[(index - 1)]
            data.img_items[(index - 1)] = item
            this.data = data
          }
          break
        case 1:
          if (index !== (this.data.img_items.length - 1)) {
            let data = JSON.parse(JSON.stringify(this.data))
            data.img_items[index] = data.img_items[(index + 1)]
            data.img_items[(index + 1)] = item
            this.data = data
          }
          break
        case 2:
          this.data.img_items.splice(index, 1)
      }
      this.$forceUpdate()
    },
    // 点击模块左侧添加图片调起弹窗
    handleToCallImgDialog (index) {
      this.changeLeft = true
      this.changeLeftIndex = index
      this.isDraggable = false
      this.tuneUp = !this.tuneUp
    },
    // 点击底部添加列表调起图片弹窗
    handleToClickAddList () {
      this.changeLeft = false
      this.isDraggable = true
      this.tuneUp = !this.tuneUp
    },
    // 图片弹窗选中后回传图片信息
    handleToGetImgUrl (res) {
      console.log(res)
      if (this.changeLeft) {
        this.data.img_items[this.changeLeftIndex].img_url = res.imgUrl
      } else {
        // 限制只能传6个
        let length = 6 - this.data.img_items.length
        if (res.length > length) {
          this.$message.warning({
            message: '列表超过上限！',
            showClose: true
          })
        } else {
          let arr = res.map((item, index) => {
            return {
              img_url: item.imgUrl,
              title_link: '',
              src_width: item.imgWidth,
              src_height: item.imgHeight,
              can_show: 0,
              showmore: false
            }
          })
          console.log(arr)
          this.data.img_items = this.data.img_items.concat(arr)
          console.log(this.data.img_items)
        }
      }
    },
    // 调起选择链接弹窗
    handleToCallLinkDialog (index) {
      this.nowPathClick = index
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗回传数据
    handleToGetLinkPath (path) {
      this.data.img_items[this.nowPathClick].title_link = path
    }
  }
}
</script>
<style lang="scss" scoped>
.rightPictureNavigation {
  .rightPictureNavigationMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
      span {
        margin-left: 3%;
        color: #9a9a9a;
      }
    }
    .main {
      margin-top: 15px;
      .tips {
        color: #9a9a9a;
      }
      > .tips {
        margin-bottom: 20px;
      }
      .mainList {
        display: flex;
        margin-bottom: 20px;
        .left {
          height: 32px;
          line-height: 32px;
          white-space: nowrap;
        }
        .right {
          flex: 1;
          .right-radios {
            line-height: 32px;
          }
          .tips {
            max-width: 335px;
          }
        }
      }
      .add_Imgs {
        .chiose {
          position: relative;
          border: 1px solid #e5e5e5;
          margin: 10px 10px 0 10px;
          padding: 14px 14px 0;
          background: #fff;
          .add_img_top {
            display: flex;
            .add_img_left {
              display: flex;
              align-items: center;
              padding: 8px 10px;
            }
            .add_img_center {
              width: 15%;
              font-size: 14px;
              line-height: 32px;
            }
            .add_img_right {
              flex: 1;
              .add_img_rb {
                padding: 8px 0;
                .add_img_more {
                  margin-left: 10px;
                  color: #5a8bff;
                  font-size: 14px;
                  cursor: pointer;
                }
              }
            }
          }
          .add_img_bottom {
            display: flex;
            padding: 10px 0;
            border-top: 1px dashed #e5e5e5;
            .add_img_bl {
              margin-left: 96px;
            }
            .add_img_br {
              flex: 1;
            }
          }
        }
      }
      .add_list {
        padding: 30px 0;
        border: 1px dashed #ddd;
        color: #5a8cfd;
        text-align: center;
        background: #fff;
        margin: 20px 10px 0 10px;
        cursor: pointer;
      }
    }
    //end
  }
}
</style>
