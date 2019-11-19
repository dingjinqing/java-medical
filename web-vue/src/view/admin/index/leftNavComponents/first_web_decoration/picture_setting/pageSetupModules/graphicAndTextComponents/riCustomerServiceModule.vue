<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>在线客服</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="content">
          <span>选择样式：</span>
          <div class="styleListContainer">
            <div
              class="styleList"
              v-for="(item,index) in styleListData"
              :key="index"
            >
              <img
                :style="item.id==='8'?'cursor:pointer':''"
                :src="item.imgUrl"
                @click="handleToAddImg(item.id)"
              >
              <el-radio
                v-model="moduleSaveData.service"
                :label="item.id"
              >{{''}}</el-radio>
            </div>
          </div>
        </div>
      </div>
      <!--模块私有end-->
    </div>
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp='tuneUp'
      :imageSize=[90,90]
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog') // 选择图片弹窗
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      tuneUp: false, // 选择图片弹窗调起flag
      styleListData: [// 样式列表数据
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer1.png`,
          id: '1'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer2.png`,
          id: '2'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer3.png`,
          id: '3'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer4.png`,
          id: '4'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer5.png`,
          id: '5'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer6.png`,
          id: '6'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer7.png`,
          id: '7'
        },
        {
          imgUrl: `${this.$imageHost}/image/admin/shop_deco/customer_add.png`,
          id: '8'
        }
      ],
      moduleSaveData: { // 保存数据

      }

    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.moduleSaveData = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    moduleSaveData: { // 模块公共
      handler (newData) {
        console.log(newData)
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
    // 调起添加图片弹窗
    handleToAddImg (id) {
      if (id === '8') {
        this.tuneUp = !this.tuneUp
      }
    },
    // 选中图片后回传事件
    handleSelectImg (res) {
      console.log(res)
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
      .content {
        display: flex;
        margin-top: 20px;
        span {
          display: inline-block;
          width: 100px;
        }
        .styleListContainer {
          width: 360px;
          border: 1px solid #eee;
          background: #fff;
          padding: 18px 18px 8px;
          display: flex;
          flex-wrap: wrap;
          .styleList {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            margin-right: 18px;
            margin-bottom: 10px;
            img {
              display: inline-block;
              width: 45px;
              height: 45px;
            }
            /deep/ .el-radio {
              display: flex;
              justify-content: center;
              padding-left: 10px;
              margin-top: 5px;
            }
          }
        }
      }
    }
    //end
  }
}
</style>
