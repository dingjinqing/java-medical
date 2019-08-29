<template>
  <div>
    <!-- <el-form>
      <el-form-item
        label="好友砍价优惠券："
        prop=""
      >
        <el-card class="box-card">
          <div>向帮忙砍价的用户赠送优惠券，可促使帮砍用户在店铺内下单，提高交易量。</div>
          <div class="addInfo">
            <img
              :src="srcList.src"
              alt=""
            >
            <p>添加优惠券</p>
          </div>
          <div>最多添加5张优惠券，已过期和已停用的优惠券不能添加</div>
        </el-card>
      </el-form-item>

      <el-form-item
        label="鼓励奖："
        prop=""
      >
        <el-card class="box-card">
          <div>买家砍价失败后给予一定奖励，可提升买家复购</div>
          <div class="addInfo">
            <img
              :src="srcList.src"
              alt=""
            >
            <p>添加优惠券</p>
          </div>
          <div>最多添加5张优惠券，已过期和已停用的优惠券不能添加</div>
        </el-card>
      </el-form-item>

      <el-form-item
        label="活动分享："
        prop=""
      >
        <div>
          <el-radio
            v-model="radio"
            label="1"
          >
            <span>默认样式</span>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src1"></el-image>
              <el-button
                slot="reference"
                type="text"
                style="margin: 0 20px"
              >查看示例</el-button>
            </el-popover>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src"></el-image>
              <el-button
                slot="reference"
                type="text"
              >下载海报</el-button>
            </el-popover>
          </el-radio>
        </div>
        <div>
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
                <div
                  class="selectPic"
                  @click="handleShowDialog"
                >
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
          <ImageDalog
            pageIndex='pictureSpace'
            @handleSelectImg='handleSelectImg'
          />
        </div>
      </el-form-item>
    </el-form> -->
  </div>
</template>

<script>

export default {
  data () {
    return {

    }
  }

}

</script>
<style lang="scss" scoped>
.box-card {
  width: 630px;
  background-color: #f5f5f5;
  .addInfo {
    display: inline-block;
    width: 100px;
    height: 98px;
    margin-bottom: 10px;
    background: #fff;
    border: 1px solid #e4e4e4;
    padding: 13px 0;
    cursor: pointer;
    text-align: center;
  }
}
.selectPic {
  // display: flex;
  width: 70px;
  height: 70px;
  line-height: 70px;
  text-align: center;
  border: 1px solid #e4e4e4;
  .recPic {
    width: 80px;
    height: 80px;
    background-repeat: no-repeat;
  }
}
</style>
