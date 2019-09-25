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
            v-model="mainData.flag"
            type="datetime"
            :placeholder="$t('shopList.info.account_info8')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <span style="padding: 10px 10px 0">{{$t('shopList.info.account_info9')}}</span>
        <div class="block">
          <el-date-picker
            size="small"
            v-model="mainData.flag"
            type="datetime"
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

    <el-table
      class="experience-log mt-10 formTable"
      :data="formTable"
      header-row-class-name="table-th"
      border
      style="width: 120%;cellspacing='1';cellpadding='3';"
      max-width='120%'
      :height="tableHeight"
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
          <div v-if="scope.row.shopType==='v1'">基础版</div>
          <div v-if="scope.row.shopType==='v2'">中极版</div>
          <div v-if="scope.row.shopType==='v3'">高级版</div>
          <div v-if="scope.row.shopType==='v4'">旗舰版</div>
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
            {{scope.row.expireTime.substring(0, 10)}}
          </div>
          <div v-if="scope.row.shopExpireStatus==='0'">
            使用中
          </div>
          <div v-if="scope.row.shopExpireStatus==='1'">
            已过期
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
              class="xbutton"
              type="text"
              @click="btnEdit(scope.row)"
            > 编辑</el-button>
            <br>
            <el-button
              class="xbutton"
              type="text"
              @click="btnRenew(scope.row.shopId,0)"
            > 续费</el-button>
            <br>
            <el-button
              class="xbutton"
              type="text"
              @click="btnShowVersion(scope.row.shopId,0)"
            > 版本权限</el-button>
            <br>
            <el-button
              class="xbutton"
              type="text"
              @click="ShowRenew(scope.row.shopId,0)"
            > 查看续费</el-button>
            <br>
            <el-button
              class="xbutton"
              type="text"
              @click="changeBottom(scope.row.shopId,0)"
            > 运营数据</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

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
  </div>
</template>

<script>
import { shopSearchRequest, upEnableRequest, upBottomRequest } from '@/api/system/shopList.js'
export default {
  name: 'experienceVersion',
  data () {
    return {
      state: [{
        value: '1',
        label: '未过期'
      }, {
        value: '3',
        label: '即将过期'
      }, {
        value: '2',
        label: '已过期'
      }],
      flag: [{
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
        value: '0',
        label: '未禁用'
      }, {
        value: '1',
        label: '已禁用'
      }],
      bottom: [{
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
      showSpecialInfo: [],
      tableHeight: document.body.clientHeight - 381
    }
  },

  created () {
    this.tableHeight = document.body.clientHeight - 381
    if (this.tableHeight < 0) {
      this.tableHeight = 400
    }
    this.search()
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
          row.shopType = '基础版'
          break
        case 'v2':
          row.shopType = '中极版'
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
      let obj = {
        'currentPage': this.currentPage3,
        'pageRows': 20
      }

      console.log(this.mainData)
      let parame = Object.assign(obj, this.mainData)
      console.log(parame)
      shopSearchRequest(parame).then((res) => {
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
              console.log('进来', specialInfoList.length)
              for (var n = 0; n < specialInfoList.length; n++) {
                console.log('循环')
                console.log(this.specialInfoExchange(specialInfoList[n]))
                this.showSpecialInfo.push(this.specialInfoExchange(specialInfoList[n]))
              }
            }
            console.log(this.showSpecialInfo)
            this.formTable[i].showSpecialInfo = this.showSpecialInfo
            this.showSpecialInfo = []
          }
          console.log('处理结束')
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
</style>
