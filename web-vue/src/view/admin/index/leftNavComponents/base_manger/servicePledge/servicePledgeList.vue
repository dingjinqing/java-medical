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
          label="服务承诺列表"
          name="first"
        >
          <!-- 文字及开关 -->
          <div class="top">
            <!-- 左边文字 -->
            <div class="left">
              <div class="top1">服务承诺</div>
              <div style="margin-bottom: 10px">1.“服务承诺”是店铺对用户承诺能做到的服务和服务质量。是重视消费者利益，保证自己的产品质量、售后服务</div>
              <div style="padding-left: 15px">不发布虚假信息，无欺诈消费者的行为。</div>
              <div style="margin-top: 10px;margin-bottom: 10px">2.服务承诺开启后，用户即可在手机端商品详情中看到，请您如实履行。</div>
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
              <span style="color: rgb(153, 153, 153);">{{this.switchValue === true ? '已开启' : '已关闭'}}</span>
            </div>
          </div>
          <!-- 按钮 -->
          <div class="mid">
            <el-button
              type="primary"
              @click="changeTabValue"
            >+添加服务承诺</el-button>
            <span class="mid-text">最多可以添加20条</span>
          </div>
          <!-- 列表信息 -->
          <div class="bot">
            <!-- 表格数据 -->
            <el-table
              class="table"
              :data="tableData"
              border
              style="width:100%"
            >
              <el-table-column
                prop="pledgeName"
                label="服务名称"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="pledgeLogo"
                label="图标"
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
                label="承诺说明"
                align="center"
              ></el-table-column>
              <el-table-column
                prop=""
                label="使用商品"
                align="center"
              ></el-table-column>
              <el-table-column
                prop=""
                label="优先级"
                align="center"
              ></el-table-column>
              <el-table-column
                label="操作"
                align="center"
              >
                <template slot-scope="scope">
                  <!-- 开关 -->
                  <div class="">
                    <el-switch
                      v-model="scope.row.state"
                      active-color="#F7931E"
                      inactive-color="#ddd"
                      @change="changeOneSwitch(scope.row)"
                    >
                    </el-switch>
                    <span style="color: rgb(153, 153, 153);">
                      {{scope.row.state === true ? '已开启' : '已关闭'}}
                    </span>
                  </div>
                  <!-- 图标 -->
                  <div class="opt">
                    <!-- 编辑 -->
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="编辑"
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
                      content="删除"
                      placement="top"
                    >
                      <i
                        class="el-icon-delete"
                        @click="deleteAct(scope.row.id)"
                      ></i>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <!-- 标签页2 -->
        <el-tab-pane
          label="添加服务承诺"
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
                label="服务名称"
                prop="name"
              >
                <el-input
                  style="width:200px"
                  size="small"
                  v-model="form.name"
                  placeholder="请填写服务名称"
                ></el-input>
                <span style="padding-left: 15px;color:#999">最多填写5个字</span>
              </el-form-item>
              <!-- 优先级 -->
              <el-form-item label="优先级">
                <el-input
                  style="width:200px"
                  size="small"
                  v-model="form.first"
                  placeholder="请输入优先级"
                ></el-input>
                <span style="padding-left: 15px;color:#999">服务承诺在前端店铺将按优先级从大到小显示</span>
              </el-form-item>
              <!-- 图标 -->
              <el-form-item label="图标">

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
                  <span style="padding-left: 15px;color:#999">尺寸：30*30像素</span>
                </div>

              </el-form-item>
              <!-- 承诺说明 -->
              <el-form-item
                label="承诺说明"
                prop="desc"
              >
                <el-input
                  :autosize="{ minRows: 5}"
                  style="width:400px"
                  size="small"
                  type="textarea"
                  v-model="form.desc"
                ></el-input>
                <span style="padding-left: 15px;color:#999">最多300字</span>
              </el-form-item>
              <!-- 选择商品 -->
              <el-form-item label="选择商品">
                <el-radio
                  v-model="form.goods"
                  label=1
                >全部商品</el-radio>
                <el-radio
                  v-model="form.goods"
                  label=2
                >指定商品</el-radio>
              </el-form-item>
            </el-form>
          </div>
          <!-- 保存按钮 -->
          <div class="footer">
            <el-button
              type="primary"
              size="small"
              @click="addAct('form')"
            >保存</el-button>
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
        goods: '1'
      },
      // 数据校验
      rules: {
        name: [
          { required: true, message: '请填写服务名称', trigger: 'blur' },
          { max: 5, message: '最多填写5个字', trigger: 'blur' }
        ],
        icon: [
          { required: true, message: '请选择图标', trigger: 'blur' }
        ],
        desc: [
          { required: true, message: '请填写承诺说明', trigger: 'blur' }
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
      this.form = {}
      this.form.goods = '1'
      this.id = null
      this.srcList.src = `${this.$imageHost}/image/admin/add_img.png`
      console.log('进入初始化方法')
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
        this.$message.error('操作失败')
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
                alert('操作成功')
                this.tabValue = 'first'
                this.loadData()
              }
            }).catch(() => {
              this.$message.error('操作失败')
            })
          } else {
            addPledge(addParam).then(res => {
              console.log('addRes:', res)
              if (res.error === 0) {
                alert('操作成功')
                this.tabValue = 'first'
                this.loadData()
              }
            }).catch(() => {
              this.$message.error('操作失败')
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
      this.$confirm('确认要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let delParam = {
          'id': id
        }
        delPledge(delParam).then(res => {
          if (res.error === 0) {
            this.$message.success('删除成功!')
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
          this.$message.success('操作成功!')
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
          this.$message.success('操作成功!')
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
      width: 100%;
      padding: 10px;
      font-family: "微软雅黑";
      font-size: 14px;
      flex: 1.5;
      flex-grow: 1.5;
      flex-shrink: 1;
      flex-basis: 0%;
      .left {
        float: left;
        .top1 {
          font-size: 20px;
          margin-bottom: 10px;
          font-weight: bold;
        }
      }
      .right {
        float: right;
        right: 0;
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
    .bot {
      // background-color: #f2f2f2;
      height: 800px;
      width: 100%;
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
.opt {
  display: flex;
  justify-content: space-around;
  > .item {
    font-size: 22px;
    color: #66b1ff;
    cursor: pointer;
  }
}
.ImgWrap {
  width: 50px;
  height: 50px;
  border: 1px solid #ccc;
  // margin: 5px 5px;
  // position: relative;
}
</style>
