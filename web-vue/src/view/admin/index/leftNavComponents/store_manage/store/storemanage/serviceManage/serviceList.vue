<template>
  <div>
    <div class="service_list_page">
      <div class="list_info">
        <!-- 服务分类下拉 -->
        <el-select
          size="small"
          style="width: 170px;"
          v-model="queryParams.catId"
          @change="categroyChangeHandle"
        >
          <el-option
            :label="$t('serviceList.selectServicePl')"
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
          :placeholder="$t('serviceList.searchPl')"
          style="width: 170px;"
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
        >{{$t('serviceList.inquire')}}</el-button>
      </div>
      <div class="list_table">
        <el-table
          ref="serviceTable"
          :data="tableData"
          class="tableClass"
          height="280"
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
            :label="$t('serviceList.serviceName')"
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
            :label="$t('serviceList.price')"
            prop="servicePrice"
          ></el-table-column>
          <el-table-column
            :label="$t('serviceList.serviceClass')"
            prop="catName"
          ></el-table-column>
          <el-table-column
            :label="$t('serviceList.sales')"
            prop="saleNum"
          ></el-table-column>
          <el-table-column
            :label="$t('serviceList.addTime')"
            prop="createTime"
            width="180"
          ></el-table-column>
          <el-table-column
            :label="$t('serviceList.serviceMode')"
            prop="serviceType"
            :formatter="formatType"
          ></el-table-column>
          <el-table-column
            :label="$t('serviceList.status')"
            prop="serviceShelf"
            :formatter="formatShelf"
          >
          </el-table-column>
          <el-table-column
            :label="$t('serviceList.operate')"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row }">
              <div style="word-break:keep-all; font-size:13px;">
                <el-tooltip :content="$t('serviceList.edit')">
                  <!-- <span
                    class="iconSpan"
                    @click="edit('edit', row)"
                  >{{$t('serviceList.edit')}}</span> -->
                  <span
                    class="el-icon-edit-outline iconSpan"
                    @click="edit('edit', row)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  v-if="row.serviceShelf === 1"
                  :content="$t('serviceList.unShelf')"
                >
                  <!-- <span
                    class="iconSpan"
                    @click="edit('off', row)"
                  >{{$t('serviceList.unShelf')}}</span> -->
                  <!-- 下架 -->
                  <span
                    class="el-icon-bottom iconSpan"
                    @click="edit('off', row)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  v-if="row.serviceShelf === 0"
                  :content="$t('serviceList.shelf')"
                >
                  <!-- <span
                    class="iconSpan"
                    @click="edit('on', row)"
                  >{{$t('serviceList.shelf')}}</span> -->
                  <span
                    class="el-icon-top iconSpan"
                    @click="edit('on', row)"
                  ></span>
                </el-tooltip>
                <el-tooltip :content="$t('serviceList.share')">
                  <!-- <span
                    class="iconSpan"
                    @click="edit('share', row)"
                  >{{$t('serviceList.share')}}</span> -->
                  <span
                    class="el-icon-share iconSpan"
                    @click="edit('share', row)"
                  ></span>
                </el-tooltip>
                <el-tooltip :content="$t('serviceList.view')">
                  <!-- <span
                    class="iconSpan"
                    @click="edit()"
                  >{{$t('serviceList.view')}}</span> -->
                  <span
                    class="el-icon-tickets iconSpan"
                    @click="edit('look', row)"
                  ></span>
                </el-tooltip>
                <el-tooltip :content="$t('serviceList.delete')">
                  <!-- <span
                    class="iconSpan"
                    @click="edit('delete', row)"
                  >{{$t('serviceList.delete')}}</span> -->
                  <span
                    class="el-icon-delete iconSpan"
                    @click="edit('delete', row)"
                  ></span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="table-page">
          <div style="margin-top: 10px;">
            <el-checkbox
              v-model="isSelectAll"
              @change="selectAllChange"
            >全选</el-checkbox>
            <el-button
              size="small"
              @click="shelfHandle"
            >{{$t('serviceList.shelf')}}</el-button>
            <el-button
              size="small"
              @click="obtainedHandle"
            >{{$t('serviceList.unShelf')}}</el-button>
          </div>
          <div>
            <pagination
              :page-params.sync="pageParams"
              @pagination="initDataList"
            ></pagination>
          </div>
        </div>
      </div>
      <!-- 分享 -->
      <el-dialog
        width="320px"
        :visible.sync="shareDialogVisible"
        center
        :title="$t('serviceList.sweepTips')"
      >
        <div class="share_content">
          <div class="share_img_wrap">
            <el-image
              :src="shareImageUrl"
              style="width: 160px; height: 160px;"
            ></el-image>
          </div>
          <div>
            <a
              :href="shareImageUrl"
              download
              class="down_imgs"
            >{{$t('serviceList.downloadQrCode')}}</a>
          </div>
        </div>
        <div class="share_footer">
          <el-input
            ref="shareInput"
            size="small"
            v-model="shareUrl"
          ></el-input>
          <el-button
            type="text"
            @click="selectSharePath"
          >{{$t('serviceList.copy')}}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getAllServiceCats, getServiceList, deleteService, onService, offService, shareService } from '@/api/admin/storeManage/storemanage/serviceManage'
import pagination from '@/components/admin/pagination/pagination'
import vm from '@/main'
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
      selects: [],
      shareDialogVisible: false,
      shareImageUrl: '',
      shareUrl: '',
      isSelectAll: false
    }
  },
  created () {
    this.storeId = this.$route.query.id
    this.queryParams.storeId = this.storeId
    this.langDefault()
    this.initServiceSelect()
    this.initDataList()
  },
  filters: {
    formatImgUrl (serviceImg) {
      if (serviceImg !== '') {
        const imgs = JSON.parse(serviceImg)
        let img = imgs ? vm.$imageHost + '/' + imgs[0] : ''
        return img
      } else {
        return ''
      }
    }
  },
  methods: {
    formatType (row) {
      if (row.serviceType === 0) {
        return this.$t('serviceList.noTechnician')
      } else if (row.serviceType === 1) {
        return this.$t('serviceList.haveTechnician')
      } else {
        return ''
      }
    },
    formatShelf (row) {
      if (row.serviceShelf === 0) {
        return this.$t('serviceList.unShelf')
      } else if (row.serviceShelf === 1) {
        return this.$t('serviceList.shelf')
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
    selectAllChange (val) {
      this.$refs.serviceTable.toggleAllSelection()
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
              this.$message.success(this.$t('serviceList.successDelete'))
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
                this.$message.success(this.$t('serviceList.underSuccess'))
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
                this.$message.success(this.$t('serviceList.successShelf'))
                this.initDataList()
              }
            })
          }
          break
        case 'share':
          let data = {
            serviceId: row.id
          }
          shareService(data).then(res => {
            if (res.error === 0) {
              console.log('share...', res.content)
              that.shareImageUrl = res.content.imageUrl
              that.shareUrl = res.content.pagePath
              that.shareDialogVisible = !that.shareDialogVisible
            }
          })
          break
      }
    },
    selectChangeHandle (selects) {
      if (selects && this.tableData.length === selects.length) {
        this.isSelectAll = true
      } else {
        this.isSelectAll = false
      }
      this.selects = selects
    },
    // 上架
    shelfHandle () {
      let params = this.selects.map(item => item.id)
      onService(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('serviceList.successShelf'))
          this.initDataList()
        }
      })
    },
    // 下架
    obtainedHandle () {
      let params = this.selects.map(item => item.id)
      offService(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('serviceList.underSuccess'))
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
    },
    selectSharePath () {
      console.log(this.$refs.shareInput)
      this.$refs.shareInput.select()
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
    font-size: 22px;
  }
  .list_info {
    padding-bottom: 10px;
  }
  .table-page {
    display: flex;
    justify-content: space-between;
    overflow: hidden;
  }
  .share_content {
    margin: 0 auto;
    text-align: center;
    .down_imgs {
      color: #999;
      font-size: 14px;
      display: inline-block;
      height: 40px;
      line-height: 40px;
      width: 100%;
      text-align: center;
      margin-left: 0;
      border-bottom: 1px solid #eee;
      cursor: pointer;
    }
  }
  .share_footer {
    display: flex;
    align-items: center;
  }
}
</style>
