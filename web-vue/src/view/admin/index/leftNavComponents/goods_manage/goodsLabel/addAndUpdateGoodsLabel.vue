<template>
  <div class="contentWrap">
    <div class="content">
      <el-form
        ref="goodsLabelFormRef"
        :model="goodsLabelData"
        :rules="goodsLabelRules"
        label-width="120px"
      >
        <el-form-item
          :label="$t('addAndUpdateGoodsLabel.labelName')+'：'"
          prop="nameOld"
        >
          <el-input
            ref="nameRef"
            v-model="goodsLabelData.nameOld"
            size="small"
            style="width: 170px;"
            @change="labelNameChanged"
          />
          <span class="inputTip">{{$t('addAndUpdateGoodsLabel.labelNameTip')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('addAndUpdateGoodsLabel.labelLevel')+'：'"
          prop="levelOld"
        >
          <el-input
            ref="levelRef"
            v-model.number="goodsLabelData.levelOld"
            size="small"
            style="width: 80px;"
            @change="labelLevelChanged"
          />
          <span class="inputTip">{{$t('addAndUpdateGoodsLabel.labelNameTip')}}</span>
        </el-form-item>
        <el-form-item :label="$t('addAndUpdateGoodsLabel.webUseModel')+'：'">
          <ul class="useModelUl">
            <li>
              <el-checkbox
                v-model="goodsLabelData.goodsDetail"
                :true-label="1"
                :false-label="0"
              >{{$t('addAndUpdateGoodsLabel.goodsDetailPage')}}</el-checkbox>
              <el-popover
                placement="right-start"
                trigger="hover"
              >
                <el-image
                  :src="$imageHost+'/image/admin/share/goods_info_exapmle.jpg'"
                  fit="scale-down"
                  style="width:220px;height: 400px;"
                />
                <span
                  slot="reference"
                  style="color:#409EFF;cursor:pointer;"
                >{{$t('addAndUpdateGoodsLabel.exampleTip')}}</span>
              </el-popover>
            </li>
            <li>
              <el-checkbox
                v-model="goodsLabelData.goodsList"
                :true-label="1"
                :false-label="0"
              >{{$t('addAndUpdateGoodsLabel.goodsListPage')}}</el-checkbox>
            </li>
            <li>
              <el-checkbox
                v-model="goodsLabelData.goodsSelect"
                :true-label="1"
                :false-label="0"
              >{{$t('addAndUpdateGoodsLabel.goodsSelectPage')}}</el-checkbox>
              <el-popover
                placement="right-start"
                trigger="hover"
              >
                <el-image
                  :src="$imageHost+'/image/admin/share/goods_info_exapmle.jpg'"
                  fit="scale-down"
                  style="width:220px;height: 400px;"
                />
                <span
                  slot="reference"
                  style="color:#409EFF;cursor:pointer;"
                >{{$t('addAndUpdateGoodsLabel.exampleTip')}}</span>
              </el-popover>
            </li>
          </ul>
          <div
            v-if="goodsLabelData.goodsList === 1"
            class="useModelListPattern"
          >
            <span><em style="color: red;">*</em>{{$t('addAndUpdateGoodsLabel.labelStyle')+':'}}</span>
            <div style="margin-top: 5px;display: flex;justify-content: space-around;">
              <div style="flex-grow: 1;text-align: center;">
                <div>图片1</div>
                <el-radio
                  v-model="goodsLabelData.listPattern"
                  :label="1"
                >{{""}}</el-radio>
              </div>
              <div style="flex-grow: 1;text-align: center;">
                <div>图片2</div>
                <el-radio
                  v-model="goodsLabelData.listPattern"
                  :label="2"
                >{{""}}</el-radio>
              </div>
              <div style="flex-grow: 1;text-align: center;">
                <div>图片3</div>
                <el-radio
                  v-model="goodsLabelData.listPattern"
                  :label="3"
                >{{""}}</el-radio>
              </div>
              <div style="flex-grow: 1;text-align: center;">
                <div>图片4</div>
                <el-radio
                  v-model="goodsLabelData.listPattern"
                  :label="4"
                >{{""}}</el-radio>
              </div>
            </div>
          </div>
        </el-form-item>
        <el-form-item :label="$t('addAndUpdateGoodsLabel.addGoods')+'：'">
          <span class="inputTip">{{$t('addAndUpdateGoodsLabel.addGoodsTip')}}</span>
          <div class="goodsInfoWrap">
            <div>
              <el-radio
                v-model="goodsLabelData.isAll"
                :label="1"
              >{{$t('addAndUpdateGoodsLabel.allGoods')}}</el-radio>
              <el-radio
                v-model="goodsLabelData.isAll"
                :label="0"
              >{{$t('addAndUpdateGoodsLabel.pointGoods')}}</el-radio>
            </div>
            <div v-if="goodsLabelData.isAll ===0">
              <div>
                <el-button @click="addGoodsClicked">+{{$t('addAndUpdateGoodsLabel.addGoods')}}</el-button>
                <div v-if="selectedGoodsList.length > 0">
                  <el-table
                    :data="selectedGoodsList"
                    class="tableClass"
                    border
                    style="width: 100%"
                  >
                    <el-table-column
                      align="center"
                      prop="name"
                      :label="$t('addAndUpdateGoodsLabel.goodsName')"
                    >
                      <template slot-scope="{row}">
                        <img
                          style="width: 50px;height: 50px;float: left;"
                          :src="row.goodsImg"
                        />
                        <div style="padding:10px;">
                          {{row.goodsName}}
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      prop="shopPrice"
                      :label="$t('addAndUpdateGoodsLabel.shopPrice')"
                      width="100px"
                    />
                    <el-table-column
                      align="center"
                      prop="goodsNumber"
                      :label="$t('addAndUpdateGoodsLabel.goodsNumber')"
                      width="100px"
                    />
                    <el-table-column
                      align="center"
                      :label="$t('addAndUpdateGoodsLabel.operate')"
                      width="100px"
                    >
                      <template slot-scope="{$index}">
                        <span
                          class="operateSpan"
                          @click="selectedGoodsDeleteItem($index)"
                        >{{$t('addAndUpdateGoodsLabel.deleteOperate')}}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <div>
                <el-button @click="addSortClicked">+{{$t('addAndUpdateGoodsLabel.addSort')}}</el-button>
                <div></div>
              </div>
              <div>
                <el-button @click="addCatClicked">+{{$t('addAndUpdateGoodsLabel.addCategory')}}</el-button>
                <div></div>
              </div>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <choosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="chooseGoodsResult"
    />
    <div class="contentFooter">
      <el-button
        type="primary"
        @click="saveGoodsLabel"
      >{{$t('addAndUpdateGoodsLabel.save')}}</el-button>
    </div>
  </div>
</template>

<script>
// api引入
import { isGoodsLabelNameOk, addGoodsLabel, updateGoodsLabel, getGoodsLabel } from '@/api/admin/goodsManage/goodsLabel/goodsLabel'
// 组件引入
import choosingGoods from '@/components/admin/choosingGoods'
// 工具类引入
import { isStrBlank } from '@/util/goodsUtil'
export default {
  name: 'addAndUpdateGoodsLabel',
  components: { choosingGoods },
  watch: {
    lang () {
      this.goodsLabelRules.nameOld[0].message = this.$t('addAndUpdateGoodsLabel.labelNameRequired')
      this.goodsLabelRules.levelOld[0].message = this.$t('addAndUpdateGoodsLabel.labelLevelRequired')
    }
  },
  data () {
    return {
      isUpdate: false,
      // 修改或者新增的接口数据
      goodsLabelData: {
        id: null,
        nameOld: null,
        name: null,
        levelOld: null,
        level: null,
        goodsDetail: 0,
        goodsList: 0,
        listPattern: 1,
        goodsSelect: 0,
        isAll: 1
      },
      goodsLabelRules: {
        nameOld: [
          { required: true, message: this.$t('addAndUpdateGoodsLabel.labelNameRequired'), trigger: 'change' }
        ],
        levelOld: [
          { required: true, message: this.$t('addAndUpdateGoodsLabel.labelLevelRequired'), trigger: 'change' }
        ]
      },
      /* 以下数据再goodsLabelData.isAll 为0时才有效 */
      // 关联的商品信息
      selectedGoodsList: [],
      tuneUpChooseGoods: true,
      // 关联的商家分类信息
      selectedSortList: [],
      // 关联的平台分类信息
      selectedCatList: []
    }
  },
  methods: {
    /* 标签名称改变事件 */
    labelNameChanged () {
      if (isStrBlank(this.goodsLabelData.nameOld)) {
        this.$message.warning({ message: this.$t('addAndUpdateGoodsLabel.labelNameRequired') })
        this.goodsLabelData.nameOld = this.goodsLabelData.name
        this.$refs.nameRef.focus()
        return
      }
      let params = {
        id: this.goodsLabelData.id,
        goodsLabelName: this.goodsLabelData.nameOld,
        isUpdate: this.isUpdate
      }
      isGoodsLabelNameOk(params).then(res => {
        if (res.error !== 0) {
          this.$message.warning({ message: res.message })
          this.goodsLabelData.nameOld = this.goodsLabelData.name
          this.$refs.nameRef.focus()
        } else {
          this.goodsLabelData.name = this.goodsLabelData.nameOld
        }
      })
    },
    /* 标签优先级改变事件 */
    labelLevelChanged () {
      if (typeof this.goodsLabelData.levelOld !== 'number') {
        this.$message.warning({ message: this.$t('addAndUpdateGoodsLabel.labelLevelRequired') })
        this.goodsLabelData.levelOld = this.goodsLabelData.level
        this.$refs.labelRef.focus()
      } else {
        this.goodsLabelData.level = this.goodsLabelData.levelOld
      }
    },
    /* 添加标签关联的商品信息 */
    addGoodsClicked () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
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
    },
    /* 初始化关联商品信息 */
    _initSelectedList (labelData) {
      if (labelData.goodsViewList !== null && labelData.goodsViewList.length > 0) {
        this.selectedGoodsList = labelData.goodsViewList
      }
      if (labelData.sortList !== null && labelData.sortList.length > 0) {
        this.selectedSortList = labelData.sortList
      }
      if (labelData.catList !== null && labelData.catList.length > 0) {
        this.selectedCatList = labelData.catList
      }
    },
    /* 修改标签内容回显 */
    _initDataForUpdate (labelId) {
      getGoodsLabel(labelId).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
        }
        let labelData = res.content
        this.goodsLabelData.id = labelData.id
        this.goodsLabelData.name = labelData.name
        this.goodsLabelData.nameOld = labelData.name
        this.goodsLabelData.level = labelData.level
        this.goodsLabelData.levelOld = labelData.level
        this.goodsLabelData.goodsDetail = labelData.goodsDetail
        this.goodsLabelData.goodsList = labelData.goodsList
        this.goodsLabelData.listPattern = labelData.listPattern
        this.goodsLabelData.goodsSelect = labelData.goodsSelect
        this.goodsLabelData.isAll = labelData.isAll
        if (this.goodsLabelData.isAll === 0) {
          this._initSelectedList(labelData)
        }
      })
    },
    /* 验证数据正确性 */
    _validateGoodsLabelData () {
      if (isStrBlank(this.goodsLabelData.name)) {
        this.$message.warning({ message: this.$t('addAndUpdateGoodsLabel.labelNameRequired') })
        this.$refs.nameRef.focus()
        this.$refs.goodsLabelFormRef.validate()
        return false
      }

      if (typeof this.goodsLabelData.level !== 'number') {
        this.$message.warning({ message: this.$t('addAndUpdateGoodsLabel.labelLevelRequired') })
        this.$refs.nameRef.focus()
        this.$refs.goodsLabelFormRef.validate()
        return false
      }

      return true
    },
    /* 收集待交互数据 */
    _getFormData () {
      let params = {
        id: this.goodsLabelData.id,
        name: this.goodsLabelData.name,
        level: this.goodsLabelData.level,
        goodsDetail: this.goodsLabelData.goodsDetail,
        goodsList: this.goodsLabelData.goodsList,
        listPattern: this.goodsLabelData.listPattern,
        goodsSelect: this.goodsLabelData.goodsSelect,
        isAll: this.goodsLabelData.isAll,
        goodsId: [],
        sortId: [],
        catId: []
      }
      if (this.goodsLabelData.isAll === 0) {
        this.selectedGoodsList.forEach(item => params.goodsId.push(item.goodsId))
        this.selectedSortList.forEach(item => params.sortId.push(item.sortId))
        this.selectedCatList.forEach(item => params.catId.push(item.catId))
      }
      return params
    },
    /* 新增或修改标签 */
    saveGoodsLabel () {
      if (!this._validateGoodsLabelData()) {
        return
      }
      let params = this._getFormData()

      let execFun = this.isUpdate ? updateGoodsLabel : addGoodsLabel
      execFun(params).then(res => {
        if (res.error === 0) {
          this.$router.push({ name: 'label' })
        } else {
          this.$message.error({ message: res.message })
        }
      })
    }
  },
  mounted () {
    this.langDefault()

    if (this.$route.params.id !== undefined) {
      this.isUpdate = true
      this._initDataForUpdate(this.$route.params.id)
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
.useModelUl {
  min-width: 100px;
}
.useModelUl li {
  padding: 5px 0px;
}
.useModelListPattern {
  width: 990px;
  margin-top: 5px;
  background: #f8f8f8;
  padding: 10px 15px 20px 10px;
  border: 1px solid #eee;
  border-radius: 5px;
}
.goodsInfoWrap {
  width: 550px;
  padding: 10px;
  border: 1px solid #eee;
  border-radius: 5px;
  background: #f8f8f8;
}
/deep/.tableClass th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.operateSpan {
  font-size: 16px;
  color: #5a8bff;
  cursor: pointer !important;
}
.contentFooter {
  background: #f8f8fa;
  text-align: center;
  box-sizing: border-box;
  height: 60px;
  padding-top: 10px;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 2;
}
</style>
