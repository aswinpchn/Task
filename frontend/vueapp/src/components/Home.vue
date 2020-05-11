<template>
  <div class="hello">
    <h1>List of folders and it's underlying PDF's in Designated folder</h1>
<!--    https://vuejs.org/v2/guide/list.html    Looping in HTML elements, as JSX support is nto there.-->
    <div v-for="folder in folders" :key="folder.name">
      <h2>
        Folder name: {{folder.name}}
      </h2>
      <span>
        Files in this folder:
      </span>
      <span v-for="file in folder.files" :key="file">
        <button v-on:click="handleClick(folder.name+ '/' +file)">
          {{file}}
        </button>
      </span>
    </div>
	</div>
</template>

<script>
import axios from 'axios';

// https://www.digitalocean.com/community/tutorials/vuejs-component-lifecycle      (vue lifecycle)
export default {
  name: 'Home',
	data () {
    return {
			info: null,
      folders: {}
    }
  },
  async mounted () {
    try {
      let result = await axios.get('http://localhost:3000/folders');
      //console.log(result.data);
      this.folders = result.data;
    }catch (e) {
      console.log('Error fetching folders and files');
    }
  },
  methods: {
    // https://vuejs.org/v2/guide/events.html How to handle events?
    handleClick (message) {
      //console.log(message);
      this.$router.push('/pdf/' + message); // https://stackoverflow.com/questions/35664550/vue-js-redirection-to-another-page
    }
  }
}
</script>

<style scoped>
  button {
    background: #0069ff;
    color: white;
    border-radius: 5px;
    font-size: 20px;
  }
  span {
    margin: 10px;
  }
</style>