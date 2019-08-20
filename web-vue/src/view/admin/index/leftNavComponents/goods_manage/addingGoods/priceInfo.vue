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
            <div
              class="form-item"
              v-for="(attr,index) in attrs"
              :key="`attr${index}`"
            >
              <div class="form-title">
                <el-input
                  style="width:120px"
                  size="small"
                  value=""
                  v-model="attr.pName"
                  placeholder="规格名"
                ></el-input>
                <span
                  class="delete"
                  @click="toDelete(index)"
                >×</span>
              </div>
              <ul class="form-list">
                <li
                  class="list"
                  v-for="(item,index2) in attr.spec"
                  :key="`item${index2}`"
                >
                  <el-input
                    size="small"
                    style="width:120px"
                    value=""
                    v-model="item.cName"
                    placeholder="规格值"
                  ></el-input>
                  <span
                    class="deleteVal"
                    @click="toDeletVal(index2)"
                  >×</span>
                </li>
                <li>
                  <el-button
                    type="text"
                    @click="addSpecVal"
                  >添加规格值</el-button>
                </li>
              </ul>
            </div>
          </section>
          <section class="addSpec">
            <el-button
              @click="addItem"
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
          <section>
            <!-- 规格价格 -->
            <table
              class="table-sku"
              border="1px solid #ccc"
            >
              <thead>
                <tr>
                  <th
                    align="center"
                    v-for="(list,index) in tableData"
                    :key="`list${index}`"
                  >{{list['pName']}}</th>
                  <th align="center">价格(元)</th>
                  <th align="center">成本价格(元)</th>
                  <th align="center">库存</th>
                  <th align="center">规格编码</th>
                  <th align="center">规格图片</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(row,index) in rows"
                  :key="`row${index}`"
                >
                  <td
                    v-show="!((row-1)%item['rowspan'])"
                    v-for="(item,index2) in tableData"
                    :rowspan="item['rowspan']"
                    :key="`item${index2}`"
                  >
                    {{item|getName(row)}}

                  </td>

                  <td>
                    <input
                      type="text"
                      v-model="tableList[row-1]['price']"
                    >
                  </td>
                  <td>
                    <input
                      type="text"
                      v-model="tableList[row-1]['costPrice']"
                    >
                  </td>
                  <td>
                    <input
                      type="text"
                      v-model="tableList[row-1]['stock']"
                    >
                  </td>
                  <td>
                    <input
                      type="text"
                      v-model="tableList[row-1]['code']"
                    >
                  </td>
                  <td>
                    <!-- {{row}}{{tableList}} -->
                  </td>
                </tr>
              </tbody>
            </table>
          </section>
          <!-- 批量设置 -->
          <section class="batchSetting">
            <span>批量设置：</span>
            <el-button type="text">价格</el-button>
            <el-button type="text">成本价格</el-button>
            <el-button type="text">库存</el-button>
            <el-button type="text">规格图片</el-button>
            <el-button @click="test">测试数据</el-button>
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
          :disabled="isShowproSpec"
        ></el-input-number>
        <span style="color: #999;">设置了规格库存商品库存将失效，不在前端展示</span>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品价格："
        prop="prdPrice"
      >
        <el-input
          :disabled="isShowproSpec"
          style="width:130px;"
          v-model="formData.prdPrice"
          size="small"
        ></el-input>
        <span style="color: #999;">设置了规格价格商品价格将失效，不在前端展示</span>
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

  filters: {
    getName: function (obj, index) {
      if (obj) {
        let r = parseInt((index - 1) / obj['rowspan'])
        let l = obj['specLen'] || 1
        let key = r % l
        return obj['spec'] && obj['spec'][key] && obj['spec'][key]['cName']
      }
    }
  },
  computed: {
    // 获取价格
    getPrice () {
      return this.formData
    },
    // 表格数据
    tableData () {
      let attrs = this.attrs
      let len = attrs.length
      if (len === 0) {
        return
      }
      let tData = []
      // 初始化tableData
      for (let i = 0; i < len; i++) {
        let row = {}
        row['pName'] = attrs[i]['pName']
        row['spec'] = []
        row['price'] = {}
        row['costPrice'] = {}
        row['stock'] = {}
        row['code'] = {}
        row['img'] = {}
        let len2 = attrs[i]['spec'].length
        let specLen = 0
        for (let j = 0; j < len2; j++) {
          let spe = {}
          let cName = attrs[i]['spec'][j]['cName']
          if (!cName) {
            continue
          }
          ++specLen
          spe['cName'] = cName
          row['spec'].push(spe)
        }
        row['specLen'] = specLen
        tData.push(row)
      }
      // 获取rowspan
      for (let k = 0, len3 = tData.length; k < len3; k++) {
        let rowspan = 1
        for (let k1 = k + 1; k1 < len3; k1++) {
          let kSpecLen = tData[k1]['specLen'] || 1
          rowspan *= kSpecLen
        }
        tData[k]['rowspan'] = rowspan
      }
      return tData
    },
    rows () {
      if (!this.tableData) {
        return
      }
      let rows = 1
      let tableData = this.tableData
      let len = tableData.length
      for (let i = 0; i < len; i++) {
        let specLen = tableData[i]['specLen'] || 1
        rows *= specLen
      }
      // 每条rowspan都为1情况
      if (rows === 1) {
        return tableData[0]['spec'].length
      }
      return rows
    },
    tableList () {
      if (!this.tableData) {
        return
      }
      let rows = this.rows
      let tList = []
      let srcData = this.tableData
      // console.log(srcData);
      for (let i = 0; i < rows; i++) {
        let listItem = {}
        // 构建动态项
        for (let j = 0; j < srcData.length; j++) {
          // 构造第一类目
          let key = srcData[j]['pName']
          let rowspan = srcData[j]['rowspan']
          let len = srcData[j]['specLen']
          if (!len) {
            continue
          }
          let spec = srcData[j]['spec']
          let index = parseInt(i / rowspan) % len
          listItem[key] = spec[index]['cName']
        }
        // 构建固定项(价格price,成本价格costPrice,库存stock,规格编码code,规格图片img)
        listItem['price'] = ''
        listItem['costPrice'] = ''
        listItem['stock'] = ''
        listItem['code'] = ''
        listItem['img'] = ''
        tList.push(listItem)
      }
      return tList
    }
  },
  data () {
    return {

      attrs: [],
      obj: {},
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
      counter: [],
      isShowOne: true,
      name: '',
      num: 0,
      isShowBtn: true,
      isShowproSpec: false
    }
  },
  methods: {
    test () {
      console.log(this.tableList)
    },

    handleGetPrdNumber (val) {

    },
    handleChange1 (val) {

    },
    handleChange2 (val) {

    },
    toDeletVal (index2) {
      console.log(index2)
      console.log(this.attrs)
      this.attrs.forEach(item => {
        item.spec.splice(index2, 1)
      })
    },
    hide () {
      this.isShowSpecPrice = false
      this.isShowWrap = false
      this.showAddSpec = true
    },
    sendSpecName (val) {
      console.log(val)
      this.name = val
    },
    handleShowCon () {
      this.isShowproSpec = true
      this.isShowBtn = false
    },
    addSpecVal () {
      this.obj.spec.push({ cName: '' })
    },
    // 添加一个规格选项
    addItem () {
      let obj = {
        pName: '',
        rowspan: 1,
        spec: [
          { cName: '' }
        ]
      }
      this.obj = obj
      this.attrs.push(this.obj)
    },
    // 删除规格名
    toDelete (index) {
      this.attrs.splice(index, 1)
      if (this.attrs.length === 0) {
        this.isShowBtn = true
        this.isShowproSpec = false
        this.isShowproSpec = false
      }
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
/*table*/
table {
  border: 0;
}
table.table-sku {
  width: 100%;
  background-color: #fff;
  text-align: left;
}
table {
  border-collapse: collapse;
  border-spacing: 0;
}
table.table-sku td {
  border: 1px solid #e5e5e5;
  padding: 8px;
}
table.table-sku td input {
  padding: 10px;
  border: 1px solid #ccc;
}
/**/
.form-title {
  background: #f8f8f8;
  padding: 10px;
  position: relative;
}
.form-title .label {
  color: #999;
}
.form-title .delete {
  width: 20px;
  height: 20px;
  line-height: 20px;
  border: 1px solid #ccc;
  border-radius: 50%;
  position: absolute;
  right: 15px;
  top: 50%;
  margin-top: -10px;
  text-align: center;
  color: #fff;
  background: #ccc;
  cursor: pointer;
}
.list {
  position: relative;
}
.deleteVal {
  width: 20px;
  height: 20px;
  line-height: 20px;
  border: 1px solid #ccc;
  border-radius: 50%;
  position: absolute;
  right: 5px;
  top: 50%;
  margin-top: -10px;
  text-align: center;
  color: #fff;
  background: #ccc;
  cursor: pointer;
}
.form-title input {
  background: #fff;
  border: 1px solid #ccc;
  padding: 10px;
}
.form-list {
  padding: 10px;
  margin-top: 0;
}
.form-list li {
  display: inline-block;
  margin-top: 10px;
}
.spec-item {
  width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.form-list,
.form-title {
  text-align: left;
}
.form-list input {
  background: #fff;
  margin-right: 10px;
  border: 1px solid #ccc;
  padding: 10px;
}
.form-group {
  border: 1px solid #ccc;
  padding: 10px;
}
.form-table {
  margin-top: 50px;
}
.form-btn-group {
  margin-top: 20px;
  background: #f8f8f8;
  padding: 10px;
}
.stock-title,
.form-h {
  height: 40px;
  line-height: 40px;
}
</style>
