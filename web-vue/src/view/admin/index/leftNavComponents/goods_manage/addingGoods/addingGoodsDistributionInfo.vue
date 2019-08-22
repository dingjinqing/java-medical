<template>
  <!-- 编辑分销信息 -->
  <div class="addingGoodsDistributionInfo">
    <el-form
      ref="form"
      :model="formData"
      label-width="120px"
    >
      <!-- 分销改价 -->
      <el-form-item label="分销改价：">
        <el-checkbox v-model="checked">允许分销员分销商品时修改商品售价</el-checkbox>
        <!-- 允许分销员分销商品时修改商品售价 -->
        <section
          class="modify_price"
          v-show="checked"
        >
          <section class="modify_price_header">
            <section>建议售价(元)</section>
            <section>最低售价(元)</section>
            <section>最高售价（元）</section>
          </section>
          <section class="ipts">
            <el-input
              size="small"
              style="width:200px"
              v-model="formData.advisePrice"
              placeholder="建议售价为"
            ></el-input>

            <el-input
              size="small"
              style="width:200px"
              v-model="formData.miniPrice"
              placeholder="最低售价为"
            ></el-input>

            <el-input
              style="width:200px"
              size="small"
              v-model="formData.maxPrice"
              placeholder="最高售价为"
            ></el-input>
          </section>
        </section>

      </el-form-item>
      <!-- 分销推广语言 -->
      <el-form-item label="分销推广语：">
        <section class="switchWrap">
          <section class="switch">
            <!-- 开关 -->
            <el-switch
              style="display: block"
              v-model="promotionLanguageSwitch"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="已开启"
              inactive-text="已关闭"
            >
            </el-switch>
          </section>
          <section style="color:#999">分销员下载当前商品海报时将直接复制此推广语到手机剪贴板</section>
        </section>
        <!-- 推广语言内容 -->
        <section
          class="promotional_content"
          v-show="promotionLanguageSwitch"
        >
          <section class="content">推广语内容:</section>
          <section>
            <el-input
              style="width:400px"
              type="textarea"
              :autosize="{ minRows: 5,}"
              placeholder="请输入推广语内容"
              v-model="formData.promotionLanguage"
              maxlength="200"
              show-word-limit
            >
            </el-input>
          </section>
        </section>
      </el-form-item>
      <!-- 商品分享海报 -->
      <el-form-item label="商品分享海报:">
        <!-- 默认样式 -->
        <section>
          <el-radio
            v-model="radioList.radio1"
            label="默认样式"
          ><span style="margin-right:10px">默认样式</span>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src1"></el-image>
              <el-button
                slot="reference"
                type="text"
                style="margin-right:20px"
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
          </el-radio>
        </section>
        <!-- 自定义样式 -->
        <section class="customize">
          <el-radio
            v-model="radioList.radio1"
            label="自定义样式"
          >自定义样式</el-radio>
        </section>
        <!-- 文案 -->
        <section class="copywriting">
          <span>文案：</span>
          <el-input
            size="small"
            v-model="formData.copywriting"
            placeholder="请输入15个以内的字符"
            style="width:220px"
          ></el-input>
        </section>
        <!-- 分享图 -->
        <section class="share_img">
          <section>分享图：</section>
          <section class="">
            <!-- 商品主图 -->
            <section>
              <el-radio
                v-model="radioList.radio2"
                label="商品主图"
              >商品主图</el-radio>
            </section>
            <!-- 自定义图片 -->
            <section>
              <el-radio
                v-model="radioList.radio2"
                label="自定义图片"
              >自定义图片</el-radio>
            </section>
            <!-- Image -->
            <section class="img">
              <section class="image_wrap">
                <el-image
                  style="width: 70px; height: 70px"
                  :src="srcList.imageUrl"
                  fit="fill"
                ></el-image>
              </section>
            </section>
          </section>
        </section>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  name: 'addingGoodsDistributionInfo',
  data () {
    return {
      // 表单数据
      formData: {
        advisePrice: '', // 建议售价
        miniPrice: '', // 最低售价
        maxPrice: '', // 最高售价
        promotionLanguage: '', // 分享推广语
        copywriting: '' // 文案
      },
      checked: false, // 用来控制是否显示改价信息
      promotionLanguageSwitch: false, // 分销推广语
      radioList: {
        radio1: '默认样式',
        radio2: '商品主图'
      },
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/goods_info_exapmle1.jpg`,
        src2: `${this.$imageHost}/image/admin/share/goods_info_exapmle.jpg`,
        imageUrl: ``
      }
    }
  },
  // 方法
  methods: {

  }
}
</script>
<style scoped>
.modify_price {
  border: 1px solid #ccc;
  padding: 10px;
  color: #333;
  border-radius: 2px;
}
.modify_price_header {
  background-color: #f8f8f8;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.ipts {
  display: flex;
  justify-content: space-around;
}
.switchWrap {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 10px;
}
.switch {
  margin-right: 10px;
}
.promotional_content {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.content {
  margin-right: 20px;
}
.share_img {
  display: flex;
  justify-content: flex-start;
}
.copywriting,
.customize {
  margin: 10px 0;
}
.image_wrap {
  width: 70px;
  height: 70px;
  background: url(../../../../../../assets/image/admin/btn_add.png) no-repeat;
}
.img {
  margin: 10px 0;
}
</style>
