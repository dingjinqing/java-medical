<template>
  <div class="picture_setting">
    <div class="picture_settingMain">

      <div class="p_top">
        <div class="p_top_left">
          <el-button
            type="primary"
            size="small"
            @click="handleToNewPage()"
          >新建微页面</el-button>
          <div class="tipsDiv">当前版本未旗舰版，不限制微页面个数<img :src="iconUrl">
            <div class="tipsHidden">
              <div class="tipsTop">
                <p>
                  体验版最多创建5页微页面，基础班最多创建5页微页面，高级版最多创建50微页面，旗舰版不限制微页面个数
                </p>
              </div>
              <div class="tipsBottom">
                <el-button
                  type="primary"
                  size="small"
                >更多</el-button>
              </div>
            </div>
          </div>
        </div>
        <div class="p_top_right">
          <div class="topRightDiv">
            <span>页面名称：</span>
            <el-input
              size="small"
              v-model="inputPageName"
              placeholder="请输入页面名称"
              style="width:170px;"
            ></el-input>
          </div>
          <div class="topRightDiv">
            <span>页面分类：</span>
            <el-select
              v-model="selectValue"
              placeholder="请选择"
              size="small"
              style="width:170px;"
            >
              <el-option
                v-for="item in pageSetoptions"
                :key="item.value"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
          <div class="topRightDiv s_btn">
            <el-button
              type="primary"
              size="small"
              @click="list()"
            >查询</el-button>
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
            label=""
            align="center"
          >
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.ischeck"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column
            prop="pageName"
            label="页面名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="isFirstPage"
            label="是否首页"
            align="center"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.pageType">是</span>
              <span
                style="color:#5A8BFF;cursor:pointer"
                v-if="!scope.row.pageType"
                @click="handleSetFirstPage(scope.row.pageId)"
              >设为首页</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="pageClass"
            label="页面分类"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.name}}</span>
              <span
                @click="getPageCate(scope.row.pageId)"
                style="color:#5A8BFF;cursor:pointer"
              >设置</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="150"
          >
            <!-- slot-scope="scope" -->
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  content="编辑"
                  placement="top"
                >
                  <span
                    class="el-icon-edit-outline iconSpn"
                    @click="edit(scope.row.pageId)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="删除"
                  placement="top"
                >
                  <span
                    @click="del(scope.row.pageId)"
                    class="el-icon-delete iconSpn"
                    v-if="!scope.row.isFirstPage"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="复制"
                  placement="top"
                >
                  <span
                    class="fa fa-copy iconSpn"
                    @click="copy(scope.row.pageId)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="分享"
                  placement="top"
                >
                  <span
                    class="el-icon-share iconSpn"
                    @click="handleOperation(scope.row,3)"
                  ></span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>

      </div>
      <div class="footer">
        <div class="footer_left">
          <el-checkbox v-model="allChecked">全选</el-checkbox>
          <span
            @click="batchSetPageCate()"
            style="color:#5a8bff;cursor:pointer"
          >批量设置分类</span>
        </div>
        <pagination
          :page-params.sync="pageParams"
          @pagination="list"
        />
      </div>

    </div>
    <!--设置页面分类弹窗-->
    <div class="pageDialog">
      <el-dialog
        title="设置页面分类"
        :visible.sync="pageSetdialogVisible"
        width="20%"
      >
        <div class="pageDialogMain">
          <el-select
            v-model="pageSetvalue"
            placeholder="选择页面分类"
            size="small"
          >
            <el-option
              v-for="item in pageSetoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="pageSetdialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="savePageCate()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--分享弹窗-->
    <div class="shareC">
      <el-dialog
        title="扫一扫，分享给好友吧~"
        :visible.sync="dialogVisibleShare"
        width="25%"
        :modal='false'
      >
        <div class="shareDialog_">
          <div class="shareDialog_content_"><img
              style="height:160px"
              :src="shareImg"
            ></div>
          <div
            class="shareDialog_bottom_"
            @click="downs()"
          >下载二维码</div>
        </div>

        <div class="d_footer">
          <div
            slot="footer"
            class="dialogFooter"
          >
            <el-input
              v-model="pathInput"
              placeholder="请输入内容"
              size="mini"
              ref="copy"
            ></el-input>
            <span
              style="cursor:pointer"
              @click="clickCopy()"
            >复制</span>
          </div>
        </div>

      </el-dialog>
    </div>
    <!--新建微页面弹窗-->
    <SelectTemplateDialog :tuneUpMiniPage.sync="tuneUpMiniPage" />
    <!-- 删除二次提示弹窗 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>是否继续删除该装修页面</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="delConfirm()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { pageList, setFirstPage, getPageCate, setPageCate, batchSet, delPage, pageCopy } from '@/api/admin/decoration/pageSet.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { SelectTemplateDialog: () => import('./selectTemplateDialog'), pagination },
  data () {
    return {
      inputPageName: null,
      iconUrl: this.$imageHost + '/image/admin/system_icon.png',
      pageNameInput: '',
      currentPage: '',
      restaurants: [],
      statePage: '',
      selectValue: null,
      selectOptions: [],
      pageSetvalue: '',
      pageSetoptions: [],
      tableData: [],
      allChecked: false,
      allCheckedFlag: false,
      pageSetdialogVisible: false,
      dialogVisibleShare: false,
      dialogVisible: false,
      shareImg: 'http://mpdev.weipubao.cn/upload/4748160/qrcode/33/T33P307bfc9947d3756c206033bd06eb13b0_20190614100251.jpg',
      pathInput: '',
      flag: true,
      tuneUpMiniPage: false,
      pageParams: {
        pageName: '',
        catId: ''
      },
      param: {},
      pageId: '',
      cateId: '',
      setPageCateParam: {},
      pageIds: '',
      isBatch: 0,
      delPageId: ''

    }
  },
  watch: {
    allChecked (newData) {
      console.log(newData)
      switch (newData) {
        case true:
          this.tableData.map((item, index) => {
            item.ischeck = true
          })
          break
        case false:
          this.tableData.map((item, index) => {
            item.ischeck = false
          })
      }
    },
    'tableData': {
      handler (newData) {
        console.log(newData)
        let arr = newData.filter((item, index) => {
          return item.ischeck === false
        })
        if (!arr.length) {
          this.allChecked = true
        } else {
          this.allChecked = false
        }
      },
      deep: true
    },
    currency (newData) {
      console.log(newData)
    }
  },
  mounted () {
    console.log(this.currencyPool)
    this.restaurants = this.loadAll()
    // 初始化语言
    this.langDefault()
    this.list()
    this.getPageCate(-1)
  },
  methods: {
    querySearch (queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter (queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    loadAll () {
      // return [
      //   { 'value': '三全鲜食（北新泾店）', 'address': '长宁区新渔路144号' },
      //   { 'value': 'Hot honey 首尔炸鸡（仙霞路）', 'address': '上海市长宁区淞虹路661号' },
      //   { 'value': '新旺角茶餐厅', 'address': '上海市普陀区真北路988号创邑金沙谷6号楼113' },
      //   { 'value': '泷千家(天山西路店)', 'address': '天山西路438号' }

      // ]
    },
    // 页面列表
    list () {
      this.pageParams.pageName = this.inputPageName
      this.pageParams.catId = this.selectValue
      pageList(this.pageParams).then((res) => {
        if (res.error === 0) {
          res.content.dataList.forEach(item => {
            item.ischeck = false
          })
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    // 当前页发生变化
    handleCurrentChange (val) {
      console.log(val)
      this.pageParams.currentPage = val
      this.list()
    },
    // 页面名称输入框监听回车事件
    handlePageName () {
      console.log(this.statePage)
    },
    // 查询事件
    handleQuery () {
      console.log(this.statePage, this.selectValue)
    },
    // 设置首页
    handleSetFirstPage (pageId) {
      this.param.pageId = pageId
      setFirstPage(this.param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success({
            type: 'success',
            message: '设置成功!'
          })
          this.list()
        }
      })
    },
    // 删除 二次确认
    del (pageId) {
      this.delPageId = pageId
      this.dialogVisible = true
    },

    delConfirm () {
      this.param.pageId = this.delPageId
      delPage(this.param).then(res => {
        if (res.error === 0) {
          this.dialogVisible = false
          this.$message.success('删除成功')
          this.list()
        }
      })
    },

    // 复制页面
    copy (pageId) {
      this.param.pageId = pageId
      pageCopy(this.param).then(res => {
        if (res.error === 0) {
          this.$message.success('复制成功')
          this.list()
        }
      })
    },
    // 下载图片
    downs () {
      //  var alink = document.createElement('a')
      // alink.href = this.shareImg
      // alink.download = name || 'pic' // 图片名
      // alink.click()
    },
    // 复制
    clickCopy () {
      this.$refs.copy.select() // 选择对象
      document.execCommand('Copy') // 执行浏览器复制命令
    },
    // 批量设置分类
    batchSetPageCate () {
      this.isBatch = 1
      let newarr = []
      this.tableData.filter((item, index) => {
        if (item.ischeck === true) {
          newarr.push(item.pageId)
        }
        return item.ischeck === true
      })
      this.pageIds = newarr.join(',')

      if (this.pageIds.length === 0) {
        this.$message.error('请选择页面')
      } else {
        this.getPageCate()
        this.pageSetdialogVisible = true
      }
    },
    // 点击新建微页面
    handleToNewPage () {
      this.tuneUpMiniPage = true
    },
    // 获取页面分类
    getPageCate (pageId) {
      this.pageId = pageId
      getPageCate().then(res => {
        console.log(res)
        if (res.error === 0) {
          let obj = {
            id: '',
            name: '全部分类'
          }
          res.content.unshift(obj)
          this.pageSetoptions = res.content
        }
      })
      if (pageId > -1) {
        this.pageSetdialogVisible = true
      }
    },
    // 装修页面设置页面分类
    savePageCate () {
      if (this.isBatch === 0) {
        this.setPageCateParam.pageId = this.pageId
        this.setPageCateParam.id = this.pageSetvalue
        setPageCate(this.setPageCateParam).then(res => {
          if (res.error === 0) {
            this.$message.success({
              type: 'success',
              message: '设置成功'
            })
            this.pageSetdialogVisible = false
            this.list()
          }
        })
      } else { // 批量设置
        this.setPageCateParam.pageIds = this.pageIds
        this.setPageCateParam.id = this.pageSetvalue
        batchSet(this.setPageCateParam).then(res => {
          if (res.error === 0) {
            this.$message.success({
              type: 'success',
              message: '设置成功'
            })
            this.pageSetdialogVisible = false
            this.list()
            this.pageIds = ''
          }
        })
      }
    },
    // 编辑点击
    edit (res) {
      console.log(res)
      this.$router.push({
        path: '/admin/home/main/decorationHome',
        query: {
          pageId: res
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.picture_setting {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .picture_settingMain {
    position: relative;
    .p_top {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
      background-color: #fff;
      padding: 15px;
      .p_top_left {
        display: flex;
        /deep/ .el-button {
          padding: none;
          height: 32px;
        }
        .tipsDiv {
          position: relative;
          cursor: pointer;
          height: 30px;
          line-height: 30px;
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
      .p_top_right {
        display: flex;
        /deep/ .el-button {
          padding: none;
          height: 32px;
        }
        span {
          white-space: nowrap;
          height: 32px;
          line-height: 32px;
          margin-right: 10px;
        }
        .topRightDiv {
          &:nth-of-type(2) {
            margin: 0 10px 0 30px;
          }
          display: flex;
          /deep/ .my-autocomplete {
            li {
              line-height: normal;
              padding: 7px;

              .name {
                text-overflow: ellipsis;
                overflow: hidden;
              }
              .addr {
                font-size: 12px;
                color: #b4b4b4;
              }

              .highlighted .addr {
                color: #ddd;
              }
            }
          }
        }
      }
    }
    .p_middle {
      background-color: #fff;
      padding: 15px;
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
          font-size: 22px;
          color: #5a8bff;
          cursor: pointer;
        }
      }
    }
    .footer {
      background-color: #fff;
      padding: 20px 0 20px 20px;
      display: flex;
      justify-content: space-between;
      .footer_right {
        display: flex;
        span {
          display: block;
          height: 32px;
          line-height: 32px;
        }
      }
    }
  }
  .pageDialog {
    /deep/ .el-dialog__header {
      text-align: center;
      background-color: #f3f3f3;
      span {
        font-size: 14px;
      }
    }
    .pageDialogMain {
      display: flex;
      justify-content: center;
    }
  }
  .shareC {
    .shareDialog_ {
      .shareDialog_content_ {
        display: flex;
        justify-content: center;
        img {
          width: 160px;
        }
      }
      .shareDialog_bottom_ {
        text-align: center;
        margin-top: 18px;
        cursor: pointer;
      }
    }
    .d_footer {
      margin-top: 10px;
      .dialogFooter {
        width: 100%;
        display: flex;
        /deep/ .el-input {
          width: 80%;
        }

        span {
          margin-left: 20px !important;
          color: #5a8bff;
          height: 28px;
          line-height: 28px;
        }
      }
    }
  }
}
</style>
<style>
</style>
