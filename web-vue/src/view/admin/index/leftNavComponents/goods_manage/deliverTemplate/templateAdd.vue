<template>
  <div class="templateAdd">
    <el-form
      ref="addForm"
      :model="addForm"
      label-width="120px"
    >
      <el-form-item label="模板名称：">
        <el-input
          size="small"
          style="width:210px"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <section class="checkbox">
          <el-checkbox v-model="checked">除可配送区域外，其他不可配送</el-checkbox>
        </section>
      </el-form-item>
      <el-form-item>
        <section class="other">

        </section>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>
export default {
  name: 'templateAdd',
  data () {
    return {
      addForm: {

      },
      checked: false

    }
  },
  created () {
    console.log(this.$route)
  }
}
</script>
<style scoped>
.checkbox {
  width: 455px;
  height: 50px;
  background-color: #fff7ec;
  border: 1px solid #ffd6a6;
  color: #333;
  line-height: 36px;
  font-size: 14px;
  padding: 10px;
  padding-top: 5px;
}
.other {
  border: 1px solid #ddd;
  border-radius: 0px;
  width: 865px;
  height: 51px;
}
</style>
