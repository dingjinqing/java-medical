<template>
  <div>
    <div class="service_list_page">
      <div class="list_info">
        <!-- 服务分类下拉 -->
        <el-select
          size="small"
          v-model="queryParams.catId"
          @change="categroyChangeHandle"
        >
          <el-option
            label="请选择服务分类"
            :value="null"
          ></el-option>
          <el-option
            v-for="item in serviceCats"
            :key="item.catId"
            :label="item.catName"
            :value="item.catId"
          ></el-option>
        </el-select>
        <el-input
          v-model="queryParams.serviceName"
          placeholder="搜索服务"
          style="width: 188px;"
          size="small"
        >
          <i
            class="el-icon-search el-input__icon"
            slot="suffix"
            @click="searchHandle"
          >
          </i>
        </el-input>
        <el-button
          type="primary"
          size="small"
        >查询</el-button>
      </div>
      <div class="list_table">
        <el-table
          ref="serviceTable"
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
            type="selection"
            align="center"
          ></el-table-column>
          <el-table-column
            label="服务名称"
            prop="serviceName"
          >
            <template slot-scope="{ row }">
              <div>
                <el-image
                  class="service_list_img"
                  :src="row.serviceImg"
                  :fit="fit"
                ></el-image>
                <p>{{row.serviceName}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="价格"
            prop="servicePrice"
          ></el-table-column>
          <el-table-column
            label="服务分类"
            prop="catName"
          ></el-table-column>
          <el-table-column
            label="销量"
            prop="saleNum"
          ></el-table-column>
          <el-table-column
            label="添加时间"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            label="服务模式"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="状态"
            prop="serviceType"
          >
            <template slot-scope="row">
              <div>
                {{row.serviceType|formatType}}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
          >
            <template slot-scope="{ row }">
              <div>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit(row.id)"
                  >编辑</span>
                </el-tooltip>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >分享</span>
                </el-tooltip>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >查看评价</span>
                </el-tooltip>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >删除</span>
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
import { getAllServiceCats, getServiceList } from '@/api/admin/storeManage/storemanage/serviceManage'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      storeId: null,
      serviceCats: [],
      queryParams: {
        storeId: '',
        catId: '',
        serviceName: ''
      },
      tableData: [],
      pageParams: {
        storeId: '',
        catId: '',
        serviceName: ''
      }
    }
  },
  created () {
    this.storeId = this.$route.query.id
    this.queryParams.storeId = this.storeId
    this.initServiceSelect()
    this.initDataList()
  },
  filters: {
    formatType (val) {
      if (val === 0) {
        return '无技师'
      } else if (val === 1) {
        return '有技师'
      }
    }
  },
  methods: {
    searchHandle () {
      this.initDataList()
    },
    categroyChangeHandle () {
      this.initDataList()
    },
    edit () {

    },
    initServiceSelect () {
      let params = {
        storeId: this.storeId
      }
      getAllServiceCats(params).then(res => {
        if (res.error === 0) {
          this.serviceCats = res.content
        }
      })
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getServiceList(params).then(res => {
        if (res.error === 0) {
          console.log(res.content)
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.service_list_page {
  margin: 0 25px;
  .iconSpan {
    color: #5a8bff;
    text-decoration: none;
    cursor: pointer !important;
  }
  .list_info {
    padding-bottom: 10px;
    .service_list_img {
      display: inline-block;
      width: 60px;
      height: 60px;
    }
  }
}
</style>
