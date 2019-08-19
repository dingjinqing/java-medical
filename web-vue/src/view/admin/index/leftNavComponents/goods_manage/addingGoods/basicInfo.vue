<template>
  <div class="basicInfo">
    <el-form
      :model="formData"
      :rules="rules"
      ref="basicInfo_form"
      label-width="120px"
      class="basic"
    >
      <el-form-item
        style="width:400px;"
        label="商品名称："
        prop="goodsName"
      >
        <el-input
          ref="goodsName"
          style="width:400px"
          @blur="sendGoodsName"
          v-model="formData.goodsName"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="商品广告词："
        prop="goodsAd"
      >
        <el-input
          style="width:400px"
          v-model="formData.goodsAd"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="商品货号："
        prop="goodsNumber"
      >
        <el-input
          style="width:160px;"
          v-model="formData.goodsNumber"
          size="small"
        ></el-input>
        <span style="color:#999;margin-left:15px;">不填则由系统自动生成货号</span>
      </el-form-item>
      <el-form-item
        label="平台分类："
        prop="catId"
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
      <el-form-item
        label="商品主图："
        prop="goodsImg"
      >
        <goodsMainPic />
      </el-form-item>
    </el-form>
    <!-- 展开收起更多配置 -->
    <el-collapse accordion>
      <el-collapse-item
        title="展开/收起更多配置"
        name="1"
      >
        <el-form
          :rules="rules1"
          :model="formData1"
          ref="form1"
          label-width="120px"
          class="form1"
        >
          <!-- 单位 -->
          <el-form-item
            label="单位："
            prop="unit"
          >
            <el-select
              size="small"
              v-model="formData1.unit"
              placeholder="请选择"
              @change="getValue"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <el-input
              size="small"
              style="width:100px;"
              v-model="customUnit"
              v-show="isShow"
            ></el-input>
          </el-form-item>
          <!-- 商家分类 -->
          <el-form-item
            label="商家分类："
            prop="sortId"
          >
            <el-select
              filterable
              placeholder="请选择商家分类"
              v-model="formData1.sortId"
              size="small"
            >
              <el-option
                v-for="item in sortOptions"
                :key="item.sortId"
                :label="item.sortName"
                :value="item.sortId"
                :class="[item.level ===1?'level_1':'',item.level ===2?'level_2':'']"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <!-- 商品标签 -->
          <el-form-item
            label="商品标签："
            prop="name"
          >
            <el-select
              filterable
              placeholder="请选择商品标签"
              v-model="formData1.name"
              size="small"
            >
              <el-option
                v-for="item in goodsLabels"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <!-- 商品品牌 -->
          <el-form-item
            label="商品品牌："
            prop="brandName"
          >
            <span
              class="brand"
              @click="showDialog"
            >添加品牌</span>
          </el-form-item>
          <!-- 商品视频 -->
          <el-form-item
            label="商品视频："
            prop="video"
          >
            <span>上传视频仅支持MP4格式。为保障无线端各种网络环境下正常播放，只支持上传大小不超过10M，时长不超过3分钟的视频。</span>
          </el-form-item>
        </el-form>
      </el-collapse-item>
    </el-collapse>
    <addBrandDialog :dialogVisible.sync="dialogVisible" />
    <!-- <el-button @click="handleTest">测试数据</el-button> -->
  </div>
</template>
<script>
import { selectPlatformClassification } from '@/api/admin/addingGoods/addingGoodsBasicInfo'
// import { getSortList } from '@/api/admin/goodsSort/goodsSort'
// import { deleteSort } from '@/api/admin/goodsSort/goodsSort'
import { initGrandgetRequest } from '@/api/admin/brandManagement.js'
import { getLabelList } from '@/api/admin/labelList/labelList'
import addBrandDialog from './addBrandDialog'
import goodsMainPic from './goodsMainPic'
export default {
  /* eslint-disable */
  name: 'basicInfo',
  components: { addBrandDialog, goodsMainPic },
  data() {
    return {
      formData: {
        goodsName: '',
        goodsAd: '',
        goodsNumber: '',
        catId: '',
        goodsImg: 'https://s2.ax1x.com/2019/08/12/exsIJI.png'
      },
      formData1: {
        unit: '个',
        cus: '',
        brand: '',
        goodsLabels: [],
        sortId: '',
        name: '',
        brandName: '',
        video: ''
      },
      rules: {
        goodsName: [
          { required: true, message: '请输入商品名称', trigger: 'change' }
        ],
        goodsAd: [
          { required: true, message: '请输入商品广告词', trigger: 'change' }
        ]
      },
      rules1: {

      },
      value: [],
      value2: [],
      value3: [],
      optionsData3: [],
      optionsData2: [],
      optionsData: [],
      second: false,
      third: false,

      options: [{
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
        value: '选项15',
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
        value: '自定义',
        label: '自定义'
      }],
      customUnit: '',
      isShow: false,
      sortOptions: [],
      optionProps: {
        value: 'sortId',
        label: 'sortName',
        children: 'children'
      },
      goodsLabels: [],
      dialogVisible: false,

    }
  },
  created() {
    this.init()
    this.fetchSortList()
  },
  mounted() {

  },
  computed: {
    getFormData() {
      return this.formData
    }
  },
  methods: {

    handleTest() {
      getLabelList({}).then(res => console.log(res)).catch(err => console.log(err))
      // deleteSort({
      //   'sortId': 20
      // }).then(res => console.log(res)).catch(err => console.log(err))
      // addSort({
      //   'sortName': '女装',
      //   'parentId': 24,
      //   'level': 1,
      //   'sortImg': '',
      //   'imgLink': '',
      //   'first': 13,
      //   'type': 0,
      //   'sortDesc': ''
      // }).then(res => console.log(res)).catch(err => console.log(err))

      let params = {
        unit: this.customUnit
      }
      console.log(params, this.formData1)
    },
    handleChange(value) {
      console.log(value)
      selectPlatformClassification(value).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.optionsData2 = this.formatContent(content)
          this.second = true
        }
      })
    },
    handleChange2(value) {
      console.log(value)
      selectPlatformClassification(value).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.optionsData3 = this.formatContent(content)
          this.third = true
        }
      })
    },
    handleChange3(value) {
      this.formData.catId = value
    },
    formatContent(content) {
      let newArr = []
      content.forEach(item => {
        newArr.push({
          value: item['catId'],
          label: item['catName']
        })
      })
      return newArr
    },
    init() {
      selectPlatformClassification(0).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.optionsData = this.formatContent(content)
        }
      })
    },
    getValue(val) {
      if (val === '自定义') {
        this.isShow = true
      } else {
        this.isShow = false
      }
    },
    fetchSortList() {
      initGrandgetRequest().then(res => {
        const { error, content: { goodsSorts, goodsLabels } } = res
        console.log(res)
        if (error === 0) {
          console.log(goodsSorts)
          console.log(goodsLabels)
          this.sortOptions = goodsSorts
          this.goodsLabels = goodsLabels
        }
      }).catch(err => console.log(err))
    },
    showDialog() {
      this.dialogVisible = true
    },
    // 传值函数 事件派发
    passValue() {

    },
    sendGoodsName() {

    }

  }

}
</script>

<style scoped>
.level_1 {
  margin-left: 15px;
}
.brand {
  background: #fff;
  color: #333;
  border: 1px solid #dcdfe6;
  border-radius: 5px;
  width: 180px;
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
</style>
