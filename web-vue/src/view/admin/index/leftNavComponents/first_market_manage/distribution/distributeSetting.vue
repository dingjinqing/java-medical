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
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
        <span v-if="form.status === 1">已开启</span>
        <span v-if="form.status === 0">已关闭</span>
        <div class="text">
          {{ $t('distribution.switchTip') }}
        </div>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.reviewed')"
        v-show="form.status === 1"
      >
        <el-switch
          v-model="form.judge_status"
          :active-value='1'
          :inactive-value='0'
        ></el-switch>
        <span v-if="form.judge_status === 1">已开启</span>
        <span v-if="form.judge_status === 0">已关闭</span>
        <span class="text">({{ $t('distribution.reviewedTip1') }})</span>
        <div class="text">
          {{ $t('distribution.reviewedTip3') }}
        </div>
        <template v-if="form.judge_status === 1">
          <div>
            自动审核：<el-checkbox
              v-model="form.activation"
              :true-label='1'
              :false-label='0'
            >{{ $t('distribution.reviewedInvitation') }}</el-checkbox>
          </div>
          <div>
            设置推广文案：<el-button
              size="small"
              plain
              @click="copyWritingHandler"
            >{{ $t('distribution.reviewedTip2') }}</el-button>
          </div>
          <div>
            <el-switch
              v-model="form.info_status"
              :active-value='1'
              :inactive-value='0'
            ></el-switch>
            <span v-if="form.info_status === 1">已开启</span>
            <span v-if="form.info_status === 0">已关闭</span>
            <span class="text">{{ $t('distribution.reviewedInfo') }}</span>
          </div>

          <!-- 审核信息 -->
          <div
            v-if="form.info_status === 1"
            style="width: 900px;"
          >
            <el-checkbox-group v-model="form.activation_cfg">
              <div>
                <el-checkbox
                  v-for="(item, index) in checkedList1"
                  :key="index"
                  :label="item"
                >{{ item }}</el-checkbox>
              </div>
              <div>
                <el-checkbox
                  v-for="(item, index) in checkedList2"
                  :key="index"
                  :label="item"
                >{{ item }}</el-checkbox>
                <!-- <el-checkbox
                  v-model="form.InvitationCode"
                  :true-label='1'
                  :false-label="0"
                >邀请码</el-checkbox> -->
                <span
                  class="text"
                  style="margin-left: 10px;"
                >{{ $t('distribution.invitationTip1') }} <a
                    href="javascript:void(0);"
                    style="color: red;"
                  >{{ $t('distribution.invitationTip2') }}</a> {{ $t('distribution.invitationTip3') }}
                  <a href="javascript:void(0);">{{ $t('distribution.invitationTip4') }}</a> {{ $t('distribution.invitationTip5') }}</span>
              </div>

            </el-checkbox-group>

            <!-- 自定义激活项 -->
            <div>
              <el-button
                size="small"
                plain
                @click="customHandler"
              >自定义激活项</el-button>
              <div
                v-if="customList.length > 0"
                style="width: 100%; min-height: 20px; border: 1px dashed #ccc;padding: 5px 10px;margin-top: 10px;"
              >
                <div
                  v-for="(item, index) in customList"
                  :key="index"
                >
                  <el-checkbox style="width: 320px;">{{ item.title }}</el-checkbox>
                  <span
                    class="el-icon-edit-outline iconStyle"
                    @click="editCustom(index)"
                  ></span>
                  <span
                    class="el-icon-delete iconStyle"
                    @click="delCustom(index)"
                  ></span>
                  <span
                    class="text"
                    v-if="item.checkbox"
                  >必填</span>
                </div>

              </div>
              <p class="text">分销员分组：勾选后，用户申请成为分销员时，需要选择加入的分销员组，默认选项为其邀请人的分组，若邀请人无分组，则默认选择商家在“分销员分组列表”中设置的默认分组</p>
            </div>
          </div>
        </template>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.ranking')"
        v-show="form.status === 1"
      >
        <el-switch
          v-model="form.rank_status"
          :active-value='1'
          :inactive-value='0'
        ></el-switch>
        <span v-if="form.rank_status === 1">已开启</span>
        <span v-if="form.rank_status === 0">已关闭</span>
        <div class="text">
          {{ $t('distribution.rankingTip') }}
        </div>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.validity')"
        v-show="form.status === 1"
      >
        <el-radio-group v-model="form.vaild">
          <el-radio :label="1">
            <el-input
              size="small"
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
        v-show="form.status === 1"
      >
        <el-radio-group v-model="form.protect_date">
          <el-radio :label="1">
            <el-input
              size="small"
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
        v-show="form.status === 1"
      >
        <el-input
          size="small"
          style="width: 200px"
          v-model="form.desc"
        ></el-input>
      </el-form-item>

      <el-form-item
        :label="$t('distribution.recommendShop')"
        v-show="form.status === 1"
      >
        <el-radio-group v-model="form.distribution_goods_type">
          <el-radio :label="0">{{ $t('distribution.recommendRadio1') }}</el-radio>
          <el-radio :label="1">{{ $t('distribution.recommendRadio2') }}</el-radio>
          <el-radio :label="2">{{ $t('distribution.recommendRadio3') }}</el-radio>
        </el-radio-group>

        <div v-if="form.distribution_goods_type === 2">
          <p class="text">{{ $t('distribution.recommendTip') }}</p>
          <el-button
            size="small"
            @click="hanldeToAddGoodS"
          ><i class="el-icon-plus"></i> {{ $t('distribution.chooseCommodity') }}</el-button>
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
        v-show="form.status === 1"
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

        <el-button
          size="small"
          @click="chooseTemplate"
        ><i class="el-icon-plus"></i> {{ $t('distribution.selectTemplate') }}</el-button>
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
        v-show="form.status === 1"
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

      <div v-show="form.status === 1 && arrorFlag">
        <p class="titleContent">{{ $t('distribution.rebateSettings') }}</p>
        <el-form-item :label="$t('distribution.rebateSettingsSwitch')">
          <el-switch
            v-model="form.withdraw_status"
            :active-value='1'
            :inactive-value='0'
            style="width: 60px;"
          ></el-switch>
          <span v-if="form.withdraw_status === 1">已开启</span>
          <span v-if="form.withdraw_status === 0">已关闭</span>
          <span style="color: red;">{{ $t('distribution.rebateSettingsTip1') }} <a
              href="javascript:void(0);"
              @click="optionHandler"
            >{{ $t('distribution.rebateSettingsTip2') }}</a></span>
          <div class="text">
            {{ $t('distribution.rebateSettingsTip3') }}
          </div>
          <div v-if="form.withdraw_status === 1">
            <el-radio
              v-model="form.withdraw_source"
              label="wx_mini"
            >{{ $t('distribution.rebateRadio1') }}</el-radio>
            <p class="text">{{ $t('distribution.rebateRadioTip1') }} <a
                href="javascript:void(0);"
                @click="dealClickHandler"
              >{{ $t('distribution.rebateRadioTip2') }}</a></p>
            <el-radio
              v-model="form.withdraw_source"
              label="wx_open"
            >{{ $t('distribution.rebateRadio2') }}</el-radio>
            <p class="text">{{ $t('distribution.rebateRadioTip3') }}
              <a
                href="javascript:void(0);"
                @click="officialHandler"
              >{{ $t('distribution.rebateRadioTip2') }}</a></p>
          </div>
        </el-form-item>

        <el-form-item :label="$t('distribution.minRebate')">
          <el-input
            size="small"
            style="width: 100px;"
            v-model="form.withdraw_cash"
          ></el-input> {{ $t('distribution.minUnit') }}
          <div class="text">
            {{ $t('distribution.minRebateTip') }}
          </div>
        </el-form-item>

        <p class="titleContent">{{ $t('distribution.rebateBg') }}</p>

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
                  <p>{{ $t('distribution.rebateNickname') }}</p>
                  <p> {{ this.form.rebate_center_name }} </p>
                </div>
              </div>
            </div>
            <div class="leftBottom">
              <span class="codeArea">{{ $t('distribution.rebateCode') }}</span>
              <img
                class="codeTips"
                src="http://mpdevimg2.weipubao.cn/image/admin/usr_codes.png"
                alt=""
              >
            </div>
          </div>
          <div class="rightContent">
            <div>
              <span class="rightLabel">{{ $t('distribution.rebateWriting') }}</span>
              <el-input
                size="small"
                v-model="form.rebate_center_name"
                class="inputWidth"
              ></el-input>
            </div>
            <div style="margin-top: 20px;">
              <span class="rightLabel">{{ $t('distribution.rebateImg') }}</span>
              <div class="defaultBg">
                <span>{{ $t('distribution.customSelect') }}</span>
                <el-select
                  size="small"
                  class="inputWidth"
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
                <span>{{ $t('distribution.uploadSelect') }}</span>
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
                <span>{{ $t('distribution.imgTip') }}</span>
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
      >{{ $t('distribution.rebateSave') }}</el-button>
    </div>

    <!-- 自定义激活项弹窗 -->
    <el-dialog
      title="自定义激活项"
      :visible.sync="customDialogVisible"
      width="30%"
      center
      :close-on-click-modal="false"
    >
      <div>
        <el-form
          ref="customForm"
          :model="customForm"
          label-width="21%"
          :label-position="'right'"
        >
          <el-form-item label="选项类型：">
            <el-radio-group v-model="customForm.radio1">
              <el-radio :label="0">单选</el-radio>
              <el-radio :label="1">多选</el-radio>
              <el-radio :label="2">文本</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="标题：">
            <el-input
              size="small"
              maxlength="20"
              show-word-limit
              v-model="customForm.title"
              class="inputWidth"
            ></el-input>
            <span class="text">最多可填写20个字</span>
          </el-form-item>
          <div v-if="customForm.radio1 !== 2">
            <div
              v-for="(item, index) in customForm.optionList"
              :key="index"
            >
              <el-form-item :label="'选项' + (index + 1) + '：'">
                <el-input
                  size="small"
                  v-model="item.value"
                  class="inputWidth"
                ></el-input>
                <span
                  v-if="index > 1"
                  class="el-icon-delete iconStyle"
                  @click="deleteOption(index)"
                ></span>
              </el-form-item>
            </div>
            <el-form-item>
              <el-button
                size="small"
                @click="addOption"
                v-if="customForm.optionList.length < 10"
              >添加选项</el-button>
              <span class="text">最多可添加10个选项</span>
            </el-form-item>
          </div>
          <el-form-item label="条件验证：">
            <el-checkbox
              v-model="customForm.checkbox"
              :true-label='1'
              :false-label="0"
            >必填</el-checkbox>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer">
        <el-button
          size="small"
          @click="cancelCustomHandler"
        >取 消</el-button>
        <el-button
          size="small"
          type="primary"
          @click="sureCustomHandler"
        >确 定</el-button>
      </span>
    </el-dialog>

    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :chooseGoodsBack="goodsInfo"
    />

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
      :tuneUpSelectTemplate="tuneUpSelectTemplate"
      @handleSelectTemplate="handleSelectTemplate"
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
    SelectTemplate: () => import('@/components/admin/selectTemplate') // 选择模板弹窗
  },
  data () {
    return {
      imageHost: 'http://jmpdevimg.weipubao.cn/',
      vaildDate: null, // 有效期天数
      protectDate: null, // 保护期天数
      isEdit: false, // 自定义激活项弹窗是否编辑状态
      editIndex: null,
      // 自定义激活项
      customList: [],
      form: {
        status: 0, // 分销开关
        judge_status: 0, // 分销员审核开关
        // 自动审核
        activation: 0, // 是否需要提交个人信息
        // 信息开关
        info_status: 0,
        // 自定义激活项
        // customList: [],
        // invitationCode: 1, // 邀请码
        activation_cfg: [], // 个人信息内容
        rank_status: 0, // 分销员排名开关
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
      checkedList: [],
      checkedList1: [],
      checkedList2: [],
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
      tuneUpSelectTemplate: false,
      templateRow: {}, // 模板弹窗回调函数
      tamplateFlag: false, // 模板数据显示

      customDialogVisible: false, // 自定义激活项弹窗
      customForm: {
        radio1: 0, // 选项类型
        title: '', // 标题
        optionList: [{
          value: ''
        }, {
          value: ''
        }],
        checkbox: 1 // 条件验证
      }
    }
  },
  watch: {
    lang () {
      this.checkedList = this.$t('distribution.checkedList')
      this.checkedList1 = this.checkedList.slice(0, 10)
      this.checkedList2 = this.checkedList.slice(11)
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.getDistribution()
  },
  methods: {
    // 获取分销配置
    getDistribution () {
      getDistribution().then((res) => {
        if (res.error === 0 && res.content) {
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

    // 自定义激活项
    customHandler () {
      this.customDialogVisible = !this.customDialogVisible
      this.isEdit = false
    },

    // 添加选项
    addOption () {
      this.customForm.optionList.push({
        value: ''
      })
    },

    // 删除选项
    deleteOption (index) {
      this.customForm.optionList.splice(index, 1)
    },

    // 确定自定义激活项
    sureCustomHandler () {
      if (!this.isEdit) {
        // 添加
        if (!this.customList) {
          this.customList = []
        }
        this.customList.push(this.customForm)
      } else {
        // 编辑
        this.customList.forEach((item, index) => {
          if (this.editIndex === index) {
            item = this.customForm
          }
        })
      }
      this.customDialogVisible = false
      // 清空弹窗数据
      this.customForm = {
        radio1: 0,
        title: '',
        optionList: [{
          value: ''
        }, {
          value: ''
        }],
        checkbox: 1
      }
    },

    // 取消自定义激活项
    cancelCustomHandler () {
      this.customDialogVisible = false
      // 清空弹窗数据
      this.customForm = {
        radio1: 0,
        title: '',
        optionList: [{
          value: ''
        }, {
          value: ''
        }],
        checkbox: 1
      }
    },

    // 编辑自定义选项
    editCustom (index) {
      this.customDialogVisible = !this.customDialogVisible
      this.isEdit = true
      this.editIndex = index
      this.customList.forEach((val, key) => {
        if (index === key) {
          this.customForm = val
        }
      })
    },

    // 删除自定义选项
    delCustom (index) {
      this.customList.splice(index, 1)
    },

    // 保存分销配置
    addDistribution () {
      // 个人信息
      // if (this.customList.length > 0) {
      //   this.customList.forEach((item, index) => {
      //     if (item.radio1 === 1) {
      //       this.form.activation_cfg.push(item.title)
      //     }
      //   })
      // }

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
          this.$message.success({ message: this.$t('distribution.rebateSaveSuccess') })
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
      this.tuneUpSelectTemplate = !this.tuneUpSelectTemplate
    },

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
      this.templateRow = data
      this.form.rebate_page_id = data.pageId
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
  min-height: 250px;
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

.inputWidth {
  width: 170px;
}

.iconStyle {
  font-size: 16px;
  color: #5a8bff;
  cursor: pointer;
}

/deep/ .el-dialog__body {
  height: 430px;
  overflow-y: auto;
}
</style>
