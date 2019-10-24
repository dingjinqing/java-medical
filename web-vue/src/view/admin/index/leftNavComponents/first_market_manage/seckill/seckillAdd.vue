<template>
  <div style="margin-bottom: 100px;">
    <wrapper>
      <el-form style="background: #fff;">
        <el-form-item
          label="活动名称"
          label-width="100px"
        >
          <el-col :span="8">
            <el-input style="width: 165px"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item
          label="活动商品"
          label-width="100px"
        >
          <el-button @click="showChoosingGoods">选择商品</el-button>
          <el-col :span="8">
            <el-input></el-input>
          </el-col>
        </el-form-item>
        <el-form-item
          label="有效期"
          label-width="100px"
        >
          <el-date-picker
            type="datetime"
            placeholder="选择日期时间"
          >
          </el-date-picker>
          至
          <el-date-picker
            type="datetime"
            placeholder="选择日期时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="限购数量"
          label-width="100px"
        >
          <el-input-number
            controls-position="right"
            :min="0"
          ></el-input-number>
          <span style="color: #999; margin-left: 10px;">单个用户对秒杀商品限购的数量，请填写阿拉伯数字，填写0表示不限制</span>
        </el-form-item>
        <el-form-item
          label="下单后"
          label-width="100px"
        >
          <el-input-number
            controls-position="right"
            :min="5"
          ></el-input-number>
          <span>分钟内未支付，则释放库存</span>
          <p style="color: #999;">为确保买家能够顺利完成支付,请设置不小于5分钟的等待时间,因微信支付结果查询占用部分时间，造成订单状态变更不及时,故会存在极小部分订单自动退款给买家</p>
        </el-form-item>
        <el-form-item
          label="秒杀价格设置"
          label-width="100px"
        >
          <el-table border>
            <el-table-column
              label="商品名称/规格"
              align="center"
            ></el-table-column>
            <el-table-column
              label="商品名称/规格"
              align="center"
            ></el-table-column>
            <el-table-column
              label="商品名称/规格"
              align="center"
            ></el-table-column>
            <el-table-column
              label="商品名称/规格"
              align="center"
            ></el-table-column>
            <el-table-column
              label="商品名称/规格"
              align="center"
            ></el-table-column>
            <el-table-column
              label="商品名称/规格"
              align="center"
            ></el-table-column>
            <el-table-column
              label="商品名称/规格"
              align="center"
            ></el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item
          label="运费设置"
          label-width="100px"
        >
          <el-radio label="1">免运费</el-radio>
          <el-radio label="2">使用原商品运费模板</el-radio>
        </el-form-item>
        <el-form-item
          label="活动初始销量"
          label-width="100px"
        >
          <el-input-number
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
            <el-checkbox label="用户持有会员卡才可以参与活动"></el-checkbox>
            <div>
              <el-select
                placeholder="请选择会员卡"
                multiple
                size="small"
              >
                <el-option>111</el-option>
                <el-option>222</el-option>
                <el-option>333</el-option>
              </el-select>
              <span>
                <a style="color: #409eff;margin: 0 5px;">刷新</a><span> | </span>
                <a style="color: #409eff;margin: 0 5px;">新建会员卡</a><span> | </span>
                <a style="color: #409eff;margin: 0 5px;">管理会员卡</a>
              </span>
            </div>
          </el-form-item>

          <!-- 引入活动分享模块 -->
          <actShare :shareConfig="form.share" />
        </div>

      </el-form>

      <!--添加商品弹窗-->
      <!-- :chooseGoodsBack="[form.goodsId]" -->
      <choosingGoods
        @resultGoodsRow="choosingGoodsResult"
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
        >保存</el-button>
      </div>
    </wrapper>
  </div>
</template>
<script>
// 引入组件
import choosingGoods from '@/components/admin/choosingGoods'
import actShare from '@/components/admin/marketActivityShareSetting'
export default {

  components: {
    choosingGoods,
    actShare
  },
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
      // 表单
      form: {
        share: {
          share_action: 1,
          share_doc: '',
          share_img_action: 1,
          share_img: ''
        }
      }
    }
  },
  mounted () {

  },
  methods: {
    // 选择商品弹窗
    showChoosingGoods () {
      // console.log('初始化商品弹窗', this.form.goodsId)
      // this.transmitEditGoodsId(this.form.goodsId)
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 获取商品ids
    choosingGoodsResult (row) {
      console.log('获取商品行', row)
      this.goodsRow = row
      console.log('goodsRow', this.goodsRow)
      // console.log('tmpGoodsIds', this.form.goodsId)
      // this.form.goodsId = row.goodsId

      // 初始化规格表格
      // getAllGoodsProductList(this.form.goodsId).then(res => {
      //   console.log('product', res.content)
      //   this.form.product = res.content
      // })
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
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
