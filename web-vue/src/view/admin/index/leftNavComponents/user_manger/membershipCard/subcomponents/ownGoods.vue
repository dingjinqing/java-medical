<template>
  <div class="ownGoodsDiv">
    <el-form
      :model="ruleForm"
      status-icon
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item class="ownGoodsItem">
        <div class="vipDiv">
          <el-checkbox v-model="ruleForm.powerOwnGoods">
            <span>{{ $t('memberCard.memberOwnGoods') }}</span>
          </el-checkbox>
          <span class="prompt">{{ $t('memberCard.buyPrompt') }}</span>
        </div>
        <div v-if="ruleForm.powerOwnGoods">
          <div
            class="noneBlockList"
            v-for="(item,index) in noneBlockVipArr"
            :key="index"
            @click="handleToAddGoods(index)"
          >
            <div class="noneBlockLeft">
              <img :src="loadAddSymbol()">
              {{item.name}}
            </div>
            <div
              v-if="item.num"
              class="noneBlockRight"
            >
              {{ item.info }}：{{item.num}}{{ item.unit }}
            </div>
          </div>

        </div>
      </el-form-item>
    </el-form>
    <!--选择商品弹窗-->
    <ChoosingGoods
      @resultGoodsIds='initGoodsId'
      :tuneUpChooseGoods='goodsDialogVisiable'
      :chooseGoodsBack='ruleForm.choosedGoodsId'
    ></ChoosingGoods>
    <!--选择商家,平台分类弹窗-->
    <AddingBusClassDialog
      :dialogVisible.sync="businessDialogVisible"
      :classFlag="currentClassType"
      :backDataArr="storeAndPlatformBackIds"
      @BusClassTrueArr="initStoreOrPlatformId"
    />

    <!--添加品牌弹窗-->
    <AddBrandDialog
      :callAddBrand.sync="brandDialogVisiable"
      :brandBackData="ruleForm.choosedBrandId"
      @handleToGetBackData="initBrandId"
    />
  </div>
</template>
<script>
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'),
    AddBrandDialog: () => import('@/components/admin/addBrandDialog')
  },
  props: {
    val: {
      type: Object,
      required: true
    }
  },
  computed: {
    ruleForm: {
      get () {
        return this.val
      },
      set () {
        this.$emit('input', this.ruleForm)
      }
    }
  },
  watch: {
    'ruleForm.powerOwnGoods': {
      handler (newName, oldName) {
        this.val.powerOwnGoods = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.choosedGoodsId': {
      handler (newName, oldName) {
        this.val.choosedGoodsId = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.choosedStoreId': {
      handler (newName, oldName) {
        this.val.choosedStoreId = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.choosedPlatformId': {
      handler (newName, oldName) {
        this.val.choosedStoreId = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.choosedBrandId': {
      handler (newName, oldName) {
        this.val.choosedBrandId = newName
        this.ruleForm = this.val
      },
      immediate: true
    }
  },
  created () {
    this.noneBlockVipArr = this.$t('memberCard.noneBlockVipArr')
    this.initGoodsId(this.ruleForm.choosedGoodsId)
    this.initStoreId(this.ruleForm.choosedStoreId)
    this.initPlatformId(this.ruleForm.choosedPlatformId)
    this.initBrandId(this.ruleForm.choosedBrandId)
  },
  data () {
    return {
      currentClassType: null, // 针对商家和平台分类
      goodsType: 0,
      storeType: 1,
      platformType: 2,
      brandType: 3,
      goodsDialogVisiable: true,
      businessDialogVisible: false, // 商家分类和平台分类
      brandDialogVisiable: false,
      storeAndPlatformBackIds: [], // 商家分类和平台分类回显数据
      noneBlockVipArr: [{ name: '', num: '' }]
    }
  },
  methods: {
    loadAddSymbol () {
      return this.$imageHost + '/image/admin/icon_jia.png'
    },
    handleToAddGoods (type) {
      console.log(type, typeof type)
      switch (type) {
        case this.goodsType:
          console.log('添加商品')
          this.showAddGoodsDialog()
          break
        case this.storeType:
          console.log('添加商家')
          this.showAddStoreDialog()
          break
        case this.platformType:
          console.log('添加平台')
          this.showAddPlatformDialog()
          break
        case this.brandType:
          console.log('添加品牌')
          this.showAddBrandDialog()
          break
      }
    },
    showAddGoodsDialog () {
      this.goodsDialogVisiable = !this.goodsDialogVisiable
      this.choosedGoodsBack = this.ruleForm.choosedGoodsId
    },
    showAddStoreDialog () {
      this.currentClassType = this.storeType
      this.businessDialogVisible = true
      this.storeAndPlatformBackIds = this.ruleForm.choosedStoreId
    },
    showAddPlatformDialog () {
      this.currentClassType = this.platformType
      this.businessDialogVisible = true
      this.storeAndPlatformBackIds = this.ruleForm.choosedPlatformId
    },
    showAddBrandDialog () {
      this.brandDialogVisiable = true
    },
    initGoodsId (idList) {
      console.log('选中商品', idList)
      this.ruleForm.choosedGoodsId = idList
      this.noneBlockVipArr[this.goodsType].num = idList.length
    },
    initStoreOrPlatformId (idList) {
      switch (this.currentClassType) {
        case this.storeType:
          this.initStoreId(idList)
          break
        case this.platformType:
          this.initPlatformId(idList)
          break
      }
    },
    initStoreId (idList) {
      this.ruleForm.choosedStoreId = idList
      this.noneBlockVipArr[this.storeType].num = idList.length
    },
    initPlatformId (idList) {
      this.ruleForm.choosedPlatformId = idList
      this.noneBlockVipArr[this.platformType].num = idList.length
    },
    initBrandId (idList) {
      this.ruleForm.choosedBrandId = idList.map(({ id }) => id)
      this.noneBlockVipArr[this.brandType].num = idList.length
    }
  }
}
</script>
<style scoped lang="scss">
.ownGoodsDiv {
  .ownGoodsItem {
    padding-left: 100px;
    .vipDiv {
      .prompt {
        color: #666;
      }
    }
    .noneBlockList {
      margin-bottom: 10px;
      display: flex;
      .noneBlockLeft {
        color: #5a8bff;
        cursor: pointer;
        border: 1px solid #ccc;
        background-color: #fff;
        margin-right: 20px;
        padding-left: 5px;
        width: 120px;
        text-align: left;
        line-height: 30px;
        height: 30px;
      }
      .noneBlockRight {
        color: #5a8bff;
        cursor: pointer;
        line-height: 30px;
      }
    }
  }
}
</style>
