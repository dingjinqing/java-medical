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
          <el-input
            v-model="basicInformation.productName"
            size="small"
          ></el-input>
        </el-col>

      </el-form-item>
      <el-form-item
        label="商品广告词："
        prop="productAd"
      >
        <el-col :span="16">
          <el-input
            v-model="basicInformation.productAd"
            size="small"
          ></el-input>
        </el-col>

      </el-form-item>
      <el-form-item
        label="商品货号："
        prop="productCode"
      >
        <el-col :span="8">
          <el-input
            v-model="basicInformation.productCode"
            size="small"
          ></el-input>
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
            size="small"
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
            size="small"
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
            size="small"
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
        :rules="[{ required: true},]"
      >
        <el-row>
          <el-col :span='3'>
            <section
              class="upLoadMainPic"
              @click="upLoadPic"
            >
              <img
                class="upLoadImg"
                :src="$imageHost+'/image/admin/add_img.png'"
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
                  size="small"
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
                  size="small"
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
              size="small"
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
              size="small"
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
    <el-form
      :model="inventoryAndPrice"
      :rules="inventoryAndPrice"
      ref="inventoryAndPrice"
      label-width="120px"
      class="inventoryAndPrice"
    >
      <el-form-item
        label-width="120px"
        label="商品规格："
        prop="productSpecifications"
      >
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品库存："
        prop="commodityStocks"
      >
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品价格："
        prop="commodityPrice"
      >
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="市场价格："
        prop="marketPrice"
      >
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="会员价："
        prop="memberPrice"
      >
      </el-form-item>
    </el-form>
    <section class="basicInformation">
      配送信息
    </section>
    <el-form
      :model="delivery"
      :rules="delivery"
      ref="delivery"
      label-width="120px"
      class="delivery"
    >
      <el-form-item
        label="运费模板："
        prop="shipping"
        :rules="[{ required: true},]"
      >
        <el-select
          size="small"
          v-model="freight"
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="item in freight_options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>

        </el-select>
        <el-link
          class="el_link"
          type="primary"
          :underline="false"
        >刷新</el-link>|
        <el-link
          class="el_link"
          type="primary"
          :underline="false"
        >新建模板</el-link>|
        <el-link
          class="el_link"
          type="primary"
          :underline="false"
        >管理模板</el-link>
        <el-row class="ten">
          店铺统一运费：10元
          <el-link
            type="primary"
            :underline="false"
            class="detail"
          >查看详细</el-link>
        </el-row>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品重量："
        prop="weight"
      >
        <el-input
          size="small"
          v-model="delivery.weight"
          style="width:160px;margin-right:8px"
        >
        </el-input>Kg
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="发货地："
        prop="place"
      >
        <el-input
          size="small"
          v-model="delivery.place"
          style="width:260px;margin-right:8px"
        >
        </el-input>
        <span>最多填写15个字</span>
      </el-form-item>
    </el-form>
    <section class="basicInformation">
      其他信息
    </section>
    <el-form
      :model="other"
      :rules="other"
      ref="other"
      label-width="120px"
      class="other"
    >

      <el-form-item
        label="会员专享商品："
        prop="member"
      >
        <el-checkbox v-model="checked">用户持有会员卡才可以购买此商品</el-checkbox>
        <el-row v-show="checked ===true">
          <el-select
            size="small"
            v-model="membershipCard"
            multiple
            placeholder="请选择会员卡"
          >
            <el-option
              v-for="item in membershipCard_options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <el-link
            class="el_link"
            type="primary"
            :underline="false"
          >刷新</el-link>|
          <el-link
            class="el_link"
            type="primary"
            :underline="false"
          >新建会员卡</el-link>|
          <el-link
            class="el_link"
            type="primary"
            :underline="false"
          >管理会员卡</el-link>
        </el-row>
      </el-form-item>

      <el-form-item
        label="上下架："
        prop="upAndDown"
      >

        <el-radio-group v-model="radio">
          <el-row>
            <el-radio :label="3">立即上架售卖</el-radio>
          </el-row>
          <el-row class="middle">
            <el-col :span="10">
              <el-radio :label="6">自定义上架时间</el-radio>
            </el-col>
            <el-col :span="10">
              <el-date-picker
                size="small"
                v-model="date_value"
                align="right"
                type="date"
                placeholder="选择上架时间"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </el-col>

          </el-row>
          <el-row>
            <el-radio :label="9">暂不售卖，放入仓库</el-radio>
          </el-row>

        </el-radio-group>

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
      <pictureTemplate />
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
// api
import { deliverTemplatelist } from '@/api/admin/goods_manage/deliver/deliver'
// components
import pictureTemplate from '@/components/admin/pictureTemplate/pictureTemplateContent'
import { selectPlatformClassification } from '@/api/admin/addingGoods/addingGoodsBasicInfo'
export default {
  components: { pictureTemplate },
  props: {
    active: Number
  },
  created () {
    this.init()
    this.fetchDeliverTemplatelist()
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
      // 库存/价格信息---- start
      inventoryAndPrice: {},
      // 库存/价格信息---- end
      // 配送信息 -------- start
      delivery: {
        weight: '',
        place: ''
      },
      freight_options: [],
      freight: '',
      // 配送信息 -------- end
      // 其他信息 -------- start
      other: { member: '', upAndDown: '' },
      checked: false,
      membershipCard: [],
      membershipCard_options: [{
        value: '1',
        label: '储值卡11'
      }, {
        value: '2',
        label: '开卡送券'
      }, {
        value: '3',
        label: '余额'
      }, {
        value: '4',
        label: '会员卡余额'
      }, {
        value: '5',
        label: '省钱月卡'
      }, {
        value: '6',
        label: '黄金会员卡'
      }, {
        value: '7',
        label: '108度会员卡'
      }],
      radio: 3,
      pickerOptions: {

        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]

      },
      date_value: ''
      // 其他信息 -------- end
    }
  },
  methods: {
    fetchDeliverTemplatelist () {
      let params = {
        'page': {
          'currentPage': '1',
          'pageRows': '20'
        }
      }
      deliverTemplatelist(params).then(res => console.log(res)).catch(err => console.log(err))
    },
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
.middle {
  margin: 20px 0;
  display: flex;
  /* justify-content: center; */
  align-items: center;
}
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

.customize {
  margin-left: 20px;
}
.el_link {
  margin: 0 8px;
}
.ten {
  padding: 0 20px;
  color: #333;
  margin-top: 10px;
  background: #f5f5f5;
  width: 920px;
  height: 46px;
}
.detail {
  float: right;
}
</style>
