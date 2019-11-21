<template>
  <div class="container">

    <!-- 表单 -->
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="130px"
        :label-position="'right'"
      >
        <el-form-item
          :label="$t('seckill.activityName') + '：'"
          prop="name"
        >
          <el-col :span="8">
            <el-input
              v-model="form.name"
              size="small"
              style="width: 170px"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.goodsName') + '：'"
          prop="goodsId"
        >
          <el-col
            :span="2"
            style="margin-right:10px"
          >
            <el-input
              :disabled="true"
              v-model="tableContent[0].goodsName"
              v-if="tableContent[0].goodsName ? true : false"
              size="small"
            ></el-input>
            <el-input
              :disabled="true"
              v-if="false"
              v-model="form.goodsId"
              size="small"
            ></el-input>
          </el-col>
          <el-button
            :disabled="this.isEdite"
            @click="showChoosingGoods"
            class="el-icon-plus"
            size="small"
          >{{ $t('seckill.select') }}</el-button>

        </el-form-item>
        <el-form-item
          :label="$t('seckill.validDate') + '：'"
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
            size="small"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.limitNum') + '：'"
          prop="limitAmount"
        >
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.limitAmount"
            controls-position="right"
            :min="0"
            size="small"
          ></el-input-number>
          <span style="color: #999; margin-left: 10px;">{{ $t('seckill.limitTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.orderAfter') + '：'"
          prop="limitPaytime"
        >
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.limitPaytime"
            controls-position="right"
            :min="5"
            size="small"
          ></el-input-number>
          <span>{{ $t('seckill.orderTip') }}</span>
          <p style="color: #999;">{{ $t('seckill.langTip') }}</p>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.seckillPrice') + '：'"
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
          :label="$t('seckill.freeFreight') + '：'"
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
          style="padding: 0 0 30px 30px"
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
          :label="this.$t('seckill.cardTitle') + '：'"
          v-if="!arrorFlag"
        >
          <el-checkbox
            :label="this.$t('seckill.cardSelect')"
            v-model="showMember"
          ></el-checkbox>
          <div
            v-if="showMember"
            style="display: flex"
          >
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
            <div>
              <span
                class="member"
                @click="refresh()"
              >{{ this.$t('seckill.cardRefresh') }}</span><span> | </span>
              <span
                class="member"
                @click="addMemberCard()"
              >{{ this.$t('seckill.cardNew') }}</span><span> | </span>
              <span
                class="member"
                @click="manageMemberCard()"
              >{{ this.$t('seckill.cardManage') }}</span>
            </div>
          </div>
        </el-form-item>

        <!-- 活动分享 -->
        <el-form-item
          prop="shareConfig"
          label="活动分享："
          v-if="!arrorFlag"
        >
          <div class="shareContent">
            <el-radio
              v-model="form.shareConfig.share_action"
              :label="1"
            >默认样式</el-radio>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src1"></el-image>
              <el-button
                slot="reference"
                type="text"
                style="margin: 0 20 0 0px"
              >查看示例</el-button>
            </el-popover>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src2"></el-image>
              <el-button
                slot="reference"
                type="text"
              >下载海报</el-button>
            </el-popover>
          </div>
          <div>
            <el-radio
              v-model="form.shareConfig.share_action"
              :label="2"
            >自定义样式</el-radio>
            <div v-if="form.shareConfig.share_action === 2">
              <span>文案：</span>
              <el-input
                v-model="form.shareConfig.share_doc"
                size="small "
                style="width: 180px;"
              ></el-input>
            </div>
            <div v-if="form.shareConfig.share_action === 2">
              <span>分享图：</span>
              <el-radio
                v-model="form.shareConfig.share_img_action"
                :label="1"
              >活动商品信息图</el-radio>
              <div style="margin-left: 60px;">
                <el-radio
                  v-model="form.shareConfig.share_img_action"
                  :label="2"
                >自定义图片</el-radio>
              </div>

              <div
                style="display: flex"
                v-if="form.shareConfig.share_img_action === 2"
              >
                <div
                  class="imgContent"
                  @click="addGoodsImg"
                >
                  <div>
                    <img
                      v-if="form.shareConfig.share_img === ''"
                      src="http://jmpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png"
                      alt=""
                    >
                    <img
                      v-if="form.shareConfig.share_img !== ''"
                      :src="form.shareConfig.share_img"
                      alt=""
                      class="shareImg"
                    >
                  </div>
                </div>
                <span class="picSizeTips">建议尺寸：800*800像素</span>
              </div>
            </div>
          </div>

        </el-form-item>

      </el-form>

      <!--添加商品弹窗-->
      <choosingGoods
        :singleElection="true"
        :loadProduct="true"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :chooseGoodsBack="[form.goodsId]"
        @resultGoodsRow="choosingGoodsResult"
      />
    </div>

    <!-- 选择图片弹框 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="showImageDialog"
      @handleSelectImg='handleSelectImg'
      :imageSize="[800, 800]"
    />

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
import ImageDalog from '@/components/admin/imageDalog'
import { addSeckillList, getSeckillList, updateSeckillList } from '@/api/admin/marketManage/seckill.js'
import { allCardApi } from '@/api/admin/marketManage/messagePush'
export default {

  components: {
    choosingGoods,
    actShare,
    ImageDalog
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
      submitStatus: false,

      // 分享
      showImageDialog: false,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`
      }
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
    // 刷新
    refresh () {
      console.log(111)
    },
    addMemberCard () {
      this.$router.push({
        path: '/admin/home/main/normalCardDetail'
      })
    },
    manageMemberCard () {
      this.$router.push({
        path: '/admin/home/main/user_card'
      })
    },
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
      this.form.secKillProduct[0].productId = row.prdId
      this.tableContent[0].goodsName = row.goodsName
      this.tableContent[0].shopPrice = row.prdPrice
      this.tableContent[0].prdPrice = null
      this.tableContent[0].goodsNumber = row.prdNumber
      this.tableContent[0].prdNumber = null
      // 可编辑状态
      this.disabledFlag = false
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 图片弹窗
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },

    // 图片点击回调函数
    handleSelectImg (res) {
      if (res != null) {
        this.form.shareConfig.share_img = res.imgUrl
      }
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
          this.tableContent[0].shopPrice = data.secKillProduct[0].prdPrice
          this.tableContent[0].prdPrice = data.secKillProduct[0].secKillPrice
          this.tableContent[0].goodsNumber = data.secKillProduct[0].prdNumber
          this.tableContent[0].prdNumber = data.secKillProduct[0].stock
          this.form.freeFreight = data.freeFreight
          // 展开设置
          this.arrorFlag = false
          // 会员卡
          this.form.cardId = []
          data.memberCard.map((item, index) => {
            this.form.cardId.push(item.id)
          })
          if (this.form.cardId.length > 0) {
            this.showMember = true
          } else {
            this.showMember = false
          }
          // 活动分享
          this.form.shareConfig.share_action = data.shopShareConfig.share_action
          this.form.shareConfig.share_doc = data.shopShareConfig.share_doc
          this.form.shareConfig.share_img_action = data.shopShareConfig.share_img_action
          this.form.shareConfig.share_img = data.shopShareConfig.share_img
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
      this.form.endTime = this.form.validity[1]
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
      if (this.form.shareConfig.share_action === 1) {
        this.form.shareConfig.share_doc = ''
        this.form.shareConfig.share_img_action = 1
        this.form.shareConfig.share_img = ''
      }

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
            // var obj = this.form
            // obj.skId = this.editId
            updateSeckillList({
              skId: this.editId,
              name: this.form.name,
              cardId: this.form.cardId,
              shareConfig: this.form.shareConfig
            }).then((res) => {
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
.shareContent a {
  text-decoration: none;
  color: #409eff;
}
.shareContent a:first-child {
  margin-right: 10px;
}
.imgContent {
  width: 80px;
  height: 80px;
  text-align: center;
  line-height: 65px;
  margin-left: 60px;
  border: 1px solid #ccc;
  cursor: pointer;
  box-sizing: border-box;
}
.imgContent .shareImg {
  width: 100%;
  height: 100%;
}
.member {
  color: #409eff;
  margin: 0 5px;
  cursor: pointer;
}
.picSizeTips {
  display: block;
  line-height: 80px;
  margin-left: 20px;
  color: rgb(153, 153, 153);
}
</style>
