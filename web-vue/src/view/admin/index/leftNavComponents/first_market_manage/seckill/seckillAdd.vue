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
          <el-col :span="2">
            <el-input
              :disabled="true"
              v-model="tableContent[0].goodsName"
              v-if="tableContent[0].goodsName ? true : false"
            ></el-input>
            <el-input
              :disabled="true"
              v-if="false"
              v-model="form.goodsId"
            ></el-input>
          </el-col>
          <el-button
            :disabled="this.isEdite"
            @click="showChoosingGoods"
            class="el-icon-plus"
          >{{ $t('seckill.select') }}</el-button>

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
          prop="secKillProduct"
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
                  :disabled="isEdite || disabledFlag"
                  v-model="scope.row.prdPrice"
                  @blur="checkNum($event, scope.row.shopPrice)"
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
                  :disabled="isEdite || disabledFlag"
                  v-model="scope.row.prdNumber"
                  @blur="checkNum($event, scope.row.goodsNumber)"
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
              multiple
              size="small"
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
        :singleElection="true"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :chooseGoodsBack="[form.goodsId]"
        @resultGoodsRow="choosingGoodsResult"
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
// 引入组件
import choosingGoods from '@/components/admin/choosingGoods'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
import { addSeckillList, getSeckillList, updateSeckillList } from '@/api/admin/marketManage/seckill.js'
import { allCardApi } from '@/api/admin/marketManage/messagePush'
export default {

  components: {
    choosingGoods,
    actShare
  },
  props: ['isEdite', 'editId'],
  data () {
    // 自定义校验商品名称
    // var validateGoodsId = (rule, value, callback) => {
    //   if (value === 0 || value === '') {
    //     callback(new Error('请选择商品!'))
    //   } else {
    //     callback()
    //   }
    // }
    // 自定义校验秒杀价格设置
    // var validateSeckillPrices = (rule, value, callback) => {
    //   if (value[0].secKillPrice === 0 || value[0].secKillPrice === 0) {
    //     callback(new Error('请完整填写表格!'))
    //   } else {
    //     callback()
    //   }
    // }

    return {
      // 展开设置箭头
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      arrorFlag: true, // 展开更多配置
      isShowChoosingGoodsDialog: false, // 商品弹窗
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
        limitPaytime: '', // 支付有效时间
        secKillProduct: [{
          productId: '', // id
          secKillPrice: '', // 秒杀价
          stock: '' // 秒杀库存
        }], // 秒杀价格表格数据
        freeFreight: 0, // 运费设置
        stock: 0, // 活动总库存
        cardId: '', // 会员卡id
        shareConfig: {
          share_action: 1,
          share_doc: '',
          share_img_action: 1,
          share_img: ''
        }
      },
      // 校验表单
      fromRules: {
        name: { required: true, message: '请填写活动名称', trigger: 'blur' },
        goodsId: { required: true },
        // goodsId: { required: true, validator: validateGoodsId, trigger: 'blur' },
        validity: { required: true, message: '请填写有效期', trigger: 'change' },
        limitAmount: { required: true, message: '请填写限购数量', trigger: 'change' },
        limitPaytime: { required: true, message: '请填写支付时间', trigger: 'change' },
        // 秒杀价格表格
        secKillProduct: { required: true },
        // secKillProduct: { required: true, validator: validateSeckillPrices, trigger: ['blur', 'change'] },
        freeFreight: { required: true, message: '请填写运费设置', trigger: 'change' }
      },
      // 选中商品信息
      tableContent: [{
        goodsName: '',
        shopPrice: '',
        prdPrice: '',
        goodsNumber: '',
        prdNumber: '',
        goodsSaleNum: '',
        prdTypeNum: ''
      }],
      disabledFlag: true,
      submitStatus: false
    }
  },
  mounted () {
    // 编辑初始化
    if (this.isEdite === true) {
      this.editSeckillInit()
    }
    // 获取会员卡数据
    allCardApi().then((res) => {
      if (res.error === 0) {
        this.cardList = res.content
      }
    })
  },
  methods: {
    // 校验表格数据
    checkNum (e, maxValue) {
      if (e.target.value > maxValue) {
        this.$message.warning({ message: '秒杀价和秒杀库存不能大于原价和原库存!' })
      }
    },

    // 选择商品弹窗
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 商品弹窗的回调函数
    choosingGoodsResult (row) {
      this.form.goodsId = row.goodsId
      this.form.secKillProduct[0].productId = row.goodsId
      this.tableContent[0].goodsName = row.goodsName
      this.tableContent[0].shopPrice = row.shopPrice
      this.tableContent[0].goodsNumber = row.goodsNumber
      // 可编辑状态
      this.disabledFlag = false
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 编辑初始化
    editSeckillInit () {
      getSeckillList({ skId: this.editId }).then((res) => {
        if (res.error === 0) {
          var data = res.content
          this.form.name = data.name
          this.form.goodsId = data.goods.goodsId
          this.form.startTime = data.startTime
          this.form.endTime = data.endTime
          this.form.validity = [data.startTime, data.endTime]
          this.form.limitAmount = data.limitAmount
          this.form.limitPaytime = data.limitPaytime
          // 秒杀价格设置
          this.tableContent[0].goodsName = data.goods.goodsName
          this.tableContent[0].shopPrice = data.goods.shopPrice
          this.tableContent[0].prdPrice = 0
          this.tableContent[0].goodsNumber = data.goods.goodsNumber
          this.tableContent[0].prdNumber = 0
          this.form.freeFreight = data.freeFreight
          // 展开设置
          this.arrorFlag = false
          // 会员卡
          this.form.cardId = data.memberCard
          if (this.form.cardId === '') {
            this.showMember = true
          } else {
            this.showMember = false
            // this.form.cardId = this.form.cardId.split(',')
            // this.form.cardId = this.form.cardId.map(Number)
          }
          // 活动分享
          this.form.shareConfig = data.shareConfig
          // 总库存
          this.form.stock = 0

          console.log(this.form)
        }
      })
    },

    // 保存秒杀活动
    saveClickHandler () {
      this.submitStatus = true

      this.form.goodsId = Number(this.form.goodsId)
      // 有效期
      this.form.startTime = this.form.validity[0]
      this.form.endTime = this.form.validity[0]
      // 秒杀价格规格
      this.form.secKillProduct[0].secKillPrice = Number(this.tableContent[0].prdPrice)
      this.form.secKillProduct[0].stock = Number(this.tableContent[0].prdNumber)
      // 会员卡专享
      if (this.form.cardId !== undefined && this.form.cardId.length > 0) {
        this.form.cardId = this.form.cardId.toString()
      } else {
        this.form.cardId = ''
      }
      // 活动分享

      console.log(this.form)
      if (this.tableContent[0].goodsName === '') {
        this.$message.warning({ message: '请选择商品!' })
        this.submitStatus = false
        return
      }
      if (this.form.secKillProduct[0].secKillPrice === 0 || this.form.secKillProduct[0].stock === 0) {
        this.$message.warning({ message: '请完整填写表格!' })
        this.submitStatus = false
        return
      }
      if ((this.tableContent[0].prdPrice > this.tableContent[0].shopPrice) || (this.tableContent[0].prdNumber > this.tableContent[0].goodsNumber)) {
        this.$message.warning({ message: '秒杀价和秒杀库存不能大于原价和原库存!' })
        this.submitStatus = false
        return
      }

      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.isEdite === false) {
            // 添加秒杀
            addSeckillList(this.form).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '添加成功' })
                this.$emit('addSeckillSubmit')
              }
            })
          } else {
            // 编辑秒杀
            var obj = this.form
            obj.id = this.editId
            updateSeckillList(obj).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '修改成功' })
                this.$emit('addSeckillSubmit')
              }
            })
          }
        }
      })
      this.submitStatus = false
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
