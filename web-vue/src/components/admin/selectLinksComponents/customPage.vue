
<template>
  <div class="customPage_Container">
    <div class="top_container">
      <div class="top_left">
        <div>{{$t('selectLinks.pageName')}}：</div>
        <el-input
          v-model="pageName"
          :placeholder="$t('selectLinks.inputName')"
          size="mini"
        ></el-input>
        <div></div>
      </div>
      <div class="top_middle">
        <div>{{$t('selectLinks.pageClassification')}}：</div>
        <el-select
          v-model="value"
          size="mini"
        >
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
      <div class="top_right">
        <el-button
          type="primary"
          size="mini"
          @click="handleSearch()"
        >{{$t('selectLinks.search')}}</el-button>
      </div>
    </div>
    <div class="content">
      <table width='100%'>
        <thead>
          <tr>
            <td>{{$t('selectLinks.name')}}</td>
            <td>{{$t('selectLinks.classification')}}</td>
            <td>{{$t('selectLinks.link')}}</td>
          </tr>
        </thead>
        <tbody
          v-if="tbodyFlag"
          v-loading="loading"
        >
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.pageName}}</td>
            <td class="link">{{item.name}}</td>
            <td class="tb_decorate_a">
              <!-- {{item.path}} -->
              pages/index/index?page={{item.pageId}}
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>{{$t('selectLinks.noDataAvailable')}}</span>
      </div>
    </div>
    <div class="pagination">
      <div class="paginationLeft">
        {{$t('selectLinks.nowPage')}}{{currentPage}}/{{pageCount}},{{$t('selectLinks.generalRecord')}}{{totalRows}}{{$t('selectLinks.strip')}}
      </div>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="20"
        layout="prev, pager, next, jumper"
        :total="totalRows"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import { pageCustomApi } from '@/api/admin/selectLinksApi/selectLinksApi'
import { getPageCate } from '@/api/admin/decoration/pageSet.js'
export default {
  data () {
    return {
      pageName: '',
      options: [],
      value: '',
      trList: [],
      clickIindex: null,
      tbodyFlag: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      currentPage: 1,
      totalRows: null,
      pageCount: null,
      loading: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.fetchData(true)
  },
  methods: {
    // 获取数据
    fetchData (flag) {
      this.loading = true

      let params = {
        currentPage: this.currentPage,
        pageName: this.pageName
      }
      pageCustomApi(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          if (!res.content.dataList.length) {
            this.tbodyFlag = false
            this.loading = false
          } else {
            this.trList = res.content.dataList
            this.tbodyFlag = true
            this.totalRows = res.content.page.totalRows
            this.pageCount = res.content.page.pageCount
            console.log(this.trList)
            this.loading = false
          }
        }
      }).catch(err => console.log(err))
      if (!flag) return
      getPageCate().then(res => {
        console.log(res)
        if (res.error === 0) {
          let obj = {
            id: '',
            name: this.$t('selectLinks.selectClassify')
          }
          res.content.unshift(obj)
          this.options = res.content
        }
      })
    },
    ...mapActions(['choisePagePath']),
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
      let path = `pages/index/index?page=${this.trList[index].pageId}`
      this.choisePagePath(path)
    },
    // 搜索
    handleSearch () {
      this.fetchData(false)
    },
    // 当前页改变
    handleCurrentChange () {
      this.fetchData(false)
    }
  }
}
</script>
<style scoped>
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  margin: 10px;
}
.top_container {
  display: flex;
  justify-content: space-around;
  padding-bottom: 10px;
}
.top_left {
  display: flex;
  align-items: center;
  /* margin-left: 7px; */
}
.top_middle {
  display: flex;
  align-items: center;
}

.clickClass {
  background-color: #eee !important;
}
.spanClass {
  display: block !important;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}
thead td:nth-of-type(1) {
  width: 254px;
}
thead td:nth-of-type(2) {
  width: 105px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 10px;
  vertical-align: middle !important;
  text-align: center;
}
.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}
.paginationLeft {
  display: flex;
  align-items: center;
}
</style>
<style>
.top_container .el-input {
  width: 140px !important;
}
</style>
