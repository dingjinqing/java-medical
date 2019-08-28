<template>
  <div>
    <reduceList />
  </div>
</template>

<script>
export default {
  components: {
    reduceList: () => import('./reduceList')
  }
}
</script>
