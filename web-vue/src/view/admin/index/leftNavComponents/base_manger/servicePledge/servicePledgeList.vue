<template>
  <div class="servicePledge">
    <div class="main">
      <!-- 标签 -->
      <el-tabs
        v-model="tabValue"
        @tab-click="clickTabs"
      >
        <!-- 标签页1 -->
        <el-tab-pane
          :label="$t('pledge.pledgeList')"
          name="first"
        >
          <!-- 文字及开关 -->
          <div class="top">
            <!-- 左边文字 -->
            <div class="left">
              <div class="top1">{{$t('pledge.pledge')}}</div>
              <div style="margin-bottom: 10px">1.{{$t('pledge.tipInfoOne')}}</div>
              <div style="padding-left: 15px">{{$t('pledge.tipInfoTwo')}}</div>
              <div style="margin-top: 10px;margin-bottom: 10px">2.{{$t('pledge.tipInfoThree')}}</div>
            </div>
            <!-- 右边开关 -->
            <div class="right">
              <el-switch
                v-model="switchValue"
                active-color="#F7931E"
                inactive-color="#ddd"
                @change="changeTotalSwitch"
              >
              </el-switch>
              <span style="color: rgb(153, 153, 153);">{{this.switchValue === true ? $t('pledge.on') : $t('pledge.off')}}</span>
            </div>
          </div>
          <!-- 按钮 -->
          <div class="mid">
            <el-button
              type="primary"
              @click="changeTabValue"
              size="small"
            >+{{$t('pledge.addButton')}}</el-button>
            <span class="mid-text">{{$t('pledge.addTip')}}</span>
          </div>
          <!-- 列表信息 -->
          <div class="table_list">
            <!-- 表格数据 -->
            <el-table
              class="table"
              :data="tableData"
              header-row-class-name="tableClss"
              border
              style="width:100%"
            >
              <el-table-column
                prop="pledgeName"
                :label="$t('pledge.name')+'：'"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="pledgeLogo"
                :label="$t('pledge.icon')+'：'"
                align="center"
              >
                <template slot-scope="scope">
                  <img
                    :src="scope.row.pledgeLogo"
                    alt=""
                    style="width: 30px;height:30px"
                  >
                </template>
              </el-table-column>
              <el-table-column
                prop="pledgeContent"
                :label="$t('pledge.explanation')+'：'"
                align="center"
              ></el-table-column>
              <el-table-column
                prop=""
                :label="$t('pledge.goods')+'：'"
                align="center"
              ></el-table-column>
              <el-table-column
                prop=""
                :label="$t('pledge.priority')+'：'"
                align="center"
              ></el-table-column>
              <el-table-column
                :label="$t('pledge.option')+'：'"
                align="center"
              >
                <template slot-scope="scope">
                  <div style="display: flex">
                    <!-- 开关 -->
                    <div class="changeSwitch">
                      <el-switch
                        v-model="scope.row.state"
                        active-color="#F7931E"
                        inactive-color="#ddd"
                        @change="changeOneSwitch(scope.row)"
                      >
                      </el-switch>
                      <span style="color: rgb(153, 153, 153);">
                        {{scope.row.state === true ? $t('pledge.on') : $t('pledge.off')}}
                      </span>
                    </div>
                    <!-- 图标 -->
                    <div class="opt">
                      <!-- 编辑 -->
                      <el-tooltip
                        class="item edit-item"
                        effect="dark"
                        :content="$t('pledge.edit')"
                        placement="top"
                      >
                        <i
                          class="el-icon-edit-outline"
                          @click="editAct(scope.row)"
                        ></i>
                      </el-tooltip>

                      <!-- 删除 -->
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="$t('pledge.delete')"
                        placement="top"
                      >
                        <i
                          class="el-icon-delete"
                          @click="deleteAct(scope.row.id)"
                        ></i>
                      </el-tooltip>
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <!-- 标签页2 -->
        <el-tab-pane
          :label="$t('pledge.addButton')"
          name="second"
          v-if="this.tabValue==='second'"
        >
          <!-- 添加标签页 -->
          <div class="add-page">
            <el-form
              ref="form"
              :model="form"
              :rules="rules"
              label-width="180px"
            >
              <!-- 服务名称 -->
              <el-form-item
                :label="$t('pledge.name')+'：'"
                prop="name"
              >
                <el-input
                  style="width:200px"
                  size="small"
                  v-model="form.name"
                  :placeholder="$t('pledge.nameCheck')"
                ></el-input>
                <span style="padding-left: 15px;color:#999">{{$t('pledge.nameTip')}}</span>
              </el-form-item>
              <!-- 优先级 -->
              <el-form-item :label="$t('pledge.priority')+'：'">
                <el-input
                  style="width:200px"
                  size="small"
                  v-model="form.first"
                  :placeholder="$t('pledge.priorityCheck')"
                ></el-input>
                <span style="padding-left: 15px;color:#999">{{$t('pledge.priorityTip')}}</span>
              </el-form-item>
              <!-- 图标 -->
              <el-form-item
                :label="$t('pledge.icon')+'：'"
                prop="logos"
              >

                <!-- 图片弹窗 -->
                <div style="display:flex;align-items:center;flex-wrap:wrap;">
                  <div
                    @click="addGoodsImg"
                    class="ImgWrap"
                  >
                    <el-image
                      style="width: 50px; height: 50px"
                      :src="srcList.src"
                      fit="scale-down"
                    ></el-image>
                  </div>
                  <span style="padding-left: 15px;color:#999">{{$t('pledge.iconTip')}}</span>
                </div>

              </el-form-item>
              <!-- 承诺说明 -->
              <el-form-item
                :label="$t('pledge.explanation')+'：'"
                prop="desc"
              >
                <el-input
                  :autosize="{ minRows: 5}"
                  style="width:400px"
                  size="small"
                  type="textarea"
                  v-model="form.desc"
                ></el-input>
                <span style="padding-left: 15px;color:#999">{{$t('pledge.explanationTip')}}</span>
              </el-form-item>
              <!-- 选择商品 -->
              <el-form-item :label="$t('pledge.chooseGoods')+'：'">
                <el-radio
                  v-model="form.goods"
                  label=1
                >{{$t('pledge.allGoods')}}</el-radio>
                <el-radio
                  v-model="form.goods"
                  label=2
                >{{$t('pledge.someGoods')}}</el-radio>
              </el-form-item>
            </el-form>
          </div>
          <!-- 保存按钮 -->
          <div class="footer">
            <el-button
              type="primary"
              size="small"
              @click="addAct('form')"
            >{{$t('pledge.save')}}</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 图片弹窗 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="showImageDialog"
      @handleSelectImg='imgDialogSelectedCallback'
    />
  </div>
</template>

<script>
import ImageDalog from '@/components/admin/imageDalog'
import { pledgeList, addPledge, delPledge, editPledge, totalSwitch, oneSwitch } from '@/api/admin/basicConfiguration/servicePledge.js'
export default {
  components: {
    ImageDalog
  },
  data () {
    return {
      tabValue: 'first',
      switchValue: '',
      tableData: [],
      id: '',
      // 表单数据
      form: {
        name: '',
        first: '',
        icon: '',
        desc: '',
        logos: '',

        goods: '1'
      },
      // 数据校验
      rules: {
        name: [
          { required: true, message: this.$t('pledge.nameCheck'), trigger: 'blur' },
          { max: 5, message: this.$t('pledge.nameTip'), trigger: 'blur' }
        ],
        icon: [
          { required: true, message: this.$t('pledge.iconCheck'), trigger: 'blur' }
        ],
        desc: [
          { required: true, message: this.$t('pledge.explanationCheck'), trigger: 'blur' },
          { max: 300, message: this.$t('pledge.explanationTip'), trigger: 'blur' }
        ],
        logos: [
          { required: true, message: this.$t('pledge.iconCheck'), trigger: 'blur' }
        ]
      },
      // 图片弹窗
      srcList: {
        src: `${this.$imageHost}/image/admin/add_img.png`
      },
      showImageDialog: false,
      imgHost: `${this.$imageHost}`
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    clickTabs (tab, event) {
      console.log(tab, event)
      this.loadData()
    },
    // 初始化加载页面
    loadData () {
      this.form = {
        name: '',
        first: '',
        icon: '',
        desc: '',
        goods: '1'
      }
      this.id = null
      this.srcList.src = `${this.$imageHost}/image/admin/add_img.png`
      pledgeList().then(res => {
        console.log('listRes:', res)
        if (res.error === 0) {
          res.content.list.map((item, index) => {
            item.state = Boolean(item.state)
          })
          this.tableData = res.content.list
          console.log('state:', Boolean(res.content.state))
          this.switchValue = Boolean(res.content.state)
        }
      }).catch(() => {
        this.$message.error(this.$t('pledge.fail'))
      })
    },
    // 添加服务承诺
    addAct (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          // 校验通过后...
          let addParam = {
            'pledgeName': this.form.name,
            'pledgeLogo': this.form.icon,
            'pledgeContent': this.form.desc
          }
          let editParam = {
            'id': this.id,
            'pledgeName': this.form.name,
            'pledgeLogo': this.form.icon,
            'pledgeContent': this.form.desc
          }
          if (this.id !== null) {
            editPledge(editParam).then(res => {
              console.log('editRes:', res)
              if (res.error === 0) {
                alert(this.$t('pledge.success'))
                this.tabValue = 'first'
                this.loadData()
              }
            }).catch(() => {
              this.$message.error(this.$t('pledge.fail'))
            })
          } else {
            addPledge(addParam).then(res => {
              console.log('addRes:', res)
              if (res.error === 0) {
                alert(this.$t('pledge.success'))
                this.tabValue = 'first'
                this.loadData()
              }
            }).catch(() => {
              this.$message.error(this.$t('pledge.fail'))
            })
          }
          // 校验没通过后...
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 删除服务承诺
    deleteAct (id) {
      this.$confirm(this.$t('pledge.isDelete'), this.$t('pledge.tip'), {
        confirmButtonText: this.$t('pledge.yes'),
        cancelButtonText: this.$t('pledge.no'),
        type: 'warning'
      }).then(() => {
        let delParam = {
          'id': id
        }
        delPledge(delParam).then(res => {
          if (res.error === 0) {
            this.$message.success(this.$t('pledge.success'))
            this.loadData()
          }
        })
      })
    },
    // 切换标签页
    changeTabValue () {
      this.tabValue = 'second'
      console.log('tabValue:', this.tabValue)
    },
    // 编辑服务承诺
    editAct (row) {
      this.tabValue = 'second'
      this.form.name = row.pledgeName
      this.form.icon = row.pledgeLogo
      this.form.desc = row.pledgeContent
      this.id = row.id
      this.srcList.src = this.form.icon
    },
    // 总开关配置
    changeTotalSwitch () {
      console.log('this.switchValue:', this.switchValue)
      totalSwitch(Number(this.switchValue).toString()).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('pledge.success'))
          this.loadData()
        }
      })
    },
    // 单个开关配置
    changeOneSwitch (row) {
      console.log(row)
      let switchParam = {
        'id': row.id,
        'state': Number(row.state)
      }
      oneSwitch(switchParam).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('pledge.success'))
        }
      })
    },
    // 活动分享 -- 添加图片点击事件，弹出图片选择组件
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    imgDialogSelectedCallback (src) {
      this.srcList.src = src.imgUrl
      this.form.icon = this.srcList.src
      console.log('icon:', this.form.icon)
    }
  }
}
</script>

<style scoped lang="scss">
.servicePledge {
  padding: 10px;
  height: 100%;
  .main {
    background-color: #fff;
    padding: 10px;
    .top {
      background-color: #f2f2f2;
      height: 120px;
      padding: 10px;
      font-family: "微软雅黑";
      font-size: 14px;
      display: flex;
      justify-content: space-between;
      .left {
        .top1 {
          font-size: 20px;
          margin-bottom: 10px;
          font-weight: bold;
        }
      }
      .right {
        width: 110px;
      }
    }
    .mid {
      background-color: #fff;
      height: 70px;
      width: 100%;
      padding: 15px;
      .mid-text {
        padding-left: 15px;
        color: #999;
        font-size: 14px;
      }
    }
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      color: #000;
      padding: 8px 10px;
    }
    .table_list {
      position: relative;
      background-color: #fff;
      padding: 0 0 10px;
      // /deep/.el-table__row {
      //   height: 45px;
      //   line-height: 45px;
      // }
    }
    .footer {
      position: fixed;
      bottom: 0;
      right: 27px;
      left: 160px;
      height: 52px;
      padding: 10px 0;
      background: #fff;
      text-align: center;
    }
  }
}
.changeSwitch {
  margin-left: 30px;
}
.opt {
  .item {
    font-size: 22px;
    color: #66b1ff;
    cursor: pointer;
  }
  .edit-item {
    margin: 0 10px 0 20px;
  }
}
.ImgWrap {
  width: 50px;
  height: 50px;
  border: 1px solid #ccc;
}
</style>
