<template>
  <div style="margin-bottom: 100px;">
    <wrapper>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="120px"
        :label-position="'right'"
        style="background: #fff;"
      >
        <el-form-item
          label="活动名称"
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
          label="活动商品"
          prop="goodsId"
        >
          <el-button
            @click="showChoosingGoods"
            class="el-icon-plus"
          >选择商品</el-button>
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
          label="有效期"
          prop="validity"
        >
          <el-date-picker
            v-model="form.validity"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="限购数量"
          prop="limitAmount"
        >
          <el-input-number
            v-model="form.limitAmount"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <span style="color: #999; margin-left: 10px;">单个用户对秒杀商品限购的数量，请填写阿拉伯数字，填写0表示不限制</span>
        </el-form-item>
        <el-form-item
          label="下单后"
          prop="limitPaytime"
        >
          <el-input-number
            v-model="form.limitPaytime"
            controls-position="right"
            :min="5"
          ></el-input-number>
          <span>分钟内未支付，则释放库存</span>
          <p style="color: #999;">为确保买家能够顺利完成支付,请设置不小于5分钟的等待时间,因微信支付结果查询占用部分时间，造成订单状态变更不及时,故会存在极小部分订单自动退款给买家</p>
        </el-form-item>
        <el-form-item
          label="秒杀价格设置"
          prop="seckillPrices"
        >
          <el-table
            border
            :data="tableContent"
          >
            <el-table-column
              label="商品名称/规格"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              label="原价(元)"
              prop="shopPrice"
              align="center"
            ></el-table-column>
            <el-table-column
              label="秒杀价(元)"
              prop="prdPrice"
              align="center"
            >
              <template slot-scope="scope">
                <el-input v-model="scope.row.prdPrice"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="原库存"
              prop="goodsNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              label="秒杀库存"
              prop="prdNumber"
              align="center"
            >
              <template slot-scope="scope">
                <el-input v-model="scope.row.prdNumber"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="已售秒杀数量"
              prop="goodsSaleNum"
              align="center"
            ></el-table-column>
            <el-table-column
              label="剩余秒杀库存"
              prop="prdTypeNum"
              align="center"
            ></el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item
          label="运费设置"
          prop="freeFreight"
        >
          <el-radio
            v-model="form.freeFreight"
            :label="1"
          >免运费</el-radio>
          <el-radio
            v-model="form.freeFreight"
            :label="0"
          >使用原商品运费模板</el-radio>
        </el-form-item>
        <el-form-item
          label="活动初始销量"
          prop="initNum"
        >
          <el-input-number
            v-model="form.initNum"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <span>活动商品初始销量</span>
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
            展开更多配置&nbsp;<img :src="ArrowArr[0].img_1">
          </div>
          <div
            v-if="!arrorFlag"
            style="color:rgb(90, 139, 255);cursor:pointer"
          >
            收起更多配置&nbsp;<img :src="ArrowArr[1].img_2">
          </div>
        </div>

        <div
          v-if="!arrorFlag"
          style="padding-bottom: 30px"
        >
          <!-- 会员专享 -->
          <el-form-item
            label="会员专享"
            label-width="100px"
          >
            <el-checkbox
              label="用户持有会员卡才可以参与活动"
              v-model="showMember"
            ></el-checkbox>
            <div v-if="showMember">
              <el-select
                placeholder="请选择会员卡"
                v-model="form.cardId"
                size="small"
                @change="selectChange"
              >
                <el-option
                  v-for="item in card_list"
                  :key="item.id"
                  :label="item.card_name"
                  :value="item.id"
                ></el-option>
              </el-select>
              <span>
                <a style="color: #409eff;margin: 0 5px;">刷新</a><span> | </span>
                <a style="color: #409eff;margin: 0 5px;">新建会员卡</a><span> | </span>
                <a style="color: #409eff;margin: 0 5px;">管理会员卡</a>
              </span>
            </div>
          </el-form-item>

          <!-- 引入活动分享模块 -->
          <actShare :shareConfig="form.shareConfig" />
        </div>

      </el-form>

      <!--添加商品弹窗-->
      <!-- :chooseGoodsBack="[form.goodsId]" -->
      <choosingGoods
        @resultGoodsRow="choosingGoodsResult"
        :chooseGoodsBack="[form.goodsId]"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="true"
        :showTips="true"
      />
    </wrapper>

    <wrapper>
      <div class="footer">
        <el-button
          size="small"
          type="primary"
          :disabled="submitStatus"
          @click="saveClickHandler(form)"
        >保存</el-button>
      </div>
    </wrapper>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
// 引入组件
import choosingGoods from '@/components/admin/choosingGoods'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
import { addSeckillList } from '@/api/admin/marketManage/seckill.js'
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
      card_list: [
        { id: '1', card_name: 'list1' },
        { id: '2', card_name: 'list2' },
        { id: '3', card_name: 'list3' },
        { id: '4', card_name: 'list4' },
        { id: '5', card_name: 'list5' }
      ],
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
        initNum: 0, // 活动初始数量
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
        initNum: [{ required: true, message: '请填写活动初始数量', trigger: 'blur' }]
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

  },
  watch: {
    editData (val) {
      this.editSeckillInit(val)
    }
  },
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
      this.form.secKillProduct[0].productId = row.prdId
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 编辑初始化
    editSeckillInit (val) {
      if (this.isEdite) {
        this.goodsRow.ischecked = true
        this.goodsRow.goodsName = val.goods.goodsName
        this.form.name = val.name
        this.form.goodsId = val.goods.goodsId
        this.form.startTime = val.startTime
        this.form.endTime = val.endTime
        this.form.validity = [val.startTime, val.endTime]
        this.form.limitAmount = val.limitAmount
        this.form.limitPaytime = val.limitPaytime
        this.form.secKillProduct = val.secKillProduct
        this.tableContent[0].goodsName = val.goods.goodsName
        this.tableContent[0].shopPrice = val.goods.shopPrice
        this.tableContent[0].goodsNumber = val.goods.goodsNumber
        this.tableContent[0].prdPrice = val.secKillProduct[0].secKillPrice
        this.tableContent[0].prdNumber = val.secKillProduct[0].stock
        this.tableContent[0].goodsSaleNum = val.secKillProduct[0].prdNumber
        this.tableContent[0].prdTypeNum = val.secKillProduct[0].totalStock
        this.form.freeFreight = val.freeFreight
        this.form.initNum = 10
        // this.form.cardId = val.cardId
        this.form.shareConfig = val.shopShareConfig
        if (val.memberCard.length === 0) {
          this.showMember = false
          this.form.cardId = ''
        } else {
          this.showMember = true
        }
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
            this.form.secKillProduct[0].productId = 5130
            // this.form.secKillProduct[0].secKillPrice = 299
            // this.form.secKillProduct[0].stock = 999
            this.form.shareConfig.share_img_action = 1
          }
          console.log(this.form)
          addSeckillList(this.form).then(res => {
            if (res.error === 0) {
              this.$message.success(res.message)
              this.$emit('addSeckillSubmit')
            } else {
              this.$message.warning(res.message)
            }
          })
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
.wrapper {
  margin: 10px 0 !important;
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
