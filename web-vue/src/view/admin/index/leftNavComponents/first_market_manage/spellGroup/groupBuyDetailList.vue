<template>

    <div>
        <wrapper>

            <el-form label-width="100px">
                <el-row :gutter=24>
                    <el-col :span="6">
                        <el-form-item :label="$t('groupBuy.userMobileNumber')">
                            <el-input
                                    v-model="mobile"
                                    :placeholder="$t('groupBuy.mobileNumber')"
                                    maxlength="11"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item :label="$t('groupBuy.userNickname')">
                            <el-input
                                    v-model="nickname"
                                    :placeholder="$t('groupBuy.nickname')"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item :label="$t('groupBuy.grouponState')">
                            <el-select v-model="stauts">
                                <el-option
                                        v-for="item in stateOptions"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="2" :offset="2">
                        <el-button type="primary" @click="searchData">{{$t('groupBuy.searchDataText')}}
                        </el-button>
                    </el-col>
                </el-row>
            </el-form>
        </wrapper>
        <wrapper>
            <el-main>

                <el-table
                        class="version-manage-table"
                        header-row-class-name="tableHeader"
                        :data="tableData"
                        border
                        style="width: 100%"
                >
                    <el-table-column
                            prop="commanderName"
                            :label="$t('groupBuy.commanderName')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="commanderMobile"
                            :label="$t('groupBuy.commanderMobile')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="username"
                            :label="$t('groupBuy.username')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="mobile"
                            :label="$t('groupBuy.mobile')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="status"
                            :label="$t('groupBuy.grouponState')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="isDefault"
                            :label="$t('groupBuy.isDefault')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="orderSn"
                            :label="$t('groupBuy.orderSn')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="startTime"
                            :label="$t('groupBuy.startTime')"
                            align="center"
                    ></el-table-column>
                    <el-table-column
                            prop="endTime"
                            :label="$t('groupBuy.endTime')"
                            align="center"
                    ></el-table-column>

                </el-table>
            </el-main>
            <pagination
                    :page-params.sync="pageParams"
                    @pagination="initialize"
            />
        </wrapper>
    </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import {detailGroupBuy} from '@/api/admin/marketManage/spellGroup.js'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: {
    wrapper,
    pagination
  },
  data () {
    return {
      mobile: null,
      nickname: null,
      stauts: 0,
      tableData: [],
      pageParams: {}
    }
  },
  computed: {
    stateOptions: function () {
      return this.$t('groupBuy.stateOptions')
    }
  },
  mounted () {
    // 初始化
    this.initialize()
  },
  methods: {
    // 初始化
    initialize () {
      console.log('初始化', this.$route.query.id)
      if (!this.$route.query.id) {
        this.$router.push({path: `/admin/home/main/spellGroup`})
      }
      this.searchData()
    },
    // 查询
    searchData () {
      let obj = {
        nickName: this.nickname,
        mobile: this.mobile,
        stauts: this.stauts,
        activityId: this.$route.query.id,
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      detailGroupBuy(obj).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.tableData = res.content.dataList
      })
    }
  }

}

</script>
<style scoped>

</style>
