<template>
  <div>
    <section class="info_container">
      <el-row>
        <el-col :span="5">
          <el-form label-width="80px">
            <el-form-item :label="$t('receiveDetails.mobile')+'：'">
              <el-input
                v-model="param.mobile"
                :placeholder="$t('receiveDetails.placeholderMobile')"
                size="small"
                clearable
                class="input_width"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="5">
          <el-form
            label-width="80px"
            class="right_distance"
          >
            <el-form-item :label="$t('receiveDetails.username')+'：'">
              <el-input
                v-model="param.username"
                :placeholder="$t('receiveDetails.placeholderName')"
                size="small"
                clearable
                class="input_width"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="5">
          <el-form label-width="80px">
            <el-form-item :label="$t('receiveDetails.goodsName')+'：'">
              <el-input
                v-model="param.goodsName"
                :placeholder="$t('receiveDetails.placeholderGoodsName')"
                size="small"
                clearable
                class="input_width"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="5">
          <el-form label-width="80px">
            <el-form-item :label="$t('receiveDetails.awardLevel')+'：'">
              <template>
                <el-select
                  v-model="param.rewardLevel"
                  :placeholder="$t('receiveDetails.placeholderLevel')"
                  size="small"
                  clearable
                  class="input_width"
                >
                  <el-option
                    v-for="item in levelOptions"
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
          style="margin-top:5px;"
        >
          <el-form label-width="80px">
            <el-button
              type="primary"
              @click="searchByCondition"
              size="small"
            >{{$t('receiveDetails.inquire')}}</el-button>
          </el-form>
        </el-col>
      </el-row>
    </section>

    <wrapper>
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
            :label="$t('receiveDetails.userId')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="username"
            :label="$t('receiveDetails.username')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('receiveDetails.mobile')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="goodsName"
            :label="$t('receiveDetails.goodsName')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="awardLevel"
            :label="$t('receiveDetails.awardLevel')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="inviteUserNum"
            :label="$t('receiveDetails.inviteUserNum')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="inviteNewUserNum"
            :label="$t('receiveDetails.inviteNewUserNum')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="rewardType"
            :label="$t('receiveDetails.rewardType')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('receiveDetails.createTime')"
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
      pageParams: {}
    }
  },
  computed: {
    levelOptions: function () {
      return this.$t('receiveDetails.options')
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
            item.rewardType = item.score + '积分'
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
        switch (item.awardLevel) {
          case 1:
            item.awardLevel = '一级'
            break
          case 2:
            item.awardLevel = '二级'
            break
          case 3:
            item.awardLevel = '三级'
            break
          default:
            item.awardLevel = ''
            break
        }
      })
      this.tableData = data
    }
  }
}
</script>
<style lang="scss" scoped>
.info_container {
  margin: 10px;
  background: #fff;
  padding: 25px 15px 10px;
}
/deep/ .el-form-item__label {
  padding-right: 0;
}
.input_width {
  width: 170px;
}
.right_distance {
  margin-right: 30px;
}
/deep/ .el-row {
  margin-bottom: 0;
}
</style>
