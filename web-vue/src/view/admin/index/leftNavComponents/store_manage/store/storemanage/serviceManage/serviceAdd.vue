<template>
  <div>
    <div class="sevice_list">
      <el-steps
        :active="1"
        simple
      >
        <el-step title="1.编辑基本信息"></el-step>
        <el-step title="2.编辑服务详情"></el-step>
      </el-steps>
      <div>
        <el-form
          ref="form"
          :model="form"
          label-width="80px"
        >
          <el-form-item label="服务名称：">
            <el-input></el-input>
          </el-form-item>
          <el-form-item label="服务价格："></el-form-item>
          <el-form-item label="预约订金："></el-form-item>
          <el-form-item label="收费说明："></el-form-item>
          <el-form-item label="服务分类："></el-form-item>
          <el-form-item label="上下架："></el-form-item>
          <el-form-item label="服务主图："></el-form-item>
          <el-form-item label="服务模式："></el-form-item>
          <el-form-item label="可服务日期："></el-form-item>
          <el-form-item label="服务时段："></el-form-item>
          <el-form-item label="服务时长："></el-form-item>
          <el-form-item label="同时段可服务人数"></el-form-item>
          <el-form-item label="同时段内技师可服务人数"></el-form-item>
        </el-form>
      </div>
      <div class="">
        <div class="service_detail_preview"></div>
        <div class="service_detail_edit"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      form: {}
    }
  },
  methods: {

  }
}
</script>
