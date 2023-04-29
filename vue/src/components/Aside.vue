<template>
  <el-menu :default-openeds="opens" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(49, 139, 87)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <b style="color: white">CCER交易系统</b>
    </div>
    <div v-for="parentNode in parentNodes" :key="parentNode.id">
      <div v-if="parentNode.path">
        <el-menu-item :index="parentNode.path">
          <i :class="parentNode.icon"></i> <span slot="title">{{ parentNode.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="parentNode.id + ''">
          <template slot="title">
            <i :class="parentNode.icon"></i> <span slot="title">{{ parentNode.name }}</span>
          </template>
          <div  v-for="childNode in parentNode.children" :key="childNode.id">
            <el-menu-item :index="childNode.path">
              <i :class="childNode.icon"></i> <span slot="title">{{ childNode.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  data() {
    return {
      parentNodes: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
    }
  }
}
</script>

<style scoped>

</style>
