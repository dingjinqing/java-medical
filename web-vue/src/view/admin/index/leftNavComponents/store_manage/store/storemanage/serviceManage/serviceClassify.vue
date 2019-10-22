<template>
  <div>
    <div class="sevice_classify_page">
      <div class="list-info service_class_info">
        <div>
          <el-input
            v-model="catName"
            :placeholder="$t('serviceClassify.enterCategoryTips')"
            style="width: 188px;"
            size="small"
          ></el-input>
          <el-button
            type="primary"
            size="small"
            @click="addCatHandle"
          >{{$t('serviceClassify.save')}}</el-button>
        </div>
        <div>
          <el-button
            type="primary"
            size="small"
            @click="initDataList"
          >{{$t('serviceClassify.inquire')}}</el-button>
          <el-input
            v-model="queryParams.catName"
            :placeholder="$t('serviceClassify.queryPl')"
            style="width: 188px;"
            suffix-icon="el-icon-search"
            size="small"
          ></el-input>
        </div>
      </div>
      <div class="list-table">
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
            :label="$t('serviceClassify.categoryName')"
            prop="catName"
          >
            <template slot-scope="{ row , $index}">
              <div v-if="row.edit">
                <el-input
                  ref="editInput"
                  size="small"
                  v-model="row.catName"
                  @blur="editHandle(row.catName, $index)"
                ></el-input>
              </div>
              <span v-else>{{row.catName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('serviceClassify.createTime')"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            :label="$t('serviceClassify.operate')"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row, $index }">
              <div>
                <el-tooltip :content="$t('serviceClassify.edit')">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row, $index)"
                  >{{$t('serviceClassify.edit')}}</span>
                </el-tooltip>
                <el-tooltip :content="$t('serviceClassify.delete')">
                  <span
                    class="iconSpan"
                    @click="edit('delete', row, $index)"
                  >{{$t('serviceClassify.delete')}}</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { getServiceCatsList, addServiceCat, updateServiceCat, deleteServiceCat } from '@/api/admin/storeManage/storemanage/serviceManage'
export default {
  data () {
    return {
      storeId: '',
      catName: '',
      queryParams: {
        storeId: '',
        catName: ''
      },
      tableData: [],
      pageParams: {},
      oldCatName: ''
    }
  },
  created () {
    this.storeId = this.$route.query.id
    this.queryParams.storeId = this.storeId
    this.langDefault()
    this.initDataList()
  },
  directives: {
    focus: {
      // 指令的定义
      inserted: function (el) {
        el.focus()
      }
    }
  },
  methods: {
    addCatHandle () {
      // 验证是否填写类别名
      if (!this.catName) return false
      let params = {
        storeId: this.storeId,
        catName: this.catName
      }
      addServiceCat(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('serviceClassify.successAddToast'))
          this.initDataList()
        }
      })
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getServiceCatsList(params).then(res => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    },
    edit (prop, row, index) {
      switch (prop) {
        case 'edit':
          // this.tableData[index].edit = true
          this.$set(this.tableData[index], 'edit', true)
          this.$nextTick(function () {
            this.oldCatName = row.catName
            this.$refs.editInput.focus()
          })
          break
        case 'delete':
          let params = {
            catId: row.catId
          }
          deleteServiceCat(params).then(res => {
            if (res.error === 0) {
              this.$message.success(this.$t('serviceClassify.successDeleteToast'))
              this.initDataList()
            }
          })
          break
      }
    },
    editHandle (name, index) {
      let _this = this
      _this.$set(this.tableData[index], 'edit', false)
      if (name === _this.oldCatName) return false
      let params = {
        catId: _this.tableData[index].catId,
        catName: name
      }
      updateServiceCat(params).then(res => {
        if (res.error === 0) {
          _this.$message.success(this.$t('serviceClassify.updateCompleted'))
          _this.initDataList()
        } else {
          _this.$message.error(this.$t('serviceClassify.updateFaild'))
          _this.$set(_this.tableData[index], 'catName', _this.oldCatName)
        }
      }).catch(err => {
        _this.$set(_this.tableData[index], 'catName', _this.oldCatName)
        throw err
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.sevice_classify_page {
  margin: 0 25px;
  .iconSpan {
    color: #5a8bff;
    text-decoration: none;
    cursor: pointer !important;
  }
  .list-info {
    display: flex;
    justify-content: space-between;
    padding-bottom: 10px;
  }
}
</style>
