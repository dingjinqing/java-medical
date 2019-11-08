<template>
  <div class="main_main">
    <userportraitsex />
    <userportraitReg />
    <userportraitsexPhone />
    <userportraitsexTable />
  </div>
</template>

<script>
import userportraitsex from './userportraitsex'
import userportraitReg from './userportraitReg'
import userportraitsexPhone from './userportraitsexPhone'
import userportraitsexTable from './userportraitsexTable'
export default {
  components: {
    userportraitsex,
    userportraitReg,
    userportraitsexPhone,
    userportraitsexTable
  },
  data () {
    return {
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
  },
  methods: {
  }
}

</script>
<style lang="scss" scoped>
.main_main {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>
