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
          :label="$t('goodsSorts.goodsSortName')"
          align="center"
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
          :label="$t('goodsSorts.goodsSortImg')"
          align="center"
        >
          <template slot-scope="{row}">
            <img
              :src="row.sortImgUrl"
              style="height: 50px;min-width: 160px;"
            />
          </template>
        </el-table-column>
        <el-table-column
          prop="imgLink"
          :label="$t('goodsSorts.goodsSortImgLink')"
          align="center"
        />
        <el-table-column
          align="center"
          :label="$t('goodsSorts.goodsSortFirst')"
          prop="first"
        />
        <el-table-column
          prop="createTime"
          :label="$t('goodsSorts.goodsSortCreateTime')"
          align="center"
        />
        <el-table-column
          :label="$t('goodsSorts.goodsSortOperate')"
          align="center"
        >
          <template slot-scope="{row}">
            <span
              class="operateSpan"
              @click="editGoodsSortClicked(row)"
            >{{$t('goodsSorts.edit')}}</span>
            <span
              class="operateSpan"
              @click="deleteGoodsSortClicked(row)"
            >{{$t('goodsSorts.delete')}}</span>
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
      this.$confirm(this.$t('goodsSorts.goodsSortDeleteMsg'), this.$t('goodsSorts.goodsSortDeleteTip'), {
        confirmButtonText: this.$t('goodsSorts.ok'),
        cancelButtonText: this.$t('goodsSorts.cancel'),
        type: 'warning'
      }).then(() => {
        deleteGoodsSort({ sortId: row.sortId }).then(res => {
          this._fetchGoodsSortData()
        })
      })
    },
    /* 获取商品分类 */
    _fetchGoodsSortData () {
      getGoodsSortList({ type: 0 }).then(res => {
        this.goodsSortData = this._disposeGoodsSortAndCatData(res.content)
      })
    },
    /* 处理后台传入的商家分类数据 */
    _disposeGoodsSortAndCatData (data) {
      let idName = 'sortId'
      let retObj = {}

      for (let i = 0; i < data.length; i++) {
        let item = data[i]

        // 是否自身节点被创建过（子节点先遍历到了）
        let selfItem = retObj[item[idName]]
        if (selfItem === undefined) {
          // 未遍历到则初始化自己
          retObj[item[idName]] = { 'item': item, children: [] }
          selfItem = retObj[item[idName]]
        } else {
          // 已创建过，（因提前遍历了子节点而创建）
          selfItem.item = item
        }

        let parentItem = retObj[item.parentId]
        // 有父亲直接插入
        if (parentItem !== undefined) {
          parentItem.children.push(selfItem)
        } else {
          // 没有则创建临时父亲
          retObj[item.parentId] = { 'item': null, children: [selfItem] }
        }
      }

      let retArr = []

      if (data.length === 0) {
        return retArr
      }
      let rootArr = retObj['0'].children

      rootArr.forEach(t => {
        t.item.open = false
        retArr.push(t.item)
      })

      // 处理结果将对象变为数组
      for (let i = 0; i < rootArr.length; i++) {
        let retItem = rootArr[i]
        if (retItem.children.length > 0) {
          retItem.children.forEach(t => {
            retItem.item.children === null ? retItem.item.children = [t.item] : retItem.item.children.push(t.item)
          })
          rootArr.splice(i + 1, 0, ...(retItem.children))
        }
      }
      return retArr
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
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
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
  font-size: 16px;
  color: #5a8bff;
  cursor: pointer !important;
}
</style>
