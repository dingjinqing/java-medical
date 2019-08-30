<template>
  <div>
    <wrapper>
      <el-row>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="手机号">
              <el-input
                v-model="param.mobile"
                placeholder="请输入手机号"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="用户昵称">
              <el-input
                v-model="param.username"
                placeholder="请输入昵称"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="商品名称">
              <el-input
                v-model="param.goodsName"
                placeholder="请输入商品名称"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="奖励等级">
              <template>
                <el-select
                  v-model="param.rewardLevel"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="3"
          :offset="3"
        >
          <el-form label-width="100px">
            <el-button
              type="primary"
              @click="searchByCondition"
            >查询</el-button>
          </el-form>
        </el-col>
      </el-row>

      <el-row>
        <el-table
          class="version-manage-table"
          header-row-class-name="tableHeader"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="userId"
            label="用户id"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="username"
            label="用户昵称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="goodsName"
            label="分享商品"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="awardLevel"
            label="奖励级别"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="inviteUserNum"
            label="邀请用户总数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="inviteNewUserNum"
            label="邀请新用户数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="rewardType"
            label="活动奖励"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="领取时间"
            align="center"
          >
          </el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <el-col
          :offset="14"
          :span="10"
        >
          <pagination
            :page-params.sync="pageParams"
            @pagination="defaultSeach"
          />
        </el-col>
      </el-row>
    </wrapper>
  </div>
</template>
<script>
import { getReceiveDetail } from '@/api/admin/marketManage/sharePolite.js'
import pagination from '@/components/admin/pagination/pagination.vue'
import wrapper from '@/components/admin/wrapper/wrapper'
export default {
  components: {
    pagination,
    wrapper
  },
  data () {
    return {
      tableData: [],
      param: {
        shareId: this.$route.params.id,
        mobile: '',
        username: '',
        goodsName: '',
        rewardLevel: '',
        currentPage: 1,
        pageRows: 20
      },
      pageParams: {},
      options: [{
        value: 1,
        label: '一级'
      }, {
        value: 2,
        label: '二级'
      }, {
        value: 3,
        label: '三级'
      }]
    }
  },
  mounted () {
    this.langDefault()
    // 页面加载时调用接口初始化数据
    this.defaultSeach()
  },
  methods: {
    defaultSeach () {
      let obj = {
        'shareId': this.$route.params.id,
        'currentPage ': this.pageParams.currentPage,
        'pageRows ': this.pageParams.pageRows
      }
      getReceiveDetail(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },
    created () {
      console.log(this.$router.params)
      if (this.$router.params.flag === true) {
        this.shareId = this.$router.params.id
      }
    },
    // 条件查询
    searchByCondition () {
      console.log(this.param)
      getReceiveDetail(this.param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      //   数组遍历jsv
      data.map((item, index) => {
        switch (item.rewardType) {
          case 1:
            item.rewardType = '积分'
            break
          case 2:
            item.rewardType = '优惠券'
            break
          case 3:
            item.rewardType = '幸运大转盘'
            break
          default:
            item.rewardType = ''
            break
        }
      })
      this.tableData = data
    }
  }
}
</script>
