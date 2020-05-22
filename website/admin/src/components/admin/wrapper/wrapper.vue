<template>
  <el-card class="wrapper">
    <div class="card-body">
      <slot />
    </div>
  </el-card>
</template>
<script>
export default {

}
</script>
<style lang="scss" scoped>
.wrapper {
  display: flex;
  margin: 10px;
  border-radius: 0;
  box-shadow: none;
}
</style>
<style lang="scss">
.el-card__body {
  width: 100% !important;
}
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.tableHeader th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: 400;
  color: #000;
  padding: 8px 10px;
}
</style>
