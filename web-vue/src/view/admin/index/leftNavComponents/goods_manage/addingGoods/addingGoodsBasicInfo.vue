<template>
  <div class="addingGoodsBasicInfo">
    <!-- 基本信息 -->
    <section class="basicInformation">
      基本信息
    </section>
    <!-- 基本信息 表单 -->
    <el-form
      :model="basicInformation"
      :rules="basicInformation_rules"
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
          :rules="rules"
          :model="moreConfiguration"
          ref="moreConfiguration"
          label-width="100px"
          class="moreConfiguration"
        >
          <!-- 单位 -->
          <el-form-item
            label="单位"
            prop="customize"
          >
            <el-row>
              <el-col :span="5">
                <el-select
                  @change="unitChangeHandle"
                  v-model="unit_value"
                  placeholder="请选择单位"
                >
                  <el-option
                    v-for="item in unit_options"
                    :key="item.unit_value"
                    :label="item.unit_label"
                    :value="item.unit_value"
                  >
                  </el-option>
                </el-select>
              </el-col>
              <el-col
                :span="5"
                class="customize"
              >
                <el-input
                  @blur="customize_value"
                  v-model="moreConfiguration.customize"
                  placeholder="长度限制为3个中文字符"
                  v-show="isCustomize"
                ></el-input>
              </el-col>
            </el-row>

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
    <!-- 商品主图上传的dialog -->
    <el-dialog
      :visible.sync="dialogVisible_pic"
      width="80%"
      :before-close="handleClose"
    >

      <span
        slot="title"
        class="dialog_hearder"
      >浏览图片</span>
      <upLoadForPicture />
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size='small'
          type="primary"
          @click="confirmUpload"
        >确 定</el-button>
        <el-button
          @click="dialogVisible_pic = false"
          size='small'
        >取 消</el-button>

      </span>
    </el-dialog>
  </div>
</template>
<script>
import upLoadForPicture from '@/components/admin/upLoadForPicture/upLoadForPicture'
import { selectPlatformClassification } from '@/api/admin/addingGoods/addingGoodsBasicInfo'
export default {
  components: { upLoadForPicture },
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
      // 商品主图上传
      dialogVisible_pic: false,
      basicInformation: {
        productName: '',
        productAd: '',
        productCode: '',
        platformClassification: ''
      },
      moreConfiguration: {
        customize: ''
      },
      basicInformation_rules: {
        customize: [
          { required: true, message: '请输入自定义单位', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 个中文字符', trigger: 'blur' }
        ]
      },
      moreConfiguration_unit: '',
      // 单位的选择
      unit_value: '',
      unit_options: [{
        unit_value: '选项1',
        unit_label: '个'
      }, {
        unit_value: '选项2',
        unit_label: '包'
      }, {
        unit_value: '选项3',
        unit_label: '箱'
      }, {
        unit_value: '选项4',
        unit_label: '袋'
      }, {
        unit_value: '选项5',
        unit_label: '套'
      }, {
        unit_value: '选项6',
        unit_label: '卷'
      }, {
        unit_value: '选项7',
        unit_label: '件'
      }, {
        unit_value: '选项8',
        unit_label: '台'
      },
      {
        unit_value: '选项9',
        unit_label: '吨'
      }, {
        unit_value: '选项10',
        unit_label: '平方米'
      }, {
        unit_value: '选项11',
        unit_label: '本'
      }, {
        unit_value: '选项12',
        unit_label: '幅'
      }, {
        unit_value: '选项13',
        unit_label: '张'
      }, {
        unit_value: '选项14',
        unit_label: '支'
      }, {
        unit_value: '选项15',
        unit_label: '盒'
      }, {
        unit_value: '选项16',
        unit_label: '份'
      }, {
        unit_value: '选项17',
        unit_label: '令'
      }, {
        unit_value: '选项18',
        unit_label: '千克'
      }, {
        unit_value: '选项19',
        unit_label: '自定义'
      }],
      isCustomize: false,
      //
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
    handleClose (done) {
      done()
    },
    confirmUpload () {
      this.dialogVisible_pic = false
    },
    upLoadPic () {
      this.dialogVisible_pic = true
    },
    // 底部按钮跳转
    handleToList () {
      console.log('tolist')
    },
    handleNextStep () {
      this.$emit('toSecondPage')
    },
    // 单位选择
    unitChangeHandle (val) {
      if (val === '选项19') {
        this.isCustomize = true
      } else {
        this.isCustomize = false
      }
    },
    customize_value () {
      console.log(this.moreConfiguration.customize)
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
.dialog_hearder {
  color: #333;
}
.expandAndCollapse {
  color: red;
  background-color: skyblue;
}
.customize {
  margin-left: 20px;
}
</style>
