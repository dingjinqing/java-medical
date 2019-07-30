<template>
  <div class="addingGoodsContent">
    <main class="addingGoodsContent_main">
      <!-- el-card start -->
      <el-card
        class="box-card"
        style="overflow-y:auto"
      >
        <!-- 步骤条 start -->
        <el-steps
          :active="2"
          finish-status="finish"
          simple
          style="margin-top: 0px"
        >
          <el-step
            title="编辑基本信息"
            icon="el-icon-edit"
          >
          </el-step>
          <el-step
            title="编辑商品详情"
            icon="el-icon-edit"
          ></el-step>
          <el-step
            title="编辑分销信息"
            icon="el-icon-edit"
          ></el-step>
        </el-steps>
        <!-- 步骤条 end -->
        <!-- 基本信息 -->
        <section class="basicInformation">
          基本信息
        </section>
        <!-- 基本信息 表单 -->
        <el-form
          :model="basicInformation"
          :rules="rules"
          ref="basicInformation"
          label-width="100px"
          class="basicInformation_form"
        >
          <el-form-item
            label="商品名称"
            prop="productName"
          >
            <el-col :span="16">
              <el-input v-model="basicInformation.productName"></el-input>
            </el-col>

          </el-form-item>
          <el-form-item
            label="商品广告词"
            prop="productAd"
          >
            <el-col :span="16">
              <el-input v-model="basicInformation.productAd"></el-input>
            </el-col>

          </el-form-item>
          <el-form-item
            label="商品货号"
            prop="productCode"
          >
            <el-col :span="8">
              <el-input v-model="basicInformation.productCode"></el-input>
            </el-col>
            <span style="color:#999;margin-left:15px;">不填则由系统自动生成货号</span>
          </el-form-item>
          <el-form-item
            label="平台分类"
            prop="platformClassification"
            :rules="[
              { required: true},
              ]"
          >
            <el-select
              v-model="value"
              clearable
              placeholder="请选择平台分类"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <span style="color:#999;">
              “平台分类”是商品在系统中的属性，不会对用户展示。可在“基础配置”中设置默认平台分类。
            </span>
            <a
              class="go"
              href="#"
            >前往</a>
          </el-form-item>
          <el-form-item
            label="商品主图"
            prop="productMainPicture"
            :rules="[
              { required: true},
              ]"
          >
            <!-- 商品主图上传 -->
            <el-upload
              style="display: inline-block;"
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img
                v-if="imageUrl"
                :src="imageUrl"
                class="avatar"
              >
              <i
                v-else
                class="el-icon-plus avatar-uploader-icon"
              ></i>
            </el-upload>
            <span class="goods-suggest">建议尺寸：800*800像素</span>
          </el-form-item>
        </el-form>
        <!-- 展开 收起更多配置 start -->
        <el-collapse
          v-model="activeName"
          accordion
        >
          <el-collapse-item
            title="展开收起更多配置"
            name="1"
          >
            <!-- 更多配置 部分 -->
            <el-form
              :model="moreConfiguration"
              ref="moreConfiguration"
              label-width="100px"
              class="moreConfiguration"
            >

              <el-form-item
                label="单位"
                prop="moreConfiguration_unit"
              >
                <el-select
                  v-model="moreConfiguration_unit"
                  clearable
                  placeholder="个"
                >
                  <el-option
                    label="个"
                    value="个"
                  >
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item
                label="商家分类"
                prop="moreConfiguration_unit"
              >
                <el-select
                  v-model="moreConfiguration_unit"
                  clearable
                  placeholder="商家分类"
                >
                  <el-option
                    label="商家分类"
                    value="商家分类"
                  >
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item
                label="商品标签"
                prop="moreConfiguration_unit"
              >
                <el-select
                  v-model="moreConfiguration_unit"
                  clearable
                  placeholder="商家标签"
                >
                  <el-option
                    label="商家标签"
                    value="商家标签"
                  >
                  </el-option>
                </el-select>
                <span>
                  <a
                    class="product_label refresh-label"
                    href="javascript:void(0)"
                  >刷新</a>
                  |
                  <a
                    class="product_label"
                    href="javascript:void(0)"
                    target="_blank"
                  >新建标签</a>
                  |
                  <a
                    class="product_label"
                    href="javascript:void(0)"
                    target="_blank"
                  >管理标签</a>
                </span>

              </el-form-item>

              <!-- 商品品牌 -->
              <el-form-item
                label="商品品牌:"
                prop="moreConfiguration_brand"
              >
                <span class="add_brand">
                  添加品牌
                </span>
                <a
                  class="link_brand"
                  href="#"
                  target="_blank"
                >新建品牌</a>
                |
                <a
                  class="link_brand"
                  href="#"
                  target="_blank"
                >管理品牌</a>
              </el-form-item>
              <!-- 商品视频 -->
              <el-form-item
                class="upload_video"
                label="商品视频:"
                prop="moreConfiguration_video"
              >
                <!-- 视频上传 -->
                <el-upload
                  class="upload-video"
                  drag
                  action="https://jsonplaceholder.typicode.com/posts/"
                  accept=""
                >
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将视频拖到此处，或<em>点击上传</em></div>
                  <div
                    class="el-upload__tip"
                    slot="tip"
                  >上传视频仅支持MP4格式。为保障无线端各种网络环境下正常播放，只支持上传大小不超过10M，时长不超过3分钟的视频。</div>
                </el-upload>
              </el-form-item>
            </el-form>
          </el-collapse-item>
        </el-collapse>
        <!-- 展开收起更多配置 end -->
        <section class="basicInformation">
          库存/价格信息
        </section>
        <section class="basicInformation">
          配送信息
        </section>
        <section class="basicInformation">
          其他信息
        </section>
        <!-- form 表单结构 start -->
        <el-form
          ref="otherInfo"
          :model="otherInfo"
          label-width="100px"
        >
          <el-form-item label="会员专享商品:">
            <!-- `checked` 为 true 或 false -->
            <el-checkbox v-model="checked">用户持有会员卡才可以购买此商品</el-checkbox>
            <el-select
              v-model="membershipCardSelection"
              clearable
              placeholder="请选择会员卡"
            >
            </el-select>
            <span>
              <a
                class="product_label refresh-label"
                href="javascript:void(0)"
              >刷新</a>
              |
              <a
                class="product_label"
                href="javascript:void(0)"
                target="_blank"
              >新建会员卡</a>
              |
              <a
                class="product_label"
                href="javascript:void(0)"
                target="_blank"
              >管理会员卡</a>
            </span>
          </el-form-item>
        </el-form>
        <!-- form 表单结构 end -->
      </el-card>
      <!-- el-card end -->
    </main>
  </div>
</template>
<script>
// import { mapActions } from 'vuex'
export default {
  name: 'step1',
  data () {
    return {
      basicInformation: {
        productName: '',
        productAd: '',
        productCode: '',
        platformClassification: ''
      },
      moreConfiguration: {

      },
      moreConfiguration_unit: '',
      rules: {
        productName: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
          // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ]
      },
      options: [
        {
          value: 'test01',
          label: 'test01'
        }
      ],
      value: '',
      imageUrl: '',
      activeName: '1',
      // 视频上传相关 data
      // 其他信息 -----------------
      otherInfo: {

      },
      checked: true,
      membershipCardSelection: ''
    }
  },
  methods: {
    // 商品主图上传的方法
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>
<style scoped lang='less'>
a {
  text-decoration: none;
}
.right_container {
  overflow-y: auto;
}
.addingGoodsContent {
  padding: 10px;
  // padding-right: 23px;
  // min-width: 1400px;
  // font-size: 14px;
  // height: 100%;
  // position: relative;
  .addingGoodsContent_main {
    padding: 0;

    .box-card {
      .basicInformation {
        height: 40px;
        background: #f8f8f8;
        line-height: 40px;
        width: 100%;
        padding-left: 10px;
        font-weight: bold;
        margin: 10px 0;
      }
      .basicInformation_form {
        .go {
          display: inline-block;
          color: #5a8bff;
          margin-left: 8px;
          text-decoration: none;
        }
        .avatar-uploader .el-upload {
          border: 1px dashed #ccc;
          border-radius: 6px;
          cursor: pointer;
          position: relative;
          overflow: hidden;
        }
        .avatar-uploader .el-upload:hover {
          border-color: #409eff;
        }
        .avatar-uploader-icon {
          font-size: 28px;
          color: #8c939d;
          width: 80px;
          height: 80px;
          line-height: 80px;
          text-align: center;
        }
        .avatar {
          width: 80px;
          height: 80px;
          display: block;
        }
        .goods-suggest {
          color: #999;
        }
      }
      .product_label {
        display: inline-block;
        color: #5a8bff;
        margin-left: 8px;
      }
      .add_brand {
        background: #fff;
        color: #333;
        border: 1px solid #ccc;
        width: 160px;
        height: 30px;
        display: inline-block;
        line-height: 30px;
        text-align: center;
        cursor: pointer;
        vertical-align: middle;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
      }
      .link_brand {
        display: inline-block;
        color: #5a8bff;
        margin-left: 8px;
      }
      // 视频上传
    }
  }
}
</style>
