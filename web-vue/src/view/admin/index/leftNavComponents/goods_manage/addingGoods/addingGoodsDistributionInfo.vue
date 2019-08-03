<template>
  <div class="addingGoodsDistributionInfo">
    <!-- 编辑分销信息 -->
    <el-row>
      <el-form
        ref="distribution"
        :model="distribution"
        label-width="120px"
      >
        <!-- 分销改价 -->
        <el-form-item label="分销改价：">
          <el-checkbox v-model="checked">允许分销员分销商品时修改商品售价</el-checkbox>
          <el-row
            class="priceGroup"
            v-if="checked"
          >
            <el-col
              :span="6"
              class="recommended_price"
            >
              <div class="sub-title">建议售价（元）</div>
              <el-input
                class="inline-input"
                v-model="state1"
                placeholder="建议售价为"
              ></el-input>
            </el-col>
            <el-col
              :span="6"
              class="lowest_price"
            >
              <div class="sub-title">最低售价（元）</div>
              <el-input
                class="inline-input"
                v-model="state2"
                placeholder="最低售价为"
              ></el-input>
            </el-col>
            <el-col :span="6">
              <div class="sub-title">最高售价（元）</div>
              <el-input
                class="inline-input"
                v-model="state2"
                placeholder="最高售价为"
              ></el-input>
            </el-col>
          </el-row>
        </el-form-item>
        <!-- 分销推广语言 -->
        <el-form-item label="分校推广语：">
          <el-row>
            <el-col
              :span="4"
              class="switch"
            >
              <el-switch
                style="display: block"
                v-model="isOpen"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="已开启"
                inactive-text="已关闭"
              >

              </el-switch>
            </el-col>
            <el-col :span="20">
              <span class="distributor">分销员下载当前商品海报时将直接复制此推广语到手机剪贴板</span>
            </el-col>
          </el-row>
          <!-- 推广语言内容 -->
          <el-row v-if="isOpen">
            <el-col :span="3">
              <label for="">推广语内容：</label>
            </el-col>
            <el-col :span="10">
              <el-input
                type="textarea"
                placeholder="请输入内容"
                v-model="textarea"
                maxlength="200"
                show-word-limit
              >
              </el-input>
            </el-col>
          </el-row>

        </el-form-item>
        <!-- 商品分享海报 -->
        <el-form-item label="商品分享海报:">
          <el-row>
            <el-col :span="3">
              <el-radio
                v-model="radio"
                label="1"
              >默认样式</el-radio>
            </el-col>
            <el-col :span="3">
              <el-link
                type="primary"
                :underline="false"
              >查看示例</el-link>
            </el-col>
            <el-col :span="3">
              <el-link
                type="primary"
                :underline="false"
              >下载海报</el-link>
            </el-col>

          </el-row>
          <el-row>
            <el-radio
              v-model="radio"
              label="2"
            >自定义样式
            </el-radio>
          </el-row>
          <el-row>
            <el-col :span="2">
              <label for="">文案：</label>
            </el-col>
            <el-col :span="8">
              <el-input></el-input>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="2">
              <label for="">分享图：</label>
            </el-col>
            <el-col :span="8">
              <el-radio
                v-model="radio1"
                label="1"
              >商品主图</el-radio>
              <el-radio
                v-model="radio1"
                label="2"
              >自定义图片</el-radio>

            </el-col>
          </el-row>
          <el-row>
            <el-col
              :span="4"
              class="upload_pic"
            >
              <el-upload
                class="uploader"
                action=""
                :show-file-list="false"
                :on-success="handleSuccess"
                :before-upload="beforeUpload"
              >
                <img
                  v-if="imageUrl"
                  :src="imageUrl"
                  class="pic"
                >
                <i
                  v-else
                  class="el-icon-plus uploader-icon"
                ></i>
              </el-upload>
            </el-col>
            <el-col
              :span="8"
              class="suggest"
            >
              <span>建议尺寸: 800*800像素</span>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </el-row>
    <!-- footer -->
    <div class="addingGoodsFooter">
      <section class="addingGoodsFooter">
        <el-button
          type="primary"
          size="small"
          @click.native.prevent="handleToList"
        >保存后返回列表</el-button>
        <el-button
          size="small"
          @click.native.prevent="handlePreStep"
        >上一步</el-button>
        <el-button
          type="primary"
          size="small"
          @click.native.prevent="handleAddAfterSaving"
        >保存后继续添加</el-button>
        <el-button
          type="primary"
          size="small"
          @click.native.prevent="handlePreview"
        >保存后预览商品</el-button>
      </section>
    </div>
  </div>
</template>
<script>
export default {
  name: 'addingGoodsDistributionInfo',
  data () {
    return {
      distribution: {

      },
      checked: true,
      state1: '',
      state2: '',
      state3: '',
      isOpen: true,
      textarea: '',
      radio: '1',
      radio1: '1',
      imageUrl: ''
    }
  },
  methods: {
    handleSuccess (res, file) {

    },
    beforeUpload (file) {

    },
    handleToList () {

    },
    handlePreStep () {
      this.$emit('toPre')
    },

    handleAddAfterSaving () {

    },
    handlePreview () {

    }
  }
}
</script>
<style scoped>
.priceGroup {
  border: 1px solid #ccc;
  padding: 10px;
  color: #333;
  border-radius: 2px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.recommended_price,
.lowest_price {
  margin-right: 10px;
}
.distributor {
  color: #999;
}
.switch {
  margin-top: 10px;
}
/* 上传图片样式 */
.upload_pic {
  border: 1px solid #eee;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
  position: relative;
  width: 79px;
  height: 79px;
  margin-left: 80px;
}
.uploader {
  width: 100%;
  height: 100%;
  display: block;
  justify-content: center;
  align-items: center;
}
.uploader-icon {
  color: #8c939d;
  width: 78px;
  height: 78px;
  line-height: 78px;
  text-align: center;
}
.suggest {
  margin-top: 20px;
  margin-left: 20px;
}
.addingGoodsFooter {
  position: fixed;
  bottom: 0;
  width: 1092px;
  padding: 10px;
  margin-left: -16px;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  z-index: 2;
  background: #f8f8fa;
  display: block;
}
</style>
