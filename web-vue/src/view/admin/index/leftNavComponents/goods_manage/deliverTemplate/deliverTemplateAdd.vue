<template>
  <!-- 添加运费模板组件 -->
  <div class="deliverTemplateAdd">
    <el-form
      ref="form"
      :model="formData"
      label-width="120px"
    >
      <el-form-item
        label="模板名称："
        class="name"
      >
        <!-- 模板名称 -->
        <section>
          <el-input
            v-model="formData.templateName"
            size="small"
            style="width:210px"
          ></el-input>
        </section>
        <!-- 橙黄框 -->
        <section class="wrap">
          <el-checkbox v-model="checked">除可配送区域外，其他不可配送</el-checkbox>
        </section>
        <!-- 其他区域运费 -->
        <section
          class="other"
          v-show="!checked"
        >
          其他区域运费:
          <el-input
            size="small"
            v-model="formData.piece"
            style="width:50px;"
          ></el-input>
          件内，
          <el-input
            size="small"
            v-model="formData.money"
            style="width:50px;"
          ></el-input>
          元，每增加
          <el-input
            size="small"
            v-model="formData.addPiece"
            style="width:50px;"
          ></el-input>
          件，增加运费
          <el-input
            size="small"
            v-model="formData.addMoney"
            style="width:50px;"
          ></el-input>
          元
        </section>
      </el-form-item>
      <!-- 配送区域 -->
      <el-form-item>
        <!-- 配送区域表格 -->
        <section>
          <el-table
            :data="tableData"
            border
            style="width: 100%"
            header-align="center"
            :header-cell-style="tableHeaderStyle"
          >
            <!-- 表格无数据的时候 -->
            <template slot="empty">
              .
            </template>
            <!-- 可配送区域 -->
            <el-table-column
              prop="price"
              label="可配送区域"
              align="center"
            >
            </el-table-column>
            <!-- 首件（件） -->
            <el-table-column
              prop="price"
              label="首件（件）"
              align="center"
            >
            </el-table-column>
            <!-- 运费（元） -->
            <el-table-column
              prop="price"
              label="运费（元）"
              align="center"
            >
            </el-table-column>
            <!-- 续件（件） -->
            <el-table-column
              prop="price"
              label="续件（件）"
              align="center"
            >
            </el-table-column>
            <!-- 续费（元） -->
            <el-table-column
              prop="price"
              label="续费（元）"
              align="center"
            >
            </el-table-column>
          </el-table>
          <section class="add">
            <el-button
              type="text"
              style="color:#686868"
              @click="handleAdd"
            >指定可配送区域和运费</el-button>
          </section>
        </section>
        <!-- 指定条件包邮（可选） -->
        <section class="designation">
          <el-checkbox v-model="checked1">指定条件包邮（可选）</el-checkbox>
        </section>
        <section class="">
          <el-table
            :data="tableData1"
            border
          >
            <!-- 包邮可配送区域 -->
            <el-table-column
              prop="price"
              label="包邮可配送区域"
              align="center"
            >
            </el-table-column>
            <!-- 设置包邮条件 -->
            <el-table-column
              prop="price"
              label="设置包邮条件"
              align="center"
            >
            </el-table-column>
          </el-table>

        </section>
      </el-form-item>
    </el-form>
    <!-- 添加模板 -->
    <section class="add_template">
      <!-- 添加模板按钮 -->
      <el-button
        @click="handleAddTemplate"
        size="small"
        type="primary"
      >添加模板</el-button>
    </section>
    <!-- 指定运费模板省市区三级联动 弹窗-->
    <areaLinkage :outerVisible.sync="outerVisible" />
  </div>
</template>
<script>
// 引入省市区三级联动
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage'
export default {
  name: 'templateAdd',
  components: { areaLinkage },
  data () {
    return {
      formData: {
        templateName: ``,
        piece: 1, // 几件内
        money: 0, // 多少元
        addPiece: 1, // 每增加多少件
        addMoney: 0 // 增加运费多少
      }, // 表单的数据
      checked: false, // 默认其他区域可配送
      checked1: false, // 指定条件包邮（可选）
      // 表格数据
      tableData: [],
      tableData1: [],
      outerVisible: false
    }
  },
  methods: {
    //  修改table header的样式
    tableHeaderStyle ({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return 'background-color: #f5f5f5;color: #333;font-weight: 500;'
      }
    },
    // 指定可配送区域和运费
    handleAdd () {
      this.outerVisible = true
    },
    // 添加模板
    handleAddTemplate () {

    }
  }
}
</script>
<style scoped>
.wrap {
  width: 455px;
  height: 50px;
  background-color: #fff7ec;
  border: 1px solid #ffd6a6;
  display: flex;
  align-items: center;
  padding: 10px;
  margin: 20px 0;
}
.designation {
  margin: 10px 0;
}
.add_template {
  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
.add {
  border: 1px solid #ddd;
  border-top: 0px solid #ddd;
}
</style>
