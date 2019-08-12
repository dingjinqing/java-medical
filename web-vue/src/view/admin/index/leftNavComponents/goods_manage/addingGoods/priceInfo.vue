<template>
  <div class="priceInfo">
    <el-form
      :model="formData"
      :rules="rules"
      ref="form"
      label-width="120px"
      class="inventoryAndPrice"
    >
      <el-form-item
        label-width="120px"
        label="商品规格："
        prop=""
      >
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品库存："
        prop="goodsNumber"
      >
        <el-input-number
          size="small"
          v-model="formData.goodsNumber"
          controls-position="right"
          @change="handleChange"
          :min="0"
        ></el-input-number>
        <span style="color: #999;">设置了规格库存商品库存将失效，不在前端展示</span>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品价格："
        prop="prdPrice"
      >
        <el-input
          style="width:130px;"
          v-model="formData.prdPrice"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="市场价格："
        prop="prdMarketPrice"
      >
        <el-input
          style="width:130px;"
          v-model="formData.prdMarketPrice"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="会员价："
        prop=""
      >
      </el-form-item>
    </el-form>
    <!-- 展开更多配置 -->
    <el-collapse
      v-model="activeNames"
      @change="handleChange1"
    >
      <el-collapse-item
        title="展开/收起更多配置"
        name="1"
      >
        <el-form
          :model="formData1"
          :rules="rules1"
          ref="form1"
          label-width="120px"
          class="more"
        >
          <el-form-item
            label-width="120px"
            label="最小限购数量："
            prop="limitBuyNum"
          >
            <el-input-number
              size="small"
              v-model="formData1.limitBuyNum"
              controls-position="right"
              :min="1"
            ></el-input-number>
            <span style="color: #999;">0或不填表示不限制购买数量</span>
          </el-form-item>
          <el-form-item
            label-width="120px"
            label="最大限购数量："
            prop="limitMaxNum"
          >
            <el-input-number
              size="small"
              v-model="formData1.limitMaxNum"
              controls-position="right"
              :min="1"
            ></el-input-number>
            <span style="color: #999;">0或不填表示不限制购买数量</span>
          </el-form-item>

          <el-form-item
            label-width="120px"
            label="成本价格："
            prop="costPrice"
          >
            <el-input
              style="width:130px;"
              v-model="formData.costPrice"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item
            label-width="120px"
            label="初始销量："
            prop="init"
          >
            <el-input-number
              size="small"
              v-model="formData1.init"
              controls-position="right"
              :min="1"
            ></el-input-number>
            <span style="color: #999;">设置后，您的用户看到的销量=初始销量+下单量，初始销量不计入统计。</span>
          </el-form-item>
          <el-form-item
            label-width="120px"
            label="商品规格编码："
            prop="specValName"
          >
            <el-input
              style="width:130px;"
              v-model="formData.specValName"
              size="small"
            ></el-input>
          </el-form-item>
        </el-form>
      </el-collapse-item>
    </el-collapse>
    <el-button
      @click="handleTest"
      type="success"
    >测试按钮</el-button>
  </div>
</template>
<script>
export default {
  name: 'priceInfo',
  data () {
    return {
      formData: {
        goodsNumber: '',
        prdPrice: '',
        prdMarketPrice: ''
      },
      rules: {
        goodsNumber: [
          { required: true, message: '请输入商品库存', trigger: 'change' }
        ],
        prdPrice: [
          { required: true, message: '请输入商品价格', trigger: 'change' }
        ]
      },
      activeNames: ['1'],
      formData1: {
        limitBuyNum: '',
        limitMaxNum: '',
        costPrice: '',
        specValName: '',
        init: ''
      },
      rules1: {

      }
    }
  },
  methods: {
    handleTest () {

    },
    handleChange (val) {

    },
    handleChange1 (val) {

    }
  }
}
</script>
<style scoped>
</style>
