const express = require('express')
const fs = require('fs-extra')
const cors = require('cors');
const app = express()
app.use(cors());
const port = 3000

app.get('/', (req, res) => res.send('Hello World!'))

/*
  This will index files in the given argument (here -> "Folder"). This will for example let us access the files inside the folder by calling this backend. (localhost:3000/1/greensheet.pdf) <1 is folder inside "Folder" and greensheet is file inside "1">
 */
app.use(express.static('Folder'));

/*
  This will give list of folders and files inside the "Folder"
  [{"name":"1","files":[".DS_Store","GreenSheet.pdf","dsadasd.pdf"]},{"name":"2","files":["GreenSheet.pdf"]},{"name":"3","files":["GreenSheet.pdf"]},{"name":"4","files":[]}]
 */
app.use('/folders',(req, res) => {

	let result = [];

	try {
		let folders = fs.readdirSync('./Folder');
		folders.forEach(folder => {
			const currentFolder = {};
			currentFolder['name'] = folder;
			if(fs.lstatSync('./Folder' + '/' + folder).isDirectory()) { // Folder that you want to look for files.
				let files = fs.readdirSync('./Folder' + '/' + folder);

				files = files.filter(file => {
				  let dotIndex = file.indexOf('.');
				  let extension = file.substr(dotIndex);
				  if(extension === '.pdf')
				    return true;
        });
				currentFolder['files'] = files;
				console.log(files);
				result.push(currentFolder);
			}
		});
	}catch(e) {
		console.log(e);
	}

	res.send(result);
});

app.listen(port, () => console.log(`Example app listening at http://localhost:${port}`))