<template>
  <div class="discountRoot">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        :label="$t('memberCard.memberPower')"
        prop="discount"
        class="discountItem first"
      >
        <div class="discountDiv equity">
          <el-checkbox v-model="ruleForm.powerDiscount">
            {{ $t('memberCard.memberDiscount') }}
          </el-checkbox>
          <el-input-number
            v-model="ruleForm.discount"
            size="small"
            :precision="2"
            :controls="false"
          >
          </el-input-number>
          <span>{{ $t('memberCard.discount') }}</span>
        </div>
      </el-form-item>

      <el-form-item class="discountItem">
        <div class="disCountGoodsDiv">
          <div class="disCountGoodsIntro">{{ $t('memberCard.memberDiscountGoods') }}</div>
          <el-radio
            v-model="ruleForm.discountGoodsType"
            label='1'
          >
            {{ $t('memberCard.allGoods') }}
          </el-radio>
          <el-radio
            v-model="ruleForm.discountGoodsType"
            label='0'
          >
            {{ $t('memberCard.assignGoods') }}
          </el-radio>
        </div>
        <!-- Start: 点击指定商品后显示模块 -->
        <div
          v-if="ruleForm.discountGoodsType==='0'"
          class="noneBlock"
        >
          <div
            v-for="(item,index) in noneBlockDiscArr"
            :key="index"
            class="noneBlockList"
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
              {{ item.info }}：{{item.num}}{{ item.unit  }}
            </div>
          </div>
        </div>
      </el-form-item>
      <!-- End: 点击指定商品后显示模块 -->
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
      set (val) {
        this.$emit('input', this.ruleForm)
      }
    }
  },
  watch: {
    'val': {
      handler (newName, oldName) {
        console.log(newName)
        this.noneBlockDiscArr[this.goodsType].num = this.val.choosedGoodsId.length
        this.noneBlockDiscArr[this.storeType].num = this.val.choosedStoreId.length
        this.noneBlockDiscArr[this.platformType].num = this.val.choosedPlatformId.length
        this.noneBlockDiscArr[this.brandType].num = this.val.choosedBrandId.length
      },
      deep: true
    },
    'ruleForm.discount': {
      handler (newName, oldName) {
        this.val.discount = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.powerDiscount': {
      handler (newName, oldName) {
        this.val.powerDiscount = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.discountGoodsType': {
      handler (newName, oldName) {
        this.val.discountGoodsType = newName
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
        this.val.choosedPlatformId = newName
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
  data () {
    var validateDiscount = (rule, value, callback) => {
      if (!this.checkDiscountVal(value) && this.ruleForm.powerDiscount) {
        callback(new Error('会员折扣请输入0-10的数字'))
      } else {
        callback()
      }
    }
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
      noneBlockDiscArr: [{ name: '', num: '' }],
      rules: {
        discount: [
          { validator: validateDiscount, required: true, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.noneBlockDiscArr = this.$t('memberCard.noneBlockDiscArr')
  },
  mounted () {
    this.$on('checkRule', () => {
      this.$refs.ruleForm.validate((valid) => {
        console.log(valid)
        this.ruleForm.valid = valid
        if (this.ruleForm.powerDiscount && !valid) {
          this.$message.warning('会员折扣请输入0-10的数字')
        }
      })
    })
  },

  methods: {
    checkDiscountVal (val) {
      // 0-10允许两位小数的数字
      return /^((10)||((?:[0-9]|0[1-9])(\.\d{1,2})?))$/.test(val)
    },
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
      this.ruleForm.choosedGoodsId = idList
      console.log(idList.length)
      this.noneBlockDiscArr[this.goodsType].num = idList.length
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
      this.noneBlockDiscArr[this.storeType].num = idList.length
    },
    initPlatformId (idList) {
      this.ruleForm.choosedPlatformId = idList
      this.noneBlockDiscArr[this.platformType].num = idList.length
      console.log(this.noneBlockDiscArr[this.platformType].num)
      console.log(this.noneBlockDiscArr[this.storeType].num)
    },
    initBrandId (idList) {
      this.ruleForm.choosedBrandId = idList.map(({ id }) => id)
      console.log(this.ruleForm.choosedBrandId)
      this.noneBlockDiscArr[this.brandType].num = idList.length
      console.log(this.noneBlockDiscArr[this.brandType].num)
    }
  }
}
</script>
<style scoped lang="scss">
.discountRoot {
  .discountItem {
    padding-left: 100px;
    .discountDiv {
      display: flex;
      height: 40px;
      justify-content: flex-start;
      align-items: center;
      /deep/ .el-input-number {
        width: 120px;
        margin-left: 15px;
        /deep/ .el-input {
          width: 100%;
        }
      }

    }
    .equity {
      span {
        margin-left: 13px;
      }
      /deep/ .el-radio {
        margin-right: 17px;
      }
      /deep/ .el-input {
        width: 20%;
        .el-input__inner {
          width: 100%;
        }
      }
    }
    .disCountGoodsDiv {
      display: flex;
      align-items: center;
      .disCountGoodsIntro {
        margin-right: 25px;
      }
    }
    .noneBlock {
      .noneBlockList {
        margin-bottom: 10px;
        display: flex;
        .noneBlockLeft {
          line-height: 30px;
          height: 30px;
          width: 120px;
          text-align: left;
          color: #5a8bff;
          border: 1px solid #ccc;
          background: #fff;
          padding-left: 5px;
          margin-right: 20px;
          cursor: pointer;
        }
        .noneBlockRight {
          color: #5a8bff;
          cursor: pointer;
          height: 30px;
          line-height: 30px;
        }
      }
    }
  }
  .first {
    /deep/ .el-form-item__label {
      margin-left: -8px;
    }
  }
}
</style>
