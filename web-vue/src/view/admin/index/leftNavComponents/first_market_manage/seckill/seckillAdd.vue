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
          <el-input
            :disabled="true"
            v-model="goodsRow.goodsName"
            v-if="goodsRow.ischecked"
            size="small"
            style="width: 170px;"
          ></el-input>
          <el-input
            :disabled="true"
            v-if="false"
            v-model="form.goodsId"
            size="small"
            style="width: 170px;"
          ></el-input>

          <el-button
            :disabled="this.isEdite"
            class="el-icon-plus"
            size="small"
            @click="showChoosingGoods"
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
            :default-time="['00:00:00','23:59:59']"
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
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="form.secKillProduct"
            border
            style="width: 100%"
          >
            <el-table-column
              :label="$t('seckill.specifications')"
              prop="prdDesc"
              align="center"
            >
            </el-table-column>
            <el-table-column
              :label="$t('seckill.shopPrice')"
              prop="prdPrice"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('seckill.prdPrice')"
              align="center"
            >
              <template slot="append">
                <span>{{$t('groupBuy.groupBuyPrice')}}</span>
                <el-button
                  @click="setCurrent(1)"
                  size="small"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template>
              <template slot-scope="scope">
                <el-form-item
                  :prop="'secKillProduct.' +  scope.$index+ '.secKillPrice'"
                  :rules="[
                    { required: true, message: '秒杀价不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.prdPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.secKillPrice"
                    size="small"
                    :disabled="isEdite || disabledFlag"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('seckill.goodsNumber')"
              prop="prdNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('seckill.prdNumber')"
              align="center"
            >
              <template slot="append">
                <span>{{$t('groupBuy.groupBuyStock')}}</span>
                <el-button
                  @click="setCurrent(2)"
                  size="mini"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template>
              <template slot-scope="scope">
                <el-form-item
                  :prop="'secKillProduct.' +  scope.$index+ '.stock'"
                  :rules="[
                    { required: true, message: '秒杀库存不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.prdNumber)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.stock"
                    size="small"
                    :disabled="isEdite || disabledFlag"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <div
              slot="append"
              class="moreSetUp"
            >
              <span>{{ this.$t('groupBuy.moreSettings') }}</span>
              <a
                :class="activeIndex === 1 ? '' : 'settings'"
                @click="setCurrent(1)"
              >秒杀价
              </a>
              <a
                :class="activeIndex === 2 ? '' : 'settings'"
                @click="setCurrent(2)"
              >秒杀库存
              </a>
            </div>
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
        <div v-if="!arrorFlag">
          <!-- 会员专享 -->
          <el-form-item :label="this.$t('seckill.cardTitle') + '：'">
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
        </div>

      </el-form>

      <!--添加商品弹窗-->
      <choosingGoods
        :singleElection="true"
        :showTips="true"
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
        @click="saveClickHandler"
      >{{ $t('seckill.save') }}</el-button>
    </div>

  </div>
</template>
<script>
// 引入组件
import choosingGoods from '@/components/admin/choosingGoods'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
import ImageDalog from '@/components/admin/imageDalog'
import { getSeckillList, addSeckillList, updateSeckillList } from '@/api/admin/marketManage/seckill.js'
import { allCardApi } from '@/api/admin/marketManage/messagePush'
import { getAllGoodsProductList } from '@/api/admin/brandManagement.js'
import { getSelectGoods } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    choosingGoods,
    actShare,
    ImageDalog
  },
  props: ['isEdite', 'editId'],
  data () {
    return {
      activeIndex: 0, // 批量设置
      // 表单
      form: {
        name: '', // 活动名称
        goodsId: '', // 商品id
        validity: '', // 有效期
        startTime: '', // 开始时间
        endTime: '', // 结束时间
        limitAmount: '', // 限购数量
        limitPaytime: '', // 支付有效时间
        secKillProduct: [], // 秒杀价格表格数据
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
        name: [
          { required: true, message: '请填写活动名称', trigger: 'blur' }
        ],
        goodsId: [
          { required: true, message: '请选择活动商品', trigger: 'change' }
        ],
        validity: [
          { required: true, message: '请填写有效期', trigger: 'change' }
        ],
        limitAmount: [
          { required: true, message: '请填写限购数量', trigger: 'change' }
        ],
        limitPaytime: [
          { required: true, message: '请填写支付时间', trigger: 'change' }
        ],
        freeFreight: [
          { required: true, message: '请填写运费设置', trigger: 'change' }
        ]
      },
      disabledFlag: true, // 是否可编辑
      submitStatus: false, // 提交

      // 展开设置箭头
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      arrorFlag: true, // 展开更多配置

      showMember: false, // 会员专享
      cardList: [], // 会员卡列表

      // 分享
      showImageDialog: false,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`
      },

      isShowChoosingGoodsDialog: false, // 商品弹窗
      // 选中商品id
      goodsRow: {},
      goodsIds: []
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
  watch: {
    lang () {

    },
    'form.goodsId': function (value) {
      if (value) {
        this.$refs.form.validateField('goodsId')
      }
    }
  },
  methods: {
    // 编辑初始化
    editSeckillInit () {
      getSeckillList({ skId: this.editId }).then((res) => {
        if (res.error === 0) {
          var data = res.content
          this.form.name = data.name
          this.form.goodsId = data.goods.goodsId
          this.getGoodsInfo(data.goodsId)
          this.form.secKillProduct = data.secKillProduct
          this.form.secKillProduct.forEach((item, index) => {
            if (item.prdDesc === '') {
              item.prdDesc = data.goods.goodsName
            }
          })
          this.form.startTime = data.startTime
          this.form.endTime = data.endTime
          this.form.validity = [data.startTime, data.endTime]
          this.form.limitAmount = data.limitAmount
          this.form.limitPaytime = data.limitPaytime
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

    // 获取商品信息
    getGoodsInfo (id) {
      getSelectGoods({ goodsId: id }).then((res) => {
        if (res.error === 0) {
          this.goodsRow = res.content
          this.goodsRow.ischecked = true
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
      // 总库存
      this.form.stock = 0
      this.form.secKillProduct.forEach((item, index) => {
        item.productId = item.prdId
        item.secKillPrice = Number(item.secKillPrice)
        item.stock = Number(item.stock)
        this.form.stock += item.stock
      })

      console.log(this.form)

      if (this.form.goodsId === '') {
        this.$message.warning({ message: '请选择商品!' })
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
              } else {
                this.$message.warning({ message: res.message })
              }
            })
          } else {
            // 编辑秒杀
            updateSeckillList({
              skId: this.editId,
              name: this.form.name,
              cardId: this.form.cardId,
              shareConfig: this.form.shareConfig
            }).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '修改成功' })
                this.$emit('addSeckillSubmit')
              } else {
                this.$message.warning({ message: res.message })
              }
            })
          }
        }
      })
      this.submitStatus = false
    },

    // 选择商品弹窗
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 商品弹窗的回调函数
    choosingGoodsResult (row) {
      this.goodsRow = row
      this.form.goodsId = row.goodsId
      if (Object.keys(row).length === 0) {
        return
      }
      this.initTableData()
      // 可编辑状态
      this.disabledFlag = false
    },

    // 初始化规格表格
    initTableData () {
      getAllGoodsProductList(this.form.goodsId).then(res => {
        res.content.forEach((item, index) => {
          item.index = index
          // 单规格名称回显
          if (item.prdDesc === '') {
            item.prdDesc = this.goodsRow.goodsName
          }
        })
        this.form.secKillProduct = res.content
        console.log(' this.form.secKillProduct ', this.form.secKillProduct)
      })
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

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 刷新
    refresh () {
      console.log(111)
    },

    addMemberCard () {
      window.open('/admin/home/main/normalCardDetail')
    },

    manageMemberCard () {
      window.open('/admin/home/main/user_card')
    },

    // 校验秒杀价格
    validateMoney (rule, value, callback, prdPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > prdPrice) {
        callback(new Error('秒杀价不能大于商品原价'))
      } else {
        callback()
      }
    },

    // 校验秒杀库存
    validateNum (rule, value, callback, prdNumber) {
      var re = /^[1-9]\d*$/
      if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else if (value > prdNumber) {
        callback(new Error('秒杀库存不能大于商品库存'))
      } else {
        callback()
      }
    },

    // 批量设置数据
    setCurrent (index) {
      // 拷贝一份数据
      let price = JSON.parse(JSON.stringify(this.form.secKillProduct))
      switch (index) {
        case 1:
          price.forEach(row => {
            row.secKillPrice = price[0].secKillPrice
          })
          this.activeIndex = 1
          break
        case 2:
          price.forEach(row => {
            row.stock = price[0].stock
          })
          this.activeIndex = 2
          break
      }
      this.form.secKillProduct = price
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
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.moreSetUp a {
  margin-right: 10px;
  cursor: pointer;
}
.settings {
  color: #5a8bff;
}
</style>
