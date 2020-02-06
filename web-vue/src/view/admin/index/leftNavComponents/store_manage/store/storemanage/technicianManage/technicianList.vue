<template>
  <div>
    <div class="technician_list_page">
      <div class="list_info">
        <label style="font-size: 14px;">
          {{$t('technicianList.technicianName')}}：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.technicianName"
          ></el-input>
        </label>
        <!-- 服务分类下拉 -->
        <el-select
          size="small"
          style="width:170px;"
          v-model="queryParams.groupId"
          @change="initDataList"
        >
          <el-option
            :label="$t('technicianList.selectTip')"
            :value="null"
          ></el-option>
          <el-option
            v-for="item in technicianCats"
            :key="item.groupId"
            :label="item.groupName"
            :value="item.groupId"
          ></el-option>
        </el-select>
        <el-input
          type="tel"
          :placeholder="$t('technicianList.technicianPhone')"
          style="width: 170px;"
          size="small"
          v-model="queryParams.technicianMobile"
        >
        </el-input>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('technicianList.inquire')}}</el-button>
      </div>
      <div class="list_table">
        <el-table
          ref="technicianTable"
          :data="tableData"
          class="tableClass"
          max-height="500"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'border':'none'
          }"
        >
          <el-table-column
            :label="$t('technicianList.technicianName')"
            prop="technicianName"
          >
          </el-table-column>
          <el-table-column
            :label="$t('technicianList.technicianGroup')"
            prop="seviceGroup"
          >
            <template slot-scope="{row}">
              {{row.seviceGroup&&row.seviceGroup.groupName?row.seviceGroup.groupName:''}}
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('technicianList.contactNum')"
            prop="technicianMobile"
          ></el-table-column>
          <el-table-column
            :label="$t('technicianList.serviceItems')"
            prop="serviceList"
            :formatter="formatServiceList"
          ></el-table-column>
          <el-table-column
            :label="$t('technicianList.addTime')"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            :label="$t('technicianList.introduction')"
            prop="technicianIntroduce"
          ></el-table-column>
          <el-table-column
            :label="$t('technicianList.operate')"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row }">
              <div style="margin-top:10px;">
                <el-tooltip :content="$t('technicianList.shiftManagement')">
                  <span
                    class="iconSpan"
                    @click="edit('scheduling', row)"
                  >{{$t('technicianList.shiftManagement')}}</span>
                </el-tooltip>
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row)"
                  >{{$t('technicianList.edit')}}</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="table-page">
          <pagination
            :page-params.sync="pageParams"
            @pagination="initDataList"
          ></pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getTechnicianList, getTechnicianGroup } from '@/api/admin/storeManage/storemanage/technicianManage'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      technicianCats: [],
      queryParams: {
        storeId: '',
        technicianName: '',
        technicianMobile: '',
        groupId: null
      },
      tableData: [],
      pageParams: {}
    }
  },
  created () {
    this.queryParams.storeId = this.$route.query.id
    this.langDefault()
    this.initGroupData()
    this.initDataList()
  },
  methods: {
    initGroupData () {
      let params = {
        storeId: this.queryParams.storeId
      }
      getTechnicianGroup(params).then(res => {
        if (res.error === 0) {
          this.technicianCats = res.content
        }
      })
    },
    formatServiceList (row) {
      let list = row.serviceList.map(item => item.serviceName)
      return list.join(';')
    },
    edit (operate, row) {
      switch (operate) {
        case 'scheduling':
          this.$router.push({
            name: 'schedule_setting',
            query: {
              id: this.queryParams.storeId,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType,
              technicianId: row.id,
              technicianName: row.technicianName
            }
          })
          break
        case 'edit':
          this.$router.push({
            name: 'store_storemanage_technician_add',
            query: {
              id: this.queryParams.storeId,
              technicianId: row.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
      }
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getTechnicianList(params).then(res => {
        if (res.error === 0) {
          this.tableData = [...res.content.dataList]
          this.pageParams = Object.assign({}, res.content.page)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.technician_list_page {
  margin: 0 25px;
  .list_info {
    padding-bottom: 10px;
    .filter_input {
      width: 170px;
    }
    .technician_list_img {
      display: inline-block;
      width: 60px;
      height: 60px;
    }
  }
  .list_table {
    .iconSpan {
      color: #5a8bff;
      text-decoration: none;
      cursor: pointer !important;
    }
  }
}
</style>
