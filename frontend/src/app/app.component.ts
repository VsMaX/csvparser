import { Component } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';

const URL = 'http://localhost:8080/upload';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
	public uploader:FileUploader = new FileUploader({url: URL});

	public onFileUpload() {
		this.uploader.uploadAll();
	}
 
}
