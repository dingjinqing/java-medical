<template>
  <div class="allGoodsSort">
    <allGoodsSortHeaderTab :tabIndex="0" />
    <div class="goodsSortForm">
      <el-button
        type="primary"
        size="small"
        @click="addGoodsSortClicked"
      >{{$t('goodsSorts.goodsSortsAdd')}}</el-button>
    </div>
    <div>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="goodsSortData"
        border
        style="width: 100%"
      >
        <el-table-column
          align="left"
          :label="$t('goodsSorts.goodsSortName')"
        >
          <template slot-scope="{row,$index}">
            <template v-if="row.level === 0">
              <span
                v-if="!row.open"
                class="collapseIcon el-icon-folder-add"
                @click="collapseIconClicked(row,$index)"
              ></span>
              <span
                v-else
                class="collapseIcon el-icon-folder-remove"
                @click="collapseIconClicked(row,$index)"
              ></span>
              {{row.sortName}}
            </template>
            <template v-else>
              <span class="collapseTab"></span>
              {{row.sortName}}
            </template>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('goodsSorts.goodsSortImg')"
        >
          <template slot-scope="{row}">
            <div style="height: 50px;">
              <img
                v-if="row.sortImg"
                :src="row.sortImg"
                style="height: 50px;min-width: 160px;"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('goodsSorts.goodsSortImgLink')"
          prop="imgLink"
        />
        <el-table-column
          align="center"
          :label="$t('goodsSorts.goodsSortFirst')"
          prop="first"
        />
        <el-table-column
          align="center"
          :label="$t('goodsSorts.goodsSortCreateTime')"
          prop="createTime"
        />
        <el-table-column
          align="center"
          :label="$t('goodsSorts.goodsSortOperate')"
        >
          <template slot-scope="{row}">
            <el-tooltip
              :content="$t('goodsSorts.edit')"
              placement="top"
            >
              <span
                class="el-icon-edit-outline operateSpan"
                @click="editGoodsSortClicked(row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('goodsSorts.delete')"
              placement="top"
            >
              <span
                class="el-icon-delete operateSpan"
                @click="deleteGoodsSortClicked(row)"
              ></span>
            </el-tooltip>

            <!-- <span
              class="operateSpan"
              @click="editGoodsSortClicked(row)"
            >{{$t('goodsSorts.edit')}}</span>
            <span
              class="operateSpan"
              @click="deleteGoodsSortClicked(row)"
            >{{$t('goodsSorts.delete')}}</span> -->
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
// 导入api
import { getGoodsSortList, deleteGoodsSort } from '@/api/admin/goodsManage/goodsSortManagement/goodsSortManagement'
// 组件导入
import allGoodsSortHeaderTab from './allGoodsSortHeaderTab'
// 工具函数导入
import { convertDataFromArrayToTree } from '@/util/goodsSortCatUtil'
export default {
  name: 'allGoodsSort',
  components: {
    allGoodsSortHeaderTab
  },
  data () {
    return {
      goodsSortData: []
    }
  },
  methods: {
    addGoodsSortClicked () {
      this.$router.push({ name: 'addGoodsSort' })
    },
    collapseIconClicked (row, $index) {
      row.open = !row.open
      if (row.open) {
        this.goodsSortData.splice($index + 1, 0, ...this.goodsSortData[$index].children)
      } else {
        this.goodsSortData.splice($index + 1, this.goodsSortData[$index].children.length)
      }
    },
    /* 修改分类 */
    editGoodsSortClicked (row) {
      this.$router.push({ name: 'updateGoodsSort', params: { sortId: row.sortId } })
    },
    /* 删除商品分类 */
    deleteGoodsSortClicked (row) {
      let deleteMsg = row.level === 0 ? this.$t('goodsSorts.goodsSortDeleteMsg') : this.$t('goodsSorts.goodsSortDeleteSecondMsg')
      this.$confirm(deleteMsg, this.$t('goodsSorts.goodsSortDeleteTip'), {
        confirmButtonText: this.$t('goodsSorts.ok'),
        cancelButtonText: this.$t('goodsSorts.cancel'),
        type: 'warning'
      }).then(() => {
        deleteGoodsSort(row.sortId).then(res => {
          this._fetchGoodsSortData()
        })
      })
    },
    /* 获取商品分类 */
    _fetchGoodsSortData () {
      getGoodsSortList({ type: 0 }).then(res => {
        this.goodsSortData = this._disposeGoodsSortData(res.content, 'sortId')
      })
    },
    /* 处理平台分类数据列表为树形结构 */
    _disposeGoodsSortData (goodsSorts) {
      let treeArray = convertDataFromArrayToTree(goodsSorts, 'sortId')
      treeArray.forEach(treeNode => {
        treeNode.open = false
        if (treeNode.children === undefined) {
          treeNode.children = []
        }
      })
      return treeArray
    }
  },
  mounted () {
    this.langDefault()
    this._fetchGoodsSortData()
  }
}
</script>

<style scoped>
.goodsSortForm {
  margin: 10px 0px;
}
.collapseIcon {
  font-size: 20px;
  margin-right: 4px;
  margin-left: 20px;
  color: #5a8bff;
  cursor: pointer !important;
}
.collapseTab {
  display: inline-block;
  width: 60px;
}
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
