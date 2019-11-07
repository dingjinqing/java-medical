<template>
  <div class="container">

    <!-- 表单 -->
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="120px"
        :label-position="'right'"
      >
        <el-form-item
          :label="$t('seckill.activityName')"
          prop="name"
        >
          <el-col :span="8">
            <el-input
              v-model="form.name"
              style="width: 165px"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.goodsName')"
          prop="goodsId"
        >
          <el-button
            :disabled="this.isEdite"
            @click="showChoosingGoods"
            class="el-icon-plus"
          >{{ $t('seckill.select') }}</el-button>
          <el-col :span="2">
            <el-input
              :disabled="true"
              v-model="goodsRow.goodsName"
              v-if="goodsRow.ischecked"
            ></el-input>
            <el-input
              :disabled="true"
              v-if="false"
              v-model="form.goodsId"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.validDate')"
          prop="validity"
        >
          <el-date-picker
            :disabled="this.isEdite"
            v-model="form.validity"
            type="datetimerange"
            :range-separator="$t('seckill.to')"
            :start-placeholder="$t('seckill.startTime')"
            :end-placeholder="$t('seckill.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.limitNum')"
          prop="limitAmount"
        >
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.limitAmount"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <span style="color: #999; margin-left: 10px;">{{ $t('seckill.limitTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.orderAfter')"
          prop="limitPaytime"
        >
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.limitPaytime"
            controls-position="right"
            :min="5"
          ></el-input-number>
          <span>{{ $t('seckill.orderTip') }}</span>
          <p style="color: #999;">{{ $t('seckill.langTip') }}</p>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.seckillPrice')"
          prop="seckillPrices"
        >
          <el-table
            border
            :data="tableContent"
          >
            <el-table-column
              :label="$t('seckill.specifications')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('seckill.shopPrice')"
              prop="shopPrice"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('seckill.prdPrice')"
              prop="prdPrice"
              align="center"
            >
              <template slot-scope="scope">
                <el-input
                  :disabled="isEdite"
                  v-model="scope.row.prdPrice"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('seckill.goodsNumber')"
              prop="goodsNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('seckill.prdNumber')"
              prop="prdNumber"
              align="center"
            >
              <template slot-scope="scope">
                <el-input
                  :disabled="isEdite"
                  v-model="scope.row.prdNumber"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('seckill.goodsSaleNum')"
              prop="goodsSaleNum"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('seckill.prdTypeNum')"
              prop="prdTypeNum"
              align="center"
            ></el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.freeFreight')"
          prop="freeFreight"
        >
          <el-radio
            :disabled="this.isEdite"
            v-model="form.freeFreight"
            :label="1"
          >{{ $t('seckill.freeShipping') }}</el-radio>
          <el-radio
            v-model="form.freeFreight"
            :label="0"
          >{{ $t('seckill.template') }}</el-radio>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.stock')"
          prop="stock"
        >
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.stock"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <span>{{ $t('seckill.initTip') }}</span>
        </el-form-item>

        <!-- 收起、展开更多配置 -->
        <div
          @click="handleToChangeArror"
          style="padding: 0 0 30px 50px"
        >
          <div
            v-if="arrorFlag"
            style="color:rgb(90, 139, 255);cursor:pointer"
          >
            {{ $t('seckill.openConfigure') }}&nbsp;<img :src="ArrowArr[0].img_1">
          </div>
          <div
            v-if="!arrorFlag"
            style="color:rgb(90, 139, 255);cursor:pointer"
          >
            {{ $t('seckill.closeConfigure') }}&nbsp;<img :src="ArrowArr[1].img_2">
          </div>
        </div>

        <!-- 会员专享 -->
        <el-form-item
          :label="this.$t('seckill.cardTitle')"
          v-if="!arrorFlag"
        >
          <el-checkbox
            :label="this.$t('seckill.cardSelect')"
            v-model="showMember"
          ></el-checkbox>
          <div v-if="showMember">
            <el-select
              :placeholder="this.$t('seckill.cardTip')"
              v-model="form.cardId"
              size="small"
              @change="selectChange"
            >
              <el-option
                v-for="item in cardList"
                :key="item.id"
                :label="item.cardName"
                :value="item.id"
              ></el-option>
            </el-select>
            <span>
              <a style="color: #409eff;margin: 0 5px;">{{ this.$t('seckill.cardRefresh') }}</a><span> | </span>
              <a style="color: #409eff;margin: 0 5px;">{{ this.$t('seckill.cardNew') }}</a><span> | </span>
              <a style="color: #409eff;margin: 0 5px;">{{ this.$t('seckill.cardManage') }}</a>
            </span>
          </div>
        </el-form-item>

        <!-- 活动分享 -->
        <actShare
          :shareConfig="form.shareConfig"
          v-if="!arrorFlag"
        />

      </el-form>

      <!--添加商品弹窗-->
      <choosingGoods
        @resultGoodsRow="choosingGoodsResult"
        :chooseGoodsBack="[form.goodsId]"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="true"
        :showTips="true"
      />
    </div>

    <!-- 底部 -->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        :disabled="submitStatus"
        @click="saveClickHandler(form)"
      >{{ $t('seckill.save') }}</el-button>
    </div>

  </div>
</template>
<script>
import { mapActions } from 'vuex'
// 引入组件
import choosingGoods from '@/components/admin/choosingGoods'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
import { addSeckillList, updateSeckillList } from '@/api/admin/marketManage/seckill.js'
import { allCardApi } from '@/api/admin/marketManage/messagePush'
export default {

  components: {
    choosingGoods,
    actShare
  },
  props: ['isEdite', 'editId', 'editData'],
  data () {
    return {
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      arrorFlag: true, // 展开更多配置
      isShowChoosingGoodsDialog: false,
      // 选中商品id
      goodsRow: {},
      goodsIds: [],
      // 会员专享
      showMember: false,
      cardList: [], // 会员卡列表
      // 表单
      form: {
        name: '', // 活动名称
        goodsId: '', // 商品id
        validity: '', // 有效期
        startTime: '', // 开始时间
        endTime: '', // 结束时间
        limitAmount: '', // 限购数量
        limitPaytime: '', // 有效时间
        secKillProduct: [{
          productId: '', // id
          secKillPrice: '', // 秒杀价
          stock: '' // 秒杀库存
        }], // 秒杀价格表格数据
        freeFreight: 0, // 运费设置
        stock: 0, // 活动初始数量
        cardId: '', // 会员卡id
        shareConfig: {
          share_action: 1,
          share_doc: '',
          share_img_action: '1',
          share_img: ''
        }
      },
      // 校验表单
      fromRules: {
        name: [{ required: true, message: '请填写活动名称', trigger: 'blur' }],
        goodsId: [{ required: true }],
        validity: [{ required: true, message: '请填写有效期', trigger: 'blur' }],
        limitAmount: [{ required: true, message: '请填写限购数量', trigger: 'blur' }],
        limitPaytime: [{ required: true, message: '请填写支付时间', trigger: 'blur' }],
        freeFreight: [{ required: true, message: '请填写运费设置', trigger: 'change' }],
        // seckillPrices: [{ required: true }],
        stock: [{ required: true, message: '请填写活动初始数量', trigger: 'blur' }]
      },
      tableContent: [{
        goodsName: '',
        shopPrice: '',
        prdPrice: '',
        goodsNumber: '',
        prdNumber: '',
        goodsSaleNum: '',
        prdTypeNum: ''
      }], // 表格数据
      submitStatus: false
    }
  },
  mounted () {
    // 初始化
    this.editSeckillInit()
    // 会员卡数据
    allCardApi().then((res) => {
      if (res.error === 0) {
        this.cardList = res.content
      }
    })
  },
  // watch: {
  //   editData (val) {
  //     this.editSeckillInit(val)
  //   }
  // },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 选择商品弹窗
    showChoosingGoods () {
      this.transmitEditGoodsId(this.form.goodsId)
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 获取商品ids
    choosingGoodsResult (row) {
      this.tableContent = []
      console.log(row)
      this.goodsRow = row
      this.form.goodsId = row.goodsId
      this.tableContent.push(row)
      this.form.secKillProduct[0].productId = row.catId
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 编辑初始化
    editSeckillInit () {
      if (this.isEdite) {
        let val = this.editData
        this.goodsRow.ischecked = true
        this.form.name = val.name
        this.goodsRow.goodsName = val.goods.goodsName
        this.form.goodsId = val.goods.goodsId
        this.form.startTime = val.startTime
        this.form.endTime = val.endTime
        this.form.validity = [val.startTime, val.endTime]
        this.form.limitAmount = val.limitAmount
        this.form.limitPaytime = val.limitPaytime
        this.form.secKillProduct = val.secKillProduct // 秒杀初始数量
        this.form.freeFreight = val.freeFreight
        this.form.stock = 10 // 秒杀初始数量
        this.tableContent[0].goodsName = val.goods.goodsName
        this.tableContent[0].shopPrice = val.goods.shopPrice
        // this.tableContent[0].goodsNumber = val.goods.goodsNumber
        // this.tableContent[0].prdPrice = val.secKillProduct[0].secKillPrice
        // this.tableContent[0].prdNumber = val.secKillProduct[0].stock
        // this.tableContent[0].goodsSaleNum = val.secKillProduct[0].prdNumber
        // this.tableContent[0].prdTypeNum = val.secKillProduct[0].totalStock
        // this.form.cardId = val.cardId // 会员卡
        this.form.shareConfig = val.shopShareConfig
      }
    },

    // 保存活动
    saveClickHandler (formName) {
      this.submitStatus = true
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.startTime = this.form.validity[0]
          this.form.endTime = this.form.validity[1]
          this.form.secKillProduct[0].secKillPrice = Number(this.tableContent[0].prdPrice)
          this.form.secKillProduct[0].stock = Number(this.tableContent[0].prdNumber)
          if (this.isEdite) {
            this.form.secKillProduct[0].productId = this.form.secKillProduct[0].skproId
            updateSeckillList({
              skId: this.editId,
              status: 1,
              name: this.form.name,
              cardId: this.form.cardId,
              shareConfig: this.form.shareConfig
            }).then((res) => {
              if (res.error === 0) {
                this.$message.success(res.message)
                this.$emit('addSeckillSubmit')
              } else {
                this.$message.warning(res.message)
              }
            })
          } else {
            addSeckillList(this.form).then(res => {
              if (res.error === 0) {
                this.$message.success(res.message)
                this.$emit('addSeckillSubmit')
              } else {
                this.$message.warning(res.message)
              }
            })
          }
        } else {
          this.$message.warning('error submit!!')
        }
      })
      this.submitStatus = false
    },

    // 会员卡切换
    selectChange (value) {
      this.cardId = value
    }
  }
}
</script>
<style scoped>
.container {
  margin-top: 10px;
  padding: 10px;
  margin-bottom: 100px;
  background: #fff;
}
.footer {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
}
</style>
