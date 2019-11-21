<template>
  <div class="serviceAuth">
    <div class="serviceAuthMain">
      <div class="p_top">
        <div class="p_top_left">
          <div class=abutton>
            <a
              class="link"
              :href="hrefMpData"
              target="_blank"
            >
              <el-button
                @click="centerDialogVisible = false"
                type="primary"
              >{{$t('serviceAuth.addButton')}}</el-button>
            </a>
          </div>
          <div class="tipsDiv">{{$t('serviceAuth.tipsOne')}}<img :src="iconUrl">
            <div class="tipsHidden">
              <div class="tipsTop">
                <p> {{$t('serviceAuth.tipsTop1')}}<a href="https://developers.weixin.qq.com/miniprogram/dev/api/notice.html#%E4%B8%8B%E5%8F%91%E6%9D%A1%E4%BB%B6%E8%AF%B4%E6%98%8E">{{$t('serviceAuth.tipsTop2')}}</a>，{{$t('serviceAuth.tipsTop3')}}
                </p>
              </div>
            </div>
          </div>
        </div>

        <div class="p_middle">
          <el-table
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="tableData"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="nickName"
              :label="$t('serviceAuth.nickName')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="principalName"
              :label="$t('serviceAuth.principalName')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="alias"
              :label="$t('serviceAuth.alias')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="lastAuthTime"
              :label="$t('serviceAuth.lastAuthTime')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              :label="$t('serviceAuth.mpNickName')"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                <div
                  v-for='(item,index) in scope.row.mpNickName'
                  :key="index"
                >
                  {{item}}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="isAuthOkTrans"
              :label="$t('serviceAuth.isAuthOk')"
              width="150"
              align="center"
            >
            </el-table-column>
            <el-table-column
              :label="$t('serviceAuth.operation')"
              align="center"
              width="150"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="detailOperation(scope.row)"
                >{{$t('serviceAuth.payLook')}}</el-button>
                <el-button
                  type="text"
                  @click="payCertManage(scope.row)"
                >{{$t('serviceAuth.payManage')}}</el-button>

              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="footer">
          <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totle"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!--设置页面分类弹窗-->
    <div class="pageDialogMy">
      <el-dialog
        :title="$t('serviceAuth.payManage')"
        :visible.sync="dialogFormVisible"
        width="680px"
      >
        <el-form
          :model="form"
          label-width="300px"
          style="text-align: center;"
          :label-position="labelPosition"
        >
          <el-form-item :label="$t('serviceAuth.payStoreName')">
            <el-input
              class="inputClass"
              v-model="form.payMchId"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            style="background-color: #f1f1f1;"
            :label="$t('serviceAuth.payKey')"
          >
            <el-input
              class="inputClass"
              v-model="form.payKey"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item :label="$t('serviceAuth.payCertContent')">
            <el-input
              class="textareaClass"
              type="textarea"
              :rows="4"
              v-model="form.payCertContent"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            style="background-color: #f1f1f1;"
            :label="$t('serviceAuth.payKeyContent')"
          >
            <el-input
              class="textareaClass"
              type="textarea"
              :rows="4"
              v-model="form.payKeyContent"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
        <div
          slot="footer"
          class="dialog-footer changeMa"
        >
          <el-button
            size="small"
            @click="dialogFormVisible = false"
          >{{$t('serviceAuth.cancel')}}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="payManageUp()"
          >{{$t('serviceAuth.sure')}}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { authorizationRequest, serviceAuthListRequest, payManageRequest } from '@/api/admin/serviceAuth'
export default {
  name: 'serviceAuthList',
  data () {
    return {
      iconUrl: this.$imageHost + '/image/admin/system_icon.png',
      restaurants: [],
      labelPosition: 'left',
      tableData: [
      ],
      currentPage: 1,
      totle: null,
      pageCount: null,
      dialogFormVisible: false,
      hrefMpData: null,
      dialogVisibleShare: false,
      form: {
        appId: '',
        payMchId: '',
        payKey: '',
        payCertContent: '',
        payKeyContent: ''
      }
    }
  },
  watch: {
    lang () {
      this.formatter(this.tableData)
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  methods: {
    defaluteData () {
      authorizationRequest().then((res) => {
        if (res.error === 0) {
          this.hrefMpData = res.content
        }
      })
      let params = {
        'currentPage': this.currentPage,
        'pageRows': 20
      }
      serviceAuthListRequest(params).then((res) => {
        if (res.error === 0) {
          this.formatter(res.content.dataList)
          this.tableData = res.content.dataList
          console.log('this.tableData')
          console.log(this.tableData)
          this.totle = res.content.page.totalRows
          this.currentPage = res.content.page.currentPage
          this.pageCount = res.content.page.pageCount
        }
      })
    },
    // 查看
    detailOperation (data) {
      this.$router.push({
        path: '/admin/home/shopMain',
        query: {
          change_components: '6',
          appId_Detail: data.appId
        }
      })
    },
    // 提现配置展示
    payCertManage (row) {
      this.dialogFormVisible = true
      this.form.appId = row.appId
      this.form.payKey = row.payKey
      this.form.payMchId = row.payMchId
      this.form.payCertContent = row.payCertContent
      this.form.payKeyContent = row.payKeyContent
    },
    // 提现配置
    payManageUp () {
      payManageRequest(this.form).then((res) => {
        this.dialogFormVisible = false
        if (res.error === 0) {
          this.$message.success(res.message)
          this.defaluteData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    formatter (data) {
      for (var i = 0; i < data.length; i++) {
        switch (data[i].isAuthOk) {
          case 0:
            data[i].isAuthOkTrans = this.$t('serviceAuth.haveCancle')
            break
          case 1:
            data[i].isAuthOkTrans = this.$t('serviceAuth.haveAuth')
            break
        }
      }
    },
    handleCurrentChange () {
      this.defaultData()
    }
  }
}
</script>
<style lang="scss" scoped>
.serviceAuth {
  width: 100%;
  height: 100%;
  background-color: #fff;
  padding: 107px;
}
.p_top_left {
  display: flex;
  /deep/ .el-button {
    padding: 6px 20px;
  }
  .abutton {
    display: inline-block;
    text-align: center;
    margin: 0 -30px 0 0px;
  }
  .tipsDiv {
    position: relative;
    cursor: pointer;
    height: auto;
    font-size: 14px;
    line-height: 30px;
    color: #999;
    padding: 0 12px;
    margin-bottom: 10px;
    margin-left: 30px;
    &:hover .tipsHidden {
      display: block;
    }
    img {
      margin-left: 5px;
      position: relative;
      top: 2px;
    }
    .tipsHidden {
      display: none;
      position: absolute;
      top: 32px;
      right: 0;
      width: 300px;
      background: #fff;
      box-shadow: 0px 0px 3px #f0f0f0;
      z-index: 100;
      .tipsTop {
        border: 1px solid #ccc;
        background-color: white;
        z-index: 2;
        padding: 10px;
        p {
          color: #666;
          font-size: 12px;
          border-bottom: 1px solid #efedee;
        }
      }
      .tipsBottom {
        display: flex;
        justify-content: center;
      }
    }
  }
}
.p_middle {
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .operation {
    display: flex;
    justify-content: space-around;
    .iconSpn {
      display: block;
      font-size: 20px;
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
.footer {
  background-color: #fff;
  height: 50px;
  line-height: 50px;
  color: #333;
  font-size: 14px;
  display: flex;
  justify-content: flex-end;
  padding-right: 10px;
  /deep/ .el-pagination {
    display: flex;
    align-items: center;
    .el-pager {
      display: flex;
      align-items: center;
    }
  }
}
.inputClass {
  width: 270px;
  height: 25px;
  margin-left: -160px;
}
.textareaClass {
  width: 270px;
  height: 100px;
  margin-left: -160px;
}
.pageDialogMy {
  text-align: center;
  /deep/ .el-form-item {
    margin-bottom: 15px;
  }
  /deep/ .el-form--label-left .el-form-item__label {
    text-align: center;
  }
  /deep/ .el-dialog__header {
    background-color: #f1f1f1;
    margin-bottom: -18px;
  }
}
.changeMa {
  margin-top: -36px;
}
</style>
<style>
</style>
