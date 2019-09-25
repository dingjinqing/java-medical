<template>
  <div class="contentWrap">
    <div class="content">
      <el-form
        ref="goodsLabelFormRef"
        :model="goodsLabelData"
        :rules="goodsLabelRules"
        label-width="120px">
        <el-form-item label="标签名称："
          prop="name">
          <el-input ref="labelRef" v-model="goodsLabelData.name" size="small"  style="width: 170px;"/>
        </el-form-item>
        <el-form-item label="优先级："
                      prop="level">
          <el-input ref="levelRef" v-model="goodsLabelData.level" size="small"  style="width: 80px;"/>
        </el-form-item>
        <el-form-item label="前端应用模块：">
          <ul class="useModelUl">
            <li>
              <el-checkbox v-model="goodsLabelData.goodsDetail" :true-label="1" :false-label="0">商品详情页</el-checkbox>
            </li>
            <li><el-checkbox v-model="goodsLabelData.goodsList" :true-label="1" :false-label="0">商品列表</el-checkbox></li>
            <li>
              <el-checkbox v-model="goodsLabelData.goodsSelect" :true-label="1" :false-label="0">商品筛选页</el-checkbox>
            </li>
          </ul>
          <div v-if="goodsLabelData.goodsList === 1" class="useModelListPattern">
            <span><em style="color: red;">*</em>标签样式:</span>
            <div style="margin-top: 5px;display: flex;justify-content: space-around;">
              <div style="flex-grow: 1;text-align: center;">
                <div>图片1</div>
                <el-radio v-model="goodsLabelData.listPattern" :label="1">{{""}}</el-radio>
              </div>
              <div style="flex-grow: 1;text-align: center;">
                <div>图片2</div>
                <el-radio v-model="goodsLabelData.listPattern" :label="2">{{""}}</el-radio>
              </div>
              <div style="flex-grow: 1;text-align: center;">
                <div>图片3</div>
                <el-radio v-model="goodsLabelData.listPattern" :label="3">{{""}}</el-radio>
              </div>
              <div style="flex-grow: 1;text-align: center;">
                <div>图片4</div>
                <el-radio v-model="goodsLabelData.listPattern" :label="4">{{""}}</el-radio>
              </div>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="添加商品：">
          <span class="inputTip">选择需要添加商品标签的商品</span>
          <div class="goodsInfoWrap">
            <div>
              <el-radio v-model="goodsInfoRadio" :label="0">全部商品</el-radio>
              <el-radio v-model="goodsInfoRadio" :label="1">指定商品</el-radio>
            </div>
            <div v-if="goodsInfoRadio ===1">
              <div>
                <el-button @click="addGoodsClicked">+添加商品</el-button>
                <div v-if="selectedGoodsList.length > 0">
                  <el-table
                    :data="selectedGoodsList"
                    class="tableClass"
                    border
                    style="width: 100%">
                    <el-table-column align="center" prop="name" label="商品名称">
                      <template slot-scope="{row}">
                        <img style="width: 50px;height: 50px;float: left;" :src="row.goodsImg"/>
                        <div style="padding:10px;">
                          {{row.goodsName}}
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column align="center" prop="shopPrice" label="价格" width="100px" />
                    <el-table-column align="center" prop="goodsNumber" label="库存" width="100px" />
                    <el-table-column align="center" label="操作" width="100px">
                      <template slot-scope="{$index}">
                        <span class="operateSpan" @click="selectedGoodsDeleteItem($index)">删除</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <div>
                <el-button @click="addSortClicked">+添加商家分类</el-button>
                <div></div>
              </div>
              <div>
                <el-button @click="addCatClicked">+添加平台分类</el-button>
                <div></div>
              </div>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <choosingGoods :tuneUpChooseGoods="tuneUpChooseGoods" @resultGoodsDatas="chooseGoodsResult"/>
  </div>
</template>

<script>
// 组件引入
import choosingGoods from '@/components/admin/choosingGoods'

export default {
  name: 'addAndUpdateGoodsLabel',
  components: {choosingGoods},
  data () {
    return {
      isUpdate: false,
      // 修改或者新增的接口数据
      goodsLabelData: {
        name: null,
        level: null,
        goodsDetail: null,
        goodsList: null,
        goodsSelect: null,
        listPattern: null,
        isAll: null,
        goodsId: [],
        sortId: [],
        catId: []
      },
      goodsLabelRules: {
        name: [
          { required: true, message: '请输入标签名称', trigger: 'change' }
        ],
        level: [
          { required: true, message: '优先级', trigger: 'change' }
        ]
      },
      // 全部商品0，1指定商品或分类
      goodsInfoRadio: 0,
      selectedGoodsList: [],
      tuneUpChooseGoods: true,
      selectedSortList: [],
      selectedCatList: []
    }
  },
  methods: {
    /* 添加标签关联的商品信息 */
    addGoodsClicked () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
      console.log(this.selectedGoodsList)
    },
    /* 添加标签关联的商家分类信息 */
    addSortClicked () {

    },
    /* 添加标签关联的平台分类信息 */
    addCatClicked () {

    },
    /* 商品弹出回调处理函数 */
    chooseGoodsResult (data) {
      this.selectedGoodsList = data
    },
    /* 删除已选中的需要关联的商品某一项 */
    selectedGoodsDeleteItem (index) {
      this.selectedGoodsList.splice(index, 1)
    }
  },
  mounted () {
    if (this.$route.params.id !== undefined) {
      this.isUpdate = true
      this.goodsLabelData.id = this.$route.params.id
    }
  }
}
</script>

<style scoped>
  .contentWrap {
    padding: 10px 10px;
  }
  .content {
    background-color: white;
    padding: 10px 10px 20px 10px;
  }
  .inputTip {
    color: #999;
    margin-left: 15px;
  }
  .useModelUl{
    min-width: 100px;
  }
  .useModelUl li{
    padding: 5px 0px;
  }
  .useModelListPattern{
    width: 990px;
    margin-top: 5px;
    background: #f8f8f8;
    padding: 10px 15px 20px 10px;
    border: 1px solid #eee;
    border-radius: 5px;
  }
  .goodsInfoWrap{
    width: 550px;
    padding: 10px;
    border: 1px solid #eee;
    border-radius: 5px;
    background: #f8f8f8;
  }
  /deep/.tableClass th{
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .operateSpan{
    font-size: 16px;
    color: #5a8bff;
    cursor: pointer !important;
  }
</style>
