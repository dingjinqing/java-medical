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
          v-show="isShowWrap"
        >
          <specName
            @hide="hide"
            @sendSpecName="sendSpecName"
          />
          <specValue />
          <section
            v-for="(item,index) in counter"
            :key="index"
          >
            <section
              class="GoodsSpec"
              v-if="isShowOne"
            >
              <section class="specName">
                <specName @row="row" />
              </section>
              <section class="specValName">
                <specValue />
              </section>
            </section>
          </section>

          <section class="addSpecs">
            <el-button
              class="add_one"
              @click="addSpecOptions"
            >添加规格选项</el-button>
          </section>
        </section>
      </el-form-item>
      <el-form-item
        v-if="isShowSpecPrice"
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
              ...
            </template>

            <el-table-column
              :label="name"
              width="180"
              prop='value'
            >
            </el-table-column>
            <el-table-column
              label="价格（元）"
              width="180"
              prop="price"
            >
              <template slot-scope="scope">
                <slot
                  :row="scope.row"
                  :$index="scope.$index"
                >
                  <el-input
                    v-model="scope.row.price"
                    size="small"
                  ></el-input>
                </slot>
              </template>
            </el-table-column>
            <el-table-column
              label="成本价格（元）"
              width="180"
            >
              <template slot-scope="scope">
                <slot
                  :row="scope.row"
                  :$index="scope.$index"
                >
                  <el-input size="small"></el-input>
                </slot>
              </template>
            </el-table-column>
            <el-table-column
              label="库存"
              width="180"
            >
              <template slot-scope="scope">
                <slot
                  :row="scope.row"
                  :$index="scope.$index"
                >
                  <el-input-number
                    v-model="num"
                    size="small"
                    controls-position="right"
                    @change="handleChange2"
                    :min="0"
                    :max="1000000000000"
                    label="库存"
                  ></el-input-number>
                </slot>
              </template>

            </el-table-column>
            <el-table-column
              label="规格编码"
              width="180"
            >
              <template slot-scope="scope">
                <slot
                  :row="scope.row"
                  :$index="scope.$index"
                >
                  <el-input size="small"></el-input>
                </slot>
              </template>
            </el-table-column>
            <el-table-column
              label="规格图片"
              width="180"
            >
              <template slot-scope="scope">
                <slot
                  :row="scope.row"
                  :$index="scope.$index"
                >
                  <el-input size="small"></el-input>
                </slot>
              </template>
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
import specValue from './specValue'
import specName from './specName'
export default {
  name: 'priceInfo',
  components: { specValue, specName },
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

        init: ''
      },
      rules1: {

      },
      showAddSpec: true,
      isShowWrap: false,
      tableData: [{
        value: '001',
        price: '',
        edit: 0
      }],
      specName: '',
      isShowSpecPrice: false,
      specValName: '',
      counter: [],
      isShowOne: true,
      name: '',
      num: 0
    }
  },
  methods: {
    handleTest () {

    },
    handleChange (val) {

    },
    handleChange1 (val) {

    },
    handleChange2 (val) {

    },
    handleAddSpec () {
      this.showAddSpec = false
      this.isShowSpecPrice = true
      this.isShowWrap = true
    },

    addSpecOptions () {
      // this.counter.push({})
      // console.log(this.counter)
      // this.isShowOne = true
    },
    hide () {
      this.isShowSpecPrice = false
      this.isShowWrap = false
      this.showAddSpec = true
    },
    row () {

    },
    sendSpecName (val) {
      console.log(val)
      this.name = val
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
  border: 1px solid red;
  padding: 10px;
  color: #333;
  min-width: 100%;
}
.batchSetting {
  display: flex;
  justify-content: center;
  align-items: center;
}
.specName {
  background-color: #f8f8f8;
}
.del_img {
  position: relative;
}
.addSpecs {
  background-color: #f8f8f8;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.add_one {
  background: #fff;
  color: #333;
  border: 1px solid #ccc;
  width: 120px;
  height: 30px;
  margin-top: 10px;
}
.GoodsSpec {
  margin-bottom: 3px;
}
</style>
