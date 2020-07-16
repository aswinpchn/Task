<template>
  <div id="app">
    <PDFDocument v-bind="{url, scale}" />
  </div>
</template>

<script>
  /*
    This is a basic implementation of PDFJS in Vue.<No page controls> (https://github.com/rossta/vue-pdfjs-demo/tree/tutorial/part-1-simple-document)
    For complex implementation <With zoom, page controls etc.> (https://github.com/rossta/vue-pdfjs-demo/tree/master)
   */
  import PDFDocument from './PDFViewer/PDFDocument'; // This is required for embedding PDF in a vue page.

  export default {
    name: 'PDFDisplay',
    components: {
      PDFDocument
    },
    data () {
      return {
        url: '', // a PDF
        scale: 2
      }
    },
    mounted () {
      // console.log(this.$route.params.folder);
      // console.log(this.$route.params.file);

      let url = 'http://localhost:3000/' + this.$route.params.folder + '/' + this.$route.params.file;
      console.log(url);
      this.url = url;
    }
  }
</script>
