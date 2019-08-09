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
            <span>页面名称</span>
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="statePage"
              :fetch-suggestions="querySearch"
              placeholder="请输入页面名称"
              size='small'
              @keyup.enter.native="handlePageName()"
            >
              <i
                class="el-icon-search el-input__icon"
                slot="suffix"
              >
              </i>
              <template slot-scope="props">
                <div class="name">{{ props.item.value }}</div>
                <span class="addr">{{ props.item.address }}</span>
              </template>
            </el-autocomplete>
          </div>
          <div class="topRightDiv">
            <span>页面分类</span>
            <el-select
              v-model="selectValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in selectOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="topRightDiv s_btn">
            <el-button
              type="primary"
              size="small"
              @click="handleQuery()"
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
            prop="creatTime"
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
              <span v-if="scope.row.isFirstPage">是</span>
              <span
                style="color:#5A8BFF;cursor:pointer"
                v-if="!scope.row.isFirstPage"
                @click="handleSetFirstPage()"
              >设为首页</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="pageClass"
            label="页面分类"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.pageClass}}</span>
              <span style="color:#5A8BFF;cursor:pointer">设置</span>
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
                    @click="handleOperation(scope.row,0)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="删除"
                  placement="top"
                >
                  <span
                    @click="handleOperation(scope.row,1)"
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
                    @click="handleOperation(scope.row,2)"
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
            @click="handleBatchSet()"
            style="color:#5a8bff;cursor:pointer"
          >批量设置分类</span>
        </div>
        <div class="footer_right">
          <span>当前页面1/4，总记录80条</span>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="4"
          >
          </el-pagination>
        </div>
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
            placeholder="请选择"
            size="small"
          >
            <el-option
              v-for="item in pageSetoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="pageSetdialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="pageSetdialogVisible = false"
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
    <SelectTemplateDialog />
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import SelectTemplateDialog from './selectTemplateDialog'
export default {
  components: { SelectTemplateDialog },
  data () {
    return {
      iconUrl: this.$imageHost + '/image/admin/system_icon.png',
      pageNameInput: '',
      restaurants: [],
      statePage: '',
      selectValue: '',
      selectOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      pageSetvalue: '',
      pageSetoptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      tableData: [
        {
          ischeck: false,
          pageName: '尾浦巴普电商运营',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: true,
          pageClass: '测试页面'

        },
        {
          ischeck: false,
          pageName: '测试页面',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: false,
          pageClass: '测试页面'

        },
        {
          ischeck: false,
          pageName: '帅飞',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: false,
          pageClass: '测试页面'

        },
        {
          ischeck: false,
          pageName: '帅飞啊',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: false,
          pageClass: '测试页面'

        }
      ],
      allChecked: false,
      allCheckedFlag: false,
      pageSetdialogVisible: false,
      currentPage: null,
      dialogVisibleShare: false,
      shareImg: 'http://mpdev.weipubao.cn/upload/4748160/qrcode/33/T33P307bfc9947d3756c206033bd06eb13b0_20190614100251.jpg',
      pathInput: ''
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
    }
  },
  mounted () {
    this.restaurants = this.loadAll()
  },
  methods: {
    ...mapActions(['handleToCallMicropage']),
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
      return [
        { 'value': '三全鲜食（北新泾店）', 'address': '长宁区新渔路144号' },
        { 'value': 'Hot honey 首尔炸鸡（仙霞路）', 'address': '上海市长宁区淞虹路661号' },
        { 'value': '新旺角茶餐厅', 'address': '上海市普陀区真北路988号创邑金沙谷6号楼113' },
        { 'value': '泷千家(天山西路店)', 'address': '天山西路438号' }

      ]
    },
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
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
    handleSetFirstPage () {

    },
    // 删除
    handleOperation (data, flag) {
      switch (flag) {
        case 0:
          break
        case 1:
          break
        case 2:
          break
        case 3:
          this.dialogVisibleShare = true
          break
      }
      console.log(data)
    },
    // 下载图片
    downs () {
      // var alink = document.createElement('a')
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
    handleBatchSet () {
      let arr = this.tableData.filter((item, index) => {
        return item.ischeck === true
      })
      if (arr.length === 0) {
        this.$message('请选择页面')
      } else {
        this.pageSetdialogVisible = true
      }
    },
    // 点击新建微页面
    handleToNewPage () {
      this.handleToCallMicropage(true)
    }
  }
}
</script>
<style lang="scss" scoped>
.picture_setting {
  padding: 10px;
  min-width: 1400px;
  font-size: 14px;
  height: 100%;
  .picture_settingMain {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 0 20px;
    .p_top {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
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
