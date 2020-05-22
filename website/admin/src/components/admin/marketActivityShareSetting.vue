<!--活动分享模块-->
<template>
  <div>
    <el-form-item
      :label="$t('marketCommon.activitySharing') + ':'"
      prop=''
    >
      <div>
        <el-radio
          v-model="actShare"
          label="1"
        >{{$t('marketCommon.defaultStyle')}}</el-radio>
        <el-popover
          placement="right-start"
          width="220"
          trigger="hover"
        >
          <el-image :src="srcList.src1"></el-image>
          <el-button
            slot="reference"
            type="text"
            style="margin: 0 20 0 0px"
          >{{$t('marketCommon.viewExample')}}</el-button>
        </el-popover>
        <el-popover
          placement="right-start"
          width="220"
          trigger="hover"
        >
          <el-image :src="srcList.src2"></el-image>
          <el-button
            slot="reference"
            type="text"
          >{{$t('marketCommon.downloadPoster')}}</el-button>
        </el-popover>
      </div>

      <div>
        <el-radio
          v-model="actShare"
          label="2"
        >{{$t('marketCommon.customStyle')}}</el-radio>
        <div
          style="margin: 15px 0"
          v-if="actShare == '2'"
        >
          <span style="color:#606266">{{ $t('marketCommon.copywriting') + '：'}}</span>
          <el-input
            v-model="shareConfig.share_doc"
            size="small"
            style="width:200px"
          ></el-input>
        </div>
        <div v-if="actShare == '2'">
          <span style="color:#606266">{{ $t('marketCommon.sharedPicture') + '：'}}</span>
          <el-radio
            v-model="shareImg.share_img_action"
            label="1"
            style="margin-left:10px"
          >{{$t('marketCommon.goodsInformationPicture')}}</el-radio>

          <div style="margin: 10px 0 0 60px">
            <el-radio
              v-model="shareImg.share_img_action"
              label="2"
            >{{$t('marketCommon.customPicture')}}</el-radio>
          </div>
          <div style="margin: 10px 0 0 60px; display:flex">
            <span
              @click="deleteSelectImg()"
              v-if="this.srcList.src3 !==`${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`"
              class="deleteIcon"
            >×</span>
            <div
              class="choose"
              @click="addGoodsImg"
            >
              <img
                class="selectImage"
                :src="srcList.src3"
              >
            </div>
            <span style="margin: 30px 0 0 30px">{{$t('marketCommon.customPictureTip')}}</span>
          </div>
        </div>

        <ImageDalog
          pageIndex='pictureSpace'
          :tuneUp="showImageDialog"
          :imageSize="[800, 800]"
          @handleSelectImg='handleSelectImg'
        />
      </div>
    </el-form-item>
  </div>
</template>

<script>
import ImageDalog from '@/components/admin/imageDalog'

export default {
  components: { ImageDalog },
  props: ['shareConfig'],
  data () {
    return {
      actShare: '1',
      shareImg: {
        share_img_action: '1'
      },
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        imageUrl: ``
      },
      showImageDialog: false
    }
  },
  methods: {

    // 活动分享 -- 添加图片点击事件，弹出图片选择组件
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    handleSelectImg (res) {
      console.log(res)
      if (res != null) {
        console.log(res)
        this.srcList.src3 = res.imgUrl
      }
    },
    // 删除商品图片
    deleteSelectImg () {
      this.srcList.src3 = `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`
    }
  }

}

</script>
<style lang="scss" scoped>
.choose {
  display: block;
  width: 70px;
  height: 70px;
  line-height: 70px;
  background: #fff;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  text-align: center;
  .selectImage {
    margin-top: 8px;
  }
}
.deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: relative;
  top: -8px;
  right: -80px;
  cursor: pointer;
  opacity: 0.8;
}
</style>
