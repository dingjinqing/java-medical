<template>
  <div class="rightPictureNavigation">
    <div class="rightPictureNavigationMain">
      <h2>{{$t('pictureNavigation.pictureNavigationModule')}}<span>{{$t('pictureNavigation.pictureSuggestedSize')}}：140*140</span></h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="mainList">
          <span :style="guideCircleClass?'text-align: center;width:76px':''">{{$t('pictureNavigation.navigationStyle')}}：</span>
          <div
            class="navigationDiv"
            :style="guideCircleClass?'flex-direction: column':''"
          >
            <div
              class="guide_circle"
              :style="data.nav_style==='1'?'border: 1px solid #5a8BFF':''"
              @click="data.nav_style='1'"
              :class="guideCircleClass"
            >
              <div
                v-for="(item,index) in $t('pictureNavigation.guidList')"
                :key="index"
                class="circle"
                :style="guideCircleClass?'align-items: center':''"
              >
                <div style="border-radius: 50%;"></div>
                <p :style="guideCircleClass?'text-align: center':''">{{item}}</p>
              </div>
            </div>
            <div
              class="guide_circle"
              :style="data.nav_style==='2'?'border: 1px solid #5a8BFF':''"
              @click="data.nav_style='2'"
              :class="guideCircleClass"
            >
              <div
                v-for="(item,index) in $t('pictureNavigation.guidList')"
                :key="index"
                class="circle"
                :style="guideCircleClass?'align-items: center':''"
              >
                <div></div>
                <p :style="guideCircleClass?'text-align: center':''">{{item}}</p>
              </div>
            </div>
          </div>

        </div>
        <div class="mainList">
          <span class="left">{{$t('pictureNavigation.fontColor')}}：</span>
          <span class="colorSelect">
            <el-color-picker
              v-model="data.font_color"
              show-alpha
              :predefine="predefineColors"
            >
            </el-color-picker>

          </span>
          <div style="margin-left:10px;margin-top:-1px">
            <el-button
              @click="handleToReset(0)"
              size="small"
            >{{$t('pictureNavigation.reset')}}</el-button>
          </div>
        </div>
        <div class="mainList">
          <span class="left">{{$t('pictureNavigation.bgColor')}}：</span>
          <span class="colorSelect">
            <el-color-picker
              v-model="data.bg_color"
              show-alpha
              :predefine="predefineColors"
            >
            </el-color-picker>
          </span>
          <div style="margin-left:10px;margin-top:-1px">
            <el-button
              @click="handleToReset(1)"
              size="small"
            >{{$t('pictureNavigation.reset')}}</el-button>
          </div>
        </div>
        <div class="bottom">
          <div
            class="chiose"
            v-for="(item,index) in data.nav_group"
            :key="index"
          >
            <div
              class="addImg"
              :style="`background:url('${$imageHost}/image/admin/shop_beautify/add_decorete.png') no-repeat;background-size: 45%;background-position: center`"
              @click="handleToCallImgDialog(index)"
            >
              <img
                v-if="item.nav_src"
                :src="item.nav_src"
              >
            </div>
            <div class="chioseRight">
              <div>
                <span>{{$t('pictureNavigation.writtenWords')}}：</span>
                <el-input
                  v-model="item.nav_name"
                  size="small"
                ></el-input>
              </div>
              <div>
                <span>{{$t('pictureNavigation.link')}}：</span>
                <el-input
                  v-model="item.nav_link"
                  :placeholder="$t('pictureNavigation.linkPlaceholder')"
                  size="small"
                ></el-input>
                <el-button
                  @click="handleToCallLinkDialog(index)"
                  size="small"
                >{{$t('pictureNavigation.selectLink')}}</el-button>
              </div>
            </div>
            <!--右上角icon-->
            <div class="chioseIcon">
              <img
                @click="handleToClickIcon(index,0)"
                class="iconImg"
                :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
              >
              <img
                @click="handleToClickIcon(index,1)"
                class="iconImg"
                :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
              >
              <img
                @click="handleToClickIcon(index,2)"
                class="iconImg"
                :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
              >
            </div>
          </div>
        </div>
        <div
          class="addList"
          @click="handleToClickAddList()"
        >
          {{$t('pictureNavigation.addList')}}
        </div>
      </div>
      <!--end-->
    </div>
    <!--图片弹窗-->
    <ImageDialog
      :tuneUp='tuneUp'
      pageIndex='pictureSpace'
      :isDraggable='isDraggable'
      :imageSize='[140, 140]'
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
      tuneUpSelectLink: false,
      tuneUp: false,
      guidList: ['导航一', '导航二', '导航三'],
      isChecked: 0,
      defaultTypefaceColor: '#92b0e4',
      defaultBgColor: '#fff',
      navigationList: [
        {
          input: '导航一',
          linkPpath: '',
          imgUrl: ''
        },
        {
          input: '导航二',
          linkPpath: '',
          imgUrl: ''
        },
        {
          input: '导航三',
          linkPpath: '',
          imgUrl: ''
        },
        {
          input: '导航四',
          linkPpath: '',
          imgUrl: ''
        }
      ],
      isDraggable: false,
      changeLeft: true,
      changeLeftIndex: null,
      nowPathClick: null, // 当前点击的选择链接按钮所在列表的下标
      guideCircleClass: 'guideCircleClass', // 图片导航导航样式英文适配
      // 模块保存数据
      data: {
        'nav_style': '1',
        'font_color': '#92b0e4',
        'bg_color': '#ffffff',
        'nav_group': []
      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        this.$nextTick(() => {
          this.data = this.modulesData
        })
      },
      immediate: true
    },
    // 监听数据变换
    data: {
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    },
    lang () {
      this.data.nav_group = this.$t('pictureNavigation.navigationList')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 点击重置
    handleToReset (index) {
      if (index === 0) {
        this.data.font_color = 'rgba(255, 69, 0, 1)'
      } else {
        this.data.bg_color = 'rgba(255, 255, 255, 1)'
      }
    },
    // 导航配置列表右上角icon点击统一处理
    handleToClickIcon (index, flag) {
      console.log(index, flag)
      let item = this.data.nav_group[index]
      switch (flag) {
        case 0:
          if (index !== 0) {
            let data = JSON.parse(JSON.stringify(this.data))
            data.nav_group[index] = data.nav_group[(index - 1)]
            data.nav_group[(index - 1)] = item
            this.data = data
          }
          break
        case 1:
          if (index !== (this.data.nav_group.length - 1)) {
            console.log(1111)
            let data = JSON.parse(JSON.stringify(this.data))
            data.nav_group[index] = data.nav_group[(index + 1)]
            data.nav_group[(index + 1)] = item
            this.data = data
          }
          break
        case 2:
          this.data.nav_group.splice(index, 1)
      }
      this.$forceUpdate()
    },
    // 图片弹窗选中后回传图片信息
    handleToGetImgUrl (res) {
      console.log(res)
      if (this.changeLeft) {
        this.data.nav_group[this.changeLeftIndex].nav_src = res.imgUrl
      } else {
        let length = 5 - this.data.nav_group.length
        if (res.length > length) {
          this.$message.warning({
            message: '列表超过上限',
            showClose: true
          })
        } else {
          let arr = []
          res.forEach((item, index) => {
            let obj = {
              nav_name: '',
              nav_link: '',
              nav_src: ''
            }
            obj.nav_src = item.imgUrl
            arr.push(obj)
          })
          console.log(arr)
          let newArr = this.data.nav_group.concat(arr)
          this.data.nav_group = newArr
          console.log(newArr)
        }
        console.log(res)
        // this.navigationList.push(res)
      }
    },
    // 点击底部添加列表调起图片弹窗
    handleToClickAddList () {
      this.changeLeft = false
      this.isDraggable = true
      this.tuneUp = !this.tuneUp
    },
    // 点击导航条左边添加图片调起弹窗
    handleToCallImgDialog (index) {
      this.changeLeft = true
      this.changeLeftIndex = index
      this.isDraggable = false
      this.tuneUp = !this.tuneUp
    },
    // 调起选择链接弹窗
    handleToCallLinkDialog (index) {
      this.nowPathClick = index
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗回传数据
    handleToGetLinkPath (path) {
      console.log(path)
      this.data.nav_group[this.nowPathClick].nav_link = path
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
    //模块私有样式
    .main {
      margin-top: 15px;
      .mainList {
        display: flex;
        margin-bottom: 20px;
        .left {
          height: 32px;
          line-height: 32px;
        }
        .navigationDiv {
          display: flex;
          .guide_circle {
            width: 170px;
            height: 90px;
            background-color: #fff;
            margin-right: 10px;
            border-radius: 6px;
            display: flex;
            justify-content: space-around;
            border: 1px solid #fff;
            .circle {
              display: flex;
              flex-direction: column;
              justify-content: center;
              div {
                background: #e9f8fd;
                width: 36px;
                height: 36px;
                margin-bottom: 5px;
              }
            }
          }
          .guideCircleClass {
            width: auto;
            justify-content: space-around;
            margin-bottom: 5px;
          }
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
      .bottom {
        .chiose {
          position: relative;
          border: 1px solid #e5e5e5;
          margin: 10px 10px 0 10px;
          padding: 14px;
          background: #fff;
          display: flex;
          .addImg {
            height: 70px;
            width: 70px;
            border: 1px solid #e5e5e5;
            img {
              width: 100%;
              height: 100%;
            }
          }
          .chioseRight {
            margin-left: 10px;
            div {
              height: 36px;
              display: flex;
              span {
                white-space: nowrap;
                display: inline-block;
                height: 36px;
                line-height: 36px;
              }
              /deep/ .el-input {
                width: 122px;
              }
              /deep/ .el-button {
                margin-left: 5px;
                height: 32px;
              }
            }
          }
        }
        .chioseIcon {
          position: absolute;
          top: 5px;
          right: 5px;
          .iconImg {
            padding-right: 5px;
            cursor: pointer;
          }
        }
      }
      .addList {
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
