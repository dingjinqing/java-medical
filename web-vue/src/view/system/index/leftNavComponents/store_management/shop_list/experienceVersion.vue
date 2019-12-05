<template>
  <div class="experience-version">
    <div class="select-menu top">
      <el-input
        v-model="mainData.accountKey"
        :placeholder="$t('shopList.info.account_info1')"
        size="small"
        class="select-input ml-6"
      ></el-input>
      <el-input
        v-model="mainData.keywords"
        :placeholder="$t('shopList.info.account_info2')"
        size="small"
        class="select-input ml-6"
      ></el-input>
      <el-select
        v-model="mainData.isUse"
        :placeholder="$t('shopList.info.account_info3')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in state"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-select
        v-if="shopTypes==='2'"
        v-model="mainData.shopType"
        :placeholder="$t('shopList.selectType')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in shopVersion"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>

      <el-select
        v-model="mainData.shopFlag"
        :placeholder="$t('shopList.info.account_info4')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in flag"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </div>

    <div class="select-menu bottom">
      <el-select
        v-model="mainData.isEnabled"
        :placeholder="$t('shopList.info.account_info5')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in disabled"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-select
        v-model="mainData.hidBottom"
        :placeholder="$t('shopList.info.account_info6')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in bottom"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <div class="timeline">
        <span style="padding: 10px 15px 0 0 ">{{$t('shopList.info.account_info7')}}</span>
        <div class="block">
          <el-date-picker
            size="small"
            v-model="mainData.expireStartTime"
            type="date"
            :placeholder="$t('shopList.info.account_info8')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <span style="padding: 10px 10px 0">{{$t('shopList.info.account_info9')}}</span>
        <div class="block">
          <el-date-picker
            size="small"
            v-model="mainData.expireEndTime"
            type="date"
            :placeholder="$t('shopList.info.account_info8')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
      </div>
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="search()"
      >{{$t('shopList.info.account_info10')}}</el-button>
    </div>
    <div
      class="tableSize"
      id="tableSize"
      :style="{maxWidth:'100%',width: '100%',height:tableHeight+ 'px',overflowY:'scroll',overflowX:'scroll'}"
    >
      <el-table
        class="experience-log mt-10 formTable"
        :data="formTable"
        header-row-class-name="table-th"
        border
        style="width: 120%;cellspacing='1';cellpadding='3';max-width:120%"
      >
        <el-table-column
          prop="sysId"
          :label="$t('shopList.table.ID')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="shopType"
          :label="$t('shopList.table.shopID')"
          align="center"
        >

          <template slot-scope="scope">
            <div>{{scope.row.shopId}}</div>
            <div v-if="scope.row.shopType==='v1'">(体验版)</div>
            <div v-if="scope.row.shopType==='v2'">(基础版)</div>
            <div v-if="scope.row.shopType==='v3'">(高级版)</div>
            <div v-if="scope.row.shopType==='v4'">(旗舰版)</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="shopName"
          align="center"
          :label="$t('shopList.table.shopName')"
        >
        </el-table-column>
        <el-table-column
          prop="nickName"
          align="center"
          :label="$t('shopList.table.wechatName')"
        >
          <template slot-scope="scope">
            <div>{{scope.row.nickName}}</div>
            <div
              style="color: #d2d2d2;"
              v-show="scope.row.principalName!==null"
            >({{scope.row.principalName}})</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="mobile"
          align="center"
          :label="$t('shopList.table.mobile')"
        >
        </el-table-column>
        <el-table-column
          prop="created"
          align="center"
          :label="$t('shopList.table.createTime')"
        >
          <template slot-scope="scope">
            <div>{{moment(scope.row.created).format('YYYY-MM-DD')}}</div>
            <div>{{moment(scope.row.created).format('HH:mm:ss')}}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="expireTime"
          align="center"
          :label="$t('shopList.table.endTime')"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.expireTime===null">
              暂未续费
            </div>
            <div v-if="scope.row.expireTime!==null">
              {{moment(scope.row.expireTime).format('YYYY-MM-DD')}}
            </div>
            <div v-if="scope.row.shopExpireStatus==='0'">
              (使用中)
            </div>
            <div v-if="scope.row.shopExpireStatus==='1'">
              (已过期)
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="isEnabled"
          align="center"
          :label="$t('shopList.table.isDisabled')"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.isEnabled===1">
              <el-button
                type="text"
                @click="changeEnable(scope.row.shopId,1)"
              > 已禁止</el-button>
            </div>
            <div v-if="scope.row.isEnabled===0">
              <el-button
                type="text"
                @click="changeEnable(scope.row.shopId,0)"
              > 未禁止</el-button>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="currencyType"
          align="center"
          :label="$t('shopList.table.currency')"
          :formatter="currencyFormatter"
        >
        </el-table-column>

        <el-table-column
          prop="shopLanguage"
          align="center"
          :label="$t('shopList.table.language')"
          :formatter="shopLanguageFormatter"
        >
        </el-table-column>

        <el-table-column
          prop="isAuthOk"
          align="center"
          :label="$t('shopList.table.permission')"
          :formatter="permissionFormatter"
        >
        </el-table-column>
        <el-table-column
          prop="userName"
          align="center"
          :label="$t('shopList.table.account')"
        >
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="toAccountList(scope.row.userName)"
            > {{scope.row.userName}}</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="renewMoney"
          align="center"
          :label="$t('shopList.table.money')"
        >
        </el-table-column>
        <el-table-column
          prop="shopFlag"
          align="center"
          :label="$t('shopList.table.shopFlag')"
          :formatter="changeShopFlag"
        >
        </el-table-column>
        <el-table-column
          prop="hidBottom"
          align="center"
          :label="$t('shopList.table.bottom')"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.hidBottom===0">
              <el-button
                type="text"
                @click="changeBottom(scope.row.shopId,1)"
              > 显示</el-button>
            </div>
            <div v-if="scope.row.hidBottom===1">
              <el-button
                type="text"
                @click="changeBottom(scope.row.shopId,0)"
              > 隐藏</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="showSpecialInfo"
          align="center"
          :label="$t('shopList.table.special')"
        >
          <template slot-scope="scope">
            <div
              v-for='(item,index) in scope.row.showSpecialInfo'
              :key="index"
            >
              {{item}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="operate"
          align="center"
          :label="$t('shopList.table.operating')"
        >
          <template slot-scope="scope">
            <div>
              <el-button
                v-if="scope.row.nickName!==null"
                class="xbutton"
                type="text"
                @click="handleOperation(scope.row)"
              >查看小程序授权信息</el-button>
              <br>
              <el-button
                class="xbutton"
                type="text"
                @click="btnEdit(scope.row)"
              >编辑</el-button>
              <br>
              <el-button
                class="xbutton"
                type="text"
                @click="btnRenew(scope.row)"
              >续费</el-button>
              <br>
              <el-button
                class="xbutton"
                type="text"
                @click="btnShowVersion(scope.row)"
              >版本权限</el-button>
              <br>
              <el-button
                class="xbutton"
                type="text"
                @click="ShowRenew(scope.row)"
              >查看续费</el-button>
              <br>
              <el-button
                class="xbutton"
                type="text"
                @click="changeBottom(scope.row.shopId)"
              > 运营数据</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="footer clearfixed pagination-wrap">
      <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage3"
        layout="prev, pager, next, jumper"
        :page-count="pageCount"
        :small="pagination_b"
      >
      </el-pagination>
    </div>
    <div
      class="renew"
      v-if="dialogVisible"
    >
      <el-dialog
        title="店铺续费窗口"
        :visible.sync="dialogVisible"
        width="780px"
        center
      >
        <ul>
          <li class="shop_message">
            <label class="fl">店铺名称：</label>
            <span>{{this.renData.shopName}}</span>
            <label for="last_time">上次续费到期时间：</label>
            <span v-if="this.renData.expireTime!==null">{{moment(this.renData.expireTime).format('YYYY-MM-DD')}}</span>
          </li>
          <li>
            <label class="fl">交费类型：</label>
            <el-select
              size="mini"
              v-model="renewTypeValue"
              @change="changeRenew"
              style="width: 96px;"
            >
              <el-option
                v-for="item in renewType"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
            <label
              class="renew_money"
              v-if="renew_money_show"
            ><span>{{this.renewMoneyName}}</span>金额：</label>
            <el-input
              type="text"
              size="mini"
              ref="renewMoney"
              v-if="renew_money_show"
              v-model="renewMoney"
              placeholder="请输入金额"
              style="width:20%"
            ></el-input>
            <label
              class="renew_time"
              v-if="renew_time_show"
            ><span>{{this.renewMoneyName}}</span>时长：</label>
            <el-input
              size="mini"
              v-if="renew_time_show"
              name="year"
              class="time_change"
              v-model="year"
              @change="changeExpireTime"
              :min="0"
              style="width:8%"
            ></el-input> 年 <el-input
              size="mini"
              name="month"
              class="time_change"
              v-model="month"
              @change="changeExpireTime"
              style="width:8%"
              :min="0"
            ></el-input>月
          </li>
          <li>
            <label class="fl">赠送类型：</label>
            <el-select
              size="mini"
              v-model="sendTypeValue"
              @change="changeSend"
              style="width: 96px;"
            >
              <el-option
                v-for="item in sendTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
            <span
              class="send_time_length"
              is_show="1"
            >
              <label v-if="rend_show">赠送时长：</label>
              <el-input
                v-if="rend_show"
                size="mini"
                name="send_year"
                class="time_change"
                v-model="send_year"
                @change="changeExpireTime"
                style="width:8%"
                :min="0"
              ></el-input><span v-if="rend_show">年</span>
              <el-input
                v-if="rend_show"
                size="mini"
                name="send_month"
                class="time_change"
                v-model="send_month"
                @change="changeExpireTime"
                style="width:8%"
                :min="0"
              ></el-input><span v-if="rend_show">月</span>
            </span>

            <span
              v-if="send_function_show"
              class="send_function"
              style="margin-left: 4%;"
            >
              <el-input
                size="mini"
                type="button"
                value="选择功能"
                style="width:12%"
                class="bt_sel"
              ></el-input>
              <span></span>
            </span>

          </li>
          <li style="margin-left: 2%;">
            <label class="fl">有效期：</label>
            <el-date-picker
              v-model="newExpire_time"
              ref="newExpire_time"
              size="mini"
              type="date"
              placeholder="到期日期"
              value-format="yyyy-MM-dd"
              style="width:20%"
            >
            </el-date-picker>
          </li>
          <li style="margin-left: 4%;">
            <label class="fl">备注：</label>
            <el-input
              style="width:20%"
              size="mini"
              type="text"
              ref="renewDesc"
              v-model="renewDesc"
              name="renew_desc"
            ></el-input>
          </li>
        </ul>
        <div style="margin-bottom: 10px;margin-right: 20px;text-align: right;">
          <el-button
            type="primary"
            size="medium"
            @click="beforeButton()"
          >确 定</el-button>
          <el-button
            @click="dialogVisible = false"
            size="medium"
          >取 消</el-button>
        </div>

      </el-dialog>
    </div>
  </div>
</template>

<script>
import { shopSearchRequest, upEnableRequest, upBottomRequest, renewShopRequest } from '@/api/system/shopList.js'
export default {
  name: 'experienceVersion',
  components: {
    shopRenenwList: () => import('./shopRenenwList')
  },
  data () {
    return {
      state: [
        {
          value: '',
          label: '选择店铺状态'
        }, {
          value: '1',
          label: '未过期'
        }, {
          value: '3',
          label: '即将过期'
        }, {
          value: '2',
          label: '已过期'
        }],
      flag: [
        {
          value: '',
          label: '选择店铺标识'
        }, {
          value: '0',
          label: '店+'
        }, {
          value: '1',
          label: '欧派'
        }, {
          value: '2',
          label: '寺库'
        }],
      disabled: [{
        value: '',
        label: '选择禁用状态'
      }, {
        value: '0',
        label: '未禁用'
      }, {
        value: '1',
        label: '已禁用'
      }],
      bottom: [{
        value: '',
        label: '底部导航'
      }, {
        value: '0',
        label: '显示'
      }, {
        value: '1',
        label: '隐藏'
      }],
      value: '',
      text: '',
      totalRows: null,
      pageRows: '',
      currentPage: '',
      currentPage3: 1,
      pageCount: null,
      pagination_b: true,
      mainData: {
        currentPage3: 1,
        accountKey: '',
        keywords: '',
        isUse: '',
        shopType: '',
        shopFlag: '',
        isEnabled: '',
        hidBottom: ''
      },
      formTable: [{
        shopId: '',
        shopType: '',
        shopName: '',
        nickName: '',
        mobile: '',
        created: '',
        isUse: '',
        expireTime: '',
        isEnabled: '',
        isAuthOK: '',
        userName: '',
        renewMoney: '',
        shopFlag: '',
        hidBottom: '',
        notice: '',
        specialInfo: [],
        showSpecialInfo: []
      }],
      sendTypes: [{
        value: 0,
        label: '无'
      },
      {
        value: 1,
        label: '时间'
      },
      {
        value: 2,
        label: '功能'
      }
      ],
      dialogVisible: false,
      showSpecialInfo: [],
      renData: null,
      renewTypeValue: 1,
      renewType: this.$t('shopList.renew_Type'),
      renewMoneyName: '续费',
      renew_money_show: true,
      renew_time_show: true,
      sendTypeValue: 0,
      year: 0,
      month: 0,
      send_year: 0,
      send_month: 0,
      newExpire_time: null,
      rend_show: false,
      send_function_show: false,
      renewDesc: null,
      renewMoney: null,
      shopTypes: '2',
      shopVersion: [{
        value: '',
        label: '选择店铺类型'
      },
      {
        value: 'v2',
        label: '基础版'
      },
      {
        value: 'v3',
        label: '高级版'
      },
      {
        value: 'v4',
        label: '旗舰版'
      }
      ],
      tableHeight: document.documentElement.clientHeight - 370,
      languageType: this.$t('shopList.language_type'),
      currencyType: this.$t('shopList.currency_type')
    }
  },
  props: ['shopType'],
  mounted () {
    this.tableHeight = document.documentElement.clientHeight - 370
    if (this.tableHeight < 0) {
      this.tableHeight = 400
    }
    console.log('高度')
    console.log(this.tableHeight)
    this.search()
    // document.getElementById('tableSize').style.height = this.tableHeight + 'px'
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.search()
    },

    // 选择店铺类型文字转化
    changeShopId (row, rol) {
      switch (row.shopType) {
        case 'v1':
          row.shopType = '体验版'
          break
        case 'v2':
          row.shopType = '基础版'
          break
        case 'v3':
          row.shopType = '高级版'
          break
        case 'v4':
          row.shopType = '旗舰版'
          break
      }
      return row.shopType
    },

    // 选择店铺状态文字转化
    changeShopState (row, rol) {
      switch (row.isUse) {
        case 1:
          row.isUse = '使用中'
          break
        case 2:
          row.isUse = '已过期'
          break
      }
      return row.isUse
    },

    // 店铺标识文案处理
    changeShopFlag (row, rol) {
      switch (row.shopFlag) {
        case 0:
          row.shopFlag = '店+'
          break
        case 1:
          row.shopFlag = '欧派'
          break
        case 2:
          row.shopFlag = '寺库'
          break
      }
      return row.shopFlag
    },

    // 是否禁用状态文字转化
    changeIsDisabled (row, rol) {
      switch (row.isEnabled) {
        case 0:
          row.isEnabled = '未禁用'
          break
        case 1:
          row.isEnabled = '已禁用'
          break
      }
      return row.isEnabled
    },

    // 店铺列表查询
    search () {
      if (!this.isEmpty(this.shopType)) {
        this.shopTypes = this.shopType
      }
      console.log('shopTypes的值')
      console.log(this.shopTypes)

      this.mainData.shopTypes = this.shopTypes
      shopSearchRequest(this.mainData).then((res) => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          let formList = content.dataList
          console.log('formList')
          console.log(formList)
          this.formTable = formList
          for (var i = 0; i < this.formTable.length; i++) {
            var specialInfoList = this.formTable[i].specialInfo
            if (!this.isEmpty(specialInfoList)) {
              for (var n = 0; n < specialInfoList.length; n++) {
                this.showSpecialInfo.push(this.specialInfoExchange(specialInfoList[n]))
              }
            }
            console.log(this.showSpecialInfo)
            this.formTable[i].showSpecialInfo = this.showSpecialInfo
            this.showSpecialInfo = []
          }
          console.log(this.formTable)
          this.currentPage = content.page.currentPage
          this.pageRows = content.page.pageRows
          this.pageCount = content.page.pageCount
          this.totalRows = content.page.totalRows
          this.firstPage = content.page.firstPage
          this.lastPage = content.page.lastPage
          this.nextPage = content.page.nextPage
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    permissionFormatter (row, rol) {
      switch (row.isAuthOk) {
        case 0:
          row.isAuthOkTrane = '未绑定授权'
          break
        case 1:
          row.isAuthOkTrane = '已授权'
          break
        default:
          row.isAuthOkTrane = '未绑定授权'
          break
      }
      return row.isAuthOkTrane
    },
    changeEnable (row, data) {
      let message = '确认要启用么'
      let isEnabled = 'yes'
      if (data === 1) {
        // 确认要启用么
        message = '确认要启用么'
        isEnabled = 'no'
      } if (data === 0) {
        // 确认要禁用么
        message = '确认要禁用么'
        isEnabled = 'yes'
      }
      let parame = {
        'shopId': row,
        'isEnable': isEnabled
      }
      console.log(parame)
      this.$confirm(message, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        upEnableRequest(parame).then((res) => {
          console.log('结果')
          console.log(res)
          if (res.error === 0) {
            this.$message.success(res.message)
            this.search()
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message.info('已取消更改')
      })
    },
    changeBottom (row, data) {
      let message = '确认要隐藏么'
      let hidBottom = 'yes'
      if (data === 0) {
        // 确认要隐藏么
        message = '确认要显示么'
        hidBottom = 'no'
      } if (data === 1) {
        // 确认要显示么
        message = '确认要隐藏么'
        hidBottom = 'yes'
      }
      let parame = {
        'shopId': row,
        'hidBottom': hidBottom
      }
      console.log(parame)
      this.$confirm(message, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        upBottomRequest(parame).then((res) => {
          console.log('结果')
          console.log(res)
          if (res.error === 0) {
            this.$message.success(res.message)
            this.search()
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message.info('已取消更改')
      })
    },
    specialInfoExchange (data) {
      if (data === 'BaseSale') {
        return '初始销量功能开启'
      }
      if (data === 'AddCommentSwitch') {
        return '商家添加评价功能开启'
      }
      if (data === 'ErpStatus') {
        return 'erp已对接'
      }
      if (data === 'SellerAccount') {
        return '微信全链路开启'
      }
      if (data === 'HidBottom') {
        return '隐藏底部功能开启'
      }
      if (data === 'PictureNumPlus') {
        return '图片空间已扩容'
      }

      if (data === 'VideoNumPlus') {
        return '视频空间已扩容'
      }
      if (data === 'DecorateNumPlus') {
        return '页面装修数量已扩容'
      }
    },
    toAccountList (data) {
      this.$router.push(
        {
          name: 'accountList',
          params: {
            toUserName: data
          }
        }
      )
    },
    btnEdit (data) {
      console.log('点了')
      let params = {
        'shopId': data.shopId,
        'userName': data.userName,
        'flag': 4
      }
      this.$emit('sendShopId', params)
    },
    btnRenew (data) {
      this.dialogVisible = true
      this.renData = data
      this.year = 0
      this.month = 0
      this.send_year = 0
      this.send_month = 0
      this.renewDesc = null
      this.renewMoney = null
      this.newExpire_time = null
    },
    changeRenew (data) {
      console.log(data)
      this.renewMoneyName = '续费'
      if (data === 1) {
        this.renewMoneyName = '续费'
      } else if (data === 2) {
        this.renewMoneyName = '试用'
      } else if (data === 3) {
        this.renewMoneyName = '赠送'
      } else if (data === 4) {
        this.renewMoneyName = '退款'
      }
      if (data === 2 || data === 3) {
        this.renew_money_show = false
      } else {
        this.renew_money_show = true
      }
    },
    changeSend (data) {
      console.log(data)
      if (data === 0) {
        this.rend_show = false
        this.send_function_show = false
      } if (data === 1) {
        this.rend_show = true
        this.send_function_show = false
      } if (data === 2) {
        this.rend_show = false
        this.send_function_show = true
      }
    },
    changeExpireTime () {
      let now = this.moment().format('YYYY-MM-DD')
      let newTime = this.moment().add(this.year, 'y').add(this.month, 'M').add(this.send_year, 'y').add(this.send_month, 'M')
      this.newExpire_time = this.moment(newTime).format('YYYY-MM-DD 00:00:00')
      console.log(now)
      console.log(this.newExpire_time)
    },
    beforeButton () {
      if (this.checkData()) {
        this.renewButton()
      }
    },
    checkData () {
      if (this.renew_money_show === true) {
        if (this.isEmpty(this.renewMoney)) {
          this.$message.error('金额不能为空')
          this.$refs.renewMoney.$el.querySelector('input').focus()
          return false
        }
      }
      if (this.isEmpty(this.newExpire_time)) {
        this.$message.error('有效期不能为空')
        this.$refs.newExpire_time.$el.querySelector('input').focus()
        return false
      } if (this.isEmpty(this.renewDesc)) {
        this.$message.error('说明不能为空')
        this.$refs.renewDesc.$el.querySelector('input').focus()
        return false
      }
      return true
    },
    renewButton () {
      let params = {
        'shopId': this.renData.shopId,
        'sysId': this.renData.sysId,
        'mobile': this.renData.mobile,
        'renewMoney': this.renewMoney,
        'expireTime': this.moment(this.newExpire_time).format('YYYY-MM-DD 00:00:00'),
        'renewDesc': this.renewDesc,
        'renewType': this.renewTypeValue,
        'year': this.year,
        'month': this.month,
        'sendType': this.sendTypeValue,
        'sendYear': this.send_year,
        'sendMonth': this.send_month
      }
      console.log(params)
      renewShopRequest(params).then(res => {
        console.log(res)
        this.centerDialogVisible = false
        if (res.error === 0) {
          this.$message.success('保存成功')
          this.dialogVisible = false
          this.search()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleOperation (data) {
      this.$router.push({
        name: 'programManage',
        params: {
          page: 'authMsg',
          appId: data.appId
        }
      })
    },
    ShowRenew (data) {
      console.log('点击续费')
      let params = {
        'shopId': data.shopId,
        'sysId': data.sysId,
        'shopName': data.shopName,
        'flag': 5
      }
      this.$emit('sendShopId', params)
    },
    currencyFormatter (row, rol) {
      for (var i = 0; i < this.currencyType.length; i++) {
        if (this.currencyType[i].value === row.currency) {
          row.currencyTypeTrane = this.currencyType[i].label
        }
      }
      return row.currencyTypeTrane
    },
    shopLanguageFormatter (row, rol) {
      for (var i = 0; i < this.languageType.length; i++) {
        if (this.languageType[i].value === row.shopLanguage) {
          row.shopLanguageTrane = this.languageType[i].label
        }
      }
      return row.shopLanguageTrane
    },
    btnShowVersion (data) {
      console.log('版本权限')
      let params = {
        'shopId': data.shopId,
        'shopType': data.shopType,
        'flag': 6
      }
      this.$emit('sendShopId', params)
    }
  }
}
</script>

<style lang="scss" scoped>
.select-menu {
  display: flex;
  padding: 10px;
  background: #fff;
}
.select-input {
  width: 200px;
}
.footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
.timeline {
  display: flex;
  margin-left: 15px;
  font-size: 14px;
}
/deep/ .xbutton {
  padding: 0;
}
.renew {
  z-index: 9999;
  border: 1px solid #eee;
  background: #ffffff;
  /deep/ .el-dialog__header {
    border-bottom: 1px solid #ccc;
  }
}
.renew label {
  margin-left: 4%;
}

.renew ul li {
  height: 40px;
}

.renew ul li .fl {
  width: 70px;
  text-align: right;
}
.re_title {
  width: 100%;
  border-bottom: 1px solid #ccc;
  padding: 15px 0;
  text-align: center;
  margin-bottom: 20px;
}
</style>
