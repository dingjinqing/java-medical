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
              >添加授权</el-button>
            </a>
          </div>
          <div class="tipsDiv">注意：仅可以授权认证服务号.授权完成认证服务号，关注服务号的用户在接收小程序消息通知时优先通过小程序接收<img :src="iconUrl">
            <div class="tipsHidden">
              <div class="tipsTop">
                <p> 由于微信平台相关要求，公众号授权完成后，
                  需要在店铺内小程序管理-小程序授权绑定和该店铺小程序同主体的公众号，绑定后，使用该绑定公众号登录微信公众平台，
                  在公众平台小程序小程序管理页面添加关联小程序，
                  关联该小程序后，关注公众号的用户在接收小程序消息通知时即可通过公众号接收。
                  小程序接收消息限制较大，详情查看<a href="https://developers.weixin.qq.com/miniprogram/dev/api/notice.html#%E4%B8%8B%E5%8F%91%E6%9D%A1%E4%BB%B6%E8%AF%B4%E6%98%8E">模板消息下发条件说明</a>，此功能方便商家针对用户进行小程序消息通知。
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
              label="服务号名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="principalName"
              label="主体名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="alias"
              label="公众微信号"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="lastAuthTime"
              label="授权时间"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="mpNickName"
              label="已绑定店铺"
              width="150"
            >
            </el-table-column>
            <el-table-column
              prop="isAuthOk"
              label="授权状态"
              width="150"
            >
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              width="150"
            >
              <template slot-scope="scope">
                <div class="operation">
                  <el-tooltip
                    content="查看"
                    placement="top"
                  >
                    <span
                      class="el-icon-edit-outline iconSpn"
                      @click="detailOperation(scope.row)"
                    ></span>
                  </el-tooltip>
                  <el-tooltip
                    content="提现配置"
                    placement="top"
                  >
                    <span
                      @click="payCertManage(scope.row)"
                      class="el-icon-delete iconSpn"
                    ></span>
                  </el-tooltip>
                </div>
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
    <div class="pageDialog">
      <el-dialog
        title="提现配置"
        :visible.sync="dialogFormVisible"
      >
        <el-form :model="form">
          <el-form-item
            label="商户号:"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="form.payMchId"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="支付秘钥:"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="form.payKey"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="支付证书:"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              :rows="4"
              v-model="form.payCertContent"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="支付私钥:"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              :rows="4"
              v-model="form.payKeyContent"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
        <div
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="payManageUp()"
          >确 定</el-button>
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
  mounted () {
    // 初始化数据
    this.defaluteData()
    // 初始化语言
    this.langDefault()
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
          this.tableData = res.content.dataList
          this.totle = res.content.page.totalRows
          this.currentPage = res.content.page.currentPage
          this.pageCount = res.content.page.pageCount
        }
      })
    }
  },
  // 查看
  detailOperation () {

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
        this.defaluteData()
      } else {
        this.$message.error(res.message)
      }
    })
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
    height: 30px;
    font-size: 14px;
    line-height: 30px;
    text-indent: 2em;
    color: #999;
    padding: 0 12px;
    margin-bottom: 10px;
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
</style>
<style>
</style>
