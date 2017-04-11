import { Component, NgZone, Inject, EventEmitter } from '@angular/core';
import { NgUploaderOptions, UploadedFile, UploadRejected } from 'ngx-uploader';

const URL = 'http://localhost:8080/upload';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {

	options: NgUploaderOptions;
	inputUploadEvents: EventEmitter<string>;

	constructor() {
		this.options = new NgUploaderOptions({
			url: URL,
			filterExtensions: true,
			allowedExtensions: ['csv'],
			maxSize: 2097152,
			data: { fileName: "testFile1.csv" },
			autoUpload: false,
			fieldName: 'file',
			fieldReset: true,
			method: 'POST',
			previewUrl: true,
			withCredentials: false
		});

		this.inputUploadEvents = new EventEmitter<string>();
	}

	public handleUpload() {
		this.inputUploadEvents.emit('startUpload');
	}

	public 

}
