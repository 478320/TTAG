<template>
  <div class="comment-container">
    <div class="left">
      <el-avatar
        shape="circle"
        size="large"
        :src="props.data.head"
      />
      <span class="username">{{ props.data.username }}</span>
    </div>
    <div class="center">
      <el-rate v-model="props.data.review.rating" disabled />
      <div class="comment">{{ props.data.review.comment }}</div>
    </div>
    <div class="footer">
      <span>{{ props.data.review.createAt }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
  // 定义类型
  interface DataType {
  username: string,
  head: string,
  review: {
    id: string,
    userid: string,
    productid: string,
    rating: number,
    comment: string,
    createAt: string,
    orderid: string
  }
}
  import { defineProps } from 'vue'
  const props = defineProps<{
    data: DataType
  }>()
</script>

<style scoped lang="scss">
  .comment-container {
    display: flex;
    border-bottom: 1px solid rgba(0, 0, 0, 0.3);
    box-sizing: border-box;
    padding: 20px 0;
    .left {
      width: 100px;
      height: 100px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      .username {
        font-weight: 290;
        font-size: 14px;
        line-height: 19px;
      }
    }
    .center {
      padding: 10px;
      flex-grow: 1;
      .comment {
        font-weight: 290;
        font-size: 14px;
        line-height: 19px;
      }
    }
    .footer {
      font-weight: 290;
      font-size: 14px;
      line-height: 19px;
      display: flex;
      align-items: flex-end;
    }
  }
</style>