
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
        v-model="mainData.shopType"
        :placeholder="$t('shopList.info.shop_type')"
        size="small"
        class="select-input ml-6"
      >
        <el-option
          v-for="item in type"
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
      header-row-class-name="table-th"
      :data="formTable"
      border
      style="width: 100%"
      height="400"
    >
      <el-table-column
        prop="shopId"
        :label="$t('shopList.table.ID')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="shopType"
        :label="$t('shopList.table.shopID')"
        :formatter="changeShopId"
        align="center"
      >
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
        :formatter="changeShopState"
      >
      </el-table-column>
      <el-table-column
        prop="isEnabled"
        align="center"
        :label="$t('shopList.table.isDisabled')"
        :formatter="changeIsDisabled"
      >
      </el-table-column>
      <el-table-column
        prop="isAuthOk"
        align="center"
        :label="$t('shopList.table.permission')"
      >
      </el-table-column>
      <el-table-column
        prop="userName"
        align="center"
        :label="$t('shopList.table.account')"
      >
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
        :formatter="changeBottom"
      >
      </el-table-column>
      <el-table-column
        prop="specialsetting"
        align="center"
        :label="$t('shopList.table.special')"
      >
      </el-table-column>
      <el-table-column
        prop="operate"
        align="center"
        :label="$t('shopList.table.operating')"
      >
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
import { shopSearchRequest } from '@/api/system/shopList.js'
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
      type: [{
        value: 'v1',
        label: '体验版'
      }, {
        value: 'v2',
        label: '基础版'
      }, {
        value: 'v3',
        label: '高级版'
      }, {
        value: 'v4',
        label: '旗舰版'
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
        hidBottom: ''
      }]
    }
  },

  created () {
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
      for (var i in this.formTable) {
        if (this.formTable[i].isUse === 1) {
          return '使用中'
        } else {
          return '已过期'
        }
      }
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

    // 底部导航文字转化
    changeBottom (row, rol) {
      switch (row.hidBottom) {
        case 0:
          row.hidBottom = '显示'
          break
        case 1:
          row.hidBottom = '隐藏'
          break
      }
      return row.hidBottom
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
          this.formTable = formList

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
</style>
