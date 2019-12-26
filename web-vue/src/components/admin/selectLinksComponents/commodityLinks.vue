<template>
  <div style="overflow-y:auto;height:100%">
    <div class="content">
      <div class="top_container">
        <div class="top_left">
          <div>{{$t('selectLinks.keyWord')}}：</div>
          <el-input
            v-model="pageName"
            :placeholder="$t('selectLinks.keyWordPlaceHold')"
            size="mini"
          ></el-input>
          <div class="top_right">
            <el-button
              type="primary"
              size="mini"
              @click="handleSearch()"
            >{{$t('selectLinks.search')}}</el-button>
          </div>
        </div>
      </div>
      <table width='100%'>
        <thead>
          <tr>
            <td>{{$t('selectLinks.commodityInformation')}}</td>
            <td>{{$t('selectLinks.productCode')}}</td>
            <td>{{$t('selectLinks.link')}}</td>
          </tr>
        </thead>
        <tbody v-loading="loading">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index,item)"
          >

            <td class="isLeft">
              <img :src="$imageHost+'/'+item.goodsImg">
              <span>{{item.goodsName}}</span>

            </td>
            <td>{{item.goodsSn}}</td>
            <td class="tb_decorate_a">
              pages/item/item?goodsId={{item.goodsId}}
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
import { mapGetters, mapActions } from 'vuex'
import { goodsListApi } from '@/api/admin/selectLinksApi/selectLinksApi'
export default {
  data () {
    return {
      trList: [],
      clickIindex: null,
      tbodyFlag: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      pageName: '',
      currentPage: 1,
      totalRows: null,
      pageCount: null,
      loading: true
    }
  },
  computed: {
    ...mapGetters(['selectlinksIndex']),
    selectlinksIndex_ () {
      console.log(this.selectlinksIndex)
      return this.selectlinksIndex
    }
  },
  watch: {
    selectlinksIndex_: {
      handler (newData, oldData) {
        console.log(newData)
        // 初始化数据
        this.defaultData(newData)
      },
      immediate: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    ...mapActions(['choisePagePath']),
    defaultData (newData) {
      console.log(newData)
      this.loading = true
      goodsListApi({
        'currentPage': this.currentPage,
        'pageRows': 20,
        'keyWords': this.pageName
      }).then(res => {
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
        console.log(res)
      }).catch(err => console.log(err))
    },
    // 行选中高亮
    handleClick (index, item) {
      this.clickIindex = index
      console.log('选中', item)
      let path = `pages/item/item?goodsId=${this.trList[index].goodsId}`
      this.choisePagePath(path)
      this.$emit('handleToGetDetailData', item)
    },
    // 搜索
    handleSearch () {
      console.log(this.pageName, this.value)
      this.defaultData()
    },
    // 当前页改变
    handleCurrentChange () {
      this.defaultData()
    }
  }
}
</script>
<style scoped>
.top_container {
  /* display: flex; */
  /* justify-content: space-around; */
  padding-bottom: 10px;
}
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
/* .top_container {
  padding-bottom: 10px;
}
.top_container {
  display: flex;
  justify-content: space-around;
} */
.top_left {
  display: flex;
  align-items: center;
  /* margin-left: 7px; */
}
.top_right {
  margin-left: 10px;
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
  width: 258px;
}
thead td:nth-of-type(2) {
  width: 106px;
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
img {
  margin-left: 10px;
}
.isLeft {
  display: flex;
}
.isLeft img {
  width: 40px;
  height: 40px;
}
.isLeft span {
  display: inline-block;
  vertical-align: top;
  margin-top: 9px;
  margin-left: 5px;
}
.tdCenter {
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
