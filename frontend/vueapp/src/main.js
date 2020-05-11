import Vue from 'vue'
import App from './App.vue'
import PDFDisplay from "./components/PDFDisplay";
import VueRouter from "vue-router";
import HelloWorld from "./components/HelloWorld";
import Home from "./components/Home";

// https://www.youtube.com/watch?v=nnVVOe7qdeQ&list=PLpzy7FIRqpGDuLIo0zZ43CpA5MmYnnCUy&index=13  (How to use Vue router)
Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  mode: 'history',
  routes: [
    { path: '/pdf/:folder/:file', component:PDFDisplay },
    { path: '/', component: HelloWorld },
    {path: '/home', component: Home}
  ]
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
