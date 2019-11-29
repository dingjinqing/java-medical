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
            style="width: 170px;"
            @change="labelLevelChanged"
          />
          <span class="inputTip">{{$t('addAndUpdateGoodsLabel.labelLevelTip')}}</span>
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
                  :src="$imageHost+'/image/admin/new_preview_image/label/xiangqing.jpg'"
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
                  :src="$imageHost+'/image/admin/new_preview_image/label/shaixuan.jpg'"
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
              <div @click="addGoodsClicked" class="pointGoodsItemBtnWrap">
                <el-button size="small">+{{$t('addAndUpdateGoodsLabel.addGoods')}}</el-button>
                <span>已选{{selectedGoodsList.length}}件商品</span>
              </div>
              <div @click="tuneUpChooseSort=true" class="pointGoodsItemBtnWrap">
                <el-button  size="small">+{{$t('addAndUpdateGoodsLabel.addSort')}}</el-button>
                <span>已选{{selectedSortList.length}}件商家</span>
              </div>
              <div @click="tuneUpChooseCat=true" class="pointGoodsItemBtnWrap">
                <el-button size="small">+{{$t('addAndUpdateGoodsLabel.addCategory')}}</el-button>
                <span>已选{{selectedCatList.length}}件平台</span>
              </div>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="contentFooter">
      <el-button
        type="primary"
        size="small"
        @click="saveGoodsLabel"
      >{{$t('addAndUpdateGoodsLabel.save')}}</el-button>
    </div>
    <choosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="goodsResultChoosed"
      :chooseGoodsBack="selectedGoodsList"
    />
    <catSortDialog @BusClassTrueArr="sortResultChoosed" :classFlag="1" :backDataArr="selectedSortList" :dialogVisible.sync="tuneUpChooseSort"/>
    <catSortDialog @BusClassTrueArr="catResultChoosed" :classFlag="2" :backDataArr="selectedCatList" :dialogVisible.sync="tuneUpChooseCat"/>
  </div>
</template>

<script>
// api引入
import { isGoodsLabelNameOk, addGoodsLabel, updateGoodsLabel, getGoodsLabel } from '@/api/admin/goodsManage/goodsLabel/goodsLabel'
// 组件引入
import choosingGoods from '@/components/admin/choosingGoods'
import catSortDialog from '@/components/admin/addingBusClassDialog'
// 工具类引入
import { isStrBlank } from '@/util/typeUtil'
export default {
  name: 'addAndUpdateGoodsLabel',
  components: {choosingGoods, catSortDialog},
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
      tuneUpChooseGoods: false,
      // 关联的商家分类信息
      selectedSortList: [],
      tuneUpChooseSort: false,
      // 关联的平台分类信息
      selectedCatList: [],
      tuneUpChooseCat: false
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
      if (typeof this.goodsLabelData.levelOld !== 'number' || this.goodsLabelData.levelOld <= 0) {
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
    /* 商品弹出回调处理函数 */
    goodsResultChoosed (data) {
      if (data == null) {
        this.selectedGoodsList = []
      } else {
        this.selectedGoodsList = []
        data.forEach(item => {
          this.selectedGoodsList.push(item.goodsId)
        })
      }
    },
    sortResultChoosed (data) {
      this.selectedSortList = data
    },
    catResultChoosed (data) {
      this.selectedCatList = data
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
          this.selectedGoodsList = labelData.goodsIds
          this.selectedSortList = labelData.sortIds
          this.selectedCatList = labelData.catIds
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
        goodsIds: [],
        sortIds: [],
        catIds: []
      }
      if (this.goodsLabelData.isAll === 0) {
        params.goodsIds = this.selectedGoodsList
        params.sortIds = this.selectedSortList
        params.catIds = this.selectedCatList
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
  padding: 10px;
}
.content {
  background-color: white;
  padding: 10px;
  margin-bottom: 50px;
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
.pointGoodsItemBtnWrap{
  cursor: pointer;
  width: 200px;
}
.contentFooter {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
}
</style>
