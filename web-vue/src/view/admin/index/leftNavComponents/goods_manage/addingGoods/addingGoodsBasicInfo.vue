<template>
  <div class="addingGoodsBasicInfo">
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
        label="商品名称："
        prop="productName"
      >
        <el-col :span="16">
          <el-input v-model="basicInformation.productName"></el-input>
        </el-col>

      </el-form-item>
      <el-form-item
        label="商品广告词："
        prop="productAd"
      >
        <el-col :span="16">
          <el-input v-model="basicInformation.productAd"></el-input>
        </el-col>

      </el-form-item>
      <el-form-item
        label="商品货号："
        prop="productCode"
      >
        <el-col :span="8">
          <el-input v-model="basicInformation.productCode"></el-input>
        </el-col>
        <span style="color:#999;margin-left:15px;">不填则由系统自动生成货号</span>
      </el-form-item>
      <!-- 平台分类 -->
      <el-form-item
        label="平台分类："
        prop="platformClassification"
        :rules="{
      required: true, message: '平台分类不能为空', trigger: 'blur'
    }"
      >
        <el-row>
          <el-select
            v-model="value"
            placeholder="请选择平台分类"
            @change='handleChange'
          >
            <el-option
              v-for="item in optionsData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <el-select
            v-show="second"
            placeholder="请选择"
            v-model="value2"
            @change='handleChange2'
          >
            <el-option
              v-for="item in optionsData2"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <el-select
            v-show="third"
            placeholder="请选择"
            v-model="value3"
            @change='handleChange3'
          >
            <el-option
              v-for="item in optionsData3"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-row>

        <span style="color:#999;">
          “平台分类”是商品在系统中的属性，不会对用户展示。可在“基础配置”中设置默认平台分类。
        </span>

        <el-link
          type="primary"
          :underline="false"
          href="#"
          target="_blank"
        >前往</el-link>
      </el-form-item>
      <!-- 商品主图 -->
      <el-form-item
        label="商品主图："
        prop="productMainPicture"
        :rules="[
              { required: true},
              ]"
      >
        <!-- 商品主图上传 -->
        <el-row>
          <el-col :span='3'>
            <section
              class="upLoadMainPic"
              @click="upLoadPic"
            >
              <img
                class="upLoadImg"
                src="http://mpdevimg2.weipubao.cn/image/admin/add_img.png"
                alt="商品主图上传"
              >
            </section>
          </el-col>
          <el-col :span='10'>
            <section class="goods-suggest">建议尺寸：800*800像素</section>
          </el-col>
        </el-row>

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
              <el-link
                type="primary"
                :underline="false"
              >刷新</el-link>
              |
              <el-link
                type="primary"
                :underline="false"
              >新建标签</el-link>
              |
              <el-link
                type="primary"
                :underline="false"
              >管理标签</el-link>
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
            <el-link
              type="primary"
              :underline="false"
            >新建品牌</el-link>
            |
            <el-link
              type="primary"
              :underline="false"
            >管理品牌</el-link>
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
          <el-link
            type="primary"
            :underline="false"
          >刷新</el-link>
          |
          <el-link
            type="primary"
            :underline="false"
          >新建会员卡</el-link>
          |
          <el-link
            type="primary"
            :underline="false"
          >管理会员卡</el-link>
        </span>
      </el-form-item>
    </el-form>
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
          @click.native.prevent="handleNextStep"
        >下一步</el-button>
      </section>
    </div>
  </div>
</template>
<script>
import { selectPlatformClassification } from '@/api/admin/addingGoods/addingGoodsBasicInfo'
export default {
  props: {
    active: Number
  },
  created () {
    this.init()
  },
  data () {
    return {
      // 选择平台
      value: [],
      value2: [],
      value3: [],
      optionsData3: [],
      optionsData2: [],
      optionsData: [],
      second: false,
      third: false,
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
    // 平台分类
    handleChange (value) {
      selectPlatformClassification(value).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.optionsData2 = this.formatContent(content)
          this.second = true
        }
      })
    },
    handleChange2 (value) {
      selectPlatformClassification(value).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.optionsData3 = this.formatContent(content)
          this.third = true
        }
      })
    },
    handleChange3 (value) {
      console.log(value)
    },
    formatContent (content) {
      let newArr = []
      content.forEach(item => {
        newArr.push({
          value: item['catId'],
          label: item['catName']
        })
      })
      return newArr
    },
    init () {
      selectPlatformClassification(0).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.optionsData = this.formatContent(content)
        }
      })
    },
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
    },
    handleToList () {
      console.log('tolist')
    },
    handleNextStep () {
      this.$emit('toSecondPage')
    },
    upLoadPic () {
      console.log('上传商品主图')
    }
  }
}
</script>
<style scoped>
.basicInformation {
  font-weight: bold;
  height: 40px;
  background: #f8f8f8;
  line-height: 40px;
  width: 100%;
  padding-left: 10px;
  margin-top: 20px;
}
.addingGoodsFooter {
  position: fixed;
  bottom: 0;
  width: 1093px;
  padding: 10px;
  margin-left: -16px;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  z-index: 2;
  background: #f8f8fa;
  display: block;
}
.upLoadMainPic {
  background: #f7f7f7;
  border: 1px solid #ccc;
  width: 80px;
  height: 81px;
  text-align: center;
  line-height: 80px;
  font-size: 0;
}
.goods-suggest {
  color: #999;
  float: left;
  height: 80px;
  line-height: 80px;
}
.upLoadImg {
  max-width: 100%;
  max-height: 100%;
  vertical-align: middle;
  border: none;
}
</style>
