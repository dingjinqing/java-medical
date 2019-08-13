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
        <el-button
          v-show="showAddSpec"
          class="add-spec add_all"
          @click="handleAddSpec"
        >添加规格</el-button>
        <section
          class="show-spec"
          v-show="!showAddSpec"
        >
          <section class="GoodsSpec">
            <section class="specName">
              <span>规格名：</span>
              <el-tag
                :key="tag"
                v-for="tag in tagsList"
                closable
                :disable-transitions="false"
                @close="handleClose(tag)"
              >
                {{tag}}
              </el-tag>
              <el-input
                class="input-new-tag"
                v-if="inputVisible"
                v-model="inputValue"
                ref="saveTagInput"
                size="small"
                @keyup.enter.native="handleInputConfirm"
                @blur="handleInputConfirm"
              >
              </el-input>
              <el-button
                v-else
                class="button-new-tag"
                size="small"
                @click="showInput"
              >+ New Tag</el-button>
            </section>
            <section class="specValName">
              <span>规格值：</span>
              <el-tag type="info">标签三</el-tag>
            </section>
          </section>
        </section>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="规格价格："
        prop=""
      >
        <section
          class="show-spec"
          v-show="!showAddSpec"
        >
          <el-table
            :data="tableData"
            style="width: 100%"
          >
            <template slot="empty">

            </template>

            <el-table-column
              label=""
              width="180"
            >
            </el-table-column>
            <el-table-column
              label="价格（元）"
              width="180"
            >
            </el-table-column>
            <el-table-column
              label="成本价格（元）"
              width="180"
            >
            </el-table-column>
            <el-table-column
              label="库存"
              width="180"
            >
            </el-table-column>
            <el-table-column
              label="规格编码"
              width="180"
            >
            </el-table-column>
            <el-table-column
              label="规格图片"
              width="180"
            >
            </el-table-column>
          </el-table>
          <section class="batchSetting">
            <span>批量设置：</span>
            <el-button type="text">价格</el-button>
            <el-button type="text">成本价格</el-button>
            <el-button type="text">库存</el-button>
            <el-button type="text">规格图片</el-button>
          </section>
        </section>
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

      },
      showAddSpec: false,
      tableData: [],
      tagsList: [],
      inputVisible: false,
      inputValue: ''
    }
  },
  methods: {
    handleTest () {

    },
    handleChange (val) {

    },
    handleChange1 (val) {

    },
    handleAddSpec () {
      this.showAddSpec = false
    },
    handleClose (tag) {
      this.tagsList.splice(this.tagsList.indexOf(tag), 1)
    },
    showInput () {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm () {
      let inputValue = this.inputValue
      if (inputValue) {
        this.tagsList.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    }
  }
}
</script>
<style scoped>
.add-spec {
  background: #fff;
  color: #333;
  border: 1px solid #ccc;
  width: 120px;
  height: 30px;
  line-height: 10px;
}
.show-spec {
  border: 1px solid #ccc;
  padding: 10px;
  color: #333;
  min-width: 745px;
}
.batchSetting {
  display: flex;
  justify-content: center;
  align-items: center;
}
.specName {
  background-color: #f8f8f8;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
