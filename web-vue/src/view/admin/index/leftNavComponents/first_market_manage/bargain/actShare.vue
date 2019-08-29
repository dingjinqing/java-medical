<template>
  <div>
    <el-form-item
      label="活动分享："
      prop=''
    >
      <section>
        <el-radio
          v-model="radio"
          label="1"
        >
          <span>默认样式</span>
        </el-radio>
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
          >查看示例</el-button>
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
          >下载海报</el-button>
        </el-popover>
      </section>

      <section>
        <el-radio
          v-model="radio"
          label="2"
        >
          自定义样式
          <div style="margin: 15px 0">
            <span>文案：</span>
            <el-input
              size="small"
              style="width:200px"
            ></el-input>
          </div>
          <div>
            <span>分享图：</span>
            <el-radio
              v-model="radio"
              label="1"
            >活动商品信息图</el-radio>
            <div style="margin: 10px 0 0 60px">
              <el-radio
                v-model="radio"
                label="2"
              >自定义图片</el-radio>
            </div>
            <div style="margin: 10px 0 0 60px; display:flex">
              <div class="selectPic">
                <img
                  class="recPic"
                  :src="srcList.src3"
                  alt=""
                >
              </div>
              <span style="margin: 30px 0 0 30px">建议尺寸：800*800像素</span>
            </div>
          </div>
        </el-radio>
        <!-- <ImageDalog
          pageIndex='pictureSpace'
          @handleSelectImg='handleSelectImg'
        /> -->
      </section>
    </el-form-item>
  </div>
</template>

<script>

export default {
  data () {
    return {
      radio: 1,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        imageUrl: ``
      }
    }
  }

}

</script>
<style lang="scss" scoped>
</style>
