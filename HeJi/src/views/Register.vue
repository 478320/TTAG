<template>
  <div class="wrapper">
    <HomeCard />
  </div>
</template>

<script setup lang="ts">
  import HomeCard from '@/components/HomeCard.vue'
</script>

<style scoped lang="scss">
  .wrapper {
    width: 100vw;
    height: 100vh;
    background: url('../assets/BackgroundImg.png') no-repeat center center fixed;
    display: flex;
    flex-direction: column;
  }
</style>