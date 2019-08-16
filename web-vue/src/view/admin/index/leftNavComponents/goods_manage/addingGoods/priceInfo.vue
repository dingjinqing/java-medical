<template>
  <div class="priceInfo">
    <el-form
      :model="formData"
      :rules="rules"
      ref="form"
      label-width="120px"
      class="inventoryAndPrice"
    >
      <!-- 商品规格 -->
      <el-form-item
        label-width="120px"
        label="商品规格："
        prop=""
      >
        <section class="btn">
          <el-button
            size="small"
            @click="handleShowCon"
            v-if="isShowBtn"
          >添加规格</el-button>
        </section>
        <section
          class="proSpec"
          v-if="isShowproSpec"
        >
          <section class="conta">
            <section
              class="group"
              v-for="(item,index) in specNameList"
              :key="index"
            >

              <section class="name">
                <span>规格名：</span>
                <el-input
                  v-model="item.name"
                  size="small"
                  style="width:100px"
                ></el-input>
                <img
                  @click="delRow(index)"
                  class="right_top"
                  src="../../../../../../assets/image/admin/icon_delete.png"
                  alt="del"
                >
              </section>
              <section class="children">
                <span>规格值：</span>
                <el-input
                  v-for=" (ipt,i) in item.children"
                  :key="i"
                  size="small"
                  style="width:100px"
                  v-model="ipt.val"
                  ref="saveTagInput"
                  @keyup.enter.native="handleConfirm"
                  @blur="handleConfirm"
                >
                </el-input>
                <el-button
                  type="text"
                  @click="addVal(index)"
                >添加规格值</el-button>
              </section>
            </section>
          </section>
          <section class="addSpec">
            <el-button
              @click="handleAddSpec"
              size="small"
            >添加规格选项</el-button>
          </section>
        </section>
      </el-form-item>
      <!-- 规格价格 -->
      <el-form-item
        v-if="isShowproSpec"
        label-width="120px"
        label="规格价格："
        prop=""
      >

        <section class="specPrice">
          <el-table
            ref="filterTable"
            :data="tableData"
            style="width: 100%"
          >
            <template slot="empty">
              ...
            </template>
            <el-table-column
              prop=""
              label=""
              width="180"
            >

            </el-table-column>
            <el-table-column
              prop="prdPrice"
              label="价格(元)"
              width="180"
            >
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.status"
                  v-model="scope.row.prdPrice"
                ></el-input>
                <span v-else>{{scope.row.prdPrice}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="prdCostPrice"
              label="成本价格(元)"
              width="180"
            >
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.status"
                  v-model="scope.row.prdCostPrice"
                ></el-input>
                <span v-else>{{scope.row.prdCostPrice}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="prdNumber"
              label="库存"
              width="180"
            >
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.status"
                  v-model="scope.row.prdNumber"
                ></el-input>
                <span v-else>{{scope.row.prdNumber}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="prdCoding"
              label="规格编码"
              width="180"
            >
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.status"
                  v-model="scope.row.prdCoding"
                ></el-input>
                <span v-else>{{scope.row.prdCoding}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="img"
              label="规格图片"
              width="180"
            >
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.status"
                  v-model="scope.row.img"
                ></el-input>
                <span v-else>{{scope.row.img}}</span>
              </template>
            </el-table-column>
          </el-table>

          <!-- 批量设置 -->
          <section class="batchSetting">
            <span>批量设置：</span>
            <el-button type="text">价格</el-button>
            <el-button type="text">成本价格</el-button>
            <el-button type="text">库存</el-button>
            <el-button type="text">规格图片</el-button>
            <el-button
              type="text"
              @click="addOne"
            >添加一行</el-button>
          </section>
        </section>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品库存："
        prop="prdNumber"
      >
        <el-input-number
          size="small"
          v-model="formData.prdNumber"
          controls-position="right"
          @change="handleGetPrdNumber"
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

  </div>
</template>
<script>

export default {
  name: 'priceInfo',
  computed: {
    getPrice () {
      return this.formData
    }
  },
  data () {
    return {
      formData: {
        prdNumber: '',
        prdPrice: '',
        prdMarketPrice: ''
      },
      rules: {
        prdNumber: [
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

      tableData: [],
      specName: '',
      specValName: '',
      counter: [],
      isShowOne: true,
      name: '',
      num: 0,
      specNameList: [],
      isShowBtn: true,
      isShowproSpec: false,
      vals: [],
      inputVisible: false,
      inputValue: ''

    }
  },
  methods: {
    addOne () {
      this.tableData.map(item => {
        if (item.status) {
          item.status = 0
        }
        return item
      })
      this.tableData.push({
        name: '', age: '', status: 1
      })
    },

    handleGetPrdNumber (val) {

    },
    handleChange1 (val) {

    },
    handleChange2 (val) {

    },
    handleAddSpec () {
      this.specNameList.push({
        name: ``,
        children: [{
          val: ``
        }]
      })
    },

    addSpecOptions () {

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
    },
    handleShowCon () {
      this.isShowproSpec = true
      this.isShowBtn = false
      this.specNameList.push({
        name: ``,
        children: [{
          val: ``
        }]
      })
    },
    delRow (index) {
      this.specNameList.splice(index, 1)
      if (this.specNameList.length === 0) {
        this.isShowBtn = true
        this.isShowproSpec = false
        this.isShowproSpec = false
      }
    },
    handleConfirm () {
      console.log(this.specNameList)
    },
    addVal (index) {
      console.log(index)
      let res = this.specNameList.find((item, i) => i === index)
      console.log(res)
      res.children.push({
        val: ``
      })
      // this.specNameList.forEach(item => {
      //   item.children.push({
      //     val: ``
      //   })
      // })
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
.proSpec {
  border: 1px solid #ccc;
  padding: 10px;
  color: #333;
  min-width: 100%;
}
.specPrice {
  border: 1px solid #ccc;
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
.addSpec {
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
.tb-edit .el-input {
  display: none;
}
.tb-edit .current-row .el-input {
  display: block;
}
.tb-edit .current-row .el-input + span {
  display: none;
}
.right_top {
  position: absolute;
  left: 150px;
  cursor: pointer;
}
.wrapper {
  position: relative;
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
