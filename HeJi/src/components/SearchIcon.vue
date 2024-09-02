<template>
  <svg class="icon" @click="clickSearch" width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M10.9374 19.7916C15.8274 19.7916 19.7916 15.8274 19.7916 10.9374C19.7916 6.04742 15.8274 2.08325 10.9374 2.08325C6.04742 2.08325 2.08325 6.04742 2.08325 10.9374C2.08325 15.8274 6.04742 19.7916 10.9374 19.7916Z" stroke="#F5D35A" stroke-width="4" stroke-linejoin="round"/>
    <path d="M13.8838 7.47036C13.1298 6.71635 12.0881 6.25 10.9375 6.25C9.78689 6.25 8.74522 6.71635 7.99121 7.47036" stroke="#F5D35A" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
    <path d="M17.303 17.303L21.7224 21.7224" stroke="#F5D35A" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
  </svg>
</template>

<script setup lang="ts">
  import emitter from '@/utils/emitter'
  function clickSearch() {
    emitter.emit('click-search')
  }
</script>

<style scoped>
  .icon {
    cursor: pointer;
  }
</style>