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
          @click="searchHandle"
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
          @selection-change="selectChangeHandle"
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
                  style="width: 60px; height: 60px;"
                  :src="row.serviceImg|formatImgUrl"
                  fit="cover"
                ></el-image>
              </div>
              <p>{{row.serviceName}}</p>
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
            width="180"
          ></el-table-column>
          <el-table-column
            label="服务模式"
            prop="serviceType"
            :formatter="formatType"
          ></el-table-column>
          <el-table-column
            label="状态"
            prop="serviceShelf"
            :formatter="formatShelf"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row }">
              <div style="word-break:keep-all; font-size:13px;">
                <el-tooltip content="编辑">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row)"
                  >编辑</span>
                </el-tooltip>
                <el-tooltip
                  v-if="row.serviceShelf === 1"
                  content="下架"
                >
                  <span
                    class="iconSpan"
                    @click="edit('off', row)"
                  >下架</span>
                </el-tooltip>
                <el-tooltip
                  v-if="row.serviceShelf === 0"
                  content="上架"
                >
                  <span
                    class="iconSpan"
                    @click="edit('on', row)"
                  >上架</span>
                </el-tooltip>
                <el-tooltip content="分享">
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >分享</span>
                </el-tooltip>
                <el-tooltip content="查看评价">
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >查看评价</span>
                </el-tooltip>
                <el-tooltip content="删除">
                  <span
                    class="iconSpan"
                    @click="edit('delete', row)"
                  >删除</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="table-page">
          <div>
            <el-button
              size="small"
              @click="shelfHandle"
            >上架</el-button>
            <el-button
              size="small"
              @click="obtainedHandle"
            >下架</el-button>
          </div>
          <div>
            <pagination
              :page-params.sync="pageParams"
              @pagination="initDataList"
            ></pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllServiceCats, getServiceList, deleteService, onService, offService } from '@/api/admin/storeManage/storemanage/serviceManage'
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
      pageParams: {},
      selects: []
    }
  },
  created () {
    this.storeId = this.$route.query.id
    this.queryParams.storeId = this.storeId
    this.initServiceSelect()
    this.initDataList()
  },
  filters: {
    formatImgUrl (serviceImg) {
      const imgs = JSON.parse(serviceImg)
      return imgs[0]
    }
  },
  methods: {
    formatType (row) {
      if (row.serviceType === 0) {
        return '无技师'
      } else if (row.serviceType === 1) {
        return '有技师'
      } else {
        return ''
      }
    },
    formatShelf (row) {
      if (row.serviceShelf === 0) {
        return '下架'
      } else if (row.serviceShelf === 1) {
        return '上架'
      } else {
        return ''
      }
    },
    searchHandle () {
      this.initDataList()
    },
    categroyChangeHandle () {
      this.initDataList()
    },
    edit (operate, row) {
      let that = this
      switch (operate) {
        case 'edit':
          this.$router.push({
            name: 'store_storemanage_service_add',
            query: {
              id: that.storeId,
              serviceId: row.id,
              businessHours: that.$route.query.businessHours
            }
          })
          break
        case 'delete':
          let params = {
            id: row.id
          }
          deleteService(params).then(res => {
            if (res.error === 0) {
              this.$message.success('删除成功')
              this.initDataList()
            }
          })
          break
        case 'off':
          if (row.serviceShelf === 1) {
            let params = []
            params[0] = row.id
            offService(params).then(res => {
              if (res.error === 0) {
                this.$message.success('下架成功')
                this.initDataList()
              }
            })
          }
          break
        case 'on':
          if (row.serviceShelf === 0) {
            let params = []
            params[0] = row.id
            onService(params).then(res => {
              if (res.error === 0) {
                this.$message.success('上架成功')
                this.initDataList()
              }
            })
          }
          break
      }
    },
    selectChangeHandle (selects) {
      this.selects = selects
    },
    // 上架
    shelfHandle () {
      let params = this.selects.map(item => item.id)
      onService(params).then(res => {
        if (res.error === 0) {
          this.$message.success('上架成功')
          this.initDataList()
        }
      })
    },
    // 下架
    obtainedHandle () {
      let params = this.selects.map(item => item.id)
      offService(params).then(res => {
        if (res.error === 0) {
          this.$message.success('下架成功')
          this.initDataList()
        }
      })
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
          this.tableData = [...res.content.dataList]
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
  }
  .table-page {
    display: flex;
    justify-content: space-between;
    overflow: hidden;
  }
}
</style>
