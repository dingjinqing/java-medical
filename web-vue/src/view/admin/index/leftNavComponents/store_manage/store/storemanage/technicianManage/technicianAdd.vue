<template>
  <div>
    <div class="technician_add_page">
      <div class="technician_add_content">
        <div class="technician_add_info">
          <el-form
            ref="technicianForm"
            :model="form"
            :rules="rules"
            label-width="180px"
          >
            <el-form-item
              label="技师名称："
              prop="name"
            >
              <el-input
                size="small"
                class="middle_input"
                v-model="form.name"
              ></el-input>
            </el-form-item>
            <el-form-item label="手机号码：">
              <el-input
                type="tel"
                size="small"
                class="middle_input"
              ></el-input>
            </el-form-item>
            <el-form-item label="头像：">
              <el-image></el-image>
            </el-form-item>
            <el-form-item label="介绍：">
              <el-input
                size="small"
                class="big_input"
              ></el-input>
            </el-form-item>
            <el-form-item label="所属分组：">
            </el-form-item>
            <el-form-item label="服务项目：">
              <el-radio-group>
                <el-radio>全部项目</el-radio>
                <el-radio>部分项目</el-radio>
              </el-radio-group>
              <el-table></el-table>
            </el-form-item>
            <el-form-item label="备注：">
              <el-input
                type="textarea"
                placeholder="备注不能超过200字"
                :maxlength="200"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="step_2">
          <div class="technician_detail_preview"></div>
          <div class="technician_detail_edit"></div>
        </div>
      </div>
      <div class="footer">
        <el-button
          size="small"
          type="primary"
          class="footer-btn"
        >保存</el-button>
        <el-button
          size="small"
          class="footer-btn"
        >下一步</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      form: {
        name: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入服务名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.technician_add_page {
  position: relative;
  width: 100%;
  .technician_add_content {
    margin: 0 25px 50px;
    .technician_add_info {
      input {
        padding-left: 20px;
      }
      .big_input {
        width: 400px;
      }
      .middle_input {
        width: 250px;
      }
    }
  }
  .footer {
    position: fixed;
    bottom: 0;
    display: flex;
    justify-content: center;
    width: calc(100% - 186px);
    padding: 10px;
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    .footer-btn {
      width: 105px;
      margin: 0 10px;
    }
  }
}
</style>
