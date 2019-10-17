<template>
  <div>
    <div class="technician_list_page">
      <div class="list_info">
        <label>
          技师姓名：
          <el-input
            size="small"
            class="filter_input"
            placeholder="技师名称"
            v-model="queryParams.technicianName"
          ></el-input>
        </label>
        <!-- 服务分类下拉 -->
        <el-select
          size="small"
          v-model="queryParams.groupId"
          @change="initDataList"
        >
          <el-option
            label="请选择技师分组"
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
          placeholder="技师电话"
          style="width: 188px;"
          size="small"
          v-model="queryParams.technicianMobile"
        >
        </el-input>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >查询</el-button>
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
            label="技师名称"
            prop="technicianName"
          >
          </el-table-column>
          <el-table-column
            label="技师分组"
            prop="seviceGroup"
          >
            <template slot-scope="{row}">
              {{row.seviceGroup.groupName}}
            </template>
          </el-table-column>
          <el-table-column
            label="联系电话"
            prop="technicianMobile"
          ></el-table-column>
          <el-table-column
            label="服务项目"
            prop="serviceList"
            :formatter="formatServiceList"
          ></el-table-column>
          <el-table-column
            label="添加时间"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            label="介绍"
            prop="technicianIntroduce"
          ></el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row }">
              <div>
                <el-tooltip content="排班管理">
                  <span
                    class="iconSpan"
                    @click="edit('scheduling', row)"
                  >排班管理</span>
                </el-tooltip>
                <el-tooltip content="编辑">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row)"
                  >编辑</span>
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
      technicianCats: [
        { id: 1, name: '添加' }
      ],
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
          break
        case 'edit':
          this.$router.push({
            name: 'store_storemanage_technician_add',
            query: {
              id: this.queryParams.storeId,
              technicianId: row.id
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
      width: 130px;
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
