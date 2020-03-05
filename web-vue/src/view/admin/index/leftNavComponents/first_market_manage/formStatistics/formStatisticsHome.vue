<template>
  <div class="formStatisticsHome">
    <div class="formStatisticsTop">
      <el-button
        size="small"
        type="primary"
        @click="handleToAddForm()"
      >添加表单</el-button>
      <div class="tipsDiv">当前版本为旗舰版,不限制表单个数<img :src="this.$imageHost + '/image/admin/system_icon.png'">
        <div class="tipsHidden">
          <div class="tipsTop">
            <p>
              {{$t('pictureSetting.hiddenTips')}}
            </p>
          </div>
          <div class="tipsBottom">
            <el-button
              type="primary"
              size="small"
            >了解更多</el-button>
          </div>
        </div>
      </div>
    </div>
    <div class="formStatisticsBottom">
      <div class="formStatisticsTop">
        <div class="list">
          <span>状态</span>
          <el-select
            v-model="statusValue"
            size="small"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <div
          class="list"
          style="margin-left:10px"
        >
          <span>创建时间</span>
          <el-date-picker
            size="small"
            v-model="createStartTime"
            type="datetime"
            :placeholder="$t('allGoods.batchDialog.selectDateTime')"
            default-time="12:00:00"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          至
          <el-date-picker
            size="small"
            v-model="createEndTime"
            type="datetime"
            :placeholder="$t('allGoods.batchDialog.selectDateTime')"
            default-time="12:00:00"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <div
          class="list"
          style="margin-left:10px"
        >
          <span>表单名称</span>
          <el-input
            size="small"
            v-model="formNameInput"
            placeholder="请输入表单名称"
          ></el-input>
        </div>
        <el-button
          size="small"
          type="primary"
          @click="handleToScreen()"
        >筛选</el-button>
      </div>
      <div class="formStatisticsBottom">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="pageName"
            label="表单名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('pictureSetting.crateTime')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="submitNum"
            label="反馈数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.status===0?'未发布':scope.row.status===1?'已发布':scope.row.status===2?'已关闭':'已删除'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="validityPeriod"
            label="有效期"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.validityPeriod===1?'永久有效':'期限内有效'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('pictureSetting.operation')"
            align="center"
            width="150"
          >
            <!-- slot-scope="scope" -->
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  :content="scope.row.status===0?$t('pictureSetting.edtitText'):'查看'"
                  placement="top"
                >
                  <span
                    @click="handletoClickOperation(scope.row,1)"
                    class="el-icon-edit-outline iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="发布"
                  placement="top"
                  v-if="scope.row.status===0"
                >
                  <span
                    @click="handletoClickOperation(scope.row,2)"
                    class="el-icon-s-promotion iconSpn"
                  ></span>
                </el-tooltip>

                <el-tooltip
                  :content="$t('pictureSetting.del')"
                  placement="top"
                >
                  <span
                    @click="handletoClickOperation(scope.row,3)"
                    class="el-icon-delete iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="查看反馈"
                  placement="top"
                  v-if="scope.row.status===1"
                >
                  <span class="iconfont iconchakanfankui iconSpn"></span>
                </el-tooltip>
                <el-tooltip
                  content="反馈统计"
                  placement="top"
                  v-if="scope.row.status===1"
                >
                  <span class="iconfont iconfankuitongji iconSpn"></span>
                </el-tooltip>
                <el-tooltip
                  content="关闭"
                  placement="top"
                  v-if="scope.row.status===1"
                >
                  <span
                    @click="handletoClickOperation(scope.row,6)"
                    class="iconfont icontingyong iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('pictureSetting.copy')"
                  placement="top"
                >
                  <span
                    @click="handletoClickOperation(scope.row,4)"
                    class="fa fa-copy iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('pictureSetting.share')"
                  placement="top"
                >
                  <span
                    @click="handletoClickOperation(scope.row,5)"
                    class="el-icon-share iconSpn"
                  ></span>
                </el-tooltip>

              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="footer">
          <span>当前页面{{currentPage}}/{{totalPage}}，总记录{{totalRows}}条</span>
          <el-pagination
            @current-change="handleDetailCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totalRows"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!--二次确认弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="dialogTwoVisible"
      width="20%"
    >
      <span style="text-align:center">{{twoSureText}}</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="handleToSureTwo()">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogTwoVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--分享弹窗-->
    <el-dialog
      title="扫一扫分享给好友吧~"
      :visible.sync="shareVisible"
      width="30%"
    >
      <div class="copyContainer">
        <img
          src="http://mpdevimg2.weipubao.cn/upload/4748160/pictorial/4/T100P385_20200305172829.png"
          alt=""
          style="width:160px;height:160px"
          class="code_imgs"
        >
      </div>
      <div
        class="copyContainer"
        style="color:#999"
      >
        下载海报码
      </div>
      <div class="copyContainer copyDiv">
        <span>下载海报链接：</span>
        <el-input
          size="small"
          v-model="posterAddress"
          ref="qrCodePageUrlInput"
        ></el-input>
        <span
          class="copy"
          @click="handelToCopy"
        >复制</span>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { formListQuery, delCloseListQuery, shareFormQuery } from '@/api/admin/marketManage/formDecoration' // 装修方法混入
export default {
  data () {
    return {
      statusValue: null, // 状态
      statusOptions: [{
        value: null,
        label: '全部'
      }, {
        value: 0,
        label: '未发布'
      }, {
        value: 1,
        label: '已发布'
      }, {
        value: 2,
        label: '已关闭'
      }],
      createStartTime: '', // 创建开始时间
      createEndTime: '', // 创建结束时间
      formNameInput: '', // 表单名称input值
      tableData: [],
      currentPage: 1, // 当前页
      totalPage: 1, // 总页数
      totalRows: 0, // 总条数
      dialogTwoVisible: false, // 二次确认弹窗flag
      shareVisible: false, // 分享弹窗flag
      twoSureText: '', // 二次确认弹窗显示的文案
      nowClickRow: null, // 当前点击操作icon数据
      noClickFlag: null, //  当前点击的icon序号
      posterAddressImgUrl: '', // 海报分享图路径
      posterAddress: '' // 海报地址链接
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 数据初始化查询
    this.initData()
  },
  methods: {
    // 初始化数据
    initData () {
      let params = {
        pageName: this.formNameInput,
        startTime: this.createStartTime,
        endTime: this.createEndTime,
        status: this.statusValue,
        currentPage: this.currentPage,
        pageRows: 20
      }
      formListQuery(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          // res.content.dataList[0].status = 1
          this.tableData = res.content.dataList
          this.totalPage = res.content.page.pageCount
          this.totalRows = res.content.page.totalRows
        }
      })
    },
    // 添加表单点击
    handleToAddForm () {
      this.$router.push({
        name: 'formDecorationHome'
      })
    },
    // 点击筛选按钮
    handleToScreen () {
      this.initData()
    },
    // 表格操作图标点击综合处理
    handletoClickOperation (row, flag) {
      console.log(flag)
      this.nowClickRow = row
      this.noClickFlag = flag
      switch (flag) {
        case 1: // 编辑
          console.log(row)
          this.$router.push({
            path: '/admin/home/main/formDecorationHome',
            query: {
              pageId: row.pageId,
              flag: true
            }
          })
          break
        case 2: // 发布
          this.twoSureText = '确认要发布吗？'
          this.dialogTwoVisible = true

          break
        case 3: // 删除
          this.twoSureText = '确认要删除吗？'
          this.dialogTwoVisible = true

          break
        case 4: // 复制
          this.$router.push({
            path: '/admin/home/main/formDecorationHome',
            query: {
              pageId: row.pageId,
              flag: false
            }
          })
          break
        case 5: // 分享
          this.shareVisible = true
          shareFormQuery({ pageId: row.pageId }).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.posterAddress = this.$imageHost + '/' + res.content.pagePath
              this.posterAddressImgUrl = res.content.imageUrl
            }
          })
          break
        case 6: // 关闭
          this.twoSureText = '确认要关闭吗？'
          this.dialogTwoVisible = true
          break
      }
    },
    // 点击复制
    handelToCopy () {
      this.$refs.qrCodePageUrlInput.select()
      document.execCommand('Copy')
    },
    // 二次确认弹窗确认事件
    handleToSureTwo () {
      let status = null
      let message = null
      switch (this.noClickFlag) {
        case 3:
          status = 3
          message = '删除成功'
          break
        case 2:
          status = 1
          message = '发布成功'
          break
        case 6:
          status = 2
          message = '关闭成功'
          break
      }
      delCloseListQuery({ pageId: this.nowClickRow.pageId, formStatus: status }).then(res => {
        if (res.error === 0) {
          this.$message.success({
            message: message,
            showClose: true
          })
        }
      })
    },
    // 当前页发生变化
    handleDetailCurrentChange () {
      this.initData()
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.formStatisticsHome {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .formStatisticsTop {
    display: flex;
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 30px;
    .tipsDiv {
      font-size: 12px;
      position: relative;
      cursor: pointer;
      height: 30px;
      line-height: 30px;
      color: #999;
      padding: 0 12px;
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
        left: 0;
        width: 300px;
        padding: 10px;
        background: #fff;
        box-shadow: 0px 0px 10px #f0f0f0;
        z-index: 100;
        .tipsTop {
          line-height: 30px;
          margin-bottom: 35px;
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
  .formStatisticsBottom {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 10px 0;
    .formStatisticsTop {
      .list {
        span {
          display: inline-block;
          padding: 0 5px;
        }
        /deep/ .el-input {
          width: 150px;
        }
      }
      /deep/ .el-button {
        margin-left: 20px;
      }
    }
    .formStatisticsBottom {
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
        flex-wrap: wrap;
        // justify-content: space-around;
        .iconSpn {
          display: block;
          font-size: 22px;
          color: #5a8bff;
          cursor: pointer;
          margin-right: 3px;
        }
      }
      .footer {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        margin-top: 10px;
      }
    }
  }
}
.copyContainer {
  display: flex;
  justify-content: center;
  .copy {
    cursor: pointer;
    color: #5a8bff;
  }
  /deep/ .el-input {
    width: 200px;
    margin: 0 10px;
  }
}
.copyDiv {
  align-items: center;
  margin-top: 20px;
  span {
    white-space: nowrap;
  }
}
</style>
