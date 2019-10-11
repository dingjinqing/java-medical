<template>
  <div class="rightPictureNavigation">
    <div class="rightPictureNavigationMain">
      <h2>轮播图片模块<span>图片最多6个，最少1个，建议尺寸：620*310</span></h2>
      <div class="main">
        <p>轮播图组件至少设置一张轮播图为全部用户可见</p>
        <div class="mainList">
          <label class="left">预览原图:</label>
          <div class="">
            <div>
              <el-radio-group v-model="data.is_preview">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
              </el-radio-group>
            </div>
            <p>选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图</p>
          </div>
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
      tuneUp: false,

      isDraggable: false,
      changeLeft: true,
      changeLeftIndex: null,
      nowPathClick: null, // 当前点击的选择链接按钮所在列表的下标
      // 模块保存数据
      data: {
        is_preview: '0', // 是否可以在小程序中预览原图
        img_items: []
      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        this.data = this.modulesData
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
    }
  },
  methods: {
    // 点击重置
    handleToReset (index) {
      if (index === 0) {
        this.data.font_color = '#fff'
      } else {
        this.bg_color = '#fff'
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
    .main {
      margin-top: 15px;
      .maniList {
        display: flex;
        margin-bottom: 20px;
        .left {
          height: 32px;
          line-height: 32px;
        }
      }
    }
    //end
  }
}
</style>
