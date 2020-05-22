<template>
  <div class="main_container">
    <div class="video_container">
      <videoSpaceCore />
    </div>
  </div>
</template>
<script>
import videoSpaceCore from '@/components/admin/videoSpace/videoSpaceCore'
export default {
  components: { videoSpaceCore }
}
</script>
<style scoped>
  .main_container{
    padding: 10px 20px;
  }
  .video_container{
    background: #fff;
    padding: 10px 10px;
  }
</style>
