import { defineStore } from 'pinia'
import { ref } from 'vue'

interface ProductsType {
  id: string,
  name: string,
  price: number,
  stock: number,
  image: string,
  description: string,
  score: number,
  status: string,
  shipAddress: string
}

interface ProductType {
  id: string,
  name: string,
  price: number,
  stock: number,
  image: string,
  description: string,
  score: number,
  status: string,
  shipAddress: string,
  service: string,
  farmerId: string
}

interface CommentType {
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

export const useProductsStore = defineStore('products', () => {
  // 数据
  const products = ref<ProductsType[]>([])
  const current = ref(0)
  const productsMe = ref<ProductsType[]>([])
  // 
  const product = ref<ProductType>()
  const comments = ref<CommentType[]>([])
  // 函数

  // 设置products
  function setProducts(val: ProductsType[]) {
    products.value = val
  }
  // 设置current
  function setCurrent(val: number) {
    current.value = val
  }
  // 加页数
  function plusCurrent() {
    current.value += 1
  }
  // push新的product
  function pushProducts(val: ProductsType[]) {
    val.forEach(product => {
      products.value.push(product)
    })
  }

  // 设置product
  function setProduct(val: ProductType) {
    product.value = val
  }
  // 设置comments
  function setComments(val: CommentType[]) {
    comments.value = val
  }
  // 新添comment
  function pushComments(val: CommentType[]) {
    val.forEach(c => {
      comments.value.push(c)
    })
  }

  // 设置productsMe
  function setProductsMe(val: ProductsType[]) {
    productsMe.value = val
  }
  // 新添productsMe
  function pushProductsMe(val: ProductsType[]) {
    val.forEach(p => {
      productsMe.value.push(p)
    })
  }
  // 改库存
  function changeStock(id: string, num: number) {
    productsMe.value.forEach(p => {
      if (p.id === id) {
        p.stock = num
      }
    })
  }
  // 下架货物
  function deleteProduct(id: string) {
    productsMe.value = productsMe.value.filter(p => p.id !== id)
  }

  // 清除数据
  function clearProducts() {
    products.value = []
    setCurrent(0)
  }
  function clearProductDetail() {
    product.value = {} as ProductType
    comments.value = []
  }
  function clearProductsMe() {
    productsMe.value = []
  }

  return {
    products,
    current,
    product,
    comments,
    setProducts,
    setCurrent,
    plusCurrent,
    pushProducts,
    setProduct,
    setComments,
    pushComments,
    clearProducts,
    clearProductDetail,
    productsMe,
    setProductsMe,
    pushProductsMe,
    clearProductsMe,
    changeStock,
    deleteProduct
  }
}, {
  persist: true
})