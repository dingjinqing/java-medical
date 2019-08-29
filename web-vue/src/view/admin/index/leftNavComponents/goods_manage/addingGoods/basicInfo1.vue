<template>
  <div>
    <!--基本信息配置模块-->
    <div>
      <el-form
        ref="basicInfoForm"
        :model="goodsProductInfo"
        :rules="basicInfoRules"
        label-width="120px"
      >
        <el-form-item
          label="商品名称："
          prop="goodsName"
        >
          <el-input
            v-model="goodsProductInfo.goodsName"
            size="small"
            style="width:400px"
          />
        </el-form-item>
        <el-form-item label="商品广告词：">
          <el-input
            v-model="goodsProductInfo.goodsAd"
            size="small"
            style="width:400px"
          />
        </el-form-item>
        <el-form-item label="商品货号：">
          <el-input
            v-model="goodsProductInfo.goodsSn"
            size="small"
            style="width:170px;"
          />
          <span class="inputTip">不填则由系统自动生成货号</span>
        </el-form-item>
        <el-form-item
          label="平台分类："
          prop="catId"
        >
          <el-select
            v-model="catIdTemp.firstCatId"
            size="small"
            @change="catIdSelectChange(1,$event)"
          >
            <el-option
              :value="null"
              label="请选择平台分类"
            />
            <el-option
              v-for="item in catIdTemp.firstCatData"
              :label="item.catName"
              :value="item.catId"
              :key="item.catId"
            />
          </el-select>
          <el-select
            v-if="!!catIdTemp.firstCatId"
            v-model="catIdTemp.secondCatId"
            size="small"
            @change="catIdSelectChange(2,$event)"
          >
            <el-option
              :value="null"
              label="请选择平台分类"
            />
            <el-option
              v-for="item in catIdTemp.secondCatData"
              :label="item.catName"
              :value="item.catId"
              :key="item.catId"
            />
          </el-select>
          <el-select
            v-if="!!catIdTemp.firstCatId&&!!catIdTemp.secondCatId"
            v-model="catIdTemp.thirdCatId"
            size="small"
            @change="catIdSelectChange(3,$event)"
          >
            <el-option
              :value="null"
              label="请选择平台分类"
            />
            <el-option
              v-for="item in catIdTemp.thirdCatData"
              :label="item.catName"
              :value="item.catId"
              :key="item.catId"
            />
          </el-select>
          <span class="inputTip">
            “平台分类”是商品在系统中的属性，不会对用户展示。可在“基础配置”中设置默认平台分类。
          </span>
          <el-link
            type="primary"
            :underline="false"
            href="#"
            target="_blank"
          >前往</el-link>
        </el-form-item>
        <el-form-item
          label="商品主图："
          prop="goodsImg"
        >
          <!-- 商品主图 -->
          <section class="goods_img">
            <!-- 5张图片 -->
            <ul class="img_group">
              <li
                class="li_image"
                v-for="(item,i) in goodsImgs"
                :key="i"
              >
                <el-image
                  style="width: 100%; height: 100%"
                  :src="item.url"
                  fit="fill"
                ></el-image>
              </li>
            </ul>
            <section
              v-show="this.goodsImgs.length !== 5"
              class="add_img"
              @click="handleVisibileDialog"
            >
              <el-image
                style="width: 48px; height: 48px"
                :src="srcList.src1"
                fit="fill"
              ></el-image>
            </section>
            <!-- 建议尺寸 -->
            <section>
              <span class="inputTip">
                建议尺寸：800*800像素
              </span>
            </section>
          </section>

          <!-- <goodsMainPic @imgListChange="goodsImgsChange" /> -->

        </el-form-item>
      </el-form>
      <!-- 基本信息更多配置 -->
      <el-collapse accordion>
        <el-collapse-item
          title="展开/收起更多配置"
          name="1"
        >
          <el-form
            ref="basicInfoOtherForm"
            :model="goodsProductInfo"
            :rules="basicInfoRules"
            label-width="120px"
          >
            <el-form-item
              label="单位："
              prop="unit"
            >
              <el-select
                v-model="unitSelectedValue"
                @change="unitSelectChange"
                size="small"
              >
                <el-option
                  v-for="(item,index) in unitSelectOptions"
                  :key="index"
                  :value="item.value"
                  :label="item.label"
                />
              </el-select>
              <el-input
                v-if="unitSelectedValue===null"
                v-model="unitCustomerValue"
                @change="unitCustomerChange"
                size="small"
                style="width:100px;"
              />
              <span
                v-if="unitSelectedValue===null"
                class="inputTip"
              >长度限制为3个中文字符</span>
            </el-form-item>
            <el-form-item
              label="商家分类："
              prop="sortId"
            >
              <el-select
                filterable
                v-model="goodsProductInfo.sortId"
                placeholder="请选择商家分类"
                size="small"
              >
                <el-option
                  v-for="item in sortSelectOptions"
                  :key="item.sortId"
                  :label="item.sortName"
                  :value="item.sortId"
                  :style="{'marginLeft': item.level===1? '10px':'0px'}"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="商品标签："
              prop="goodsLabels"
            >
              <el-select
                v-model="labelSelectedTempVal"
                placeholder="请选择商品标签"
                size="small"
                @change="labelSelectChange"
              >
                <el-option
                  v-for="item in labelSelectOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
              <el-link
                type="primary"
                :underline="false"
                @click="labelSelectRefresh"
                href="#"
                style="margin:0 5px;"
              >刷新
              </el-link>
              |
              <el-link
                type="primary"
                :underline="false"
                href="#"
                style="margin:0 5px;"
              >新建标签</el-link>
              |
              <el-link
                type="primary"
                :underline="false"
                href="#"
                style="margin:0 5px;"
              >管理标签</el-link>
            </el-form-item>
            <el-form-item
              label="商品品牌："
              prop="brandName"
            >
              <el-input
                v-model="goodsProductInfo.brandId"
                disabled
                placeholder="添加品牌"
                size="small"
                @click="brandInputClick"
                style="width:170px;"
              />
            </el-form-item>
            <el-form-item
              label="商品视频："
              prop="video"
              @click="videoInputClick"
            >

              <span class="inputTip">上传视频仅支持MP4格式。为保障无线端各种网络环境下正常播放，只支持上传大小不超过10M，时长不超过3分钟的视频。</span>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </div>
    <!-- 商品主图选择弹窗 -->
    <imageDialogExpansion
      pageIndex='basicInfo'
      @handleGoodsImgs='handleGoodsImgs'
    />
  </div>
</template>
<script>
// 接口函数引入
import {
  selectPlatformClassification,
  goodsSortAndGoodsBrandInitApi
} from '@/api/admin/goodsManage/addingGoods/addingGoods'
// 组件导入
// import goodsMainPic from './goodsMainPic'
// 导入图片弹窗扩展组件
import imageDialogExpansion from '@/components/admin/imageDialogExpansion/imageDialogExpansion'
import addBrandDialog from './addBrandDialog'

export default {
  components: { addBrandDialog, imageDialogExpansion },
  data () {
    return {
      // 商品主图上传相关data
      srcList: {
        src1: `${this.$imageHost}/image/admin/add_img.png`
      },
      // 商品主图
      goodsImgs: [],
      goodsProductInfo: {
        // 基本信息
        goodsName: null,
        goodsAd: null,
        goodsSn: null,
        catId: null,
        goodsImg: null,
        goodsImgs: [],
        unit: null,
        sortId: null,
        goodsLabels: null,
        brandId: null,
        goodsVideo: null,
        goodsVideoImg: null,
        goodsVideoSize: null,
        goodsVideoId: null
      },
      /* 基本信息部分 */
      // 基本信息验证
      basicInfoRules: {
        goodsName: [
          { required: true, message: '请输入商品名称', trigger: 'change' }
        ],
        catId: [
          { required: true, message: '请选择平台分类', trigger: 'change' }
        ],
        goodsImg: [
          { required: true, message: '请选择商品图片', trigger: 'change' }
        ],
        unit: [
          { required: true, message: '单位不可为空', trigger: 'change' }
        ]
      },
      // 基本信平台分类辅助数据，对应分类每一级别的下落框数据和选中值
      catIdTemp: {
        firstCatId: null,
        secondCatId: null,
        thirdCatId: null,
        firstCatData: null,
        secondCatData: null,
        thirdCatData: null
      },
      /* 基本信息更多配置部分 */
      unitSelectOptions: [{
        value: '个',
        label: '个'
      }, {
        value: '包',
        label: '包'
      }, {
        value: '箱',
        label: '箱'
      }, {
        value: '袋',
        label: '袋'
      }, {
        value: '套',
        label: '套'
      }, {
        value: '卷',
        label: '卷'
      }, {
        value: '件',
        label: '件'
      }, {
        value: '台',
        label: '台'
      },
      {
        value: '吨',
        label: '吨'
      }, {
        value: '平方米',
        label: '平方米'
      }, {
        value: '本',
        label: '本'
      }, {
        value: '幅',
        label: '幅'
      }, {
        value: '张',
        label: '张'
      }, {
        value: '支',
        label: '支'
      }, {
        value: '盒',
        label: '盒'
      }, {
        value: '份',
        label: '份'
      }, {
        value: '令',
        label: '令'
      }, {
        value: '千克',
        label: '千克'
      }, {
        value: null,
        label: '自定义'
      }],
      unitSelectedValue: '个',
      unitCustomerValue: null,
      // 商家分类下落框
      sortSelectOptions: null,
      // 商品标签下拉框
      labelSelectOptions: null,
      // 商品品牌下拉框
      brandSelectOptions: null,
      // 标签已选中列表
      labelSelectedItems: [],
      // 标签来下框选中瞬间的值
      labelSelectedTempVal: null
    }
  },

  methods: {
    /* 基本信息部分 */
    // 平台分类下拉框交互
    catIdSelectChange (level, catId) {
      this.goodsProductInfo.catId = catId

      //  选则一级则重置二三级
      if (level === 1) {
        this.catIdTemp.secondCatId = null
        this.catIdTemp.secondCatData = null
        this.catIdTemp.thirdCatId = null
        this.catIdTemp.thirdCatData = null
      }
      // 选择二级则重置三级
      if (level === 2) {
        this.catIdTemp.thirdCatId = null
        this.catIdTemp.thirdCatData = null
        if (catId === null) {
          this.goodsProductInfo.catId = this.catIdTemp.firstCatId
        }
      }
      // 选择三级
      if (level === 3 && catId === null) {
        this.goodsProductInfo.catId = this.catIdTemp.secondCatId
      }

      if (catId === null) {
        return
      }

      selectPlatformClassification(catId).then(res => {
        if (level === 1) {
          this.catIdTemp.secondCatData = res.content
        }
        if (level === 2) {
          this.catIdTemp.thirdCatData = res.content
        }
      })
    },
    // 初始化平台分类一级下拉框数据
    catIdInit () {
      selectPlatformClassification(0).then(res => {
        this.catIdTemp.firstCatData = res.content
      })
    },
    // 商品主图相关函数
    // 唤起弹窗
    handleVisibileDialog () {
      this.$http.$emit('dtVisible')
    },
    // 商品图片事件监听函数
    handleGoodsImgs (val) {
      console.log(val)
      this.goodsImgs = val
    },
    // 商品图片事件监听函数
    // goodsImgsChange (imgList) {
    //   if (imgList === null || imgList.length === 0) {
    //     return
    //   }
    //   // 数组第一个值默认为商品主图
    //   this.goodsProductInfo.goodsImg = imgList.shift()
    //   // 其余为商品子图
    //   this.goodsProductInfo.goodsImgs = imgList
    //   this.$refs.basicInfoForm.validateField('goodsImg')
    // },
    /* 基本信息更多配置部分 */
    // 单位选择下拉框处理事件
    unitSelectChange (value) {
      if (value === null) {
        // 当用户自定义过单位然后选择其他单位，当再次选择自定义时可以保留上一次的输入
        this.goodsProductInfo.unit = this.unitCustomerValue
      } else {
        this.goodsProductInfo.unit = value
      }
      this.$refs.basicInfoOtherForm.validateField('unit')
    },
    // 自定义单位处理事件
    unitCustomerChange () {
      this.goodsProductInfo.unit = this.unitCustomerValue
      this.$refs.basicInfoOtherForm.validateField('unit')
    },
    // 初始化商家分类和商品标签
    sortAndLabelAndBrandSelectInit () {
      goodsSortAndGoodsBrandInitApi().then(res => {
        const { content: { goodsBrands, goodsLabels, goodsSorts } } = res
        this.sortSelectOptions = goodsSorts
        this.labelSelectOptions = goodsLabels
        this.brandSelectOptions = goodsBrands
      })
    },
    // 标签下拉框选择事件
    labelSelectChange () {
      this.labelSelectOptions = this.labelSelectOptions.filter(item => {
        if (item.id === this.labelSelectedTempVal) {
          this.labelSelectedItems.push(item)
          return false
        }
        return true
      })
      this.labelSelectedTempVal = null
    },
    labelSelectRefresh () {
      goodsSortAndGoodsBrandInitApi().then(res => {
        const { content: { goodsLabels } } = res
        this.labelSelectOptions = goodsLabels.filter(item => !this.labelSelectedItems.some(innerItem => innerItem.id === item.id))
      })
    },
    brandInputClick () {
      // TODO: 目前的实现的商品品牌选择框逻辑错误，需要修改
    },
    videoInputClick () {
      // TODO: 视频选择弹出未实现
    }
  },
  mounted () {
    // 初始化平台分类一级下拉框
    this.catIdInit()
    // 初始化商家分类和商品标签
    this.sortAndLabelAndBrandSelectInit()
  }
}
</script>
<style scoped>
.inputTip {
  color: #999;
  margin-left: 15px;
}

.goods_img {
  display: flex;
  align-items: center;
}
.add_img {
  width: 80px;
  height: 80px;
  color: #333;
  background-color: #f7f7f7;
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
}
.img_group {
  display: flex;
}
.li_image {
  border: 1px solid #ccc;
  width: 80px;
  height: 80px;
  margin: 0 10px;
}
</style>
