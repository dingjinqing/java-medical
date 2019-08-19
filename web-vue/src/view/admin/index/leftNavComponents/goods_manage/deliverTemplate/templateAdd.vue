<template>
  <div class="templateAdd">
    <el-form
      ref="addForm"
      :model="addForm"
      label-width="120px"
    >
      <el-form-item label="模板名称：">
        <el-input
          v-model="name"
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
        <section
          class="other"
          v-show="!checked"
        >
          其他区域运费:
          <el-input
            size="small"
            v-model="piece"
            style="width:50px;"
          ></el-input>
          件内，
          <el-input
            size="small"
            v-model="money"
            style="width:50px;"
          ></el-input>
          元，每增加
          <el-input
            size="small"
            v-model="addPiece"
            style="width:50px;"
          ></el-input>
          件，增加运费
          <el-input
            size="small"
            v-model="addMoney"
            style="width:50px;"
          ></el-input>
          元
        </section>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>
export default {
  name: 'templateAdd',
  // data() {
  //   return {
  //     addForm: {

  //     },
  //     checked: false,
  //     piece: 1,
  //     money: 0,
  //     addPiece: 1,
  //     addMoney: 0,
  //     name: ''

  //   }
  // },
  methods: {
    // testData () {
    //   let params = {
    //     'templateName': this.name,
    //     'goodsDeliverTemplateLimitParam': {

    //     }
    //   }
    //   console.log(params)
    // }
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
  display: flex;
  /* justify-content: center; */
  align-items: center;
}
</style>
