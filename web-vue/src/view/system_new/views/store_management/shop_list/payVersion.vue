<template>
  <div class="experience-version">
    <div class="select-menu top">
      <el-input
        v-model="mainData.accountKey"
        placeholder="账号ID、公司"
        size="small"
        class="select-input ml-6"
      ></el-input>
      <el-input
        v-model="mainData.keywords"
        placeholder="店铺ID、账户名称、手机号、小程序名"
        size="small"
        class="select-input ml-6"
      ></el-input>
      <el-select
        v-model="mainData.isUse"
        placeholder="选择店铺状态"
        size="small"
        class="select-input ml-6"
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
        placeholder="选择店铺类型"
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
        placeholder="选择店铺标识"
        size="small"
        class="select-input ml-6"
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
        placeholder="选择禁用状态"
        size="small"
        class="select-input ml-6"
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
        placeholder="底部导航"
        size="small"
        class="select-input ml-6"
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
        <span>店铺到期时间</span>
        <el-input
          v-model="mainData.flag"
          placeholder="请选择时间"
          size="small"
          class="select-input ml-6"
        ></el-input>
        <span>至</span>
        <el-input
          v-model="mainData.flag"
          placeholder="请选择时间"
          size="small"
          class="select-input ml-6"
        ></el-input>
      </div>
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="search()"
      >搜索</el-button>
    </div>

    <el-table
      class="experience-log mt-10"
      header-row-class-name="table-th"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="shopId"
        label="账号ID"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="shopType"
        label="店铺ID(店铺类型)"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="shopName"
        align="center"
        label="店铺名称"
      >
      </el-table-column>
      <el-table-column
        prop="nickName"
        align="center"
        label="小程序名称(公司)"
      >
      </el-table-column>
      <el-table-column
        prop="mobile"
        align="center"
        label="手机号"
      >
      </el-table-column>
      <el-table-column
        prop="created"
        align="center"
        label="创建时间"
      >
      </el-table-column>
      <el-table-column
        prop="expireTime"
        align="center"
        label="到期时间(店铺状态)"
      >
      </el-table-column>
      <el-table-column
        prop="isEnabled"
        align="center"
        label="是否禁用"
      >
      </el-table-column>
      <el-table-column
        prop="isAuthOk"
        align="center"
        label="小程序授权"
      >
      </el-table-column>
      <el-table-column
        prop="userName"
        align="center"
        label="所属账号"
      >
      </el-table-column>
      <el-table-column
        prop="renewMoney"
        align="center"
        label="续费总金额"
      >
      </el-table-column>
      <el-table-column
        prop="shopFlag"
        align="center"
        label="店铺标识"
      >
      </el-table-column>
      <el-table-column
        prop="hidBottom"
        align="center"
        label="底部导航"
      >
      </el-table-column>
      <el-table-column
        prop="specialsetting"
        align="center"
        label="特殊配置"
      >
      </el-table-column>
      <el-table-column
        prop="operate"
        align="center"
        label="操作"
      >
      </el-table-column>
    </el-table>

    <div class="footer clearfixed pagination-wrap">
      <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage3"
        layout="prev, pager, next, jumper"
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
        label: '使用中'
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
        hidBottm: ''
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
        hidBottm: ''
      }]
    }
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.searchAccount()
    },

    // 店铺列表查询
    search () {
      let obj = {
        'accountKey': '2',
        'keywords': '740296',
        'isUse': '1',
        'shopType': 'v3',
        'shopFlag': '2',
        'isEnabled': '0',
        'hidBottom': '0',
        'currentPage': this.currentPage3,
        'pageRows': 10
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
</style>
