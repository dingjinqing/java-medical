<template>
  <div style="margin-bottom: 60px;">
    <el-form
      ref="form"
      :model="form"
      label-width="140px"
    >
      <el-form-item :label="$t('distribution.switch')">
        <el-switch
          v-model="form.status"
          :active-value='1'
          :inactive-value='0'
        ></el-switch>
        <div class="text">
          {{ $t('distribution.switchTip') }}
        </div>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.reviewed')"
        v-if="form.status === 1"
      >
        <el-switch
          v-model="form.judge_status"
          :active-value='1'
          :inactive-value='0'
        ></el-switch>
        <span class="text">({{ $t('distribution.reviewedTip1') }}）
          <a
            href="javascript:void(0);"
            @click="copyWritingHandler"
          >{{ $t('distribution.reviewedTip2') }}</a></span>
        <div class="text">
          {{ $t('distribution.reviewedTip3') }}
        </div>
        <template v-if="form.judge_status === 1">
          <el-checkbox
            v-model="form.invitationCode"
            :true-label='1'
            :false-label="0"
          >{{ $t('distribution.reviewedInvitation') }}</el-checkbox>
          <p>{{ $t('distribution.invitationTip1') }} <a
              href="javascript:void(0);"
              style="color: red;"
            >{{ $t('distribution.invitationTip2') }}</a> {{ $t('distribution.invitationTip3') }}
            <a
              href="javascript:void(0);"
              @click="listClickHandler"
            >{{ $t('distribution.invitationTip4') }}</a> {{ $t('distribution.invitationTip5') }}</p>
          <el-checkbox
            v-model="form.activation"
            :true-label='1'
            :false-label="0"
          >{{ $t('distribution.reviewedInfo') }}</el-checkbox>

          <div
            v-if="form.activation === 1"
            style="width: 900px;"
          >
            <el-checkbox-group v-model="form.activation_cfg">
              <el-checkbox
                v-for="(item, index) in checkedList"
                :key="index"
                :label="item"
              >{{ item }}</el-checkbox>
            </el-checkbox-group>
          </div>
        </template>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.ranking')"
        v-if="form.status === 1"
      >
        <el-switch
          v-model="form.rank_status"
          :active-value='1'
          :inactive-value='0'
        ></el-switch>
        <div class="text">
          {{ $t('distribution.rankingTip') }}
        </div>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.validity')"
        v-if="form.status === 1"
      >
        <el-radio-group v-model="form.vaild">
          <el-radio :label="1">
            <el-input
              style="width: 100px;"
              v-model.number="vaildDate"
              :disabled="form.vaild === 0"
            ></el-input> {{ $t('distribution.validityDay') }}
          </el-radio>
          <el-radio :label="0">{{ $t('distribution.validityForever') }}</el-radio>
        </el-radio-group>
        <div class="text">{{ $t('distribution.validityTip') }}</div>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.protection')"
        v-if="form.status === 1"
      >
        <el-radio-group v-model="form.protect_date">
          <el-radio :label="1">
            <el-input
              style="width: 100px;"
              v-model.number="protectDate"
              :disabled="form.protect_date === 0"
            ></el-input> {{ $t('distribution.validityDay') }}
          </el-radio>
          <el-radio :label="0">{{ $t('distribution.validityForever') }}</el-radio>
        </el-radio-group>
        <div class="text">{{ $t('distribution.protectionTip') }}</div>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.pageName')"
        v-if="form.status === 1"
      >
        <el-input
          style="width: 200px"
          v-model="form.desc"
        ></el-input>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.recommendShop')"
        v-if="form.status === 1"
      >
        <el-radio-group v-model="form.distribution_goods_type">
          <el-radio :label="0">{{ $t('distribution.recommendRadio1') }}</el-radio>
          <el-radio :label="1">{{ $t('distribution.recommendRadio2') }}</el-radio>
          <el-radio :label="2">{{ $t('distribution.recommendRadio3') }}</el-radio>
        </el-radio-group>

        <div v-if="form.distribution_goods_type === 2">
          <p class="text">{{ $t('distribution.recommendTip') }}</p>
          <el-button @click="hanldeToAddGoodS"><i class="el-icon-plus"></i> {{ $t('distribution.chooseCommodity') }}</el-button>
          <el-table
            :data="tableData"
            border
            style="width: 500px;margin-top: 20px;"
          >
            <el-table-column
              :label="$t('distribution.commodityName')"
              prop="goodsName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('distribution.commodityPrice')"
              prop="shopPrice"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('distribution.commodityStock')"
              prop="goodsNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('distribution.commodityOption')"
              align="center"
            >
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="mini"
                  @click="deleteTable(scope.$index)"
                >{{ $t('distribution.commodityDelete') }}</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.customContent')"
        v-if="form.status === 1"
      >

        <div
          v-if="tamplateFlag === true && this.templateRow.pageName !== ''"
          style="display: inline-block;"
        >
          {{ this.templateRow.pageName }}
          <i
            class="el-icon-error"
            style="color: #ccc;font-size: 16px;"
            @click="clearClickHandler()"
          ></i>
        </div>

        <el-button @click="chooseTemplate"><i class="el-icon-plus"></i> {{ $t('distribution.selectTemplate') }}</el-button>
        <a
          href="javascript:void(0);"
          style="margin: 0 20px;"
        >{{ $t('distribution.refresh') }}</a>
        <a
          href="javascript:void(0);"
          @click="templateHandler"
        >{{ $t('distribution.addTemplate') }}</a>
      </el-form-item>

      <!-- 展开更多配置 -->
      <div
        v-if="form.status === 1"
        @click="handleToChangeArror"
        class="arrorContent"
      >
        <div
          v-if="!arrorFlag"
          class="arror"
        >
          {{ $t('seckill.openConfigure') }}&nbsp;<img :src="arrowArr[0].img_1">
        </div>
        <div
          v-if="arrorFlag"
          class="arror"
        >
          {{ $t('seckill.closeConfigure') }}&nbsp;<img :src="arrowArr[1].img_2">
        </div>
      </div>

      <div v-if="form.status === 1 && arrorFlag">
        <p class="titleContent">返利提现设置</p>
        <el-form-item label="返利提现开关：">
          <el-switch
            v-model="form.withdraw_status"
            :active-value='1'
            :inactive-value='0'
            style="width: 60px;"
          ></el-switch>
          <span style="color: red;">注：开启提现功能开关前，请阅读 <a
              href="javascript:void(0);"
              @click="optionHandler"
            >《返利提现配置操作说明》</a></span>
          <div class="text">
            开关开启，分销员推广返利获得的佣金可提现到微信钱包，分销员在小程序发起返利申请，需后台审核通过才可提现到账
          </div>
          <div v-if="form.withdraw_status === 1">
            <el-radio
              v-model="form.withdraw_source"
              label="wx_mini"
            >小程序</el-radio>
            <p class="text">注意：使用返利提现功能，请确保小程序已开通微信支付，否则不可提现 <a
                href="javascript:void(0);"
                @click="dealClickHandler"
              >去配置</a></p>
            <el-radio
              v-model="form.withdraw_source"
              label="wx_open"
            >公众号</el-radio>
            <p class="text">注意：使用返利提现功能，请确保小程序已绑定认证服务号并配置相关支付信息，否则不可提现，未关注公众号的用户将会提现失败
              <a
                href="javascript:void(0);"
                @click="officialHandler"
              >去配置</a></p>
          </div>
        </el-form-item>

        <el-form-item label="返利最小提现金额：">
          <el-input
            style="width: 100px;"
            v-model="form.withdraw_cash"
          ></el-input> 元
          <div class="text">
            分销员发起返利提现，单次申请最小提现金额。为防止分销员提现过于频繁，请设置单次最小提现金额。
          </div>
        </el-form-item>

        <p class="titleContent">分销中心推广海报背景图</p>

        <el-form-item>
          <div class="leftContent">
            <div
              class="leftTop"
              :style="{ 'backgroundImage' : this.form.bg_img !== '' ? 'url(' + this.form.bg_img + ')' : 'url(' + this.defaultValue +  ')' }"
            >
              <div class="userInfo">
                <div class="leftInfo">
                  <img
                    src="http://mpdevimg2.weipubao.cn/image/admin/user_touxiang.png"
                    alt=""
                  >
                </div>
                <div class="rightInfo">
                  <p>昵称</p>
                  <p> {{ this.form.rebate_center_name }} </p>
                </div>
              </div>
            </div>
            <div class="leftBottom">
              <span class="codeArea">二维码区域</span>
              <img
                class="codeTips"
                src="http://mpdevimg2.weipubao.cn/image/admin/usr_codes.png"
                alt=""
              >
            </div>
          </div>
          <div class="rightContent">
            <div>
              <span class="rightLabel">邀请文案：</span>
              <el-input
                v-model="form.rebate_center_name"
                style="width: 300px;"
              ></el-input>
            </div>
            <div style="margin-top: 20px;">
              <span class="rightLabel">海报背景图：</span>
              <div class="defaultBg">
                <span>默认背景选择：</span>
                <el-select
                  v-model="defaultValue"
                  @change="selectChange"
                >
                  <el-option
                    v-for="(item, index) in options"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </div>
              <div class="customBg">
                <span>上传背景图片：</span>
                <div
                  class="custom"
                  @click="handleToCallImgDialog"
                >
                  <img
                    v-if="form.bg_img === ''"
                    src="http://jmpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png"
                    alt=""
                  >
                  <img
                    v-if="form.bg_img !== ''"
                    :src="form.bg_img"
                    alt=""
                    class="customImg"
                  >
                </div>
                <span>图片尺寸640px*640px</span>
              </div>
            </div>

          </div>
        </el-form-item>

      </div>
    </el-form>

    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="addDistribution()"
      >保存</el-button>
    </div>

    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :chooseGoodsBack="goodsInfo"
    />

    <!-- 选择模板弹窗 -->
    <!-- <el-dialog
      title="选择页面"
      :visible.sync="templateDialog"
      width="50%"
      :close-on-click-modal="false"
      center
    >
      <div style="width: 100%; text-align: center;">
        <el-form
          ref="formDialog"
          :model="formDialog"
          label-width="90px"
        >
          <el-row>
            <el-col :span="8">
              <el-form-item label="页面名称：">
                <el-input
                  v-model="formDialog.pageName"
                  style="width: 150px;"
                  size="small"
                  placeholder="请输入页面名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="页面分类：">
                <el-input
                  v-model="formDialog.catId"
                  style="width: 150px;"
                  size="small"
                  placeholder="请选择页面分类"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-button
                type="primary"
                size="small"
                style="margin-top: 5px;"
              >搜索</el-button>
            </el-col>
          </el-row>

        </el-form>

        <el-table
          ref="templateData"
          :data="templateData"
          border
          highlight-current-row
          @current-change="handleCurrentChange"
          height="300px"
          style="width: 100%;"
        >
          <el-table-column
            prop="pageName"
            label="页面名称"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="typeText"
            label="是否首页"
            align="center"
          ></el-table-column>
        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="getTemplateData"
        />
      </div>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="templateDialog = false"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="sureClickHandler()"
        >确 定</el-button>
      </span>
    </el-dialog> -->

    <!-- 选择图片弹窗 -->
    <ImageDalog
      pageIndex='imageDalog'
      :tuneUp='tuneUp'
      :isDraggable='isDraggable'
      :imageSize='imageSize'
      @handleSelectImg='handleSelectImg'
    />

    <!-- 选择模板弹窗 -->
    <SelectTemplate
      :tuneUpSelectTemplate='tuneUpSelectTemplate'
      @handleSelectTemplate='handleSelectTemplate'
    />

  </div>
</template>

<script>
// 引入组件
import { setDistribution, getDistribution, getSelectGoods, getSelectTemplate } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'), // 选择商品弹窗
    ImageDalog: () => import('@/components/admin/imageDalog'), // 选择图片弹窗
    // Pagination: () => import('@/components/admin/pagination/pagination.vue'), // 分页
    SelectTemplate: () => import('./selectTemplate') // 选择模板弹窗
  },
  data () {
    return {
      imageHost: 'http://jmpdevimg.weipubao.cn/',
      vaildDate: null, // 有效期天数
      protectDate: null, // 保护期天数
      form: {
        status: 1, // 分销开关
        judge_status: 1, // 分销员审核开关
        invitationCode: 1, // 邀请码
        activation: 1, // 是否需要提交个人信息
        activation_cfg: [], // 个人信息内容
        rank_status: 1, // 分销员排名开关
        vaild: 0, // 返利有效期
        protect_date: 0, // 分销员保护期
        desc: '分销中心', // 分销中心页面名称
        distribution_goods_type: 2, // 推荐商品(自定义)
        recommend_goods_id: '', // 推荐商品ID
        rebate_page_id: '', // 推广模版文案id
        withdraw_status: 1, // 返利体现开关
        withdraw_source: 'wx_mini', // 返利方式
        withdraw_cash: null, // 返利最小提现金额
        rebate_center_name: '分享给你一个好物店铺快来购物吧！', // 邀请文案
        bg_img: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_1.jpg' // 海报背景图
      },
      // 分销员信息
      checkedList: ['真实姓名', '手机号', '身份证号码', '性别', '生日', '婚姻状况', '教育程度', '所在行业', '所在地', '备注', '图片上传'],
      // 推荐商品表格
      tableData: [],
      arrorFlag: true, // 展开更多配置
      // 展开设置箭头
      arrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      tuneUpChooseGoods: false, // 商品弹窗
      goodsInfo: [], // 商品弹窗回调数据
      // 默认背景图
      defaultValue: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_1.jpg',
      options: [{
        label: '背景图1',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_1.jpg'
      }, {
        label: '背景图2',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_2.jpg'
      }, {
        label: '背景图3',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_3.jpg'
      }, {
        label: '背景图4',
        value: 'http://mpdevimg2.weipubao.cn/image/admin/dis_bg_4.jpg'
      }],
      tuneUp: false, //  调起添加图片弹窗flag
      imageSize: [640, 640], // 调起添加图片宽高
      isDraggable: false, // 添加商品弹窗是否开启多选底部可拖拽状态
      isAddImgOrChangeFlga: false, // true为添加图片  false为更换列表项中的图片
      // templateDialog: false, // 模板弹窗
      templateRow: {}, // 模板弹窗回调函数
      // formDialog: {
      //   pageName: '',
      //   catId: ''
      // },
      tuneUpSelectTemplate: false,
      pageParams: {}, // 分页
      // requestParams: {},
      // templateData: [], // 模板表格
      tamplateFlag: false // 模板数据显示
    }
  },
  mounted () {
    // 初始化数据
    this.getDistribution()
    // this.getTemplateData()
  },
  methods: {
    // 获取分销配置
    getDistribution () {
      getDistribution().then((res) => {
        if (res.error === 0) {
          this.form = res.content
          // 有效期
          if (this.form.vaild === 0) {
            this.vaildDate = null
          } else {
            this.vaildDate = this.form.vaild
            this.form.vaild = 1
          }
          // 保护期
          if (this.form.protect_date === 0) {
            this.protectDate = null
          } else {
            this.protectDate = this.form.protect_date
            this.form.protect_date = 1
          }
          // 推荐商品ID
          this.form.recommend_goods_id = this.form.recommend_goods_id.split(',')
          this.form.recommend_goods_id = this.form.recommend_goods_id.map(Number)
          // 推荐商品数据回显
          this.goodsInfo = this.form.recommend_goods_id
          let that = this
          for (var j = 0; j < this.goodsInfo.length; j++) {
            that.getSelectGoods(this.goodsInfo[j])
          }
          // 选择模板数据回显
          if (this.form.rebate_page_id !== '') {
            that.getSelectTemplate(this.form.rebate_page_id)
          }

          // 默认背景图
          for (var i = 0; i < this.options.length; i++) {
            if (this.form.bg_img === this.options[i].value) {
              this.defaultValue = this.form.bg_img
            }
          }
        }
      })
    },

    // 获取已选择的商品
    getSelectGoods (id) {
      this.tableData = []
      getSelectGoods({
        goodsId: id
      }).then((res) => {
        if (res.error === 0) {
          this.tableData.push(res.content)
        }
      })
    },

    // 获取已选择的模板
    getSelectTemplate (id) {
      getSelectTemplate({
        pageId: id
      }).then((res) => {
        if (res.error === 0) {
          this.tamplateFlag = true
          this.templateRow.pageId = res.content.page_id
          this.templateRow.pageName = res.content.page_name
          console.log(this.templateRow.pageName, 'current pageName')
          this.templateRow.createTime = res.content.create_time
          this.templateRow.pageType = res.content.page_type
        }
      })
    },

    // 保存分销配置
    addDistribution () {
      // 有效期
      if (this.form.vaild === 1) {
        this.form.vaild = this.vaildDate
      }
      // 保护期
      if (this.form.protect_date === 1) {
        this.form.protect_date = this.protectDate
      }
      this.form.recommend_goods_id = this.goodsInfo.toString()
      this.form.tableData = this.tableData

      setDistribution(this.form).then((res) => {
        if (res.error === 0) {
          this.$message.success({ message: '保存成功!' })
          this.getDistribution()
        }
      })
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 显示商品弹窗
    hanldeToAddGoodS () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },

    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      console.log('选择商品弹窗回调显示:', row)
      this.tableData = row
      this.goodsInfo = []
      this.tableData.map((item, index) => {
        this.goodsInfo.push(item.goodsId)
      })
    },

    // 删除推荐商品
    deleteTable (index) {
      this.tableData.splice(index, 1)
      this.goodsInfo.splice(index, 1)
    },

    // 调起模板弹窗
    chooseTemplate () {
      // this.templateDialog = !this.templateDialog
      this.tuneUpSelectTemplate = !this.tuneUpSelectTemplate
    },

    // 获取模板弹窗表格数据
    // getTemplateData () {
    //   this.requestParams.pageName = this.formDialog.pageName
    //   this.requestParams.catId = this.formDialog.catId
    //   this.requestParams.currentPage = this.pageParams.currentPage
    //   this.requestParams.pageRows = this.pageParams.pageRows
    //   shopDecorateList(this.requestParams).then((res) => {
    //     if (res.error === 0) {
    //       this.pageParams = res.content.page
    //       this.templateData = res.content.dataList
    //       // 表格数据处理
    //       this.templateData.map((item, index) => {
    //         if (item.pageType === 1) {
    //           item.typeText = '是'
    //         } else {
    //           item.typeText = '否'
    //         }
    //       })
    //     }
    //   })
    // },

    // 选中表格数据
    // handleCurrentChange (val) {
    //   this.templateRow = val
    //   this.form.rebate_page_id = val.pageId
    // },

    // // 模板数据回显
    // sureClickHandler () {
    //   this.templateDialog = false
    //   this.tamplateFlag = true
    // },

    // 删除模板
    clearClickHandler () {
      this.templateRow = {}
      this.form.rebate_page_id = ''
      this.tamplateFlag = false
    },

    // 显示图片弹窗
    handleToCallImgDialog () {
      this.tuneUp = !this.tuneUp
    },

    // 添加图片弹窗选中图片数据回传
    handleSelectImg (imgData) {
      this.form.bg_img = imgData.imgUrl
    },

    // 添加选择模板选中数据回传
    handleSelectTemplate (data) {
      this.tamplateFlag = true
      this.templateRow.pageName = data
      console.log(this.templateRow.pageName)
      console.log(data, 'child data')
    },

    // 切换背景图
    selectChange (val) {
      this.form.bg_img = val
    },

    // 跳转推广文案配置
    copyWritingHandler () {
      this.$router.push({ name: 'distribution_copyWriting' })
    },

    // 跳转分销员列表
    listClickHandler () {
      this.$emit('tabChange')
    },

    // 跳转添加模板
    templateHandler () {
      this.$router.push({
        path: '/admin/home/main/decorationHome',
        query: {
          pageId: -1
        }
      })
    },

    // 跳转返利提现配置
    optionHandler () {
      window.open('http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=686&fromuid=1')
    },

    // 跳转交易配置
    dealClickHandler () {
      this.$router.push({ name: 'pay' })
    },

    // 跳转授权公众号
    officialHandler () {
      this.$router.push({
        path: '/admin/home/shopMain',
        query: { change_components: '3' }
      })
    }

  }

}

</script>
<style scoped>
a {
  text-decoration: none;
  color: #5a8bff;
}

.footer {
  position: fixed;
  bottom: 0;
  right: 27px;
  width: 87.8%;
  margin: 0 auto;
  height: 50px;
  line-height: 50px;
  background: #f8f8f8;
  text-align: center;
  z-index: 99;
}

.text {
  color: #999;
  font-size: 12px;
  line-height: 2;
}

.arrorContent {
  padding: 10px 0 30px 25px;
  width: 150px;
}

.arrorContent .arror {
  color: rgb(90, 139, 255);
  cursor: pointer;
}

.titleContent {
  width: 100%;
  height: 40px;
  line-height: 40px;
  background: #f8f8f8;
  padding-left: 20px;
  margin-bottom: 10px;
}

.leftContent {
  width: 330px;
  border: 1px solid #e2e2e2;
  float: left;
  margin-right: 20px;
}

.leftTop {
  background-color: #ddd;
  font-size: 14px;
  color: #919191;
  position: relative;
  height: 330px;
  /* background-image: url(http://mpdevimg2.weipubao.cn/image/admin/dis_bg_1.jpg); */
  background-size: 100%, 100%;
}

.leftTop .imgTips {
  position: absolute;
  top: 180px;
  width: 100%;
  height: 75px;
  text-align: center;
  float: left;
}

.leftTop .userInfo {
  position: absolute;
  width: 100%;
  top: 0;
  padding: 10px;
  background: rgba(255, 255, 255, 0.5);
  font-size: 13px;
}

.userInfo .leftInfo {
  float: left;
  width: 60px;
  height: 60px;
  margin-right: 10px;
}

.userInfo .rightInfo {
  float: left;
  height: 60px;
  width: 230px;
  line-height: 1.5;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.leftBottom {
  padding: 10px 0;
  display: flex;
  justify-content: center;
}

.leftBottom .codeArea {
  width: 75px;
  height: 75px;
  background: #f5f5f5;
  display: inline-block;
  float: left;
  margin-right: 10px;
  text-align: center;
  font-size: 12px;
  line-height: 75px;
}

.leftBottom .codeTips {
  width: 75px;
  height: 75px;
  float: left;
}

.rightContent {
  float: left;
  height: 250px;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #e2e2e2;
  background: #f8f8f8;
}

.rightContent .rightLabel {
  display: inline-block;
  width: 90px;
}

.rightContent .defaultBg {
  display: inline-block;
}

.rightContent .customBg {
  margin-left: 90px;
  margin-top: 20px;
  cursor: pointer;
}

.rightContent .customBg .custom {
  width: 70px;
  height: 70px;
  line-height: 80px;
  display: inline-block;
  border: 1px solid #ccc;
  text-align: center;
}

.rightContent .customBg .custom .customImg {
  width: 100%;
  height: 100%;
}
</style>
