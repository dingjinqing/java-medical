<template>
  <div class="container">
    <div class="top">
      <ul class="filters">
        <li>
          <label>标题：</label>
          <el-input
            class="filter-input"
            size="small"
            v-model="queryParams.title"
          ></el-input>
        </li>
        <li>
          <label for="">发布状态：</label>
          <el-select
            v-model="queryParams.status"
            size="small"
          >
            <el-option
              label="全部"
              :value="-1"
            ></el-option>
            <el-option
              label="已发布"
              :value="1"
            ></el-option>
            <el-option
              label="未发布"
              :value="0"
            ></el-option>
          </el-select>
        </li>
        <li>
          <el-button
            size="small"
            type="primary"
          >筛选</el-button>
        </li>
      </ul>
      <div style="margin-top:10px;">
        <el-button
          type="primary"
          size="small"
          @click="addAnnouncement"
        >添加公告</el-button>
      </div>
    </div>
    <div class="content">
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryParams: {
        title: '',
        status: -1
      },
      pageParams: {}
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      announcementListApi(params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        } else {
          this.$message.error(res.message)
        }
      })
    },
    addAnnouncement () {
      this.$router.push({
        path: '/admin/home/main/store/storeAnnouncementAdd'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  font-size: 14px;
  color: #333;
  .top {
    padding: 15px;
    background: #fff;
    .filters {
      li {
        display: inline-block;
        margin-right: 10px;
      }
      label {
        display: inline-block;
      }
    }
    .filter-input {
      width: 170px;
    }
  }
  .content {
    padding: 15px;
    margin-top: 10px;
    background: #fff;
  }
}
</style>
